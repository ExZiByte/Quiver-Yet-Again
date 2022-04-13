package me.exzibyte.Listeners.Settings;

import me.exzibyte.Quiver;
import me.exzibyte.Utilities.Logging;
import me.exzibyte.Utilities.StaticEmbeds;
import me.exzibyte.Utilities.Utilities;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.Instant;

public class Groups extends ListenerAdapter {

    private final Quiver quiver;
    Utilities utils = new Utilities();
    Logging LOGGING = new Logging();

    public Groups(Quiver quiver) {
        this.quiver = quiver;
    }

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        if (!event.getName().equals("groups")) return;
        if (!event.isFromGuild()) return;

        EmbedBuilder eb = new EmbedBuilder();

//        var guild = quiver.getGuildManager().getGuild(event.getGuild());
//        var config = guild.getConfig();
//
//        if (config.isBlacklisted()) {
//            event.replyEmbeds(StaticEmbeds.blacklisted(event.getGuild())).queue();
//            return;
//        }
        if (/*Check Perms don't exist as guard clause: !member.getGroups().hasPermission("commands.settings.groups")*/ 1 != 1)
            return;
        if (event.getSubcommandName().equals("create")) {
            //Create a group for the guild


        }
        if (event.getSubcommandName().equals("delete")) {
            //Delete a group for the guild
        }
        if (event.getSubcommandName().equals("give")) {
            //Give permissions to an already created group from the command
        }
        if (event.getSubcommandName().equals("parse-permissions")) {
            //Parse permissions from an uploaded permissions.json file with the ability to assign permissions for multiple groups at the same time with multiple permissions for each group

            File guildTempDir = new File(String.format("./tmp/%s", event.getGuild().getId()));
            if(!guildTempDir.exists()) {
                guildTempDir.mkdirs();
                event.getOption("permissions-json").getAsAttachment().downloadToFile(String.format("./tmp/%s/permissions.json", event.getGuild().getId()));
            }
            JSONParser parser = new JSONParser();
            try (FileReader reader = new FileReader(String.format("./tmp/%s/permissions.json", event.getGuild().getId()))) {
                JSONObject permissionsJSON = (JSONObject) parser.parse(reader);

            } catch (IOException | ParseException exception) {
                exception.printStackTrace();
                LOGGING.error(this.getClass(), exception.getMessage());
            }
        }
    }
}