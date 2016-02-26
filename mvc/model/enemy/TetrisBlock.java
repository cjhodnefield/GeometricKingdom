package GeometricKingdom.mvc.model.enemy;

import GeometricKingdom.mvc.controller.Game;
import GeometricKingdom.mvc.model.HPEntity;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Jonathan on 11/21/2015.
 */
public class TetrisBlock extends HPEntity {

    public TetrisBlock(int blockType) {
        super();
        maxHP = 1;
        HP = maxHP;
        dmg = 5;
        spd = 10;

        setRadProportion(1/20.0);
        setRadius((int) (Game.DIM.width * getRadProportion()));

        setTeam(Team.OBSTACLE);
        setColor(randomEnemyColor());
        setBounceable(false);

        setSpd(spd + Game.R.nextInt(5));

        setCenter(new Point((Game.DIM.width / 2), (Game.DIM.height / 2)));

        ArrayList<Point> pntCs = getBlockTypePoints(blockType);

        assignPolarPoints(pntCs);
        setOrientation(Game.R.nextInt(4) * 90);
    }

    public ArrayList<Point> getBlockTypePoints(int blockType) {
        ArrayList<Point> pntCs = new ArrayList<Point>();
        if (blockType == 1) {
            pntCs.add(new Point(0, 0));
            pntCs.add(new Point(10, 0));
            pntCs.add(new Point(10, 10));
            pntCs.add(new Point(0, 10));
            pntCs.add(new Point(0, 0));
            pntCs.add(new Point(0, 10));
            pntCs.add(new Point(-10, 10));
            pntCs.add(new Point(-10, 0));
            pntCs.add(new Point(0, 0));
            pntCs.add(new Point(-10, 0));
            pntCs.add(new Point(-10, -10));
            pntCs.add(new Point(0, -10));
            pntCs.add(new Point(0, 0));
            pntCs.add(new Point(0, -10));
            pntCs.add(new Point(10, -10));
            pntCs.add(new Point(10, 0));
            pntCs.add(new Point(0, 0));
        } else if (blockType == 2) {
            pntCs.add(new Point(-10, 5));
            pntCs.add(new Point(-10, -15));
            pntCs.add(new Point(0, -15));
            pntCs.add(new Point(0, 15));
            pntCs.add(new Point(10, 15));
            pntCs.add(new Point(10, -5));
            pntCs.add(new Point(-10, -5));
            pntCs.add(new Point(-10, 5));
            pntCs.add(new Point(10, 5));
        }
        return pntCs;
    }
}
