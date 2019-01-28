package ru.geekbrains.desktop;

import ru.geekbrains.spaceObjects.Asteroid;
import ru.geekbrains.spaceObjects.SpaceShip;
import ru.geekbrains.base.Vector;

public class main {
    public static void main(String[] args) {
        Vector v = new Vector (1,1,0);
        Vector a = new Vector(0, 2, 0);

        SpaceShip spaceShip = new SpaceShip(0, 0);
        spaceShip.setWidth(20);
        spaceShip.fire();
        System.out.println(spaceShip.getBulletList().size());
//        Asteroid asteroid = new Asteroid();
//        System.out.println(spaceShip);
//        System.out.println(asteroid);
//
//        System.out.println(spaceShip.wasDamaged(asteroid));

        //System.out.println(a.scal(v));
//        System.out.println(a.getAngle(v.getX(), v.getY()));
//        System.out.println("a = " + a);
//        a = a.rotate(49);
//        System.out.println("a.rotate() = " + a);
//        System.out.println("v = " + v);
//        v = v.add(a);
//        System.out.println("v = " + v);

    }
}
