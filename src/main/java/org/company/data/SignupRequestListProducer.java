package org.company.data;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.event.Reception;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.company.model.SignupRequest;
import org.company.service.SignupRequestService;


@RequestScoped
public class SignupRequestListProducer {
	@Inject
	private SignupRequestService requestService;

	private List<SignupRequest> requests;

	// @Named provides access the return value via the EL variable name
	// "requests" in the UI (e.g.,
	// Facelets or JSP view)
	@Produces
	@Named
	public List<SignupRequest> getMembers() {
		return requests;
	}

	public void onMemberListChanged(
			@Observes(notifyObserver = Reception.IF_EXISTS) final SignupRequest request) {
		retrieveAllMembersOrderedByName();
	}

	@PostConstruct
	public void retrieveAllMembersOrderedByName() {
		this.requests = requestService.getAllUnconfirmedRequests();
	}
}
