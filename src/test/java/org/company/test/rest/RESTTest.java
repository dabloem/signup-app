package org.company.test.rest;

import java.net.URL;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import junit.framework.Assert;
import org.company.context.LoggerProducer;
import org.company.context.SignupRequestListProducer;
import org.company.model.SignupRequest;
import org.company.model.Status;
import org.company.rest.JaxRsActivator;
import org.company.rest.SignupRequestRestService;
import org.company.service.*;
import org.company.service.events.Approved;
import org.company.service.events.Confirmed;
import org.company.service.events.Denied;
import org.company.service.events.Registered;
import org.company.test.service.MockSignupRequestService;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.resteasy.client.ProxyFactory;
import org.jboss.resteasy.plugins.providers.RegisterBuiltin;
import org.jboss.resteasy.specimpl.MultivaluedMapImpl;
import org.jboss.resteasy.spi.ResteasyProviderFactory;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.DependencyResolvers;
import org.jboss.shrinkwrap.resolver.api.maven.MavenDependencyResolver;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
@RunAsClient
public class RESTTest {

    private static final String RESOURCE_PREFIX = JaxRsActivator.class.getAnnotation(ApplicationPath.class).value().substring(1);
    private static final String WEBAPP_SRC = "src/main/webapp";
    private static final String WEBINF_SRC = "src/main/webapp/WEB-INF";

    @BeforeClass
    public static void initResteasyClient() {
        RegisterBuiltin.register(ResteasyProviderFactory.getInstance());
    }

    @Deployment(testable = false)
    public static WebArchive createTestArchive() {
        MavenDependencyResolver resolver = DependencyResolvers.use(
                MavenDependencyResolver.class).loadMetadataFromPom("pom.xml");

        WebArchive jar = ShrinkWrap.create(WebArchive.class, "rest-test.war").addAsLibraries(
                resolver.artifact(
                "commons-httpclient:commons-httpclient:3.1").resolveAsFiles()).addClasses(SignupRequest.class, Status.class).addClass(LoggerProducer.class).addClass(Notifier.class).addClass(LogNotifier.class).addClass(SignupRequestService.class).addClass(MockSignupRequestService.class).addClass(SignupRequestListProducer.class).addClass(Predicate.class).addClass(SignupRequestNotFoundException.class).addClasses(Approved.class, Confirmed.class, Denied.class,
                Registered.class).addPackage(SignupRequestRestService.class.getPackage()) // .addAsResource("users.properties", "users.properties")
                // .addAsResource("roles.properties", "roles.properties")
                // .addAsWebResource(new File(WEBAPP_SRC, "index.html"))
                // .addAsWebResource(new File(WEBAPP_SRC, "index.xhtml"))
                // .addAsWebResource(new File(WEBAPP_SRC, "login.xhtml"))
                // .addAsWebResource(new File(WEBAPP_SRC, "ok.xhtml"))
                // .addAsWebResource(new File(WEBAPP_SRC, "register.xhtml"))
                // .addAsWebResource(new File(WEBAPP_SRC, "401.xhtml"))
                // .addAsWebResource(new File(WEBAPP_SRC, "403.xhtml"))
                // .addAsWebResource(new File(WEBAPP_SRC, "404.xhtml"))
                // .addAsWebResource(new File(WEBAPP_SRC, "500.xhtml"))
                // .addAsWebResource(new File(WEBAPP_SRC,
                // "admin/approved.xhtml"),
                // "admin/approved.xhtml")
                // .addAsWebResource(
                // new File(WEBAPP_SRC, "admin/confirmDenial.xhtml"),
                // "admin/confirmDenial.xhtml")
                // .addAsWebResource(
                // new File(WEBAPP_SRC, "admin/confirmed.xhtml"),
                // "admin/confirmed.xhtml")
                // .addAsWebResource(new File(WEBAPP_SRC, "admin/denied.xhtml"),
                // "admin/denied.xhtml")
                // .addAsWebResource(
                // new File(WEBAPP_SRC, "admin/requestDetail.xhtml"),
                // "admin/requestDetail.xhtml")
                // .addAsWebResource(
                // new File(WEBAPP_SRC, "admin/unconfirmed.xhtml"),
                // "admin/unconfirmed.xhtml")
                // .addAsWebResource(
                // new File(WEBAPP_SRC,
                // "resources/bscomp/formInputField.xhtml"),
                // "resources/bscomp/formInputField.xhtml")
                // .addAsWebResource(
                // new File(WEBAPP_SRC, "resources/css/bootstrap.css"),
                // "resources/css/bootstrap.css")
                // .addAsWebResource(
                // new File(WEBAPP_SRC, "resources/css/container-app.css"),
                // "resources/css/container-app.css")
                // .addAsWebResource(
                // new File(WEBAPP_SRC, "resources/css/screen.css"),
                // "resources/css/screen.css")
                // .addAsWebResource(
                // new File(WEBAPP_SRC, "resources/gfx/banner.png"),
                // "resources/gfx/banner.png")
                // .addAsWebResource(
                // new File(WEBAPP_SRC, "resources/gfx/logo.png"),
                // "resources/gfx/logo.png")
                // .addAsWebResource(
                // new File(WEBAPP_SRC, "resources/images/logo.png"),
                // "resources/images/logo.png")
                // .addAsWebInfResource(
                // new File(WEBINF_SRC, "templates/default.xhtml"),
                // "templates/default.xhtml")
                // .addAsWebInfResource(
                // new File(WEBINF_SRC, "templates/container-app.xhtml"),
                // "templates/container-app.xhtml")
                // .addAsWebInfResource(
                // new File(WEBINF_SRC, "templates/topbar.xhtml"),
                // "templates/topbar.xhtml")
                // .addAsWebInfResource(new File(WEBINF_SRC,
                // "faces-config.xml"))
                // .addAsWebInfResource(new File(WEBINF_SRC, "jboss-web.xml"))
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml") // .setWebXML(new File(WEBINF_SRC, "web.xml"))
                ;
        System.out.println(jar.toString());
        return jar;
    }
    @ArquillianResource
    URL deploymentUrl;

