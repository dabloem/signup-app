/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.company.notifier;

import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.PropertiesCredentials;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClient;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.Alternative;
import javax.inject.Inject;
import org.company.model.SignupRequest;
import org.company.service.Notifier;
import org.company.service.events.Registered;

/**
 *
 * @author duncan
 */
@Alternative
public class AmazonNotifier implements Notifier {

    private final String ENDPOINT = "https://sns.eu-west-1.amazonaws.com";
    private final String AWS_PROPERTIES = "/aws.properties";
    
    @Inject
    private SignupPublishRequest publishRequest;
    
    @Override
    public void notify(@Observes @Registered SignupRequest signupRequest) {
        try {
            AWSCredentials credentials = new PropertiesCredentials(AmazonNotifier.class.getResourceAsStream(AWS_PROPERTIES));
            AmazonSNS amazonSNS = new AmazonSNSClient(credentials);
            amazonSNS.setEndpoint(ENDPOINT);
            
            publishRequest.setSignupRequest(signupRequest);
            amazonSNS.publish(publishRequest);
        } catch (AmazonClientException ex) {
            //TODO send to exception cach/log/whatever...
            Logger.getLogger(AmazonNotifier.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(AmazonNotifier.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}