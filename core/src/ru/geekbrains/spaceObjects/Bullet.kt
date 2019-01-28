package ru.geekbrains.spaceObjects

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
        speed = obj.speed + 10
        height = 20
        width = 50
        level = 1
        outfit = "fire.png"
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
        //speed += 10
        level ++
    }

    enum class BelongsTo { ENEMY, SPACE_SHIP }

}