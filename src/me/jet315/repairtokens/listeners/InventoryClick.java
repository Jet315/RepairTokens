package me.jet315.repairtokens.listeners;

import me.jet315.repairtokens.Core;
import me.jet315.repairtokens.events.TokenUseEvent;
import me.jet315.repairtokens.manager.RepairItem;
import me.jet315.repairtokens.utils.ParticleUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class InventoryClick implements Listener {

    private Core instance;

    public InventoryClick(Core instance) {
        this.instance = instance;
    }

    @EventHandler
    public void invClick(InventoryClickEvent e){
        for(RepairItem item : instance.getRepairManager().getValidTokens().values()){
            if(item.isRepairItem(e.getCursor())){
                ItemStack itemClicked = e.getCurrentItem();
                if(itemClicked.getType() == Material.AIR) return;
                Player p = (Player) e.getWhoClicked();
                //check if item clicked is valid
                if(!instance.getRepairManager().getRepairableItems().contains(itemClicked.getType())){
                    p.sendMessage(instance.getProperties().getInvalidItemMessage().replaceAll("%PREFIX%",instance.getProperties().getPluginPrefix()));
                    return;
                }
                //check if item already fully repaired
                if(itemClicked.getDurability() == 0){
                    p.sendMessage(instance.getProperties().getItemAlreadyFullyRepaired().replaceAll("%PREFIX%",instance.getProperties().getPluginPrefix()));
                    return;
                }

                //Create, and trigger the HouseClaimEvent so others are able to have a say in what happens
                TokenUseEvent tokenUseEvent = new TokenUseEvent(p,item,itemClicked);
                Core.getInstance().getServer().getPluginManager().callEvent(tokenUseEvent);
                if(tokenUseEvent.isCancelled()) return;


                //the durability to repair the item by
                int durability = item.getDurabilityToRepair();

                //check if item will be fully repaired
                if((itemClicked.getDurability() - durability) <= 0){
                    itemClicked.setDurability((short) 0);
                    p.sendMessage(instance.getProperties().getFullyRepaired().replaceAll("%PREFIX%",instance.getProperties().getPluginPrefix()));
                    //e.getCursor().setAmount(e.getCursor().getAmount()-1);
                    consumeItem(p,e.getCursor());
                    if(item.getSound() != null){
                        p.playSound(p.getLocation(),item.getSound(),100,1);
                    }
                    if(item.isSpawnParticles()){
                        ParticleUtils.spawnParticles(ParticleUtils.generateHelix((Player) e.getWhoClicked()),(Player) e.getWhoClicked());
                    }
                    e.setCancelled(true);
                    p.updateInventory();
                    return;
                }
                //else just repair the item partially
                itemClicked.setDurability((short) (itemClicked.getDurability()-durability));
                p.sendMessage(instance.getProperties().getPartlyRepaired().replaceAll("%PREFIX%",instance.getProperties().getPluginPrefix()).replaceAll("%DURABILITY%",String.valueOf(durability)));
                //e.getCursor().setAmount(e.getCursor().getAmount()-1);
                consumeItem(p,e.getCursor());
                if(item.getSound() != null){
                    p.playSound(p.getLocation(),item.getSound(),100,1);
                }
                if(item.isSpawnParticles()){
                    ParticleUtils.spawnParticles(ParticleUtils.generateHelix((Player) e.getWhoClicked()),(Player) e.getWhoClicked());
                }
                e.setCancelled(true);
                p.updateInventory();

            }
        }

    }

    private void consumeItem(Player p,ItemStack itemStack){
        if(itemStack.getAmount() > 1){
            itemStack.setAmount(itemStack.getAmount()-1);
        }else{
           p.setItemOnCursor(null);
           p.updateInventory();
        }

    }

}

