package ru.geekbrains.base

import com.badlogic.gdx.graphics.g2d.Batch
import ru.geekbrains.spaceObjects.Award
import ru.geekbrains.spaceObjects.Bullet
import ru.geekbrains.spaceObjects.Meme
import ru.geekbrains.spaceObjects.SpaceShip

class MemeManager {

    private var screenWidth: Double = 800.0
    var screenHeight: Double = 400.0

    var MEME_QUANTITY = 15
    var memeFactory: MemeFactory = MemeFactory(screenWidth, screenHeight)

    var memeList: ArrayList<Meme> = ArrayList()
    private var withoutDamage: Int = 0
    lateinit var award: Award

    fun generateMemeList() {
        for (i: Int in 0 until  MEME_QUANTITY) {
            var meme: Meme = memeFactory.getMeme()
            meme.position = meme.getMemeStartPosition(screenWidth, screenHeight)
            println(meme)
            meme.resize(screenWidth, screenHeight)
            memeList.add(meme)
        }
    }

    fun move () {
        for (meme: Meme in memeList) {
            meme.move(Point (screenWidth, screenHeight))
        }
    }

    /**
      * @param _spaceObject - the object, which can damage compared object.
    */
    fun checkDamage(nyanCat: SpaceShip) {
        for (bullet: Bullet in nyanCat.bulletList) {
            for (meme: Meme in memeList) {
                if (meme.wasDamaged(bullet)) {
                    nyanCat.addExperience()
                    bullet.resetBullet()
                    meme.resetMeme(screenWidth, screenHeight)
                }
            }
        }
    }
    
    fun checkAward(updateWithoutDamageCounter: Boolean, batch: Batch){
        if(award == null)
            return
        if (updateWithoutDamageCounter)
            withoutDamage++
        else
            withoutDamage = 0
        award.checkAward(withoutDamage)
    
        award.render(batch)
        if (award.wasShown) {
            award = this.memeFactory.getAward()
            award.resize(screenWidth.toInt(), screenHeight.toInt())
            withoutDamage = 0
        }
    }

    fun render(batch: Batch) {
        batch.begin()
        for (meme: Meme in memeList) {
            meme.render(batch)
        }
        batch.end()
    }

    fun resize(screenWidth: Double, screenHeight: Double) {
        this.screenWidth = screenWidth
        this.screenHeight = screenHeight
        for (meme: Meme in memeList)
            meme.resize(screenWidth, screenHeight)
    }
}