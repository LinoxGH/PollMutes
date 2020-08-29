package top.ageofelysian.pollmutes.Utilities;

import de.tr7zw.itemnbtapi.NBTItem;

import org.bukkit.OfflinePlayer;
import org.bukkit.inventory.ItemStack;

public class SkullUtils {

    public static ItemStack setTexture(final ItemStack stack, final OfflinePlayer player) {
        final NBTItem nbti = new NBTItem(stack);
        nbti.setString("SkullOwner", player.getName());

        return nbti.getItem();
    }
}
