package info.ahaha.wittystructapi.struct;

import info.ahaha.wittystructapi.util.BlockLocation;
import info.ahaha.wittystructapi.util.Direction;
import info.ahaha.wittystructapi.util.Vec;
import org.bukkit.block.Block;

import java.io.Serializable;
import java.util.List;

public interface Struct extends Serializable {
    boolean containsBlock(Block block);

    default Vec relative_to_center(Block block){
        return new Vec(block.getLocation()).relative(getCenter().toVec());
    }

    List<BlockLocation> getBlocks();

    Direction getDirection();

    Blueprint getBlueprint();

    default Class<? extends Blueprint> getBlueprintType(){
        return getBlueprint().getClass();
    }

    BlockLocation getCenter();

    void remove();
}