    @Test
    public void testSignupRequestProgress1() throws Exception {
        SignupRequestRestServiceClient client = ProxyFactory.create(
                SignupRequestRestServiceClient.class, deploymentUrl.toString()
                + RESOURCE_PREFIX);
        MultivaluedMap<String, String> mmap = new MultivaluedMapImpl<String, String>();
        mmap.add(SignupRequest.ATTR_FIRSTNAME, "hantsy");
        mmap.add(SignupRequest.ATTR_LASTNAME, "bai");
        System.out.println(client.register(mmap, "http://refer"));

        String response = client.listAllUnconfirmedRequsts();

        System.out.println("unconfirmed response@" + response);
        assertNotNull(response);
        assertTrue(response.contains("hantsy"));
        assertTrue(response.contains("bai"));

        int startPos = response.indexOf("<id>");
        int endPos = response.indexOf("</id>");

        String id = response.substring(startPos + 4, endPos);

        System.out.println(" @@id@@" + id);

        client.confirm(id);

        response = client.listAllConfirmedRequsts();

        System.out.println("confirmed response@" + response);
        assertNotNull(response);
        assertTrue(response.contains("hantsy"));
        assertTrue(response.contains("bai"));

        client.approve(id);

        response = client.listAllApprovedRequsts();

        System.out.println("approved response@" + response);
        assertNotNull(response);
        assertTrue(response.contains("hantsy"));
        assertTrue(response.contains("bai"));

    }

    @Test
    public void testSignupRequestProgress2() throws Exception {
        SignupRequestRestServiceClient client = ProxyFactory.create(
                SignupRequestRestServiceClient.class, deploymentUrl.toString()
                + RESOURCE_PREFIX);
        MultivaluedMap<String, String> mmap = new MultivaluedMapImpl<String, String>();
        mmap.add(SignupRequest.ATTR_FIRSTNAME, "hantsy");
        mmap.add(SignupRequest.ATTR_LASTNAME, "bai");
        System.out.println(client.register(mmap, "http://refer"));

        String response = client.listAllUnconfirmedRequsts();

        System.out.println("unconfirmed response@" + response);
        assertNotNull(response);
        assertTrue(response.contains("hantsy"));
        assertTrue(response.contains("bai"));

        int startPos = response.indexOf("<id>");
        int endPos = response.indexOf("</id>");

        String id = response.substring(startPos + 4, endPos);

        System.out.println(" @@id@@" + id);

        client.confirm(id);

        response = client.listAllConfirmedRequsts();

        System.out.println("confirmed response@" + response);
        assertNotNull(response);
        assertTrue(response.contains("hantsy"));
        assertTrue(response.contains("bai"));

        client.deny(id);

        response = client.listAllDeniedRequsts();

        System.out.println("approved response@" + response);
        assertNotNull(response);
        assertTrue(response.contains("hantsy"));
        assertTrue(response.contains("bai"));

        client.approveDenied(id);
        response = client.listAllApprovedRequsts();

        System.out.println("approved response@" + response);
        assertNotNull(response);
        assertTrue(response.contains("hantsy"));
        assertTrue(response.contains("bai"));


        response = client.listAllUnconfirmedRequsts();

        System.out.println("non existing response@" + response);
        assertNotNull(response);
        assertFalse(response.contains("hantsy"));
        assertFalse(response.contains("bai"));

        Response nonExisting = client.lookupById("23432423");
        Assert.assertTrue(nonExisting.getStatus() == 404);

    }
}