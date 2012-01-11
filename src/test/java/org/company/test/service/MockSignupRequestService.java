package org.company.test.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.inject.Named;

import org.company.model.SignupRequest;
import org.company.model.Status;
import org.company.service.Predicate;
import org.company.service.SignupRequestService;
import org.company.service.events.Approved;
import org.company.service.events.Confirmed;
import org.company.service.events.Denied;
import org.company.service.events.Registered;

@Named
@ApplicationScoped
public class MockSignupRequestService implements SignupRequestService {

	private ConcurrentHashMap<String, SignupRequest> unconfirmedCache = new ConcurrentHashMap<String, SignupRequest>();

	private ConcurrentHashMap<String, SignupRequest> confirmedCache = new ConcurrentHashMap<String, SignupRequest>();

	private ConcurrentHashMap<String, SignupRequest> approvedCache = new ConcurrentHashMap<String, SignupRequest>();

	private ConcurrentHashMap<String, SignupRequest> deniedCache = new ConcurrentHashMap<String, SignupRequest>();

	@Inject
	@Registered
	Event<SignupRequest> registerEventSrc;

	@Inject
	@Confirmed
	private Event<SignupRequest> confirmEventSrc;

	@Inject
	@Approved
	private Event<SignupRequest> approveEventSrc;

	@Inject
	@Denied
	private Event<SignupRequest> denyEventSrc;

	@Override
	public List<SignupRequest> getAllUnconfirmedRequests() {
		return new ArrayList<SignupRequest>(unconfirmedCache.values());
	}

	@Override
	public List<SignupRequest> getAllConfirmedRequests() {
		return new ArrayList<SignupRequest>(confirmedCache.values());
	}

	@Override
	public List<SignupRequest> getAllApprovedRequests() {
		return new ArrayList<SignupRequest>(approvedCache.values());
	}

	@Override
	public List<SignupRequest> getAllDeniedRequests() {
		return new ArrayList<SignupRequest>(deniedCache.values());
	}

	@Override
	public void register(SignupRequest m) {
		String uuid = UUID.randomUUID().toString();
		// remove the char '-'
		uuid = uuid.replace("-", "");

		m.setId(uuid);
		m.setStatus(Status.UNCONFIRMED);
		m.setCreatedOn(new Date());
		unconfirmedCache.put(m.getId(), m);
		registerEventSrc.fire(m);
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

		Predicate.nonNull(_m);

		return _m;
	}

	@Override
	public void confirm(String id) {
		SignupRequest m = (SignupRequest) unconfirmedCache.get(id);
		Predicate.nonNull(m);

		unconfirmedCache.remove(id);
		m.setStatus(Status.CONFIRMED);
		confirmedCache.put(id, m);
		confirmEventSrc.fire(m);
	}

	@Override
	public void approve(String id) {
		SignupRequest m = confirmedCache.get(id);
		Predicate.nonNull(m);

		confirmedCache.remove(id);
		m.setStatus(Status.APPROVED);
		approvedCache.put(id, m);
		approveEventSrc.fire(m);
	}

	@Override
	public void deny(String id) {
		SignupRequest m = confirmedCache.get(id);
		Predicate.nonNull(m);

		confirmedCache.remove(id);
		m.setStatus(Status.DENIED);
		deniedCache.put(id, m);
		denyEventSrc.fire(m);

	}

	@Override
	public void approveDenied(String id) {
		SignupRequest m = deniedCache.get(id);
		Predicate.nonNull(m);

		deniedCache.remove(id);
		m.setStatus(Status.APPROVED);
		approvedCache.put(id, m);
		approveEventSrc.fire(m);
	}
}
