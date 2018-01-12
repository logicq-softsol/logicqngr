package com.logicq.ngr.common.helper;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.logicq.ngr.client.druid.DruidRestClient;
import com.logicq.ngr.common.constant.Constant;
import com.logicq.ngr.common.constant.DateConstant;
import com.logicq.ngr.common.property.CommonAttribute;
import com.logicq.ngr.constant.AggregationEnum;
import com.logicq.ngr.constant.Constants;
import com.logicq.ngr.constant.ReportType;
import com.logicq.ngr.controllers.LoadApplicationData;
import com.logicq.ngr.model.AggregationType;
import com.logicq.ngr.model.DatasourceDetail;
import com.logicq.ngr.model.DruidTemplate;
import com.logicq.ngr.model.common.Attribute;
import com.logicq.ngr.model.common.Metric;
import com.logicq.ngr.model.druid.request.DruidGroupByRequest;
import com.logicq.ngr.model.druid.request.DruidSelectRequest;
import com.logicq.ngr.model.druid.request.DruidTimeseriesRequest;
import com.logicq.ngr.model.druid.request.DruidTopNRequest;
import com.logicq.ngr.model.druid.request.ReportRequest;
import com.logicq.ngr.model.druid.request.DruidBaseRequest.DruidBaseRequestBuilder;
import com.logicq.ngr.model.response.DruidGroupByResponse;
import com.logicq.ngr.model.response.DruidSelectResponse;
import com.logicq.ngr.model.response.DruidTimeSeriesResponse;
import com.logicq.ngr.model.response.DruidTopNResponse;
import com.logicq.ngr.model.response.Response;
import com.logicq.ngr.service.AggregationService;
import com.logicq.ngr.service.DatasourceService;
import com.logicq.ngr.service.GranularityMetadataService;
import com.logicq.ngr.service.druid.DruidTemplateService;
import com.logicq.ngr.service.inventory.PropertyCacheService;
import com.logicq.ngr.util.DruidRequestHelper;
import com.logicq.ngr.util.ObjectFactory;

@Component
public class DruidHelper {

	@Autowired
	CommonAttribute commonAttribute;

	@Autowired
	DruidTemplateService druidTemplateService;

	@Autowired
	DatasourceService datasourceService;

	@Autowired
	GranularityMetadataService granularityMetadataService;

	@Autowired
	AggregationService aggregationService;

	@Autowired
	PropertyCacheService propertyCacheService;

	private DruidRestClient<?> druidRestClient = ObjectFactory.getDruidRestClient();

	public String getDruidURI() {
		return LoadApplicationData.siteOptionMap.get("druidUri");
	}

	/**
	 * Method to generate date string from inputInterval
	 * 
	 * @param interval
	 *            like "current 30 minutes", "previous 1 hour", "last 1 day"
	 * @return interval string like
	 *         2016-10-14T00:00:00.000Z/2016-10-15T00:00:00.000Z
	 * @throws Exception
	 */
	public String generateIntervalString(String interval) throws Exception {
		LocalDateTime currentDateTime = LocalDateTime.now(ZoneId.of(commonAttribute.getZoneId()));

		if (interval.toLowerCase().contains(Constant.CURRENT)) {
			return DateHelper.generateDruidIntervalForCurrent(currentDateTime, interval);
		} else if (interval.toLowerCase().contains(Constant.PREVIOUS)) {
			return DateHelper.generateDruidIntervalForPrevious(currentDateTime, interval);
		} else if (interval.toLowerCase().contains(Constant.LAST)) {
			return DateHelper.generateDruidIntervalForLast(currentDateTime, interval);
		} else if (interval.equalsIgnoreCase(DateConstant.TODAY) || interval.equalsIgnoreCase(DateConstant.YESTERDAY)) {
			return DateHelper.generateDruidIntervalForYesterdayToday(currentDateTime, interval);
		}

		return interval;
	}

	/**
	 * 
	 * @param policyTaskType
	 *            like 'daily, weekly etc'
	 * @return
	 * @throws Exception
	 */
	public String generateDateIntervalFromPolicy(Map<String, Object> taskParamValMap) throws Exception {
		LocalDateTime currentDateTime = LocalDateTime.now(ZoneId.of(commonAttribute.getZoneId()));
		int month;
		int dayOfMonth;
		int hour;
		int minute;

		if (!StringUtils.isEmpty(taskParamValMap.get("taskExecutionDate")))
			dayOfMonth = Integer.valueOf((String) taskParamValMap.get("taskExecutionDate"));
		else
			dayOfMonth = currentDateTime.getDayOfMonth();

		if (!StringUtils.isEmpty(taskParamValMap.get("taskExecutionMonth")))
			month = Integer.valueOf((String) taskParamValMap.get("taskExecutionMonth"));
		else
			month = currentDateTime.getMonthValue();

		if (!StringUtils.isEmpty(taskParamValMap.get("taskExecutionTime"))) {
			String time = (String) taskParamValMap.get("taskExecutionTime");
			String[] inputTimeSplit = time.split(":");
			hour = Integer.valueOf(inputTimeSplit[0]);
			minute = Integer.valueOf(inputTimeSplit[1]);
		} else {
			hour = currentDateTime.getHour();
			minute = currentDateTime.getMinute();
		}

		currentDateTime = currentDateTime.of(currentDateTime.getYear(), month, dayOfMonth, hour, minute,
				currentDateTime.getSecond(), currentDateTime.getNano());
		return DateHelper.generateDateIntervalFromPolicy(currentDateTime, (String) taskParamValMap.get("taskType"));
	}

