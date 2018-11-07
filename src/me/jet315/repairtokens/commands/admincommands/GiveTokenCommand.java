package me.jet315.repairtokens.commands.admincommands;

import me.jet315.repairtokens.Core;
import me.jet315.repairtokens.commands.CommandExecutor;
import me.jet315.repairtokens.manager.RepairItem;
import me.jet315.repairtokens.utils.NBTUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class GiveTokenCommand extends CommandExecutor {

    public GiveTokenCommand() {
        setCommand("give");
        setPermission("repairtoken.admin.give");
        setLength(4);
        setBoth();
        setUsage("/repairtoken give <player> <repairtoken> <amount>");


    }


    @Override
    public void execute(CommandSender sender, String[] args) {
        Player targetPlayer = Bukkit.getPlayer(args[1]);
        if (targetPlayer == null) {
            sender.sendMessage(Core.getInstance().getProperties().getPluginPrefix() + ChatColor.RED + "The player + " + args[1] + " is not online!");
            return;
        }
        RepairItem repairItem = Core.getInstance().getRepairManager().getValidTokens().get(args[2]);
        if (repairItem == null) {
            sender.sendMessage(Core.getInstance().getProperties().getPluginPrefix() + ChatColor.RED + "The Repair Token " + args[2] + " does not exist!");
            return;
        }
        int amount;
        try {
            amount = Integer.parseInt(args[3]);
            if (amount == 0) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            sender.sendMessage(Core.getInstance().getProperties().getPluginPrefix() + ChatColor.RED + args[3] + " is not a valid, positive integer!");
            return;
        }

        sender.sendMessage(Core.getInstance().getProperties().getPluginPrefix() + ChatColor.GREEN + targetPlayer.getName() + " has been given " + amount + " " +repairItem.getItemStack().getItemMeta().getDisplayName());

        ItemStack repairTokenClone = repairItem.getItemStack().clone();

        if(!Core.getInstance().getProperties().isStackable()) {
            HashMap<String,String> map = new HashMap<>();
            map.put(String.valueOf(new Random().nextInt(21490000)),"");
            repairTokenClone = NBTUtils.setNBTData(repairTokenClone,map);
        }
        repairTokenClone.setAmount(amount);
        targetPlayer.getInventory().addItem(repairTokenClone);
        if (!Core.getInstance().getProperties().getReceivedRepairToken().equalsIgnoreCase("none")) {
            targetPlayer.sendMessage(Core.getInstance().getProperties().getReceivedRepairToken().replaceAll("%NAME%", repairItem.getItemStack().getItemMeta().getDisplayName()).replaceAll("%NUMBER%", String.valueOf(amount)).replaceAll("%PREFIX%",Core.getInstance().getProperties().getPluginPrefix()));

        }

    }


}

