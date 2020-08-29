package top.ageofelysian.pollmutes.Objects.Guis;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import top.ageofelysian.pollmutes.PollMutes;
import top.ageofelysian.pollmutes.Utilities.SkullUtils;

import java.io.File;
import java.util.*;

public class PollHistory {
    private static short currentPage = 0;

    private Inventory inv;
    private Inventory[] pages = null;

    public PollHistory() {
        init();
    }

    private void init() {
        inv = Bukkit.createInventory(null, 45, PollMutes.getStorage().getTag() + ChatColor.BLACK + " Poll History");
        inv.setContents(PollMutes.getStorage().getDefaultGui().getContents());
    }

    public void prepareForPlayerUse(int amount) {

        int pageAmount = 1;
        int temp = amount;

        while (temp > 36) {
            temp -= 36;
            pageAmount++;
        }

        pages = new Inventory[pageAmount];

        final File file = new File(PollMutes.getInstance().getDataFolder().getAbsolutePath() + File.separator + "poll-history.yml");
        final FileConfiguration history = YamlConfiguration.loadConfiguration(file);

        if (history.getKeys(false).size() < amount) amount = history.getKeys(false).size();

        final List<String> keys = new ArrayList<>(history.getKeys(false)).subList(0, amount);

        int i1 = 0;
        while (i1 < pageAmount) {
            final Inventory page = inv;

            if (keys.size() > 0) {

                int i2 = 9;
                while (i2 < 36) {

                    if (keys.size() <= (i2 - 9)) {
                        i2 = 36;
                        continue;
                    }

                    boolean boo = false;
                    if (i1 == 0) {
                        i1++;
                        boo = true;
                    }

                    final ConfigurationSection pollInfo = history.getConfigurationSection(keys.get(i2 - (9 * i1)));

                    ItemStack skull = new ItemStack(Material.PLAYER_HEAD);
                    final ItemMeta skullItemMeta = skull.getItemMeta();

                    final List<String> lores = new ArrayList<>();
                    lores.add(0, ChatColor.AQUA + "Target player: " + pollInfo.getString("target"));

                    if (pollInfo.getBoolean("success")) {

                        lores.add(1, ChatColor.AQUA + "Status: " + ChatColor.GREEN + "Passed.");
                        skullItemMeta.setDisplayName(ChatColor.AQUA + "Poll-#" + (i2 - (9 * i1) + 1) + " " + ChatColor.GREEN + pollInfo.getString("starter").split("/")[0]);

                    } else {

                        lores.add(1, ChatColor.AQUA + "Status: " + ChatColor.DARK_RED + "Failed.");
                        skullItemMeta.setDisplayName(ChatColor.AQUA + "Poll-#" + (i2 - (9 * i1) + 1) + " " + ChatColor.DARK_RED + pollInfo.getString("starter").split("/")[0]);

                    }

                    final String[] words = pollInfo.getString("reason").split(" ");

                    final int maxChars = PollMutes.getInstance().getConfig().getInt("reason-tooltip-line-length", 30);
                    int i = 0;
                    StringBuilder loreSB = new StringBuilder();
                    for (String word : words) {

                        loreSB.append(word);
                        i += word.length();

                        if (i >= maxChars) {
                            lores.add(loreSB.toString());
                            loreSB = new StringBuilder();

                            i = 0;
                        }
                    }

                    lores.add(" ");
                    lores.add(ChatColor.AQUA + "Click on me to see the info about the voters.");

                    skullItemMeta.setLore(lores);
                    skull.setItemMeta(skullItemMeta);

                    skull = SkullUtils.setTexture(skull, Bukkit.getOfflinePlayer(UUID.fromString(pollInfo.getString("target").split("/")[1])));

                    page.setItem(i2, skull);

                    if (boo) i1--;
                    i2++;
                }

            }

            if (i1 != (pageAmount - 1)) {

                final ItemStack next = new ItemStack(Material.PAPER);
                final ItemMeta nextMeta = next.getItemMeta();

                nextMeta.setDisplayName(ChatColor.GREEN + "Click me to go to the next page.");
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
