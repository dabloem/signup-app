package org.company.controller;

import java.util.logging.Logger;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.company.service.SignupRequestService;

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

	public void deny(String id) {
		log.info("deny....@" + id);
		requestService.deny(id);
	}

}
