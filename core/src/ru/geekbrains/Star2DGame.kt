package ru.geekbrains

import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input

import ru.geekbrains.screen.MenuScreen

class Star2DGame : Game() {

    override fun create() {
        setScreen(MenuScreen(this))
    }

    override fun render() {
        super.render()
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            pause()
            setScreen(MenuScreen(this))
        }
    }

//    override fun resize(width: Int, height: Int) {
//        super.resize(width, height)
//    }

}
