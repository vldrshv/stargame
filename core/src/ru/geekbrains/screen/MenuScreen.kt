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
    var btnLoadGame: Sprite
    var btnLoadGameAnimated: Sprite

    var position: Point

    internal var WAIT = 0

    init {
        super.show()
        textureAtlas = TextureAtlas("buttons_sprites.txt")

        position = Point()
        
        btnNewGame = textureAtlas.createSprite("button_newGame")
        btnNewGame.setPosition(0f, (2 * SCREEN_HEIGHT / 3).toFloat())

        btnNewGameAnimated = textureAtlas.createSprite("button_newGameAnimated")
        btnNewGameAnimated.setPosition(0f, (2 * SCREEN_HEIGHT / 3).toFloat())

        btnExitGame = textureAtlas.createSprite("button_exitGame")
        btnExitGame.setPosition(0f, 0f)

        btnExitGameAnimated = textureAtlas.createSprite("button_exitGameAnimated")
        btnExitGameAnimated.setPosition(0f, 0f)
        
        btnLoadGame = textureAtlas.createSprite("button_loadGame")
        btnLoadGame.setPosition(0f, (SCREEN_HEIGHT / 3).toFloat())
    
        btnLoadGameAnimated = textureAtlas.createSprite("button_loadGameAnimated")
        btnLoadGameAnimated.setPosition(0f, (SCREEN_HEIGHT / 3).toFloat())
        
        resize(SCREEN_WIDTH, SCREEN_HEIGHT)
    }
    
    override fun dispose() {
        textureAtlas.dispose()
    }

    override fun render(delta: Float) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 0f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        
        batch.begin()
    
        renderNewGame()
        renderLoadGame()
        renderExitGame()
        
        batch.end()
    }
    
    private fun renderNewGame(){
        if (position.y > 2 * SCREEN_HEIGHT / 3 && position.y < SCREEN_HEIGHT) {
            btnNewGameAnimated.draw(batch)
            if (_wait()) {
                val instanceFile = Gdx.files.local("\\gameInstance.ser")
                if(instanceFile.exists()) {
                    println("INSTANCE DELETED")
                    instanceFile.delete()
                }
                game.screen = GameScreen()
            }
        } else
            btnNewGame.draw(batch)
    }
    private fun renderLoadGame() {
        if (position.y > SCREEN_HEIGHT / 3 && position.y < 2 * SCREEN_HEIGHT / 3) {
            btnLoadGameAnimated.draw(batch)
            // TODO:  check saved GameInstance
            if (_wait())
                game.screen = GameScreen()
        } else
            btnLoadGame.draw(batch)
    }
    private fun renderExitGame() {
        if (position.y > 0 && position.y < SCREEN_HEIGHT / 3) {
            btnExitGameAnimated.draw(batch)
            if (_wait())
                Gdx.app.exit()
        } else
            btnExitGame.draw(batch)
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
    
    override fun resize(width: Int, height: Int) {
        btnNewGame.setSize((SCREEN_WIDTH - 10).toFloat(), (SCREEN_HEIGHT / 100 * 30).toFloat())
        btnNewGameAnimated.setSize((SCREEN_WIDTH - 10).toFloat(), (SCREEN_HEIGHT / 100 * 30).toFloat())
        btnExitGame.setSize((SCREEN_WIDTH - 10).toFloat(), (SCREEN_HEIGHT / 100 * 30).toFloat())
        btnExitGameAnimated.setSize((SCREEN_WIDTH - 10).toFloat(), (SCREEN_HEIGHT / 100 * 30).toFloat())
        btnLoadGame.setSize((SCREEN_WIDTH - 10).toFloat(), (SCREEN_HEIGHT / 100 * 30).toFloat())
        btnLoadGameAnimated.setSize((SCREEN_WIDTH - 10).toFloat(), (SCREEN_HEIGHT / 100 * 30).toFloat())
        
        super.resize(width, height)
    }
}