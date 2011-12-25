package org.company.controller;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public final class FacesUtil {
	
	public FacesUtil(){
		throw new RuntimeException("FacesUtil can be instantiated");
	}

	public static void info(String message) {
		FacesContext context = FacesContext.getCurrentInstance();
		//context.getExternalContext().getFlash().setKeepMessages(true);
		context.addMessage(null, new FacesMessage(message, message));
	}

}
