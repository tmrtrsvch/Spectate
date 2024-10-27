package ch.tmrtrsv.spectate.data;

import org.bukkit.GameMode;
import org.bukkit.Location;

public class SpectatorData {
    private final Location previousLocation;
    private final GameMode previousGameMode;

    public SpectatorData(Location previousLocation, GameMode previousGameMode) {
        this.previousLocation = previousLocation;
        this.previousGameMode = previousGameMode;
    }

    public Location getPreviousLocation() {
        return previousLocation;
    }

    public GameMode getPreviousGameMode() {
        return previousGameMode;
    }
}