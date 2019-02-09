package ru.geekbrains.base

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.audio.Music
import ru.geekbrains.spaceObjects.SpaceShip
import java.io.Serializable

class GameInstance : Serializable {
    var ship: SpaceShip = SpaceShip()
    var musicTrack: String = ""
    var musicBeginningTimeSec: Double = 0.0
}