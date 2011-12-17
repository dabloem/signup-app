package org.company.test;

import java.util.logging.Logger;

import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.BeanManager;
import javax.inject.Inject;

import org.company.config.UnconfirmedCache;
import org.company.model.SignupRequest;
import org.infinispan.Cache;

public class DataSeeder {
	
	@Inject Logger log;
	
	@Inject 
	@UnconfirmedCache
	Cache<String, SignupRequest> unconfirmedCache;
	
	
	public void initialize(@Observes BeanManager  beanManager){
		log.info("...initialize data...");
		
		
	}

}
