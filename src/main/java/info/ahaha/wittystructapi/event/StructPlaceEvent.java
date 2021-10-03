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

public class StructPlaceEvent extends PlayerEvent implements Cancellable {
    static HandlerList handlerList = new HandlerList();

    Block placeBlock;
    boolean cancel = false, itemDrop = true;
    Blueprint blueprint;
    ItemStack item;
    StructContainer<? extends Struct,? extends Blueprint> container;

    public StructPlaceEvent(Player who,StructContainer<? extends Struct,? extends Blueprint> container, Block block, Blueprint blueprint,ItemStack item) {
        super(who);
        this.item = item;
        this.container = container;
        this.blueprint = blueprint;
        placeBlock = block;
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }

    public Block getPlaceBlock() {
        return placeBlock;
    }

    public Blueprint getBlueprint() {
        return blueprint;
    }

    public StructContainer<? extends Struct, ? extends Blueprint> getContainer() {
        return container;
    }

    public boolean isItemDrop() {
        return itemDrop;
    }

    public void setItemDrop(boolean itemDrop) {
        this.itemDrop = itemDrop;
    }

    public ItemStack getItem() {
        return item;
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
