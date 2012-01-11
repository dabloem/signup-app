package org.company.test.service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.util.Date;

import javax.inject.Inject;

import org.company.model.SignupRequest;
import org.company.model.Status;
import org.company.service.Predicate;
import org.company.service.SignupRequestNotFoundException;
import org.company.service.SignupRequestService;
import org.company.service.events.Confirmed;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class MockSignupRequestServiceTest {

	@Deployment
	public static JavaArchive createTestArchive() {
		return ShrinkWrap.create(JavaArchive.class, "test.jar")
				.addClass(SignupRequest.class).addClass(Status.class)
				.addClass(SignupRequestService.class)
				.addClass(MockSignupRequestService.class)
				.addClass(Predicate.class)
				.addClass(SignupRequestNotFoundException.class)
				.addPackages(false, Confirmed.class.getPackage())
				.addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
	}

	@Inject
	SignupRequestService service;

	@Test
	public void testCdiBootstrap() {
		assertNotNull(service);
	}

	@Test(expected = SignupRequestNotFoundException.class)
	public void testGetRequestException() {
		service.get("1234324");
	}
	
	@Test(expected = SignupRequestNotFoundException.class)
	public void testConfirmException() {
		service.confirm("1234324");
	}
	
	@Test(expected = SignupRequestNotFoundException.class)
	public void testApproveException() {
		service.approve("1234324");
	}
	
	@Test(expected = SignupRequestNotFoundException.class)
	public void testDenyException() {
		service.deny("1234324");
	}

	@Test(expected = SignupRequestNotFoundException.class)
	public void testApproveDeniedException() {
		service.approveDenied("1234324");
	}
	
	/**
	 * regiter->confirm->approve
	 */
	@Test
	public void testSignupService() {
		SignupRequest request = new SignupRequest();
		request.setComment("Comment");
		request.setCompanyName("CompanyName");
		request.setCreatedOn(new Date());
		request.setEmail("xxxx@abc.com");
		request.setFirstName("FirstName");
		request.setLastName("Last Name");
		request.setHttpRefer("refer");

		service.register(request);
		assertFalse(service.getAllUnconfirmedRequests().isEmpty());

		SignupRequest _unconfirmedRequest = service.getAllUnconfirmedRequests()
				.get(0);

		assertNotNull(_unconfirmedRequest);
		System.out.println("unconfirmed id @" + _unconfirmedRequest.getId());

		service.confirm(_unconfirmedRequest.getId());

		SignupRequest _confirmedRequest = service.getAllConfirmedRequests()
				.get(0);

		assertNotNull(_confirmedRequest);

		service.approve(_confirmedRequest.getId());

		SignupRequest _approvedRequest = service.getAllApprovedRequests()
				.get(0);

		assertNotNull(_approvedRequest);

	}
	
	/**
	 * Another path: register->confirm->deny->approveDeined
	 */
	@Test
	public void testSignupService2() {
		SignupRequest request = new SignupRequest();
		request.setComment("Comment");
		request.setCompanyName("CompanyName");
		request.setCreatedOn(new Date());
		request.setEmail("xxxx@abc.com");
		request.setFirstName("FirstName");
		request.setLastName("Last Name");
		request.setHttpRefer("refer");

		service.register(request);
		assertFalse(service.getAllUnconfirmedRequests().isEmpty());

		SignupRequest _unconfirmedRequest = service.getAllUnconfirmedRequests()
				.get(0);

		assertNotNull(_unconfirmedRequest);
		System.out.println("unconfirmed id @" + _unconfirmedRequest.getId());

		service.confirm(_unconfirmedRequest.getId());

		SignupRequest _confirmedRequest = service.getAllConfirmedRequests()
				.get(0);

		assertNotNull(_confirmedRequest);
		
		service.deny(_confirmedRequest.getId());

		SignupRequest _denidRequest = service.getAllDeniedRequests()
				.get(0);

		assertNotNull(_denidRequest);
		

		service.approveDenied(_denidRequest.getId());

		SignupRequest _approvedRequest = service.getAllApprovedRequests()
				.get(0);

		assertNotNull(_approvedRequest);

	}


}
