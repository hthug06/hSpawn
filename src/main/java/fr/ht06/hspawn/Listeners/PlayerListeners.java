package fr.ht06.hspawn.Listeners;

import fr.ht06.hspawn.HSpawn;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerListeners implements Listener {

    private HSpawn main;
    public PlayerListeners(HSpawn main) {
        this.main = main;
    }

    public void onJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        event.setJoinMessage(main.getConfig().getString("Join").replace("[PLAYER]", player.getName()).replace("&", "§"));
    }

}
