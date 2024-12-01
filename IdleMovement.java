package incineratorcow2.incineratorcow2;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Cow;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

//public class IdleMovement extends IncineratorCow {
public class IdleMovement extends IncineratorCow {
    JavaPlugin _mainPlugin;
    Cow _myCow;
    public IdleMovement(JavaPlugin mainPlugin, Cow myCow) {
        super(mainPlugin, myCow);
        _mainPlugin = mainPlugin;
        _myCow = myCow;
    }

    public void RandomMovement() {
        Location loc = _myCow.getLocation();
        double X = rand.nextInt(40) + 20;
        double Z = rand.nextInt(40) + 20;
        Vector target = new Vector(X, loc.getY(), Z);
        Vector currentPos = loc.toVector();

        _myCow.setVelocity(target.subtract(currentPos).normalize().multiply(0.5));
    }

    public void CircleMovement(double h, double k, double r, double desiredZ) {
        Location loc = _myCow.getLocation();
        //Bukkit.broadcastMessage("h: " + h + "k: " + k + "r: " + r + "desiredZ: " + desiredZ);
        //double x = loc.getX();
        double y = loc.getY();
        ///double z = loc.getZ();
        //double desiredZ = z + (k-z)/Math.abs(k-z);
        double desiredX = h + Math.sqrt(Math.pow(r, 2) - Math.pow(desiredZ - k, 2));

        Vector target = new Vector(desiredX, y, desiredZ);
        Vector currentPos = loc.toVector();

        _myCow.setVelocity(target.subtract(currentPos).normalize().multiply(0.5));

        //Bukkit.broadcastMessage("testing");
    }
}
