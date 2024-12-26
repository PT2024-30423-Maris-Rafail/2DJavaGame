package Display;

import Entity.Entity;
import GameState.State;
import Items.Item;

public class CheckCollision {
    Panel panel;

    public CheckCollision(Panel panel) {
        this.panel = panel;
    }

    /**
     * A player can only enter onto, at most, 2 tiles, so, by analyzing the direction (key pressed) we check if that tile is in bounds and if it can be entered into
     * @param entity entity moving into the tile
     */
    public void checkTile(Entity entity) {

        int whichMap = 0;
        if (panel.state == State.MAP2) whichMap = 1;

        int leftX, rightX, topY, bottomY;//coordinates on the world map
        leftX = entity.mapX + entity.collisionX;
        rightX = entity.mapX + entity.collisionX + entity.widthCollision;
        topY = entity.mapY + entity.collisionY;
        bottomY = entity.mapY + entity.collisionY + entity.heightCollision;
        //System.out.println("here");
        //checks if the tile is out of bounds, so that we don't access wrong memory from the tile array
        if (!((leftX - entity.speed >= 0) && (rightX + entity.speed <= panel.map.wrldHeight[whichMap]) && (topY - entity.speed >= 0) && (bottomY + entity.speed <= panel.map.wrldWidth[whichMap])
        )) {
            //System.out.println("HERE"+(leftX - entity.speed) + " " + (rightX + entity.speed ) + " " + ((topY - entity.speed )) + " " + ((bottomY + entity.speed )));
            return;
        }
        //System.out.println((leftX - entity.speed) + " " + (rightX + entity.speed ) + " " + ((topY - entity.speed )) + " " + ((bottomY + entity.speed )));

        //we have coordinates. we have to check if the tile we want to move into is available for moving (doesn't cause collision)
        // = > we need to know which tile(S) we can move to
        //we can't move into more than 2 tiles =>
        int leftCol, rightCol, topRow, bottomRow;
        //coordinates on map
        leftCol = leftX / panel.actualSize;
        rightCol = rightX / panel.actualSize;
        topRow = topY / panel.actualSize;
        bottomRow = bottomY / panel.actualSize;
        int movedIntoTile1, movedIntoTile2;
        //depending on the moving direction, we go to check a specific tile
        switch (entity.direction) {

            case LEFT:
                leftCol = (leftX - entity.speed) / panel.actualSize;
                movedIntoTile1 = panel.map.tilesManager[whichMap][leftCol][topRow];
                movedIntoTile2 = panel.map.tilesManager[whichMap][leftCol][bottomRow];
                if (panel.map.tiles[whichMap][movedIntoTile1].isCollision || panel.map.tiles[whichMap][movedIntoTile2].isCollision) {
                    //System.out.println("left collision");
                    entity.collides = true;
                }

                break;
            case RIGHT:
                rightCol = (rightX + entity.speed) / panel.actualSize;
                movedIntoTile1 = panel.map.tilesManager[whichMap][rightCol][topRow];
                movedIntoTile2 = panel.map.tilesManager[whichMap][rightCol][bottomRow];
                if (panel.map.tiles[whichMap][movedIntoTile1].isCollision || panel.map.tiles[whichMap][movedIntoTile2].isCollision) {
                    entity.collides = true;
                    //System.out.println("right collision");
                }
                break;
            case UP:
                topRow = (topY - entity.speed) / panel.actualSize;
                movedIntoTile1 = panel.map.tilesManager[whichMap][leftCol][topRow];
                movedIntoTile2 = panel.map.tilesManager[whichMap][rightCol][topRow];
                if (panel.map.tiles[whichMap][movedIntoTile1].isCollision || panel.map.tiles[whichMap][movedIntoTile2].isCollision) {
                    entity.collides = true;
                    //System.out.println("up collision");
                }
                break;
            case DOWN:
                bottomRow = (bottomY + entity.speed) / panel.actualSize;
                movedIntoTile1 = panel.map.tilesManager[whichMap][leftCol][bottomRow];
                movedIntoTile2 = panel.map.tilesManager[whichMap][rightCol][bottomRow];
                if (panel.map.tiles[whichMap][movedIntoTile1].isCollision || panel.map.tiles[whichMap][movedIntoTile2].isCollision) {
                    entity.collides = true;
                    //System.out.println("down collision");
                }
                break;

        }

    }

