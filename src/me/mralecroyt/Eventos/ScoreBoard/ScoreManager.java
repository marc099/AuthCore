package me.mralecroyt.Eventos.ScoreBoard;

import me.mralecroyt.authcore.CoreMain;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;

import java.util.HashMap;

public class ScoreManager{
    public static void scoredSidebar(Player p, String title, HashMap<String, Integer> elements) {
        if (title == null) {
            title = "Unamed";
        }
        if (title.length() > 32) {
            title = title.substring(0, 32);
        }
        String finalTitle = title;
        while (elements.size() > 16) {
            String minimumKey = (String)elements.keySet().toArray()[0];
            int minimum = elements.get(minimumKey);
            for (String string : elements.keySet()) {
                if (elements.get(string) >= minimum && (elements.get(string) != minimum || string.compareTo(minimumKey) >= 0)) continue;
                minimumKey = string;
                minimum = elements.get(string);
            }
            elements.remove(minimumKey);
        }
        Bukkit.getScheduler().runTask(CoreMain.get(), () -> {
            if (p == null || !p.isOnline()) {
                return;
            }
            if (Bukkit.getScoreboardManager().getMainScoreboard() != null && Bukkit.getScoreboardManager().getMainScoreboard() == p.getScoreboard()) {
                p.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
            }
            if (p.getScoreboard() == null) {
                p.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
            }
            Bukkit.getScheduler().runTaskAsynchronously(CoreMain.getInstance(), () -> {
                Objective objective = p.getScoreboard().getObjective(DisplaySlot.SIDEBAR);
                if (objective == null) {
                    objective = p.getScoreboard().registerNewObjective(finalTitle.length() > 16 ? finalTitle.substring(0, 15) : finalTitle, "dummy");
                }
                objective.setDisplayName(finalTitle);
                if (objective.getDisplaySlot() == null || objective.getDisplaySlot() != DisplaySlot.SIDEBAR) {
                    objective.setDisplaySlot(DisplaySlot.SIDEBAR);
                }
                for (String text : elements.keySet()) {
                    if (objective.getScore(text).isScoreSet() && objective.getScore(text).getScore() == elements.get(text).intValue()) continue;
                    objective.getScore(text).setScore(elements.get(text).intValue());
                }
                for (String str : p.getScoreboard().getEntries()) {
                    if (!objective.getScore(str).isScoreSet() || elements.containsKey(str)) continue;
                    p.getScoreboard().resetScores(str);
                }
            });
        });
    }
}
