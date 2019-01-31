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

class GameScreen : Base2DScreen() {

    private var wallpaperImg: Texture

    private var dest: Point
    private var ship: SpaceShip
    private var memeList: MutableList<Meme>

    private var ASTEROID_QUANTITY = 7

    private var music: Music? = null

    private var memeFactory: MemeFactory
//
    private var withoutDamage: Int = 0
    private var withoutDamageRender: Boolean = false
    private var awardMemeTime: Int = 120

    var textureAtlas: TextureAtlas = TextureAtlas("meme_spaceship_sprite.txt")
//    var awardMeme: Sprite = textureAtlas.createSprite("award_meme")
//

    init {
        super.show()
        wallpaperImg = Texture("space.jpg")
        ship = SpaceShip((SCREEN_WIDTH / 2).toDouble(), 0.0)
        memeList = ArrayList()

        dest = Point(ship.position)
        ship.width = SCREEN_WIDTH / 100 * 10
        ship.height = SCREEN_HEIGHT / 100 * 15

        music = Gdx.audio.newMusic(Gdx.files.internal("shooting_stars.mp3"))
        music!!.isLooping = true
        music!!.play()

        memeFactory = MemeFactory(SCREEN_WIDTH.toDouble(), SCREEN_WIDTH.toDouble())
        initAsteroids()
    }

    private fun initAsteroids() {
        for (i in 0 until ASTEROID_QUANTITY) {
            val asteroid = memeFactory.getMeme()//Meme()
//            asteroid.width = SCREEN_WIDTH / 100 * 5
//            asteroid.height = SCREEN_HEIGHT / 100 * 10
//            asteroid.position = Point(
//                    Math.random() * 1024 % SCREEN_WIDTH,
//                    SCREEN_HEIGHT + Math.random() * 1000 % SCREEN_HEIGHT, 0.0)
            memeList.add(i, asteroid)
        }
    }

    override fun render(delta: Float) {
        super.render(delta)
        Gdx.gl.glClearColor(0.5f, 0.2f, 0.3f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        drawGameObjects()
        ship.move(SCREEN_WIDTH.toDouble())
        moveSpaceShipBullets()
        ship.fire()
        moveAsteroids()

        checkDamage()
    }

    private fun checkDamage() {
        // 1 TODO: 28.01.2019 Проверить урон, который был нанесен астероиду
        // 2 TODO: 28.01.2019 Удалить астероид при столкновении с кораблем
        // 3 TODO: 28.01.2019 Проверить урон, нанесенный кораблю
        // 4 TODO: 28.01.2019 Проверить урон, нанесенный кораблям врага
        for (asteroid in memeList) {
            if (ship.wasDamaged(asteroid)) {
                withoutDamage = 0
                ship.health = ship.health - asteroid.damage // ship damage (2)
                asteroid.resetMeme(SCREEN_WIDTH.toDouble(), SCREEN_HEIGHT.toDouble())
            }
            // check ship health
            if (ship.health <= 0) {
                ship.downgrade()
            }
            for (bullet in ship.bulletList) {
                if (asteroid.wasDamaged(bullet)) {
                    asteroid.health = asteroid.health - bullet.damage
                    bullet.resetBullet()
                    // check asteroid health
                    if (asteroid.health <= 0) {
                        asteroid.resetMeme(SCREEN_WIDTH.toDouble(), SCREEN_HEIGHT.toDouble())
                        ship.addExperience()
                    }
                }
            }
        }

        checkAward()

    }

    private fun checkAward() {
        withoutDamage ++
        if (withoutDamageRender)
            awardMemeTime--
        if (awardMemeTime == 0){
            withoutDamageRender = false
            withoutDamage = 0
            awardMemeTime = 120
        }
        when (withoutDamage){
            1000 -> {
                withoutDamageRender = true
                withoutDamage = 0
            }
        }
    }

    private fun moveSpaceShipBullets() {
        for (b in ship.bulletList) {
            b.move(Point(SCREEN_WIDTH, SCREEN_HEIGHT, 0))
        }
    }

    private fun moveAsteroids() {
        for (asteroid in memeList) {
            asteroid.move(Point(SCREEN_WIDTH, SCREEN_HEIGHT, 0))
        }
    }

    private fun drawGameObjects() {
        batch.begin()
        batch.draw(wallpaperImg, 0f, 0f, SCREEN_WIDTH.toFloat(), SCREEN_HEIGHT.toFloat())
        batch.end()
        ship.render(batch)
        for (asteroid in memeList) {
            asteroid.render(batch)
        }

        for (bullet in ship.bulletList) {
            bullet.render(batch)
        }

        if (withoutDamageRender) {
            batch.begin()
//            awardMeme.setSize(100f, 100f)
//            awardMeme.setPosition(0f, 0f)
//            awardMeme.draw(batch)
            batch.end()
        }
    }

    override fun resize(width: Int, height: Int) {
        super.resize(width, height)
        ship.resize(SCREEN_WIDTH.toDouble(), SCREEN_HEIGHT.toDouble())
        for (b in ship.bulletList)
            b.resize(SCREEN_WIDTH.toDouble(), SCREEN_HEIGHT.toDouble())
        for (a in memeList)
            a.resize(SCREEN_WIDTH.toDouble(), SCREEN_HEIGHT.toDouble())
    }

    override fun dispose() {
        wallpaperImg.dispose()
        batch.dispose()
        ship.dispose()
        for (b in ship.bulletList)
            b.dispose()
        for (a in memeList)
            a.dispose()

        super.dispose()
    }

    override fun touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        dest = Point(screenX, SCREEN_HEIGHT - screenY, 0)
        ship.updateMovingDirection(screenX, SCREEN_WIDTH, false)
        return super.touchDown(dest, pointer)
    }

    override fun touchUp(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        ship.updateMovingDirection(screenX, SCREEN_WIDTH, true)
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