package ru.geekbrains.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.geekbrains.base.Point;
import ru.geekbrains.base.Base2DScreen;

public class MenuScreen extends Base2DScreen{

    TextureAtlas textureAtlas;

    Sprite btnNewGame, btnExitGame;
    Sprite btnNewGameAnimated, btnExitGameAnimated;

    Point position;
    Game game;

    int WAIT = 0;

    public MenuScreen(Game game){
        this.game = game;
    }

    @Override
    public void show() {
        super.show();
        textureAtlas = new TextureAtlas("buttons_sprites.txt");

        position = new Point();
        btnNewGame = textureAtlas.createSprite("button_newGame");
        btnNewGame.setPosition(0, SCREEN_HEIGHT / 2);

        btnNewGameAnimated = textureAtlas.createSprite("button_newGameAnimated");
        btnNewGameAnimated.setPosition(0, SCREEN_HEIGHT / 2);

        btnExitGame = textureAtlas.createSprite("button_exitGame");
        btnExitGame.setPosition(0, 0);

        btnExitGameAnimated = textureAtlas.createSprite("button_exitGameAnimated");
        btnExitGameAnimated.setPosition(0, 0);

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
            btnExitGameAnimated.draw(batch);
            if (_wait())
                Gdx.app.exit();
        }
         else
            btnExitGame.draw(batch);
        if(position.getY() > SCREEN_HEIGHT / 2 && position.getY() < SCREEN_HEIGHT ) {
            btnNewGameAnimated.draw(batch);
            if(_wait())
                game.setScreen(new GameScreen());
        }
        else
            btnNewGame.draw(batch);
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