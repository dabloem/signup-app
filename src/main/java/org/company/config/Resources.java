package org.company.config;

import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;

import org.infinispan.cdi.ConfigureCache;
import org.infinispan.config.Configuration;
import org.infinispan.eviction.EvictionStrategy;
import org.infinispan.loaders.file.FileCacheStoreConfig;

/**
 * 
 * @author hantsy
 * 
 */
public class Resources {

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
	@ApplicationScoped
	public Configuration unconfirmedCacheConfiguration() {
		return new Configuration()
				.fluent()
				.eviction()
				.strategy(EvictionStrategy.FIFO)
				.maxEntries(10)
				.expiration()
				.lifespan(24L * 60 * 60 * 1000)
				.loaders()
				.shared(false)
				.preload(true)
				.passivation(false)
				.addCacheLoader(
						new FileCacheStoreConfig()
								.location("signup-unconfirmed-cache")
								.fetchPersistentState(true)
								.purgeOnStartup(false)
								.ignoreModifications(false)).build();
	}

	@ConfigureCache("signup-confirmed-cache")
	@ConfirmedCache
	@Produces
	@ApplicationScoped
	public Configuration confirmedCacheConfiguration() {
		return new Configuration()
				.fluent()
				.eviction()
				.strategy(EvictionStrategy.FIFO)
				.maxEntries(10)
				.expiration()
				.lifespan(24L * 60 * 60 * 1000)
				.loaders()
				.shared(false)
				.preload(true)
				.passivation(false)
				.addCacheLoader(
						new FileCacheStoreConfig()
								.location("signup-confirmed-cache")
								.fetchPersistentState(true)
								.purgeOnStartup(false)
								.ignoreModifications(false)).build();

	}

	@ConfigureCache("signup-approved-cache")
	@ApprovedCache
	@Produces
	@ApplicationScoped
	public Configuration approvedCacheConfiguration() {
		return new Configuration()
				.fluent()
				.eviction()
				.strategy(EvictionStrategy.FIFO)
				.maxEntries(10)
				.expiration()
				.lifespan(24L * 60 * 60 * 1000)
				.loaders()
				.shared(false)
				.preload(true)
				.passivation(false)
				.addCacheLoader(
						new FileCacheStoreConfig()
								.location("signup-approved-cache")
								.fetchPersistentState(true)
								.purgeOnStartup(false)
								.ignoreModifications(false)).build();

	}

	@ConfigureCache("signup-denied-cache")
	@DeniedCache
	@Produces
	@ApplicationScoped
	public Configuration deniedCacheConfiguration() {
		return new Configuration()
				.fluent()
				.eviction()
				.strategy(EvictionStrategy.FIFO)
				.maxEntries(10)
				.expiration()
				.lifespan(24L * 60 * 60 * 1000)
				.loaders()
				.shared(false)
				.preload(true)
				.passivation(false)
				.addCacheLoader(
						new FileCacheStoreConfig()
								.location("signup-denied-cache")
								.fetchPersistentState(true)
								.purgeOnStartup(false)
								.ignoreModifications(false)).build();

	}

	@Produces
	public Logger produceLog(InjectionPoint injectionPoint) {
		return Logger.getLogger(injectionPoint.getMember().getDeclaringClass()
				.getName());
	}
}
