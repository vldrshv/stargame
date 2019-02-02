package ru.geekbrains.screen

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.audio.Music
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureAtlas

import java.util.ArrayList

import ru.geekbrains.spaceObjects.Meme
import ru.geekbrains.base.Point
import ru.geekbrains.spaceObjects.SpaceShip
import ru.geekbrains.base.Base2DScreen
import ru.geekbrains.base.MemeFactory
import ru.geekbrains.base.MemeManager

class GameScreen : Base2DScreen() {

    private var wallpaperImg: Texture

    private var dest: Point
    private var ship: SpaceShip

    private var music: Music? = null
    
    private var memeManager: MemeManager
//
    private var withoutDamage: Int = 0
    private var withoutDamageRender: Boolean = false
    private var awardMemeTime: Int = 120

    var textureAtlas: TextureAtlas = TextureAtlas("meme_spaceship_sprite.txt")

    init {
        super.show()
        wallpaperImg = Texture("space.jpg")
        ship = SpaceShip((SCREEN_WIDTH / 2).toDouble(), 0.0)

        dest = Point(ship.position)
        ship.width = SCREEN_WIDTH / 100 * 10
        ship.height = SCREEN_HEIGHT / 100 * 15

        music = Gdx.audio.newMusic(Gdx.files.internal("shooting_stars.mp3"))
        music!!.isLooping = true
        music!!.play()

        memeManager = MemeManager()//SCREEN_WIDTH.toDouble(), SCREEN_WIDTH.toDouble())
        memeManager.memeFactory = MemeFactory(SCREEN_WIDTH.toDouble(), SCREEN_WIDTH.toDouble())
        memeManager.generateMemeList()
    }

    override fun render(delta: Float) {
        super.render(delta)
        Gdx.gl.glClearColor(0.5f, 0.2f, 0.3f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        drawGameObjects()

        memeManager.move()
        ship.move()
        
        memeManager.checkDamage(ship)
        ship.checkDamage(memeManager.memeList)
    }
    
    private fun drawGameObjects() {
        batch.begin()
        batch.draw(wallpaperImg, 0f, 0f, SCREEN_WIDTH.toFloat(), SCREEN_HEIGHT.toFloat())
        batch.end()

        memeManager.render(batch)
        ship.render(batch)
        ship.fire()
    }

    override fun resize(width: Int, height: Int) {
        super.resize(width, height)
        ship.resize(SCREEN_WIDTH.toDouble(), SCREEN_HEIGHT.toDouble())
        memeManager.resize(SCREEN_WIDTH.toDouble(), SCREEN_HEIGHT.toDouble())
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