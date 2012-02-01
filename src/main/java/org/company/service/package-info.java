/**
 * Confirm, approve and deny the signup request.
 * 
 * <p>
 * There are two roles in this application, administrator and viewer which are defined as ROLE_ADMINISTRATOR and ROLE_VIEWER in the web descriptor.
 * </p>
 * <ol> 
 * <li>Administrator who is able to approve or deny an signup request.</li>
 * <li>Viewer who can only view the view request info.</li>
 * </ol>
 * 
 * <p>
 * There are four caches to store the requests of different status. Respectively, there are four qualifiers defined for the purpose. 
 * </p>
 *  
 * <ol> 
 * <li>{@link org.company.context.qualifiers.UnconfirmedCache} identify the unconfirmed cache.</li>
 * <li>{@link org.company.context.qualifiers.ConfirmedCache} identify the confirmed cache.</li>
 * <li>{@link org.company.context.qualifiers.ApprovedCache} identify the approved cache.</li>
 * <li>{@link org.company.context.qualifiers.DeniedCache} identify the denied cache.</li>
 * </ol>
 * 
 * <p>
 * The {@link org.company.service.SignupRequestService} is responsible for processing the signup request from remote client via REST API.
 * </p>
 * <ol> 
 * <li>The requester send a signup request from client form based page and submit via Rest API, 
 * the service will store it in the unconfirmed cache, and notify the agent(defined as {@link  org.company.service.Notifier}, who is able to inform the requestor for confirmation).</li> 
 * <li>When the requester received the confirmation, he can confirm the request via http url(in a email message or other place). 
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
 * <li>{@link org.company.service.events.Registered} identify the registered event which should be fired when an request is registered(stored in the unconfirmed cache).</li>
 * <li>{@link org.company.service.events.Confirmed} identify the confirmed event which should be fired when an request is confirmed(stored in the confirmed cache).</li>
 * <li>{@link org.company.service.events.Approved} identify the approved event which should be fired when an request is approved(stored in the approved cache).</li>
 * <li>{@link org.company.service.events.Denied} identify the denied event which should be fired when an request is denied(stored in the denied cache).</li>
 * </ol>
 * 
 * 
 * @author hantsy
 */
package org.company.service;
