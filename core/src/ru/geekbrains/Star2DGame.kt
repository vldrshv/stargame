package ru.geekbrains

import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.Screen
import com.badlogic.gdx.audio.Music
import ru.geekbrains.base.Base2DScreen
import ru.geekbrains.screen.GameScreen

import ru.geekbrains.screen.MenuScreen

class Star2DGame : Game() {

    var music: Music? = null
    var musicCounter: Int = 1

    override fun create() {
        setScreen(MenuScreen(this))
        music = Gdx.audio.newMusic(Gdx.files.internal("menu1.mp3"))
        music?.play()
    }

    override fun render() {
        super.render()



        if (getScreen() is GameScreen) {
            music?.pause()
        }
        if (getScreen() is MenuScreen) {
            if (!music!!.isPlaying){
                if (musicCounter == 1) {
                    music = Gdx.audio.newMusic(Gdx.files.internal("menu2.mp3"))
                    println("menu2")
                    musicCounter = 2
                } else if (musicCounter == 2) {
                    music = Gdx.audio.newMusic(Gdx.files.internal("menu1.mp3"))
                    println("menu1")
                    musicCounter = 1
                }
            }
            music?.play()
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE) || Gdx.input.isKeyJustPressed(Input.Keys.BACK)) {
            pause()

            // TODO Save progress before pause to local instance
            setScreen(MenuScreen(this))
            return
        }


        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            resume()
            // TODO Upload saved progress from local instance
            setScreen(GameScreen())
            return
        }


    }

    override fun dispose() {
        music?.dispose()
        super.dispose()
    }

}
