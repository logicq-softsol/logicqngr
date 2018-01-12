package com.logicq.ngr.vo;

import java.io.Serializable;
import java.util.Date;

public class UserDetailVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5639643248019818728L;

	private String userName;
	private String firstName;
	private String lastName;
	private String email;
	private Boolean enabled;

	private Date lastLoggedIn;
	private Date lastPasswordResetDate;
	private RoleVO roleVO;
	private String ein;
	private String mobileNumber;
	private Date dob;
	private ProfileVO profileVO;
	private Boolean isActive = false;

	public UserDetailVO() {

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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public Date getLastPasswordResetDate() {
		return lastPasswordResetDate;
	}

	public void setLastPasswordResetDate(Date lastPasswordResetDate) {
		this.lastPasswordResetDate = lastPasswordResetDate;
	}

	public String getEin() {
		return ein;
	}

	public void setEin(String ein) {
		this.ein = ein;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public Date getLastLoggedIn() {
		return lastLoggedIn;
	}

	public void setLastLoggedIn(Date lastLoggedIn) {
		this.lastLoggedIn = lastLoggedIn;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public RoleVO getRoleVO() {
		return roleVO;
	}

	public void setRoleVO(RoleVO roleVO) {
		this.roleVO = roleVO;
	}

	public ProfileVO getProfileVO() {
		return profileVO;
	}

	public void setProfileVO(ProfileVO profileVO) {
		this.profileVO = profileVO;
	}

	@Override
	public String toString() {
		return "UserDetailVO [userName=" + userName + ", firstName=" + firstName + ", lastName=" + lastName + ", email="
				+ email + ", enabled=" + enabled + ", lastLoggedIn=" + lastLoggedIn + ", lastPasswordResetDate="
				+ lastPasswordResetDate + ", roleVO=" + roleVO + ", ein=" + ein + ", mobileNumber=" + mobileNumber
				+ ", dob=" + dob + ", profileVO=" + profileVO + ", isActive=" + isActive + "]";
	}
}
