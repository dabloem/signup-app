package org.company.controller;

import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.Stateful;
import javax.enterprise.event.Event;
import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

import org.company.model.SignupRequest;
import org.company.service.SignupRequestService;


// The @Stateful annotation eliminates the need for manual transaction demarcation
//@Stateful
// The @Model stereotype is a convenience mechanism to make this a
// request-scoped bean that has an
// EL name
// Read more about the @Model stereotype in this FAQ:
// http://sfwk.org/Documentation/WhatIsThePurposeOfTheModelAnnotation
@Model
public class SingupRequestHome {

	
	@Inject
	private Logger log;

	@Inject
	private SignupRequestService requestService;

	@Inject
	private Event<SignupRequest> requestEventSrc;

	private SignupRequest newMember;

	@Produces
	@Named
	public SignupRequest getNewMember() {
		return newMember;
	}

	public void register() throws Exception {
		log.info("Registering " + newMember.getName());
		requestService.register(newMember);
		requestEventSrc.fire(newMember);
		initNewMember();
	}

	@PostConstruct
	public void initNewMember() {
		newMember = new SignupRequest();
	}
}
