package GeometricKingdom.mvc.model.floater;

import GeometricKingdom.mvc.controller.Game;
import GeometricKingdom.mvc.model.Sprite;
import GeometricKingdom.mvc.view.MapPanel;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Jonathan on 12/3/2015.
 */
public class PowerUp extends Sprite {
    public static enum PowerUpType {
        HEAL,           // heals the adventurer
        HP_UP,          // increases max HP
        DMG_UP,         // increases max damage
        SPD_UP,         // increases speed
        ATT_RATE_UP,    // increases attack rate
        UNIQUE_UP,      // increases max unique resource
        REGEN_UP        // increases unique resource regen
    }

    private PowerUpType type;
    private int valueFactor;
    private ArrayList<Point> pntCs;
    private Color[] colors = new Color[] {Color.RED, Color.BLUE, Color.GREEN, Color.ORANGE, Color.CYAN, Color.YELLOW};

    public PowerUp(MapPanel.MapZone mapZone) {
        int randInt = Game.R.nextInt(PowerUpType.values().length - 1) + 1;
        type = PowerUpType.values()[randInt];
        setColor(colors[randInt - 1]);

        setTeam(Team.FLOATER);
        setRadius(10);

//        switch (mapZone) {
//            case FOREST:
//                valueFactor = 1;
//                break;
//            case CAVE:
//                valueFactor = 2;
//                break;
//            case CASTLE:
//                valueFactor = 3;
//                break;
//        }

        getPoints();
        assignPolarPoints(pntCs);
        setFilled(true);
        setCenter(Game.getCenterOfPanel());
    }

    public ArrayList<Point> getPlusPoints() {
        ArrayList<Point> pntCs = new ArrayList<Point>();

        pntCs.add(new Point(1, 4));
        pntCs.add(new Point(1, 1));
        pntCs.add(new Point(4, 1));
        pntCs.add(new Point(4, -1));
        pntCs.add(new Point(1, -1));
        pntCs.add(new Point(1, -4));
        pntCs.add(new Point(-1, -4));
        pntCs.add(new Point(-1, -1));
        pntCs.add(new Point(-4, -1));
        pntCs.add(new Point(-4, 1));
        pntCs.add(new Point(-1, 1));
        pntCs.add(new Point(-1, 4));
        pntCs.add(new Point(1, 4));

        return pntCs;
    }

    public PowerUpType getType() {
        return type;
    }

    public int getValueFactor() {
        return valueFactor;
    }

    private void getPoints() {
        pntCs = getPlusPoints();

//        switch (type) {
//            case HP_UP:
//                break;
//            case DMG_UP:
//                break;
//            case SPD_UP:
//                break;
//            case ATT_RATE_UP:
//                break;
//            case UNIQUE_UP:
//                break;
//            case REGEN_UP:
//                break;
//        }

        assignPolarPoints(pntCs);
    }
}
