package org.company.service;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.Any;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;

import org.company.model.SignupRequest;

@ApplicationScoped
public class EventsObserver {
	
	@Inject
	@Any 
	Instance<Notifier> notifiers;
	
	public void onRegister(@Observes SignupRequest signupRequest){
            for (Notifier notifier : notifiers){
		notifier.notify(signupRequest);
            }
	}

}
