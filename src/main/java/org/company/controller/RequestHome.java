package org.company.controller;

import java.io.IOException;
import java.util.logging.Logger;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
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

	public void logout() {
		log.info("logout....@");
		ExternalContext externalContext = FacesContext.getCurrentInstance()
				.getExternalContext();
		externalContext.invalidateSession();
		try {
			externalContext.redirect(externalContext.getRequestContextPath());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
