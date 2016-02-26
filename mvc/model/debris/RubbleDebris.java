package GeometricKingdom.mvc.model.debris;

import GeometricKingdom.mvc.controller.Game;
import GeometricKingdom.mvc.model.Movable;
import GeometricKingdom.mvc.model.enemy.Rubble;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Jonathan on 12/6/2015.
 */
public class RubbleDebris extends Debris {
    private ArrayList<Point> pntCs;

    public RubbleDebris() {
        setTeam(Team.DEBRIS);
        setColor(Color.DARK_GRAY);
        setFilled(true);
        setTraced(true, Color.BLACK);
        setBounceable(false);
        setRadProportion(1 / 15.0);
        setRadius((int) (Game.DIM.width * getRadProportion()));

        assignRandomShape();

        int randOrientMod = Game.R.nextInt(181);
        setOrientation(-90 + randOrientMod);
    }

    public void assignRandomShape () {
        int nSide = Game.R.nextInt( 7 ) + 7;
        int nSidesTemp = nSide;

        int[] nSides = new int[nSide];
        for ( int nC = 0; nC < nSides.length; nC++ )
        {
            int n = nC * 48 / nSides.length - 4 + Game.R.nextInt( 8 );
            if ( n >= 48 || n < 0 )
            {
                n = 0;
                nSidesTemp--;
            }
            nSides[nC] = n;
        }

        Arrays.sort( nSides );

        double[]  dDegrees = new double[nSidesTemp];
        for ( int nC = 0; nC <dDegrees.length; nC++ )
        {
            dDegrees[nC] = nSides[nC] * Math.PI / 24 + Math.PI / 2;
        }
        setDegrees( dDegrees);

        double[] dLengths = new double[dDegrees.length];
        for (int nC = 0; nC < dDegrees.length; nC++) {
            if(nC %3 == 0)
                dLengths[nC] = 1 - Game.R.nextInt(40)/100.0;
            else
                dLengths[nC] = 1;
        }
        setLengths(dLengths);

    }

    public void setRandomCenter() {
        int width = Game.DIM.width;
        int height = Game.DIM.height;
        int r = getRadius();
        int x = Game.R.nextInt(width - r + 1) + r;
        int y = Game.R.nextInt(height - r + 1) + r;

        setCenter(new Point(x, y));
    }
}
