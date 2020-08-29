package top.ageofelysian.pollmutes.Objects;

import top.ageofelysian.pollmutes.PollMutes;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Formatter;
import java.util.logging.*;

public class MessageHistory {
    private final HashMap<UUID, Object[]> messages = new HashMap<>();
    private int timer = 0;

    public HashMap<UUID, Object[]> getMessages() {
        return messages;
    }

    /**
     * @param id      Message id.
     * @param message Object[0] is the System.currentTimeMillis() and Object[1] is the message itself.
     */
    public void addMessage(final UUID id, final Object[] message) {
        messages.put(id, message);
        updateMessages();
        timer++;
    }

    private void updateMessages() {
        if (timer == 20) {
            final Set<UUID> keysToRemove = new HashSet<>();
            messages.forEach((key, value) -> {
                if (((long) value[0]) < (System.currentTimeMillis() - 300_000)) {
                    keysToRemove.add(key);
                }
            });

            keysToRemove.forEach(messages::remove);
            timer = 0;
        }
    }

    public void save(final UUID player) {
        if (messages.size() == 0) return;

        try {

            final File dir = new File(PollMutes.getInstance().getDataFolder().getAbsolutePath() + File.separator + "message-history");
            if (!dir.exists()) dir.mkdirs();

            final File logFile = new File(dir.getAbsolutePath() + File.separator + player.toString() + ".txt");
            if (logFile.exists()) logFile.delete();

            final Handler fileHandler = new FileHandler(dir.getAbsolutePath() + File.separator + player.toString() + ".txt");
            final Logger pollLogger = Logger.getLogger("MessageHistory");

            fileHandler.setFormatter(new Formatter() {

                @Override
                public String format(LogRecord record) {
                    return record.getMessage() + '\n';
                }

            });

            pollLogger.setLevel(Level.INFO);
            pollLogger.addHandler(fileHandler);

            final Set<UUID> ids = PollMutes.getStorage().getPlayerMessages().get(player);

            ids.forEach(key -> {
                final SimpleDateFormat sdf = new SimpleDateFormat("yyyy_MM_dd_HH-mm-ss");
                final Date date = new Date((long) messages.get(key)[0]);

                pollLogger.info("[" + sdf.format(date) + "]" + messages.get(key)[1] + " : \"" + messages.get(key)[2] + "\"");
            });

            fileHandler.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
