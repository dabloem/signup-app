package org.company.controller;

import java.util.logging.Logger;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.event.Reception;
import javax.inject.Inject;
import javax.inject.Named;

import org.company.model.SignupRequest;
import org.company.service.SignupRequestService;
import org.company.service.events.Approved;
import org.company.service.events.Confirmed;

@Named("requestHome")
@RequestScoped
public class RequestHome {

	@Inject
	private Logger log;

	@Inject
	private SignupRequestService requestService;


	public void confirm(String id) {
		log.info("confirm....@" + id);
		requestService.confirm(id);
	}

	public void approve(String id) {
		log.info("approve....@" + id);
		requestService.approve(id);
	}
	
	public void approveDenied(String id) {
		log.info("approve denied....@" + id);
		requestService.approveDenied(id);
	}

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


}
