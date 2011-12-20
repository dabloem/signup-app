package org.company.controller;

import java.util.logging.Logger;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.company.service.SignupRequestNotFoundException;
import org.company.service.SignupRequestService;

@Named("requestHome")
@RequestScoped
public class RequestHome {

	@Inject
	private Logger log;

	@Inject
	private SignupRequestService requestService;
	
	public void confirm(String id) {
		log.info("confirm....@"+id);
		try {
			requestService.confirm(id);
		} catch (SignupRequestNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void approve(String id) {
		log.info("approve....@"+id);
		try {
			requestService.approve(id);
		} catch (SignupRequestNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void deny(String id) {
		log.info("deny....@"+id);
		try {
			requestService.deny(id);
		} catch (SignupRequestNotFoundException e) {			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
