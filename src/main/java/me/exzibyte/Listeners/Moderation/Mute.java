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

public class Mute extends ListenerAdapter {

    private final Quiver quiver;
    Utilities utils = new Utilities();

    public Mute(Quiver quiver){
        this.quiver = quiver;
    }

    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if(!event.getName().equals("mute")) return;
        EmbedBuilder eb = new EmbedBuilder();
        EmbedBuilder log = new EmbedBuilder();
        EmbedBuilder target = new EmbedBuilder();

        var guild = quiver.getGuildManager().getGuild(event.getGuild());
        var config = guild.getConfig();

        if (config.isBlacklisted()) {
            event.replyEmbeds(StaticEmbeds.blacklisted(event.getGuild())).queue();
            return;
        }

        if(event.getMember().hasPermission(Permission.VOICE_MUTE_OTHERS)) {
            if(event.getOptions().size() == 1) {
                eb.setDescription(String.format("Muted %s for No Reason Specified", event.getOption("member").getAsMember().getAsMention()));
                eb.setColor(utils.successGreen);
                eb.setFooter("Quiver Member Muted");

                log.setDescription(String.format("%s muted member: %s \n\nReason: \n```No Reason Specified\n```", event.getMember().getAsMention(), event.getOption("member").getAsMember().getAsMention()));
                log.setColor(utils.warningYellow);
                log.setTimestamp(Instant.now());
                log.setFooter("Quiver Member Muted | Log");

                target.setDescription(String.format("You've been muted on %s\nReason:\nNo reason specified\n", event.getGuild().getName()));
                target.setColor(utils.failedRed);
                target.setTimestamp(Instant.now());
                target.setFooter("Quiver Member Muted | Private Message");

                event.replyEmbeds(eb.build()).queue((msg) -> {
                    msg.deleteOriginal().queueAfter(30, TimeUnit.SECONDS);
                    event.getGuild().getTextChannelCache().getElementById(quiver.getGuildManager().getGuild(event.getGuild()).getConfig().getLogChannel()).sendMessageEmbeds(log.build()).queue((msg2) -> {
                        log.clear();
                        event.getOption("member").getAsMember().getUser().openPrivateChannel().queue((channel) -> {
                           channel.sendMessageEmbeds(target.build()).queue();
                           target.clear();
                           event.getGuild().addRoleToMember(event.getOption("member").getAsMember(), event.getGuild().getRoleById(quiver.getGuildManager().getGuild(event.getGuild()).getConfig().getMuteRole())).queue();
                        });
                    });
                });
            }
            if(event.getOptions().size() == 2){
                String reason = event.getOption("reason").getAsString();
                eb.setDescription(String.format("Muted %s for %s", event.getOption("member").getAsMember().getAsMention(), reason));
                eb.setColor(utils.successGreen);
                eb.setFooter("Quiver Member Muted");

                log.setDescription(String.format("%s muted member: %s \n\nReason: \n```%s\n```", event.getMember().getAsMention(), event.getOption("member").getAsMember().getAsMention(), reason));
                log.setColor(utils.warningYellow);
                log.setTimestamp(Instant.now());
                log.setFooter("Quiver Member Muted | Log");

                target.setDescription(String.format("You've been muted on %s\nReason:\n%s\n```", event.getGuild().getName(), reason));
                target.setColor(utils.failedRed);
                target.setTimestamp(Instant.now());
                target.setFooter("Quiver Member Muted | Private Message");

                event.replyEmbeds(eb.build()).queue((msg) -> {
                    msg.deleteOriginal().queueAfter(30, TimeUnit.SECONDS);
                    event.getGuild().getTextChannelCache().getElementById(quiver.getGuildManager().getGuild(event.getGuild()).getConfig().getLogChannel()).sendMessageEmbeds(log.build()).queue((msg2) -> {
                        log.clear();
                        event.getOption("member").getAsMember().getUser().openPrivateChannel().queue((channel) -> {
                            channel.sendMessageEmbeds(target.build()).queue();
                            target.clear();
                            event.getGuild().addRoleToMember(event.getOption("member").getAsMember(), event.getGuild().getRoleById(quiver.getGuildManager().getGuild(event.getGuild()).getConfig().getMuteRole())).queue();
                        });
                    });
                });
            }
            if(event.getOptions().size() >= 3){
                String reason = event.getOption("reason").getAsString();
                eb.setDescription(String.format("Muted %s for %s", event.getOption("member").getAsMember().getAsMention(), reason));
                eb.setImage(event.getOption("proof").getAsAttachment().getUrl());
                eb.setColor(utils.successGreen);
                eb.setFooter("Quiver Member Muted");

                log.setDescription(String.format("%s muted member: %s \n\nReason: \n```%s\n```", event.getMember().getAsMention(), event.getOption("member").getAsMember().getAsMention(), reason));
                eb.setImage(event.getOption("proof").getAsAttachment().getUrl());
                log.setColor(utils.warningYellow);
                log.setTimestamp(Instant.now());
                log.setFooter("Quiver Member Muted | Log");

                target.setDescription(String.format("You've been muted on %s\nReason:\n%s\n```", event.getGuild().getName(), reason));
                eb.setImage(event.getOption("proof").getAsAttachment().getUrl());
                target.setColor(utils.failedRed);
                target.setTimestamp(Instant.now());
                target.setFooter("Quiver Member Muted | Private Message");

                event.replyEmbeds(eb.build()).queue((msg) -> {
                    msg.deleteOriginal().queueAfter(30, TimeUnit.SECONDS);
                    event.getGuild().getTextChannelCache().getElementById(quiver.getGuildManager().getGuild(event.getGuild()).getConfig().getLogChannel()).sendMessageEmbeds(log.build()).queue((msg2) -> {
                        log.clear();
                        event.getOption("member").getAsMember().getUser().openPrivateChannel().queue((channel) -> {
                            channel.sendMessageEmbeds(target.build()).queue();
                            target.clear();
                            event.getGuild().addRoleToMember(event.getOption("member").getAsMember(), event.getGuild().getRoleById(quiver.getGuildManager().getGuild(event.getGuild()).getConfig().getMuteRole())).queue();
                        });
                    });
                });
            }
        } else {
            eb.setDescription("Insufficient Permission!\nYou require the permission to mute members on this guild.\n\n If you believe this message was shown in error contact the guild owner.");
            eb.setColor(utils.warningYellow);
            eb.setFooter("Quiver Insufficient Permissions");

            event.replyEmbeds(eb.build()).queue((msg) -> {
                msg.deleteOriginal().queueAfter(30, TimeUnit.SECONDS);
                eb.clear();
            });
        }
    }

}
