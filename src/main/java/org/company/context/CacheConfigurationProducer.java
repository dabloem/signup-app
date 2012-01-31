package org.company.context;

import javax.enterprise.inject.Produces;

import org.company.context.qualifiers.ApprovedCache;
import org.company.context.qualifiers.ConfirmedCache;
import org.company.context.qualifiers.DeniedCache;
import org.company.context.qualifiers.UnconfirmedCache;
import org.infinispan.cdi.ConfigureCache;
import org.infinispan.configuration.cache.Configuration;
import org.infinispan.configuration.cache.ConfigurationBuilder;
import org.infinispan.eviction.EvictionStrategy;

/**
 * Produce cache configurations for different purpose.
 *
 * @author hantsy
 *
 */
public class CacheConfigurationProducer {

    @ConfigureCache("signup-unconfirmed-cache")
    @UnconfirmedCache
    @Produces
    public Configuration unconfirmedCacheConfiguration() {
        return new ConfigurationBuilder()
                .eviction()
                    .strategy(EvictionStrategy.LRU)
                    .maxEntries(10)
                .expiration()
                    .lifespan(24L * 60 * 60 * 1000)
                .loaders()
                    .shared(false)
                    .preload(true)
                    .passivation(false)
                .addFileCacheStore()
                     .location("signup-unconfirmed-cache")
                     .fetchPersistentState(true)
                     .purgeOnStartup(false)
                     .ignoreModifications(false)
                .build();
    }

    @ConfigureCache("signup-confirmed-cache")
    @ConfirmedCache
    @Produces
    public Configuration confirmedCacheConfiguration() {
    	return new ConfigurationBuilder()
                .eviction()
                    .strategy(EvictionStrategy.LRU)
                    .maxEntries(10)
                .expiration()
                    .lifespan(24L * 60 * 60 * 1000)
                .loaders()
                    .shared(false)
                    .preload(true)
                    .passivation(false)
                .addFileCacheStore()
                     .location("signup-confirmed-cache")
                     .fetchPersistentState(true)
                     .purgeOnStartup(false)
                     .ignoreModifications(false)
                .build();
    }

    @ConfigureCache("signup-approved-cache")
    @ApprovedCache
    @Produces
    public Configuration approvedCacheConfiguration() {
    	return new ConfigurationBuilder()
                .eviction()
                    .strategy(EvictionStrategy.LRU)
                    .maxEntries(10)
                .expiration()
                    .lifespan(24L * 60 * 60 * 1000)
                .loaders()
                    .shared(false)
                    .preload(true)
                    .passivation(false)
                .addFileCacheStore()
                     .location("signup-approved-cache")
                     .fetchPersistentState(true)
                     .purgeOnStartup(false)
                     .ignoreModifications(false)
                .build();
    }

    @ConfigureCache("signup-denied-cache")
    @DeniedCache
    @Produces
    public Configuration deniedCacheConfiguration() {
    	return new ConfigurationBuilder()
                .eviction()
                    .strategy(EvictionStrategy.LRU)
                    .maxEntries(10)
                .expiration()
                    .lifespan(24L * 60 * 60 * 1000)
                .loaders()
                    .shared(false)
                    .preload(true)
                    .passivation(false)
                .addFileCacheStore()
                     .location("signup-denied-cache")
                     .fetchPersistentState(true)
                     .purgeOnStartup(false)
                     .ignoreModifications(false)
                .build();
    }
}
