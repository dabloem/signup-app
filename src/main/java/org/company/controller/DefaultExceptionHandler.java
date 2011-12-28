package org.company.controller;

import java.util.logging.Logger;

import javax.faces.context.FacesContext;

import org.company.service.SignupRequestNotFoundException;
import org.jboss.solder.exception.control.CaughtException;
import org.jboss.solder.exception.control.Handles;
import org.jboss.solder.exception.control.HandlesExceptions;

@HandlesExceptions
public class DefaultExceptionHandler {

	public void handleSignupRequestNotFoundException(
			@Handles CaughtException<SignupRequestNotFoundException> event,
			Logger log) {
		log.info("Exception logged by seam-catch catcher: "
				+ event.getException().getMessage());
		FacesContext context = FacesContext.getCurrentInstance();
		log.info("context @"+context);
		log.info("external context @"+context.getExternalContext());
		context.getExternalContext().setResponseStatus(404);	
		context.responseComplete();
	}

}
