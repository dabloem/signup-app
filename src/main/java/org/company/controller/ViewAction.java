package org.company.controller;

import java.util.logging.Logger;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.company.model.SignupRequest;
import org.company.service.SignupRequestNotFoundException;
import org.company.service.SignupRequestService;

@Named("viewAction")
@RequestScoped
public class ViewAction {

	@Inject
	private Logger log;

	@Inject
	private SignupRequestService requestService;

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

	public void loadRequest() {
		log.info("call loadRequest...@" + this.requestId);
		this.currentRequest = requestService.get(this.requestId);
	}

}
