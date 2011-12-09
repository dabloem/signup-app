package org.company.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.company.model.Member;
import org.infinispan.Cache;

@Named
@ApplicationScoped
public class InfinispanMemberService implements MemberService {

	@Inject
	private Cache<String, Member> memberCache;

	@Override
	public List<Member> getAllMembers() {
		return new ArrayList<Member>(memberCache.values());
	}

	@Override
	public void register(Member m) {
		memberCache.putIfAbsent(m.getEmail(), m, 24, TimeUnit.HOURS);
	}

	@Override
	public Member get(String email) {
		return memberCache.get(email);

	}

	@Override
	public void approve(String email) {
		Member m = get(email);
		m.setActive(true);
		memberCache.put(email, m);

	}

	@Override
	public void decline(String email) {
		Member m = get(email);
		m.setActive(false);
		memberCache.put(email, m);
	}
}
