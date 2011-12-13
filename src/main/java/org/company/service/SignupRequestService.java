package org.company.service;

import java.util.List;

import org.company.model.SignupRequest;

public interface SignupRequestService {
	public abstract List<SignupRequest> getAllRequests();

	public abstract void register(SignupRequest m);

	public abstract SignupRequest get(String id);

	public abstract void approve(String id);

	public abstract void decline(String id);

	public abstract void confirm(String id);

}
