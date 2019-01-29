package ru.geekbrains.spaceObjects

import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import ru.geekbrains.base.Point
import ru.geekbrains.base.SpaceObject
import ru.geekbrains.base.Vector

class SpaceShip(x: Double = 0.0, y: Double = 0.0) : SpaceObject() {
    var bulletList: ArrayList<Bullet>

    private var experience: Int = 0
    private var EP: Int = 10
    private var UPD_POINTS: Int = 100
    private var BPS: Int = 30 // Bullet Per {value} Second
    private var TIME_COUNTER: Int = 1 //

    private var bulletNum: Int = 0

    init {
        position = Point(x, y)
        bulletList = arrayListOf()
        addBullets()

        textureAtlas = TextureAtlas("spaceship_sprites.txt")
        outfit = textureAtlas.createSprite("light_fighter")
    }

    override fun move(x: Double, y: Double) {
        val destVector: Vector = Vector(x - position.x, y - position.y, 0.0)
        movingVector = destVector.normalize().growVector(speed)

        val deltaX: Double = Vector(x - position.x, 0.0).length()
        position.x += if (deltaX < movingVector.x) deltaX else movingVector.x

        val deltaY: Double = Vector(0.0, y - position.y, 0.0).length()
        position.y += if (deltaY < movingVector.y) deltaY else movingVector.y

    }
    override fun upgrade() {
        if (level < 5) {
            level++
            speed++
            for (bullet in bulletList)
                bullet.upgrade()
            println("bullet_Damage = ${bulletList.get(0).damage}")
            health += level * 200
            BPS -= 3
        }
    }
    override fun render(batch: Batch) {
        outfit.setPosition(this.position.x.toFloat(), this.position.y.toFloat())
        outfit.setSize(this.width.toFloat(), this.height.toFloat())
        batch.begin()
        outfit.draw(batch)
        batch.end()
    }


    fun downgrade() {
//        level = 1
//        speed = 5.0
//        damage = 50
//        health = 200
//        BPS = 30
//
//        outfit = "${level}spaceship.png"
//        println("outfit = $outfit")
    }
    fun fire() {
        if (TIME_COUNTER >= BPS) {
            TIME_COUNTER = 0

            this.bulletList.get(bulletNum).canBeShooted = true
            this.bulletList.get(bulletNum).position = Point(
                    this.position.x + this.width / 4,
                    this.position.y + this.height / 2)

            bulletNum ++
            if (bulletNum == 30) bulletNum = 0
            return
        }
        TIME_COUNTER ++

    }
    fun addExperience() {
        experience += EP
        checkUpgrade()
    }
    fun resize(screenWidth: Double, screenHeight: Double)
            = super.resize(screenWidth, screenHeight, 10, 20)


    private fun addBullets() {
        while (bulletList.size != 30) {
            bulletList.add(Bullet(this))
        }
    }
    private fun checkUpgrade(): Boolean {
        if (experience == UPD_POINTS) {
            UPD_POINTS *= 2
            upgrade()
            return true
        }

        return false
    }
}