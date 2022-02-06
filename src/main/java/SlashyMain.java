import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;
import net.dv8tion.jda.api.sharding.ShardManager;

import javax.security.auth.login.LoginException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import static net.dv8tion.jda.api.OnlineStatus.OFFLINE;
import static net.dv8tion.jda.api.entities.Activity.listening;
import static secret.Tokenholder.BOT_TOKEN;

public class SlashyMain {

    // Connection zum Bot
    public ShardManager shardManager;

    public static void main(String[] args) {
        // Wir müssen am Anfang den Bot instanziieren
        try {
            new SlashyMain();
        } catch (LoginException e) {
            e.printStackTrace();
        }
    }

    // Konstruktor der eine Instanz des Bots erzeugt
    public SlashyMain() throws LoginException {
        this.shardManager = configureShardManager();
        System.out.println("Bot geht online");
    }




    // Erstellt den Bot und übergibt ihm das wichtigste was er brauch
    public ShardManager configureShardManager() throws LoginException {
        // * * * Startup (Konfiguriert den Bot ganz nackig) * * * //
        DefaultShardManagerBuilder builder = DefaultShardManagerBuilder.createDefault("");
        builder.setToken(BOT_TOKEN);

        // * * * Einstellungen * * * //
        // Was macht er gerate
        builder.setActivity(listening("deinem Bullshit"));
        // Onlinestatus
        builder.setStatus(OnlineStatus.ONLINE);

        // Build (Starten des Bots)
        return builder.build();
    }


    }
}
