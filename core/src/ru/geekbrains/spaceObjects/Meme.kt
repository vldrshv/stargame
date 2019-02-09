package ru.geekbrains.spaceObjects

import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.Sprite
import ru.geekbrains.base.Point
import kotlin.math.cos
import kotlin.math.sin

class Meme(x: Double = 0.0, y: Double = 0.0, sprite: Sprite) {

    // TODO:  добавить функцию случайного распределения

    var position = Point()
    var outfit = sprite//textureAtlas.createSprite("meme-2018-12-${(Math.random() * 10).toInt() % 3 + 1}")//"asteroid")
    var width = 10
    var height = 10
    var damage = 200
    var health = 20
    var speed = Math.random() * 10 % 2

    fun move(_point: Point) = move(_point.x, _point.y)
    private fun move(screenWidth: Double, screenHeight: Double) {
        if (!this.isOutOfScreen())
            this.position.y -= this.speed
        else
            this.resetMeme(screenWidth, screenHeight)
    }

    private fun isOutOfScreen(): Boolean {
        return position.y < -height.toDouble()
    }

    /**
     * @param nyanCat - the object, which can damage compared object.
     */
    fun wasDamaged(bullet: Bullet): Boolean {
        if (bullet.position.y in (position.y..this.position.y + this.height) ||
                this.position.y in (bullet.position.y..bullet.position.y + bullet.height)) {
            if (bullet.position.x in (this.position.x..this.position.x + this.width))
                return true
            if (this.position.x in (bullet.position.x..bullet.position.x + bullet.width))
                return true
        }
        return false
    }

    fun render(batch: Batch) {
        outfit.setPosition(position.x.toFloat(), position.y.toFloat())
        outfit.setSize(width.toFloat(), height.toFloat())
        outfit.draw(batch)
    }

    fun resize(screenWidth: Double, screenHeight: Double) {
        resize(screenWidth, screenHeight, 5, 10)
    }

    private fun resize(screenWidth: Double, screenHeight: Double, width: Int, height: Int) {
        this.height = (screenHeight / 100 * height).toInt()
        this.width = (screenWidth / 100 * width).toInt()
    }

    fun resetMeme(screenWidth: Double, screenHeight: Double) {
        this.position = getMemeStartPosition(screenWidth, screenHeight)
    }

    fun getMemeStartPosition(screenWidth: Double, screenHeight: Double): Point {
        // TODO:  продумать функцию распределения
        val y = (Math.random() * 1000).toInt() % (screenHeight / 2)
        //val funcReturnX = ((screenWidth-100) * sin((Math.PI / screenHeight) * funcY))

//        val funcY = (Math.random() * 1000 / screenWidth)
        val funcReturnX = (sin(y/80) * sin(y/80) + cos(y/10) * cos(y/10)) * (screenHeight - 20)
        return Point(positionGenerator(y) { funcReturnX }, y + screenHeight.toInt())
    }

    private fun positionGenerator(value: Double, generate: (Double) -> Double): Double {
        return generate(value)
    }
    
    override fun toString(): String {
        return "Meme(speed=$speed)"
    }
    
    
}