package org.company.test.rest;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.util.Date;

import javax.inject.Inject;

import org.company.context.LoggerProducer;
import org.company.controller.DenyAction;
import org.company.controller.LoginAction;
import org.company.controller.RequestHome;
import org.company.model.SignupRequest;
import org.company.model.Status;
import org.company.rest.SignupRequestRestService;
import org.company.service.LogNotifier;
import org.company.service.Notifier;
import org.company.service.Predicate;
import org.company.service.SignupRequestNotFoundException;
import org.company.service.SignupRequestService;
import org.company.service.events.Approved;
import org.company.service.events.Confirmed;
import org.company.service.events.Denied;
import org.company.service.events.Registered;
import org.company.test.service.MockSignupRequestService;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class RESTTest {
	private static final String WEBAPP_SRC = "src/main/webapp";
	private static final String WEBINF_SRC = "src/main/webapp/WEB-INF";

	@Deployment
	public static WebArchive createTestArchive() {
		WebArchive jar = ShrinkWrap
				.create(WebArchive.class, "rest-test.war")
				.addClasses(SignupRequest.class, Status.class)
				.addClass(LoggerProducer.class)
				.addClass(Notifier.class)
				.addClass(LogNotifier.class)
				.addClass(SignupRequestService.class)
				.addClass(MockSignupRequestService.class)
				.addClass(Predicate.class)
				.addClass(SignupRequestNotFoundException.class)
				.addClasses(Approved.class, Confirmed.class, Denied.class, Registered.class)
				.addPackages(false, SignupRequestRestService.class.getPackage())				
				.addAsResource("users.properties", "users.properties")
				.addAsResource("roles.properties", "roles.properties")	
				.addAsWebInfResource(new File(WEBINF_SRC, "jboss-web.xml"))
				.setWebXML(new File(WEBINF_SRC, "web.xml"))
				.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
		System.out.println(jar.toString());
		return jar;
	}


	@Inject
	SignupRequestService service;

	@Test
	public void testCdiBootstrap() {
		assertNotNull(service);
	}
	


}