package me.exzibyte;

import me.exzibyte.Listeners.MIscellaneous.Ready;
import me.exzibyte.Listeners.Moderation.Ban;
import me.exzibyte.Listeners.Moderation.Kick;
import me.exzibyte.Listeners.Moderation.Mute;
import me.exzibyte.Utilities.Config;
import me.exzibyte.Utilities.Database;
import me.exzibyte.Utilities.GuildConfig;
import me.exzibyte.Utilities.Logging;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;
import net.dv8tion.jda.api.sharding.ShardManager;
import net.dv8tion.jda.api.utils.MemberCachePolicy;

import javax.security.auth.login.LoginException;

public class Quiver {

    private final Config config;
    private final GuildConfig gConfig;
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
        gConfig = new GuildConfig(this);
        gConfig.load();
        //Access the Config file and instantiate a JDA Instance with the token field's value
        quiver = DefaultShardManagerBuilder.createDefault(getConfig().get("token"));


        quiver.setActivity(Activity.watching("my quiver being filled with arrows"));
        quiver.setStatus(OnlineStatus.DO_NOT_DISTURB);

        quiver.enableIntents(GatewayIntent.GUILD_MEMBERS, GatewayIntent.GUILD_MESSAGES);
        quiver.setMemberCachePolicy(MemberCachePolicy.ALL);

        quiver.addEventListeners(

                // Miscellaneous Listeners
                new Ready(),

                //Moderation Listeners
                new Ban(this),
                new Kick(this),
                new Mute(this)

        );

        quiver.setEnableShutdownHook(true);
        quiver.setUseShutdownNow(false);
        quiver.setShardsTotal(Integer.parseInt(getConfig().get("shardCount")));

        manager = quiver.build();
    }

    public static void main(String[] args) throws LoginException{
        new Quiver();
    }

    public Database getDatabase(){
        return database;
    }

    public Config getConfig(){
        return config;
    }

    public GuildConfig getGuildConfig(){
        return gConfig;
    }

}
