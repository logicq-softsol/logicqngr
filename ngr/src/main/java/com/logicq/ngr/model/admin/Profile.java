package com.logicq.ngr.model.admin;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.logicq.ngr.model.UserDetails;
import com.logicq.ngr.model.reportpack.ReportpackDetails;

@Table(name = "PROFILE")
@Entity
public class Profile implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4677090558564322234L;

	@Id
	@Column(name = "PROFILE_ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long profileId;

	@Column(name = "NAME")
	private String name;

	@JsonIgnore
	@OneToMany(mappedBy = "profile", cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
	private Set<UserDetails> users;

	@JsonIgnore
	@ManyToMany(cascade = CascadeType.PERSIST,fetch = FetchType.EAGER)
	@JoinTable(name = "PROFILE_ROLE", joinColumns = @JoinColumn(name = "PROFILE_ID"), inverseJoinColumns = @JoinColumn(name = "ROLE_ID"))
	private List<Role> roles;

	@Lob
	@Column(name = "FEATURE_PROPERTY")
	private byte[] features;
	
	@JsonIgnore
	@ManyToMany(cascade = CascadeType.PERSIST,fetch = FetchType.EAGER)
	@JoinTable(name = "PROFILE_REPORTPACK", joinColumns = @JoinColumn(name = "PROFILE_ID"), inverseJoinColumns = @JoinColumn(name = "REPORTPACK_ID"))
	private Set<ReportpackDetails> reportpacks=new HashSet<>();
	
	public Set<ReportpackDetails> getReportpacks() {
		return reportpacks;
	}

	public void setReportpacks(Set<ReportpackDetails> reportpacks) {
		this.reportpacks = reportpacks;
	}

	public Long getProfileId() {
		return profileId;
	}

	public void setProfileId(Long profileId) {
		this.profileId = profileId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<UserDetails> getUsers() {
		return users;
	}

	public void setUsers(Set<UserDetails> users) {
		this.users = users;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public byte[] getFeatures() {
		return features;
	}

	public void setFeatures(byte[] features) {
		this.features = features;
	}

	@Override
	public String toString() {
		return "Profile [profileId=" + profileId + ", name=" + name + "]";
	}

}