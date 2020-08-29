package top.ageofelysian.pollmutes.Objects.Guis;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import top.ageofelysian.pollmutes.PollMutes;
import top.ageofelysian.pollmutes.Utilities.SkullUtils;

import java.util.ArrayList;
import java.util.List;

public class PollStart {
    private static short currentPage = 0;

    private Inventory inv;
    private Inventory[] pages = null;

    public PollStart() {
        init();
    }

    private void init() {
        inv = Bukkit.createInventory(null, 45, PollMutes.getStorage().getTag() + ChatColor.BLACK + " Target Selection");
        inv.setContents(PollMutes.getStorage().getDefaultGui().getContents());

        //Initialization of question
        final ItemStack question = new ItemStack(Material.PAPER);
        final ItemMeta questionMeta = question.getItemMeta();

        questionMeta.setDisplayName(ChatColor.RED + "Please select the targeting player.");
        question.setItemMeta(questionMeta);

        inv.setItem(4, question);
    }

    public void prepareForPlayerChoose(final Player player) {
        final List<Player> players = new ArrayList<>(Bukkit.getOnlinePlayers());
        players.remove(player);

        int amount = 1;
        int size = players.size();

        while (size > 36) {
            size = size - 36;
            amount++;
        }

        pages = new Inventory[amount];

        int i1 = 0;
        while (i1 < amount) {
            final Inventory page = inv;

            if (players.size() > 0) {

                int i2 = 9;
                while (i2 < 36) {

                    if (players.size() <= (i2 - 9)) {
                        i2 = 36;
                        continue;
                    }

                    ItemStack skull = new ItemStack(Material.PLAYER_HEAD);
                    final ItemMeta skullItemMeta = skull.getItemMeta();

                    skullItemMeta.setDisplayName(ChatColor.AQUA + players.get(i2 - 9).getName());
                    skull.setItemMeta(skullItemMeta);

                    skull = SkullUtils.setTexture(skull, players.get(i2 - 9));

                    page.setItem(i2, skull);
                    i2++;
                }

            }

            if (i1 != (amount - 1)) {

                final ItemStack next = new ItemStack(Material.PAPER);
                final ItemMeta nextMeta = next.getItemMeta();

                nextMeta.setDisplayName(ChatColor.GREEN + "Click me to see the next page.");
                next.setItemMeta(nextMeta);

                page.setItem(44, next);

            } else if (i1 != 0) {

                final ItemStack back = new ItemStack(Material.PAPER);
                final ItemMeta backMeta = back.getItemMeta();

                backMeta.setDisplayName(ChatColor.RED + "Click me to go back to the previous page.");
                back.setItemMeta(backMeta);

                page.setItem(36, back);

            }

            final ItemStack pageNumber = new ItemStack(Material.PAPER);
            final ItemMeta pageNumberMeta = pageNumber.getItemMeta();

            pageNumberMeta.setDisplayName(ChatColor.AQUA + "Page #" + (i1 + 1));
            pageNumber.setItemMeta(pageNumberMeta);

            inv.setItem(40, pageNumber);

            pages[i1] = page;
            i1++;
        }
    }

    public Inventory[] getPages() { return pages; }

    public short getCurrentPage() { return currentPage; }

    public void increaseCurrentPage() { currentPage++; }
    public void decreaseCurrentPage() { currentPage--; }

    public void resetCurrentPage() { currentPage = 0; }
}
