package ru.geekbrains.base

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.audio.Music
import ru.geekbrains.spaceObjects.SpaceShip
import java.io.Serializable

class GameInstance : Serializable {
    // TODO:  save ship progress
    // TODO:  save music progress
    // TODO:  save XP
    
    var ship: SpaceShip = SpaceShip()
    var musicTrack: String = ""
    var musicBeginningTimeSec: Double = 0.0
}