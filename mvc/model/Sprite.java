package GeometricKingdom.mvc.model;

import GeometricKingdom.mvc.controller.Game;

import java.awt.*;
import java.util.ArrayList;

public abstract class Sprite implements Movable {
	//the center-point of this sprite
	private Point pntCenter;
	//this causes movement; change in x and change in y
	private double dDeltaX, dDeltaY;

    //every sprite needs to know about the size of the gaming environ
	private Dimension dim;      //dim of the gaming environment
	private Team mTeam;         //we need to know what team we're on
	private int nRadius;        //the radius of circumscribing circle
    private double nRadProportion;
	private int nOrientation;
	private int nExpiry = -1;   //natural mortality (short-living objects)
	private Color col;          //the color of this sprite
    private boolean bBounceable = true;
	private boolean bTraced = false;
    private Color traceColor;
	private boolean bFilled = false;

	//radial coordinates
	//this game uses radial coordinates to render sprites
	public double[] dLengths;
	public double[] dDegrees;
	
	//for drawing alternative 
	//public double[] dLengthAlts;
	//public double[] dDegreeAlts;

	//fade value for fading in and out
	private int nFade;

	//these are used to draw the polygon. You don't usually need to interface with these
	private Point[] pntCoords; //an array of points used to draw polygon
	private int[] nXCoords;
	private int[] nYCoords;

	@Override
	public Team getTeam() {
		//default
	  return mTeam;
	}

	public void setTeam(Team team){
		mTeam = team;
	}

	public void move() {
        String edge = mapEdgeEncountered();

        int x = getCenter().x;
        int y = getCenter().y;
        int r = (int) (1.5 * getRadius());
        int width = Game.DIM.width;
        int height = Game.DIM.height;
        int border = Game.WALL_THICKNESS;

        int nudge = 5;

        if (edge.equals("east")) {
            setDeltaX(-1 * getDeltaX());
            setCenter(new Point(x - nudge, y));
        } else if (edge.equals("west")) {
            setDeltaX(-1 * getDeltaX());
            setCenter(new Point(x + nudge, y));
        } else if (edge.equals("north")) {
            setDeltaY(-1 * getDeltaY());
            setCenter(new Point(x, y + nudge));
        } else if (edge.equals("south")) {
            setDeltaY(-1 * getDeltaY());
            setCenter(new Point(x, y - nudge));
        }

        Point pnt = getCenter();
        double dX = pnt.x + getDeltaX();
        double dY = pnt.y + getDeltaY();
        setCenter(new Point((int) dX, (int) dY));

//		//this just keeps the sprite inside the bounds of the frame
//		if (pnt.x > getDim().width) {
//			setCenter(new Point(1, pnt.y));
//		} else if (pnt.x < 0) {
//			setCenter(new Point(getDim().width - 1, pnt.y));
//		} else if (pnt.y > getDim().height) {
//			setCenter(new Point(pnt.x, 1));
//		} else if (pnt.y < 0) {
//			setCenter(new Point(pnt.x, getDim().height - 1));
//		} else {
//			setCenter(new Point((int) dX, (int) dY));
//		}
	}

	public Sprite() {

	//you can override this and many more in the subclasses
		setDim(Game.DIM);
		setColor(Color.white);
		setCenter(new Point(Game.R.nextInt(Game.DIM.width),
				Game.R.nextInt(Game.DIM.height)));


	}

    public int getExpire() {
        return nExpiry;
    }

	public void setExpire(int n) {
		nExpiry = n;
	}

	public double[] getLengths() {
		return this.dLengths;
	}

	public void setLengths(double[] dLengths) {
		this.dLengths = dLengths;
	}

	public double[] getDegrees() {
		return this.dDegrees;
	}

	public void setDegrees(double[] dDegrees) {
		this.dDegrees = dDegrees;
	}

	public Color getColor() {
		return col;
	}

	public void setColor(Color col) {
		this.col = col;
	}

	public int points() {
		//default is zero
		return 0;
	}

    public boolean getBounceable() {
        return bBounceable;
    }

    public void setBounceable(boolean bounceable) {
        bBounceable = bounceable;
    }

	public boolean isExploding() {
		return false;
	}

	public int getOrientation() {
		return nOrientation;
	}

	public void setOrientation(int n) {
		nOrientation = n;
	}

	public void setDeltaX(double dSet) {
		dDeltaX = dSet;
	}

	public void setDeltaY(double dSet) {
		dDeltaY = dSet;
	}

	public double getDeltaY() {
		return dDeltaY;
	}

	public double getDeltaX() {
		return dDeltaX;
	}

	public int getRadius() {
		return nRadius;
	}

    public double getRadProportion() {
        return nRadProportion;
    }

    public void setRadProportion(double radProportion) {
        this.nRadProportion = radProportion;
    }

	public void setRadius(int n) {
		nRadius = n;
	}

	public Dimension getDim() {
		return dim;
	}

	public void setDim(Dimension dim) {
		this.dim = dim;
	}

	public Point getCenter() {
		return pntCenter;
	}

	public void setCenter(Point pntParam) {
		pntCenter = pntParam;
	}

