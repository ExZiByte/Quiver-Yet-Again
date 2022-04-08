package me.exzibyte;

import me.exzibyte.Listeners.Miscellaneous.JoinedGuild;
import me.exzibyte.Listeners.Miscellaneous.Ready;
import me.exzibyte.Listeners.Moderation.Ban;
import me.exzibyte.Listeners.Moderation.Clear;
import me.exzibyte.Listeners.Moderation.Kick;
import me.exzibyte.Listeners.Moderation.Mute;
import me.exzibyte.Listeners.Settings.Settings;
import me.exzibyte.Utilities.*;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;
import net.dv8tion.jda.api.sharding.ShardManager;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.MemberCachePolicy;

import javax.security.auth.login.LoginException;

public class Quiver {

    private final Config config;
    private final GuildManager guildManager;
    private static ShardManager manager;
    private static DefaultShardManagerBuilder quiver;
    private final Database database;
    private final Logging logging = new Logging();
    private Quiver() throws LoginException {

        //Load Config class
        config = new Config(this);
        config.load();
        this.database = new Database(this);
        database.connect();
        this.guildManager = new GuildManager(this);
        //Access the Config file and instantiate a JDA Instance with the token field's value
        quiver = DefaultShardManagerBuilder.createDefault(getConfig().get("token"));


        quiver.setActivity(Activity.watching("my quiver being filled with arrows"));
        quiver.setStatus(OnlineStatus.DO_NOT_DISTURB);

        quiver.enableIntents(GatewayIntent.GUILD_MEMBERS, GatewayIntent.GUILD_MESSAGES);
        quiver.setMemberCachePolicy(MemberCachePolicy.ALL);
        quiver.setChunkingFilter(ChunkingFilter.ALL);

        quiver.addEventListeners(

                // Miscellaneous Listeners
                new JoinedGuild(this),
                new Ready(this),

                //Moderation Listeners
                new Ban(this),
                new Clear(this),
                new Kick(this),
                new Mute(this),

                new Settings(),

                getGuildManager()

        );

        quiver.setEnableShutdownHook(true);
        quiver.setUseShutdownNow(false);
        quiver.setShardsTotal(Integer.parseInt(getConfig().get("shardCount")));

        manager = quiver.build();
    }

    public static void main(String[] args) throws LoginException{
        new Quiver();
    }

    public static ShardManager getManager() {
        return manager;
    }

    public Database getDatabase(){
        return database;
    }

    public Config getConfig(){
        return config;
    }

    public GuildManager getGuildManager(){
        return guildManager;
    }
}
