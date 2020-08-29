package top.ageofelysian.pollmutes.Objects.Guis;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import top.ageofelysian.pollmutes.PollMutes;

public class SureQuestion {
    private Inventory inv;

    public SureQuestion() {
        init();
    }

    private void init() {
        inv = Bukkit.createInventory(null, 45, PollMutes.getStorage().getTag() + ChatColor.BLACK + " Cancellation Check");
        inv.setContents(PollMutes.getStorage().getDefaultGui().getContents());

        //Initialization of question
        final ItemStack question = new ItemStack(Material.PAPER);
        final ItemMeta questionMeta = question.getItemMeta();

        questionMeta.setDisplayName(ChatColor.RED + "Are you sure that you want to cancel this poll-mute?");
        question.setItemMeta(questionMeta);

        inv.setItem(4, question);

        //Initialization of answer YES
        final ItemStack answerYes = new ItemStack(Material.GREEN_STAINED_GLASS_PANE, 1);
        final ItemMeta answerYesMeta = answerYes.getItemMeta();

        answerYesMeta.setDisplayName(ChatColor.GREEN + "YES");
        answerYes.setItemMeta(answerYesMeta);

        inv.setItem(21, answerYes);

        //Initialization of answer NO
        final ItemStack answerNo = new ItemStack(Material.RED_STAINED_GLASS_PANE, 1);
        final ItemMeta answerNoMeta = answerNo.getItemMeta();

        answerNoMeta.setDisplayName(ChatColor.RED + "NO");
        answerNo.setItemMeta(answerNoMeta);

        inv.setItem(23, answerNo);
    }

    public Inventory getInv() { return inv; }
}
