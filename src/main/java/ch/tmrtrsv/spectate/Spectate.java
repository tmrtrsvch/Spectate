package ch.tmrtrsv.spectate;

import ch.tmrtrsv.spectate.commands.SpecCommand;
import ch.tmrtrsv.spectate.commands.UnspecCommand;
import ch.tmrtrsv.spectate.data.SpectatorData;
import ch.tmrtrsv.spectate.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Spectate extends JavaPlugin {

    private FileConfiguration config;
    private Map<UUID, SpectatorData> spectatorDataMap;

    @Override
    public void onEnable() {
        sendCredit();

        File configFile = new File(getDataFolder(), "config.yml");
        if (!configFile.exists()) {
            saveResource("config.yml", false);
        }
        config = YamlConfiguration.loadConfiguration(configFile);

        spectatorDataMap = new HashMap<>();

        getCommand("spec").setExecutor(new SpecCommand(spectatorDataMap, config));
        getCommand("unspec").setExecutor(new UnspecCommand(spectatorDataMap, config));
    }

    @Override
    public void onDisable() {
        sendCredit();
    }

    private void sendCredit() {
        Bukkit.getConsoleSender().sendMessage(Utils.color(""));
        Bukkit.getConsoleSender().sendMessage(Utils.color("&f &#8A31EAS&#8A2EECp&#892CEEe&#8929EFc&#8826F1t&#8823F3a&#8721F5t&#871EF6e &#8618FAv&#8516FC1&#8513FD.&#8410FF0"));
        Bukkit.getConsoleSender().sendMessage(Utils.color("&f Автор: &#FB3908Т&#FC2B06и&#FD1D04м&#FE0E02у&#FF0000р"));
        Bukkit.getConsoleSender().sendMessage(Utils.color("&f Телеграм: &#008DFF@&#0086FFt&#007FFFm&#0078FFr&#0071FFt&#006BFFr&#0064FFs&#005DFFv&#0056FFc&#004FFFh"));
        Bukkit.getConsoleSender().sendMessage(Utils.color(""));
    }
}