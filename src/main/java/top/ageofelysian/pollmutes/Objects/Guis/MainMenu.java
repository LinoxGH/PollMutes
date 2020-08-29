package top.ageofelysian.pollmutes.Objects.Guis;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import top.ageofelysian.pollmutes.PollMutes;

import java.util.ArrayList;
import java.util.List;

public class MainMenu {
    private Inventory inv;

    public MainMenu() {
        init();
    }

    private void init() {

        inv = Bukkit.createInventory(null, 45, PollMutes.getStorage().getTag() + ChatColor.BLACK + " Main Menu");
        inv.setContents(PollMutes.getStorage().getDefaultGui().getContents());

        //Initialization of help booklet.
        final ItemStack help = new ItemStack(Material.KNOWLEDGE_BOOK);
        final ItemMeta helpMeta = help.getItemMeta();

        helpMeta.setDisplayName(ChatColor.AQUA + "Poll-Mute Help!");

        final List<String> helpLores = new ArrayList<>();
        helpLores.add(0, ChatColor.DARK_AQUA + "Click the paper at right to start a new poll-mute.");
        helpLores.add(1, ChatColor.DARK_AQUA + "The starters of poll-mutes are kept anonymous.");
        helpMeta.setLore(helpLores);

        help.setItemMeta(helpMeta);
        inv.setItem(20, help);

        //Initialization of starting paper.
        final ItemStack start = new ItemStack(Material.PAPER);
        final ItemMeta startMeta = start.getItemMeta();

        startMeta.setDisplayName(ChatColor.AQUA + "Start a Poll-Mute!");

        final List<String> startLores = new ArrayList<>();
        startLores.add(0, ChatColor.DARK_AQUA + "Click on me to start a poll on muting a player.");
        startLores.add(1, ChatColor.DARK_RED + "Please note that you cannot cancel your poll-mute.");
        startMeta.setLore(startLores);

        start.setItemMeta(startMeta);
        inv.setItem(24, start);
    }

    public Inventory getInv() { return inv; }
}
