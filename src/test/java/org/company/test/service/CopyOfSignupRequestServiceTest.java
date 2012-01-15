package org.company.test.service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.naming.Context;
import javax.naming.NamingException;

import org.company.context.SignupRequestListProducer;
import org.company.context.qulifiers.ApprovedCache;
import org.company.model.SignupRequest;
import org.company.service.InfinispanSignupRequestService;
import org.company.service.SignupRequestNotFoundException;
import org.company.service.SignupRequestService;
import org.company.service.events.Approved;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.prototyping.context.api.ArquillianContext;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.DependencyResolvers;
import org.jboss.shrinkwrap.resolver.api.maven.MavenDependencyResolver;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class CopyOfSignupRequestServiceTest {
	private static final String WEBAPP_SRC = "src/main/webapp";
	private static final String WEBINF_SRC = "src/main/webapp/WEB-INF";

	@Deployment
	public static JavaArchive createTestArchive() {
		MavenDependencyResolver resolver = DependencyResolvers.use(
				MavenDependencyResolver.class).loadMetadataFromPom("pom.xml");

		JavaArchive jar = ShrinkWrap
				.create(JavaArchive.class, "test-ejb.jar")
//				.addAsLibraries(
//						resolver.artifact(
//								"org.infinispan:infinispan-cdi:5.1.0.BETA3")
//								.resolveAsFiles())

				.addPackage(SignupRequestListProducer.class.getPackage())
				.addPackage(ApprovedCache.class.getPackage())
				.addPackage(SignupRequest.class.getPackage())
				.addPackage(InfinispanSignupRequestService.class.getPackage())
				.addPackage(Approved.class.getPackage())

				.addAsResource("users.properties", "users.properties")
				.addAsResource("roles.properties", "roles.properties")
				//.addAsWebInfResource(new File(WEBINF_SRC, "jboss-web.xml"))
				.addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
				//.setWebXML(new File(WEBINF_SRC, "web.xml"));

		System.out.println(jar.toString());
		return jar;
	}

	@Inject
	private ArquillianContext arquillianContext;

	@Inject
	SignupRequestService service;

	@Inject
	Logger log;

	@Test
	public void testCdiBootstrap() {
		log.info("@ service @" + service);
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
		Context _context = this.login("admin", "admin");

		try {
			this.service = this.getEjb(_context);
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

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
		Context _context = this.login("admin", "admin");

		try {
			this.service = this.getEjb(_context);
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

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

		SignupRequest _denidRequest = service.getAllDeniedRequests().get(0);

		assertNotNull(_denidRequest);

		service.approveDenied(_denidRequest.getId());

		SignupRequest _approvedRequest = service.getAllApprovedRequests()
				.get(0);

		assertNotNull(_approvedRequest);

	}

	private Context login(final String username, final String password) {
		// Precondition checks
		assert username != null : "username must be supplied";
		assert password != null : "password must be supplied";

		// Log in and create a context
		final Map<String, Object> namingContextProps = new HashMap<String, Object>();
		namingContextProps.put(Context.SECURITY_PRINCIPAL, username);
		namingContextProps.put(Context.SECURITY_CREDENTIALS, password);
		final Context context = arquillianContext.get(Context.class,
				namingContextProps);

		// Return
		return context;
	}

	private SignupRequestService getEjb(final Context context)
			throws NamingException {
		return (SignupRequestService) context
				.lookup("InfinispanSignupRequestService");
	}

}