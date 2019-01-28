package ru.geekbrains

class SpaceShip(x: Double = 0.0, y: Double = 0.0) : SpaceObject() {
    var fireOutfit: String = "fire.png"
    var experience: Int = 0
    var EP: Int = 10
    var UPD_POINTS: Int = 100

    init {
        position = Point(x, y)
        outfit = "${level}spaceship.png"
    }

    override fun move(x: Double, y: Double) {
        val destVector: Vector = Vector(x - position.x, y - position.y, 0.0)
        movingVector = destVector.normalize().growVector(speed)

        val deltaX: Double = Vector(x - position.x, 0.0).length()
        position.x += if (deltaX < movingVector.x) deltaX else movingVector.x

        val deltaY: Double = Vector(0.0, y - position.y, 0.0).length()
        position.y += if (deltaY < movingVector.y) deltaY else movingVector.y
    }

    fun fire() = fireOutfit

    fun addExperience() {
        experience += EP
    }

    fun isUpgrated(): Boolean {
        return checkUpgrade()
    }

    fun checkUpgrade(): Boolean {
        if (experience == UPD_POINTS) {
            UPD_POINTS *= 2
            upgrade()
            return true
        }

        return false
    }

    override fun upgrade() {
        if (level < 5) {
            level++
            speed++
            damage += level * 150
            health += level * 200
        }

        outfit = "${level}spaceship.png"
        println("outfit = $outfit")

    }

    fun downgrade() {
        level = 1
        speed = 5.0
        damage = 50
        health = 200

        outfit = "${level}spaceship.png"
        println("outfit = $outfit")
    }
}