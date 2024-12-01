package incineratorcow2.incineratorcow2;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Cow;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class AttackTunnel extends IncineratorCow {
    JavaPlugin _mainPlugin;
    Cow _myCow;

    public AttackTunnel(JavaPlugin mainPlugin, Cow myCow) {
        super(mainPlugin, myCow);
        _mainPlugin = mainPlugin;
        _myCow = myCow;
    }

    public void Tunnel() {
        int blocksDown = 30;
        int tickCheck = 3;
        double speedMultiplier = 3;
        //Vector stop;
        Player target;

        //stop = new Vector(0, 0, 0);
        target = GetRandomPlayer(50);
        if(target == null) {
            Bukkit.broadcastMessage("nope!");
            CancelAbility(false);
            return;
        }

        new BukkitRunnable() {
            int loopCount = 0;
            int stageCount = 0;
            int startY = -1;
            int height = 7;
            int width = 5;

            Location pursuedLoc;
            Location startLoc;
            Vector direction;
            boolean isStopped;
            boolean checkX;
            boolean checkY;
            boolean checkZ;

            AttackCharge attackCharge = new AttackCharge(_mainPlugin, cow);


            @Override
            public void run() {
                attackCharge.RemoveNearbyArea(cow, startY, width, height);

                //intializes variables
                if (loopCount < 1) {
                    startLoc = cow.getLocation();
                    isStopped = true;
                }

                switch (stageCount) {
                    case 0:
                        checkX = false;
                        checkY = true;
                        checkZ = false;
                        loopCount = 0;
                        pursuedLoc = cow.getLocation();
                        pursuedLoc.setY(startLoc.getY() - blocksDown);
                        direction = getDirection(cow.getLocation(), pursuedLoc);

                        isStopped = false;
                        break;
                    case 1:
                        checkX = true;
                        checkY = false;
                        checkZ = true;
                        loopCount = 0;
                        startY = 2;
                        height = 5;
                        width = 4;
                        pursuedLoc = cow.getLocation();
                        pursuedLoc.setX(target.getLocation().getX());
                        pursuedLoc.setZ(target.getLocation().getZ());

                        direction = getDirection(cow.getLocation(), pursuedLoc);
                        //_myCow.setVelocity(direction.multiply(speedMultiplier));

                        isStopped = false;
                        break;
                    case 2:
                        checkX = false;
                        checkY = true;
                        checkZ = false;
                        loopCount = 0;
                        startY = 2;
                        height = 9;
                        width = 5;
                        pursuedLoc = cow.getLocation();
                        pursuedLoc.setY(pursuedLoc.getY() + (target.getLocation().getY() - cow.getLocation().getY()) + 10);

                        direction = getDirection(cow.getLocation(), pursuedLoc);

                        isStopped = false;
                        break;
                    case 3:
                        Creeper creeper = (Creeper) Bukkit.getWorld("World").spawnEntity(cow.getLocation(),
                                EntityType.CREEPER);
                        creeper.setPowered(true);
                        creeper.setExplosionRadius(12);
                        creeper.explode();
                        cow.setVelocity(stop);
                        cow.addScoreboardTag("Ready");
                        CancelAbility(true);
                        this.cancel();
                        break;
                }

                if(CheckCords(cow.getLocation(), 3, stageCount, pursuedLoc, checkX, checkY, checkZ)) {
                    cow.setVelocity(stop);
                    Bukkit.broadcastMessage("Done!");

                    stageCount += 1;
                    isStopped = true;
                }

                direction = getDirection(cow.getLocation(), pursuedLoc);
                if(stageCount != 2) {
                    cow.setVelocity(direction);
                }
                else {
                    cow.setVelocity(direction.multiply(speedMultiplier));
                }

                if(loopCount > 600) {
                    Bukkit.broadcastMessage("Didn't make it");
                    cow.addScoreboardTag("Ready");
                    this.cancel();
                }

                loopCount += tickCheck;
            }
        }.runTaskTimer(_mainPlugin, 0, tickCheck);
    }
}
