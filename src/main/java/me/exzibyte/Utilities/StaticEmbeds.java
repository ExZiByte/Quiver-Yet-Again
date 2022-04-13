package me.exzibyte.Utilities;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.time.Instant;

public class StaticEmbeds {

    static Utilities utils = new Utilities();
    static EmbedBuilder eb = new EmbedBuilder();

    public static MessageEmbed blacklisted(Guild guild){
        eb.clear();
        eb.setDescription(":exclamation: This server is blacklisted and has lost the ability to use Quiver\n\nYou may appeal [here](https://quiverbot.io/blacklisted/appeal?guild=" + guild.getId() + "\"Quiver Blacklisted Server Appeal for " + guild.getName() + "\")");
        eb.setColor(utils.failedRed);
        eb.setTimestamp(Instant.now());
        eb.setFooter("Quiver Blacklisted Guild");
        return eb.build();
    }
}
