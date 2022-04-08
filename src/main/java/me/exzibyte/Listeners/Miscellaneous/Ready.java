package me.exzibyte.Listeners.Miscellaneous;

import me.exzibyte.Quiver;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData;
public class Ready extends ListenerAdapter {

    private final Quiver quiver;

    public Ready(Quiver quiver) {
        this.quiver = quiver;
    }

    public void onReady(ReadyEvent event) {

//        // TODO debug garbage
//        for (var guild : event.getJDA().getGuilds()) {
//            if (!guild.getName().contains("Nested")) {
//                continue;
//            }

//            quiver.getGuildManager().createGuildConfig(guild);
//        }
//
//        //Informational Slash Commands
//        event.getJDA().getGuildById(488137783127572491L)
//                .upsertCommand("help", "List of commands available from me.")
//                .addOption(OptionType.STRING, "command", "Help with a specific command", false, true)
//                .queue();


        //Moderation Slash Commands
        event.getJDA()
                .upsertCommand("ban", "Ban a member from the server")
                .addOption(OptionType.MENTIONABLE, "member", "The member you wish to ban", true, false)
                .addOption(OptionType.STRING, "reason", "The reason for banning the member", false, false)
                .addOption(OptionType.ATTACHMENT, "proof", "Proof of the reason for the ban", false, false).queue();
        event.getJDA()
                .upsertCommand("clear", "Clear multiple messages at the same time from a specified channel")
                .addOption(OptionType.INTEGER, "amount", "The amount of message to delete", true, false)
                .addOption(OptionType.CHANNEL, "channel", "[WIP] The channel to delete messages from. Defaults to the channel the command was ran in", false, false).queue();
        event.getJDA()
                .upsertCommand("kick", "Kick a member from the server")
                .addOption(OptionType.MENTIONABLE, "member", "The member you wish to kick", true, false)
                .addOption(OptionType.STRING, "reason", "The reason for kicking the member", false, false)
                .addOption(OptionType.ATTACHMENT, "proof", "Proof of the reason for the kick", false, false).queue();
        event.getJDA()
                .upsertCommand("mute", "Mute a member on the server")
                .addOption(OptionType.MENTIONABLE, "member", "The member you wish to mute", true, false)
                .addOption(OptionType.STRING, "reason", "The reason for muting the member", false, false)
                .addOption(OptionType.ATTACHMENT, "proof", "Proof of the reason for the mute", false, false).queue();
        event.getJDA()
                .upsertCommand("softban", "Temporarily ban a member from the server to delete message from that member")
                .addOption(OptionType.MENTIONABLE, "member", "The member you wish to softban", true, false)
                .addOption(OptionType.STRING, "reason", "The reason for softbanning the member", false, false)
                .addOption(OptionType.ATTACHMENT, "proof", "Proof of the reason for the softban", false, false).queue();


        event.getJDA()
                .upsertCommand("set", "Define settings for the server")
                .addSubcommands(new SubcommandData("setting", "The setting that you would like to modify")
                        .addOption(OptionType.CHANNEL, "log-channel", "Set the log channel")
                        .addOption(OptionType.CHANNEL, "announcement-channel", "Set the announcements channel")
                        .addOption(OptionType.CHANNEL, "join-log-channel", "Set the join/leave log channel")
                        .addOption(OptionType.ROLE, "mute-role", "Set the muted role")
                        .addOption(OptionType.STRING, "locale", "Set the language that you would like Quiver to talk in")
                        .addOption(OptionType.STRING, "prefix", "Set the prefix for commands if you wish to not use Slash Commands"))
                .queue();

    }

}
