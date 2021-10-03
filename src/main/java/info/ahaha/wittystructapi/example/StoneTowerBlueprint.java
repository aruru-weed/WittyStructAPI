package info.ahaha.wittystructapi.example;

import info.ahaha.wittystructapi.struct.Blueprint;
import info.ahaha.wittystructapi.struct.Struct;
import info.ahaha.wittystructapi.util.BlockLocation;
import info.ahaha.wittystructapi.util.Direction;
import info.ahaha.wittystructapi.util.Vec;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

import java.util.ArrayList;
import java.util.List;

public class StoneTowerBlueprint implements Blueprint {
    @Override
    public List<Block> getBlocks(Block center, BlockFace face) {
        List<Block> r = new ArrayList<>();
        for (int y = 0; y < 26; y++)
            for (int i = -1; i < 2; i++) {
                r.add(center.getRelative(i, y, -2));
                r.add(center.getRelative(i, y, 2));
                r.add(center.getRelative(2, y, i));
                r.add(center.getRelative(-2, y, i));
            }
        for (int y = 5; y < 26; y += 5)
            for (int i = -1; i < 2; i++)
                for (int j = -1; j < 2; j++)
                    r.add(center.getRelative(i, y, j));
        return r;
    }

    public List<Vec> getRelativeBlocksLocations(BlockFace face) {
        List<Vec> r = new ArrayList<>();
        for (int y = 0; y < 26; y++)
            for (int i = -1; i < 2; i++) {
                r.add(new Vec(i, y, -2));
                r.add(new Vec(i, y, 2));
                r.add(new Vec(2, y, i));
                r.add(new Vec(-2, y, i));
            }
        for (int y = 5; y < 26; y += 5)
            for (int i = -1; i < 2; i++)
                for (int j = -1; j < 2; j++)
                    r.add(new Vec(i, y, j));
        return r;
    }

    @Override
    public boolean equalStruct(Block center, BlockFace face) {
        for (Block block : getBlocks(center, face))
            if (block.getType() != Material.COBBLESTONE)
                return false;
        return true;
    }

    @Override
    public boolean canPlace(Block center, BlockFace face) {
        for (Block block : getBlocks(center, face))
            if (block.getType() != Material.AIR)
                return false;
        return true;
    }

    @Override
    public Struct place(Block center, BlockFace face) {
        for (Block block : getBlocks(center, face)) {
            block.setType(Material.COBBLESTONE);
        }
        return new StoneTowerStruct(new BlockLocation(center), Direction.byBlockFace(face));
    }

    @Override
    public String getName() {
        return "StoneTower";
    }
}
