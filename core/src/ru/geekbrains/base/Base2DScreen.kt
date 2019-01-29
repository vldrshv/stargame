package ru.geekbrains.base

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.InputProcessor
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.utils.viewport.ExtendViewport

open class Base2DScreen : Screen, InputProcessor {

    protected var batch: SpriteBatch
    private var camera: OrthographicCamera? = null
    private var viewport: ExtendViewport

    var SCREEN_HEIGHT: Int
    var SCREEN_WIDTH: Int

    init {
        Gdx.input.inputProcessor = this
        SCREEN_HEIGHT = Gdx.graphics.height
        SCREEN_WIDTH = Gdx.graphics.width
        batch = SpriteBatch()
        camera = OrthographicCamera()
        viewport = ExtendViewport(SCREEN_WIDTH.toFloat(), SCREEN_HEIGHT.toFloat(), camera)
    }

    override fun show() {
        println("show")
    }

    override fun render(delta: Float) {
        //        System.out.println("render");
    }

    override fun resize(width: Int, height: Int) {
        println("resize: h = $height w = $width")
        SCREEN_HEIGHT = height
        SCREEN_WIDTH = width

        viewport.update(width, height, true)
        batch.projectionMatrix = camera!!.combined
    }

    override fun pause() {
        println("pause")
    }

    override fun resume() {
        println("resume")
    }

    override fun hide() {
        println("hide")
        dispose()
    }

    override fun dispose() {
        println("dispose")
    }


    override fun keyDown(keycode: Int): Boolean {
        println("keyDown keycode = $keycode")
        return false
    }

    override fun keyUp(keycode: Int): Boolean {
        println("keyUp keycode = $keycode")
        return false
    }

    override fun keyTyped(character: Char): Boolean {
        println("keyTyped character = $character")
        return false
    }

    override fun touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        //System.out.println("touchDown screenX = " + screenX + " screenY = " + screenY);
        return false
    }

    fun touchDown(touch: Point, pointer: Int): Boolean {
        //        System.out.println("touchDown touch.x = " + touch.getX() + " touch.y = " + touch.getY());
        return false
    }

    override fun touchUp(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        //        System.out.println("touchUp screenX = " + screenX + " screenY = " + screenY);
        return false
    }

    fun touchUp(touch: Vector, pointer: Int): Boolean {
        //        System.out.println("touchUp touch.x = " + touch.getX() + " touch.y = " + touch.getY());
        return false
    }

    override fun touchDragged(screenX: Int, screenY: Int, pointer: Int): Boolean {
        //        System.out.println("touchDragged screenX = " + screenX + " screenY = " + screenY);
        //        touch.set(screenX, screenBounds.getHeight() - screenY).mul(screenToWorlds);
        //        touchDragged(touch, pointer);
        return false
    }

    //    public boolean touchDragged(Vector2 touch, int pointer) {
    //        System.out.println("touchDragged touch.x = " + touch.x + " touch.y = " + touch.y);
    //        return false;
    //    }

    override fun mouseMoved(screenX: Int, screenY: Int): Boolean {
        return false
    }

    override fun scrolled(amount: Int): Boolean {
        return false
    }
}
