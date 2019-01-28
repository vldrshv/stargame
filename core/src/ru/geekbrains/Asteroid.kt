package ru.geekbrains

class Asteroid (x: Double = 0.0, y: Double = 0.0): SpaceObject(){

    init {
        position = Point(x, y)
        outfit = "asteroid.png"
        width = 10
        height = 10
        damage = 200
    }

    override fun move(screenWidth: Double, screenHeight: Double) {
        if (!isOutOfScreen(screenWidth, screenHeight)) {
            if (!position.equal(Point(screenWidth, screenHeight + height)))
                position.y -= speed
        }
        else
            resetAsteroid(screenWidth, screenHeight)
    }

    fun isOutOfScreen(screenWidth: Double, screenHeight: Double) : Boolean{
        return position.y < -height.toDouble()
    }
    fun resetAsteroid(screenWidth: Double, screenHeight: Double) {
        this.position = Point(Math.random() * 1024 % screenWidth,
                screenHeight + Math.random() * 100 % screenHeight)
    }

    override fun upgrade() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }



}