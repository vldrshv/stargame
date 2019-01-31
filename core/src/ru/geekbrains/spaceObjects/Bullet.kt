package ru.geekbrains.spaceObjects

import com.badlogic.gdx.graphics.g2d.Batch
import ru.geekbrains.base.Point
import ru.geekbrains.base.SpaceObject

class Bullet(obj: SpaceObject) : SpaceObject() {

    var belongsTo : BelongsTo = BelongsTo.ENEMY
    var owner: SpaceObject = obj
    var canBeShooted: Boolean = false


    init {
        if (obj is SpaceShip)
            belongsTo = BelongsTo.SPACE_SHIP

        position = Point(obj.position.x, obj.position.y)
        damage = 10
        health = 10
        speed = obj.speed
        height = obj.height
        width = obj.width
        level = 1
//        outfit = textureAtlas.createSprite("fire")
        outfit = textureAtlas.createSprite("rainbow")
    }

    private fun getSign(): Int {
        return if (belongsTo == BelongsTo.SPACE_SHIP) 1 else -1
    }

    override fun move(x: Double, y: Double) {
        if (this.isOutOfScreen(x, y)){
            resetBullet()
        } else if (this.canBeShooted)
            this.position.y += getSign()*speed
    }

    fun resetBullet() {
        this.position = Point(owner.position)
        this.canBeShooted = false
    }

    override fun upgrade() {
        damage += 10
        level ++
    }

    override fun render(batch: Batch) {
        if (this.canBeShooted) {
            batch.begin()
            outfit.setSize(this.width.toFloat(), this.height.toFloat())
            outfit.setPosition(this.position.x.toFloat(), this.position.y.toFloat())
            outfit.draw(batch)
            batch.end()
        }
    }

    fun resize(screenWidth: Double, screenHeight: Double)
            = super.resize(screenWidth, screenHeight, 5, 15)//5)

    enum class BelongsTo { ENEMY, SPACE_SHIP }

}