import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;

import javax.security.auth.login.LoginException;

import static secret.Tokenholder.BOT_TOKEN;

public class SlashyMain {

    public static void main(String[] args) throws LoginException {

        DefaultShardManagerBuilder builder = DefaultShardManagerBuilder.createDefault("");
        builder.setToken(BOT_TOKEN);








        builder.build();
    }
}