	/**
	 * 
	 * @param reportRequest
	 * @param event
	 * @return
	 */
	public void prepareTimeseriesResponse(List<Response> responseList, ReportRequest reportRequest,
			DruidBaseRequestBuilder druidBaseRequestBuilder) throws Exception {
		DruidTimeseriesRequest druidTimeseriesRequest = new DruidTimeseriesRequest.TimeseriesRequestBuilder(
				druidBaseRequestBuilder, reportRequest).build();
		DruidTimeSeriesResponse[] druidTimeSeriesResponseArray = (DruidTimeSeriesResponse[]) druidRestClient
				.druidResponse(getDruidURI(), druidTimeseriesRequest);
		for (DruidTimeSeriesResponse druidTimeSeriesResponse : druidTimeSeriesResponseArray) {
			Response response = new Response();
			List<Map<String, Object>> events = new ArrayList<>();
			Map<String, Object> event = druidTimeSeriesResponse.getResult();
			event.put(Constant.TIMESTAMP, druidTimeSeriesResponse.getTimestamp());

			// Enriching druid response
			event = getRespWithPropertiesValues(reportRequest, event);

			events.add(event);
			response.setEvents(events);
			responseList.add(response);
		}
	}

	/**
	 * 
	 * @param reportRequest
	 * @param event
	 * @return
	 */
	public void prepareSelectResponse(List<Response> responseList, ReportRequest reportRequest,
			DruidBaseRequestBuilder druidBaseRequestBuilder) throws Exception {
		DruidSelectRequest druidSelectRequest = new DruidSelectRequest.SelectRequestBuilder(druidBaseRequestBuilder,
				reportRequest).build();
		DruidSelectResponse[] druidSelectResponseArray = (DruidSelectResponse[]) druidRestClient
				.druidResponse(getDruidURI(), druidSelectRequest);
		for (DruidSelectResponse druidSelectResponse : druidSelectResponseArray) {
			Response response = new Response();
			response.setPagingIdentifiers(
					(Map<String, Object>) druidSelectResponse.getResult().get(Constant.PAGING_IDENTIFIERS));
			List<Map<String, Object>> events = (List<Map<String, Object>>) druidSelectResponse.getResult()
					.get(Constant.EVENTS);
			events.forEach(event -> {
				event.putAll((Map<String, Object>) event.get(Constant.EVENT));

				// Enriching druid response
				event = getRespWithPropertiesValues(reportRequest, event);

				event.remove(Constant.EVENT);
			});
			response.setEvents(events);
			responseList.add(response);
		}
	}

	/**
	 * 
	 * @param reportRequest
	 * @param event
	 * @return
	 */
	public void prepareGroupByResponse(List<Response> responseList, ReportRequest reportRequest,
			DruidBaseRequestBuilder druidBaseRequestBuilder) throws Exception {
		DruidGroupByRequest druidGroupByRequest = new DruidGroupByRequest.GroupByRequestBuilder(druidBaseRequestBuilder,
				reportRequest).build();
		DruidGroupByResponse[] druidGroupByResponseArray = (DruidGroupByResponse[]) druidRestClient
				.druidResponse(getDruidURI(), druidGroupByRequest);
		for (DruidGroupByResponse druidGroupByResponse : druidGroupByResponseArray) {
			Response response = new Response();
			List<Map<String, Object>> events = new ArrayList<>();
			Map<String, Object> event = druidGroupByResponse.getEvent();
			event.put(Constant.TIMESTAMP, druidGroupByResponse.getTimestamp());

			// Enriching druid response
			event = getRespWithPropertiesValues(reportRequest, event);

			events.add(event);
			response.setEvents(events);
			responseList.add(response);
		}
	}

	/**
	 * 
	 * @param reportRequest
	 * @param event
	 * @return
	 */
	public void prepareTopNResponse(List<Response> responseList, String metricName, ReportRequest reportRequest,
			DruidBaseRequestBuilder druidBaseRequestBuilder) throws Exception {
		DruidTopNRequest topnRequest = new DruidTopNRequest.TopNRequestBuilder(metricName, druidBaseRequestBuilder,
				reportRequest).build();
		DruidTopNResponse[] druidTopNResponseArray = (DruidTopNResponse[]) druidRestClient.druidResponse(getDruidURI(),
				topnRequest);

		for (DruidTopNResponse druidTopNResponse : druidTopNResponseArray) {
			Response response = new Response();
			List<Map<String, Object>> resultList = druidTopNResponse.getResult();
			resultList.forEach(resultMap -> {
				resultMap.put(Constant.TIMESTAMP, druidTopNResponse.getTimestamp());

				// Enriching druid response
				resultMap = getRespWithPropertiesValues(reportRequest, resultMap);
			});
			response.setEvents(resultList);
			responseList.add(response);
		}
	}

