package com.logicq.ngr.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.logicq.ngr.model.admin.Profile;
import com.logicq.ngr.model.admin.Role;
import com.logicq.ngr.model.report.Dashboard;

/**
 * 
 * @author 611164825
 *
 */
@Entity
@Table(name = "USER_DETAILS", uniqueConstraints = @UniqueConstraint(columnNames = { "USER_NAME", "EMAIL",
		"MOBILE_NUMBER", "EIN" }))
public class UserDetails implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3830649584518621238L;

	@Id
	@Column(name = "USER_ID", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "USER_NAME", unique = true, nullable = false)
	private String userName;

	@Column(name = "FIRSTNAME", nullable = false)
	private String firstName;

	@Column(name = "LASTNAME", nullable = false)
	private String lastName;

	@Column(name = "DOC")
	private Date dateofCreation;

	@ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
	@JoinColumn(name = "ROLE_ID")
	private Role role;

	@Column(name = "DOB")
	private Date dob;

	@Column(name = "EMAIL", unique = true, nullable = false)
	private String email;

	@Column(name = "MOBILE_NUMBER", unique = true, nullable = false)
	private String mobilenumber;

	@Column(name = "LAST_PASSWORD_RESET_DATE")
	private Date lastPasswordResetDate;

	@Column(name = "ENABLED")
	private Boolean enabled = false;

	@Column(name = "ISACTIVE")
	private Boolean isActive = false;

	@Column(name = "LAST_LOGGEDIN")
	private Date lastLoggedIn;

	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "userDetails")
	private List<Dashboard> dashboards;

	@ManyToOne
	@JoinColumn(name = "PROFILE_ID")
	private Profile profile;

	@Column(name = "EIN", unique = true, nullable = false)
	private String ein;

	@Column(name = "DELETED", nullable = false, columnDefinition = "boolean default false")
	private boolean deleted;

	@Column(name = "MODIFIED",nullable = false,columnDefinition = "boolean default false")
	private boolean modified;
	
	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public Date getLastLoggedIn() {
		return lastLoggedIn;
	}

	public void setLastLoggedIn(Date date) {
		this.lastLoggedIn = date;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
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

	public Date getDateofCreation() {
		return dateofCreation;
	}

	public void setDateofCreation(Date dateofCreation) {
		this.dateofCreation = dateofCreation;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobilenumber() {
		return mobilenumber;
	}

	public void setMobilenumber(String mobilenumber) {
		this.mobilenumber = mobilenumber;
	}

	public Date getLastPasswordResetDate() {
		return lastPasswordResetDate;
	}

	public void setLastPasswordResetDate(Date lastPasswordResetDate) {
		this.lastPasswordResetDate = lastPasswordResetDate;
	}

	public Boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public List<Dashboard> getDashboards() {
		return dashboards;
	}

	public void setDashboards(List<Dashboard> dashboards) {
		this.dashboards = dashboards;
	}

	public Profile getProfile() {
		return profile;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}

	public String getEin() {
		return ein;
	}

	public void setEin(String ein) {
		this.ein = ein;
	}
	
	public boolean isModified() {
		return modified;
	}

	public void setModified(boolean modified) {
		this.modified = modified;
	}

	@Override
	public String toString() {
		return "UserDetails [id=" + id + ", userName=" + userName + ", firstName=" + firstName + ", lastName="
				+ lastName + ", dateofCreation=" + dateofCreation + ", dob=" + dob + ", email=" + email
				+ ", mobilenumber=" + mobilenumber + ", lastPasswordResetDate=" + lastPasswordResetDate + ", enabled="
				+ enabled + ", isActive=" + isActive + ", lastLoggedIn=" + lastLoggedIn + ", ein=" + ein + ", deleted=" + deleted + ", modified="
				+ modified + "]";
	}
}
