package top.ageofelysian.pollmutes.Events;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.conversations.Conversation;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import top.ageofelysian.pollmutes.Objects.ReasonConversation;
import top.ageofelysian.pollmutes.PollMutes;

public class InventoryClick implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        final Inventory inv = event.getClickedInventory();

        if (inv == null) return;
        if (event.getView().getTitle().contains(PollMutes.getStorage().getTag())) {
            event.setCancelled(true);

            final Player p = (Player) event.getWhoClicked();
            final ItemStack clickedItem = event.getCurrentItem();

            if (event.getView().getTitle().equals(PollMutes.getStorage().getTag() + ChatColor.BLACK + " Main Menu")) {

                if (clickedItem.getType() == Material.PAPER) {

                    PollMutes.getStorage().getPollStart(p.getUniqueId()).prepareForPlayerChoose(p);
                    PollMutes.getStorage().getPollStart(p.getUniqueId()).increaseCurrentPage();

                    p.openInventory(PollMutes.getStorage().getPollStart(p.getUniqueId()).getPages()[0]);

                } else if (clickedItem.getType() == Material.WRITABLE_BOOK) {

                    p.openInventory(PollMutes.getStorage().getHistoryQuantity(p.getUniqueId()).getInv());

                }

            } else if (event.getView().getTitle().equals(PollMutes.getStorage().getTag() + ChatColor.BLACK + " Target Selection")) {

                if (clickedItem.getType() == Material.PAPER) {

                    if (clickedItem.getItemMeta().getDisplayName().equals(ChatColor.GREEN + "Click me to see the next page.")) {

                        int page = PollMutes.getStorage().getPollStart(p.getUniqueId()).getCurrentPage();
                        page++;

                        PollMutes.getStorage().getPollStart(p.getUniqueId()).increaseCurrentPage();
                        p.openInventory(PollMutes.getStorage().getPollStart(p.getUniqueId()).getPages()[page]);

                    } else if (clickedItem.getItemMeta().getDisplayName().equals(ChatColor.RED + "Click me to go back to the previous page.")) {

                        int page = PollMutes.getStorage().getPollStart(p.getUniqueId()).getCurrentPage();
                        page--;

                        PollMutes.getStorage().getPollStart(p.getUniqueId()).decreaseCurrentPage();
                        p.openInventory(PollMutes.getStorage().getPollStart(p.getUniqueId()).getPages()[page]);

                    }

                } else if (clickedItem.getType() == Material.PLAYER_HEAD) {

                    final ItemMeta clickedItemMeta = clickedItem.getItemMeta();
                    String targetName = clickedItemMeta.getDisplayName();
                    targetName = targetName.replace(ChatColor.AQUA.toString(), "");

                    final Player target = Bukkit.getPlayer(targetName);

                    PollMutes.getStorage().getVoting().setTargetPlayer(target);

                    PollMutes.getStorage().getPollUtils().startPoll(p, target);
                    p.closeInventory();

                    final Conversation conv = new Conversation(PollMutes.getInstance(), p, new ReasonConversation());
                    p.beginConversation(conv);
                }

            } else if (event.getView().getTitle().equals(PollMutes.getStorage().getTag() + ChatColor.BLACK + " Poll Votes")) {

                if (clickedItem.getType() == Material.PAPER) {

                    if (clickedItem.getItemMeta().getDisplayName().equals(ChatColor.GREEN + "Click me to see the next page.")) {

                        int page = PollMutes.getStorage().getPollVotes(p.getUniqueId()).getCurrentPage();
                        page++;

                        PollMutes.getStorage().getPollVotes(p.getUniqueId()).increaseCurrentPage();
                        p.openInventory(PollMutes.getStorage().getPollVotes(p.getUniqueId()).getPages()[page]);

                    } else if (clickedItem.getItemMeta().getDisplayName().equals(ChatColor.RED + "Click me to go back to the previous page.")) {

                        int page = PollMutes.getStorage().getPollVotes(p.getUniqueId()).getCurrentPage();
                        page--;

                        PollMutes.getStorage().getPollVotes(p.getUniqueId()).decreaseCurrentPage();
                        p.openInventory(PollMutes.getStorage().getPollVotes(p.getUniqueId()).getPages()[page]);

                    }

                }

            } else if (event.getView().getTitle().equals(PollMutes.getStorage().getTag() + ChatColor.BLACK + " Poll History")) {

                if (clickedItem.getType() == Material.PAPER) {

                    if (clickedItem.getItemMeta().getDisplayName().equals(ChatColor.GREEN + "Click me to see the next page.")) {

                        int page = PollMutes.getStorage().getPollHistory(p.getUniqueId()).getCurrentPage();
                        page++;

                        PollMutes.getStorage().getPollHistory(p.getUniqueId()).increaseCurrentPage();
                        p.openInventory(PollMutes.getStorage().getPollHistory(p.getUniqueId()).getPages()[page]);

                    } else if (clickedItem.getItemMeta().getDisplayName().equals(ChatColor.RED + "Click me to go back to the previous page.")) {

                        int page = PollMutes.getStorage().getPollHistory(p.getUniqueId()).getCurrentPage();
                        page--;

                        PollMutes.getStorage().getPollHistory(p.getUniqueId()).decreaseCurrentPage();
                        p.openInventory(PollMutes.getStorage().getPollHistory(p.getUniqueId()).getPages()[page]);

                    }

                } else if (clickedItem.getType() == Material.PLAYER_HEAD) {

                    PollMutes.getStorage().getPollVotes(p.getUniqueId()).prepareForAdminView(event.getSlot(), PollMutes.getStorage().getPollHistory(p.getUniqueId()).getCurrentPage());

                    PollMutes.getStorage().removePollHistory(p.getUniqueId());
                    p.openInventory(PollMutes.getStorage().getPollVotes(p.getUniqueId()).getPages()[0]);
                }
            } else if (event.getView().getTitle().equals(PollMutes.getStorage().getTag() + ChatColor.BLACK + " Quantity Insertion")) {

                if (clickedItem.getItemMeta().getDisplayName().equals(ChatColor.GREEN + "Add 1")) {

                    PollMutes.getStorage().getHistoryQuantity(p.getUniqueId()).addQuantity((short) 1);
                    p.openInventory(PollMutes.getStorage().getHistoryQuantity(p.getUniqueId()).getInv());

                } else if (clickedItem.getItemMeta().getDisplayName().equals(ChatColor.GREEN + "Add 10")) {

                    PollMutes.getStorage().getHistoryQuantity(p.getUniqueId()).addQuantity((short) 10);
                    p.openInventory(PollMutes.getStorage().getHistoryQuantity(p.getUniqueId()).getInv());

                } else if (clickedItem.getItemMeta().getDisplayName().equals(ChatColor.RED + "Remove 1")) {

                    PollMutes.getStorage().getHistoryQuantity(p.getUniqueId()).addQuantity((short) -1);
                    p.openInventory(PollMutes.getStorage().getHistoryQuantity(p.getUniqueId()).getInv());

                } else if (clickedItem.getItemMeta().getDisplayName().equals(ChatColor.RED + "Remove 10")) {

                    PollMutes.getStorage().getHistoryQuantity(p.getUniqueId()).addQuantity((short) -10);
                    p.openInventory(PollMutes.getStorage().getHistoryQuantity(p.getUniqueId()).getInv());

                } else if (clickedItem.getItemMeta().getDisplayName().equals(ChatColor.AQUA + "Finish")) {

                    final short quantity = PollMutes.getStorage().getHistoryQuantity(p.getUniqueId()).getQuantity();

                    if (quantity < 0) {
                        PollMutes.getStorage().getHistoryQuantity(p.getUniqueId()).resetQuantity();
                        PollMutes.getStorage().removeHistoryQuantity(p.getUniqueId());

                        p.closeInventory();
                        p.sendMessage(PollMutes.getStorage().getTag() + ChatColor.DARK_RED + " You cannot enter a negative value!");
                        return;
                    }

                    PollMutes.getStorage().getHistoryQuantity(p.getUniqueId()).resetQuantity();
                    PollMutes.getStorage().removeHistoryQuantity(p.getUniqueId());

                    PollMutes.getStorage().getPollHistory(p.getUniqueId()).prepareForPlayerUse(quantity);
                    p.openInventory(PollMutes.getStorage().getPollHistory(p.getUniqueId()).getPages()[0]);

                }
            } else if (event.getView().getTitle().equals(PollMutes.getStorage().getTag() + ChatColor.BLACK + " Cancellation Check")) {

                if (clickedItem.getItemMeta().getDisplayName().equals(ChatColor.GREEN + "YES")) {

                    p.sendMessage(PollMutes.getStorage().getTag() + ChatColor.RED + " Cancelled the poll-mute.");
                    Bukkit.getLogger().info("The poll-mute on player " + PollMutes.getStorage().getPoll().getTarget().getName() + "/" + PollMutes.getStorage().getPoll().getTarget().getUniqueId().toString() + " got cancelled by player " + p.getName() + "/" + p.getUniqueId().toString() + ".");

                    PollMutes.getStorage().getPollUtils().endPoll(true);
                    p.closeInventory();

                } else if (clickedItem.getItemMeta().getDisplayName().equals(ChatColor.RED + "NO")) {

                    p.closeInventory();

                }
            } else if (event.getView().getTitle().equals(PollMutes.getStorage().getTag() + ChatColor.BLACK + " Voting Interface")) {
                if (PollMutes.getStorage().getPoll() == null) return;

                if (clickedItem.getType() == Material.WRITABLE_BOOK) {

                    p.openInventory(PollMutes.getStorage().getHistoryQuantity(p.getUniqueId()).getInv());

                } else if (clickedItem.getType() == Material.BARRIER) {

                    p.openInventory(PollMutes.getStorage().getSureQuestion().getInv());

                } else {

                    if (clickedItem.getItemMeta().getDisplayName().equals(ChatColor.GREEN + "Vote FOR")) {

                        if (p.getUniqueId().equals(PollMutes.getStorage().getPoll().getStarter().getUniqueId())) {
                            p.sendMessage(PollMutes.getStorage().getTag() + ChatColor.DARK_RED + " You cannot vote on a poll you started!");
                            p.closeInventory();
                            return;
                        } else if (p.getUniqueId().equals(PollMutes.getStorage().getPoll().getTarget().getUniqueId())) {
                            p.sendMessage(PollMutes.getStorage().getTag() + ChatColor.DARK_RED + " You cannot vote on a poll targeting you!");
                            p.closeInventory();
                            return;
                        }

                        PollMutes.getStorage().getPoll().addVote(p, 1);
                        p.closeInventory();

                    } else if (clickedItem.getItemMeta().getDisplayName().equals(ChatColor.DARK_RED + "Vote AGAINST")) {

                        if (p.getUniqueId().equals(PollMutes.getStorage().getPoll().getStarter().getUniqueId())) {
                            p.sendMessage(PollMutes.getStorage().getTag() + ChatColor.DARK_RED + " You cannot vote on a poll you started!");
                            p.closeInventory();
                            return;
                        } else if (p.getUniqueId().equals(PollMutes.getStorage().getPoll().getTarget().getUniqueId())) {
                            p.sendMessage(PollMutes.getStorage().getTag() + ChatColor.DARK_RED + " You cannot vote on a poll targeting you!");
                            p.closeInventory();
                            return;
                        }

                        PollMutes.getStorage().getPoll().addVote(p, -1);
                        p.closeInventory();

                    } else if (clickedItem.getItemMeta().getDisplayName().equals(ChatColor.YELLOW + "Revoke Vote")) {

                        if (p.getUniqueId().equals(PollMutes.getStorage().getPoll().getStarter().getUniqueId())) {
                            p.sendMessage(PollMutes.getStorage().getTag() + ChatColor.DARK_RED + " You cannot vote on a poll you started!");
                            p.closeInventory();
                            return;
                        } else if (p.getUniqueId().equals(PollMutes.getStorage().getPoll().getTarget().getUniqueId())) {
                            p.sendMessage(PollMutes.getStorage().getTag() + ChatColor.DARK_RED + " You cannot vote on a poll targeting you!");
                            p.closeInventory();
                            return;
                        }

                        PollMutes.getStorage().getPoll().removeVote(p);
                        p.closeInventory();

                    } else if (clickedItem.getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.RED + "Instantly end this poll now.")) {

                        PollMutes.getStorage().getPollUtils().endPoll(false);
                        p.closeInventory();
                    }

                }
            }
        }
    }
}