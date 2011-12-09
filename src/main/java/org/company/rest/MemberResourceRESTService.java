package org.company.rest;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.company.model.Member;
import org.company.service.MemberService;

/**
 * JAX-RS Example
 * 
 * This class produces a RESTful service to read the contents of the members
 * table.
 */
@Path("/members")
@RequestScoped
public class MemberResourceRESTService {
	@Inject
	private MemberService memberService;

	@GET
	@Produces("text/xml")
	public List<Member> listAllMembers() {
		List<Member> results = memberService.getAllMembers();
		return results;
	}

	@GET
	@Path("/{id}")
	@Produces("text/xml")
	public Member lookupMemberById(@PathParam("id") String email) {
		return memberService.get(email);
	}
}
