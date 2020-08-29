package top.ageofelysian.pollmutes.Utilities;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitTask;
import top.ageofelysian.pollmutes.Objects.Poll;
import top.ageofelysian.pollmutes.PollMutes;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

public class PollUtils {
    private BukkitTask announcement;

    public void startPoll(Player starter, Player target) {

        PollMutes.getStorage().getVoting().resetVoteInfo();
        PollMutes.getStorage().setPoll(new Poll(starter, target, false));

        announcement = Bukkit.getScheduler().runTaskTimer(PollMutes.getInstance(), () -> {

            Bukkit.broadcastMessage(PollMutes.getStorage().getTag() + ChatColor.AQUA + " There is a poll-mute going on!");
            Bukkit.broadcastMessage(PollMutes.getStorage().getTag() + ChatColor.AQUA + " Do not forget to vote with the command " + ChatColor.YELLOW + "/pollmute" + ChatColor.AQUA + "!");

        }, 1, PollMutes.getStorage().getPeriods() * 20);

        Bukkit.getScheduler().runTaskLater(PollMutes.getInstance(), () -> endPoll(false), PollMutes.getStorage().getPollLength() * 1200);

    }

    public void endPoll(boolean cancel) {

        if (PollMutes.getStorage().getPoll() == null) return;

        announcement.cancel();

        if (!cancel) {
            if (PollMutes.getStorage().getPoll().getSuccess()) {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), PollMutes.getStorage().getCommand().replaceAll("%PLAYER%", PollMutes.getStorage().getPoll().getTarget().getName()));

                Bukkit.getLogger().info("Muted player " + PollMutes.getStorage().getPoll().getTarget().getName() + "/" + PollMutes.getStorage().getPoll().getTarget().getUniqueId().toString() + " by the poll of " + PollMutes.getStorage().getPoll().getStarter().getName() + "/" + PollMutes.getStorage().getPoll().getStarter().getUniqueId().toString() + ".");
                Bukkit.broadcastMessage(PollMutes.getStorage().getTag() + ChatColor.GREEN + " Successfully poll-muted the player " + PollMutes.getStorage().getPoll().getTarget().getName() + "!");
            }

            Bukkit.getLogger().info("Logged the messages that the player " + PollMutes.getStorage().getPoll().getStarter().getName() + "/" + PollMutes.getStorage().getPoll().getStarter().getUniqueId().toString() + " saw in the last " + (PollMutes.getStorage().getPollLength() + 5) + " minutes.");

            PollMutes.getStorage().getMessageHistory().save(PollMutes.getStorage().getPoll().getStarter().getUniqueId());
        }

        try {
            save(PollMutes.getStorage().getPoll());
        } catch (IOException e) {
            e.printStackTrace();
        }

        PollMutes.getStorage().setPoll(null);
    }

    public Inventory addAdminOptionsVoting(final Inventory gui) {

        //Cancel
        final ItemStack cancel = new ItemStack(Material.BARRIER);
        final ItemMeta cancelMeta = cancel.getItemMeta();

        cancelMeta.setDisplayName(ChatColor.DARK_RED + "Click on me to cancel the ongoing poll!");
        cancel.setItemMeta(cancelMeta);

        gui.setItem(7, cancel);

        //Force-end
        final ItemStack forceEnd = new ItemStack(Material.LIGHT_BLUE_STAINED_GLASS_PANE);
        final ItemMeta forceEndMeta = forceEnd.getItemMeta();

        forceEndMeta.setDisplayName(ChatColor.RED + "Instantly end this poll now.");
        forceEnd.setItemMeta(forceEndMeta);

        gui.setItem(0, forceEnd);

        return addAdminOptionsMainMenu(gui);
    }

    public Inventory addAdminOptionsMainMenu(final Inventory gui) {

        final ItemStack history = new ItemStack(Material.WRITABLE_BOOK);
        final ItemMeta historyMeta = history.getItemMeta();

        final List<String> bookLores = new ArrayList<>();
        bookLores.add(0, ChatColor.AQUA + "Click on me to see the poll history!");

        historyMeta.setLore(bookLores);
        historyMeta.setDisplayName(ChatColor.DARK_AQUA + "Poll-Mute History");

        history.setItemMeta(historyMeta);
        gui.setItem(8, history);

        return gui;
    }

    public Poll getPoll(final int itemIndex, final int page) {

        final File file = new File(PollMutes.getInstance().getDataFolder().getAbsolutePath() + File.separator + "poll-history.yml");
        final FileConfiguration history = YamlConfiguration.loadConfiguration(file);

        final List<String> keys = new ArrayList<>(history.getKeys(false));
        keys.sort(Comparator.reverseOrder());

        final ConfigurationSection pollInfo = history.getConfigurationSection(keys.get((itemIndex - (9 * page)) - 9));

        final OfflinePlayer starter = Bukkit.getOfflinePlayer(UUID.fromString(pollInfo.getString("starter").split("/")[1]));
        final OfflinePlayer target = Bukkit.getOfflinePlayer(UUID.fromString(pollInfo.getString("target").split("/")[1]));
        final String reason = pollInfo.getString("reason");

        final Poll poll = new Poll(starter, target, reason, true);

        for (final String string : pollInfo.getConfigurationSection("votes").getKeys(false)) {

            if (!pollInfo.getString("votes." + string).equals("none")) {
                poll.addVote(Bukkit.getOfflinePlayer(UUID.fromString(string.split("/")[1])), Float.parseFloat(pollInfo.getString("votes." + string)));
            }
        }

        return poll;
    }

    private void save(final Poll poll) throws IOException {
        final Date date = new Date();
        final SimpleDateFormat format = new SimpleDateFormat("yyyy_MM_dd_HH-mm-ss");
        final String key = format.format(date);

        final File file = new File(PollMutes.getInstance().getDataFolder().getAbsolutePath() + File.separator + "poll-history.yml");
        final FileConfiguration history = YamlConfiguration.loadConfiguration(file);

        history.createSection(key);

        history.getConfigurationSection(key).set("reason", poll.getReason());
        history.getConfigurationSection(key).set("success", poll.getSuccess());
        history.getConfigurationSection(key).set("starter", poll.getStarter().getName() + "/" + poll.getStarter().getUniqueId().toString());
        history.getConfigurationSection(key).set("target", poll.getTarget().getName() + "/" + poll.getTarget().getUniqueId().toString());
        history.getConfigurationSection(key).createSection("votes");

        poll.getVotes().forEach((player, vote) -> history.getConfigurationSection(key + ".votes").set(player.getName() + "/" + player.getUniqueId(), vote.toString()));

        Bukkit.getOnlinePlayers().forEach(p -> {

            if (!poll.getVotes().containsKey(p)) history.getConfigurationSection(key + ".votes").set(p.getName() + "/" + p.getUniqueId().toString(), "none");

        });

        history.save(file);
    }

    private float getVoteWeight(final OfflinePlayer player) {

        final String group = PollMutes.getInstance().getPerms().getPrimaryGroup(player.getPlayer());

        return PollMutes.getStorage().getGroupValues().get(group);
    }
}
