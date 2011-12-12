package org.company.service;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.inject.Named;

import org.company.model.SignupRequest;
import org.company.model.Status;
import org.infinispan.Cache;

@Named
@ApplicationScoped
public class InfinispanMemberService implements SignupRequestService {

	@Inject
	private Cache<String, SignupRequest> memberCache;
	
	@Inject
	Event<SignupRequest> registerationEventSrc;

	@Override
	public List<SignupRequest> getAllMembers() {
		return new ArrayList<SignupRequest>(memberCache.values());
	}

	@Override
	public void register(SignupRequest m) {
		memberCache.putIfAbsent(m.getEmail(), m, 24, TimeUnit.HOURS);
		registerationEventSrc.fire(m);
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
		m.setStatus(Status.DECLINED);
		memberCache.put(email, m);
	}
}
