package incineratorcow2.incineratorcow2;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class MoveLand {
    /*public MoveLand(JavaPlugin mainPlugin, Cow myCow) {
        _mainPlugin = mainPlugin;
        _myCow = myCow;
    }*/

    public void move(int landSize, int depth, Location centerLoc, Player target, int blockMovement, int heightAbovePlayer) {
        Location blockLoc;
        Location targetLoc;
        Location topCorner;
        Location bottomCorner;
        Location centerLoc2;
        Location cloneLoc;
        Location startBlockLoc;
        Vector targetVelocity;
        Material blockMaterial;
        World world;

        // initializes variables
        world = Bukkit.getWorld("World");
        centerLoc2 = new Location(world, centerLoc.getX(), centerLoc.getY(), centerLoc.getZ());
        cloneLoc = new Location(world, 0, 0, 0);
        startBlockLoc = centerLoc2;
        bottomCorner = new Location(world, centerLoc.getX() - (landSize) / 2, centerLoc.getY() - (depth - heightAbovePlayer),
                centerLoc.getZ() + (landSize) / 2);
        topCorner = new Location(world, centerLoc.getX() + (landSize) / 2, centerLoc.getY() + heightAbovePlayer,
                centerLoc.getZ() - (landSize) / 2);
        startBlockLoc.setY(centerLoc.getY() + heightAbovePlayer);
        startBlockLoc.setX(centerLoc.getX() - landSize / 2);
        startBlockLoc.setZ(centerLoc.getZ() - landSize / 2);
        blockLoc = startBlockLoc;
        //startBlockLoc.getBlock().setType(Material.DIAMOND_BLOCK);

        //tps player up one block if they're on the chunk being raised
        targetLoc = target.getLocation();
        if(isPlayerInArea(bottomCorner, topCorner, target)) {
            targetVelocity = target.getVelocity();
            targetLoc.setY(target.getLocation().getY() + blockMovement);
            target.teleport(targetLoc);
            target.setVelocity(targetVelocity);
        }

        // clones the land and moves it up by blockMovement
        for (int i = 0; i < depth; i++) {
            for (int ii = 0; ii < landSize; ii++) {
                for (int iii = 0; iii < landSize; iii++) {
                    if(!(blockLoc.getBlock().equals(Material.AIR))) {
                        cloneLoc.setX(blockLoc.getX());
                        cloneLoc.setY(blockLoc.getY() + blockMovement);
                        cloneLoc.setZ(blockLoc.getZ());
                        blockMaterial = blockLoc.getBlock().getType();

                        blockLoc.getBlock().setType(Material.AIR);
                        cloneLoc.getBlock().setType(blockMaterial);
                        //bottomCorner.getBlock().setType(Material.NETHERITE_BLOCK);
                        //topCorner.getBlock().setType(Material.GOLD_BLOCK);
                    }
                        blockLoc.setZ(blockLoc.getZ() + 1);
                }
                blockLoc.setX(blockLoc.getX() + 1);
                blockLoc.setZ(blockLoc.getZ() - landSize);
            }
            blockLoc.setY(blockLoc.getY() - 1);
            blockLoc.setX(blockLoc.getX() - landSize);
        }
    }

    private boolean isPlayerInArea(Location bottomCorner, Location topCorner, Player target) {
        Location targetLoc = target.getLocation();
        if(!(targetLoc.getX() > topCorner.getX() || targetLoc.getX() < bottomCorner.getX())) {
            if (!(targetLoc.getY() > topCorner.getY() || targetLoc.getY() < bottomCorner.getY())) {
                if (!(targetLoc.getZ() < topCorner.getZ() || targetLoc.getZ() > bottomCorner.getZ())) {
                    //Bukkit.broadcastMessage("Tped");
                    return true;
                }
            }
        }
        return false;
    }
}
