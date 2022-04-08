package me.exzibyte.Listeners.Miscellaneous;

import me.exzibyte.Quiver;
import me.exzibyte.Utilities.Logging;
import me.exzibyte.Utilities.Utilities;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.interactions.components.buttons.ButtonStyle;
import net.dv8tion.jda.internal.utils.PermissionUtil;
import org.jetbrains.annotations.NotNull;
import java.util.List;
import java.util.Optional;


public class JoinedGuild extends ListenerAdapter {

    private final Quiver quiver;
    Utilities utils = new Utilities();
    Logging logging = new Logging();

    private static final List<Button> DEFAULT_BUTTONS = List.of(
            Button.of(ButtonStyle.SUCCESS, "permissionsYes", "Yes", Emoji.fromUnicode("\u2705")),
            Button.of(ButtonStyle.DANGER, "permissionsNo", "No", Emoji.fromUnicode("\u2716"))
    );

    public JoinedGuild(Quiver quiver) {
        this.quiver = quiver;
    }

    public void onGuildJoin(GuildJoinEvent event) {
        var eb = new EmbedBuilder();
        eb.setDescription(String.format("Thank you for adding me to your guild. My name is [Quiver](https://quiverbot.io \"Quiver's Official Website\"), I'm a multipurpose bot. I just have one question for %s right now. Since I don't rely on the built-in permission system of Discord to allow a more granular control of permissions. \nWould you like me to use a default set of permissions for your administrators?\n\nNote: Members of the guild that currently have the permission Administrator will automatically use these default permissions if you choose.", event.getGuild().getOwner().getAsMention()));

        // ur mom joined the guild
        findFirstPermittedChannel(event.getGuild()).ifPresentOrElse(channel -> {
            channel.sendMessageEmbeds(eb.build()).setActionRow(DEFAULT_BUTTONS).queue();
        }, () -> {
            // TODO handle error here
        });

        // This is handled by default in guildmanager now!!
        // quiver.getGuildManager().createGuildConfig(event.getGuild());
    }

    @NotNull
    private Optional<GuildMessageChannel> findFirstPermittedChannel(Guild guild) {
        // Check if we can use the default channel first
        var defaultChannel = guild.getDefaultChannel();

        if (defaultChannel != null && canMessageInChannel(defaultChannel)) {
            return Optional.of(defaultChannel);
        }

        // Loop through all other available channels
        for (var channel : guild.getTextChannels()) {
            if (canMessageInChannel(channel)) {
                return Optional.of(channel);
            }
        }

        // There are no channels we can use
        return Optional.empty();
    }

    private boolean canMessageInChannel(IPermissionContainer channel) {
        return PermissionUtil.checkPermission(channel, channel.getGuild().getSelfMember(), Permission.MESSAGE_SEND);
    }
}
