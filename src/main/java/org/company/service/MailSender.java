package org.company.service;

import org.company.model.SignupRequest;

public interface MailSender {

	public void send(SignupRequest m);
}
