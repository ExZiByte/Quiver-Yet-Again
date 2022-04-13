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
        quiver.onReady();

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
                .upsertCommand("settings", "Define settings for the server")
                .addSubcommands(new SubcommandData("update", "Modify a setting. You can modify multiple of them at the same time.")
                        .addOption(OptionType.CHANNEL, "log-channel", "Set the log channel")
                        .addOption(OptionType.CHANNEL, "announcement-channel", "Set the announcements channel")
                        .addOption(OptionType.CHANNEL, "join-log-channel", "Set the join/leave log channel")
                        .addOption(OptionType.ROLE, "mute-role", "Set the muted role")
                        .addOption(OptionType.STRING, "locale", "Set the language that you would like Quiver to talk in"))
                .addSubcommands(new SubcommandData("reset", "Reset a setting back to the default value")
                        .addOption(OptionType.CHANNEL, "log-channel", "Reset the log channel")
                        .addOption(OptionType.CHANNEL, "announcement-channel", "Reset the announcements channel")
                        .addOption(OptionType.CHANNEL, "join-log-channel", "Reset the join/leave log channel")
                        .addOption(OptionType.ROLE, "mute-role", "Reset the muted role")
                        .addOption(OptionType.STRING, "locale", "Reset the language that you would like Quiver to talk in")).queue();

        //event.getJDA().deleteCommandById(962901723112951818L).queue();

        event.getJDA()
                .upsertCommand("groups", "Manage groups and permissions")
                .addSubcommands(new SubcommandData("create", "Create a group")
                        .addOption(OptionType.STRING, "name", "The name of the group and how you would modify this group later", true, false))
                .addSubcommands(new SubcommandData("rename", "Rename an existing group")
                        .addOption(OptionType.STRING, "name", "The current name of the group you want to rename", true , false)
                        .addOption(OptionType.STRING, "new-name", "The new name for this group", true, false))
                .addSubcommands(new SubcommandData("give", "Give a group a permission node")
                        .addOption(OptionType.STRING, "name", "The name of the group that you would like to grant a permission to.", true, false)
                        .addOption(OptionType.STRING, "permission", "A permission node to give the group, for a list of these https://quiverbot.io/permissions", true, false))
                .addSubcommands(new SubcommandData("parse-permissions", "Upload a permissions.json file to parse permissions from")
                        .addOption(OptionType.ATTACHMENT, "permissions-json", "The permissions.json file to parse permissions from. For help https://quiverbot.io/permissions", true, false))
                .addSubcommands(new SubcommandData("delete", "Delete a group")
                        .addOption(OptionType.STRING, "name", "The name of the group you would like to delete", true, false)).queue();
    }

}
