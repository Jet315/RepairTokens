package me.jet315.repairtokens.commands.admincommands;

import me.jet315.repairtokens.Core;
import me.jet315.repairtokens.commands.CommandExecutor;
import me.jet315.repairtokens.manager.RepairItem;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;


public class ListTokensCommand extends CommandExecutor {

    /**
     * Reloads the configuration file
     */

    public ListTokensCommand() {
        setCommand("list");
        setPermission("repairtoken.admin.list");
        setLength(1);
        setPlayer();
        setUsage("/repairtoken list");

    }


    @Override
    public void execute(CommandSender sender, String[] args) {
        Player p = (Player) sender;
        p.sendMessage(ChatColor.translateAlternateColorCodes('&',"&7&m------------------"+Core.getInstance().getProperties().getPluginPrefix().replaceAll(" ","")+"&7&m------------------"));
        if(Core.getInstance().getRepairManager().getValidTokens().values().size() == 0){
            p.sendMessage(ChatColor.GREEN + "There isn't currently any active Repair Tokens");
            return;
        }else if(Core.getInstance().getRepairManager().getValidTokens().values().size() == 1){
            p.sendMessage(ChatColor.GREEN + "There is currently " + ChatColor.RED + "1 active Repair Token");
        }else {
            p.sendMessage(ChatColor.GREEN + "There are currently " + ChatColor.RED + Core.getInstance().getRepairManager().getValidTokens().values().size() + ChatColor.GREEN + " active Repair Tokens");
        }
        int i = 1;
        HashMap<String,RepairItem> validTokens = Core.getInstance().getRepairManager().getValidTokens();
        for(String repairItemString : validTokens.keySet()){

            TextComponent message = new TextComponent(net.md_5.bungee.api.ChatColor.RED + ""+ i + "." + net.md_5.bungee.api.ChatColor.GREEN +" Name: " + net.md_5.bungee.api.ChatColor.LIGHT_PURPLE + repairItemString + net.md_5.bungee.api.ChatColor.AQUA+ "  [" + net.md_5.bungee.api.ChatColor.GOLD +"Hover for information" + net.md_5.bungee.api.ChatColor.AQUA+"]");
          message.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,new ComponentBuilder(net.md_5.bungee.api.ChatColor.translateAlternateColorCodes('&',"&b&l&nInformation\n&cName:&a " + repairItemString +"\n&cDisplay Name:&a " + validTokens.get(repairItemString).getItemStack().getItemMeta().getDisplayName() + "\n&cRepair Durability:&a " + validTokens.get(repairItemString).getDurabilityToRepair() + "\n&cSound Played:&a " + (validTokens.get(repairItemString).getSound() == null ? "NONE" :validTokens.get(repairItemString).getSound().toString()) + "\n\n&f&nClick to Receive an Item")).create()));

          message.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/rt give " + p.getName() + " " + repairItemString + " 1"));
          p.spigot().sendMessage(message);
          i++;

        }

    }

}