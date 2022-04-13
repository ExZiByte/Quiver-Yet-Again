package me.exzibyte.command;

import me.exzibyte.Quiver;
import net.dv8tion.jda.api.interactions.commands.CommandInteraction;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.internal.interactions.CommandDataImpl;
import org.jetbrains.annotations.NotNull;

// @Permission("commands.debug.use")
public class DebugCommand implements Command {

    private final Quiver quiver;

    public DebugCommand(Quiver quiver) {
        this.quiver = quiver;
    }

    @Override
    @NotNull
    public CommandData getData() {
        return new CommandDataImpl("debug", "Test command for Quiver internal defuckification.");
    }

    @Override
    public void execute(CommandInteraction interaction) {
        var entity = quiver.getGuildManager().getGuild(interaction.getGuild());

        if (entity == null) {
            interaction.reply("Guild entity not cached!").queue();
            return;
        }

        interaction.reply("Guild entity cached! " + entity).queue();
    }
}
