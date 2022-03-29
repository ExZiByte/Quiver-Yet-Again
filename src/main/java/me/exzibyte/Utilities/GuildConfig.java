package me.exzibyte.Utilities;
import org.bson.Document;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Object representation of all the config values for a specific guild
 */
public class GuildConfig {

    private final Document document;
    private final Map<String, List<String>> groups;
    private static final Logging LOGGING = new Logging();
    private final String prefix;
    private final String locale;
    private final String logChannelID;
    private final String joinLogID;
    private final String muteRoleID;

    public GuildConfig(Document document) {
        this.document = document;
        this.groups = new HashMap<>();
        this.locale = document.getString("locale");
        this.prefix = document.getString("prefix");
        this.logChannelID = document.getString("logChannelID");
        this.joinLogID = document.getString("joinLogID");
        this.muteRoleID = document.getString("muteRoleID");
    }


    public String getPrefix() {
        return prefix;
    }

    public String getLogChannel() {
        return logChannelID;
    }

    public String getJoinLog() {
        return joinLogID;
    }

    public String getMuteRole() {
        return muteRoleID;
    }

    public String getLocale() {
        return locale;
    }

}
