package top.ageofelysian.pollmutes.Objects;

import org.bukkit.OfflinePlayer;
import top.ageofelysian.pollmutes.PollMutes;

import java.util.HashMap;

public class Poll {
    private HashMap<OfflinePlayer, Float> votes;

    private String reason;

    private boolean success;
    private final boolean old;

    private final OfflinePlayer starter;
    private final OfflinePlayer target;

    public Poll(final OfflinePlayer starter, final OfflinePlayer target, final boolean old) {
        votes = new HashMap<>();

        this.reason = " ";

        this.starter = starter;
        this.target = target;

        success = false;
        this.old = old;
    }

    public Poll(final OfflinePlayer starter, final OfflinePlayer target, final String reason, final boolean old) {
        votes = new HashMap<>();

        this.reason = reason;

        this.starter = starter;
        this.target = target;

        success = false;
        this.old = old;
    }

    public OfflinePlayer getStarter() { return starter; }
    public OfflinePlayer getTarget() { return target; }

    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }

    public boolean getSuccess() { return success; }
    public boolean isOld() { return old; }
    public HashMap<OfflinePlayer, Float> getVotes() { return votes; }

    /**
     * @param vote               true = FOR; false = AGAINST
     */
    public void addVote(final OfflinePlayer p, final float vote) {
        votes.put(p, vote);
        updateSuccess();

        if (!isOld()) PollMutes.getStorage().getVoting().updateVoteInfo();
    }

    public void removeVote(final OfflinePlayer p) {
        votes.remove(p);
        updateSuccess();

        if (!isOld()) PollMutes.getStorage().getVoting().updateVoteInfo();
    }

    private void updateSuccess() {
        final int For = (int) votes.values().stream().filter(f -> f > 0).count();
        final int Against = (int) votes.values().stream().filter(f -> f < 0).count();

        success = For > Against;
    }
}
