package me.exzibyte.Listeners.Moderation;

import me.exzibyte.Quiver;
import me.exzibyte.Utilities.StaticEmbeds;
import me.exzibyte.Utilities.Utilities;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.time.Instant;
import java.util.concurrent.TimeUnit;

public class Softban extends ListenerAdapter {

    private final Quiver quiver;
    Utilities utils = new Utilities();

    private Softban(Quiver quiver){
        this.quiver = quiver;
    }

    public void onSlashCommandInteraction(SlashCommandInteractionEvent event){
        if(!event.getName().equals("sotban")) return;
        EmbedBuilder eb = new EmbedBuilder();
        EmbedBuilder log = new EmbedBuilder();
        EmbedBuilder target = new EmbedBuilder();

        var guild = quiver.getGuildManager().getGuild(event.getGuild());
        var config = guild.getConfig();

        if (config.isBlacklisted()) {
            event.replyEmbeds(StaticEmbeds.blacklisted(event.getGuild())).queue();
            return;
        }

        if(event.getMember().hasPermission(Permission.BAN_MEMBERS)){
            if(event.getOptions().size() == 1){
                eb.setDescription(String.format("Softbanned %s for No Reason Specified", event.getOption("member").getAsMember().getAsMention()));
                eb.setColor(utils.successGreen);
                eb.setFooter("Quiver Member Softbanned");

                log.setDescription(String.format("%s softbanned member: %s\n\nReason:\n```\nNo Reason\n```", event.getMember().getAsMention(), event.getOption("member").getAsMember().getAsMention()));
                log.setColor(utils.warningYellow);
                log.setTimestamp(Instant.now());
                log.setFooter("Quiver Member Softbanned | Log");

                target.setDescription(String.format("You've been softbanned from %s \nReason: No reason was specified\n\n[Here is a link to rejoin the server](%s)", event.getGuild().getName(), event.getGuild().getDefaultChannel().createInvite().complete().getUrl()));
                target.setColor(utils.failedRed);
                target.setTimestamp(Instant.now());
                target.setFooter("Quiver Softbanned Member | Private Message");

                event.replyEmbeds(eb.build()).queue((msg) -> {
                    eb.clear();
                    msg.deleteOriginal().queueAfter(30, TimeUnit.SECONDS);
                    event.getGuild().getTextChannelCache().getElementById(quiver.getGuildManager().getGuild(event.getGuild()).getConfig().getLogChannel()).sendMessageEmbeds(log.build()).queue((msg2) -> {
                        log.clear();
                        event.getOption("member").getAsMember().getUser().openPrivateChannel().queue((channel) -> {
                            channel.sendMessageEmbeds(target.build()).queue();
                            target.clear();
                            event.getGuild().ban(event.getOption("member").getAsMember().getUser(), 7, String.format("No Reason | Softban Executor: %s", event.getMember().getUser().getAsTag())).queue((__) -> {
                                event.getGuild().unban(event.getOption("member").getAsMember().getUser()).queue();
                            });
                        });
                    });
                });
            }
        }
    }

}
