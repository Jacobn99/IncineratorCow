package incineratorcow2.incineratorcow2;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

public class AttackThrow extends IncineratorCow {
    JavaPlugin _mainPlugin;
    Cow _myCow;

    public AttackThrow(JavaPlugin mainPlugin, Cow myCow) {
        super(mainPlugin, myCow);
        _mainPlugin = mainPlugin;
        _myCow = myCow;
    }

    public void Throw() {
        new BukkitRunnable() {
            List<FallingBlock> fallingBlocks = new ArrayList<>();
            List<FallingBlock> thrownBlocks = new ArrayList<>();
            List<FallingBlock> deadBlocks = new ArrayList<>();
            List<Integer> fbCorners = new ArrayList<>();
            Location pursuedLoc;
            Location fbLoc;
            Vector direction;
            int blockCount;
            int loopCount = 0;
            int fbID;
            int fireTime;
            double distance;
            double maxDistanceCow;
            double maxDistanceCorner;

            Block block;
            @Override
            public void run() {
                double[] CornerXLocs = {maxDistanceCow, -maxDistanceCow, -maxDistanceCow, maxDistanceCow}; //whenever there is a 0, it means that in that iteration I don't want the X to change
                double[] CornerZLocs = {maxDistanceCow, maxDistanceCow, -maxDistanceCow, -maxDistanceCow}; //whenever there is a 0, it means that in that iteration I don't want the Z to change
                Player target;

                //initializes variables
                if (loopCount == 0) {
                    blockCount = 20;
                    maxDistanceCow = 2.3;
                    maxDistanceCorner = 1;
                    fireTime = 200;
                    Bukkit.broadcastMessage(String.valueOf(CornerXLocs[0]));
                }
                fbID = 0;

                //picks up blocks
                if (loopCount < blockCount) {
                    block = FindRandomBlock(30);
                    if (block != null) {
                        fallingBlocks.add(pickUpBlock(block));
                        fbCorners.add(0);
                    } else {
                        Bukkit.broadcastMessage("No Block");
                    }
                }

                if (fallingBlocks.size() == deadBlocks.size()) {
                    Bukkit.broadcastMessage("Done!");
                    CancelAbility(true);
                    this.cancel();
                }

                for (FallingBlock fb : fallingBlocks) {
                    if (!fb.isDead() && !thrownBlocks.contains(fb)) {
                        fbLoc = fb.getLocation();
                        pursuedLoc = _myCow.getLocation();
                        pursuedLoc.setX(pursuedLoc.getX() + CornerXLocs[fbCorners.get(fbID)]);
                        pursuedLoc.setZ(pursuedLoc.getZ() + CornerZLocs[fbCorners.get(fbID)]);
                        distance = fbLoc.distance(pursuedLoc);

                        if (!thrownBlocks.contains(fb)) {
                            if (loopCount * 3 > fireTime) {
                                target = GetRandomPlayer(40);
                                if (target != null) {
                                    fb.setVelocity(getDirection(fbLoc, target.getLocation()).multiply(3));
                                    thrownBlocks.add(fb);
                                    fireTime += 5;
                                }
                            } else if (distance < maxDistanceCorner) {
                                if (fbCorners.get(fbID) < 3) {
                                    fbCorners.set(fbID, fbCorners.get(fbID) + 1);
                                } else {
                                    fbCorners.set(fbID, 0);
                                }
                            } else {
                                direction = getDirection(fbLoc, pursuedLoc).multiply(0.5);
                                fb.setVelocity(direction);
                            }
                            fbID++;
                        }
                    }
                    //else if(fb.isDead() && thrownBlocks.contains(fb)) {
                    else if(fb.isDead() && !deadBlocks.contains(fb)) {
                        TNTPrimed tnt = (TNTPrimed) Bukkit.getWorld("World").spawnEntity(fb.getLocation(),
                                EntityType.PRIMED_TNT);
                        tnt.setFuseTicks(0);
                        thrownBlocks.remove(fb);
                        deadBlocks.add(fb);
                    }
                }
                loopCount++;
            }
        }.runTaskTimer(_mainPlugin, 0, 3);
    }

    private Block FindRandomBlock(int radius) {
        Block block = null;
        Location blockLoc;
        int randX;
        int randZ;
        int limitY = 25;

        randX = rand.nextInt(radius) - radius/2;
        randZ = rand.nextInt(radius) - radius/2;
        blockLoc = _myCow.getLocation();
        blockLoc.setX(blockLoc.getX() + randX);
        blockLoc.setZ(blockLoc.getZ() + randZ);


        for(int i = 0; i < limitY; i++) {
            if (blockLoc.getBlock().getType() != Material.AIR) {
                block = blockLoc.getBlock();
                break;
            }
            blockLoc.setY(blockLoc.getY() - 1);
        }
        return block;
    }

    private FallingBlock pickUpBlock(Block block) {
        Location blockLoc;
        Location up;
        Vector direction;
        Material blockType;

        blockLoc = block.getLocation().clone();
        up = block.getLocation().clone();
        up.setY(up.getY() + 3);
        blockType = block.getType();
        direction = up.subtract(blockLoc).toVector().normalize();

        block.setType(Material.AIR);
        FallingBlock fallingBlock = blockLoc.getWorld().spawnFallingBlock(blockLoc, blockType, (byte) 0);
        fallingBlock.setVelocity(direction);
        return fallingBlock;
    }
}