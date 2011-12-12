package org.company.service;

import java.util.List;

import org.company.model.SignupRequest;

public interface SignupRequestService {
	List<SignupRequest> getAllMembers();

	void register(SignupRequest m);

	SignupRequest get(String email);
	
	void approve(String email);
	
	void decline(String email);

}
