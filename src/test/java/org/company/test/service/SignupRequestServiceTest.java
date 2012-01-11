package org.company.test.service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.util.Date;

import javax.annotation.Resources;
import javax.inject.Inject;
import javax.naming.Context;
import javax.naming.NamingException;

import org.company.context.SignupRequestListProducer;
import org.company.controller.DenyAction;
import org.company.model.SignupRequest;
import org.company.service.InfinispanSignupRequestService;
import org.company.service.SignupRequestNotFoundException;
import org.company.service.SignupRequestService;
import org.company.service.events.Approved;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.DependencyResolvers;
import org.jboss.shrinkwrap.resolver.api.maven.MavenDependencyResolver;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class SignupRequestServiceTest {
	private static final String WEBAPP_SRC = "src/main/webapp";
	private static final String WEBINF_SRC = "src/main/webapp/WEB-INF";

	private MavenDependencyResolver resolver = DependencyResolvers.use(
			MavenDependencyResolver.class).loadMetadataFromPom("pom.xml");

	@Deployment
	public static WebArchive createTestArchive() {
		WebArchive jar = ShrinkWrap
				.create(WebArchive.class)
				.addPackages(false, SignupRequest.class.getPackage(),
						Resources.class.getPackage(),
						InfinispanSignupRequestService.class.getPackage(),
						Approved.class.getPackage(),
						DenyAction.class.getPackage(),
						SignupRequestListProducer.class.getPackage())
				.addAsResource("users.properties", "users.properties")
				.addAsResource("roles.properties", "roles.properties")
				.addAsWebResource(new File(WEBAPP_SRC, "index.html"))
				.addAsWebResource(new File(WEBAPP_SRC, "index.xhtml"))
				.addAsWebResource(new File(WEBAPP_SRC, "login.xhtml"))
				.addAsWebResource(new File(WEBAPP_SRC, "ok.xhtml"))
				.addAsWebResource(new File(WEBAPP_SRC, "register.xhtml"))
				.addAsWebResource(new File(WEBAPP_SRC, "401.xhtml"))
				.addAsWebResource(new File(WEBAPP_SRC, "403.xhtml"))
				.addAsWebResource(new File(WEBAPP_SRC, "404.xhtml"))
				.addAsWebResource(new File(WEBAPP_SRC, "500.xhtml"))
				.addAsWebResource(new File(WEBAPP_SRC, "admin/approved.xhtml"),
						"admin/approved.xhtml")
				.addAsWebResource(
						new File(WEBAPP_SRC, "admin/confirmDenial.xhtml"),
						"admin/confirmDenial.xhtml")
				.addAsWebResource(
						new File(WEBAPP_SRC, "admin/confirmed.xhtml"),
						"admin/confirmed.xhtml")
				.addAsWebResource(new File(WEBAPP_SRC, "admin/denied.xhtml"),
						"admin/denied.xhtml")
				.addAsWebResource(
						new File(WEBAPP_SRC, "admin/requestDetail.xhtml"),
						"admin/requestDetail.xhtml")
				.addAsWebResource(
						new File(WEBAPP_SRC, "admin/unconfirmed.xhtml"),
						"admin/unconfirmed.xhtml")
				.addAsWebResource(
						new File(WEBAPP_SRC,
								"resources/bscomp/formInputField.xhtml"),
						"resources/bscomp/formInputField.xhtml")
				.addAsWebResource(
						new File(WEBAPP_SRC, "resources/css/bootstrap.css"),
						"resources/css/bootstrap.css")
				.addAsWebResource(
						new File(WEBAPP_SRC, "resources/css/container-app.css"),
						"resources/css/container-app.css")
				.addAsWebResource(
						new File(WEBAPP_SRC, "resources/css/screen.css"),
						"resources/css/screen.css")
				.addAsWebResource(
						new File(WEBAPP_SRC, "resources/gfx/banner.png"),
						"resources/gfx/banner.png")
				.addAsWebResource(
						new File(WEBAPP_SRC, "resources/gfx/logo.png"),
						"resources/gfx/logo.png")
				.addAsWebResource(
						new File(WEBAPP_SRC, "resources/images/logo.png"),
						"resources/images/logo.png")
				.addAsWebInfResource(
						new File(WEBINF_SRC, "templates/default.xhtml"),
						"templates/default.xhtml")
				.addAsWebInfResource(
						new File(WEBINF_SRC, "templates/container-app.xhtml"),
						"templates/container-app.xhtml")
				.addAsWebInfResource(
						new File(WEBINF_SRC, "templates/topbar.xhtml"),
						"templates/topbar.xhtml")
				.addAsWebInfResource(new File(WEBINF_SRC, "faces-config.xml"))
				.addAsWebInfResource(new File(WEBINF_SRC, "jboss-web.xml"))
				.setWebXML(new File(WEBINF_SRC, "web.xml"));
		;
		System.out.println(jar.toString());
		return jar;
	}

	// @Inject
	// private ArquillianContext arqContext;

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

		SignupRequest _denidRequest = service.getAllDeniedRequests().get(0);

		assertNotNull(_denidRequest);

		service.approveDenied(_denidRequest.getId());

		SignupRequest _approvedRequest = service.getAllApprovedRequests()
				.get(0);

		assertNotNull(_approvedRequest);

	}

	// private Context login(final String username, final String password) {
	// // Precondition checks
	// assert username != null : "username must be supplied";
	// assert password != null : "password must be supplied";
	//
	// // Log in and create a context
	// final Map<String, Object> namingContextProps = new HashMap<String,
	// Object>();
	// namingContextProps.put(Context.SECURITY_PRINCIPAL, username);
	// namingContextProps.put(Context.SECURITY_CREDENTIALS, password);
	// final Context context;// = arqContext.get(Context.class,
	// // namingContextProps);
	//
	// // Return
	// return context;
	// }

	private SignupRequestService getEjb(final Context context)
			throws NamingException {
		return (SignupRequestService) context
				.lookup("InfinispanSignupRequestService");
	}

}