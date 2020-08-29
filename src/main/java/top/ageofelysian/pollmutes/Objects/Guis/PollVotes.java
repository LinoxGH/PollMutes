package top.ageofelysian.pollmutes.Objects.Guis;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import top.ageofelysian.pollmutes.Objects.Poll;
import top.ageofelysian.pollmutes.PollMutes;
import top.ageofelysian.pollmutes.Utilities.SkullUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PollVotes {
    private short currentPage = 0;

    private Inventory[] pages = null;

    public void prepareForAdminView(final int itemIndex, final int pageId) {
        final Poll poll = PollMutes.getStorage().getPollUtils().getPoll(itemIndex, pageId);

        final List<Map.Entry<OfflinePlayer, Float>> votes = new ArrayList<>(poll.getVotes().entrySet());

        int temp = votes.size();

        int pageAmount = 1;
        while (temp > 36) {
            temp -= 36;
            pageAmount++;
        }

        pages = new Inventory[pageAmount];

        int i1 = 0;
        while (i1 < pageAmount) {

            final Inventory inv = Bukkit.createInventory(null, 45, PollMutes.getStorage().getTag() + ChatColor.BLACK + " Poll Votes");
            inv.setContents(PollMutes.getStorage().getDefaultGui().getContents());

            if (votes.size() > 0) {

                int i2 = 9;
                while (i2 < 36) {

                    if (votes.size() <= (i2 - 9)) {
                        i2 = 36;
                        continue;
                    }

                    final Map.Entry<OfflinePlayer, Float> entry = votes.get(i2 - 9);

                    ItemStack skull = new ItemStack(Material.PLAYER_HEAD);
                    final ItemMeta skullItemMeta = skull.getItemMeta();

                    final List<String> lores = new ArrayList<>();
                    if (entry.getValue() > 0) {

                        skullItemMeta.setDisplayName(ChatColor.AQUA + "Player: " + ChatColor.GREEN + entry.getKey().getName());
                        lores.add(0, ChatColor.DARK_AQUA + "The casted vote: " + ChatColor.GREEN + "FOR");

                    } else {

                        skullItemMeta.setDisplayName(ChatColor.AQUA + "Player: " + ChatColor.DARK_RED + entry.getKey().getName());
                        lores.add(0, ChatColor.DARK_AQUA + "The casted vote: " + ChatColor.DARK_RED + "AGAINST");

                    }

                    lores.add(1, ChatColor.AQUA + "UUID: " + ChatColor.YELLOW + entry.getKey().getUniqueId().toString());
                    skullItemMeta.setLore(lores);

                    skull.setItemMeta(skullItemMeta);

                    skull = SkullUtils.setTexture(skull, entry.getKey());

                    inv.setItem(i2, skull);
                    i2++;
                }
            }

            if (i1 != (pageAmount - 1)) {

                final ItemStack next = new ItemStack(Material.PAPER);
                final ItemMeta nextMeta = next.getItemMeta();

                nextMeta.setDisplayName(ChatColor.GREEN + "Click me to see the next page.");
                next.setItemMeta(nextMeta);

                inv.setItem(44, next);

            } else if (i1 != 0) {

                final ItemStack back = new ItemStack(Material.PAPER);
                final ItemMeta backMeta = back.getItemMeta();

                backMeta.setDisplayName(ChatColor.RED + "Click me to go back to the previous page.");
                back.setItemMeta(backMeta);

                inv.setItem(36, back);

            }

            final ItemStack pageNumber = new ItemStack(Material.PAPER);
            final ItemMeta pageNumberMeta = pageNumber.getItemMeta();

            pageNumberMeta.setDisplayName(ChatColor.AQUA + "Page #" + (i1 + 1));
            pageNumber.setItemMeta(pageNumberMeta);

            inv.setItem(40, pageNumber);

            pages[i1] = inv;
            i1++;
        }
    }

    public Inventory[] getPages() { return pages; }
    public short getCurrentPage() { return currentPage; }

    public void increaseCurrentPage() { currentPage++; }
    public void decreaseCurrentPage() { currentPage--; }

    public void resetCurrentPage() { currentPage = 0; }
}
