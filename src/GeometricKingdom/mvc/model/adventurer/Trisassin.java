package GeometricKingdom.mvc.model.adventurer;

import GeometricKingdom.mvc.controller.Game;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Jonathan on 11/19/2015.
 */
public class Trisassin extends Adventurer {

    private int overheatTick;
    private int overheatTimer;

    public Trisassin() {
        super();
        dmg = 3;
        spd = 15;
        prjSpd = 20;


        overheatTick = 0;
        overheatTimer = 30;

        resourceRefillRate = 5;

        uniqueResourceName = "AMMO";

        setTeam(Team.FRIEND);
        pntCs = getTrisassinPoints();

        // Triangular assassin design
        pntCs.add(new Point(10, 0));
        pntCs.add(new Point(-10, -10));
        pntCs.add(new Point(-10, 10));

        assignPolarPoints(pntCs);

        trueColor = Color.GREEN;
        setColor(trueColor);
        setCenter(new Point(Game.DIM.width / 2, Game.DIM.height / 2));
        setOrientation(0);
    }

    public ArrayList<Point> getTrisassinPoints() {
        ArrayList<Point> pntCs = new ArrayList<Point>();

        pntCs.add(new Point(10, 0));
        pntCs.add(new Point(-10, -10));
        pntCs.add(new Point(-10, 10));

        return pntCs;
    }

    public int getOverheatTick() {
        return overheatTick;
    }

    public boolean attacking() {
        return bAttackingLeft || bAttackingRight || bAttackingUp || bAttackingDown;
    }

    public void attack() {

    }

    public void refillResource() {
        if (!attacking()) {
            super.refillResource();
        }
    }

    public void move() {
        super.move();

//        // decrease the overheated timer if it exists
//        if (overheatTick > 0) {
//            overheatTick -= 1;
//        }
//
//        // if ammo is empty, start an overheated timer
//        if (uniqueResource <= 0) {
//            overheatTick = overheatTimer;
//        }
    }
}
