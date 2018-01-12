package com.logicq.ngr.model.druid.common;

import java.io.Serializable;

import com.logicq.ngr.validator.Preconditions;

public class Aggregations implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8029264760311972357L;
	private String type;
	private String name;
	private String fieldName;

	public String getType() {
		return type;
	}

	public String getName() {
		return name;
	}

	public String getFieldName() {
		return fieldName;
	}

	private Aggregations(AggregationsBuilder aggreationbuilder) {
		this.type = aggreationbuilder.type;
		this.name = aggreationbuilder.name;
		this.fieldName = aggreationbuilder.fieldName;
	}

	public static class AggregationsBuilder {

		private String type;
		private String name;
		private String fieldName;

		public AggregationsBuilder(String type, String name, String fieldName) {
			this.type = Preconditions.checkNotNull(type, "Aggreation Type  must not be null");
			this.name = Preconditions.checkNotNull(name, "Aggreation Name  must not be null");
			this.fieldName = fieldName;
		}

		public Aggregations build() {
			return new Aggregations(this);
		}
	}
	


}
