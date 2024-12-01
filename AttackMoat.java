package incineratorcow2.incineratorcow2;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.*;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class AttackMoat extends IncineratorCow {
    private JavaPlugin _mainPlugin;
    Cow _myCow;

    enum MoatSides {
        EAST, //positive X
        SOUTH, //positive Z
        WEST, //negative X
        NORTH //negative Z
    }

    public AttackMoat(JavaPlugin mainPlugin, Cow myCow) {
        super(mainPlugin, myCow);
        _mainPlugin = mainPlugin;
        _myCow = myCow;
    }


    public void Moat() {
        Location targetLoc;
        Location blockLoc;
        double startLocZ;
        double startLocX;
        double endLocZ;
        double endLocX;
        int moatSize;
        int depth;
        int heightAbovePlayer;
        int depth2;
        int raisedHeight;
        int blockMovement;
        Player target;
        PotionEffect potionEffect = new PotionEffect(PotionEffectType.SLOW, 200, 4, true, false);

        moatSize = 12;
        depth = 30;
        heightAbovePlayer = 30;
        depth2 = depth + heightAbovePlayer;
        raisedHeight = 20;
        blockMovement = 2;

        target = GetRandomPlayer(50);
        if(target == null) {
            CancelAbility(false);
            return;
        }
        target.addPotionEffect(potionEffect);

        startLocZ = target.getLocation().getZ() - (moatSize) / 2;
        startLocX = target.getLocation().getX() + (moatSize) / 2;
        endLocZ = target.getLocation().getZ() + (moatSize) / 2;
        endLocX = target.getLocation().getX() - (moatSize) / 2;

        Location corner1 = target.getLocation();
        corner1.setX(startLocX);
        corner1.setZ(startLocZ);
        Location corner2 = target.getLocation();
        corner2.setX(endLocX);
        corner2.setZ(endLocZ);

        targetLoc = target.getLocation();
        blockLoc = target.getLocation();
        blockLoc.setX(startLocX);
        blockLoc.setZ(startLocZ);

        for(int i = 0; i < depth; i += 1) {
            for (MoatSides m : MoatSides.values()) {
                MakeMoatSide(moatSize, blockLoc, m, startLocZ, startLocX);
            }
            blockLoc.setY(target.getLocation().getY() - i);
        }

        new BukkitRunnable() {
            MoveLand moveLand = new MoveLand();
            int loopCount = 0;
            @Override
            public void run() {
                if(loopCount > raisedHeight) {
                    this.cancel();
                    blowUpLand(targetLoc, depth, depth2, moatSize);
                }
                moveLand.move(moatSize - 1, depth2, targetLoc, target, blockMovement, heightAbovePlayer);
                targetLoc.setY(targetLoc.getY() + 1);
                loopCount += blockMovement;
            }
        }.runTaskTimer(_mainPlugin, 0, 10);
    }


    private void MakeMoatSide(int moatSize, Location blockLoc, MoatSides side, double startLocZ, double startLocX) {
        int zMultiplier = 0;
        int xMultiplier = 0;

        switch(side) {
            case EAST:
                zMultiplier = 1;
                break;
            case SOUTH:
                xMultiplier = -1;
                startLocZ += moatSize;
                break;
            case WEST:
                //zMultiplier = -1;
                zMultiplier = 1;
                startLocX -= moatSize;
                break;
            case NORTH:
                xMultiplier = -1;
                break;
        }
        for (int i = 0; i < moatSize; i++) {
            blockLoc.setZ(startLocZ + (i * zMultiplier)); // east and west
            blockLoc.setX(startLocX + (i * xMultiplier)); // north and south
            //blockLoc.getBlock().setType(Material.GLASS);
            blockLoc.getBlock().breakNaturally();
        }
    }

    public void blowUpLand(Location targetLoc, int depth, int depth2, int moatSize) {
        Location tntLoc = new Location(Bukkit.getWorld("World"), targetLoc.getX(), targetLoc.getY(), targetLoc.getZ());
        int distanceBetweenExplosions = 4;
        double distance = moatSize/4;
        double[] creeperXLocs = {distance, 0, distance * -1, 0}; //whenever there is a 0, it means that in that iteration I don't want the X to change
        double[] creeperZLocs = {0, distance, 0, distance * -1}; //whenever there is a 0, it means that in that iteration I don't want the Z to change

        tntLoc.setY(targetLoc.getY() - depth);
        new BukkitRunnable() {
            int loopCount = 0;
            @Override
            public void run() {
                //spawns 4 tnt spaced the same way around the center of the chunk
                for(int i = 0; i < 4; i++) {
                    tntLoc.setX(targetLoc.getX() + creeperXLocs[i]);
                    tntLoc.setZ(targetLoc.getZ() + creeperZLocs[i]);

                    Creeper creeper = (Creeper) Bukkit.getWorld("World").spawnEntity(tntLoc,
                            EntityType.CREEPER);
                    creeper.setPowered(true);
                    creeper.setExplosionRadius(4);
                    creeper.explode();

                    //reset X and Y in tntLoc
                    tntLoc.setX(tntLoc.getX() - creeperXLocs[i]);
                    tntLoc.setZ(tntLoc.getZ() - creeperZLocs[i]);
                }
                tntLoc.setY(tntLoc.getY() + distanceBetweenExplosions);
                loopCount += distanceBetweenExplosions;

                if(loopCount > depth2) {
                    this.cancel();
                }

            }
        }.runTaskTimer(_mainPlugin, 0, 6);
    }
}