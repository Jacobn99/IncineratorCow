package incineratorcow2.incineratorcow2;

import org.bukkit.Bukkit;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class MyEvents implements Listener {
    private JavaPlugin _mainPlugin;

    public MyEvents(JavaPlugin mainPlugin) {
        _mainPlugin = mainPlugin;
    }

    @EventHandler
    public void onHit(ProjectileHitEvent e) {
        Entity entity = e.getEntity();

        if (e.getEntity().getScoreboardTags().contains("GhostProjectile") && e.getHitEntity() != null) {
            Bukkit.broadcastMessage("Gaming");
            if (e.getHitEntity() instanceof Fireball) {
                Bukkit.broadcastMessage("YOOO");
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void cowHit(EntityDamageEvent event) {
        if (event.getEntity() instanceof Cow) {
            Cow cow = (Cow) event.getEntity();
            if (cow.getScoreboardTags().contains("God")) {
                if (event.getCause() == EntityDamageEvent.DamageCause.FALL || event.getCause() == EntityDamageEvent.DamageCause.SUFFOCATION) {
                    Bukkit.broadcastMessage("Yooo");
                    event.setCancelled(true);
                    //_incineratorCow.CowRecovery();
                } else if (event.getCause() == EntityDamageEvent.DamageCause.ENTITY_EXPLOSION) {
                    Bukkit.broadcastMessage("Yooo");
                    event.setCancelled(true);
                /*else if(cow.getScoreboardTags().contains("Invulnerability") && event.getCause() == EntityDamageEvent.DamageCause.ENTITY_EXPLOSION) {
                    cow.removeScoreboardTag("Invulnerability");
                    Bukkit.broadcastMessage("Yooo");
                    event.setCancelled(true);
                }*/
                }
            }
        }

    /*@EventHandler
    public void onCrash(EntityDamageEvent event) {
        if (event.getEntity() instanceof Cow) {
            Cow cow = (Cow) event.getEntity();
            if (event.getCause() == EntityDamageEvent.DamageCause.FALL && cow.getScoreboardTags().contains("God")) {
                Bukkit.broadcastMessage("Yooo");
                event.setCancelled(true);

                TNTPrimed tntPrimed = (TNTPrimed) Bukkit.getWorld("World").spawnEntity(cow.getLocation(),
                        EntityType.PRIMED_TNT);
                //cow.addScoreboardTag("TemporaryInvulnerability");
                tntPrimed.setFuseTicks(0);
            }
        }
    }*/
    }
}
