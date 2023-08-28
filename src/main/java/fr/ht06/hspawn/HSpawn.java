package fr.ht06.hspawn;

import fr.ht06.hspawn.Commands.CommandSpawn;
import fr.ht06.hspawn.Listeners.PlayerListeners;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class HSpawn extends JavaPlugin {

    @Override
    public void onEnable() {
        saveDefaultConfig();
        getCommand("spawn").setExecutor(new CommandSpawn(this));
        getCommand("setspawn").setExecutor(new CommandSpawn(this));
        getCommand("hsreload").setExecutor(this);
        getCommand("hspawn").setExecutor(this);
        getServer().getPluginManager().registerEvents(new PlayerListeners(this), this);

        System.out.println(getConfig().getList("on_first_join.messages"));
        
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

        if (cmd.getName().equalsIgnoreCase("hSpawn")){
            sender.sendMessage("§bhSpawn v1.5.1 create by §9§lht06");




        }

        return true;
    }

}
