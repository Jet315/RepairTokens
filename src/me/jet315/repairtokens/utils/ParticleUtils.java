package me.jet315.repairtokens.utils;

import me.jet315.repairtokens.Core;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;

public class ParticleUtils {

    public static ArrayList<Location> generateHelix(Player p){
        ArrayList<Location> locations = new ArrayList<>();
        Location loc = p.getLocation();
        for(double y = 0; y <= 10; y+=0.1) {
            double x = 1.5 * Math.cos(y);
            double z = 1.5 * Math.sin(y);
            locations.add(new Location(loc.getWorld(),loc.getX() + x, loc.getY() + y/4, loc.getZ() +z));
        }
        return locations;
    }


    public static void spawnParticles(ArrayList<Location> locationsToSpawnParticles,Player p){
        new BukkitRunnable(){

            @Override
            public void run() {
                for(int i = 0; i < locationsToSpawnParticles.size() ; i++){
                    if(i == 7){
                        break;
                    }
                    Location loc = locationsToSpawnParticles.get(i);
                    locationsToSpawnParticles.remove(i);
                    if(Core.serverVersion.startsWith("v1_8")) {
                        p.playEffect(loc, Effect.CLOUD, 1);
                    }else {

                        loc.getWorld().spawnParticle(Particle.CLOUD, loc, 1,0,0,0,0,null);
                    }
                }
                if(locationsToSpawnParticles.size() ==0){
                    cancel();
                }

      /*          if(locationsToSpawnParticles.size() > 0){
                    Location loc = locationsToSpawnParticles.get(0);

                    if(!Core.serverVersion.startsWith("v1_12")) {
                        p.playEffect(loc, Effect.CLOUD, 1);
                    }else {
                        loc.getWorld().spawnParticle(Particle.CLOUD, loc, 1,null);
                    }
                    locationsToSpawnParticles.remove(0);
                }else{
                    cancel();
                    return;
                }*/
            }
        }.runTaskTimer(Core.getInstance(),0L,1L);
    }
}
