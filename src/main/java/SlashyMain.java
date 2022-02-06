import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;

import javax.security.auth.login.LoginException;

import static net.dv8tion.jda.api.entities.Activity.listening;
import static secret.Tokenholder.BOT_TOKEN;

public class SlashyMain {

    public static void main(String[] args) throws LoginException {

        // * * * Startup (Konfiguriert den Bot ganz nackig) * * * //
        DefaultShardManagerBuilder builder = DefaultShardManagerBuilder.createDefault("");
        builder.setToken(BOT_TOKEN);

        // * * * Einstellungen * * * //
        // Was macht er gerate
        builder.setActivity(listening("deinem Bullshit"));
        // Onlinestatus
        builder.setStatus(OnlineStatus.ONLINE);

        // Build (Starten des Bots)
        builder.build();
    }
}
