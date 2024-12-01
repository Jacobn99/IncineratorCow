package incineratorcow2.incineratorcow2;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Cow;
import org.bukkit.entity.EnderDragon;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.util.Vector;

import static org.bukkit.Bukkit.getServer;

//public class AIManager extends IncineratorCow {
public class AIManager extends IncineratorCow {
    private JavaPlugin _mainPlugin;
    private Cow _myCow;
    private IdleMovement _idleMovement;
    private PhaseOne _phaseOne;

    public AIManager(JavaPlugin mainPlugin, Cow myCow) {
        super(mainPlugin, myCow);
        _mainPlugin = mainPlugin;
        _myCow = myCow;
        _idleMovement = new IdleMovement(_mainPlugin, _myCow);
        _phaseOne = new PhaseOne(_mainPlugin, _myCow);
    }

    enum States {
        ATTACK,
        EVADE,
        EVADE_AND_ATTACK,
    }

//    public void RunPhaseOne2() {
//        new BukkitRunnable() {
//            @Override
//            public void run() {
//                Bukkit.broadcastMessage("Cum");
//            }
//        }.runTaskTimerAsynchronously(_mainPlugin, 0, 10);
//    }

//    public void RunMainThread() {
//        new BukkitRunnable() {
//            States state;
//            int ticks = 0;
//            @Override
//            public void run() {
//                if(ticks == 0) {
//                    state = States.ATTACK;
//                }
//                switch (state) {
//                    case EVADE:
//
//                        break;
//                    case ATTACK:
//                        break;
//                    case EVADE_AND_ATTACK:
//                        break;
//                }
//                ticks += 1;
//            }
//        }.runTaskTimerAsynchronously(_mainPlugin, 0, 1);
//
//    }


