package com.logicq.ngr.client.druid;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.logicq.ngr.model.druid.request.DruidGroupByRequest;
import com.logicq.ngr.model.druid.request.DruidSelectRequest;
import com.logicq.ngr.model.druid.request.DruidTimeseriesRequest;
import com.logicq.ngr.model.druid.request.DruidTopNRequest;
import com.logicq.ngr.model.response.DruidBaseResponse;
import com.logicq.ngr.model.response.DruidGroupByResponse;
import com.logicq.ngr.model.response.DruidSelectResponse;
import com.logicq.ngr.model.response.DruidTimeSeriesResponse;
import com.logicq.ngr.model.response.DruidTopNResponse;
import com.logicq.ngr.util.ObjectFactory;

/**
 * 
 * @author 611022163
 *
 * @param <T>
 */
public class DruidRestClient<T extends DruidBaseResponse> extends AbstractRestClient {

	private static final Logger logger = Logger.getLogger(DruidRestClient.class);
	private ObjectMapper mapper = ObjectFactory.getObjectMapper();

	public T[] druidResponse(String uri, Object request) throws Exception {

		// This is purpose of logging response it will disable after successful
		// test
		Object json = mapper.readValue(mapper.writeValueAsString(request), Object.class);
		System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(json));

		if (request instanceof DruidTopNRequest) {
			return (T[]) post(uri, request, DruidTopNResponse[].class);

		}
		if (request instanceof DruidGroupByRequest) {
			return (T[]) post(uri, request, DruidGroupByResponse[].class);

		}
		if (request instanceof DruidSelectRequest) {
			return (T[]) post(uri, request, DruidSelectResponse[].class);

		}
		if (request instanceof DruidTimeseriesRequest) {
			return (T[]) post(uri, request, DruidTimeSeriesResponse[].class);
		}

		return null;
	}

}