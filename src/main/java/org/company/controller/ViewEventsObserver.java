package org.company.controller;

import java.util.logging.Logger;

import javax.enterprise.event.Observes;
import javax.enterprise.event.Reception;
import javax.inject.Inject;

import org.company.model.SignupRequest;
import org.company.service.events.Approved;
import org.company.service.events.Confirmed;
import org.company.service.events.Denied;

public class ViewEventsObserver {
	@Inject
	Logger log;

	public void onConfirmed(
			@Observes(notifyObserver = Reception.IF_EXISTS) @Confirmed SignupRequest request) {
		log.info("confirmed event was triggered.");
		FacesUtil.info("Signup Request '" + request.getId()
				+ "' was confirmed.");

	}

	public void onApproved(
			@Observes(notifyObserver = Reception.IF_EXISTS) @Approved SignupRequest request) {
		log.info("approved event was triggered.");
		FacesUtil
				.info("Signup Request '" + request.getId() + "' was approved.");

	}
	

	public void onDenied(@Observes(notifyObserver=Reception.IF_EXISTS) @Denied SignupRequest request) {
		log.info("denied event was triggered.");
		FacesUtil.info("Signup Request '"+request.getId()+"' was denied.");
		
	}
	
	
	public void onLoggedIn(@Observes(notifyObserver=Reception.IF_EXISTS) @LoggedIn String username) {
		log.info("loggedin event was triggered.");
		FacesUtil.info("Welcome back, " + username);
		
	}
}
