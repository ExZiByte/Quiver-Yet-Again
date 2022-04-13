package me.exzibyte.command;

import me.exzibyte.Quiver;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class CommandRegistry extends ListenerAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger("Command Registry");
    private final Quiver quiver;
    private final Map<String, ParsedCommand> registeredCommands;

    public CommandRegistry(Quiver quiver) {
        this.quiver = quiver;
        this.registeredCommands = new HashMap<>();

        register(new DebugCommand(quiver));
    }

    public void register(Command... commands) {
        Arrays.stream(commands).forEach(this::register);
    }

    private void register(Command command) {
        registeredCommands.put(command.getData().getName().toLowerCase(), new ParsedCommand(command));

        var guilds = quiver.getGuildManager().allGuilds();
        guilds.forEach(guild -> guild.guild().upsertCommand(command.getData()).queue());

        LOGGER.info("Registered command \"{}\" in {} guilds.", command.getData().getName(), guilds.size());
    }

    public void registerAllCommandsInGuild(Guild guild) {
        registeredCommands.values().stream()
                .map(ParsedCommand::command)
                .forEach(command ->  guild.upsertCommand(command.getData()).queue());
    }

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        var command = registeredCommands.get(event.getName().toLowerCase());

        if (command == null) {
            event.reply("Unrecognized command.")
                    .setEphemeral(true)
                    .queue();
            return;
        }

        // FIXME dev test, if permission just say fuck you!!!
        if (command.requiredPermission() != null) {
            event.reply("You do not have sufficient permissions to use this command.")
                    .setEphemeral(true)
                    .queue();
            return;
        }

        command.command().execute(event.getInteraction());
    }
}
