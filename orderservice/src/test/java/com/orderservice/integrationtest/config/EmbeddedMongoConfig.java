package com.orderservice.integrationtest.config;

import de.flapdoodle.embed.mongo.Command;
import de.flapdoodle.embed.mongo.config.Defaults;
import de.flapdoodle.embed.mongo.config.SupportConfig;
import de.flapdoodle.embed.process.config.RuntimeConfig;
import de.flapdoodle.embed.process.config.store.HttpProxyFactory;
import de.flapdoodle.embed.process.runtime.CommandLinePostProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class EmbeddedMongoConfig {

    private static Logger log = LoggerFactory.getLogger(EmbeddedMongoConfig.class);

    @Bean
    public RuntimeConfig embeddedMongoRuntimeConfig() {
        final Command command = Command.MongoD;

        log.info("CONFIGURATION OF EMBEDDED MONGO WITH THE PROXY 10.6.62.150:80");

        final RuntimeConfig runtimeConfig = Defaults.runtimeConfigFor(command)
                .artifactStore(Defaults.extractedArtifactStoreFor(command)
                        .withDownloadConfig(Defaults.downloadConfigFor(command)
                                .proxyFactory(new HttpProxyFactory("10.6.62.150", 80))
                                .build()))
                .build();

        return runtimeConfig;
    }

}
