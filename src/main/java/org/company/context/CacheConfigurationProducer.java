package org.company.context;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import org.company.context.qulifiers.ApprovedCache;
import org.company.context.qulifiers.ConfirmedCache;
import org.company.context.qulifiers.DeniedCache;
import org.company.context.qulifiers.UnconfirmedCache;
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

    // @Produces
    // @ApplicationScoped
    // @Resource(mappedName = "java:jboss/infinispan/signup-unconfirmed-cache")
    // @UnconfirmedCache
    // private EmbeddedCacheManager unconfirmedCacheManager;
    //
    //
    // @Produces
    // @ApplicationScoped
    // @Resource(mappedName = "java:jboss/infinispan/signup-confirmed-cache")
    // @ConfirmedCache
    // private EmbeddedCacheManager confirmedCacheManager;
    //
    //
    // @Produces
    // @ApplicationScoped
    // @Resource(mappedName = "java:jboss/infinispan/signup-approved-cache")
    // @ApprovedCache
    // private EmbeddedCacheManager approvedCacheManager;
    //
    //
    // @Produces
    // @ApplicationScoped
    // @Resource(mappedName = "java:jboss/infinispan/signup-denied-cache")
    // @DeniedCache
    // private EmbeddedCacheManager deniedCacheManager;
    @ConfigureCache("signup-unconfirmed-cache")
    @UnconfirmedCache
    @Produces
   // @ApplicationScoped
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
    //@ApplicationScoped
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
    //@ApplicationScoped
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
   // @ApplicationScoped
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
