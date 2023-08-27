package fr.ht06.hspawn.Listeners;

import fr.ht06.hspawn.HSpawn;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.Timer;
import java.util.TimerTask;

public class PlayerListeners implements Listener {

    private HSpawn main;
    public PlayerListeners(HSpawn main) {
        this.main = main;
    }


    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        event.setJoinMessage(main.getConfig().getString("Join").replace("[PLAYER]", player.getName()).replace("&", "§"));

    }
    @EventHandler
    public void onQuit(PlayerQuitEvent event){
        Player player = event.getPlayer();
        event.setQuitMessage(main.getConfig().getString("Quit").replace("[PLAYER]", player.getName()).replace("&", "§"));

    }

    @EventHandler
    public void onFirstJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        if (!player.hasPlayedBefore()){
            Bukkit.broadcastMessage(main.getConfig().getString("Message on first join").replace("[PLAYER]", player.getName()).replace("&", "§"));

            for (String string : main.getConfig().getStringList("Commands on first join")){
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), string.replace("[PLAYER]", player.getName()).replace("&", "§"));
            }

            if (main.getConfig().getBoolean("Player to spawn on first join")){
                String world = main.getConfig().getString("spawn.world");
                double x = main.getConfig().getDouble("spawn.x");
                double y = main.getConfig().getDouble("spawn.y");
                double z = main.getConfig().getDouble("spawn.z");
                float Yaw = (float) main.getConfig().getDouble("spawn.Yaw" );
                float Pitch = (float) main.getConfig().getDouble("spawn.Pitch");

                World World = Bukkit.getWorld(world);

                Location spawn = new Location(Bukkit.getWorld(world), x, y, z, Yaw, Pitch);
                player.teleport(spawn);
            }
        }
    }

}
