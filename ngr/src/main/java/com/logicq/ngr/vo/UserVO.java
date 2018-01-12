package com.logicq.ngr.vo;

import java.util.Collection;
import java.util.Date;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserVO implements UserDetails {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1087611289059293540L;
	private String id;
	private String userName;
	private String firstName;
	private String lastName;
	private String email;
	private Collection<? extends GrantedAuthority> authorities;
	private Boolean enabled;
	private Date lastPasswordResetDate;
	private String lastLoggedIn;
	private String role;
	private String ein;
	private String mobileNumber;
	private String dob;
	

	public UserVO(){
		
	}
	public UserVO(String username, String firstname, String lastname, String email, String ein,
			Collection<? extends GrantedAuthority> authorities, String lastLoggedIn, Boolean enabled) {
		this.userName = username;
		this.firstName = firstname;
		this.lastName = lastname;
		this.email = email;
		this.ein = ein;
		this.authorities = authorities;
		this.lastLoggedIn = lastLoggedIn;
		this.enabled = enabled;

	}

	public UserVO(String username, String firstname, String lastname, String date) {
		this.userName = username;
		this.firstName = firstname;
		this.lastName = lastname;
		this.lastLoggedIn = date;
	}

	public UserVO(String username, String role) {
		this.userName = username;
		this.role = role;
	}

	public UserVO(String id, Boolean enabled) {
		this.id = id;
		this.enabled = enabled;
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
	public String getEin() {
		return ein;
	}

	public void setEin(String ein) {
		this.ein = ein;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	@JsonIgnore
	public String getId() {
		return id;
	}

	public String getUsername() {
		return userName;
	}

	@JsonIgnore
	public boolean isAccountNonExpired() {
		return true;
	}

	@JsonIgnore
	public boolean isAccountNonLocked() {
		return true;
	}

	@JsonIgnore
	public boolean isCredentialsNonExpired() {
		return true;
	}

	public String getFirstname() {
		return firstName;
	}

	public String getLastname() {
		return lastName;
	}

	public String getEmail() {
		return email;
	}

	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public boolean isEnabled() {
		return enabled;
	}

	@JsonIgnore
	public Date getLastPasswordResetDate() {
		return lastPasswordResetDate;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setUsername(String username) {
		this.userName = username;
	}

	public void setFirstname(String firstname) {
		this.firstName = firstname;
	}

	public void setLastname(String lastname) {
		this.lastName = lastname;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
		this.authorities = authorities;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public void setLastPasswordResetDate(Date lastPasswordResetDate) {
		this.lastPasswordResetDate = lastPasswordResetDate;
	}

	public String getLastLoggedIn() {
		return lastLoggedIn;
	}

	public void setLastLoggedIn(String lastLoggedIn) {
		this.lastLoggedIn = lastLoggedIn;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	@Override
	public String toString() {
		return "UserVO [id=" + id + ", userName=" + userName + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", email=" + email + ", authorities=" + authorities + ", enabled=" + enabled
				+ ", lastPasswordResetDate=" + lastPasswordResetDate + ", lastLoggedIn=" + lastLoggedIn + ", role="
				+ role + ", ein=" + ein + ", mobileNumber=" + mobileNumber + ", dob=" + dob + "]";
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return null;
	}

}
