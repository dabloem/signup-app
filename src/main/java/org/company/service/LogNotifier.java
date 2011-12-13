package org.company.service;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.inject.Named;

import org.company.model.SignupRequest;

@Named
public class LogNotifier implements Notifier {
    
        @Inject
        private Logger log;

	@Override
	public void notify(SignupRequest signupRequest) {
            log.log(Level.INFO, "receiving {0}", signupRequest);
	}

}
