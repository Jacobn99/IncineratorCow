package incineratorcow2.incineratorcow2;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

import java.util.*;

public class IncineratorCow {
    private JavaPlugin _mainPlugin;
    Cow cow;

    Random rand = new Random();
    ArrayList<Player> nearbyPlayers = new ArrayList<Player>();
    Vector stop = new Vector(0, 0, 0);


    public IncineratorCow(JavaPlugin mainPlugin, Cow myCow) {
        _mainPlugin = mainPlugin;
        cow = myCow;
    }


    protected void EntityFacePlayer(Entity e, Player target, boolean isBackwards) {
        Vector entityLocation;
        Vector playerLocation;
        Vector dirBetweenLocations;

        entityLocation = e.getLocation().toVector();
        playerLocation = target.getLocation().toVector();
        dirBetweenLocations = playerLocation.subtract(entityLocation);

        if(isBackwards) {
            dirBetweenLocations = dirBetweenLocations.multiply(-1);
        }

        e.teleport(e.getLocation().setDirection(dirBetweenLocations));
    }

    protected Player GetRandomPlayer(int radius) {
        Player target = null;

        nearbyPlayers.clear();

        for (Entity e : cow.getNearbyEntities(radius, radius, radius)) {
            if (e instanceof Player) {
                Player p = (Player) e;
                nearbyPlayers.add(p);
            }
        }
        if (nearbyPlayers.size() > 0) {
            int playerIndex = rand.nextInt(nearbyPlayers.size());
            target = nearbyPlayers.get(playerIndex);
        }
        else {
            Bukkit.broadcastMessage("No one in range");
        }
        return target;
    }

    protected boolean checkSurroundingBlocks(Entity e, Double distance) {
        Location entityLocation = e.getLocation();
        Location scanLoc = new Location(Bukkit.getWorld("World"), entityLocation.getX(), entityLocation.getY(), entityLocation.getZ());
        double blockOrder[] = new double[]{0,0,0};

        for(int i = 0; i < 2; i++) {
            for (int ii = 0; ii < blockOrder.length; ii++) {
                blockOrder[ii] = distance;

                scanLoc.setX(entityLocation.getX() + blockOrder[0]);
                scanLoc.setY(entityLocation.getY() + (blockOrder[1]));
                scanLoc.setZ(entityLocation.getZ() + blockOrder[2]);

                //scanLoc.getBlock().setType(Material.GLASS);
               if(!(scanLoc.getBlock().getType() == Material.AIR)) {
                   //Bukkit.broadcastMessage("AYOOOOOOO");
                   return true;
                }
                //Bukkit.broadcastMessage("scanLoc = X: " + scanLoc.getX() + " Y: " + scanLoc.getY() + " Z: " + scanLoc.getZ());
                //Bukkit.broadcastMessage("Block Change = X: " + blockOrder[0] + " Y: " + blockOrder[1] + " Z: " + blockOrder[2]);
                blockOrder[ii] = 0;

            }
            distance = distance * -1;
        }

        return false;
    }
    public Vector getDirection(Location startLoc, Location targetLoc) {
        Vector i = targetLoc.toVector().subtract(startLoc.toVector()).normalize();
        return i;
    }

    public boolean CheckCords(Location entityLoc, int distance, int stageCount, Location pursuedLoc, boolean checkX, boolean checkY, boolean checkZ) {
        if(!checkX || Math.abs(pursuedLoc.getX() - entityLoc.getX()) < distance) {
            if(!checkY || Math.abs(pursuedLoc.getY() - entityLoc.getY()) < distance) {
                if(!checkZ || Math.abs(pursuedLoc.getZ() - entityLoc.getZ()) < distance) {
                    return true;
                }
            }
        }

        if(pursuedLoc.getY() < entityLoc.getY() && stageCount == 2) {
            return true;
        }
        return false;
    }

    public void RemoveNearbyArea(Entity e, double startY, int width, int height) {
        Location aroundEntity;
        aroundEntity = e.getLocation();
        aroundEntity.setX(aroundEntity.getX() - width/2);
        aroundEntity.setY(aroundEntity.getY() + startY);
        aroundEntity.setZ(aroundEntity.getZ() - width/2);

        for (int i = 0; i < height; i++) {
            for (int ii = 0; ii < width; ii++) {
                for (int iii = 0; iii < width; iii++) {
                    //aroundEntity.getBlock().setType(Material.GLASS);
                    aroundEntity.getBlock().breakNaturally();
                    aroundEntity.setZ(aroundEntity.getZ() + 1);
                }
                aroundEntity.setZ(aroundEntity.getZ() - width);
                aroundEntity.setX(aroundEntity.getX() + 1);
            }
            aroundEntity.setX(aroundEntity.getX() - width);
            aroundEntity.setY(aroundEntity.getY() - 1);
        }
    }

    public void CancelAbility(/*BukkitRunnable task,*/ Boolean isDelayed) {
        if(isDelayed) {
            cow.addScoreboardTag("Ready");
        }
        else {
            cow.addScoreboardTag("Go");
        }

    }
}

