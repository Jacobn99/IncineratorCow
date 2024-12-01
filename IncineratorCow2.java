package incineratorcow2.incineratorcow2;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.*;
import org.bukkit.plugin.java.JavaPlugin;

public final class IncineratorCow2 extends JavaPlugin {
    IncineratorCow incineratorCow;
    Cow myCow;
    MyEvents myEvents = new MyEvents(this);
    String[] commandNames = {"fire", "charge", "debug", "missileSheep", "moat", "tunnel", "throw"};

    @Override
    public void onEnable() {
        // Plugin startup logic
        this.getServer().getPluginManager().registerEvents(myEvents, this);
        Location worldSpawnLoc = Bukkit.getWorld("World").getSpawnLocation();
        Location spawnLoc = new Location(Bukkit.getWorld("World"), worldSpawnLoc.getX(), worldSpawnLoc.getY() + 10,
                worldSpawnLoc.getZ());

        if (spawnLoc != null) {
            myCow = (Cow) Bukkit.getWorld("World").spawnEntity(spawnLoc,
                    EntityType.COW);
            myCow.addScoreboardTag("God");
            myCow.setGravity(false);
            //myCow.setCollidable(false);
        }

        //PhaseOne phaseOne = new PhaseOne(this, myCow);
        incineratorCow = new IncineratorCow(this, myCow);
        //IdleMovement idleMovement = new IdleMovement(this, myCow);

        CommandExecuter commandExecuter = new CommandExecuter(this, myCow);
        setCommandExecuter(commandExecuter);

        AIManager AI = new AIManager(this, myCow);
        AI.RunPhaseOne();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        myCow.remove();
    }

    private void setCommandExecuter(CommandExecuter commandExecuter) {
        for(String s : commandNames) {
            getCommand(s).setExecutor(commandExecuter);
        }
    }
}