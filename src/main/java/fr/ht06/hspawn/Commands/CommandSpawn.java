package fr.ht06.hspawn.Commands;

import fr.ht06.hspawn.HSpawn;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class CommandSpawn implements CommandExecutor {

    private HSpawn main;
    public CommandSpawn(HSpawn main) {
        this.main = main;
    }


    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
        Player player = (Player) sender;
        Location ploc = player.getLocation();

        FileConfiguration config = main.getConfig();

        if (cmd.getName().equalsIgnoreCase("setSpawn")){
            config.set("spawn.world", ploc.getWorld().getName());
            config.set("spawn.x", ploc.getX());
            config.set("spawn.y", ploc.getY());
            config.set("spawn.z", ploc.getZ());
            config.set("spawn.Yaw", ploc.getYaw());
            config.set("spawn.Pitch", ploc.getPitch());
            config = main.getConfig();
            config.options().copyDefaults(true);
            main.saveConfig();


            player.sendMessage(main.getConfig().getString("Message.Setspawn").replace("[PLAYER]", player.getName()).replace("&", "§"));
        }

        if (cmd.getName().equalsIgnoreCase("Spawn")){

            if(config.getString("spawn.world") == null){
                Bukkit.getLogger().severe("Le world n'est pas configuré dans config.yml");
                return false;
            }

            if (args.length == 0){
                String world = config.getString("spawn.world");
                //main.FallDamageEnable = true;
                double x = config.getDouble("spawn.x");
                double y = config.getDouble("spawn.y");
                double z = config.getDouble("spawn.z");
                float Yaw = (float) config.getDouble("spawn.Yaw" );
                float Pitch = (float) config.getDouble("spawn.Pitch");


                Location spawn;
                spawn = new Location(Bukkit.getWorld(world), x, y, z, Yaw, Pitch);

                player.teleport(spawn);

                if(main.getConfig().getBoolean("playsound-on-/spawn")){
                    Sound sound = Sound.valueOf(main.getConfig().getString("SoundName").toUpperCase());
                    player.playSound(spawn, Sound.ENTITY_PLAYER_LEVELUP, 1.0f, 1.0f);
                }

                player.sendMessage(main.getConfig().getString("Message.TpToSpawn").replace("[PLAYER]", player.getName()).replace("&", "§"));
            }

            if((args.length >= 1) && player.hasPermission(main.getConfig().getString("Permission--tp-player-to-spawn"))){
                String targetName = args[0];

                if (Bukkit.getPlayer(targetName) != null){

                    Player target = Bukkit.getPlayer(targetName);

                    String world = config.getString("spawn.world");
                    double x = config.getDouble("spawn.x");
                    double y = config.getDouble("spawn.y");
                    double z = config.getDouble("spawn.z");
                    float Yaw = (float) config.getDouble("spawn.Yaw" );
                    float Pitch = (float) config.getDouble("spawn.Pitch");

                    World World = Bukkit.getWorld(world);

                    Location spawn = new Location(Bukkit.getWorld(world), x, y, z, Yaw, Pitch);
                    Location spawn1 = new Location(Bukkit.getWorld(world), x, World.getHighestBlockYAt(spawn), z, Yaw, Pitch);

                    //msg to target Player
                    target.sendMessage(main.getConfig().getString("Message.TpToSpawnbyAdmin").replace("[PLAYER]", player.getName()).replace("&", "§"));

                    target.teleport(spawn1);
                    player.playSound(spawn, Sound.ENTITY_PLAYER_LEVELUP, 1.0f, 1.0f);

                    //message to player who tp the target
                    player.sendMessage(targetName + "§6 has been teleported to the Spawn");
                }

            }



            /*String world = config.getString("spawn.world");
            int x = config.getInt("spawn.x");
            int y = config.getInt("spawn.y");
            int z = config.getInt("spawn.z");

            Location spawn;
            spawn = new Location(Bukkit.getWorld(world), x, y, z);
            player.teleport(spawn);
            player.sendMessage("Vous venez d'être téléporter au spawn");*/
        }



        return true;
    }


}