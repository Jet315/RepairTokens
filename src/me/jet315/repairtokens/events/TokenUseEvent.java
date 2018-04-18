package me.jet315.repairtokens.events;

import me.jet315.repairtokens.manager.RepairItem;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;

public class TokenUseEvent extends Event implements Cancellable {

    /**
     * Called when a user first claims a house
     */

    private static final HandlerList handlers = new HandlerList();

    private Player player;

    private RepairItem repairItem;

    private ItemStack clickedItem;

    private boolean isCancelled = false;

    public TokenUseEvent(Player player, RepairItem repairItem, ItemStack clickedItem) {
        this.player = player;
        this.repairItem = repairItem;
        this.clickedItem = clickedItem;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    /**
     *
     * @return The player who clicked
     */
    public Player getPlayer() {
        return player;
    }

    /**
     *
     * @return Whether the event is canceled
     */
    @Override
    public boolean isCancelled() {
        return isCancelled;
    }
    @Override
    public void setCancelled(boolean b) {
        this.isCancelled = b;
    }

    /**
     *
     * @return The RepairAble item that was used
     */
    public RepairItem getRepairItem() {
        return repairItem;
    }

    /**
     *
     * @return The item that the token has been used on
     */
    public ItemStack getClickedItem() {
        return clickedItem;
    }
}
