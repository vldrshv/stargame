package ru.geekbrains.spaceObjects

import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import ru.geekbrains.base.Point
import java.io.Serializable

class SpaceShip(x: Double = 0.0, y: Double = 0.0) : Serializable{
    var bulletList: ArrayList<Bullet>
    var textureAtlas: TextureAtlas = TextureAtlas("meme_spaceship_sprite.txt")
    var position = Point(x, y)
    var health = 200
    var speed: Double = 10.0
    var width: Int = 20
    var height: Int = 10
    var level: Int = 1
    var outfit: Sprite = textureAtlas.createSprite("nyan_cat")
    var screenWidth: Double = 800.0
    var screenHeight: Double = 400.0

    private var experience: Int = 0
    private var EP: Int = 10
    private var UPD_POINTS: Int = 100
    private var BPS: Int = 30 // Bullet Per {value} Second
    private var TIME_COUNTER: Int = 1
    
    private var bulletNum: Int = 0
    private var direction: Direction = Direction.STOP

    enum class Direction { LEFT, RIGHT, STOP }

    init {
        bulletList = ArrayList()
        addBullets()
    }

    constructor(_point: Point) : this(_point.x, _point.y)

    /**
     * @param screenWidth - Value of screen width
     */
    fun move(){
        when (direction) {
            Direction.LEFT -> position.x -= speed
            Direction.RIGHT -> position.x += speed
        }
        if (position.x + width >= screenWidth)
            position.x = screenWidth - width
        if (position.x < 0.0)
            position.x = 0.0
    }
    /**
     *
     * @param x - x_point of touch on screen
     * @param screenWidth - the width of screen
     * @param touchUp - set true if you call when finger was removed from the screen
     */
    fun updateDirection(x: Double, screenWidth: Double, touchUp: Boolean = false) {
        direction = if (!touchUp) {
            if (x > screenWidth / 2)
                Direction.RIGHT
            else
                Direction.LEFT
        } else {
            Direction.STOP
        }
    }
    
    fun render(batch: Batch) {
        outfit.setPosition(this.position.x.toFloat(), this.position.y.toFloat())
        outfit.setSize(this.width.toFloat(), this.height.toFloat())
        batch.begin()
        outfit.draw(batch)
        batch.end()
        for (b: Bullet in bulletList)
            b.render(batch)
    }
    fun checkDamage(memeList: ArrayList<Meme>) : Boolean {
        var wasDamaged: Boolean = false
        for(meme: Meme in memeList){
            if (this.wasDamaged(meme)){
                meme.resetMeme(screenWidth, screenHeight)
                this.health -= meme.damage
            }
            if (health <= 0) {
                downgrade()
                wasDamaged = true
            }
        }
        
        return !wasDamaged
    }
    
    private fun wasDamaged(meme: Meme) : Boolean {
        if (meme.position.y in (position.y..this.position.y + this.height) ||
                this.position.y in (meme.position.y .. meme.position.y + meme.height)) {
            if (meme.position.x in (this.position.x..this.position.x + this.width))
                return true
            if (this.position.x in (meme.position.x .. meme.position.x + meme.width))
                return true
        }
        return false
    }

    private fun addBullets() {
        while (bulletList.size != 30) {
            bulletList.add(Bullet(this))
        }
    }
    
    fun resize(screenWidth: Double, screenHeight: Double) {
        this.screenWidth = screenWidth
        this.screenHeight = screenHeight
        width = (screenWidth / 100 * 10).toInt()
        height = (screenHeight / 100 * 10).toInt()
        for (b: Bullet in bulletList)
            b.resize(screenWidth, screenHeight)
    }

    fun upgrade() {
        if (level < 5) {
            level++
            speed ++
            for (bullet in bulletList)
                bullet.upgrade()
            println("bullet_Damage = ${bulletList.get(0).damage}")
            health += level * 200
            BPS -= 3
        }
    }
    fun downgrade() {
        level = 1
        speed = 10.0
        health = 200
        BPS = 30

    }
    fun fire() {
        checkBulletsReady()
        for (b: Bullet in bulletList)
            b.move(screenHeight)
    }
    private fun checkBulletsReady() {
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

    private fun checkUpgrade(): Boolean {
        if (experience == UPD_POINTS) {
            UPD_POINTS *= 2
            upgrade()
            return true
        }
        return false
    }
    
    override fun toString(): String {
        return "SpaceShip(speed=$speed)"
    }
    
    
}