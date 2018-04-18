package me.jet315.repairtokens.commands.admincommands;

import me.jet315.repairtokens.Core;
import me.jet315.repairtokens.commands.CommandExecutor;
import me.jet315.repairtokens.manager.RepairItem;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class GiveAllTokenCommand extends CommandExecutor {

    public GiveAllTokenCommand() {
        setCommand("give");
        setPermission("repairtoken.admin.giveall");
        setLength(3);
        setBoth();
        setUsage("/repairtoken giveall <repairtoken> <amount>");


    }


    @Override
    public void execute(CommandSender sender, String[] args) {

        RepairItem repairItem = Core.getInstance().getRepairManager().getValidTokens().get(args[1]);
        if(repairItem == null){
            sender.sendMessage(Core.getInstance().getProperties().getPluginPrefix() + ChatColor.RED + "The Repair Token " + args[2] + " does not exist!");
            return;
        }
        int amount;
        try{
            amount = Integer.parseInt(args[2]);
            if(amount == 0) throw new NumberFormatException();
        }catch (NumberFormatException e){
            sender.sendMessage(Core.getInstance().getProperties().getPluginPrefix() + ChatColor.RED + args[3] + " is not a valid, positive integer!");
            return;
        }
        sender.sendMessage(Core.getInstance().getProperties().getPluginPrefix() + ChatColor.GREEN + "Everyone has been given " + amount + repairItem.getItemStack().getItemMeta().getDisplayName());

        ItemStack repairTokenClone = repairItem.getItemStack().clone();
        repairTokenClone.setAmount(amount);
        for(Player player : Bukkit.getOnlinePlayers()){
            player.getInventory().addItem(repairTokenClone);
        }

        if(!Core.getInstance().getProperties().getAllReceivedRepairToken().equalsIgnoreCase("none")){
            String message = Core.getInstance().getProperties().getAllReceivedRepairToken().replaceAll("%NAME%",repairItem.getItemStack().getItemMeta().getDisplayName()).replaceAll("%NUMBER%",String.valueOf(amount));
            for(Player player : Bukkit.getOnlinePlayers()){
                player.sendMessage(message);
            }
        }
    }


}