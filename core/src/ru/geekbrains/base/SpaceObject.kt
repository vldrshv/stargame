package ru.geekbrains.base

import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.TextureAtlas

abstract class SpaceObject (
        var position: Point = Point(),
        var damage: Int = 50,
        var health: Int = 200,
        var speed: Double = 5.0,
        var height: Int = 10,
        var width: Int = 15,
        var movingVector: Vector = Vector(),
        var level: Int = 1
) {

//    protected var textureAtlas: TextureAtlas = TextureAtlas("spaceship_sprites.txt")
    protected var textureAtlas: TextureAtlas = TextureAtlas("meme_spaceship_sprite.txt")
    open lateinit var outfit: Sprite

    fun move(_dest: Point) {
        if(!this.position.equal(_dest))
            move(_dest.x, _dest.y)
    }

    protected open fun move(x: Double, y: Double) {
        if (position.x == x) {
            position.x += x
        } else {
            position.x += 0
        }
        if (position.y == y) {
            position.y += y
        } else {
            position.y += 0
        }
    }

    open fun isOutOfScreen(screenWidth: Double, screenHeight: Double): Boolean {
        return position.x - width > screenWidth || position.y - height > screenHeight
    }

    /**
     * @param _spaceObject - the object, which can damage compared object.
     */
    fun wasDamaged(_spaceObject: SpaceObject) : Boolean {
        if (_spaceObject.position.y in (this.position.y .. this.position.y + this.height) ||
                this.position.y in (_spaceObject.position.y .. _spaceObject.position.y + _spaceObject.height)) {
            if (_spaceObject.position.x in (this.position.x..this.position.x + this.width))
                return true
            if (this.position.x in (_spaceObject.position.x.._spaceObject.position.x + _spaceObject.width))
                return true
        }
        return false
    }
    abstract fun upgrade()
    override fun toString(): String {
        return "SpaceObject(\n" +
                "\tposition=$position,\n" +
                "\tdamage=$damage,\n" +
                "\thealth=$health,\n" +
                "\tspeed=$speed,\n" +
                "\theight=$height,\n" +
                "\twidth=$width,\n" +
                "\tlevel=$level\n" +
                ");"
    }
    abstract fun render(batch: Batch)
    fun dispose(){
        textureAtlas.dispose()
    }
    protected open fun resize(screenWidth: Double, screenHeight: Double, width:Int, height: Int){
        this.height = (screenHeight / 100 * height).toInt()
        this.width = (screenWidth / 100 * width).toInt()
    }

}