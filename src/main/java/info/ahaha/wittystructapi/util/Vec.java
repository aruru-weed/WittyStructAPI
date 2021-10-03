package info.ahaha.wittystructapi.util;

import org.bukkit.Location;

import java.io.Serializable;

public class Vec implements Serializable {
    public Vec(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vec(Location loc) {
        x = (int) loc.getX();
        y = (int) loc.getY();
        z = (int) loc.getZ();
    }

    private int x, y, z;

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setZ(int z) {
        this.z = z;
    }

    public void flip(Direction.CardinalDirection direction) {
        switch (direction) {
            case NORTH:
            case SOUTH:
                z *= -1;
                break;
            case EAST:
            case WEST:
                x *= -1;
                break;
        }
    }

    public void rotate() {

    }

    public Vec relative(Vec origin) {
        return new Vec(x - origin.x, y - origin.y, z - origin.z);
    }

    public Vec add(Vec v) {
        x += v.x;
        y += v.y;
        z += v.z;
        return this;
    }

    public Vec sub(Vec v) {
        x -= v.x;
        y -= v.y;
        z -= v.z;
        return v;
    }
}
