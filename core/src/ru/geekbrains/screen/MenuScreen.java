package ru.geekbrains.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.geekbrains.Point;
import ru.geekbrains.base.Base2DScreen;

public class MenuScreen extends Base2DScreen{

    TextureAtlas textureAtlas;

    Sprite button_newGame, button_exitGame;
    Sprite button_newGameAnimated, button_exitGameAnimated;

    Point position;
    Game game;

    int WAIT = 0;

    public MenuScreen(Game game){
        this.game = game;
    }

    @Override
    public void show() {
        super.show();
        textureAtlas = new TextureAtlas("sprites.txt");

        position = new Point();
        button_newGame = textureAtlas.createSprite("button_newGame");
        button_newGame.setPosition(0, SCREEN_HEIGHT / 2);

        button_newGameAnimated = textureAtlas.createSprite("button_newGameAnimated");
        button_newGameAnimated.setPosition(0, SCREEN_HEIGHT / 2);

        button_exitGame = textureAtlas.createSprite("button_exitGame");
        button_exitGame.setPosition(0, 0);

        button_exitGameAnimated = textureAtlas.createSprite("button_exitGameAnimated");
        button_exitGameAnimated.setPosition(0, 0);

    }

    @Override
    public void dispose() {
        textureAtlas.dispose();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();

        if(position.getY() > 0 && position.getY() < SCREEN_HEIGHT / 2) {
            button_exitGameAnimated.draw(batch);
            if (_wait())
                Gdx.app.exit();
        }
         else
            button_exitGame.draw(batch);
        if(position.getY() > SCREEN_HEIGHT / 2 && position.getY() < SCREEN_HEIGHT ) {
            button_newGameAnimated.draw(batch);
            if(_wait())
                game.setScreen(new GameScreen());
        }
        else
            button_newGame.draw(batch);
        batch.end();
    }

    private boolean _wait(){
        if (WAIT != 20)
            WAIT ++;
        else {
            WAIT = 0;
            return true;
        }
        System.out.println(WAIT);
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        position = new Point(screenX, SCREEN_HEIGHT - screenY, 0);
        System.out.println(position);
        return super.touchDown(position, pointer);
    }
}