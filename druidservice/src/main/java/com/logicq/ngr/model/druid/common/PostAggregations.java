package com.logicq.ngr.model.druid.common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.logicq.ngr.constant.Constants;
import com.logicq.ngr.validator.Preconditions;

public class PostAggregations implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3274244697746216837L;
	private String type;
	private String name;
	private String fn;
	private List<Aggregations> fields;

	public String getType() {
		return type;
	}

	public String getName() {
		return name;
	}

	public String getFn() {
		return fn;
	}

	public List<Aggregations> getFields() {
		return fields;
	}

	private PostAggregations(PostAggregationsBuilder postAggregationBuilder) {
		this.type = postAggregationBuilder.type;
		this.name = postAggregationBuilder.name;
		this.fn = postAggregationBuilder.fn;
		this.fields = postAggregationBuilder.fields;
	}

	public static class PostAggregationsBuilder {
		private String type;
		private String name;
		private String fn;
		private List<Aggregations> fields = new ArrayList<>();

		public PostAggregationsBuilder(String type, String name, String fn) {
			this.type = Preconditions.checkNotNull(type, "Aggreation Type  must not be null");
			this.name = Preconditions.checkNotNull(name, "Aggreation Name  must not be null");
			this.fn = fn;
			Aggregations aggregation1 = new Aggregations.AggregationsBuilder(Constants.fieldAccess,
					name + Constants.UnderScore + Constants.Total, name + Constants.UnderScore + Constants.Total)
							.build();
			Aggregations aggregation2 = new Aggregations.AggregationsBuilder(Constants.fieldAccess,
					name + Constants.UnderScore + Constants.ROWS, name + Constants.UnderScore + Constants.ROWS).build();
			fields.add(aggregation1);
			fields.add(aggregation2);

		}

		public PostAggregations build() {
			return new PostAggregations(this);
		}
	}

}
