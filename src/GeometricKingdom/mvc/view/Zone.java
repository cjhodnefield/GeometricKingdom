package GeometricKingdom.mvc.view;

import GeometricKingdom.mvc.controller.Game;

import java.util.ArrayList;

/**
 * Created by Jonathan on 11/19/2015.
 */
public class Zone {
    private MapPanel homePanel;
    private MapPanel exitPanel;
    private MapPanel bossPanel;
    private MapPanel itemPanel;
    private MapPanel[][] mapPanelGrid;
    private ArrayList<MapPanel> mapPanels;
    private int zoneSize;
    private int panelRange;

    public Zone(MapPanel.MapZone mapZone) {
        zoneSize = 3;
        mapPanels = new ArrayList<MapPanel>();
        mapPanelGrid = new MapPanel[zoneSize][zoneSize];
        String homeDisplay = "", exitDisplay = "", bossDisplay = "", itemDisplay = "";

        switch (mapZone) {
            case FOREST:
                homeDisplay = "You've arrived at the Tetris Forest. I wonder why they call it that...";
                exitDisplay = "It looks like a cave of some sort. Better investigate it.";
                bossDisplay = "Hm, looks like a landslide. You'll have to find a way to blast through it.";
                itemDisplay = "That's some serious explosive power right there.";
                panelRange = 4;
                break;
            case CAVE:
                homeDisplay = "Octarachnawhatnow? I have no idea what that means.";
                exitDisplay = "That must be a secret entrance into the castle. Onward!";
                bossDisplay = "I think I understand the name of the cave now. Watch out for those web strands!";
                itemDisplay = "Never look a gift horse in the mouth, I always say.";
                panelRange = 5;
                break;
            case CASTLE:
                homeDisplay = "You've made it to Castle Geo! Find the stairs to the throne room to end this struggle!";
                exitDisplay = "The stairs! Make haste to the throne room!";
                bossDisplay = "That is a LOT of pointy lines. Best watch out for, uh, the pointy end.";
                itemDisplay = "A glorious aegis with which to defend yourself!";
                panelRange = 5;
                break;
        }

        homePanel = new MapPanel(mapZone, MapPanel.PanelType.HOME);
        exitPanel = new MapPanel(mapZone, MapPanel.PanelType.EXIT);

        String[] bossLocation = determineBossLocation();
        String bossDir = bossLocation[0];
        int bossX = Integer.parseInt(bossLocation[1]);
        int bossY = Integer.parseInt(bossLocation[2]);
        bossPanel = new MapPanel(mapZone, MapPanel.PanelType.BOSS, bossDir);
        bossPanel.setBossDir(bossDir);

        String[] itemLocation = determineBossLocation();
        while (itemLocation[0].equals(bossDir) && Integer.parseInt(itemLocation[1]) == bossX && Integer.parseInt(itemLocation[2]) == bossY) {
            itemLocation = determineBossLocation();
        }

        String itemDir = itemLocation[0];
        int itemX = Integer.parseInt(itemLocation[1]);
        int itemY = Integer.parseInt(itemLocation[2]);
        itemPanel = new MapPanel(mapZone, MapPanel.PanelType.ITEM, itemDir);

        homePanel.setDisplayString(homeDisplay);
        exitPanel.setDisplayString(exitDisplay);
        bossPanel.setDisplayString(bossDisplay);
        itemPanel.setDisplayString(itemDisplay);

        homePanel.setPanelName(mapZone + " HOME");
        exitPanel.setPanelName(mapZone + " EXIT");
        bossPanel.setPanelName(mapZone + " BOSS");
        itemPanel.setPanelName(mapZone + " ITEM");

        mapPanels.add(homePanel);
        mapPanels.add(exitPanel);
        mapPanels.add(bossPanel);
        mapPanels.add(itemPanel);

        for (int i = 0; i < zoneSize; i++) {
            for (int j = 0; j < zoneSize; j++) {
                MapPanel mapPanel;
                if ((mapZone == MapPanel.MapZone.FOREST) && (i == zoneSize - 1) && (j == zoneSize / 2)) {
                    mapPanel = new MapPanel(mapZone, MapPanel.PanelType.TETRIS);
                    mapPanel.setDisplayString("Oh. That's why they call it that.");
                } else {
                    int panel = Game.R.nextInt(panelRange - 3 + 1) + 3;
                    mapPanel = new MapPanel(mapZone, MapPanel.PanelType.values()[panel]);
                    if (mapZone == MapPanel.MapZone.FOREST) {
                        mapPanel.setDisplayString("Look out! Geometric monstrosities! Cleanse them from the earth!");
                    } else if (mapZone == MapPanel.MapZone.CAVE) {
                        mapPanel.setDisplayString("Is it just me, or are these ruffians hitting harder than before?");
                    } else {
                        mapPanel.setDisplayString("FIGHT! FIGHT TILL YOUR LAST BREATH!");
                    }
                }
                mapPanel.setPanelName(mapZone + " TILE");
                mapPanelGrid[i][j] = mapPanel;
                mapPanels.add(mapPanel);
            }
        }

        connectPanels(homePanel, mapPanelGrid[zoneSize - 1][zoneSize / 2], "north");
        connectPanels(mapPanelGrid[bossX][bossY], bossPanel, bossDir);
        connectPanels(mapPanelGrid[itemX][itemY], itemPanel, itemDir);
        //System.out.println(bossDir + bossX + bossY);
        connectPanels(bossPanel, exitPanel, bossDir);

        for (int i = 0; i < zoneSize; i++) {
            for (int j = 0; j < zoneSize; j++) {
                if (i != zoneSize - 1) {
                    connectPanels(mapPanelGrid[i][j], mapPanelGrid[i + 1][j], "south");
                }
                if (j != zoneSize - 1) {
                    connectPanels(mapPanelGrid[i][j], mapPanelGrid[i][j + 1], "east");
                }
            }
        }
    }

    public MapPanel getHomePanel() {
        return homePanel;
    }

    public MapPanel getExitPanel() {
        return exitPanel;
    }

    public void connectPanels(MapPanel panel1, MapPanel panel2, String direction) {
        switch (direction) {
            case "north":
                panel1.setNorth(panel2);
                panel2.setSouth(panel1);
                break;
            case "south":
                panel1.setSouth(panel2);
                panel2.setNorth(panel1);
                break;
            case "east":
                panel1.setEast(panel2);
                panel2.setWest(panel1);
                break;
            case "west":
                panel1.setWest(panel2);
                panel2.setEast(panel1);
                break;
        }
    }

    public ArrayList<MapPanel> getMapPanels() {
        return mapPanels;
    }

    public int getPanelIndex(MapPanel mapPanel) {
        return mapPanels.indexOf(mapPanel);
    }

    public String[] determineBossLocation() {
        String[] bossLocation = new String[3];
        String[] side = new String[] {"west", "north", "east"};

        int randSide = Game.R.nextInt(zoneSize);
        int panel = Game.R.nextInt(zoneSize);

        bossLocation[0] = side[randSide];
        if (bossLocation[0].equals("north")) {
            bossLocation[1] = "0";
            bossLocation[2] = Integer.toString(panel);
        } else {
            panel = Game.R.nextInt(zoneSize - 1);
            bossLocation[1] = Integer.toString(panel);
            if (bossLocation[0].equals("west")) {
                bossLocation[2] = "0";
            } else {
                bossLocation[2] = Integer.toString(zoneSize - 1);
            }
        }
        return bossLocation;
    }

    public void updateMapPanel(int index, MapPanel updatedPanel) {
        mapPanels.set(index, updatedPanel);
    }
}
