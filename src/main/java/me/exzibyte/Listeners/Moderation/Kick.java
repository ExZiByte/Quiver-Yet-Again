package me.exzibyte.Listeners.Moderation;

import me.exzibyte.Quiver;
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
        if (quiver.getGuildManager().getConfig(event.getGuild().getId()).isBlacklisted()) {
            eb.setDescription(":exclamation: This server is blacklisted and has lost the ability to use Quiver\n\nYou may appeal [here](https://quiverbot.io/blacklisted/appeal?guild=" + event.getGuild().getId() + "\"Quiver Blacklisted Server Appeal for " + event.getGuild().getName() + "\")");
            eb.setColor(utils.failedRed);
            eb.setTimestamp(Instant.now());
            eb.setFooter("Quiver Blacklisted Guild");

            event.replyEmbeds(eb.build()).queue();
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
                    event.getGuild().getTextChannelCache().getElementById(quiver.getGuildManager().getConfig(event.getGuild().getId()).getLogChannel()).sendMessageEmbeds(log.build()).queue((msg2) -> {
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
                    event.getGuild().getTextChannelCache().getElementById(quiver.getGuildManager().getConfig(event.getGuild().getId()).getLogChannel()).sendMessageEmbeds(log.build()).queue((msg2) -> {
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
                    event.getGuild().getTextChannelCache().getElementById(quiver.getGuildManager().getConfig(event.getGuild().getId()).getLogChannel()).sendMessageEmbeds(log.build()).queue((msg2) -> {
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

    public void onMessageReceived(MessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split("\\s+");
        EmbedBuilder eb = new EmbedBuilder();
        EmbedBuilder log = new EmbedBuilder();
        EmbedBuilder target = new EmbedBuilder();


        if (event.isFromGuild()) {
            if (args[0].equalsIgnoreCase(quiver.getGuildManager().getConfig(event.getGuild().getId()).getPrefix() + "kick")) {
                if (event.getMember().hasPermission(Permission.KICK_MEMBERS)) {
                    if (args.length < 2) {
                        eb.setDescription("Insufficient Arguments\nYou have not provided enough arguments for this command to run successfully");
                        eb.setColor(utils.warningYellow);
                        eb.setFooter("Quiver Insufficient Arguments");

                        event.getChannel().sendMessageEmbeds(eb.build()).queue((msg) -> {
                            msg.delete().queueAfter(30, TimeUnit.SECONDS);
                            eb.clear();
                        });
                    }
                    if (args.length == 2) {
                        eb.setDescription(String.format("Kicked %s for No Reason Specified", event.getMessage().getMentionedMembers().get(0)));
                        eb.setColor(utils.successGreen);
                        eb.setFooter("Quiver Member Kicked");

                        log.setDescription(String.format("%s Kicked member: %s\n\nReason:\n```\nNo Reason\n```", event.getMember().getAsMention(), event.getMessage().getMentionedMembers().get(0)));
                        log.setColor(utils.warningYellow);
                        log.setTimestamp(Instant.now());
                        log.setFooter("Quiver Member Kicked | Log");

                        target.setDescription(String.format("You've been Kicked from %s \n Reason: No reason was specified", event.getGuild().getName()));
                        target.setColor(utils.failedRed);
                        target.setTimestamp(Instant.now());
                        target.setFooter("Quiver Kicked Member | Private Message");

                        event.getChannel().sendMessageEmbeds(eb.build()).queue((msg) -> {
                            msg.delete().queueAfter(30, TimeUnit.SECONDS);
                            eb.clear();
                            event.getGuild().getTextChannelCache().getElementById(quiver.getGuildManager().getConfig(event.getGuild().getId()).getLogChannel()).sendMessageEmbeds(log.build()).queue((msg2) -> {
                                log.clear();
                                event.getMessage().getMentionedMembers().get(0).getUser().openPrivateChannel().queue((channel) -> {
                                    channel.sendMessageEmbeds(target.build()).queue((msg3) -> {
                                        target.clear();
                                        event.getGuild().kick(event.getMessage().getMentionedMembers().get(0), String.format("No Reason | Kick Executor: %s", event.getMember().getUser().getAsTag())).queue();
                                    });
                                });
                            });
                        });
                    }
                    if (args.length >= 3) {
                        String reason = Arrays.stream(args).skip(2).collect(Collectors.joining(" "));
                        eb.setDescription(String.format("Kicked %s for %s", event.getMessage().getMentionedMembers().get(0), reason));
                        eb.setColor(utils.successGreen);
                        eb.setFooter("Quiver Member Kicked");

                        log.setDescription(String.format("%s Kicked member: %s\n\nReason:\n```\n%s\n```", event.getMember().getAsMention(), event.getMessage().getMentionedMembers().get(0), reason));
                        log.setColor(utils.warningYellow);
                        log.setTimestamp(Instant.now());
                        log.setFooter("Quiver Member Kicked | Log");

                        target.setDescription(String.format("You've been Kicked from %s \n Reason: %s", event.getGuild().getName(), reason));
                        target.setColor(utils.failedRed);
                        target.setTimestamp(Instant.now());
                        target.setFooter("Quiver Kicked Member | Private Message");

                        event.getChannel().sendMessageEmbeds(eb.build()).queue((msg) -> {
                            msg.delete().queueAfter(30, TimeUnit.SECONDS);
                            eb.clear();
                            event.getGuild().getTextChannelCache().getElementById(quiver.getGuildManager().getConfig(event.getGuild().getId()).getLogChannel()).sendMessageEmbeds(log.build()).queue((msg2) -> {
                                log.clear();
                                event.getMessage().getMentionedMembers().get(0).getUser().openPrivateChannel().queue((channel) -> {
                                    channel.sendMessageEmbeds(target.build()).queue((msg3) -> {
                                        target.clear();
                                        event.getGuild().kick(event.getMessage().getMentionedMembers().get(0), String.format("%s | Kick Executor: %s", reason, event.getMember().getUser().getAsTag())).queue();
                                    });
                                });
                            });
                        });
                    }
                } else {
                    eb.setDescription("Insufficient Permission!\nYou require the permission to kick members from this guild.\n\n If you believe this message was shown in error contact the guild owner.");
                    eb.setColor(utils.warningYellow);
                    eb.setFooter("Quiver Insufficient Permissions");

                    event.getChannel().sendMessageEmbeds(eb.build()).queue((msg) -> {
                        msg.delete().queueAfter(30, TimeUnit.SECONDS);
                        eb.clear();
                    });
                }
            }
        }
    }
}
