package me.jet315.repairtokens.manager;

import org.bukkit.Sound;
import org.bukkit.inventory.ItemStack;

public class RepairItem {


    private ItemStack itemStack;
    private int durabilityToRepair;
    private Sound sound;
    private boolean spawnParticles;

    public RepairItem(ItemStack itemStack, int durabilityToRepair,Sound sound,boolean spawnParticles){
        this.itemStack = itemStack;
        this.durabilityToRepair = durabilityToRepair;
        this.sound = sound;
        this.spawnParticles = spawnParticles;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public int getDurabilityToRepair() {
        return durabilityToRepair;
    }


    /**
     *
     * @return The Sound, Needs a null check
     */
    public Sound getSound() {
        return sound;
    }
    /**
     *
     * @param item The item that is being compared to the repair item
     * @return True if they are the same, false otherwise
     */
    public boolean isRepairItem(ItemStack item){
        if (item == null) return false;
        if(item.getType() == itemStack.getType()){
            if(item.getItemMeta().getDisplayName().equals(itemStack.getItemMeta().getDisplayName())){
                if(itemStack.getItemMeta().getLore() == null){
                    return true;
                }
                if(item.getItemMeta().getLore() != null){
                    if(item.getItemMeta().getLore().equals(itemStack.getItemMeta().getLore())){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean isSpawnParticles() {
        return spawnParticles;
    }
}
