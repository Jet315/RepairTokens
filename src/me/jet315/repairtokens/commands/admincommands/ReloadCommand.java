package me.jet315.repairtokens.commands.admincommands;

import me.jet315.repairtokens.Core;
import me.jet315.repairtokens.commands.CommandExecutor;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class ReloadCommand extends CommandExecutor {

    /**
     * Reloads the configuration file
     */

    public ReloadCommand() {
        setCommand("reload");
        setPermission("repairtoken.admin.reload");
        setLength(1);
        setBoth();
        setUsage("/house reload");

    }


    @Override
    public void execute(CommandSender sender, String[] args) {
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&',Core.getInstance().getProperties().getPluginPrefix() + "&aStarting Reload"));
        long startTime = System.currentTimeMillis();
        Core.getInstance().reloadConfiguration();
        long endtime = System.currentTimeMillis() - startTime;
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&',Core.getInstance().getProperties().getPluginPrefix() + "&aReload Complete: &6" + endtime + "ms"));

    }

}