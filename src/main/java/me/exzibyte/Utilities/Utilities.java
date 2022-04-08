package me.exzibyte.Utilities;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;

public class Utilities {

    public int successGreen = 0x54c72a;
    public int warningYellow = 0xe6da35;
    public int failedRed = 0xd63f3a;

    public String getEmote(String emoteID, JDA jda){
        return jda.getEmoteById(emoteID).getAsMention();
    }


}
