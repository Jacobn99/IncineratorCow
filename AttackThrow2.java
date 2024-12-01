package incineratorcow2.incineratorcow2;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Cow;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;


public class AttackThrow2 extends IncineratorCow {
    JavaPlugin _mainPlugin;
    Cow _myCow;
    public AttackThrow2(JavaPlugin mainPlugin, Cow myCow) {
        super(mainPlugin, myCow);
        _mainPlugin = mainPlugin;
        _myCow = myCow;
    }

    public void Throw2() {

        new BukkitRunnable() {
            double R = 10;
            double centerCorrection = 0.5;
            Location cowLoc;
            Location positveLoc = new Location(Bukkit.getWorld("world"), R+centerCorrection, 100 ,R+centerCorrection);
            Location placeBlock;
            double H;
            double K;
            double Z; //supposed to be Z axis, not Y
            double X;
            double XGoal;
            int inverseModifier;
            double Xinciments;

            int i = 0;
            int halfCount = 0;

            @Override
            public void run() {
                if(i == 0) {
                    placeBlock = new Location(Bukkit.getWorld("world"), 0, 100, 0);
                    cowLoc = cow.getLocation();
                    H = positveLoc.getX() + 0;
                    K = positveLoc.getZ() + 0;
                    Z = positveLoc.getZ(); //supposed to be Z axis, not Y
                    X = H - R;
                    XGoal = H + R;
                    inverseModifier = 1;
                    Xinciments = 0.1;

                    halfCount = 0;
                }

                if(Z != positveLoc.getZ() || halfCount < 2) {
                    if(i > 500) {
                        Bukkit.broadcastMessage("stopped");
                        this.cancel();
                    }
                    if(X > XGoal - 0.01 && X < XGoal + 0.01) {
                        Bukkit.broadcastMessage("X: " + X);
                        X = XGoal;
                        XGoal = H - R;
                        inverseModifier = -1;
                        Xinciments *= inverseModifier;
                        halfCount += 1;
                    }

                    Z = K + inverseModifier * (Math.sqrt(Math.pow(R, 2) - Math.pow(X - H, 2)));
                    placeBlock.setX(X);
                    placeBlock.setZ(Z);
                    placeBlock.getBlock().setType(Material.GOLD_BLOCK);

//                    Bukkit.broadcastMessage("---------");
//                    Bukkit.broadcastMessage("H: " + H);
//                    Bukkit.broadcastMessage("K: " + K);
//                    Bukkit.broadcastMessage("X: " + X);
//                    Bukkit.broadcastMessage("Y: " + Z);

                    X += Xinciments;
                    i++;
                }
                else {
                    this.cancel();
                }

            }
        }.runTaskTimer(_mainPlugin, 0, 1);



        double R = 10;
        double centerCorrection = 0.5;
        Location cowLoc = cow.getLocation();
        Location positveLoc = new Location(Bukkit.getWorld("world"), R+centerCorrection, 100 ,R+centerCorrection);
        Location placeBlock = new Location(Bukkit.getWorld("world"), 0, 100, 0);
        double H = positveLoc.getX() + 0;
        double K = positveLoc.getZ() + 0;
        double Z = positveLoc.getZ(); //supposed to be Z axis, not Y
        double X = H - R;
        double XGoal = H + R;
        double cowX = cowLoc.getX();
        double cowZ = cowLoc.getZ();
        cowX = RoundHalves(cowX);
        cowZ = RoundHalves(cowZ);
        double correctionX = cowX-positveLoc.getX();
        double correctionZ = cowZ-positveLoc.getZ();
        double Xinciments = 0.1;
        int inverseModifier = 1;
        int i = 0;
        int halfCount = 0;

        while(Z != positveLoc.getZ() || halfCount < 2) {

            if(i > 500) {
                Bukkit.broadcastMessage("stopped");
                break;
            }
            if(X > XGoal - 0.01 && X < XGoal + 0.01) {
                X = XGoal;
                XGoal = H - R;
                inverseModifier = -1;
                Xinciments *= inverseModifier;
                halfCount += 1;
            }

            Z = K + inverseModifier * (Math.sqrt(Math.pow(R, 2) - Math.pow(X - H, 2)));

            placeBlock.setX(X + correctionX);
            placeBlock.setZ(Z + correctionZ);
            placeBlock.getBlock().setType(Material.GOLD_BLOCK);
            //Bukkit.broadcastMessage("cowX: " + X + "Z: " + Z);

            X += Xinciments;
            i++;
        }
        positveLoc.getBlock().setType(Material.DIAMOND_BLOCK);



//        new BukkitRunnable() {
//            double R = 10;
//            double centerCorrection = 0.5;
//            Location cowLoc = _myCow.getLocation();
//            Location positveLoc = new Location(Bukkit.getWorld("world"), R+centerCorrection, 100 ,R+centerCorrection);
//            Location placeBlock = new Location(Bukkit.getWorld("world"), 0, 100, 0);
//            double H = positveLoc.getX() + 0;
//            double K = positveLoc.getZ() + 0;
//            double Z = positveLoc.getZ(); //supposed to be Z axis, not Y
//            double X = H - R;
//            double XGoal = H + R;
//            double cowX = cowLoc.getX();
//            double cowZ = cowLoc.getZ();
//            double correctionX = cowX-positveLoc.getX();
//            double correctionZ = cowZ-positveLoc.getZ();
//            double Xinciments = 0.1;
//            int inverseModifier = 1;
//            int i = 0;
//            int halfCount = 0;
//
//            @Override
//            public void run() {
//                if(i < 1) {
//                    cowX = RoundHalves(cowX);
//                    cowZ = RoundHalves(cowZ);
//                }
//                if(Z != positveLoc.getZ() || halfCount < 2) {
//                    if(i > 500) {
//                        Bukkit.broadcastMessage("stopped");
//                        this.cancel();
//                    }
//                    if(X > XGoal - 0.01 && X < XGoal + 0.01) {
//                        X = XGoal;
//                        XGoal = H - R;
//                        inverseModifier = -1;
//                        Xinciments *= inverseModifier;
//                        halfCount += 1;
//                    }
//
//                    Z = K + inverseModifier * (Math.sqrt(Math.pow(R, 2) - Math.pow(X - H, 2)));
//
//                    placeBlock.setX(X + correctionX);
//                    placeBlock.setZ(Z + correctionZ);
//                    placeBlock.getBlock().setType(Material.GOLD_BLOCK);
//                    //Bukkit.broadcastMessage("cowX: " + X + "Z: " + Z);
//
//                    X += Xinciments;
//                    i++;
//                }
//                positveLoc.getBlock().setType(Material.DIAMOND_BLOCK);
//
//            }
//        }.runTaskTimer(_mainPlugin, 0, 1);
    }

    private double RoundHalves(double d) {
        double r = 0;
        double w;
        double dif;
        double correction;
        double sign;

        correction = 0.5;
        w = Math.round(d);
        dif = w - d;
        sign = (Math.abs(dif)/dif) * -1; // determines if 0.5 should be added or subtracted from the rounded amount
        //Bukkit.broadcastMessage("d: " + d + ", w: " + w + ", w - r = " + dif);

        if(Math.abs(dif) < correction && Math.abs(dif) > 0.25) {
            r = w + correction * sign;
        }
        else if(Math.abs(dif) > correction && Math.abs(dif) < 0.75 ) {
            r = w + correction * sign;
        }
        //Bukkit.broadcastMessage("r: " + r);
        return r;
    }
}
