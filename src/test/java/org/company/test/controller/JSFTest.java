package org.company.test.controller;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

import javax.enterprise.inject.spi.BeanManager;
import javax.inject.Inject;

import junit.framework.Assert;

import org.company.context.LoggerProducer;
import org.company.context.SignupRequestListProducer;
import org.company.controller.LoginAction;
import org.company.model.SignupRequest;
import org.company.model.Status;
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
import org.jboss.jsfunit.api.InitialPage;
import org.jboss.jsfunit.jsfsession.JSFClientSession;
import org.jboss.jsfunit.jsfsession.JSFServerSession;
import org.jboss.jsfunit.jsfsession.JSFSession;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class JSFTest {
	private static final String WEBAPP_SRC = "src/main/webapp";
	private static final String WEBINF_SRC = "src/main/webapp/WEB-INF";

	// private static MavenDependencyResolver resolver =
	// DependencyResolvers.use(
	// MavenDependencyResolver.class).loadMetadataFromPom("pom.xml");

	@Deployment
	public static WebArchive createTestArchive() {
		WebArchive jar = ShrinkWrap
				.create(WebArchive.class, "test.war")
				// .addAsLibraries(
				// resolver.artifact(
				// "org.jboss.jsfunit:jboss-jsfunit-core")
				// .resolveAsFiles())
				// .addAsLibraries(
				// resolver.artifact("org.jboss.shrinkwrap.resolver:shrinkwrap-resolver-impl-maven")
				// .resolveAsFiles())
				.addClasses(SignupRequest.class, Status.class)
				.addClass(LoggerProducer.class)
				.addClass(Notifier.class)
				.addClass(LogNotifier.class)
				.addClass(SignupRequestService.class)
				.addClass(MockSignupRequestService.class)
				.addClass(SignupRequestListProducer.class)
				.addClass(Predicate.class)
				.addClass(SignupRequestNotFoundException.class)
				.addClasses(Approved.class, Confirmed.class, Denied.class,
						Registered.class)
				.addPackage(LoginAction.class.getPackage())
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
				.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
				.setWebXML(new File(WEBINF_SRC, "web.xml"));
		System.out.println(jar.toString());
		return jar;
	}

	@Inject
	BeanManager beanManager;

	@Inject
	Logger log;

	@Inject
	SignupRequestService requestService;

	@Test
	public void testCdiBootstrap() {
		assertNotNull(beanManager);
		assertFalse(beanManager.getBeans(BeanManager.class).isEmpty());

	}

	@Test
	@InitialPage("/index.jsf")
	public void testIndexPage(JSFServerSession server, JSFClientSession client) {
		Assert.assertEquals("/index.xhtml", server.getCurrentViewID());
	}

	@Test
	@InitialPage("/login.jsf")
	public void testLoginPage(JSFServerSession server, JSFClientSession client)
			throws IOException {
		Assert.assertEquals("/login.xhtml", server.getCurrentViewID());

		client.setValue("loginForm:username", "user");
		client.setValue("loginForm:password", "user");
		client.click("loginForm:loginButton");

		Assert.assertEquals("/admin/unconfirmed.xhtml",
				server.getCurrentViewID());
		Assert.assertEquals("user", server.getFacesContext()
				.getExternalContext().getRemoteUser());
		log.info("server.getFacesContext().getExternalContext().getUserPrincipal()@"
				+ server.getFacesContext().getExternalContext()
						.getUserPrincipal());

		client.click("logoutForm:logoutButton");
		Assert.assertEquals("/login.xhtml", server.getCurrentViewID());
		Assert.assertNull(server.getFacesContext().getExternalContext()
				.getRemoteUser());
	}

	@Test
	@InitialPage("/register.jsf")
	public void testConfirmRequest(JSFServerSession server,
			JSFClientSession client) throws IOException {
		Assert.assertEquals("/register.xhtml", server.getCurrentViewID());
		client.setValue("regForm:firstName:input", "hantsy");
		client.setValue("regForm:lastName:input", "bai");
		client.setValue("regForm:email:input", "hantsy@abc.com");
		client.setValue("regForm:companyName:input", "Hantsy Labs");
		client.setValue("regForm:comment:input", "No Comments");

		client.click("regForm:registerButton");

		Assert.assertEquals("/ok.xhtml", server.getCurrentViewID());

		Assert.assertTrue(!requestService.getAllUnconfirmedRequests().isEmpty());
//		Assert.assertNotNull(server
//				.getManagedBeanValue("unconfirmedRequests"));

	}

}