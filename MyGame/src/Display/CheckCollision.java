package Display;

import Entity.Entity;
import GameState.State;
import Items.Item;

public class CheckCollision {
    Panel panel;

    public CheckCollision(Panel panel) {
        this.panel = panel;
    }

    //public void CheckItem(ObjectManager objectManager);
    public void checkTile(Entity entity) {

        int whichMap = 0;
        if(panel.state == State.MAP2)whichMap = 1;

        int leftX, rightX, topY, bottomY;//coordinates on the world map
        leftX = entity.mapX + entity.collisionX;
        rightX = entity.mapX + entity.collisionX + entity.widthCollision;
        topY = entity.mapY + entity.collisionY;
        bottomY = entity.mapY + entity.collisionY + entity.heightCollision;
        //System.out.println("here");
        if (!((leftX - entity.speed >= 0) && (rightX + entity.speed <= panel.map.wrldHeight[whichMap]) && (topY - entity.speed >= 0) && (bottomY + entity.speed <= panel.map.wrldWidth[whichMap])
        )) {
            System.out.println("here");
            //entity.collides=true;
            return;
        }

        //we have coordinates. we have to check if the tile we want to move into is available for moving (doesn't cause collision)
        // = > we need to know which tile(S) we are currently on
        //we can't be on more than 2 tiles =>
        int leftCol, rightCol, topRow, bottomRow;
        leftCol = leftX / panel.actualSize;
        rightCol = rightX / panel.actualSize;
        topRow = topY / panel.actualSize;
        bottomRow = bottomY / panel.actualSize;
        int movedIntoTile1, movedIntoTile2;
        //System.out.println(panel.currentMap);
        switch (entity.direction) {

            case LEFT:
                leftCol = (leftX - entity.speed) / panel.actualSize;
                movedIntoTile1 = panel.map.tilesManager[whichMap][leftCol][topRow];
                movedIntoTile2 = panel.map.tilesManager[whichMap][leftCol][bottomRow];
                if (panel.map.tiles[whichMap][movedIntoTile1].isCollision || panel.map.tiles[whichMap][movedIntoTile2].isCollision) {
                    entity.collides = true;
                }

                break;
            case RIGHT:
                rightCol = (rightX + entity.speed) / panel.actualSize;
                movedIntoTile1 = panel.map.tilesManager[whichMap][rightCol][topRow];
                movedIntoTile2 = panel.map.tilesManager[whichMap][rightCol][bottomRow];
                if (panel.map.tiles[whichMap][movedIntoTile1].isCollision || panel.map.tiles[whichMap][movedIntoTile2].isCollision) {
                    entity.collides = true;
                }
                break;
            case UP:
                topRow = (topY - entity.speed) / panel.actualSize;
                movedIntoTile1 = panel.map.tilesManager[whichMap][leftCol][topRow];
                movedIntoTile2 = panel.map.tilesManager[whichMap][rightCol][topRow];
                if (panel.map.tiles[whichMap][movedIntoTile1].isCollision || panel.map.tiles[whichMap][movedIntoTile2].isCollision) {
                    entity.collides = true;
                }
                break;
            case DOWN:
                bottomRow = (bottomY + entity.speed) / panel.actualSize;
                movedIntoTile1 = panel.map.tilesManager[whichMap][leftCol][bottomRow];
                movedIntoTile2 = panel.map.tilesManager[whichMap][rightCol][bottomRow];
                if (panel.map.tiles[whichMap][movedIntoTile1].isCollision || panel.map.tiles[whichMap][movedIntoTile2].isCollision) {
                    entity.collides = true;
                }
                break;

        }

    }

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
                            //System.out.println("Left collision");
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
                            //System.out.println("Right collision");
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
                            //System.out.println("UP collision");
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
                            //System.out.println("Down collision");
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

    public int whichItem(Entity entity) {
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
