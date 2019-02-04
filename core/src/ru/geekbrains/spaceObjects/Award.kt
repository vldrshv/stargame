package ru.geekbrains.spaceObjects

import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.Sprite
import ru.geekbrains.base.MemeFactory
import ru.geekbrains.base.Point

class Award {
    
    var outfit:Sprite
//    var memeFactory: MemeFactory
    var position: Point = Point()
    var width: Int = 70
    var height: Int = 70
    
    private var showingTimerCounter: Int = 200
    private var canBeRendered: Boolean = false
    public var wasShown: Boolean = false
    
    constructor(outfit: Sprite) {//, memeFactory: MemeFactory){
        this.outfit = outfit
        outfit.setPosition(0f, 0f)
        outfit.setSize(width.toFloat(), height.toFloat())
//        this.memeFactory = memeFactory
    }
    
    fun render(batch: Batch){
//        println("canBerendered = $canBeRendered")
        if (canBeRendered) {
            if (showingTimerCounter != 0) {
                wasShown = false
                batch.begin()
                outfit.draw(batch)
                batch.end()
                showingTimerCounter --
            } else {
                showingTimerCounter = 200
                canBeRendered = false
                wasShown = true
            }
        }
    }
    
    fun resize(screenWidth: Int, screenHeight: Int){
        this.width = screenWidth / 100 * 20
        this.height = screenHeight / 100 * 30
        outfit.setSize(width.toFloat(), height.toFloat())
    }
    
    fun checkAward(withoutDamageSec: Int) : Boolean {
        if (withoutDamageSec == 1000)
            canBeRendered = true
        
        return canBeRendered
    }
    
    fun canAwardBeUpdatet() = canBeRendered
}