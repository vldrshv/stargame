package ru.geekbrains;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

import ru.geekbrains.screen.MenuScreen;

public class Star2DGame extends Game {

    @Override
    public void create() {
        setScreen(new MenuScreen(this));
    }

    @Override
    public void render() {
        super.render();
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            pause();
            setScreen(new MenuScreen(this));
        }
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
    }

}
