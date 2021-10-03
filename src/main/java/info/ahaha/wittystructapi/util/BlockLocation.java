package info.ahaha.wittystructapi.util;

import org.bukkit.block.Block;

import java.io.Serializable;
import java.util.Objects;

import static org.bukkit.Bukkit.getServer;

public class BlockLocation implements Serializable {
    public BlockLocation(Block block) {
        x = block.getX();
        y = block.getY();
        z = block.getZ();
        worldName = block.getWorld().getName();
    }

    public BlockLocation(Vec v,String worldName) {
        byVec(v);
        this.worldName = worldName;
    }

    int x, y, z;
    String worldName;

    public Block toBlock(){
        return getServer().getWorld(worldName).getBlockAt(x,y,z);
    }

    public Block getRelativeBlock(Vec r){
        return getServer().getWorld(worldName).getBlockAt(x + r.getX(),y + r.getY(),z + r.getZ());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass())
            if (o instanceof Block)
                if (((Block) o).getX() == x && ((Block) o).getY() == y && ((Block) o).getZ() == z && ((Block) o).getWorld().getName().equals(worldName))
                    return true;
                else
                    return false;
        BlockLocation BlockLoc = (BlockLocation) o;
        return x == BlockLoc.x && y == BlockLoc.y && z == BlockLoc.z;
    }

    public Vec toVec(){
        return new Vec(x,y,z);
    }

    public BlockLocation byVec(Vec v){
        x = v.getX();
        y = v.getY();
        z = v.getZ();
        return this;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, z);
    }

    public int getZ() {
        return z;
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }

    public String getWorldName() {
        return worldName;
    }

    public void setZ(int z) {
        this.z = z;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setWorldName(String worldName) {
        this.worldName = worldName;
    }
}
