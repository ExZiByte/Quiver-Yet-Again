package me.exzibyte.Listeners.Moderation;

import me.exzibyte.Quiver;
import me.exzibyte.Utilities.Utilities;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.sql.Time;
import java.time.Instant;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Clear extends ListenerAdapter {

    private final Quiver quiver;
    Utilities utils = new Utilities();

    public Clear(Quiver quiver){
        this.quiver = quiver;
    }

    public void onSlashCommandInteraction(SlashCommandInteractionEvent event){
        if(!event.getName().equals("clear")) return;
        EmbedBuilder eb = new EmbedBuilder();
        EmbedBuilder log = new EmbedBuilder();

        if (quiver.getGuildManager().getConfig(event.getGuild().getId()).isBlacklisted()) {
            eb.setDescription(":exclamation: This server is blacklisted and has lost the ability to use Quiver\n\nYou may appeal [here](https://quiverbot.io/blacklisted/appeal?guild=" + event.getGuild().getId() + "\"Quiver Blacklisted Server Appeal for " + event.getGuild().getName() + "\")");
            eb.setColor(utils.failedRed);
            eb.setTimestamp(Instant.now());
            eb.setFooter("Quiver Blacklisted Guild");

            event.replyEmbeds(eb.build()).queue();
            return;
        }

        if(event.getMember().hasPermission(Permission.MESSAGE_MANAGE)){
            if(event.getOptions().size() < 2){
                eb.setDescription(String.format("Cleared **%s** messages from %s", event.getOption("amount").getAsInt(), event.getChannel().getAsMention()));
                eb.setColor(utils.successGreen);
                eb.setFooter("Quiver Messages Cleared");

                log.setDescription(String.format("%s cleared **%s** messages from %s", event.getMember().getAsMention(), event.getOption("amount").getAsInt(), event.getChannel().getAsMention()));
                log.setColor(utils.warningYellow);
                log.setTimestamp(Instant.now());
                log.setFooter("Quiver Messages Cleared | Log");

                event.replyEmbeds(eb.build()).queue((msg) -> {
                    eb.clear();
                    event.getGuild().getTextChannelCache().getElementById(quiver.getGuildManager().getConfig(event.getGuild().getId()).getLogChannel()).sendMessageEmbeds(log.build()).queue((msg2) -> log.clear());
                    event.getChannel().getHistory().retrievePast(event.getOption("amount").getAsInt() + 1).queue((history) ->{
                        event.getGuild().getTextChannelCache().getElementById(event.getChannel().getId()).deleteMessages(history).queue();
                    });

                });
            }
            if(event.getOptions().size() == 3){
                eb.setDescription(String.format("Cleared **%s** messages from %s", event.getOption("amount").getAsInt(), event.getOption("channel").getAsTextChannel().getAsMention()));
                eb.setColor(utils.successGreen);
                eb.setFooter("Quiver Messages Cleared");

                log.setDescription(String.format("%s cleared **%s** messages from %s", event.getMember().getAsMention(), event.getOption("amount").getAsInt(), event.getOption("channel").getAsTextChannel().getAsMention()));
                log.setColor(utils.warningYellow);
                log.setTimestamp(Instant.now());
                log.setFooter("Quiver Messages Cleared | Log");

                event.replyEmbeds(eb.build()).queue((msg) -> {
                    eb.clear();
                    event.getGuild().getTextChannelCache().getElementById(quiver.getGuildManager().getConfig(event.getGuild().getId()).getLogChannel()).sendMessageEmbeds(log.build()).queue((msg2) -> log.clear());
                    event.getGuild().getTextChannelCache().getElementById(event.getOption("channel").getAsTextChannel().getId()).getHistory().retrievePast(event.getOption("amount").getAsInt() + 1).queue((history) -> {
                        event.getGuild().getTextChannelCache().getElementById(event.getOption("channel").getAsTextChannel().getId()).deleteMessages(history).queue();
                    });
                });
            }
        } else {
            eb.setDescription("Insufficient Permissions\nYou require the permission to delete messages from this guild");
            eb.setColor(utils.warningYellow);
            eb.setFooter("Quiver Insufficient Permissions");

            event.replyEmbeds(eb.build()).queue((msg) -> {
                msg.deleteOriginal().queueAfter(30, TimeUnit.SECONDS);
                eb.clear();
            });
        }
    }

    public void onMessageReceived(MessageReceivedEvent event){
        String[] args = event.getMessage().getContentRaw().split("\\s+");
        EmbedBuilder eb = new EmbedBuilder();
        EmbedBuilder log = new EmbedBuilder();

        if(event.isFromGuild()){
            if(args[0].equalsIgnoreCase(quiver.getGuildManager().getConfig(event.getGuild().getId()).getPrefix() + "clear")){
                if(event.getMember().hasPermission(Permission.MESSAGE_MANAGE)){
                }
            }
        }
    }

}
