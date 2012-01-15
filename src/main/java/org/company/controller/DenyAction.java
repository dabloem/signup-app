package org.company.controller;

import java.io.Serializable;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.enterprise.event.Reception;
import javax.inject.Inject;
import javax.inject.Named;

import org.company.model.SignupRequest;
import org.company.service.SignupRequestService;
import org.company.service.events.Denied;

@Named("denyAction")
@ConversationScoped
public class DenyAction implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2854429048979793667L;

	@Inject
	transient private Logger log;

	@Inject
	private SignupRequestService requestService;

	@Inject
	Conversation convesation;

	private SignupRequest currentRequest;

	private String requestId;

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public SignupRequest getCurrentRequest() {
		return currentRequest;
	}

	public void setCurrentRequest(SignupRequest currentRequest) {
		this.currentRequest = currentRequest;
	}

	@PostConstruct
	public void init() {
		log.info("call init...@");
		if (convesation.isTransient()) {
			convesation.begin();
		}
	}

	public void loadRequest() {
		log.info("call loadRequest...@" + this.requestId);
		this.currentRequest = requestService.get(this.requestId);
	}

	public String deny() {
		log.info("deny....@");
		requestService.deny(this.currentRequest.getId());
		if (!convesation.isTransient()) {
			convesation.end();
		}
		return "/admin/denied?faces-redirect=true";
	}

	public void onDenied(
			@Observes(notifyObserver = Reception.IF_EXISTS) @Denied SignupRequest request) {
		log.info("denied event was triggered.");
		FacesUtil.info("Signup Request '" + request.getId() + "' was denied.");

	}

	public String cancel() {
		log.info("cancel....@");
		if (!convesation.isTransient()) {
			convesation.end();
		}
		return "/admin/confirmed?faces-redirect=true";
	}

}
