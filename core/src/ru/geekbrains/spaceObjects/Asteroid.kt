package ru.geekbrains.spaceObjects

import com.badlogic.gdx.graphics.g2d.Batch
import ru.geekbrains.base.Point
import ru.geekbrains.base.SpaceObject

class Asteroid (x: Double = 0.0, y: Double = 0.0): SpaceObject(){

    init {
        position = Point(x, y)
        outfit = textureAtlas.createSprite("asteroid")
        width = 10
        height = 10
        damage = 200
        health = 20
    }

    override fun move(screenWidth: Double, screenHeight: Double) {
        if (!isOutOfScreen(screenWidth, screenHeight)) {
                position.y -= speed
        }
        else
            resetAsteroid(screenWidth, screenHeight)
    }

    override fun isOutOfScreen(screenWidth: Double, screenHeight: Double) : Boolean{
        return position.y < -height.toDouble()
    }
    fun resetAsteroid(screenWidth: Double, screenHeight: Double) {
        this.position = Point(Math.random() * 1024 % screenWidth,
                screenHeight + Math.random() * 100 % screenHeight)
    }

    override fun render(batch: Batch) {
        batch.begin()
        outfit.setPosition(position.x.toFloat(), position.y.toFloat())
        outfit.setSize(width.toFloat(), height.toFloat())
        outfit.draw(batch)
        batch.end()
    }
    override fun upgrade() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun resize(screenWidth: Double, screenHeight: Double)
            = super.resize(screenWidth, screenHeight, 5, 10)



}