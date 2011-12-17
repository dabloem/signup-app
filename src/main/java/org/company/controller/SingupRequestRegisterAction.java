package org.company.controller;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.crypto.SecretKey;
import javax.ejb.Stateful;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.inject.Named;

import org.company.model.SignupRequest;
import org.company.service.SignupRequestService;

@Named("registerAction")
@ConversationScoped
// @Stateful
public class SingupRequestRegisterAction implements Serializable {

	// @Inject
	// private Logger log;

	/**
	 * 
	 */
	private static final long serialVersionUID = -7123511030910501473L;

	@Inject
	private SignupRequestService requestService;

	@Inject
	Conversation convesation;

	@Inject
	private Event<SignupRequest> requestEventSrc;

	private SignupRequest currentRequest;

	public void register() throws Exception {
		// log.info("Registering " + currentRequest.getName());
		requestService.register(currentRequest);
		requestEventSrc.fire(currentRequest);

		if (!convesation.isTransient()) {
			convesation.end();
		}

	}

	@PostConstruct
	public void initNewRequest() {
		if (convesation.isTransient()) {
			convesation.begin();
		}

		currentRequest = new SignupRequest();
	}

	public SignupRequest getCurrentRequest() {
		return currentRequest;
	}

	public void setCurrentRequest(SignupRequest currentRequest) {
		this.currentRequest = currentRequest;
	}

}
