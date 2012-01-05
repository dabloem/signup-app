package org.company.test.service;

import javax.annotation.Resources;
import javax.inject.Inject;

import org.company.model.SignupRequest;
import org.company.service.InfinispanSignupRequestService;
import org.company.service.Predicate;
import org.company.service.SignupRequestNotFoundException;
import org.company.service.SignupRequestService;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class SignupRequestServiceTest {

	@Deployment
	public static JavaArchive createDeployment() {
		return ShrinkWrap.create(JavaArchive.class)
				.addClasses(SignupRequestService.class,InfinispanSignupRequestService.class, Predicate.class, SignupRequestNotFoundException.class)
				.addPackage(SignupRequest.class.getPackage())
				.addPackage(Resources.class.getPackage())
				.addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
	}

	@Inject
	SignupRequestService service;

	@Test
	public void testRegister() throws Exception {
		//service.register(m);
	}
}