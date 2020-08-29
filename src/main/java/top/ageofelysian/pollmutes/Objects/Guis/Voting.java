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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Voting {
    private Inventory inv;

    public Voting() {
        init();
    }

    public void setTargetPlayer(Player target) {
        final Inventory temp = Bukkit.createInventory(null, 45, PollMutes.getStorage().getTag() + ChatColor.BLACK + " Voting Interface");
        temp.setContents(inv.getContents());
        this.inv = temp;

        ItemStack skull = inv.getItem(3);
        final ItemMeta skullItemMeta = skull.getItemMeta();

        final String display = skullItemMeta.getDisplayName().replace("%PLAYER%", target.getName());
        skullItemMeta.setDisplayName(display);

        skull.setItemMeta(skullItemMeta);
        skull = SkullUtils.setTexture(skull, target);

        inv.setItem(3, skull);
    }

    public void setReason(String reason) {

        final ItemStack book = new ItemStack(Material.BOOK);
        final ItemMeta bookMeta = book.getItemMeta();

        bookMeta.setDisplayName(ChatColor.AQUA + "The reason of this poll-mute:");

        final List<String> loresBook = new ArrayList<>();

        if (reason != null && !reason.equals(" ")) {

            final int maxLength = PollMutes.getInstance().getConfig().getInt("reason-tooltip-line-length", 30);
            String regex = "^(.{1," + maxLength + "}\\b(\\W|$){0,4}|(.(\\w|\\W){" + maxLength + "}))(\\W*(.*))$";
            while (reason.length() >= maxLength + 1) {
                String match =  reason.replaceAll(regex, "$1");
                if (match.length() <= maxLength) {
                    loresBook.add(ChatColor.GRAY + match);
                } else {
                    loresBook.add(ChatColor.GRAY + match + "-");
                }
                reason = reason.replaceAll(regex, "$6");
            }
            if (!reason.equals("")) {
                loresBook.add(ChatColor.GRAY + reason);
            }
        }

        bookMeta.setLore(loresBook);
        book.setItemMeta(bookMeta);

        inv.setItem(4, book);
    }

    public void updateVoteInfo() {
        final int For = (int) PollMutes.getStorage().getPoll().getVotes().values().stream().filter(f -> f > 0).count();
        final int Against = (int) PollMutes.getStorage().getPoll().getVotes().values().stream().filter(f -> f < 0).count();

        final ItemStack sign = new ItemStack(Material.OAK_SIGN);
        final ItemMeta signMeta = sign.getItemMeta();

        final List<String> lores = new ArrayList<>();
        lores.add(0, ChatColor.DARK_AQUA + "Total vote amount: " + (For + Against));
        lores.add(1, " ");
        lores.add(2, ChatColor.DARK_AQUA + "For vote amount: " + ChatColor.GREEN + For);
        lores.add(3, ChatColor.DARK_AQUA + "Against vote amount: " + ChatColor.DARK_RED + Against);
        signMeta.setLore(lores);

        signMeta.setDisplayName(ChatColor.AQUA + "Current Votes");
        sign.setItemMeta(signMeta);

        inv.setItem(5, sign);
    }

    public void resetVoteInfo() {
        final ItemStack sign = new ItemStack(Material.OAK_SIGN);
        final ItemMeta signMeta = sign.getItemMeta();

        final List<String> lores = new ArrayList<>();
        lores.add(0, ChatColor.DARK_AQUA + "Total vote amount: " + ChatColor.YELLOW + 0);
        lores.add(1, " ");
        lores.add(2, ChatColor.DARK_AQUA + "For vote amount: " + ChatColor.GREEN + 0);
        lores.add(3, ChatColor.DARK_AQUA + "Against vote amount: " + ChatColor.DARK_RED + 0);
        signMeta.setLore(lores);

        signMeta.setDisplayName(ChatColor.AQUA + "Current Votes");
        sign.setItemMeta(signMeta);

        inv.setItem(5, sign);
    }

    private void init() {
        inv = Bukkit.createInventory(null, 45, PollMutes.getStorage().getTag() + ChatColor.BLACK + " Voting for %PLAYER%");
        inv.setContents(PollMutes.getStorage().getDefaultGui().getContents());

        //TOP LINE ITEMS
        //Skull
        final ItemStack skull = new ItemStack(Material.PLAYER_HEAD);
        final ItemMeta skullMeta = skull.getItemMeta();

        skullMeta.setDisplayName(ChatColor.AQUA + "Target player:" + ChatColor.YELLOW + " %PLAYER%");
        skull.setItemMeta(skullMeta);

        inv.setItem(3, skull);

        //Sign
        final ItemStack sign = new ItemStack(Material.OAK_SIGN);
        final ItemMeta signMeta = sign.getItemMeta();

        final List<String> lores = new ArrayList<>();
        lores.add(0, ChatColor.DARK_AQUA + "Total vote amount: " + ChatColor.YELLOW + 0);
        lores.add(1, " ");
        lores.add(2, ChatColor.DARK_AQUA + "For vote amount: " + ChatColor.GREEN + 0);
        lores.add(3, ChatColor.DARK_AQUA + "Against vote amount: " + ChatColor.DARK_RED + 0);
        signMeta.setLore(lores);

        signMeta.setDisplayName(ChatColor.AQUA + "Current Votes");
        sign.setItemMeta(signMeta);

        inv.setItem(5, sign);

        //VOTING SLOTS
        //Green(for)
        final ItemStack greenPane = new ItemStack(Material.GREEN_STAINED_GLASS_PANE);
        final ItemMeta greenPaneMeta = greenPane.getItemMeta();

        greenPaneMeta.setDisplayName(ChatColor.GREEN + "Vote FOR");
        greenPane.setItemMeta(greenPaneMeta);

        final Set<Integer> greenSlots = new HashSet<>();
        greenSlots.add(9);
        greenSlots.add(10);
        greenSlots.add(11);

        greenSlots.add(18);
        greenSlots.add(19);
        greenSlots.add(20);

        greenSlots.add(27);
        greenSlots.add(28);
        greenSlots.add(29);

        for (int slot : greenSlots) {
            inv.setItem(slot, greenPane);
        }

        //Red(against)
        final ItemStack redPane = new ItemStack(Material.RED_STAINED_GLASS_PANE);
        final ItemMeta redPaneMeta = redPane.getItemMeta();

        redPaneMeta.setDisplayName(ChatColor.DARK_RED + "Vote AGAINST");
        redPane.setItemMeta(redPaneMeta);

        final Set<Integer> redSlots = new HashSet<>();
        redSlots.add(15);
        redSlots.add(16);
        redSlots.add(17);

        redSlots.add(24);
        redSlots.add(25);
        redSlots.add(26);

        redSlots.add(33);
        redSlots.add(34);
        redSlots.add(35);

        for (int slot : redSlots) {
            inv.setItem(slot, redPane);
        }

        //Yellow(revoke)
        final ItemStack yellowPane = new ItemStack(Material.YELLOW_STAINED_GLASS_PANE);
        final ItemMeta yellowPaneMeta = yellowPane.getItemMeta();

        yellowPaneMeta.setDisplayName(ChatColor.YELLOW + "Revoke Vote");
        yellowPane.setItemMeta(yellowPaneMeta);

        inv.setItem(22, yellowPane);

        final ItemStack book = new ItemStack(Material.BOOK);
        final ItemMeta bookMeta = book.getItemMeta();

        bookMeta.setDisplayName(ChatColor.AQUA + "The reason of this poll-mute:");

        final List<String> loresBook = new ArrayList<>();

        loresBook.add(ChatColor.RED + "Currently there are no reasons set.");
        loresBook.add(ChatColor.RED + "If you are the starter of this poll, click me to add the reason.");

        bookMeta.setLore(loresBook);
        book.setItemMeta(bookMeta);

        inv.setItem(4, book);
    }

    public Inventory getInv() { return inv; }
}
