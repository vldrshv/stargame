package ru.geekbrains.desktop;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import ru.geekbrains.base.GameInstance;
import ru.geekbrains.base.MemeFactory;
import ru.geekbrains.spaceObjects.SpaceShip;
import ru.geekbrains.base.Vector;

public class TestClass {
    public static void main(String[] args) {
//        Vector v = new Vector (1,1,0);
//        Vector a = new Vector(0, 2, 0);
//
//        SpaceShip spaceShip = new SpaceShip(0, 0);
//        spaceShip.setWidth(20);
//        spaceShip.fire();
//        System.out.println(spaceShip.getBulletList().size());

//        MemeFactory mf = new MemeFactory(800, 400);
//
//        for (int i = 0; i < 7; i ++){
//            System.out.println();
//        }

        SpaceShip ship = new SpaceShip();
        ship.setHealth(1000);
        ship.setHeight(50);
        ship.setWidth(100);
        ship.setLevel(2);
        ship.setScreenHeight(1024);
        ship.setScreenWidth(800);
        //ship.setOutfit(null);
        ship.setSpeed(100);


            //File f = new File("azaza.txt");
        GameInstance gi = new GameInstance() ;
        gi.setShip(ship);
        gi.setMusicTrack("azaza.mp3");
        gi.setMusicBeginningTimeSec(33.0);

        try {
            FileOutputStream fileOut =
                    new FileOutputStream(".\\gameInstance.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(gi);
            out.close();
            fileOut.close();
            System.out.printf("Serialized data is saved in /ship.txt");
        } catch (IOException i) {
            i.printStackTrace();
        }

        gi = new GameInstance();
        try {
            FileInputStream fileIn = new FileInputStream(".\\gameInstance.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            gi = (GameInstance) in.readObject();
            in.close();
            fileIn.close();
        } catch (IOException i) {
            i.printStackTrace();
            return;
        } catch (ClassNotFoundException c) {
            System.out.println("Employee class not found");
            c.printStackTrace();
            return;
        }

        System.out.println(gi);
    }
}
