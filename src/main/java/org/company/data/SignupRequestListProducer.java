package org.company.data;

import java.util.List;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.event.Reception;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

import org.company.model.SignupRequest;
import org.company.service.SignupRequestService;
import org.company.service.events.Approved;
import org.company.service.events.Confirmed;
import org.company.service.events.Denied;
import org.company.service.events.Registered;

@ApplicationScoped
public class SignupRequestListProducer {
	@Inject
	Logger log;

	@Inject
	private SignupRequestService requestService;

	private List<SignupRequest> unconfirmedRequests;
	private List<SignupRequest> confirmedRequests;
	private List<SignupRequest> approvedRequests;
	private List<SignupRequest> deniedRequests;

	@Produces
	@Named("unconfirmedRequests")
	public List<SignupRequest> getAllUnconfirmedRequests() {
		return unconfirmedRequests;
	}

	@Produces
	@Named("confirmedRequests")
	public List<SignupRequest> getAllConfirmedRequests() {
		return confirmedRequests;
	}

	@Produces
	@Named("approvedRequests")
	public List<SignupRequest> getAllApprovedRequests() {
		return approvedRequests;
	}

	@Produces
	@Named("deniedRequests")
	public List<SignupRequest> getAllDeniedRequests() {
		return deniedRequests;
	}

	public void onRegistered(
			@Observes(notifyObserver = Reception.IF_EXISTS) @Registered final SignupRequest request) {
		this.unconfirmedRequests = requestService.getAllUnconfirmedRequests();
	}

	public void onConfirmed(
			@Observes(notifyObserver = Reception.IF_EXISTS) @Confirmed final SignupRequest request) {
		this.unconfirmedRequests = requestService.getAllUnconfirmedRequests();
		this.confirmedRequests = requestService.getAllConfirmedRequests();
	}

	public void onApproved(
			@Observes(notifyObserver = Reception.IF_EXISTS) @Approved final SignupRequest request) {
		this.confirmedRequests = requestService.getAllConfirmedRequests();
		this.approvedRequests = requestService.getAllApprovedRequests();
		this.deniedRequests = requestService.getAllDeniedRequests();
	}

	public void onDenied(
			@Observes(notifyObserver = Reception.IF_EXISTS) @Denied final SignupRequest request) {
		this.confirmedRequests = requestService.getAllConfirmedRequests();
		this.deniedRequests = requestService.getAllDeniedRequests();
	}

	@PostConstruct
	public void retrieveSignupRequests() {
		this.unconfirmedRequests = requestService.getAllUnconfirmedRequests();
		log.info("unconfirmedRequests size@" + unconfirmedRequests.size());
		this.confirmedRequests = requestService.getAllConfirmedRequests();
		log.info("confirmedRequests size@" + confirmedRequests.size());
		this.approvedRequests = requestService.getAllApprovedRequests();
		log.info("approvedRequests size@" + approvedRequests.size());
		this.deniedRequests = requestService.getAllDeniedRequests();
		log.info("deniedRequests size@" + deniedRequests.size());
	}
}
