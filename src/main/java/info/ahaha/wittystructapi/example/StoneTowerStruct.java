package info.ahaha.wittystructapi.example;

import info.ahaha.wittystructapi.struct.Blueprint;
import info.ahaha.wittystructapi.struct.Struct;
import info.ahaha.wittystructapi.util.BlockLocation;
import info.ahaha.wittystructapi.util.Direction;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

public class StoneTowerStruct implements Serializable, Struct {
    BlockLocation center;
    Direction direction;
    
    public StoneTowerStruct(BlockLocation block,Direction direction){
        this.center = block;
        this.direction = direction;
    }

    @Override
    public boolean containsBlock(Block block) {
        return ExampleBlueprints.StoneTower.getBlocks(center.toBlock(), direction.toBlockFace()).contains(block);
    }

    @Override
    public List<BlockLocation> getBlocks() {
        return ExampleBlueprints.StoneTower.getRelativeBlocksLocations(direction.toBlockFace()).stream().map(v -> new BlockLocation(center.toVec().add(v),center.getWorldName())).collect(Collectors.toList());
    }

    @Override
    public Direction getDirection() {
        return direction;
    }

    @Override
    public Blueprint getBlueprint() {
        return ExampleBlueprints.StoneTower;
    }

    @Override
    public BlockLocation getCenter() {
        return center;
    }

    @Override
    public void remove() {
        for(Block block : ExampleBlueprints.StoneTower.getBlocks(center.toBlock(), direction.toBlockFace()))
            block.setType(Material.AIR);
    }
}
