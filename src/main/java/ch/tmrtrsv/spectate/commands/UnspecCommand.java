package ch.tmrtrsv.spectate.commands;

import ch.tmrtrsv.spectate.data.SpectatorData;
import ch.tmrtrsv.spectate.utils.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.UUID;

public class UnspecCommand implements CommandExecutor {

    private final Map<UUID, SpectatorData> spectatorDataMap;
    private final FileConfiguration config;

    public UnspecCommand(Map<UUID, SpectatorData> spectatorDataMap, FileConfiguration config) {
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

        SpectatorData spectatorData = spectatorDataMap.get(player.getUniqueId());
        if (spectatorData != null) {
            player.setGameMode(spectatorData.getPreviousGameMode());
            player.teleport(spectatorData.getPreviousLocation());
            spectatorDataMap.remove(player.getUniqueId());

            player.sendMessage(Utils.color(getMessage("stop_spectating").replace("%player%", player.getName())));
        } else {
            player.sendMessage(Utils.color(getMessage("not_spectating")));
        }
        return true;
    }

    private String getMessage(String path) {
        return config.contains("messages." + path) ? Utils.color(config.getString("messages." + path)) : "Message not found: " + path;
    }
}