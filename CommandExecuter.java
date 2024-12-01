package incineratorcow2.incineratorcow2;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Cow;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class CommandExecuter implements CommandExecutor{
    private JavaPlugin _mainPlugin;
    private Cow _myCow;

    public CommandExecuter(JavaPlugin mainPlugin, Cow myCow) {
        _mainPlugin = mainPlugin;
        _myCow = myCow;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (label.equalsIgnoreCase("fire")) {
            if (!IsCommandValid(sender, _myCow)) {
                return false;
            }
            AttackProjectile attack = new AttackProjectile(_mainPlugin, _myCow);
            attack.Projectile();
            return true;
        }
        else if (label.equalsIgnoreCase("charge")) {
            if (!IsCommandValid(sender, _myCow)) {
                return false;
            }
            AttackCharge attack = new AttackCharge(_mainPlugin, _myCow);
            attack.Charge();
            return true;
        }
        else if (label.equalsIgnoreCase("missileSheep")) {
            if (!IsCommandValid(sender, _myCow)) {
                return false;
            }
            AttackSheep attack = new AttackSheep(_mainPlugin, _myCow);
            attack.Sheep();
            return true;
        }
        else if (label.equalsIgnoreCase("moat")) {
            if (!IsCommandValid(sender, _myCow)) {
                return false;
            }
            AttackMoat attack = new AttackMoat(_mainPlugin, _myCow);
            attack.Moat();
            return true;
        }
        else if (label.equalsIgnoreCase("tunnel")) {
            if (!IsCommandValid(sender, _myCow)) {
                return false;
            }
            AttackTunnel tunnel = new AttackTunnel(_mainPlugin, _myCow);
            tunnel.Tunnel();
            return true;
        }
        else if (label.equalsIgnoreCase("throw")) {
            if (!IsCommandValid(sender, _myCow)) {
                return false;
            }
            AttackThrow AttackThrow = new AttackThrow(_mainPlugin, _myCow);
            AttackThrow.Throw();
            return true;
        }
        else if (label.equalsIgnoreCase("debug")) {
            if (!IsCommandValid(sender, _myCow)) {
                return false;
            }

//            AttackThrow2 throw2 = new AttackThrow2(_mainPlugin, _myCow);
//            throw2.Throw2();

            //AIManager AI = new AIManager(_mainPlugin, _myCow);
            //AI.RunPhaseOne2();
            //phaseOne.SwitchTest();
            //myCow.addScoreboardTag("Ready");
            return true;
        }
        return false;
    }

    private boolean IsPlayer(CommandSender sender) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Console can't use this command");
            return false;
        }
        return true;
    }

    private boolean IsCommandValid(CommandSender sender, Cow cow)
    {
        if (!IsPlayer(sender)) {
            return false;
        }
/*
        if (!(sender instanceof Player)) {
            sender.sendMessage("Console can't use this command");
            return false;
        }
*/
        if(cow.isDead()) {
            return false;
        }
        return true;
    }
}
