package top.ageofelysian.pollmutes.Utilities;

import com.sun.istack.internal.Nullable;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import top.ageofelysian.pollmutes.Objects.Guis.*;
import top.ageofelysian.pollmutes.Objects.MessageHistory;
import top.ageofelysian.pollmutes.Objects.Poll;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class DataStorage {

    public void init() {

        messageHistory = new MessageHistory();
        pollUtils = new PollUtils();
        tag = ChatColor.YELLOW + "[" + ChatColor.DARK_AQUA + "P-Mute" + ChatColor.YELLOW + "]";

        defaultGui = Bukkit.createInventory(null, 45, getTag());
        initDefaultGui();

        menu = new MainMenu();
        voting = new Voting();
        sureQuestion = new SureQuestion();
    }

    //MESSAGE HISTORY -----------------------------------------------------------------
    private MessageHistory messageHistory;

    public MessageHistory getMessageHistory() { return messageHistory; }
    public void setMessageHistory(MessageHistory messageHistory) { this.messageHistory = messageHistory;}

    private final HashMap<UUID, Set<UUID>> playerMessages = new HashMap<>();

    public HashMap<UUID, Set<UUID>> getPlayerMessages() { return playerMessages; }
    public void addPlayerMessage(final UUID p, final UUID id) {
        if (!playerMessages.containsKey(p)) {
            final Set<UUID> ids = new HashSet<>();
            ids.add(id);

            playerMessages.put(p, ids);
        } else {
            final Set<UUID> ids = playerMessages.get(p);
            ids.add(id);

            playerMessages.replace(p, ids);
        }
    }

    //POLL UTILS ----------------------------------------------------------------------
    private PollUtils pollUtils;

    public PollUtils getPollUtils() { return pollUtils; }

    //POLL LENGTH ---------------------------------------------------------------------
    private int pollLength;

    public int getPollLength() { return pollLength; }
    public void setPollLength(int pollLength) { this.pollLength = pollLength;}

    //CURRENT POLL --------------------------------------------------------------------
    private Poll poll = null;

    public Poll getPoll() { return poll; }
    public void setPoll(@Nullable Poll poll) { this.poll = poll; }

    //ANNOUNCEMENT PERIODS -----------------------------------------------------------
    private int periods;

    public int getPeriods() { return periods; }
    public void setPeriods(final int periods) { this.periods = periods; }

    //PLUGIN TAG ---------------------------------------------------------------------
    private String tag;

    public String getTag() { return tag; }

    //COMMAND VALUES ------------------------------------------------------------------
    private String command;

    public void setCommand(String command) { this.command = command; }
    public String getCommand() { return command; }

    //GROUP VALUES --------------------------------------------------------------------
    private final HashMap<String, Integer> groupValues = new HashMap<>();

    public HashMap<String, Integer> getGroupValues() { return groupValues; }
    public void addGroupValue(String str, int i) { groupValues.put(str, i); }

    //DEFAULT GUI ---------------------------------------------------------------------
    private Inventory defaultGui;

    private void initDefaultGui() {
        final ItemStack grayPane = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
        final ItemMeta grayPaneMeta = grayPane.getItemMeta();

        grayPaneMeta.setDisplayName(" ");
        grayPane.setItemMeta(grayPaneMeta);

        int i = 0;
        while (i <= 8) {
            defaultGui.setItem(i, grayPane);
            i++;
        }

        i = 36;
        while (i <= 44) {
            defaultGui.setItem(i, grayPane);
            i++;
        }
    }
    public Inventory getDefaultGui() { return defaultGui; }

    //MAIN MENU -----------------------------------------------------------------------
    private MainMenu menu;

    public MainMenu getMenu() { return menu; }

    //VOTING --------------------------------------------------------------------------
    private Voting voting;

    public Voting getVoting() { return voting; }

    //SURE QUESTION -------------------------------------------------------------------
    private SureQuestion sureQuestion;

    public SureQuestion getSureQuestion() { return sureQuestion; }

    //POLL START ----------------------------------------------------------------------
    private final HashMap<UUID, PollStart> pollStart = new HashMap<>();

    public PollStart getPollStart(final UUID user) {
        if (pollStart.containsKey(user)) {
            return pollStart.get(user);
        } else {
            final PollStart pollStart = new PollStart();

            this.pollStart.put(user, pollStart);
            return pollStart;
        }
    }
    public void addPollStart(final UUID user, final PollStart start) {
        if (pollStart.containsKey(user)) {
            pollStart.replace(user, start);
        } else {
            pollStart.put(user, start);
        }
    }
    public void removePollStart(final UUID user) { pollStart.remove(user); }

    //POLL HISTORY --------------------------------------------------------------------
    private final HashMap<UUID, PollHistory> pollHistory = new HashMap<>();

    public PollHistory getPollHistory(final UUID user) {
        if (pollHistory.containsKey(user)) {
            return pollHistory.get(user);
        } else {
            final PollHistory pollHistory = new PollHistory();

            this.pollHistory.put(user, pollHistory);
            return pollHistory;
        }
    }
    public void addPollHistory(final UUID user, final PollHistory history) {
        if (pollHistory.containsKey(user)) {
            pollHistory.replace(user, history);
        } else {
            pollHistory.put(user, history);
        }
    }
    public void removePollHistory(final UUID user) { pollHistory.remove(user); }

    //POLL VOTES ----------------------------------------------------------------------
    private final HashMap<UUID, PollVotes> pollVotes = new HashMap<>();

    public PollVotes getPollVotes(final UUID user) {
        if (pollVotes.containsKey(user)) {
            return pollVotes.get(user);
        } else {
            final PollVotes pollVotes = new PollVotes();

            this.pollVotes.put(user, pollVotes);
            return pollVotes;
        }
    }
    public void addPollVotes(final UUID user, final PollVotes votes) {
        if (pollVotes.containsKey(user)) {
            pollVotes.remove(user, votes);
        } else {
            pollVotes.put(user, votes);
        }
    }
    public void removePollVotes(final UUID user) { pollVotes.remove(user); }

    //HISTORY QUANTITY ----------------------------------------------------------------
    private final HashMap<UUID, HistoryQuantity> historyQuantity = new HashMap<>();

    public HistoryQuantity getHistoryQuantity(final UUID user) {
        if (historyQuantity.containsKey(user)) {
            return historyQuantity.get(user);
        } else {
            final HistoryQuantity historyQuantity = new HistoryQuantity();

            this.historyQuantity.put(user, historyQuantity);
            return historyQuantity;
        }
    }
    public void addHistoryQuantity(final UUID user, final HistoryQuantity history) {
        if (historyQuantity.containsKey(user)) {
            historyQuantity.replace(user, history);
        } else {
            historyQuantity.put(user, history);
        }
    }
    public void removeHistoryQuantity(final UUID user) { historyQuantity.remove(user); }
}