    //same as above, but checks the items
    //ADDITIONALLY: returns which of the items it read

    /**
     * Checks which Item the entity picks up
     * @param entity entity whose movement is studied
     * @param items Array of items which can be picked up
     * @return index of item to be picked up
     */
    public int checksColItemArray(Entity entity, Item[] items) {
        int entitySolidX, entitySolidY;
        int itemSolidX, itemSolidY;
        int itemHeight, itemWidth;
        entitySolidY = entity.mapY + entity.collisionY;
        entitySolidX = entity.mapX + entity.collisionX;
        int index = -1;
        for (int i = 0; i < items.length; i++) {

            if (items[i] != null) {

                itemHeight = items[i].heightCollision;
                itemWidth = items[i].widthCollision;
                itemSolidX = items[i].collisionX + items[i].mapX;
                itemSolidY = items[i].collisionY + items[i].mapY;

                switch (panel.player.direction) {

                    case LEFT:
                        entitySolidX = entitySolidX - panel.player.speed;
                        if (!(entitySolidY < itemSolidY || entitySolidY - panel.player.heightCollision > itemSolidY + itemHeight ||
                                entitySolidX + panel.player.widthCollision < itemSolidX || entitySolidX > itemSolidX + itemWidth)) {

                            index = i;
                            if (items[i].collision) entity.collides = true;
                        }

                        //if(itemSolidX>entitySolidX) {System.out.println("Left collision");}
                        entitySolidX += panel.player.speed;

                        break;
                    case RIGHT:
                        entitySolidX += panel.player.speed;
                        if (!(entitySolidY < itemSolidY || entitySolidY - panel.player.heightCollision > itemSolidY + itemHeight ||
                                entitySolidX + panel.player.widthCollision < itemSolidX || entitySolidX > itemSolidX + itemWidth)) {

                            index = i;
                            if (items[i].collision) entity.collides = true;
                        }
                        entitySolidX -= panel.player.speed;

                        //if(itemSolidX<entitySolidX) System.out.println("Right collision");
                        break;
                    case UP:
                        entitySolidY -= panel.player.speed;
                        if (!(entitySolidY < itemSolidY || entitySolidY - panel.player.heightCollision > itemSolidY + itemHeight ||
                                entitySolidX + panel.player.widthCollision < itemSolidX || entitySolidX > itemSolidX + itemWidth)) {

                            index = i;
                            if (items[i].collision) entity.collides = true;
                        }
                        entitySolidY += panel.player.speed;

                        break;
                    case DOWN:
                        entitySolidY += panel.player.speed; //+ panel.player.heightCollision;
                        //entitySolidX+=panel.player.heightCollision
                        if (!(entitySolidY + panel.player.heightCollision < itemSolidY || entitySolidY - panel.player.heightCollision > itemSolidY + itemHeight ||
                                entitySolidX + panel.player.widthCollision < itemSolidX || entitySolidX > itemSolidX + itemWidth)) {

                            index = i;
                            if (items[i].collision) entity.collides = true;
                        }
                        entitySolidY -= panel.player.speed;

                        //if(itemSolidY < entitySolidY)System.out.println("Down collision");
                        break;

                }

            }
            if (index != -1) return index;
        }
        return -1;
    }

    /**
     * Returns id of the item the entity walks into
     * @param entity studied entity
     * @return index of item in the objectManager array of the panel class
     */
    public int whichItem(Entity entity) {
        //the fact that the fragments are the only items in the second map simplified the logic
        //if more items were present, we would have to check if we encountered a fragment or something else
        int index;
        //we check which is the index of the item we encounter
        index = checksColItemArray(entity, panel.objectManager.items);
        //in map 1 we have different behaviors depending on the element we walked on
        if (index == -1 && panel.state == State.MAP2)
            index = checksColItemArray(entity, panel.objectManager.fragments.fragments);
        //in map 2 all tiles open the geoguesser, but we must know the index of the current item, since we might need to
        //reposition it later
        return index;
    }
}
