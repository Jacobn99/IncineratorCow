package incineratorcow2.incineratorcow2;

import org.bukkit.Location;
import org.bukkit.entity.Cow;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class AttackSmash extends IncineratorCow {
    JavaPlugin _mainPlugin;
    Cow _myCow;

    public AttackSmash(JavaPlugin mainPlugin, Cow myCow) {
        super(mainPlugin, myCow);
        _mainPlugin = mainPlugin;
        _myCow = myCow;
    }

    public void HoverPlayer(/*Player target*/) {
        Player target;

        target = GetRandomPlayer(50);
        new BukkitRunnable() {
            Location pursuedLoc;
            Vector direction;

            @Override
            public void run() {
                RemoveNearbyArea(cow, 2, 5, 5);
                pursuedLoc = target.getLocation();
                pursuedLoc.setY(pursuedLoc.getY() + 10);

                direction = getDirection(cow.getLocation(), pursuedLoc);
                cow.setVelocity(direction);
                CheckCords(cow.getLocation(), 3, 0, pursuedLoc, true, false, true);

            }
        }.runTaskTimer(_mainPlugin, 0, 2);
    }

}

