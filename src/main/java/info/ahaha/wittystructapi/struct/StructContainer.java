package info.ahaha.wittystructapi.struct;

import info.ahaha.wittystructapi.event.StructBreakEvent;
import info.ahaha.wittystructapi.event.StructPlaceEvent;
import info.ahaha.wittystructapi.exception.NoSupportedDirectionException;
import info.ahaha.wittystructapi.util.BlockLocation;
import org.bukkit.block.Block;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.bukkit.Bukkit.getPluginManager;
import static org.bukkit.Bukkit.getServer;

public class StructContainer<T extends Struct, B extends Blueprint> implements Serializable {
    List<T> list = new ArrayList<>();
    B b;
    transient StructListeners structListeners = null;

    public StructContainer(B b) {
        this.b = b;
    }

    public B getBlueprint() {
        return b;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public void add(T t) {
        list.add(t);
    }

    public boolean contains(Block block) {
        for (Struct s : list) {
            if (s.containsBlock(block))
                return true;
        }
        return false;
    }

    public T get(Block block) {
        for (T s : list) {
            if (s.containsBlock(block))
                return s;
        }
        return null;
    }

    public T get(BlockLocation blockloc) {
        Block block = blockloc.toBlock();
        for (T s : list) {
            if (s.containsBlock(block))
                return s;
        }
        return null;
    }

    protected StructContainer<T, B> outer() {
        return this;
    }

    public StructListeners listeners() {
        if (structListeners == null)
            structListeners = new StructListeners();
        return structListeners;
    }

    public class StructListeners {
        ItemStack keyItem = null;

        public StructListeners() {
        }

        public StructListeners(ItemStack keyItem) {
            this.keyItem = keyItem;
        }

        public void setKeyItem(ItemStack keyItem) {
            this.keyItem = keyItem;
        }

        public ItemStack getKeyItem() {
            return keyItem;
        }

        public boolean hasKeyItem() {
            return keyItem != null;
        }

        public Listener registerBreakListener(Plugin p) {
            Listener l = new KeyItemBlockBreakListener();
            getServer().getPluginManager().registerEvents(l, p);
            return l;
        }

        public Listener registerBreakListener(Plugin p, List<ItemStack> stack) {
            Listener l = new ItemListBlockBreakListener(stack);
            getServer().getPluginManager().registerEvents(l, p);
            return l;
        }

        public Listener registerBreakListener(Plugin p, JudgeBlockBreakFunction<T, B> f) {
            Listener l = new FunctionalBlockBreakListener(f);
            getServer().getPluginManager().registerEvents(l, p);
            return l;
        }

        public Listener registerNoCheckPlaceListener(Plugin p) {
            Listener l = new CanPlaceBlockPlaceListener();
            getServer().getPluginManager().registerEvents(l, p);
            return l;
        }

        public Listener registerPlaceListener(Plugin p) {
            Listener l = new KeyItemIsSimilarBlockPlaceListener();
            getServer().getPluginManager().registerEvents(l, p);
            return l;
        }

        public Listener registerPlaceListener(Plugin p, JudgeBlockPlaceFunction<T, B> f) {
            Listener l = new FunctionalBlockPlaceListener(f);
            getServer().getPluginManager().registerEvents(l, p);
            return l;
        }

        private boolean callEvent(BlockBreakEvent e, T t) {
            StructBreakEvent event = null;
            if (keyItem != null)
                event = new StructBreakEvent(e.getPlayer(), outer(), t, e.getBlock(), new ArrayList<>(Collections.singleton(keyItem)));
            else {
                ArrayList<ItemStack> d = new ArrayList<>();
                for (BlockLocation b : t.getBlocks())
                    d.addAll(b.toBlock().getDrops());
                event = new StructBreakEvent(e.getPlayer(), outer(), t, e.getBlock(), d);
            }
            getServer().getPluginManager().callEvent(event);
            if (event.isCancelled()) {
                e.setCancelled(true);
                return false;
            }
            e.setDropItems(false);
            for (ItemStack stack : event.getDrops()) {
                Item item = e.getBlock().getWorld().dropItem(e.getPlayer().getLocation(), stack);
                item.setOwner(e.getPlayer().getUniqueId());
            }
            t.remove();
            outer().list.remove(t);
            return true;
        }

        public class KeyItemBlockBreakListener implements Listener {
            @EventHandler
            public void on(BlockBreakEvent e) {
                T t = get(e.getBlock());
                if (t == null)
                    return;
                callEvent(e, t);
            }
        }

        public class ItemListBlockBreakListener implements Listener {
            List<ItemStack> items;

            public ItemListBlockBreakListener(List<ItemStack> items) {
                this.items = items;
            }

            @EventHandler
            public void on(BlockBreakEvent e) {
                T t = get(e.getBlock());
                if (t == null)
                    return;
                StructBreakEvent event = new StructBreakEvent(e.getPlayer(), outer(), t, e.getBlock(), items);
                getServer().getPluginManager().callEvent(event);
                if (event.isCancelled()) {
                    e.setCancelled(true);
                    return;
                }
                e.setDropItems(false);
                for (ItemStack stack : event.getDrops()) {
                    Item item = e.getBlock().getWorld().dropItem(e.getPlayer().getLocation(), stack);
                    item.setOwner(e.getPlayer().getUniqueId());
                }
                t.remove();
                outer().list.remove(t);
            }
        }


        public class FunctionalBlockBreakListener implements Listener {
            JudgeBlockBreakFunction<T, B> f;

            public FunctionalBlockBreakListener(JudgeBlockBreakFunction<T, B> f) {
                this.f = f;
            }

            @EventHandler
            public void on(BlockBreakEvent e) {
                T t = get(e.getBlock());
                if (t == null)
                    return;
                JudgeBlockBreakFunction.JudgeResult result = f.f(e, outer());
                if (!result.judge)
                    return;
                if (!result.dropsUsing) {
                    callEvent(e, t);
                    return;
                } else {
                    StructBreakEvent event = new StructBreakEvent(e.getPlayer(), outer(), t, e.getBlock(), result.drops);
                    getServer().getPluginManager().callEvent(event);
                    if (event.isCancelled()) {
                        e.setCancelled(true);
                        return;
                    }
                    e.setDropItems(false);
                    for (ItemStack stack : event.getDrops()) {
                        Item item = e.getBlock().getWorld().dropItem(e.getPlayer().getLocation(), stack);
                        item.setOwner(e.getPlayer().getUniqueId());
                    }
                    t.remove();
                    outer().list.remove(t);
                }
            }
        }

        public class KeyItemIsSimilarBlockPlaceListener implements Listener {
            @EventHandler
            public void on(BlockPlaceEvent e) {
                if (!e.getItemInHand().isSimilar(keyItem))
                    return;
                try {
                    if (!b.canPlace(e.getBlock(), e.getPlayer().getFacing()))
                        return;
                    e.setCancelled(true);
                    outer().add((T) b.place(e.getBlock(), e.getPlayer().getFacing()));
                } catch (NoSupportedDirectionException exception) {
                    e.getPlayer().sendMessage("No supported Direction.(対応していない向きです)");
                }
            }
        }

        public class CanPlaceBlockPlaceListener implements Listener {
            @EventHandler
            public void on(BlockPlaceEvent e) {
                try {
                    if (!outer().getBlueprint().canPlace(e.getBlock(), e.getPlayer().getFacing()))
                        return;
                    StructPlaceEvent event = new StructPlaceEvent(e.getPlayer(), outer(), e.getBlock(), b, e.getItemInHand());
                    getPluginManager().callEvent(event);
                    if (event.isCancelled()) {
                        return;
                    }
                    e.setCancelled(true);
                    outer().add((T) b.place(e.getBlock(), e.getPlayer().getFacing()));
                } catch (NoSupportedDirectionException exception) {
                    e.getPlayer().sendMessage("No supported Direction.(対応していない向きです)");
                    return;
                }
            }
        }

        public class FunctionalBlockPlaceListener implements Listener {
            JudgeBlockPlaceFunction<T, B> f;

            public FunctionalBlockPlaceListener(JudgeBlockPlaceFunction<T, B> f) {
                this.f = f;
            }

            @EventHandler
            public void on(BlockPlaceEvent e) {
                if (f.f(e, outer())) {
                    try {
                        if (!outer().getBlueprint().canPlace(e.getBlock(), e.getPlayer().getFacing()))
                            return;
                        StructPlaceEvent event = new StructPlaceEvent(e.getPlayer(), outer(), e.getBlock(), b, e.getItemInHand());
                        getPluginManager().callEvent(event);
                        if (event.isCancelled()) {
                            return;
                        }
                        e.setCancelled(true);
                        outer().add((T) b.place(e.getBlock(), e.getPlayer().getFacing()));
                    } catch (NoSupportedDirectionException exception) {
                        e.getPlayer().sendMessage("No supported Direction.(対応していない向きです)");
                    }
                }
            }
        }
    }

    @FunctionalInterface
    interface JudgeBlockPlaceFunction<T extends Struct, B extends Blueprint> {
        boolean f(BlockPlaceEvent e, StructContainer<T, B> container);
    }

    @FunctionalInterface
    interface JudgeBlockBreakFunction<T extends Struct, B extends Blueprint> {
        class JudgeResult {
            public JudgeResult(boolean judge) {
                this.judge = judge;
            }

            public JudgeResult(boolean judge, List<ItemStack> drops) {
                this.judge = judge;
                this.drops = drops;
                dropsUsing = true;
            }

            public boolean judge = true, dropsUsing;
            public List<ItemStack> drops;
        }

        JudgeResult f(BlockBreakEvent e, StructContainer<T, B> container);
    }
}
