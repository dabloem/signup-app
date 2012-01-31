package org.company.service;

import java.util.List;
import org.company.model.SignupRequest;

/**
 * <p>
 * The service interface to process signup request. 
 * </p>
 * <p>
 * There are two roles in this application, admin and viewer(the role symbols are ROLE_ADMINISTRATOR and ROLE_VIEWER).
 * Admin can confirm, approve or deny a sign request. An viewer can only view the request info.
 * </p>
 * <ol> 
 * <li>When a new signup request is sent the server side, the
 * service will store it in the unconfirmed cache, and notify Admin to confirm this request.</li> 
 * <li>Admin can confirm a unconfirmed request in the admin console, the service will move the request from unconfirmed cache to the
 * confirmed cache.</li> 
 * <li>Admin can approve or deny a confirmed request, the service will move the request to the
 * status specified cache.</li> 
 * <li>Admin can approve a denied request, the service will move the request to the
 * approved cache.</li>
 *  </ol>
 *
 * <p>
 * When an action is performed, an related event should be fired, thus other service can be notified.
 * For example, the web interface can observes the events and display a message in page.
 * </p>
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
