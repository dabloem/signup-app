package org.company.service;

import javax.inject.Named;

import org.company.model.SignupRequest;

@Named
public class DefaultMailSender implements MailSender {

	@Override
	public void send(SignupRequest m) {
		// TODO Auto-generated method stub
		System.out.println("Sending mail placeholder.");
	}

}
