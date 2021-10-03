package info.ahaha.wittystructapi.event;

import info.ahaha.wittystructapi.struct.Blueprint;
import info.ahaha.wittystructapi.struct.Struct;
import info.ahaha.wittystructapi.struct.StructContainer;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class StructBreakEvent extends PlayerEvent implements Cancellable {
    static HandlerList handlerList = new HandlerList();

    StructContainer<? extends Struct,? extends Blueprint> container;
    Struct struct;
    Block breakBlock;
    boolean cancel = false, itemDrop = true;
    List<ItemStack> drops;

    public StructBreakEvent(Player who,StructContainer<? extends Struct,? extends Blueprint> container, Struct struct, Block block, List<ItemStack> drops) {
        super(who);
        this.container = container;
        this.struct = struct;
        breakBlock = block;
        this.drops = drops;
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }

    public Struct getStruct() {
        return struct;
    }

    public StructContainer<? extends Struct, ? extends Blueprint> getContainer() {
        return container;
    }

    public Block getBreakBlock() {
        return breakBlock;
    }

    public List<ItemStack> getDrops() {
        return drops;
    }

    public boolean isItemDrop() {
        return itemDrop;
    }

    public void setItemDrop(boolean itemDrop) {
        this.itemDrop = itemDrop;
    }

    public void setDrops(List<ItemStack> drops) {
        this.drops = drops;
    }

    @Override
    public boolean isCancelled() {
        return cancel;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.cancel = cancel;
    }
}
