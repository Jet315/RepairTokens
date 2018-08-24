package me.jet315.repairtokens.listeners;

import me.jet315.repairtokens.Core;
import me.jet315.repairtokens.manager.RepairItem;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.ItemStack;

public class CraftingListener implements Listener {

    private Core instance;

    public CraftingListener(Core instance) {
        this.instance = instance;
    }

    @EventHandler
    public void onCraft(CraftItemEvent e){
        for(ItemStack item : e.getInventory().getMatrix()){
            for(RepairItem repairItem : instance.getRepairManager().getValidTokens().values()){
                if(repairItem.isRepairItem(item)){
                    e.setCancelled(true);
                }

            }
        }
    }
}
