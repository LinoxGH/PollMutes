package top.ageofelysian.pollmutes.Objects.Guis;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import top.ageofelysian.pollmutes.PollMutes;

public class HistoryQuantity {
    private static short quantity = 0;
    private Inventory inv;

    public HistoryQuantity() {
        init();
    }

    private void init() {

        inv = Bukkit.createInventory(null, 45, PollMutes.getStorage().getTag() + ChatColor.BLACK + " Quantity Insertion");
        inv.setContents(PollMutes.getStorage().getDefaultGui().getContents());

        //Initialization of question
        final ItemStack question = new ItemStack(Material.PAPER);
        final ItemMeta questionMeta = question.getItemMeta();

        questionMeta.setDisplayName(ChatColor.RED + "How many old polls would you like to view?");
        question.setItemMeta(questionMeta);

        inv.setItem(4, question);

        final ItemStack add1 = new ItemStack(Material.GREEN_STAINED_GLASS_PANE);
        final ItemMeta add1Meta = add1.getItemMeta();

        add1Meta.setDisplayName(ChatColor.GREEN + "Add 1");
        add1.setItemMeta(add1Meta);

        inv.setItem(20, add1);

        final ItemStack add10 = new ItemStack(Material.GREEN_STAINED_GLASS_PANE);
        final ItemMeta add10Meta = add10.getItemMeta();

        add10Meta.setDisplayName(ChatColor.GREEN + "Add 10");
        add10.setItemMeta(add10Meta);

        inv.setItem(19, add10);

        final ItemStack current = new ItemStack(Material.LIGHT_BLUE_STAINED_GLASS_PANE);
        final ItemMeta currentMeta = current.getItemMeta();

        currentMeta.setDisplayName(ChatColor.AQUA + "Current: " + ChatColor.YELLOW + "0");
        current.setItemMeta(currentMeta);

        inv.setItem(22, current);

        final ItemStack remove1 = new ItemStack(Material.RED_STAINED_GLASS_PANE);
        final ItemMeta remove1Meta = remove1.getItemMeta();

        remove1Meta.setDisplayName(ChatColor.RED + "Remove 1");
        remove1.setItemMeta(remove1Meta);

        inv.setItem(24, remove1);

        final ItemStack remove10 = new ItemStack(Material.RED_STAINED_GLASS_PANE);
        final ItemMeta remove10Meta = remove10.getItemMeta();

        remove10Meta.setDisplayName(ChatColor.RED + "Remove 10");
        remove10.setItemMeta(remove10Meta);

        inv.setItem(25, remove10);

        final ItemStack finish = new ItemStack(Material.LIME_STAINED_GLASS_PANE);
        final ItemMeta finishMeta = finish.getItemMeta();

        finishMeta.setDisplayName(ChatColor.AQUA + "Finish");
        finish.setItemMeta(finishMeta);

        inv.setItem(40, finish);
    }

    public Inventory getInv() { return inv; }
    public short getQuantity() { return quantity; }

    public void addQuantity(final short amount) {

        final ItemStack current = new ItemStack(Material.LIGHT_BLUE_STAINED_GLASS_PANE);
        final ItemMeta currentMeta = current.getItemMeta();

        quantity = (short) (quantity + amount);

        currentMeta.setDisplayName(ChatColor.AQUA + "Current: " + ChatColor.YELLOW + quantity);
        current.setItemMeta(currentMeta);

        inv.setItem(22, current);
    }

    public void resetQuantity() { quantity = 0; }

}
