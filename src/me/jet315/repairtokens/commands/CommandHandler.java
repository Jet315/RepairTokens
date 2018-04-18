package me.jet315.repairtokens.commands;

import me.jet315.repairtokens.Core;
import me.jet315.repairtokens.commands.admincommands.GiveAllTokenCommand;
import me.jet315.repairtokens.commands.admincommands.GiveTokenCommand;
import me.jet315.repairtokens.commands.admincommands.ListTokensCommand;
import me.jet315.repairtokens.commands.admincommands.ReloadCommand;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class CommandHandler implements org.bukkit.command.CommandExecutor {

    private Map<String, CommandExecutor> commands = new HashMap<String, CommandExecutor>();

    public CommandHandler() {
        //register sub commands
        commands.put("give", new GiveTokenCommand());
        commands.put("giveall", new GiveAllTokenCommand());
        commands.put("reload", new ReloadCommand());
        commands.put("list", new ListTokensCommand());

    }
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {

        if (cmd.getName().equalsIgnoreCase("repairtoken")) {
            if (args.length == 0) {
                if(sender.hasPermission("repairtoken.help")){
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&',"&7&m------------"+Core.getInstance().getProperties().getPluginPrefix().replaceAll(" ","")+"&7&m------------"));
                    sender.sendMessage(ChatColor.GREEN + "/repairtoken give <player> <repairtoken> <amount>");
                    sender.sendMessage(ChatColor.GREEN + "/repairtoken giveall <repairtoken> <amount>");
                    sender.sendMessage(ChatColor.GREEN + "/repairtoken reload");
                    sender.sendMessage(ChatColor.GREEN + "/repairtoken list");
                    return true;
                }else{
                    sender.sendMessage(Core.getInstance().getProperties().getNoPermission().replaceAll("%PREFIX%",Core.getInstance().getProperties().getPluginPrefix()));
                }
            }


            if (args[0] != null) {
                String name = args[0].toLowerCase();
                if (commands.containsKey(name)) {
                    final CommandExecutor command = commands.get(name);

                    if (command.getPermission() != null && !sender.hasPermission(command.getPermission())) {
                        sender.sendMessage(Core.getInstance().getProperties().getNoPermission().replaceAll("%PREFIX%",Core.getInstance().getProperties().getPluginPrefix()));
                        return true;

                    }

                    if (!command.isBoth()) {
                        if (command.isConsole() && sender instanceof Player) {
                            sender.sendMessage(ChatColor.RED + "Only console can use that command!");
                            return true;
                        }
                        if (command.isPlayer() && sender instanceof ConsoleCommandSender) {
                            sender.sendMessage(ChatColor.RED + "Only players can use that command!");
                            return true;
                        }
                    }

                    if (command.getLength() > args.length) {
                        sender.sendMessage(ChatColor.RED + "Usage: " + command.getUsage());
                        return true;
                    }

                    command.execute(sender, args);
                    return true;
                }
            }
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&',Core.getInstance().getProperties().getPluginPrefix() + "&cUnknown Command"));
        }

        if(sender.hasPermission("repairtoken.help")){
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&',"&7&m-------"+Core.getInstance().getProperties().getPluginPrefix().replaceAll(" ","")+"&7&m-------"));
            sender.sendMessage(ChatColor.GREEN + "/repairtoken give <player> <repairtoken> <amount>");
            sender.sendMessage(ChatColor.GREEN + "/repairtoken giveall <repairtoken> <amount>");
            sender.sendMessage(ChatColor.GREEN + "/repairtoken reload");
            sender.sendMessage(ChatColor.GREEN + "/repairtoken list");
            return true;
        }else{
            sender.sendMessage(Core.getInstance().getProperties().getNoPermission().replaceAll("%PREFIX%",Core.getInstance().getProperties().getPluginPrefix()));
        }

        return true;
    }
}
