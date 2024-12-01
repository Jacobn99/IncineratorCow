package incineratorcow2.incineratorcow2;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.*;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class AttackProjectile extends IncineratorCow {
    private JavaPlugin _mainPlugin;
    Cow _myCow;

    public AttackProjectile(JavaPlugin mainPlugin, Cow myCow) {
        super(mainPlugin, myCow);
        _mainPlugin = mainPlugin;
        _myCow = myCow;
    }

    public void Projectile() {
        Player target;
        target = GetRandomPlayer(30);
        if(target != null) {
            new BukkitRunnable() {
                int i = 0;

                //Execution of AttackProjectile method
                @Override
                public void run() {
                    //launches fireballs
                    //CowFacePlayer(target);
                    EntityFacePlayer(_myCow, target, false);
                    _myCow.launchProjectile(Fireball.class);

                    //launches arrows
                    launchArrow(target, 0, 1.5);
                    launchArrow(target, 0, -1.5);
                    i++;

                    if(i > 9) {
                        CancelAbility(true);
                        this.cancel();
                    }
                }
            }.runTaskTimer(_mainPlugin, 0, 4);
        }
        else {
            CancelAbility(false);
        }
    }

    private void launchArrow(Player target, double distanceFromPlayerX, double distanceFromPlayerZ) {
        double x = 0;
        double z = 0;
        PotionData potionData = new PotionData(PotionType.POISON);

        if (_myCow.getFacing().equals(BlockFace.EAST)) {
            //Bukkit.broadcastMessage("East");
            x = 1;
        } else if (_myCow.getFacing().equals(BlockFace.WEST)) {
            //Bukkit.broadcastMessage("West");
            x = -1;
        } else if (_myCow.getFacing().equals(BlockFace.NORTH)) {
            //Bukkit.broadcastMessage("North");
            z = -1;
        } else {
            //Bukkit.broadcastMessage("South");
            z = 1;
        }
            Location arrowSpawn = _myCow.getLocation();
            arrowSpawn.setX(_myCow.getLocation().getX() + x);
            arrowSpawn.setY(_myCow.getLocation().getY());
            arrowSpawn.setZ(_myCow.getLocation().getZ() + z);

        Arrow arrow = (Arrow) Bukkit.getWorld("World").spawnEntity(arrowSpawn,
                    EntityType.ARROW);
        arrow.addScoreboardTag("GhostProjectile");
        arrow.setBasePotionData(potionData);

        Location targetLoc = target.getLocation();
        targetLoc.setX(target.getLocation().getX() + distanceFromPlayerX);
        targetLoc.setZ(target.getLocation().getZ() + distanceFromPlayerZ);

        Vector loc1 = arrow.getLocation().toVector();
        Vector loc2 = targetLoc.toVector();
        loc2.setY(targetLoc.getY() + 1);
        Vector direction = (loc2.subtract(loc1).normalize()).multiply(4);

        arrow.setVelocity(direction);
    }
}

