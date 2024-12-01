package incineratorcow2.incineratorcow2;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.*;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.*;

public class AttackCharge extends IncineratorCow {
    private JavaPlugin _mainPlugin;
    Cow _myCow;

    Random rand = new Random();
    //ArrayList<Player> nearbyPlayers = new ArrayList<Player>();

    public AttackCharge(JavaPlugin mainPlugin, Cow myCow) {
        super(mainPlugin, myCow);
        _mainPlugin = mainPlugin;
        _myCow = myCow;
    }

    public void Charge() {
        Bukkit.broadcastMessage("Charge!");
        Player target = GetRandomPlayer(30);
        if(target == null) {
            Bukkit.broadcastMessage("Canceling Charge!");
            CancelAbility(false);
            return;
        }

        EntityFacePlayer(_myCow, target, false);
        Location targetLoc = target.getLocation();
        Vector loc1 = _myCow.getLocation().toVector();
        Vector loc2 = targetLoc .toVector();
        loc2.setY(targetLoc.getY() + 1);
        Vector direction = (loc2.subtract(loc1).normalize());

        _myCow.setGravity(true);
        _myCow.setVelocity((direction).multiply(3.5));
        DetectEntityCrash(_myCow);
    }

    private void DetectEntityCrash(Entity e) {
        Location cowLocation = _myCow.getLocation();

        new BukkitRunnable() {
            boolean isHit = false;
            @Override
            public void run() {
                List<Entity> nearbyMobs;
                Collection<Fireball> Fireballs;
                Collection<Arrow> Arrows;

                nearbyMobs = e.getNearbyEntities(0.5, 0.5, 0.5);
                Fireballs = Bukkit.getWorld("World").getEntitiesByClass(Fireball.class);
                Arrows = Bukkit.getWorld("World").getEntitiesByClass(Arrow.class);

                //Ensures mobs detected to be near incinerator cow aren't fireballs
                for(Entity entity : Fireballs) {
                    if(nearbyMobs.contains(e)) {
                        nearbyMobs.remove(e);
                    }
                }
                //Ensures mobs detected to be near incinerator cow aren't arrows
                for(Entity entity : Arrows) {
                    if(nearbyMobs.contains(e)) {
                        nearbyMobs.remove(e);
                    }
                }

                //Causes explosion if incinerator cow hit an entity
                if(nearbyMobs.size() > 0 && !(e.isDead()) && !isHit) {
                    TNTPrimed tntPrimed = (TNTPrimed) Bukkit.getWorld("World").spawnEntity(e.getLocation(),
                            EntityType.PRIMED_TNT);
                    tntPrimed.setFuseTicks(0);
                    isHit = true;
                }
                //Causes explosion if incinerator cow hits blocks
                if(checkSurroundingBlocks(e, 1.0)) {
                    if(!isHit) {
                        TNTPrimed tntPrimed = (TNTPrimed) Bukkit.getWorld("World").spawnEntity(e.getLocation(),
                                EntityType.PRIMED_TNT);
                        tntPrimed.setFuseTicks(0);
                    }
                    this.cancel();
                    CowRecovery();
                }
            }
        }.runTaskTimer(_mainPlugin, 0, 1);
    }

    private void CowRecovery() {
        _myCow.setCustomName("Dinnerbone");
        final int taskID = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(_mainPlugin, new Runnable() {
            int heightClimbed = 0;
            boolean isInitialized = false;

            @Override
            public void run() {
                if (!isInitialized)
                {
                    isInitialized = true;
                    Location targetLoc = _myCow.getLocation();
                    Location cowLoc = _myCow.getLocation();
                    Location up = new Location(Bukkit.getWorld("World"), cowLoc.getX(), cowLoc.getY() + 1, cowLoc.getZ());
                    targetLoc.setY(targetLoc.getY() + rand.nextInt(5));
                    PotionEffect potionEffect = new PotionEffect(PotionEffectType.LEVITATION, 15, 4, true, false);

                    Bukkit.broadcastMessage("Stopped");
                    _myCow.setCustomName(null);
                    _myCow.setGravity(false);
                    _myCow.setVelocity(stop);
                    _myCow.teleport(up);
                    _myCow.addPotionEffect(potionEffect);
                }

                if(heightClimbed <= 40)
                {
                    RemoveNearbyArea(_myCow, 4, 3, 2);
                    heightClimbed++;
                }
                else if (heightClimbed == 41)
                {
                    Bukkit.broadcastMessage("Terminating Cow Recovery");
                    heightClimbed++;
                    _myCow.addScoreboardTag("MaxHeight");
                }

            }
        }, 100, 3);

        new BukkitRunnable() {
            @Override
            public void run() {
                Set<String> tags = _myCow.getScoreboardTags();
                if (tags.contains("MaxHeight")) {
                    Bukkit.broadcastMessage("Cancelling Cow Recovery for real");
                    Bukkit.getServer().getScheduler().cancelTask(taskID);
                    _myCow.removeScoreboardTag("MaxHeight");
                    CancelAbility(true);
                    this.cancel();
                }
            }
        }.runTaskTimer(_mainPlugin, 0, 20);
    }
}

