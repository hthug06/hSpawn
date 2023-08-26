package fr.ht06.hspawn;

import fr.ht06.hspawn.Commands.CommandSpawn;
import org.bukkit.plugin.java.JavaPlugin;

public final class HSpawn extends JavaPlugin {

    @Override
    public void onEnable() {
        saveDefaultConfig();
        getCommand("spawn").setExecutor(new CommandSpawn(this));
        getCommand("setspawn").setExecutor(new CommandSpawn(this));

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
