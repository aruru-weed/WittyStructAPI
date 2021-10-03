package info.ahaha.wittystructapi.struct;

import info.ahaha.wittystructapi.exception.NoSupportedDirectionException;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

import java.io.Serializable;
import java.util.List;

public interface Blueprint extends Serializable {
    List<Block> getBlocks(Block center, BlockFace face) throws NoSupportedDirectionException;

    boolean equalStruct(Block center, BlockFace face) throws NoSupportedDirectionException;

    boolean canPlace(Block center, BlockFace face) throws NoSupportedDirectionException;

    Struct place(Block center, BlockFace face) throws NoSupportedDirectionException;

    String getName();
}
