package fr.ht06.hspawn.Listeners;

import fr.ht06.hspawn.HSpawn;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

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

}
