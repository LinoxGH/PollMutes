package top.ageofelysian.pollmutes.Events;

import org.bukkit.ChatColor;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import top.ageofelysian.pollmutes.PollMutes;

public class InventoryClose implements Listener {

    @EventHandler
    public void onClose(InventoryCloseEvent event) {
        final HumanEntity p = event.getPlayer();

        if (event.getView().getTitle().equals(PollMutes.getStorage().getTag() + ChatColor.BLACK + " Poll Votes")) {

            PollMutes.getStorage().getPollVotes(p.getUniqueId()).resetCurrentPage();
            PollMutes.getStorage().removePollVotes(p.getUniqueId());

        } else if (event.getView().getTitle().equals(PollMutes.getStorage().getTag() + ChatColor.BLACK + " Poll History")) {

            PollMutes.getStorage().getPollHistory(p.getUniqueId()).resetCurrentPage();
            PollMutes.getStorage().removePollHistory(p.getUniqueId());

        } else if (event.getView().getTitle().equals(PollMutes.getStorage().getTag() + ChatColor.BLACK + " Target Selection")) {

            PollMutes.getStorage().getPollStart(p.getUniqueId()).resetCurrentPage();
            PollMutes.getStorage().removePollStart(p.getUniqueId());

        }
    }
}
