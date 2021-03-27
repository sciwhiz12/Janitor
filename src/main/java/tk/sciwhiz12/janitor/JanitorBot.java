/*
 * Copyright (c) 2021 SciWhiz12
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to use,
 * copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the
 * Software, and to permit persons to whom the Software is furnished to do so, subject
 * to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
 * PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF
 * CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE
 * OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

/*
 * Copyright (c) 2021 SciWhiz12
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to use,
 * copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the
 * Software, and to permit persons to whom the Software is furnished to do so, subject
 * to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
 * PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF
 * CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE
 * OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package tk.sciwhiz12.janitor;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tk.sciwhiz12.janitor.bot.BotOptions;

import javax.security.auth.login.LoginException;
import java.time.OffsetDateTime;
import java.util.EnumSet;
import java.util.Objects;

public class JanitorBot extends ListenerAdapter {
    public static final Logger LOGGER = LoggerFactory.getLogger(JanitorBot.class);
    // TODO: fine-tune the gateway intents
    private static final EnumSet<GatewayIntent> GATEWAY_INTENTS = EnumSet.allOf(GatewayIntent.class);

    private static final String version = Objects.requireNonNullElse(JanitorBot.class.getPackage().getImplementationVersion(), "DEVELOPMENT-" + OffsetDateTime.now().toString());

    private final BotOptions options;
    private final JDA jda;

    public JanitorBot(BotOptions options) throws LoginException {
        LOGGER.info("Starting up Janitor v{}", version);

        this.options = options;
        final String token = options.getToken().orElseThrow(() -> new IllegalArgumentException("Bot token not found. Supply via config file or CLI option"));

        this.jda = JDABuilder.createDefault(token, GATEWAY_INTENTS)
                .setStatus(OnlineStatus.DO_NOT_DISTURB)
                .addEventListeners(this)
                .build();
    }

    @Override
    public void onReady(@NotNull ReadyEvent event) {
        LOGGER.info("Connection is ready");
        jda.getPresence().setPresence(OnlineStatus.ONLINE, null);
    }

    public BotOptions getOptions() {
        return options;
    }

    public JDA getJda() {
        return jda;
    }
}
