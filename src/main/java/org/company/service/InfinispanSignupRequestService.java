package org.company.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.inject.Named;

import org.company.config.ApprovedCache;
import org.company.config.ConfirmedCache;
import org.company.config.DeniedCache;
import org.company.config.UnconfirmedCache;
import org.company.model.SignupRequest;
import org.company.model.Status;
import org.infinispan.Cache;

@Named
@ApplicationScoped
public class InfinispanSignupRequestService implements SignupRequestService {

	@Inject
	@UnconfirmedCache
	private Cache<String, SignupRequest> unconfirmedCache;

	@Inject
	@ConfirmedCache
	private Cache<String, SignupRequest> confirmedCache;

	@Inject
	@ApprovedCache
	private Cache<String, SignupRequest> approvedCache;

	@Inject
	@DeniedCache
	private Cache<String, SignupRequest> deniedCache;

	@Inject
	Event<SignupRequest> registerationEventSrc;

	@Override
	public List<SignupRequest> getAllRequests() {
		return new ArrayList<SignupRequest>(unconfirmedCache.values());
	}

	@Override
	public void register(SignupRequest m) {
		m.setId(UUID.randomUUID().toString());
		m.setStatus(Status.UNCONFIRMED);
		unconfirmedCache.putIfAbsent(m.getId(), m, 24, TimeUnit.HOURS);
		registerationEventSrc.fire(m);
	}

	@Override
	public SignupRequest get(String id) {
		SignupRequest _m = unconfirmedCache.get(id);
		if (_m == null) {
			_m = confirmedCache.get(id);
		}

		if (_m == null) {
			_m = approvedCache.get(id);
		}

		if (_m == null) {
			_m = deniedCache.get(id);
		}

		return _m;
	}

	@Override
	public void confirm(String id) {
		SignupRequest m = unconfirmedCache.get(id);
		m.setStatus(Status.CONFIRMED);
		confirmedCache.put(id, m);
	}

	@Override
	public void approve(String id) {
		SignupRequest m = confirmedCache.get(id);
		m.setStatus(Status.APPROVED);
		approvedCache.put(id, m);
	}

	@Override
	public void decline(String id) {
		SignupRequest m = confirmedCache.get(id);
		m.setStatus(Status.DENIED);
		deniedCache.put(id, m);
	}
}
