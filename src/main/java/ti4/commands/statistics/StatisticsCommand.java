package ti4.commands.statistics;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.requests.restaction.CommandListUpdateAction;
import ti4.commands.Command;
import ti4.helpers.Constants;
import ti4.map.Map;
import ti4.map.MapManager;
import ti4.map.MapSaveLoadManager;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;

public class StatisticsCommand implements Command {

    private final Collection<StatisticsSubcommandData> subcommandData = getSubcommands();

    @Override
    public String getActionID() {
        return Constants.STATISTICS;
    }

    @Override
    public boolean accept(SlashCommandInteractionEvent event) {
        return true;
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        String subcommandName = event.getInteraction().getSubcommandName();
        StatisticsSubcommandData executedCommand = null;
        for (StatisticsSubcommandData subcommand : subcommandData) {
            if (Objects.equals(subcommand.getName(), subcommandName)) {
                subcommand.preExecute(event);
                subcommand.execute(event);
                executedCommand = subcommand;
                break;
            }
        }
        if (executedCommand == null) {
            reply(event);
        } else {
            executedCommand.reply(event);
        }
    }

    public static void reply(SlashCommandInteractionEvent event) {
        String userID = event.getUser().getId();
        Map activeMap = MapManager.getInstance().getUserActiveMap(userID);
        MapSaveLoadManager.saveMap(activeMap, event);
    }


    protected String getActionDescription() {
        return "Statistics";
    }

    private Collection<StatisticsSubcommandData> getSubcommands() {
        Collection<StatisticsSubcommandData> subcommands = new HashSet<>();
        subcommands.add(new AverageTurnTime());


        return subcommands;
    }

    @Override
    public void registerCommands(CommandListUpdateAction commands) {
        commands.addCommands(
                Commands.slash(getActionID(), getActionDescription())
                        .addSubcommands(getSubcommands()));
    }
}