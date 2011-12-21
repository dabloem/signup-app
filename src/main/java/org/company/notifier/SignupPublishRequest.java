/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.company.notifier;

import com.amazonaws.services.sns.model.PublishRequest;
import org.company.model.SignupRequest;

/**
 *
 * @author duncan
 */
class SignupPublishRequest extends PublishRequest {
    
    private static final String TOPIC_ARN = "arn:aws:sns:eu-west-1:544793272093:notify";
    private static final String SUBJECT = "signup request";
    
    public SignupPublishRequest() {
        super(TOPIC_ARN, "no message yet", SUBJECT);
    }

    void setSignupRequest(SignupRequest signupRequest) {
        //TODO json format signupRequest
        setMessage("http://<some ip address>:8080/signup-app/rest/signup/confirm/"+signupRequest.getId());
    }    
}
