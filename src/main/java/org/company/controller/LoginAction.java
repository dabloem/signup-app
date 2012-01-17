package org.company.controller;

import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.enterprise.event.Reception;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

/**
 * Process login and logout action from administration console.
 *
 * @author hantsy
 */
@Named("loginAction")
@RequestScoped
public class LoginAction {

    @Inject
    private Logger log;
    private String username;
    private String password;
    @Inject
    @LoggedIn
    Event<String> loggedInEventSrc;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Logout action.
     *
     * @return if successfully, redirect to the login page.
     */
    public String logout() {
        log.info("logout....@");
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        externalContext.invalidateSession();
        try {
            ((HttpServletRequest) externalContext.getRequest()).logout();
        } catch (ServletException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "/login?faces-redirect=true";
    }

    /**
     * Login action.
     *
     * @return if successfully, redirect to unconfirmed list page, or return to login page.
     */
    public String login() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        try {
            request.login(username, password);

            loggedInEventSrc.fire(this.username);

            return "/admin/unconfirmed?faces-redirect=true";
        } catch (ServletException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "/login?faces-redirect=true&error=1";
    }

    public void onLoggedIn(
            @Observes(notifyObserver = Reception.IF_EXISTS) @LoggedIn String username) {
        log.info("loggedin event was triggered.");
        FacesUtil.info("Welcome back, " + username);

    }
}
