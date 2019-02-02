package ru.geekbrains.base

import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import ru.geekbrains.spaceObjects.Award
import ru.geekbrains.spaceObjects.Meme
import kotlin.math.sin

class MemeFactory(var screenWidth: Double, var screenHeight: Double) {
    // TODO:  добавить атлас мемов
    // TODO:  добавить позицию Awards
    // TODO:  добавить апдейт по спрайтам мемов

    private var currentYear: Int = 2018
    private var currentMonth: Int = 12
    private var memeCounter: Int = 5
    private var awardCounter: Int = 1
    private var AWARD_QUANTITY: Int = 6

    private var textureMemeAtlas: TextureAtlas = TextureAtlas("meme_sprite.txt")
    private var textureAwardAtlas: TextureAtlas = TextureAtlas("awardmeme_sprite.txt")
//    private var textureAwardAtlas: TextureAtlas = TextureAtlas("award_sprite.txt")

    fun getMeme() : Meme {
        val memeValue: Int = (Math.random() * memeCounter).toInt() % memeCounter + 1
        val meme: Meme =  Meme(
                screenWidth,
                screenHeight,
                textureMemeAtlas.createSprite("meme-$currentYear-$currentMonth-$memeValue"))


        return meme
    }
    
    fun getAward() : Award {
        if (awardCounter > AWARD_QUANTITY)
            awardCounter = 1
    
        println("award_meme-$awardCounter")
        val award: Award = Award(textureAwardAtlas.createSprite("award_meme-$awardCounter"))//, this)
        awardCounter ++
        return award
    }

}