	public void setYcoord(int nValue, int nIndex) {
		nYCoords[nIndex] = nValue;
	}

	public void setXcoord(int nValue, int nIndex) {
		nXCoords[nIndex] = nValue;
	}

	public int getYcoord( int nIndex) {
		return nYCoords[nIndex];// = nValue;
	}

	public int getXcoord( int nIndex) {
		return nXCoords[nIndex];// = nValue;
	}

	public int[] getXcoords() {
		return nXCoords;
	}

	public int[] getYcoords() {
		return nYCoords;
	}

	public void setXcoords( int[] nCoords) {
		 nXCoords = nCoords;
	}

	public void setYcoords(int[] nCoords) {
		 nYCoords =nCoords;
	}

	protected double hypot(double dX, double dY) {
		return Math.sqrt(Math.pow(dX, 2) + Math.pow(dY, 2));
	}

	/*utility function to convert from cartesian to polar
	 *since it's much easier to describe a sprite as a list of cartesean points
	 *sprites (except Asteroid) should use the cartesean technique to describe the coordinates
	 *see Falcon or Bullet constructor for examples
	 */
	protected double[] convertToPolarDegs(ArrayList<Point> pntPoints) {

		//ArrayList<Tuple<Double,Double>> dblCoords = new ArrayList<Tuple<Double,Double>>();
		double[] dDegs = new double[pntPoints.size()];

		int nC = 0;
		for (Point pnt : pntPoints) {
			dDegs[nC++]=(Math.toDegrees(Math.atan2(pnt.y, pnt.x))) * Math.PI / 180 ;
		}
		return dDegs;
	}
	//utility function to convert to polar
	protected double[] convertToPolarLens(ArrayList<Point> pntPoints) {

		double[] dLens = new double[pntPoints.size()];

		//determine the largest hypotenuse
		double dL = 0;
		for (Point pnt : pntPoints)
			if (hypot(pnt.x, pnt.y) > dL)
				dL = hypot(pnt.x, pnt.y);

		int nC = 0;
		for (Point pnt : pntPoints) {
			if (pnt.x == 0 && pnt.y > 0) {
				dLens[nC] = hypot(pnt.x, pnt.y) / dL;
			} else if (pnt.x < 0 && pnt.y > 0) {
				dLens[nC] = hypot(pnt.x, pnt.y) / dL;
			} else {
				dLens[nC] = hypot(pnt.x, pnt.y) / dL;
			}
			nC++;
		}

		// holds <thetas, lens>
		return dLens;

	}

	protected void assignPolarPoints(ArrayList<Point> pntCs) {
		setDegrees(convertToPolarDegs(pntCs));
		setLengths(convertToPolarLens(pntCs));
	}

    public void draw(Graphics g) {
        nXCoords = new int[dDegrees.length];
        nYCoords = new int[dDegrees.length];
        //need this as well
        pntCoords = new Point[dDegrees.length];

        for (int nC = 0; nC < dDegrees.length; nC++) {
            nXCoords[nC] =    (int) (getCenter().x + getRadius() 
                            * dLengths[nC] 
                            * Math.sin(Math.toRadians(getOrientation()) + dDegrees[nC]));
            nYCoords[nC] =    (int) (getCenter().y - getRadius()
                            * dLengths[nC]
                            * Math.cos(Math.toRadians(getOrientation()) + dDegrees[nC]));

            //need this line of code to create the points which we will need for debris
            pntCoords[nC] = new Point(nXCoords[nC], nYCoords[nC]);
        }

        g.setColor(getColor());
        if (bFilled) {
            g.fillPolygon(getXcoords(), getYcoords(), dDegrees.length);
        } else {
            g.drawPolygon(getXcoords(), getYcoords(), dDegrees.length);
        }
        if (bTraced) {
            Color prevColor = getColor();
            g.setColor(traceColor);
            g.drawPolygon(getXcoords(), getYcoords(), dDegrees.length);
            g.setColor(prevColor);
        }
    }

	public Point[] getObjectPoints() {
		return pntCoords;
	}

	public void setObjectPoints(Point[] pntPs) {
		 pntCoords = pntPs;
	}
	
	public void setObjectPoint(Point pnt, int nIndex) {
		 pntCoords[nIndex] = pnt;
	}

	public int getFadeValue() {
		return nFade;
	}

	public void setFadeValue(int n) {
		nFade = n;
	}

    public boolean isFilled() {
        return bFilled;
    }

    public void setFilled(boolean filled) {
        bFilled = filled;
    }

    public boolean isTraced() {
        return bTraced;
    }

    public void setTraced(boolean traced, Color color) {
        bTraced = traced;
    }

    public String mapEdgeEncountered() {
        String output = "";

        Point pnt = getCenter();
        double dX = pnt.x + getDeltaX();
        double dY = pnt.y + getDeltaY();

        int rad = getRadius();
        int buffer = (int) (rad + Game.WALL_THICKNESS);
        //EAST COLLISION
        if (pnt.x + buffer > getDim().width) {
            output = "east";

            //WEST COLLISION
        } else if (pnt.x - buffer < 0) {
            output = "west";

            //SOUTH COLLISION
        } else if (pnt.y + buffer + Game.southBump > getDim().height) {
            output = "south";

            //NORTH COLLISION
        } else if (pnt.y - buffer < 0) {
            output = "north";

            //NO WALL COLLISION
        } else {
            output = "none";
        }
        return output;
    }

