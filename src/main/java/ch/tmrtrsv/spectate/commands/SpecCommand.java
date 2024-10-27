package ch.tmrtrsv.spectate.commands;

import ch.tmrtrsv.spectate.data.SpectatorData;
import ch.tmrtrsv.spectate.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class SpecCommand implements CommandExecutor {

    private final Map<UUID, SpectatorData> spectatorDataMap;
    private final FileConfiguration config;

    public SpecCommand(Map<UUID, SpectatorData> spectatorDataMap, FileConfiguration config) {
        this.spectatorDataMap = spectatorDataMap;
        this.config = config;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(Utils.color(getMessage("only_for_players")));
            return true;
        }

        Player player = (Player) sender;

        if (!player.hasPermission("spectate.use")) {
            player.sendMessage(Utils.color(getMessage("no_permission")));
            return true;
        }

        if (args.length != 1) {
            List<String> usageMessages = getMessages("spectate_usage");
            player.sendMessage(Utils.color(usageMessages).toArray(new String[0]));
            return true;
        }

        Player target = Bukkit.getPlayer(args[0]);

        if (target == null) {
            player.sendMessage(Utils.color(getMessage("player_not_found")));
            return true;
        }

        if (player.equals(target)) {
            player.sendMessage(Utils.color(getMessage("cannot_spectate_yourself")));
            return true;
        }

        spectatorDataMap.put(player.getUniqueId(), new SpectatorData(player.getLocation(), player.getGameMode()));

        player.setGameMode(GameMode.SPECTATOR);
        player.teleport(target);

        player.sendMessage(Utils.color(getMessage("start_spectating").replace("%player%", target.getName())));
        return true;
    }

    private List<String> getMessages(String path) {
        return config.contains("messages." + path) ? Utils.color(config.getStringList("messages." + path)) : Arrays.asList("Message not found: " + path);
    }

    private String getMessage(String path) {
        return config.contains("messages." + path) ? Utils.color(config.getString("messages." + path)) : "Message not found: " + path;
    }
}