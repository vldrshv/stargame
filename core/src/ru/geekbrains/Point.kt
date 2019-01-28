package ru.geekbrains

open class Point(var x: Double = 0.0, var y: Double = 0.0, var z: Double = 0.0){

    constructor() : this(0.0, 0.0, 0.0)
    constructor(x: Int = 0, y: Int = 0, z: Int = 0) : this(x.toDouble(), y.toDouble(), z.toDouble())
    constructor(_point: Point) : this (_point.x, _point.y, _point.z)

    fun equal(_point: Point) : Boolean {
        return this.x == _point.x && this.y == _point.y && this.z == _point.z
    }

    override fun toString(): String {
        return "Point($x, $y, $z)"
    }


}