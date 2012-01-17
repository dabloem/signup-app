package org.company.service;

import java.util.List;
import org.company.model.SignupRequest;

/**
 * Confirm, approve, deny a Signup request.
 *
 * @author hantsy
 */
public interface SignupRequestService {

    /**
     * Fetch all unconfirmed requests.
     *
     * @return
     */
    public abstract List<SignupRequest> getAllUnconfirmedRequests();

    /**
     * Fetch all confirmed requests.
     *
     * @return
     */
    public abstract List<SignupRequest> getAllConfirmedRequests();

    /**
     * Fetch all approved requests.
     *
     * @return
     */
    public abstract List<SignupRequest> getAllApprovedRequests();

    /**
     * Fetch all denied requests.
     *
     * @return
     */
    public abstract List<SignupRequest> getAllDeniedRequests();

    /**
     * Register a Signup Request from client(web page or Rest web service), and save the request in unconfirmed cache.
     *
     * @return
     */
    public abstract void register(SignupRequest m);

    /**
     * Retrieve the detail of the Signup request.
     *
     * @param id id of the Signup request
     * @return
     */
    public abstract SignupRequest get(String id);

    /**
     * Approve a confirmed Signup Request, remove it from the confirmed cache ,and save it into the approved cache.
     *
     * @param id id of the Signup Request
     */
    public abstract void approve(String id);

    /**
     * Deny a confirmed Signup Request, remove it from the confirmed cache ,and save it into the denied cache.
     *
     * @param id id of the Signup Request
     */
    public abstract void deny(String id);

    /**
     * Confirm a unconfirmed Signup Request, remove it from the unconfirmed cache ,and save it into the confirmed cache.
     *
     * @param id id of the Signup Request
     */
    public abstract void confirm(String id);

    /**
     * Approve a denied Signup Request, remove it from the denied cache ,and save it into the approved cache.
     *
     * @param id id of the Signup Request
     */
    public abstract void approveDenied(String id);
}
