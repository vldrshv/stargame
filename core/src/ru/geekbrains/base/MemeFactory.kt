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

    private var textureMemeAtlas: TextureAtlas = TextureAtlas("meme_sprite.txt")
//    private var textureAwardAtlas: TextureAtlas = TextureAtlas("award_sprite.txt")

    fun getMeme() : Meme {
        val memeValue: Int = (Math.random() * memeCounter).toInt() % memeCounter + 1
        val meme: Meme =  Meme(
                screenWidth,
                screenHeight,
                textureMemeAtlas.createSprite("meme-$currentYear-$currentMonth-$memeValue"))


        return meme
    }

}