	/**
	 * 
	 * @param reportRequest
	 * @return
	 * @throws Exception
	 */
	public List<Response> prepareDruidResponse(ReportRequest reportRequest) throws Exception {

		DruidTemplate druidTemplate = null;

		DatasourceDetail dataSource = datasourceService.fetchDataSourceAccordingToFilter(
				DruidRequestHelper.fetchUnitFromSamplingPeriod(reportRequest.getSamplingPeriod()),
				reportRequest.getConfiguration().getEntity().getName());
		String granularity = granularityMetadataService
				.getGranularity(DruidRequestHelper.fetchDruidUnitFromSamplingPeriod(reportRequest.getSamplingPeriod()));
		List<Response> responseList = new ArrayList<>();
		if (!StringUtils.isEmpty(reportRequest.getSamplingPeriod())
				&& ReportType.REALTIME.getValue().equals(reportRequest.getSamplingPeriod())) {
			String reportType = reportRequest.getConfiguration().getType() + Constants.UnderScore
					+ ReportType.REALTIME.getValue();
			druidTemplate = druidTemplateService.getTemplate(reportType);
		} else {
			druidTemplate = druidTemplateService.getTemplate(reportRequest.getConfiguration().getType());
		}

		DruidBaseRequestBuilder druidBaseRequestBuilder = new DruidBaseRequestBuilder(
				druidTemplate.getDruidQueryTemplate().getQueryType(), dataSource.getDatasourceName(), granularity,
				reportRequest.getIntervals());

		String metricName = null;
		List<Metric> metrics = reportRequest.getConfiguration().getMetrics();
		if (null != metrics && !metrics.isEmpty()) {
			for (Metric metric : metrics) {
				if (!StringUtils.isEmpty(metric.getAggregationType())) {
					AggregationType aggreationType = aggregationService.fetchAggregationAccordingToFilter(
							metric.getAggregationType(), dataSource.getDatasourceName());
					if (!AggregationEnum.AVERAGE.getValue().equals(metric.getAggregationType())) {
						metric.setAggregationType(aggreationType.getDruidAggregationType());
					}
					// Temporary fix for handel case
					if (AggregationEnum.COUNT.getValue().equals(metric.getAggregationType().toUpperCase())) {
						metric.setAggregationType(aggreationType.getDruidAggregationType());
						metric.setDisplayName(AggregationEnum.COUNT.getValue());
						metric.setId(AggregationEnum.COUNT.getValue());
						metric.setName(AggregationEnum.COUNT.getValue());
					}
				}
				metricName = metric.getName();
			}
		}
		
		if (ReportType.TOPN.getValue().equalsIgnoreCase(druidTemplate.getDruidQueryTemplate().getQueryType())) {
			prepareTopNResponse(responseList, metricName, reportRequest, druidBaseRequestBuilder);
		} else if (ReportType.GROUP_BY.getValue()
				.equalsIgnoreCase(druidTemplate.getDruidQueryTemplate().getQueryType())) {
			prepareGroupByResponse(responseList, reportRequest, druidBaseRequestBuilder);
		} else if (Constant.SELECT.equalsIgnoreCase(druidTemplate.getDruidQueryTemplate().getQueryType())) {
			prepareSelectResponse(responseList, reportRequest, druidBaseRequestBuilder);
		} else if (Constant.TIMESERIES.equalsIgnoreCase(druidTemplate.getDruidQueryTemplate().getQueryType())) {
			prepareTimeseriesResponse(responseList, reportRequest, druidBaseRequestBuilder);
		}
		return responseList;
	}

	/**
	 * 
	 * @param reportRequest
	 * @param event
	 * @return
	 */
	private Map<String, Object> getRespWithPropertiesValues(ReportRequest reportRequest, Map<String, Object> event) {
		Map<String, Object> filterColums = new HashMap<>();
		List<Attribute> properties = reportRequest.getConfiguration().getProperties();
		Attribute entity = reportRequest.getConfiguration().getEntity();
		List<Attribute> legends = reportRequest.getConfiguration().getLegends();
		legends.forEach((legend) -> {
			String columValue = String.valueOf(event.get(legend.getId()));
			filterColums.put(legend.getId(), columValue);
		});
		if (properties != null && !properties.isEmpty()) {
			Map<String, Object> propertResults = propertyCacheService.getPropertyValues(filterColums, properties,
					entity);
			if (null != propertResults && !propertResults.isEmpty()) {
				event.putAll(propertResults);
			}
		}

		return event;
	}
}
