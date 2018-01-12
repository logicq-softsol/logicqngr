package com.logicq.ngr.model.admin.feature;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * A Metadata entity class which stores feature name and its associated REST URI
 * @author 611022088
 *
 */
@Table(name = "FEATURE_METADATA")
@Entity
public class FeatureMetadata implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3555197871430230759L;

	@Id
	@Column(name = "FEATURE_NAME")
	private String featureName;
	
	@Column(name = "URI", unique = true, nullable = false)
	private String uri;

	public String getFeatureName() {
		return featureName;
	}

	public void setFeatureName(String featureName) {
		this.featureName = featureName;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	@Override
	public String toString() {
		return "FeatureMetadata [featureName=" + featureName + ", uri=" + uri + "]";
	}
	
}
