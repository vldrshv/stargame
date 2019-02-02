package ru.geekbrains.desktop

import com.badlogic.gdx.backends.lwjgl.LwjglApplication
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration

import ru.geekbrains.Star2DGame

object DesktopLauncher {
    @JvmStatic
    fun main(arg: Array<String>) {
        val config = LwjglApplicationConfiguration()
        config.title = "StarGame"
        config.width = 800
        config.height = 400
        LwjglApplication(Star2DGame(), config)



    }
}
