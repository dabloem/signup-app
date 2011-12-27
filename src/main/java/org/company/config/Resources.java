package org.company.config;

import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;

import org.infinispan.cdi.ConfigureCache;
import org.infinispan.config.Configuration;
import org.infinispan.eviction.EvictionStrategy;

/**
 * This class uses CDI to alias Java EE resources, such as the persistence
 * context, to CDI beans
 * 
 * <p>
 * Example injection on a managed bean field:
 * </p>
 * 
 * 
 */
public class Resources {

//	@Produces
//	@ApplicationScoped
//	@Resource(mappedName = "java:jboss/infinispan/signup-unconfirmed-cache")
//	@UnconfirmedCache
//	private EmbeddedCacheManager unconfirmedCacheManager;
//	
//	
//	@Produces
//	@ApplicationScoped
//	@Resource(mappedName = "java:jboss/infinispan/signup-confirmed-cache")
//	@ConfirmedCache
//	private EmbeddedCacheManager confirmedCacheManager;
//	
//	
//	@Produces
//	@ApplicationScoped
//	@Resource(mappedName = "java:jboss/infinispan/signup-approved-cache")
//	@ApprovedCache
//	private EmbeddedCacheManager approvedCacheManager;
//	
//	
//	@Produces
//	@ApplicationScoped
//	@Resource(mappedName = "java:jboss/infinispan/signup-denied-cache")
//	@DeniedCache
//	private EmbeddedCacheManager deniedCacheManager;

	@ConfigureCache("signup-unconfirmed-cache") // this is the cache name.
	@UnconfirmedCache // this is the cache qualifier.
    @Produces
    @ApplicationScoped
    public Configuration unconfirmedCacheConfiguration() {
        return new Configuration().fluent().eviction().strategy(EvictionStrategy.FIFO).maxEntries(10)
                    .expiration().lifespan(24L*60*60*1000).build();
    }
	
	

	@ConfigureCache("signup-confirmed-cache") // this is the cache name.
	@ConfirmedCache // this is the cache qualifier.
    @Produces
    @ApplicationScoped
    public Configuration confirmedCacheConfiguration() {
        return new Configuration().fluent().eviction().strategy(EvictionStrategy.FIFO).maxEntries(10)
        		.expiration().lifespan(24L*60*60*1000).build();
    }
 

	@ConfigureCache("signup-approved-cache") // this is the cache name.
	@ApprovedCache // this is the cache qualifier.
    @Produces
    @ApplicationScoped
    public Configuration approvedCacheConfiguration() {
        return new Configuration().fluent().eviction().strategy(EvictionStrategy.FIFO).maxEntries(10)
        		.expiration().lifespan(24L*60*60*1000).build();
    }
	
	
	@ConfigureCache("signup-denied-cache") // this is the cache name.
	@DeniedCache // this is the cache qualifier.
    @Produces
    @ApplicationScoped
    public Configuration deniedCacheConfiguration() {
        return new Configuration().fluent().eviction().strategy(EvictionStrategy.FIFO).maxEntries(10)
        		.expiration().lifespan(24L*60*60*1000).build();
    }
	
	@Produces
	public Logger produceLog(InjectionPoint injectionPoint) {
		return Logger.getLogger(injectionPoint.getMember().getDeclaringClass()
				.getName());
	}
}
