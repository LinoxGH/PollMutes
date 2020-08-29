package top.ageofelysian.pollmutes;

import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import top.ageofelysian.pollmutes.Events.InventoryClick;
import top.ageofelysian.pollmutes.Events.InventoryClose;
import top.ageofelysian.pollmutes.Events.PlayerChat;
import top.ageofelysian.pollmutes.Utilities.DataStorage;

import java.io.File;
import java.io.IOException;

public class PollMutes extends JavaPlugin {
    private Permission perms = null;
    private static PollMutes instance;
    private static DataStorage storage;

    @Override
    public void onEnable() {
        instance = this;

        if (!getDataFolder().exists()) {
            getDataFolder().mkdirs();
            saveDefaultConfig();
        }

        storage = new DataStorage();
        storage.init();

        for (String group : getConfig().getConfigurationSection("group-values").getKeys(false)) {
            storage.addGroupValue(group, getConfig().getInt("group-values." + group));
        }

        storage.setPollLength(getConfig().getInt("poll-length", 3));
        storage.setCommand(getConfig().getString("mute-command", "").replaceAll("%TIME%", String.valueOf(getConfig().getInt("mute-length"))));
        storage.setPeriods(getConfig().getInt("announcement-periods", 60));

        //Checking for Vault
        if (Bukkit.getServer().getPluginManager().getPlugin("Vault") == null) {
            Bukkit.getLogger().warning("The plugin, Vault is needed for this plugin!");
            getServer().getPluginManager().disablePlugin(this);
        }

        RegisteredServiceProvider<Permission> rsp = getServer().getServicesManager().getRegistration(Permission.class);
        perms = rsp.getProvider();

        final File historyFile = new File(getDataFolder().getAbsolutePath() + File.separator + "poll-history.yml");
        if (!historyFile.exists()) {
            try {
                historyFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        getCommand("pmute").setExecutor(new Commands());

        getServer().getPluginManager().registerEvents(new PlayerChat(), this);
        getServer().getPluginManager().registerEvents(new InventoryClick(), this);
        getServer().getPluginManager().registerEvents(new InventoryClose(), this);
    }

    public static PollMutes getInstance() { return instance; }
    public static DataStorage getStorage() { return storage; }

    public Permission getPerms() { return perms; }
}
