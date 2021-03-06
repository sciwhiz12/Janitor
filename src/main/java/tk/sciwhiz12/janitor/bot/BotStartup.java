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

package tk.sciwhiz12.janitor.bot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tk.sciwhiz12.janitor.JanitorBot;
import tk.sciwhiz12.janitor.bot.config.BotFileConfig;
import tk.sciwhiz12.janitor.bot.config.CommandLineBotOptions;
import tk.sciwhiz12.janitor.bot.config.MergedBotOptions;

import javax.security.auth.login.LoginException;
import java.nio.file.Path;

public class BotStartup {
    private static final Logger LOGGER = LoggerFactory.getLogger(BotStartup.class);

    public static void main(String[] args) throws LoginException {
        LOGGER.info("Parsing command line options");
        CommandLineBotOptions cmdLine = new CommandLineBotOptions(args);
        Path configFile = cmdLine.getConfigFile();
        LOGGER.info("Loading config file from {}", configFile.toAbsolutePath());
        BotFileConfig fileConfig = new BotFileConfig(configFile);

        BotOptions options = new MergedBotOptions(fileConfig, cmdLine);

        if (options.getToken().isEmpty()) {
            LOGGER.warn("No bot token found.");
            LOGGER.warn("Please specify a bot token using the config file or the CLI option.");
            return;
        }

        new JanitorBot(options);
    }

}
