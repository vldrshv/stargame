package ru.geekbrains.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;

import java.util.ArrayList;
import java.util.List;

import ru.geekbrains.spaceObjects.Asteroid;
import ru.geekbrains.base.Point;
import ru.geekbrains.spaceObjects.Bullet;
import ru.geekbrains.spaceObjects.SpaceShip;
import ru.geekbrains.base.Base2DScreen;

public class GameScreen extends Base2DScreen {

    Texture wallpaperImg;//, bulletImg, asteroidImg;

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
        dest = new Point(ship.getPosition());
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

        ship.move(dest);
        moveSpaceShipBullets();
        ship.fire();
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
            for (Bullet bullet : ship.getBulletList()){
                if (asteroid.wasDamaged(bullet)) {
                    asteroid.setHealth(asteroid.getHealth() - bullet.getDamage());
                    bullet.resetBullet();
                    // check asteroid health
                    if (asteroid.getHealth() <= 0){
                        asteroid.resetAsteroid(SCREEN_WIDTH, SCREEN_HEIGHT);
                        ship.addExperience();
                    }
                }
            }

        }
        // check ship health
        if (ship.getHealth() <= 0) {
            ship.downgrade();
//            spaceShipImg = new Texture(ship.getOutfit());
        }

    }

    private void moveSpaceShipBullets() {
        for (Bullet b : ship.getBulletList()) {
            b.move(new Point(SCREEN_WIDTH, SCREEN_HEIGHT, 0));
        }
    }

    private void moveAsteroids() {
        for (Asteroid asteroid : asteroidList) {
            asteroid.move(new Point(SCREEN_WIDTH, SCREEN_HEIGHT, 0));
        }
    }

    private void drawGameObjects() {
        batch.begin();
        batch.draw(wallpaperImg,0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
        batch.end();
        ship.render(batch);
        for (Asteroid asteroid : asteroidList) {
            asteroid.render(batch);
        }

        for (Bullet bullet : ship.getBulletList()) {
            bullet.render(batch);
        }
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        ship.resize(SCREEN_WIDTH, SCREEN_HEIGHT);
        for (Bullet b : ship.getBulletList())
            b.resize(SCREEN_WIDTH, SCREEN_HEIGHT);
        for (Asteroid a : asteroidList)
            a.resize(SCREEN_WIDTH, SCREEN_HEIGHT);
    }

    @Override
    public void dispose() {
        wallpaperImg.dispose();
        batch.dispose();
        ship.dispose();
        for (Bullet b : ship.getBulletList())
            b.dispose();
        for (Asteroid a : asteroidList)
            a.dispose();

        super.dispose();
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        dest = new Point(screenX, SCREEN_HEIGHT - screenY, 0);
        return super.touchDown(dest, pointer);
    }

}