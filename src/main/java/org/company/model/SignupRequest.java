package org.company.model;

import java.io.Serializable;
import java.util.Date;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * The object to wrap the Signup Reqeust.
 * @author hantsy
 *
 */
@XmlRootElement
public class SignupRequest implements Serializable {

	/**
	 * Default value included to remove warning. Remove or modify at will. *
	 */
	private static final long serialVersionUID = 1L;
	public static final String ATTR_EMAIL = "email";
	public static final String ATTR_FIRSTNAME = "firstName";
	public static final String ATTR_LASTNAME = "lastName";
	public static final String ATTR_HTTP_REFERER = "Referer";
	public static final String ATTR_COMMENT = "comment";
	public static final String ATTR_COMPANY_NAME = "companyName";
	private String id;
	private String email;
	private String companyName;
	private String httpRefer;
	private String firstName;
	private String lastName;
	private String comment;
	private Date createdOn;
	private Status status;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getHttpRefer() {
		return httpRefer;
	}

	public void setHttpRefer(String httpRefer) {
		this.httpRefer = httpRefer;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public String getName() {
		return (this.getFirstName() == null ? "" : this.getFirstName() + " ")
				+ (this.getLastName() == null ? "" : this.getLastName());
	}

	@Override
	public String toString() {
		return "SignupRequest{" + "email=" + email + ", companyName="
				+ companyName + ", firstName=" + firstName + ", lastName="
				+ lastName + ", status=" + status + ", createdOn=" + createdOn
				+ '}';
	}
}