package ru.geekbrains.spaceObjects

import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.Sprite
import ru.geekbrains.base.Point
import java.io.Serializable

class Bullet(obj: SpaceShip) : Serializable { //SpaceObject() {

    var canBeShooted: Boolean = false

    var position: Point = Point(400, 0)
    var damage: Int = 10
    var speed: Double = 5.0//obj.speed
    var height: Int = 3//obj.height
    var width: Int = 3//obj.width
    var level: Int = 1
    @Transient
    var outfit: Sprite = obj.textureAtlas.createSprite("rainbow")

    fun move(screenHeight: Double)  {
        if (this.isOutOfScreen(screenHeight)){
            resetBullet()
        } else if (this.canBeShooted)
            this.position.y += speed
    }

    fun resetBullet() {
        this.position = Point(this.position)
        this.canBeShooted = false
    }

    fun upgrade() {
        damage += 10
        level ++
    }

    fun render(batch: Batch) {
        if (this.canBeShooted) {
            batch.begin()
            outfit.setSize(this.width.toFloat(), this.height.toFloat())
            outfit.setPosition(this.position.x.toFloat(), this.position.y.toFloat())
            outfit.draw(batch)
            batch.end()
        }
    }

    fun resize(screenWidth: Double, screenHeight: Double)
            = resize(screenWidth, screenHeight, 3, 9)

    private fun resize(screenWidth: Double, screenHeight: Double, coefWidth: Int, coefHeight: Int){
        this.width = (screenWidth / 100 * coefWidth).toInt()
        this.height = (screenHeight / 100 * coefHeight).toInt()
    }

    private fun isOutOfScreen(screenHeight: Double): Boolean {
        return position.y - height > screenHeight
    }
    
    fun restore(spaceShip: SpaceShip){
        outfit = spaceShip.textureAtlas.createSprite("rainbow")
        //bulletList = ArrayList()
        //addBullets()
    }
    
    override fun toString(): String {
        return "Bullet(damage=$damage)"
    }
    
    
}