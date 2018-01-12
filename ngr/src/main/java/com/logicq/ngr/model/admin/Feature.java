package com.logicq.ngr.model.admin;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Table(name = "FEATURE")
@Entity
public class Feature implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3657403934303989419L;

	@Id
	@Column(name = "FEATURE_ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long featureId;

	@Column(name = "APPLICABLE_TO")
	private String applicableTo;

	@Column(name = "APPLICABLE_TYPE")
	private String applicableType;

	@Lob
	@Column(name = "FEATURE_PROPERTY")
	private byte[] featureProperty;

	@JsonIgnore
	@ManyToMany(cascade = CascadeType.PERSIST)
	@JoinTable(name = "ROLE_FEATURE", joinColumns = @JoinColumn(name = "FEATURE_ID"), inverseJoinColumns = @JoinColumn(name = "ROLE_ID"))
	private List<Role> roles;

	public Long getFeatureId() {
		return featureId;
	}

	public void setFeatureId(Long featureId) {
		this.featureId = featureId;
	}

	public String getApplicableTo() {
		return applicableTo;
	}

	public void setApplicableTo(String applicableTo) {
		this.applicableTo = applicableTo;
	}

	public String getApplicableType() {
		return applicableType;
	}

	public void setApplicableType(String applicableType) {
		this.applicableType = applicableType;
	}

	public byte[] getFeatureProperty() {
		return featureProperty;
	}

	public void setFeatureProperty(byte[] featureProperty) {
		this.featureProperty = featureProperty;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	@Override
	public String toString() {
		return "Feature [featureId=" + featureId + ", applicableTo=" + applicableTo + ", applicableType="
				+ applicableType + ", featureProperty=" + Arrays.toString(featureProperty) + "]";
	}

}