package ru.geekbrains.base;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

public class Base2DScreen implements Screen, InputProcessor {

    protected SpriteBatch batch;
    private OrthographicCamera camera;
    ExtendViewport viewport;

    public int SCREEN_HEIGHT;
    public int SCREEN_WIDTH;

    @Override
    public void show() {
        System.out.println("show");
        Gdx.input.setInputProcessor(this);
        SCREEN_HEIGHT = Gdx.graphics.getHeight();
        SCREEN_WIDTH = Gdx.graphics.getWidth();

        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        viewport = new ExtendViewport(SCREEN_WIDTH, SCREEN_HEIGHT, camera);
    }

    @Override
    public void render(float delta) {
//        System.out.println("render");
    }

    @Override
    public void resize(int width, int height) {
        System.out.println("resize: h = " + height + " w = " + width);
        SCREEN_HEIGHT = height;
        SCREEN_WIDTH = width;

        viewport.update(width, height, true);
        batch.setProjectionMatrix(camera.combined);
    }

    @Override
    public void pause() {
        System.out.println("pause");
    }

    @Override
    public void resume() {
        System.out.println("resume");
    }

    @Override
    public void hide() {
        System.out.println("hide");
        dispose();
    }

    @Override
    public void dispose() {
        System.out.println("dispose");
    }


    @Override
    public boolean keyDown(int keycode) {
        System.out.println("keyDown keycode = " + keycode);
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        System.out.println("keyUp keycode = " + keycode);
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        System.out.println("keyTyped character = " + character);
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        //System.out.println("touchDown screenX = " + screenX + " screenY = " + screenY);
        return false;
    }

    public boolean touchDown(Point touch, int pointer) {
//        System.out.println("touchDown touch.x = " + touch.getX() + " touch.y = " + touch.getY());
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
//        System.out.println("touchUp screenX = " + screenX + " screenY = " + screenY);
        return false;
    }

    public boolean touchUp(Vector touch, int pointer) {
//        System.out.println("touchUp touch.x = " + touch.getX() + " touch.y = " + touch.getY());
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
//        System.out.println("touchDragged screenX = " + screenX + " screenY = " + screenY);
//        touch.set(screenX, screenBounds.getHeight() - screenY).mul(screenToWorlds);
//        touchDragged(touch, pointer);
        return false;
    }

//    public boolean touchDragged(Vector2 touch, int pointer) {
//        System.out.println("touchDragged touch.x = " + touch.x + " touch.y = " + touch.y);
//        return false;
//    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
