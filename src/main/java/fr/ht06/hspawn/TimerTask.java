package fr.ht06.hspawn;

import org.bukkit.scheduler.BukkitRunnable;

public class TimerTask extends BukkitRunnable {


    //Pour l'utliser :
    /*TimerTask task = new TimerTask();
    task.runTaskTimer(main, 0, 1);*/

    private int timer = 1;

    @Override
    public void run() {

        if (timer == 0){
            cancel();
        }

        timer--;
    }
}
