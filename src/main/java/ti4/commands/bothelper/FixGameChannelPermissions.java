package ti4.commands.bothelper;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import ti4.helpers.Constants;
import ti4.helpers.Helper;
import ti4.map.Game;

public class FixGameChannelPermissions extends BothelperSubcommandData {
    public FixGameChannelPermissions() {
        super(Constants.FIX_CHANNEL_PERMISSIONS, "Ensure players in this game have access");
    }

    public void execute(SlashCommandInteractionEvent event) {
        Guild guild = event.getGuild();
        Game activeGame = getActiveGame();
        if (guild != null && activeGame != null) {
            Helper.fixGameChannelPermissions(guild, activeGame);
        }
        sendMessage("Channel Permissions Fixed");
    }
}
