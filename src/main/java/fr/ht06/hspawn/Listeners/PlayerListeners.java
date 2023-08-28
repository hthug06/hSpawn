package fr.ht06.hspawn.Listeners;

import fr.ht06.hspawn.HSpawn;
import fr.ht06.hspawn.TimerTask;
import org.bukkit.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashSet;
import java.util.List;
import java.util.Timer;
import java.util.UUID;

public class PlayerListeners implements Listener {

    private HSpawn main;
    public PlayerListeners(HSpawn main) {
        this.main = main;
    }

    public static HashSet<UUID> preventFallDamages = new HashSet<>();


    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        event.setJoinMessage(main.getConfig().getString("Join").replace("[PLAYER]", player.getName()).replace("&", "§"));

        if (main.getConfig().getBoolean("Spawn on join")){
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

    @EventHandler
    public void onQuit(PlayerQuitEvent event){
        Player player = event.getPlayer();
        event.setQuitMessage(main.getConfig().getString("Quit").replace("[PLAYER]", player.getName()).replace("&", "§"));

    }

    @EventHandler
    public void onFirstJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        if (!player.hasPlayedBefore()){
            //The message on first join
            String msg = (String) main.getConfig().getList("on_first_join.messages").get(0);
            Bukkit.broadcastMessage(msg.replace("[PLAYER]", player.getName()).replace("&", "§"));

            //Give Item in custom quantity on first join
            //create everything for the foreach
            List list ;
            int nbrOfBlock ;
            String blockName ;
            String blockDisplayName;
            ItemStack it ;


            //print everything in the config
            list = main.getConfig().getList("on_first_join.items");
            for (int i = 0; i < list.size(); i++){
                nbrOfBlock = (int) main.getConfig().getMapList("on_first_join.items").get(i).get("count");
                blockName = (String) main.getConfig().getMapList("on_first_join.items").get(i).get("material");
                it = new ItemStack(Material.valueOf(blockName.replace("[PLAYER]", player.getName()).replace("&", "§")), nbrOfBlock);
                blockDisplayName = (String) main.getConfig().getMapList("on_first_join.items").get(i).get("name");
                ItemMeta im = it.getItemMeta();
                im.setDisplayName(blockDisplayName.replace("[PLAYER]", player.getName()).replace("&", "§"));
                it.setItemMeta(im);
                player.getInventory().addItem(it);
            }

            String message;

            list = main.getConfig().getList("on_first_join.messages");
            for (int i = 0; i < list.size(); i++){
                message = (String) list.get(i);
                player.sendMessage(message.replace("[PLAYER]", player.getName()).replace("&", "§"));
            }

            String command;

            list = main.getConfig().getList("on_first_join.commands");
            for (int i = 0; i < list.size(); i++){
                command = (String) list.get(i);
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command.replace("[PLAYER]", player.getName()).replace("&", "§"));
            }




            //Pour tp au spawn
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

    @EventHandler
    public void onMove(PlayerMoveEvent event){
        Player player = event.getPlayer();
        Location ploc = player.getLocation();
        if (!main.getConfig().getBoolean("Void to spawn")) return;
        if (ploc.getY() <= -70){

            String world = main.getConfig().getString("spawn.world");
            double x = main.getConfig().getDouble("spawn.x");
            double y = main.getConfig().getDouble("spawn.y");
            double z = main.getConfig().getDouble("spawn.z");
            float Yaw = (float) main.getConfig().getDouble("spawn.Yaw" );
            float Pitch = (float) main.getConfig().getDouble("spawn.Pitch");

            World World = Bukkit.getWorld(world);

            Location spawn = new Location(Bukkit.getWorld(world), x, y, z, Yaw, Pitch);

            preventFallDamages.add(player.getUniqueId());

            player.teleport(spawn);


        }
    }


    //Pour annuler les dégat de chute
    @EventHandler
    public void onFall(EntityDamageEvent e) {
        if(e.getCause()!= EntityDamageEvent.DamageCause.FALL)return;

        if(preventFallDamages.contains(e.getEntity().getUniqueId())){
            e.setCancelled(true);
            //Remove it from the list if you want
            preventFallDamages.remove(e.getEntity().getUniqueId());
        }
    }

    @EventHandler
    public void onDeath(PlayerRespawnEvent event){
        Player player = event.getPlayer();
        if (main.getConfig().getBoolean("Spawn on death")){
            String world = main.getConfig().getString("spawn.world");
            double x = main.getConfig().getDouble("spawn.x");
            double y = main.getConfig().getDouble("spawn.y");
            double z = main.getConfig().getDouble("spawn.z");
            float Yaw = (float) main.getConfig().getDouble("spawn.Yaw" );
            float Pitch = (float) main.getConfig().getDouble("spawn.Pitch");

            World World = Bukkit.getWorld(world);

            Location spawn = new Location(World, x, y, z, Yaw, Pitch);

            event.setRespawnLocation(spawn);
        }

       // }


    }

}
