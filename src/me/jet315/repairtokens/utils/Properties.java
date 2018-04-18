package me.jet315.repairtokens.utils;

import me.jet315.repairtokens.Core;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;

public class Properties {

    private Core instance;

    /**
     * Stores the plugins prefix
     */
    private String pluginPrefix = "RepairToken";

    /**
     * Stores the message shown if the item clicked is invalid
     */
    private String invalidItemMessage = "Invalid item";

    /**
     * Stores the message if the item is already fully repaired
     */
    private String itemAlreadyFullyRepaired = "Item already fully repaired";
    /**
     * Stores the message if the item has been fully repaired by a repair token
     */
    private String fullyRepaired = "Item has been fully repaired";

    /**
     * Stores the message if the item has been partly repaired by a repair token
     */
    private String partlyRepaired = "Item has been partly repaired";

    /**
     * The message that is shown if the user does not have permission to a command
     */
    private String noPermission = "No Perms!";

    /**
     * The message that is shown when the user receives a token
     */
    private String receivedRepairToken = "You got a repair token!";
    /**
     *  The message that is shown when everyone online receives a token
     */
    private String allReceivedRepairToken = "Everyone got a repair token!";


    public Properties(Core instance){
        this.instance = instance;
        createConfig();
        reloadConfig();
        initialiseValues();
    }

    /**
     * Initialise the values above from the configuration file
     */
    private void initialiseValues(){
        FileConfiguration config = instance.getConfig();

        pluginPrefix = ChatColor.translateAlternateColorCodes('&',config.getString("PluginsPrefix"));
        invalidItemMessage = ChatColor.translateAlternateColorCodes('&',config.getString("InvalidItem"));
        itemAlreadyFullyRepaired = ChatColor.translateAlternateColorCodes('&',config.getString("ItemAlreadyFullyRepaired"));
        fullyRepaired = ChatColor.translateAlternateColorCodes('&',config.getString("FullyRepaired"));
        partlyRepaired = ChatColor.translateAlternateColorCodes('&',config.getString("PartlyRepaired"));
        noPermission = ChatColor.translateAlternateColorCodes('&',config.getString("NoPermission"));

        receivedRepairToken = ChatColor.translateAlternateColorCodes('&',config.getString("ReceivedRepairToken"));
        allReceivedRepairToken = ChatColor.translateAlternateColorCodes('&',config.getString("AllReceivedRepairToken"));


    }

    //Reloads the config
    private void reloadConfig(){
        instance.reloadConfig();
    }
    //Creates the config
    private void createConfig(){
        try {
            if (!instance.getDataFolder().exists()) {
                instance.getDataFolder().mkdirs();
            }
            File file = new File(instance.getDataFolder(), "config.yml");
            if (!file.exists()) {
                instance.getLogger().info("Config.yml not found, creating!");
                instance.saveDefaultConfig();
            } else {
                instance.getLogger().info("Config.yml found, loading!");
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
    }


    /**
     * Getters
     */
    public String getPluginPrefix() {
        return pluginPrefix;
    }

    public String getInvalidItemMessage() {
        return invalidItemMessage;
    }

    public String getItemAlreadyFullyRepaired() {
        return itemAlreadyFullyRepaired;
    }

    public String getFullyRepaired() {
        return fullyRepaired;
    }

    public String getPartlyRepaired() {
        return partlyRepaired;
    }

    public String getNoPermission() {
        return noPermission;
    }

    public String getAllReceivedRepairToken() {
        return allReceivedRepairToken;
    }

    public String getReceivedRepairToken() {
        return receivedRepairToken;
    }
}
