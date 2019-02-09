package ru.geekbrains.screen

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.audio.Music
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import ru.geekbrains.base.*
import ru.geekbrains.spaceObjects.SpaceShip
import java.io.*

class GameScreen : Base2DScreen() {

    private var wallpaperImg: Texture

    private var dest: Point
    private var ship: SpaceShip

    private var music: Music? = null
    
    private var memeManager: MemeManager
    
    var textureAtlas: TextureAtlas = TextureAtlas("meme_spaceship_sprite.txt")

    init {
        super.show()
        wallpaperImg = Texture("space.jpg")
        ship = SpaceShip()
        if (Gdx.files.local(".\\gameInstance.ser").exists()){
            restoreInstance()
            println("INSTANCE RESTORED")
        } else {
            ship = SpaceShip((SCREEN_WIDTH / 2).toDouble(), 0.0)
            ship.width = SCREEN_WIDTH / 100 * 10
            ship.height = SCREEN_HEIGHT / 100 * 15
        }
        dest = Point(ship.position)
        music = Gdx.audio.newMusic(Gdx.files.internal("shooting_stars.mp3"))
        music!!.isLooping = true
        music!!.play()

        memeManager = MemeManager()
        memeManager.memeFactory = MemeFactory(SCREEN_WIDTH.toDouble(), SCREEN_WIDTH.toDouble())
        memeManager.generateMemeList()
        
        memeManager.award = memeManager.memeFactory.getAward()
        
    }

    override fun render(delta: Float) {
        super.render(delta)
        Gdx.gl.glClearColor(0.5f, 0.2f, 0.3f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        drawGameObjects()

        memeManager.move()
        ship.move()
        
        memeManager.checkDamage(ship)

        val updateWithoutDamageCounter = ship.checkDamage(memeManager.memeList)
        memeManager.checkAward(updateWithoutDamageCounter, batch)
    
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE) || Gdx.input.isKeyJustPressed(Input.Keys.BACK))
            saveInstance()
    }
    
    private fun drawGameObjects() {
        batch.begin()
        batch.draw(wallpaperImg, 0f, 0f, SCREEN_WIDTH.toFloat(), SCREEN_HEIGHT.toFloat())
        batch.end()

        memeManager.render(batch)
        ship.render(batch)
        ship.fire()
    }

    private fun saveInstance(){
        var instance = GameInstance()
        instance.ship = this.ship
        instance.musicBeginningTimeSec = this.music!!.position.toDouble()
    
        try {
            var fileOut = FileOutputStream(".\\gameInstance.ser")
            var outStream = ObjectOutputStream(fileOut)
            outStream.writeObject(instance)
            outStream.close()
            fileOut.close()
            println("Serialized data is saved in \\gameInstance.ser")
        } catch (i: IOException) {
            i.printStackTrace()
        }
    }
    
    private fun restoreInstance() {
        var instance: GameInstance
        try {
            var fileIn = FileInputStream(".\\gameInstance.ser")
            var inStream = ObjectInputStream(fileIn)
            instance = inStream.readObject() as GameInstance
            inStream.close()
            fileIn.close()
        } catch (i: IOException) {
            i.printStackTrace()
            return
        } catch (c: ClassNotFoundException) {
            System.out.println("GameInstance class not found")
            c.printStackTrace()
            return
        }
    
        println(instance)
        ship = instance.ship
        ship.restore()
        println(ship)
    }
 
    override fun resize(width: Int, height: Int) {
        super.resize(width, height)
        ship.resize(SCREEN_WIDTH.toDouble(), SCREEN_HEIGHT.toDouble())
        memeManager.resize(SCREEN_WIDTH.toDouble(), SCREEN_HEIGHT.toDouble())
        memeManager.award.resize(SCREEN_WIDTH, SCREEN_HEIGHT)
        ship.bulletList.map { it -> it.resize(SCREEN_WIDTH.toDouble(), SCREEN_HEIGHT.toDouble()) }
    }

    override fun dispose() {
        wallpaperImg.dispose()
        batch.dispose()
        textureAtlas.dispose()

        super.dispose()
    }

    override fun touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        dest = Point(screenX, SCREEN_HEIGHT - screenY, 0)
        ship.updateDirection(screenX.toDouble(), SCREEN_WIDTH.toDouble(), false)
        return super.touchDown(dest, pointer)
    }

    override fun touchUp(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        ship.updateDirection(screenX.toDouble(), SCREEN_WIDTH.toDouble(), true)
        return super.touchUp(screenX, screenY, pointer, button)
    }

    override fun pause() {
        super.pause()
        music!!.pause()
    }

    override fun resume() {
        super.resume()
        music!!.play()
    }
}