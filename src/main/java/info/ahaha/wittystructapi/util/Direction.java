package info.ahaha.wittystructapi.util;

import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;

import java.io.Serializable;

public enum Direction implements Serializable {
    NORTH, EAST, SOUTH, WEST, UP, DOWN, NORTH_EAST, NORTH_WEST, SOUTH_EAST, SOUTH_WEST, WEST_NORTH_WEST, NORTH_NORTH_WEST, NORTH_NORTH_EAST, EAST_NORTH_EAST, EAST_SOUTH_EAST, SOUTH_SOUTH_EAST, SOUTH_SOUTH_WEST, WEST_SOUTH_WEST, SELF;
    public enum CardinalDirection implements Serializable{
        NORTH(Direction.NORTH), EAST(Direction.EAST), SOUTH(Direction.SOUTH), WEST(Direction.WEST);
        CardinalDirection(Direction direction){
            this.direction = direction;
        }
        final Direction direction;

        public Direction getDirection() {
            return direction;
        }

        public static CardinalDirection byDirection(Direction direction){
            for(CardinalDirection d : CardinalDirection.values()){
                if(d.getDirection() == direction)
                    return d;
            }
            return null;
        }

        public static CardinalDirection byPlayer(Player player){
            float yaw = player.getLocation().getPitch();
            if(0 < yaw && yaw <= Math.PI / 2)
                return CardinalDirection.SOUTH;
            if(Math.PI / 2 < yaw && yaw <= Math.PI)
                return CardinalDirection.WEST;
            if(Math.PI < yaw && yaw <= Math.PI * 3.0 / 2)
                return CardinalDirection.NORTH;
            if(Math.PI * 3.0 / 2 < yaw && yaw <= Math.PI * 2)
                return CardinalDirection.EAST;
            return null;
        }
    }

    public BlockFace toBlockFace(){
        return getBlockFace(this);
    }

    public static Direction byBlockFace(BlockFace face){
        switch (face) {
            case NORTH:
                return Direction.NORTH;
            case EAST:
                return Direction.EAST;
            case SOUTH:
                return Direction.SOUTH;
            case WEST:
                return Direction.WEST;
            case UP:
                return Direction.UP;
            case DOWN:
                return Direction.DOWN;
            case NORTH_EAST:
                return Direction.NORTH_EAST;
            case NORTH_WEST:
                return Direction.NORTH_WEST;
            case SOUTH_EAST:
                return Direction.SOUTH_EAST;
            case SOUTH_WEST:
                return Direction.SOUTH_WEST;
            case WEST_NORTH_WEST:
                return Direction.WEST_NORTH_WEST;
            case NORTH_NORTH_WEST:
                return Direction.NORTH_NORTH_WEST;
            case NORTH_NORTH_EAST:
                return Direction.NORTH_NORTH_EAST;
            case EAST_NORTH_EAST:
                return Direction.EAST_NORTH_EAST;
            case EAST_SOUTH_EAST:
                return Direction.EAST_SOUTH_EAST;
            case SOUTH_SOUTH_EAST:
                return Direction.SOUTH_SOUTH_EAST;
            case SOUTH_SOUTH_WEST:
                return Direction.SOUTH_SOUTH_WEST;
            case WEST_SOUTH_WEST:
                return Direction.WEST_SOUTH_WEST;
            case SELF:
                return Direction.SELF;
        }
        return null;
    }

    public static BlockFace getBlockFace(Direction direction) {
        switch (direction) {
            case NORTH:
                return BlockFace.NORTH;
            case EAST:
                return BlockFace.EAST;
            case SOUTH:
                return BlockFace.SOUTH;
            case WEST:
                return BlockFace.WEST;
            case UP:
                return BlockFace.UP;
            case DOWN:
                return BlockFace.DOWN;
            case NORTH_EAST:
                return BlockFace.NORTH_EAST;
            case NORTH_WEST:
                return BlockFace.NORTH_WEST;
            case SOUTH_EAST:
                return BlockFace.SOUTH_EAST;
            case SOUTH_WEST:
                return BlockFace.SOUTH_WEST;
            case WEST_NORTH_WEST:
                return BlockFace.WEST_NORTH_WEST;
            case NORTH_NORTH_WEST:
                return BlockFace.NORTH_NORTH_WEST;
            case NORTH_NORTH_EAST:
                return BlockFace.NORTH_NORTH_EAST;
            case EAST_NORTH_EAST:
                return BlockFace.EAST_NORTH_EAST;
            case EAST_SOUTH_EAST:
                return BlockFace.EAST_SOUTH_EAST;
            case SOUTH_SOUTH_EAST:
                return BlockFace.SOUTH_SOUTH_EAST;
            case SOUTH_SOUTH_WEST:
                return BlockFace.SOUTH_SOUTH_WEST;
            case WEST_SOUTH_WEST:
                return BlockFace.WEST_SOUTH_WEST;
            case SELF:
                return BlockFace.SELF;
        }
        return null;
    }

    public boolean isCardinal(){
        switch (this){
            case WEST:
            case EAST:
            case SOUTH:
            case NORTH:
                return true;
        }
        return false;
    }

    public CardinalDirection toCardinal(){
        switch (this){
            case WEST:
                return CardinalDirection.WEST;
            case EAST:
                return CardinalDirection.EAST;
            case SOUTH:
                return CardinalDirection.SOUTH;
            case NORTH:
                return CardinalDirection.NORTH;
        }
        return null;
    }
}
