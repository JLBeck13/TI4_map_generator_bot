package ti4.commands.map;

import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData;
import org.jetbrains.annotations.NotNull;

import ti4.helpers.Constants;
import ti4.map.Map;
import ti4.map.MapManager;

public abstract class MapSubcommandData extends SubcommandData {
    private Map activeMap;
    private User user;

    public String getActionID() {
        return getName();
    }

    public MapSubcommandData(@NotNull String name, @NotNull String description) {
        super(name, description);
    }

    public Map getActiveMap() {
        return activeMap;
    }

    public User getUser() {
        return user;
    }

    abstract public void execute(SlashCommandInteractionEvent event);

    public void preExecute(SlashCommandInteractionEvent event) {
        user = event.getUser();
        activeMap = MapManager.getInstance().getUserActiveMap(user.getId());
        if (event.getOption(Constants.GAME_NAME) != null) {
            activeMap = MapManager.getInstance().getMap(event.getOption(Constants.GAME_NAME).getAsString().toLowerCase());
        }
    }
}