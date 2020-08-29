package top.ageofelysian.pollmutes;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class Commands implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            final Player p = (Player) sender;

            if (args.length != 0) return false;

            if (PollMutes.getStorage().getPoll() == null) {
                final Inventory menu = PollMutes.getStorage().getMenu().getInv();

                if (sender.hasPermission("pollmutes.admin")) {

                    p.openInventory(PollMutes.getStorage().getPollUtils().addAdminOptionsMainMenu(menu));
                    return true;
                }

                p.openInventory(menu);
                return true;

            } else {

                final Inventory gui = PollMutes.getStorage().getVoting().getInv();

                if (sender.hasPermission("pollmutes.admin")) {

                    p.openInventory(PollMutes.getStorage().getPollUtils().addAdminOptionsVoting(gui));
                    return true;
                }

                p.openInventory(gui);
                return true;

            }
        } else {
            sender.sendMessage("This command can only be applied in-game!");
            return true;
        }
    }
}
