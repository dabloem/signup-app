package org.company.service;

import java.util.List;

import org.company.context.qualifiers.ApprovedCache;
import org.company.context.qualifiers.ConfirmedCache;
import org.company.context.qualifiers.DeniedCache;
import org.company.context.qualifiers.UnconfirmedCache;
import org.company.model.SignupRequest;
import org.company.service.events.Approved;
import org.company.service.events.Confirmed;
import org.company.service.events.Denied;
import org.company.service.events.Registered;

/**
 * <p>
 * The service interface is designed to process signup request from client. 
 * </p>
 * <p>
 * There are two roles in this application, admin and viewer which are defined as ROLE_ADMINISTRATOR and ROLE_VIEWER in the web descriptor.
 * The administrator can approve or deny a sign request from administration console. 
 * An viewer can only view the request info.
 * </p>
 * 
 * <p>
 * There are four caches to store the requests of different status. Respectively, there are four qualifiers defined for the purpose. 
 * </p>
 *  
 * <ol> 
 * <li>{@link UnconfirmedCache} identify the unconfirmed cache.</li>
 * <li>{@link ConfirmedCache} identify the confirmed cache.</li>
 * <li>{@link ApprovedCache} identify the approved cache.</li>
 * <li>{@link DeniedCache} identify the denied cache.</li>
 * </ol>
 * 
 * <p>
 * There is the signup request process progress.
 * </p>
 * <ol> 
 * <li>The requester send a signup request from client form based page and submit via Rest API, 
 * the service will store it in the unconfirmed cache, and notify the requester/agent to confirm this request.</li> 
 * <li>The requester/agent can confirm the request via http url(in a email message or other place). 
 * When the request is confirmed, the service will move the request from unconfirmed cache to the confirmed cache.</li> 
 * <li>The administrator can approve or deny a confirmed request in the administration console, the service will move the request to the
 * status specified cache.</li> 
 * <li>The administrator can approve a denied request, the service will move the request to the approved cache.</li>
 *  </ol>
 *
 * <p>
 * When an action is performed, an related event should be fired, thus other service can be notified.
 * For example, the web interface can observes the events and display a message in page.
 * </p>
 * <p>
 * There are several events are defined at the moment.
 * </p>
 * <ol> 
 * <li>{@link Registered} identify the registered event which should be fired when an request is registered(stored in the unconfirmed cache).</li>
 * <li>{@link Confirmed} identify the confirmed event which should be fired when an request is confirmed(stored in the confirmed cache).</li>
 * <li>{@link Approved} identify the approved event which should be fired when an request is approved(stored in the approved cache).</li>
 * <li>{@link Denied} identify the denied event which should be fired when an request is denied(stored in the denied cache).</li>
 * </ol>
 * 
 * 
 * @author hantsy
 */
public interface SignupRequestService {

    /**
     * Fetch all unconfirmed requests.
     *
     * @return the list of unconfirmed requests.
     */
    public abstract List<SignupRequest> getAllUnconfirmedRequests();

    /**
     * Fetch all confirmed requests.
     *
     * @return the list of confirmed requests.
     */
    public abstract List<SignupRequest> getAllConfirmedRequests();

    /**
     * Fetch all approved requests.
     *
     * @return the list of approved requests.
     */
    public abstract List<SignupRequest> getAllApprovedRequests();

    /**
     * Fetch all denied requests.
     *
     * @return the list of denied requests.
     */
    public abstract List<SignupRequest> getAllDeniedRequests();

    /**
     * Register a Signup Request from client(web page or Rest web service), and save the request in unconfirmed cache.
     *
     */
    public abstract void register(SignupRequest m);

    /**
     * Retrieve the detail of the Signup request.
     *
     * @param id id of the Signup request
     * @return the signup request detail.
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
