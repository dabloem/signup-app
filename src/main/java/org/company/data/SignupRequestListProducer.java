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

	public void onSignupRequestListChanged(
			@Observes(notifyObserver = Reception.IF_EXISTS) final SignupRequest request) {
		retrieveSignupRequests();
	}

	@PostConstruct
	public void retrieveSignupRequests() {
		this.unconfirmedRequests = requestService.getAllUnconfirmedRequests();
		log.info("unconfirmedRequests size@"+unconfirmedRequests.size());
		this.confirmedRequests = requestService.getAllConfirmedRequests();
		log.info("confirmedRequests size@"+confirmedRequests.size());
		this.approvedRequests = requestService.getAllApprovedRequests();
		log.info("approvedRequests size@"+approvedRequests.size());
		this.deniedRequests = requestService.getAllDeniedRequests();
		log.info("deniedRequests size@"+deniedRequests.size());
	}
}
