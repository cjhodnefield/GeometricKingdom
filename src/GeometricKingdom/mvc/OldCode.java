package GeometricKingdom.mvc;

/**
 * Created by Jonathan on 12/5/2015.
 */
public class OldCode {
}

//OLD ZONE GENERATION
//        MapPanel hexPanel = new MapPanel(mapZone, MapPanel.PanelType.HEX);
//        MapPanel tetrisPanel = new MapPanel(mapZone, MapPanel.PanelType.TETRIS);
//        MapPanel alienPanel = new MapPanel(mapZone, MapPanel.PanelType.ALIEN);
//
//        homePanel.setDisplayString(homeDisplay);
//        exitPanel.setDisplayString(exitDisplay);
//
//        homePanel.setPanelName("HOME");
//        exitPanel.setPanelName("EXIT");
//        hexPanel.setPanelName("HEX AREA");
//        tetrisPanel.setPanelName("TETRIS AREA");
//        alienPanel.setPanelName("ALIEN AREA");
//
//        mapPanels.add(homePanel);
//        mapPanels.add(exitPanel);
//        mapPanels.add(hexPanel);
//        mapPanels.add(tetrisPanel);
//        mapPanels.add(alienPanel);
//
//        connectPanels(homePanel, hexPanel, "north");
//        connectPanels(hexPanel, alienPanel, "north");
//        connectPanels(alienPanel, tetrisPanel, "north");
//        connectPanels(tetrisPanel, exitPanel, "north");



//OLDER ZONE GENERATION
//        int numNormalPanels = 3;    // creates
//
//        for (int i = 0; i <= numNormalPanels - 1; i++) {
//            MapPanel newMapPanel = new MapPanel(mapZone, MapPanel.PanelType.NORMAL, i + 1);
//            newMapPanel.setPanelName("AREA " + Integer.toString(i + 1));
//            if (i == 0) {
//                newMapPanel.setSouth(mapPanels.get(0));
//                mapPanels.get(0).setNorth(newMapPanel);
//            } else if (i == numNormalPanels - 1) {
//                newMapPanel.setSouth(mapPanels.get(mapPanels.size() - 1));
//                mapPanels.get(mapPanels.size() - 1).setNorth(newMapPanel);
//                newMapPanel.setNorth(mapPanels.get(1));
//                mapPanels.get(1).setSouth(newMapPanel);
//            } else {
//                newMapPanel.setSouth(mapPanels.get(mapPanels.size() - 1));
//                mapPanels.get(mapPanels.size() - 1).setNorth(newMapPanel);
//            }
//            mapPanels.add(newMapPanel);
//        }

//OLD BOUNCE CODE
//        double xDiff = this.getCenter().getX() - that.getCenter().getX();
//        double yDiff = this.getCenter().getY() - that.getCenter().getY();
//        int sumRad = this.getRadius() + that.getRadius();
//        double angle = Math.atan(yDiff / xDiff);
//        double newXDiff = sumRad * Math.cos(angle);
//        double newYDiff = sumRad * Math.sin(angle);
//
//        if (that.getBounceable()) {
//
//        } else {
//            int newX = (int) (this.getCenter().getX() + (newXDiff - xDiff));
//            int newY = (int) (this.getCenter().getY() + (newYDiff - yDiff));
//            this.setCenter(new Point(newX, newY));
//        }

//FOE-FOE COLLISION CHECK
//            for (Movable movFoe : Cc.getInstance().getMovFoes()) {
//                pntFoeCenter = movFoe.getCenter();
//                nFoeRadius = movFoe.getRadius();
//
//                for (Movable movOtherFoe : Cc.getInstance().getMovFoes()) {
//                    if (movFoe != movOtherFoe) {
//                        Point pntOtherFoeCenter = movOtherFoe.getCenter();
//                        int nOtherFoeRadius = movOtherFoe.getRadius();
//                        if (pntOtherFoeCenter.distance(pntFoeCenter) < (nOtherFoeRadius + nFoeRadius + 5)) {
//                            if (movFoe instanceof HPEntity && movOtherFoe instanceof HPEntity) {
//                                ((Sprite) movFoe).bounce((Sprite) movOtherFoe);
//                            }
//                        }
//                    }
//                }
//            }