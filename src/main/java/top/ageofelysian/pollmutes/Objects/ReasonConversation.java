package top.ageofelysian.pollmutes.Objects;

import org.bukkit.ChatColor;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.conversations.StringPrompt;

import top.ageofelysian.pollmutes.PollMutes;

public class ReasonConversation extends StringPrompt {
    /**
     * Gets the text to display to the user when this prompt is first
     * presented.
     *
     * @param context Context information about the conversation.
     * @return The text to display.
     */
    @Override
    public String getPromptText(ConversationContext context) {
        return PollMutes.getStorage().getTag() + " " + ChatColor.translateAlternateColorCodes('&', PollMutes.getInstance().getConfig().getString("reason-conversation-question", "&aEnter a &eReason &afor the mute &9>"));
    }

    /**
     * Accepts and processes input from the user. Using the input, the next
     * Prompt in the prompt graph is returned.
     *
     * @param context Context information about the conversation.
     * @param input   The input text from the user.
     * @return The next Prompt in the prompt graph.
     */
    @Override
    public Prompt acceptInput(ConversationContext context, String input) {

        if (input.length() < PollMutes.getInstance().getConfig().getInt("description-length-minimum", 10)) {
            context.getForWhom().sendRawMessage(PollMutes.getStorage().getTag() + ChatColor.translateAlternateColorCodes('&', PollMutes.getInstance().getConfig().getString("description-length-minimum-message", " &4Reason is too short. Please re-enter.")));
            return this;
        } else if (input.length() > PollMutes.getInstance().getConfig().getInt("description-length-maximum", 50)) {
            context.getForWhom().sendRawMessage(PollMutes.getStorage().getTag() + ChatColor.translateAlternateColorCodes('&', PollMutes.getInstance().getConfig().getString("description-length-maximum-message", " &4Reason is too long. Please re-enter.")));
            return this;
        }

        PollMutes.getStorage().getPoll().setReason(input);
        PollMutes.getStorage().getVoting().setReason(input);
        return null;
    }
}
