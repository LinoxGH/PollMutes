package top.ageofelysian.pollmutes.Events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import top.ageofelysian.pollmutes.Objects.MessageHistory;
import top.ageofelysian.pollmutes.PollMutes;

import java.util.UUID;

public class PlayerChat implements Listener {

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {

        final UUID id = UUID.randomUUID();
        final MessageHistory history = PollMutes.getStorage().getMessageHistory();

        final Object[] message = new Object[3];
        message[0] = System.currentTimeMillis();
        message[1] = event.getPlayer().getName();
        message[2] = event.getMessage();

        history.addMessage(id, message);
        event.getRecipients().forEach(p -> PollMutes.getStorage().addPlayerMessage(p.getUniqueId(), id));

        //Storing the changes
        PollMutes.getStorage().setMessageHistory(history);
    }
}
