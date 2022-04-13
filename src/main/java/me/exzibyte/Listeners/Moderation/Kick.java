package me.exzibyte.Listeners.Moderation;

import me.exzibyte.Quiver;
import me.exzibyte.Utilities.StaticEmbeds;
import me.exzibyte.Utilities.Utilities;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.time.Instant;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class Kick extends ListenerAdapter {

    private final Quiver quiver;
    Utilities utils = new Utilities();

    public Kick(Quiver quiver) {
        this.quiver = quiver;
    }

    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (!event.getName().equals("kick")) return;
        EmbedBuilder eb = new EmbedBuilder();
        EmbedBuilder log = new EmbedBuilder();
        EmbedBuilder target = new EmbedBuilder();

        var guild = quiver.getGuildManager().getGuild(event.getGuild());
        var config = guild.getConfig();

        if (config.isBlacklisted()) {
            event.replyEmbeds(StaticEmbeds.blacklisted(event.getGuild())).queue();
            return;
        }


        if(event.getMember().hasPermission(Permission.KICK_MEMBERS)){
            if(event.getOptions().size() == 1){
                eb.setDescription(String.format("Kicked %s for No Reason Specified", event.getOption("member").getAsMember().getAsMention()));
                eb.setColor(utils.successGreen);
                eb.setFooter("Quiver Member Kicked");

                log.setDescription(String.format("%s Kicked member: %s\n\nReason:\n```\nNo Reason\n```", event.getMember().getAsMention(), event.getOption("member").getAsMember().getAsMention()));
                log.setColor(utils.warningYellow);
                log.setTimestamp(Instant.now());
                log.setFooter("Quiver Member Kicked | Log");

                target.setDescription(String.format("You've been Kicked from %s \n Reason: No reason was specified", event.getGuild().getName()));
                target.setColor(utils.failedRed);
                target.setTimestamp(Instant.now());
                target.setFooter("Quiver Kicked Member | Private Message");

                event.replyEmbeds(eb.build()).queue((msg) -> {
                    eb.clear();
                    msg.deleteOriginal().queueAfter(30, TimeUnit.SECONDS);
                    event.getGuild().getTextChannelCache().getElementById(quiver.getGuildManager().getGuild(event.getGuild()).getConfig().getLogChannel()).sendMessageEmbeds(log.build()).queue((msg2) -> {
                        log.clear();
                        event.getOption("member").getAsMember().getUser().openPrivateChannel().queue((channel) -> {
                            channel.sendMessageEmbeds(target.build()).queue();
                            target.clear();
                            event.getGuild().kick(event.getOption("member").getAsMember(), String.format("No Reason | Kick Executor: %s", event.getMember().getUser().getAsTag())).queue();
                        });
                    });
                });
            }
            if(event.getOptions().size() == 2){
                String reason = event.getOption("reason").getAsString();
                eb.setDescription(String.format("Kicked %s for %s", event.getOption("member").getAsMember().getAsMention(), reason));
                eb.setColor(utils.successGreen);
                eb.setFooter("Quiver Member Kicked");

                log.setDescription(String.format("%s Kicked member: %s\n\nReason:\n```\n%s\n```", event.getMember().getAsMention(), event.getOption("member").getAsMember().getAsMention(), reason));
                log.setColor(utils.warningYellow);
                log.setTimestamp(Instant.now());
                log.setFooter("Quiver Member Kicked | Log");

                target.setDescription(String.format("You've been Kicked from %s \n Reason: %s", event.getGuild().getName(), reason));
                target.setColor(utils.failedRed);
                target.setTimestamp(Instant.now());
                target.setFooter("Quiver Kicked Member | Private Message");

                event.replyEmbeds(eb.build()).queue((msg) -> {
                    eb.clear();
                    msg.deleteOriginal().queueAfter(30, TimeUnit.SECONDS);
                    event.getGuild().getTextChannelCache().getElementById(quiver.getGuildManager().getGuild(event.getGuild()).getConfig().getLogChannel()).sendMessageEmbeds(log.build()).queue((msg2) -> {
                        log.clear();
                        event.getOption("member").getAsMember().getUser().openPrivateChannel().queue((channel) -> {
                            channel.sendMessageEmbeds(target.build()).queue();
                            target.clear();
                            event.getGuild().kick(event.getOption("member").getAsMember(), String.format("%s | Kick Executor: %s", reason, event.getMember().getUser().getAsTag())).queue();
                        });
                    });
                });
            }
            if(event.getOptions().size() >= 3){
                String reason = event.getOption("reason").getAsString();
                eb.setDescription(String.format("Kicked %s for %s", event.getOption("member").getAsMember().getAsMention(), reason));
                eb.setImage(event.getOption("proof").getAsAttachment().getUrl());
                eb.setColor(utils.successGreen);
                eb.setFooter("Quiver Member Kicked");

                log.setDescription(String.format("%s Kicked member: %s\n\nReason:\n```\n%s\n```", event.getMember().getAsMention(), event.getOption("member").getAsMember().getAsMention(), reason));
                log.setImage(event.getOption("proof").getAsAttachment().getUrl());
                log.setColor(utils.warningYellow);
                log.setTimestamp(Instant.now());
                log.setFooter("Quiver Member Kicked | Log");

                target.setDescription(String.format("You've been Kicked from %s \n Reason: %s", event.getGuild().getName(), reason));
                target.setImage(event.getOption("proof").getAsAttachment().getUrl());
                target.setColor(utils.failedRed);
                target.setTimestamp(Instant.now());
                target.setFooter("Quiver Kicked Member | Private Message");

                event.replyEmbeds(eb.build()).queue((msg) -> {
                    eb.clear();
                    msg.deleteOriginal().queueAfter(30, TimeUnit.SECONDS);
                    event.getGuild().getTextChannelCache().getElementById(quiver.getGuildManager().getGuild(event.getGuild()).getConfig().getLogChannel()).sendMessageEmbeds(log.build()).queue((msg2) -> {
                        log.clear();
                        event.getOption("member").getAsMember().getUser().openPrivateChannel().queue((channel) -> {
                            channel.sendMessageEmbeds(target.build()).queue();
                            target.clear();
                            event.getGuild().kick(event.getOption("member").getAsMember(), String.format("%s | Kick Executor: %s", reason, event.getMember().getUser().getAsTag())).queue();
                        });
                    });
                });
            }
        } else {
            eb.setDescription("Insufficient Permission!\nYou require the permission to Kick members from this guild.\n\n If you believe this message was shown in error contact the guild owner.");
            eb.setColor(utils.warningYellow);
            eb.setFooter("Quiver Insufficient Permissions");

            event.replyEmbeds(eb.build()).queue((msg) -> {
                msg.deleteOriginal().queueAfter(30, TimeUnit.SECONDS);
                eb.clear();
            });
        }
    }


}