    public double angleTo(Movable that) {
        double thisX = this.getCenter().x;
        double thisY = this.getCenter().y;
        double thatX = that.getCenter().x;
        double thatY = that.getCenter().y;
        double angle = Math.atan2((thisX - thatX), (thisY - thatY));
        return angle;
    }

    public int proximity(Movable that) {
        if (getCenter().distance(that.getCenter()) < (getRadius() + that.getRadius())) {
            return (int) ((getRadius() + that.getRadius()) - getCenter().distance(that.getCenter()));
        } else {
            return 0;
        }
    }

    private double bounceOfStationary(double thisDim, double thisDeltaDim, double thatDim, double factor) {
        if (thisDeltaDim > 0) {
            if (thatDim - thisDim > 0) {
                thisDim = thisDim - thisDeltaDim * factor;
            } else {
                thisDim = thisDim + thisDeltaDim * factor;
            }
        } else if (thisDeltaDim < 0) {
            if (thatDim - thisDim < 0) {
                thisDim = thisDim - thisDeltaDim * factor;
            } else {
                thisDim = thisDim + thisDeltaDim * factor;
            }
        }
        return thisDim;
    }

    private double bounceOfMoving(double thisDim, double thisDeltaDim, double thatDim, double thatDeltaDim, double factor) {
        if (thatDeltaDim > 0) {
            // moving same direction in this dimension
            if (thisDeltaDim > 0) {
                if (thatDim - thisDim > 0) {
                    thisDim = thisDim - thisDeltaDim * factor;
                } else {
                    thisDim = thisDim + (thisDeltaDim + thatDeltaDim) * factor;
                }
            // moving opposite directions in this dimension
            } else if (thisDeltaDim < 0) {
                // colliding in this dimension
                if (thatDim - thisDim < 0) {
                    thisDim = thisDim - thisDeltaDim + thatDeltaDim;
                //this case should never occur
                } else {}
            } else {
                thisDim = thisDim + thatDeltaDim;
            }
        } else {    //(thatDeltaDim < 0)
            // moving same direction in this dimension
            if (thisDeltaDim < 0) {
                if (thatDim - thisDim < 0) {
                    thisDim = thisDim - thisDeltaDim * factor;
                } else {
                    thisDim = thisDim + (thisDeltaDim + thatDeltaDim) * factor;
                }
            // moving opposite directions in this dimension
            } else if (thisDeltaDim > 0) {
                // colliding in this dimension
                if (thatDim - thisDim > 0) {
                    thisDim = thisDim - thisDeltaDim + thatDeltaDim;
                    //this case should never occur
                } else {}
            } else {
                thisDim = thisDim + thatDeltaDim;
            }
        }
        return thisDim;
    }

    public void bounce(Sprite that, int proximity) {
        double factor = 1.2;

        double thisX = getCenter().x;
        double thisY = getCenter().y;
        double thatX = that.getCenter().x;
        double thatY = that.getCenter().y;

        double thisDX = getDeltaX();
        double thisDY = getDeltaY();
        double thatDX = that.getDeltaX();
        double thatDY = that.getDeltaY();
        double angle = angleTo(that);

        if (this.getBounceable() && !that.getBounceable()) {
            if (thatDX == 0) {
                thisX = bounceOfStationary(thisX, thisDX, thatX, factor);
            } else {
                thisX = bounceOfMoving(thisX, thisDX, thatX, thatDX, factor);
            }

            if (thatDY == 0) {
                thisY = bounceOfStationary(thisY, thisDY, thatY, factor);
            } else {
                thisY = bounceOfMoving(thisY, thisDY, thatY, thatDY, factor);
            }

            setCenter(new Point((int) thisX, (int) thisY));
        } else {

        }

//        if (this.getBounceable() && !that.getBounceable()) {
//            int x = (int) (factor * proximity * Math.cos(angle));
//            int y = (int) (factor * proximity * Math.sin(angle));
//            setCenter(new Point(getCenter().x - x - thisX, getCenter().y - y - thisY));
//        } else {
//
//        }

//        if (this.getBounceable()) {
//            int X = (int) (this.getCenter().getX() - thisX + thatX);
//            int Y = (int) (this.getCenter().getY() - thisY + thatY);
//            this.setCenter(new Point(X, Y));
//            this.setDeltaX(thatX - thisX);
//            this.setDeltaX(thatY - thisY);
//        }
//        if (that.getBounceable()) {
//            int X = (int) (that.getCenter().getX() + thisX - thatX);
//            int Y = (int) (that.getCenter().getY() + thisY - thatY);
//            that.setCenter(new Point(X, Y));
//            that.setDeltaX(thisX - thatX);
//            that.setDeltaX(thisY - thatY);
//        }
    }
}
