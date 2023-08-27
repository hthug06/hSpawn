package fr.ht06.hspawn;

import fr.ht06.hspawn.Commands.CommandSpawn;
import fr.ht06.hspawn.Listeners.PlayerListeners;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.awt.*;
import java.util.List;

public final class HSpawn extends JavaPlugin {

    @Override
    public void onEnable() {
        saveDefaultConfig();
        getCommand("spawn").setExecutor(new CommandSpawn(this));
        getCommand("setspawn").setExecutor(new CommandSpawn(this));
        getCommand("hsreload").setExecutor(this);
        getServer().getPluginManager().registerEvents(new PlayerListeners(this), this);

        for (String string : getConfig().getStringList("Commands on first join")){
            System.out.println(string);
        }

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player player = (Player) sender;
        if (cmd.getName().equalsIgnoreCase("hsreload")){
            reloadConfig();
            sender.sendMessage("§9[§bhSpawn§9] §cConfig Reload");
        }

        return true;
    }

}
