package info.ahaha.wittystructapi;

import info.ahaha.wittystructapi.example.ExampleBlueprints;
import info.ahaha.wittystructapi.example.StoneTowerBlueprint;
import info.ahaha.wittystructapi.example.StoneTowerStruct;
import info.ahaha.wittystructapi.struct.StructContainer;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public final class WittyStructAPI extends JavaPlugin {
    StructContainer<StoneTowerStruct, StoneTowerBlueprint> container = new StructContainer<>(ExampleBlueprints.StoneTower);

    @Override
    public void onEnable() {
        try {
            ObjectInputStream i = new ObjectInputStream(new FileInputStream("plugins/WittyStructAPI/StoneTower.data"));
            container = (StructContainer<StoneTowerStruct, StoneTowerBlueprint>) i.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        // Plugin startup logic
        container.listeners().setKeyItem(new ItemStack(Material.COBBLESTONE));
        container.listeners().registerPlaceListener(this);
        container.listeners().registerBreakListener(this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        Path path = Paths.get("plugins/WittyStructAPI");
        if (!Files.exists(path)) {
            try {
                Files.createDirectory(path);//Make WWarpSystem
            } catch (IOException e) {
                getLogger().info(e.toString());
            }
        }
        try {
            File file = new File("plugins/WittyStructAPI/StoneTower.data");
            if (!file.exists())
                Files.createFile(file.toPath());
            ObjectOutputStream o = new ObjectOutputStream(new FileOutputStream(file));
            o.writeObject(container);
            o.flush();
            o.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
