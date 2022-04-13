package me.exzibyte;

import me.exzibyte.Listeners.Miscellaneous.JoinedGuild;
import me.exzibyte.Listeners.Miscellaneous.RandomShit;
import me.exzibyte.Listeners.Miscellaneous.Ready;
import me.exzibyte.Listeners.Moderation.Ban;
import me.exzibyte.Listeners.Moderation.Clear;
import me.exzibyte.Listeners.Moderation.Kick;
import me.exzibyte.Listeners.Moderation.Mute;
import me.exzibyte.Listeners.Settings.Groups;
import me.exzibyte.Listeners.Settings.Settings;
import me.exzibyte.Utilities.*;
import me.exzibyte.arrow.ArrowController;
import me.exzibyte.command.CommandRegistry;
import me.exzibyte.command.DebugCommand;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.exceptions.RateLimitedException;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;
import net.dv8tion.jda.api.sharding.ShardManager;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.MemberCachePolicy;

import javax.security.auth.login.LoginException;

public class Quiver {

    private final Config config;
    private final GuildManager guildManager;
    private final CommandRegistry commandRegistry;
    private static ShardManager manager;
    private static DefaultShardManagerBuilder quiver;
    private final Database database;
    private final Logging logging = new Logging();

    private final ArrowController arrowController;

    private Quiver() throws LoginException, RateLimitedException {

        //Load Config class
        config = new Config(this);
        config.load();
        this.database = new Database(this);
        database.connect();
        this.guildManager = new GuildManager(this);
        this.commandRegistry = new CommandRegistry(this);
        this.arrowController = new ArrowController(this);

        //Access the Config file and instantiate a JDA Instance with the token field's value
        manager = DefaultShardManagerBuilder.createDefault(getConfig().get("token"))
                .setActivity(Activity.watching("my quiver being filled with arrows"))
                .setStatus(OnlineStatus.DO_NOT_DISTURB)
                .enableIntents(GatewayIntent.GUILD_MEMBERS, GatewayIntent.GUILD_MESSAGES)
                .setMemberCachePolicy(MemberCachePolicy.ALL)
                .setChunkingFilter(ChunkingFilter.ALL)
                .addEventListeners(

                        // Miscellaneous Listeners
                        new JoinedGuild(this),
                        new Ready(this),

                        //Moderation Listeners
                        new Ban(this),
                        new Clear(this),
                        new Kick(this),
                        new Mute(this),

                        //Settings Listeners
                        new Groups(this),
                        new Settings(),

                        getGuildManager(),
                        getCommandRegistry()
                )
                .setEnableShutdownHook(true)
                .setUseShutdownNow(false)
                .setShardsTotal(Integer.parseInt(getConfig().get("shardCount")))
                .build();

        arrowController.loadArrows(); // :D
    }

    public static void main(String[] args) throws LoginException, RateLimitedException {
        new Quiver();
    }

    public static ShardManager getManager() {
        return manager;
    }

    public Database getDatabase() {
        return database;
    }

    public Config getConfig() {
        return config;
    }

    public GuildManager getGuildManager() {
        return guildManager;
    }

    public CommandRegistry getCommandRegistry() {
        return commandRegistry;
    }

    public ArrowController getArrowController() {
        return arrowController;
    }

    public void onReady() {
    }
}
