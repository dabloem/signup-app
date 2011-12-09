package org.company.service;

import java.util.List;

import org.company.model.Member;

public interface MemberService {
	List<Member> getAllMembers();

	void register(Member m);

	Member get(String email);
	
	void approve(String email);
	
	void decline(String email);

}
