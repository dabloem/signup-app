package org.company.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Alternative;
import javax.inject.Named;

import org.company.model.Member;
import org.company.service.MemberService;

@Named
@ApplicationScoped
@Alternative
public class MockMemberService implements MemberService {

	private ConcurrentHashMap<String, Member> memberCache = new ConcurrentHashMap<String, Member>();

	@Override
	public List<Member> getAllMembers() {
		return new ArrayList<Member>(memberCache.values());
	}

	@Override
	public void register(Member m) {
		memberCache.putIfAbsent(m.getEmail(), m);
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
