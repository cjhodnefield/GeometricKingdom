package GeometricKingdom.mvc.model.adventurer;

import GeometricKingdom.mvc.controller.Game;

import java.awt.*;

/**
 * Created by Jonathan on 11/19/2015.
 */
public class Sorcircler extends Adventurer {

    private int bombRadMod;
    private boolean bombReady;

    public Sorcircler() {
        super();

        bombRadMod = 2;
        bombReady = true;

        uniqueResourceName = "MANA";

        dLengths = new double[628];
        dDegrees = new double[628];
        for (int i = 0; i < dLengths.length; i++) {
            dLengths[i] = 1;
            dDegrees[i] = 0.01 * i;
        }

        trueColor = Color.BLUE;
        setColor(trueColor);
        setCenter(new Point(Game.DIM.width / 2, Game.DIM.height / 2));
        setOrientation(0);
    }

    public int getBombRadMod() {
        return bombRadMod;
    }

    public void setBombRadMod(int bombRadMod) {
        this.bombRadMod = bombRadMod;
    }

    public boolean isBombReady() {
        return bombReady;
    }

    public void setBombReady(boolean bombReady) {
        this.bombReady = bombReady;
    }

    public void move() {
        super.move();
        if (uniqueResource < uniqueResourceMax) {
            bombReady = false;
        } else {
            bombReady = true;
        }
    }
}
