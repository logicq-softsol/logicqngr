package com.logicq.ngr.model.report;

import java.io.Serializable;

import com.logicq.ngr.vo.FeatureDetailsVO;

public class BaseReport implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7682851143428259726L;

	private Boolean isEnabled;
	private String userName;
	private String firstName;
	private String lastName;
	private String email;
	private String lastLoggedIn;
	private FeatureDetailsVO featureConfig;

	public Boolean getIsEnabled() {
		return isEnabled;
	}

	public void setIsEnabled(Boolean isEnabled) {
		this.isEnabled = isEnabled;
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

	public String getLastLoggedIn() {
		return lastLoggedIn;
	}

	public void setLastLoggedIn(String dateTime) {
		this.lastLoggedIn = dateTime;
	}

	public FeatureDetailsVO getFeatureConfig() {
		return featureConfig;
	}

	public void setFeatureConfig(FeatureDetailsVO featureConfig) {
		this.featureConfig = featureConfig;
	}

	
	
	@Override
	public String toString() {
		return "BaseReport [isEnabled=" + isEnabled + ", userName=" + userName + ", firstName=" + firstName
				+ ", lastName=" + lastName + ", email=" + email + ", lastLoggedIn=" + lastLoggedIn + ", featureConfig="
				+ featureConfig + "]";
	}

}