    public void RunPhaseOne() {
        new BukkitRunnable() {
            int tickTime = 0;
            int delay;
            int randAbility;
            Vector stop = new Vector(0,0,0);
//            double h;
//            double k;
//            double r;
//            double x;
//            double z;
//            double desiredZ;
            Location loc = _myCow.getLocation();

            boolean isStop;

            @Override
            public void run() {

                if (!_myCow.isDead()) {
                    if (tickTime == 0) {
                        delay = 0;
                        randAbility = 0;

//                        r = 4;
//                        h = loc.getX();
//                        k = loc.getZ();
//                        desiredZ = loc.getZ();
                    }
//                    x = loc.getX();
//                    z = loc.getZ();
//
//                    //Bukkit.broadcastMessage("testing");
//                    _idleMovement.CircleMovement(h, k, r, desiredZ);
//
//                    desiredZ = h - 1;
//                    Bukkit.broadcastMessage("desiredZ: " + desiredZ);
//                    if((z-k) != 0) {
//                        desiredZ = z + (z - k) / Math.abs(z - k);
//                        Bukkit.broadcastMessage("Data: " + (x + (z - k) / Math.abs(z - k)));
//                    }
//                    else {
//                        //Bukkit.broadcastMessage("desiredZ: " + desiredZ);
//                        desiredZ = z - 1;
//                        Bukkit.broadcastMessage("desiredZ: " + desiredZ);
//                    }

                    if (_myCow.getScoreboardTags().contains("Ready") || _myCow.getScoreboardTags().contains("Go")) {
                        _idleMovement.RandomMovement();
                        if (delay < 1 || _myCow.getScoreboardTags().contains("Go")) {
                            _myCow.setVelocity(stop);
                            delay = rand.nextInt(40) + 20;
                            _myCow.removeScoreboardTag("Ready");
                            _myCow.removeScoreboardTag("Go");

                            Bukkit.broadcastMessage("delay: " + delay);

                            ChooseAbility();
                        } else {
                            delay -= 10;
                        }
                    }
                    tickTime += 10;
                }
                else {
                    Bukkit.broadcastMessage("He's dead");
                    this.cancel();
                }
            }
        }.runTaskTimer(_mainPlugin, 0, 10);
    }
//    public void RunPhaseOne() {
//        new BukkitRunnable() {
//            int tickTime = 0;
//            int delay;
//            int randAbility;
//            boolean isStop;
//
//            @Override
//            public void run() {
//                if (!_myCow.isDead()) {
//
//                }
//            }
//        }.runTaskTimer(_mainPlugin, 0, 10);
//    }
    public void ChooseAbility() {
        int random = rand.nextInt(100);
        Bukkit.broadcastMessage(String.valueOf(random));

        double tunnelPercentage = _phaseOne.GetTunnelPercentage();
        double moatPercentage = _phaseOne.GetMoatPercentage();
        double throwPercentage = _phaseOne.GetThrowPercentage();
        double sheepPercentage = _phaseOne.GetSheepPercentage();
        double chargePercentage = _phaseOne.GetChargePercentage();
        double projectilePercentage = _phaseOne.GetProjectilePercentage();

//        Bukkit.broadcastMessage(projectilePercentage + " - " + "0");
//        Bukkit.broadcastMessage((chargePercentage + projectilePercentage) + " - " + (projectilePercentage));
//        Bukkit.broadcastMessage((sheepPercentage + chargePercentage + projectilePercentage) + " - " + (chargePercentage + projectilePercentage));
//        Bukkit.broadcastMessage((throwPercentage + sheepPercentage + chargePercentage + projectilePercentage) + " - " + (sheepPercentage + chargePercentage + projectilePercentage));
//        Bukkit.broadcastMessage((moatPercentage + throwPercentage + sheepPercentage + chargePercentage + projectilePercentage) + " - " + (throwPercentage + sheepPercentage + chargePercentage + projectilePercentage));
//        Bukkit.broadcastMessage((tunnelPercentage + moatPercentage + throwPercentage + sheepPercentage + chargePercentage + projectilePercentage) + " - " + (moatPercentage + throwPercentage + sheepPercentage + chargePercentage + projectilePercentage));

        if(random <= projectilePercentage && random > 0) {
            AttackProjectile attackProjectile = new AttackProjectile(_mainPlugin, _myCow);
            attackProjectile.Projectile();
            Bukkit.broadcastMessage("Projectile");
        }
        else if (random <= chargePercentage + projectilePercentage && random > projectilePercentage) {
            AttackCharge attackCharge = new AttackCharge(_mainPlugin, _myCow);
            attackCharge.Charge();
            Bukkit.broadcastMessage("Charge");
        }
        else if (random > sheepPercentage + chargePercentage + projectilePercentage && random > chargePercentage + projectilePercentage) {
            AttackSheep attackSheep = new AttackSheep(_mainPlugin, _myCow);
            attackSheep.Sheep();
            Bukkit.broadcastMessage("Sheep");
        }
        else if (random > throwPercentage + sheepPercentage + chargePercentage + projectilePercentage && random > sheepPercentage + chargePercentage + projectilePercentage) {
            AttackThrow attackThrow = new AttackThrow(_mainPlugin, _myCow);
            attackThrow.Throw();
            Bukkit.broadcastMessage("Throw");
        }
        else if (random > moatPercentage + throwPercentage + sheepPercentage + chargePercentage + projectilePercentage && random > throwPercentage + sheepPercentage + chargePercentage + projectilePercentage) {
            AttackMoat attackMoat = new AttackMoat(_mainPlugin, _myCow);
            attackMoat.Moat();
            Bukkit.broadcastMessage("Moat");
        }
        else if (random > tunnelPercentage + moatPercentage + throwPercentage + sheepPercentage + chargePercentage + projectilePercentage && random > moatPercentage + throwPercentage + sheepPercentage + chargePercentage + projectilePercentage) {
            AttackTunnel attackTunnel = new AttackTunnel(_mainPlugin, _myCow);
            attackTunnel.Tunnel();
            Bukkit.broadcastMessage("Tunnel");
        }
    }
}
