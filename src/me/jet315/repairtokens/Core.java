package me.jet315.repairtokens;

import me.jet315.repairtokens.commands.CommandHandler;
import me.jet315.repairtokens.listeners.InventoryClick;
import me.jet315.repairtokens.manager.RepairManager;
import me.jet315.repairtokens.utils.Properties;
import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class Core extends JavaPlugin {

    private Properties properties;
    private RepairManager repairManager;
    private static Core instance;

    public static Core getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        long startTime = System.currentTimeMillis();
        System.out.println("\n[RepairToken] Initializing Plugin");

        instance = this;

        this.properties = new Properties(this);
        this.repairManager = new RepairManager(this);
        //Listeners
        Bukkit.getPluginManager().registerEvents(new InventoryClick(this),this);

        //Register Command
        getCommand("repairtoken").setExecutor(new CommandHandler());
        System.out.println("[RepairToken] Initializing Complete - Time took " + String.valueOf(System.currentTimeMillis()-startTime) +"Ms\n");

    }

    @Override
    public void onDisable() {

    }

    public void reloadConfiguration(){
        this.properties = null;
        this.repairManager = null;

        this.properties = new Properties(this);
        this.repairManager = new RepairManager(this);
    }

    public Properties getProperties() {
        return properties;
    }

    public RepairManager getRepairManager() {
        return repairManager;
    }
}
