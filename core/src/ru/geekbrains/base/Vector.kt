package ru.geekbrains.base

import kotlin.math.acos
import kotlin.math.cos
import kotlin.math.sin

open class Vector : Point {

    constructor(_point : Point) : super (_point.x, _point.y, _point.z)
    constructor(x : Double, y : Double, z : Double = 0.0) : super(x, y, z)
    constructor() : super()

    fun add(_vector : Vector) : Vector = add(_vector.x, _vector.y, _vector.z)
    fun add(_x: Double, _y: Double, _z: Double) : Vector = Vector(this.x + _x, this.y + _y, this.z + _z)

    fun sub(_vector : Vector) : Vector = sub(_vector.x, _vector.y, _vector.z)
    fun sub(_x: Double, _y: Double, _z: Double) : Vector = Vector(this.x - _x, this.y - _y, this.z - z)

    fun distance(_vector: Vector) : Double = distance(_vector.x, _vector.y, _vector.z)
    fun distance (_x: Double, _y: Double, _z: Double) : Double {
        var v : Vector = Vector(_x, _y, _z)
        v = this.sub(v)
        return v.length()
    }

    fun scal(_vector: Vector) : Double = scal(_vector.x, _vector.y, _vector.z)
    fun scal(_x: Double, _y: Double, _z: Double) : Double = (this.x * _x + this.y * _y + this.z * _z)


    fun vecMult(_vector : Vector) : Vector = vecMult(_vector.x, _vector.y, _vector.z)
    fun vecMult(_x: Double, _y: Double, _z: Double) : Vector {
        return Vector(
                this.x * _z - this.z * _y,
                this.z * _x - this.x * _z,
                this.x * _y - this.y * _x
        )
    }

    fun getAngle(_vector: Vector) : Double = getAngle(_vector.x, _vector.y)
    fun getAngle(x: Double, y: Double) : Double {
        val ch: Double = this.scal(x, y, 0.0)
        val zn: Double = (this.length() * Vector(x, y, 0.0).length())
        return acos(ch/zn) * 180
    }

    fun rotate(angle: Double): Vector {
        return Vector(
                this.x * cos(angle) - this.y * sin(angle),
                this.x * sin(angle) + this.y * cos(angle)
        )
    }

    fun growVector(coef: Double) : Vector = Vector(coef * this.x, coef * this.y, coef * this.z)

    fun length() : Double = Math.sqrt(this.x * this.x + this.y * this.y + this.z * this.z)

    fun normalize() : Vector {
        val len : Double = length()
        return Vector(this.x / len, this.y / len, this.z / len)
    }

    override fun toString(): String {
        return "Vector(${this.x}, ${this.y})"
    }


}