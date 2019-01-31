package ru.geekbrains.desktop;

import ru.geekbrains.spaceObjects.SpaceShip;
import ru.geekbrains.base.Vector;

public class TestClass {
    public static void main(String[] args) {
        Vector v = new Vector (1,1,0);
        Vector a = new Vector(0, 2, 0);

        SpaceShip spaceShip = new SpaceShip(0, 0);
        spaceShip.setWidth(20);
        spaceShip.fire();
        System.out.println(spaceShip.getBulletList().size());
    }
}
