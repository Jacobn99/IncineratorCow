package incineratorcow2.incineratorcow2;

import org.bukkit.entity.Cow;
import org.bukkit.plugin.java.JavaPlugin;

public class PhaseTwo extends IncineratorCow implements Abilities {
    JavaPlugin _mainPlugin;
    Cow _myCow;
    public PhaseTwo(JavaPlugin mainPlugin, Cow myCow) {
        super(mainPlugin, myCow);
        _mainPlugin = mainPlugin;
        _myCow = myCow;
    }
    public double GetThrowPercentage() {
        double percentage = 0;
        return percentage;
    }
    public double GetChargePercentage() {
        //double percentage = 28;
        double percentage = 0;
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
        //double percentage = 36;
        double percentage = 0;
        return percentage;
    }
    public double GetProjectilePercentage() {
        //double percentage = 36;
        double percentage = 0;
        return percentage;
    }
}
