package ru.geekbrains.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;

import java.util.ArrayList;
import java.util.List;

import ru.geekbrains.Asteroid;
import ru.geekbrains.Point;
import ru.geekbrains.SpaceShip;
import ru.geekbrains.base.Base2DScreen;

public class GameScreen extends Base2DScreen {

    Texture wallpaperImg, spaceShipImg, fireImg, asteroidImg;

    Point dest;

    SpaceShip ship;
    List<Asteroid> asteroidList;

    int ASTEROID_QUANTITY = 7;

    @Override
    public void show() {
        super.show();

        ship = new SpaceShip(SCREEN_WIDTH / 2, 0);
        ship.setWidth(SCREEN_WIDTH / 100 * 10);
        ship.setHeight(SCREEN_HEIGHT / 100 * 15);

        initAsteroids();

        wallpaperImg = new Texture("space.jpg");
        spaceShipImg = new Texture(ship.getOutfit());
        System.out.println(ship.getOutfit());
        asteroidImg = new Texture("asteroid.png");//asteroid.getOutfit());


        dest = new Point(ship.getPosition());
        System.out.println(dest);
    }
    private void initAsteroids() {
        asteroidList = new ArrayList<Asteroid>();
        for (int i = 0; i < ASTEROID_QUANTITY; i ++) {
            Asteroid asteroid = new Asteroid();
            asteroid.setWidth(SCREEN_WIDTH / 100 * 5);
            asteroid.setHeight(SCREEN_HEIGHT / 100 * 10);
            asteroid.setPosition(new Point(
                    Math.random() * 1024 % SCREEN_WIDTH,
                    SCREEN_HEIGHT + Math.random() * 1000 % SCREEN_HEIGHT, 0));
            asteroidList.add(i, asteroid);
        }
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        Gdx.gl.glClearColor(0.5f, 0.2f, 0.3f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        drawGameObjects();

//        if (!ship.getPosition().equal(dest))
        ship.move(dest);
        moveAsteroids();


        checkDamage();
    }

    private void checkDamage() {
        // 1 TODO: 28.01.2019 Проверить урон, который был нанесен астероиду
        // 2 TODO: 28.01.2019 Удалить астероид при столкновении с кораблем
        // 3 TODO: 28.01.2019 Проверить урон, нанесенный кораблю
        // 4 TODO: 28.01.2019 Проверить урон, нанесенный кораблям врага
        for (Asteroid asteroid : asteroidList){
            if(ship.wasDamaged(asteroid)) {
                ship.setHealth(ship.getHealth() - asteroid.getDamage()); // ship damage (2)
                asteroid.resetAsteroid(SCREEN_WIDTH, SCREEN_HEIGHT);
            }
            // check asteroid health
            if (asteroid.getHealth() <= 0){
                asteroid.resetAsteroid(SCREEN_WIDTH, SCREEN_HEIGHT);
                ship.addExperience();
                if (ship.isUpgrated())
                    spaceShipImg = new Texture(ship.getOutfit());
            }
        }
        // check ship health
        if (ship.getHealth() <= 0) {
            ship.downgrade();
            spaceShipImg = new Texture(ship.getOutfit());
        }
    }

    private void moveAsteroids() {
        for (Asteroid asteroid : asteroidList) {
            asteroid.move(new Point(SCREEN_WIDTH, SCREEN_HEIGHT, 0));
            if(asteroid.isOutOfScreen(SCREEN_WIDTH, SCREEN_HEIGHT)) {
                ship.addExperience();
                if (ship.isUpgrated())
                    spaceShipImg = new Texture(ship.getOutfit());
            }
        }
    }

    private void drawGameObjects() {
        batch.begin();
        batch.draw(wallpaperImg,
                0, 0,
                SCREEN_WIDTH, SCREEN_HEIGHT);
        batch.draw(spaceShipImg,
                (float) ship.getPosition().getX(), (float) ship.getPosition().getY(),
                ship.getWidth() + 5, ship.getHeight() + 5);
        for (Asteroid asteroid : asteroidList) {
            batch.draw(asteroidImg,
                    (float) asteroid.getPosition().getX(), (float) asteroid.getPosition().getY(),
                    asteroid.getWidth(), asteroid.getHeight());
        }
        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        ship.setWidth(SCREEN_WIDTH / 100 * 10);
        ship.setHeight(SCREEN_HEIGHT / 100 * 15);
    }

    @Override
    public void dispose() {
        spaceShipImg.dispose();
        wallpaperImg.dispose();
        batch.dispose();
        super.dispose();
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        dest = new Point(screenX, SCREEN_HEIGHT - screenY, 0);
        System.out.println(dest);
        return super.touchDown(dest, pointer);
    }

}