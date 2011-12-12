package org.company.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Alternative;
import javax.inject.Named;

import org.company.model.SignupRequest;
import org.company.model.Status;
import org.company.service.SignupRequestService;


@Named
@ApplicationScoped
@Alternative
public class MockSignupRequestService implements SignupRequestService {

	private ConcurrentHashMap<String, SignupRequest> memberCache = new ConcurrentHashMap<String, SignupRequest>();

	@Override
	public List<SignupRequest> getAllMembers() {
		return new ArrayList<SignupRequest>(memberCache.values());
	}

	@Override
	public void register(SignupRequest m) {
		memberCache.putIfAbsent(m.getEmail(), m);
	}

	@Override
	public SignupRequest get(String email) {
		return memberCache.get(email);

	}

	@Override
	public void approve(String email) {
		SignupRequest m = get(email);
		m.setStatus(Status.APPROVED);
		memberCache.put(email, m);

	}

	@Override
	public void decline(String email) {
		SignupRequest m = get(email);
		m.setStatus(Status.DENIED);
		memberCache.put(email, m);
	}
}
