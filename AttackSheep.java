package incineratorcow2.incineratorcow2;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.*;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.Random;

public class AttackSheep extends IncineratorCow {
    private JavaPlugin _mainPlugin;
    Cow _myCow;

    BukkitTask task = null;
    Random rand = new Random();
    ArrayList<Player> nearbyPlayers = new ArrayList<Player>();
    int playerCheckDistance = 30;


    public AttackSheep(JavaPlugin mainPlugin, Cow myCow) {
        super(mainPlugin, myCow);
        _mainPlugin = mainPlugin;
        _myCow = myCow;
    }

    public void Sheep() {
        task = new BukkitRunnable() {
            Player target;
            ArrayList<Sheep> aliveSheep = new ArrayList<Sheep>();
            boolean isNoAmo = false;
            boolean isPlayer = false;
            int i = 0;
            int sheepLeft = 0;

            @Override
            public void run() {
                //List<Entity> nearbyEntities = _myCow.getNearbyEntities(30,30,30);
                /*for(Entity e : nearbyEntities) {
                    if(e instanceof Player) {
                        isPlayer = true;
                    }
                }
                if(!isPlayer) {
                    Bukkit.broadcastMessage("Cancelling");
                    CancelAbility( false);
                    task.cancel();
                    Bukkit.broadcastMessage("not going sheep");
                }*/
                target = GetRandomPlayer(playerCheckDistance);
                if(target != null) {
                    if (!isNoAmo) {
                        //launches fireballs
                        EntityFacePlayer(_myCow, target, false);
                        launchSheep(target, aliveSheep);
                        i++;
                    }
                    if (i > 4) {
                        isNoAmo = true;
                    }
                    for (Sheep sheep : aliveSheep) {
                        if (checkSurroundingBlocks(sheep, 1.0)) {
                            TNTPrimed tntPrimed = (TNTPrimed) Bukkit.getWorld("World").spawnEntity(sheep.getLocation(),
                                    EntityType.PRIMED_TNT);
                            tntPrimed.setFuseTicks(0);
                        }
                    }
                    Bukkit.broadcastMessage("Alive Sheep: " + aliveSheep.size());
                    for (Sheep sheep : aliveSheep) {
                        if (sheep.isDead()) {
                            aliveSheep.remove(sheep);
                        }
                    }
                    if (aliveSheep.size() == 0) {
                        _myCow.addScoreboardTag("Ready");
                        task.cancel();
                    }
                }
                else {
                    CancelAbility(false);
                    this.cancel();
                }

            }
        }.runTaskTimer(_mainPlugin, 0, 4);
    }

    private void launchSheep(Player target, ArrayList<Sheep> aliveSheep) {
        Location targetLoc;
        Vector loc1;
        Vector loc2;
        Vector direction;

        Sheep missileSheep = (Sheep) Bukkit.getWorld("World").spawnEntity(_myCow.getLocation(),
                EntityType.SHEEP);
        missileSheep.addScoreboardTag("ExplosiveContact");
        aliveSheep.add(missileSheep);

        targetLoc = target.getLocation();
        loc1 = missileSheep.getLocation().toVector();
        loc2 = targetLoc .toVector();
        loc2.setY(targetLoc.getY() + 1);
        direction = (loc2.subtract(loc1).normalize());

        missileSheep.setVelocity(direction.multiply(3));
    }
}

