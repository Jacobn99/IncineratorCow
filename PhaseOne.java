package incineratorcow2.incineratorcow2;

import org.bukkit.Bukkit;
import org.bukkit.entity.Cow;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class PhaseOne extends IncineratorCow implements Abilities {
    JavaPlugin _mainPlugin;

    public PhaseOne(JavaPlugin mainPlugin, Cow myCow) {
        super(mainPlugin, myCow);
        _mainPlugin = mainPlugin;
        _myCow = myCow;
    }
    Cow _myCow;
    public double GetThrowPercentage() {
        double percentage = 0;
        return percentage;
    }
    public double GetChargePercentage() {
        //double percentage = 28;
        double percentage = 64;
        return percentage;
    }
    public double GetMoatPercentage() {
        double percentage = 0;
        return percentage;
    }
    public double GetTunnelPercentage() {
        double percentage = 0;
        return percentage;
    }
    public double GetSheepPercentage() {
        double percentage = 0;
        //double percentage = 0;
        return percentage;
    }
    public double GetProjectilePercentage() {
        double percentage = 36;
        //double percentage = 0;
        return percentage;
    }
}
