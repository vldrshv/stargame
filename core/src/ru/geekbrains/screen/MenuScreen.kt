package ru.geekbrains.screen

import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.TextureAtlas

import ru.geekbrains.base.Point
import ru.geekbrains.base.Base2DScreen

class MenuScreen(var game: Game) : Base2DScreen() {

    var textureAtlas: TextureAtlas

    var btnNewGame: Sprite
    var btnExitGame: Sprite
    var btnNewGameAnimated: Sprite
    var btnExitGameAnimated: Sprite

    var position: Point

    internal var WAIT = 0

    init {
        super.show()
        textureAtlas = TextureAtlas("buttons_sprites.txt")

        position = Point()
        btnNewGame = textureAtlas.createSprite("button_newGame")
        btnNewGame.setPosition(0f, (SCREEN_HEIGHT / 2).toFloat())

        btnNewGameAnimated = textureAtlas.createSprite("button_newGameAnimated")
        btnNewGameAnimated.setPosition(0f, (SCREEN_HEIGHT / 2).toFloat())

        btnExitGame = textureAtlas.createSprite("button_exitGame")
        btnExitGame.setPosition(0f, 0f)

        btnExitGameAnimated = textureAtlas.createSprite("button_exitGameAnimated")
        btnExitGameAnimated.setPosition(0f, 0f)
    }

//    override fun show() {
//        super.show()
//
//
//    }

    override fun dispose() {
        textureAtlas.dispose()
    }

    override fun render(delta: Float) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 0f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        batch.begin()

        if (position.y > 0 && position.y < SCREEN_HEIGHT / 2) {
            btnExitGameAnimated.draw(batch)
            if (_wait())
                Gdx.app.exit()
        } else
            btnExitGame.draw(batch)
        if (position.y > SCREEN_HEIGHT / 2 && position.y < SCREEN_HEIGHT) {
            btnNewGameAnimated.draw(batch)
            if (_wait())
                game.screen = GameScreen()
        } else
            btnNewGame.draw(batch)
        batch.end()
    }

    private fun _wait(): Boolean {
        if (WAIT != 20)
            WAIT++
        else {
            WAIT = 0
            return true
        }
        println(WAIT)
        return false
    }

    override fun touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        position = Point(screenX, SCREEN_HEIGHT - screenY, 0)
        println(position)
        return super.touchDown(position, pointer)
    }
}