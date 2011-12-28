package org.company.controller;

import java.util.Iterator;

import javax.faces.FacesException;
import javax.faces.application.NavigationHandler;
import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerWrapper;
import javax.faces.context.FacesContext;
import javax.faces.event.ExceptionQueuedEvent;
import javax.faces.event.ExceptionQueuedEventContext;

import org.company.service.SignupRequestNotFoundException;

public class DefaultExceptionHandler extends ExceptionHandlerWrapper {

	private ExceptionHandler wrapped;

	public DefaultExceptionHandler(ExceptionHandler wrapped) {
		this.wrapped = wrapped;
	}

	@Override
	public ExceptionHandler getWrapped() {
		return this.wrapped;
	}

	@Override
	public void handle() throws FacesException {
		Iterator<ExceptionQueuedEvent> events = getUnhandledExceptionQueuedEvents()
				.iterator();

		while (events.hasNext()) {
			ExceptionQueuedEvent event = events.next();
			ExceptionQueuedEventContext context = (ExceptionQueuedEventContext) event
					.getSource();
			Throwable t = context.getException();
			if (t instanceof SignupRequestNotFoundException) {
				try {
					handleSignupRequestNotFoundException((SignupRequestNotFoundException) t);
				} finally {
					events.remove();
				}
			}

			getWrapped().handle();
		}

	}

	private void handleSignupRequestNotFoundException(
			SignupRequestNotFoundException vee) {
		FacesContext context = FacesContext.getCurrentInstance();
		context.getExternalContext().setResponseStatus(404);
		context.responseComplete();
	}

}
