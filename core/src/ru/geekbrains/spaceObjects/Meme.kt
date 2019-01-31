package ru.geekbrains.spaceObjects

import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.Sprite
import ru.geekbrains.base.Point
import ru.geekbrains.base.SpaceObject
import kotlin.math.asin

class Meme (x: Double = 0.0, y: Double = 0.0, sprite: Sprite): SpaceObject(){

    // TODO:  добавить функцию случайного распределения

    init {
        position = Point((asin(Math.random()) * 1000).toInt() % x-50, (Math.sin(Math.random()) * 1000).toInt() % y )
        outfit = sprite//textureAtlas.createSprite("meme-2018-12-${(Math.random() * 10).toInt() % 3 + 1}")//"asteroid")
        width = 10
        height = 10
        damage = 200
        health = 20
        speed = 2.0
    }

    override fun move(screenWidth: Double, screenHeight: Double) {
        if (!isOutOfScreen(screenWidth, screenHeight)) {
                position.y -= speed
        }
        else
            resetMeme(screenWidth, screenHeight)
    }

    override fun isOutOfScreen(screenWidth: Double, screenHeight: Double) : Boolean{
        return position.y < -height.toDouble()
    }
    fun resetMeme(screenWidth: Double, screenHeight: Double) {
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