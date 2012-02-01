package org.company.service;

import org.company.model.SignupRequest;

/**
 * The agent who is able to send confirmation to the requester.
 * @author hantsy
 */
public interface Notifier {

    public void notify(SignupRequest m);
}
