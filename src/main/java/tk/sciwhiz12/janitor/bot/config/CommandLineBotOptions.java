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

package tk.sciwhiz12.janitor.bot.config;

import com.google.common.primitives.Longs;
import it.unimi.dsi.fastutil.longs.LongArraySet;
import it.unimi.dsi.fastutil.longs.LongSet;
import joptsimple.ArgumentAcceptingOptionSpec;
import joptsimple.OptionParser;
import joptsimple.OptionSet;
import joptsimple.util.PathConverter;
import joptsimple.util.PathProperties;

import javax.annotation.Nullable;
import java.nio.file.Path;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.List.of;

public class CommandLineBotOptions implements BotOptions {
    @Nullable
    private final String token;
    private final LongSet owners;
    private final Path configFile;

    public CommandLineBotOptions(String[] args) {
        OptionParser parser = new OptionParser();
        ArgumentAcceptingOptionSpec<String> token = parser.acceptsAll(of("token", "t"), "Discord API token")
                .withRequiredArg();
        ArgumentAcceptingOptionSpec<String> owners = parser.acceptsAll(of("owner", "o"), "Snowflake ID(s) of bot owners")
                .withRequiredArg().withValuesSeparatedBy(',');
        ArgumentAcceptingOptionSpec<Path> configFile = parser.acceptsAll(of("config", "config-file", "c"), "Bot config file")
                .withRequiredArg().withValuesConvertedBy(new PathConverter(PathProperties.FILE_EXISTING, PathProperties.WRITABLE))
                .defaultsTo(Path.of("bot-config.toml"));

        OptionSet parsed = parser.parse(args);

        this.token = parsed.valueOf(token);

        //noinspection ConstantConditions
        this.owners = new LongArraySet(parsed.valuesOf(owners).stream()
                .filter(str -> Longs.tryParse(str) != null)
                .map(Longs::tryParse)
                .collect(Collectors.toUnmodifiableList()));

        this.configFile = parsed.valueOf(configFile);
    }

    @Override
    public Optional<String> getToken() {
        return Optional.ofNullable(this.token);
    }

    @Override
    public Optional<LongSet> getBotOwners() {
        return this.owners.isEmpty() ? Optional.empty() : Optional.of(this.owners);
    }

    public Path getConfigFile() {
        return this.configFile;
    }
}
