package me.jet315.repairtokens.manager;

import me.jet315.repairtokens.Core;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RepairManager {

    private HashMap<String,RepairItem> validTokens = new HashMap<>();
    private ArrayList<Material> repairableItems = new ArrayList<>();


    public RepairManager(Core instance){
        loadValidTokens(instance);
        loadRepairableItems(instance);
    }

    private void loadValidTokens(Core instance){
        //Getting the configuration file
        FileConfiguration config = instance.getConfig();
        //Loop through the different repair items
        for (String repairName : config.getConfigurationSection("RepairItems").getKeys(false)) {
            try {
                //Get the path as a string, so it is easy to get future values from the config
                String path = "RepairItems." + repairName;
                //Gets the material
                Material material = Material.valueOf(config.getString(path + ".item"));
                //If they enter an invalid material, this wil catch it
                if(material == null){
                    System.out.println("The material " + config.getStringList(path+".item") + " is invalid");
                    continue;
                }

                int data = config.getInt(path+".data");
                int repairDurability = config.getInt(path + ".repairDurability");

                //Create the itemstack
                ItemStack repairItem = new ItemStack(material,1,(short)data);

                //Getting the lore, however it will not be formatted correctly, this formats each line
                List<String> lore = config.getStringList(path+".lore");
                ArrayList<String> formattedLore = new ArrayList<>();
                if(lore != null && lore.size() > 0){
                    for(String lineInLore : lore){
                        formattedLore.add(ChatColor.translateAlternateColorCodes('&',lineInLore).replaceAll("%DURABILITY%",String.valueOf(repairDurability)));
                    }
                }
                //getting the itemmeta for the itemstack
                ItemMeta itemMeta = repairItem.getItemMeta();

                itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&',config.getString(path+".name")));
                itemMeta.setLore(formattedLore);

                //Sets an invisible glow on the item, if enabled
                if(config.getBoolean(path+".setGlow")){
                    itemMeta.addEnchant(Enchantment.SILK_TOUCH, 1, false);
                    itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                }
                repairItem.setItemMeta(itemMeta);
                Sound sound;
                if(config.getString(path + ".sound").equalsIgnoreCase("none")){
                    sound = null;
                }else{
                    sound = Sound.valueOf(config.getString(path + ".sound"));
                }
                boolean spawnParticles = config.getBoolean(path + ".spawnParticles",true);


                RepairItem item = new RepairItem(repairItem,repairDurability,sound,spawnParticles);
                validTokens.put(repairName,item);
            }catch (Exception e){
                System.out.println("Error occurred while processing the item " + repairName);
                System.out.println("Details about the error: " + e);
            }
        }
    }

    private void loadRepairableItems(Core instance){
        List<String> list = instance.getConfig().getStringList("ValidItems");
        for(String itemStack : list){
            Material material = Material.valueOf(itemStack);
            if(material != null){
                repairableItems.add(material);
            }else{
                System.out.println("Error loading material " + itemStack +" (Material does not exist)");
            }
        }
    }

    public HashMap<String,RepairItem> getValidTokens() {
        return validTokens;
    }

    public ArrayList<Material> getRepairableItems() {
        return repairableItems;
    }
}
