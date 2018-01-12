package com.logicq.ngr.service.inventory.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.logicq.ngr.constant.FeatureEnum;
import com.logicq.ngr.controllers.LoadApplicationData;
import com.logicq.ngr.dao.inventory.PropertyCacheDao;
import com.logicq.ngr.model.UserDetails;
import com.logicq.ngr.model.common.Attribute;
import com.logicq.ngr.model.inventory.AggregationMetaData;
import com.logicq.ngr.model.inventory.Duration;
import com.logicq.ngr.model.inventory.Entities;
import com.logicq.ngr.model.inventory.EntityAttribute;
import com.logicq.ngr.model.inventory.EntityType;
import com.logicq.ngr.model.inventory.FeatureProperty;
import com.logicq.ngr.model.inventory.FeatureSetup;
import com.logicq.ngr.model.inventory.Metrics;
import com.logicq.ngr.model.inventory.Property;
import com.logicq.ngr.model.inventory.ReportType;
import com.logicq.ngr.model.inventory.SamplingPeriodInterval;
import com.logicq.ngr.model.report.Dashboard;
import com.logicq.ngr.service.inventory.PropertyCacheService;
import com.logicq.ngr.service.report.UserTemplateService;
import com.logicq.ngr.vo.FeatureDetailsVO;
import com.logicq.ngr.vo.FeaturePropertyVO;
import com.logicq.ngr.vo.ReportPropertyVO;
import com.logicq.ngr.vo.ReportSubTypeVO;
import com.logicq.ngr.vo.ReportTypeVO;
import com.logicq.ngr.vo.UserTemplateVO;

@Service
public class PropertyCacheServiceImpl implements PropertyCacheService {

	@Autowired
	PropertyCacheDao propertyCacheDao;

	@Autowired
	UserTemplateService userTemplateService;

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ReportPropertyVO loadDefaultPropertiesAccordingToUser(UserDetails userDetails, String entityType)
			throws Exception {
		ReportPropertyVO propertyVo = new ReportPropertyVO();
		String deafultEntityType = setupDefaultEntityType(userDetails, entityType, propertyVo);
		ReportPropertyVO preloadProperty = LoadApplicationData.reportProperty.get(deafultEntityType);
		if (null == preloadProperty) {
			preloadProperty = new ReportPropertyVO();
			if (!StringUtils.isEmpty(deafultEntityType)) {
				List<Entities> entityList = populateEntity(preloadProperty, deafultEntityType);
				for (Entities entity : entityList) {
					populateEntityAttribute(preloadProperty, entity);
					populateEntityProperty(preloadProperty, entity);
					populateMetric(preloadProperty, entity);

				}
				populateDuration(preloadProperty);
				populateSamplingPeriod(preloadProperty, deafultEntityType);
				populateAggregation(preloadProperty, deafultEntityType);
				populateReportType(preloadProperty, deafultEntityType);
			}
		}
		if (null != preloadProperty) {
			preloadProperty.setDefaultEntityType(propertyVo.getDefaultEntityType());
			preloadProperty.setNormalEntityType(propertyVo.getNormalEntityType());
		}
		return preloadProperty;
	}

	private String setupDefaultEntityType(UserDetails userDetails, String entityType, ReportPropertyVO propertyVo)
			throws Exception {
		String deafultEntityType = null;
		if (null == entityType || StringUtils.isEmpty(entityType)) {
			Map<String, Object> entityMap = getDefaultDataAcessType(userDetails);
			for (Map.Entry<String, Object> entity : entityMap.entrySet()) {
				if (entity.getValue() instanceof List) {
					propertyVo.setNormalEntityType((List<Attribute>) entity.getValue());
				} else {
					Attribute entityAttribute = (Attribute) entity.getValue();
					propertyVo.setDefaultEntityType(entityAttribute);
					deafultEntityType = entityAttribute.getName();
				}
			}

		} else {
			deafultEntityType = entityType;
		}
		return deafultEntityType;
	}

	private void populateReportType(ReportPropertyVO propertyVo, String deafultEntityType) {
		List<ReportType> reportList = (List<ReportType>) propertyCacheDao.loadPropertyAccordingToType(ReportType.class,
				deafultEntityType);
		if (null != reportList && !reportList.isEmpty()) {
			List<ReportTypeVO> reportTypeList = new ArrayList<>();
			Map<String, ReportTypeVO> reportTypeMap = new HashMap<>();
			reportList.forEach((report) -> {
				ReportTypeVO reportTypeVO = reportTypeMap.get(report.getApplicabletype());
				if (null == reportTypeVO) {
					reportTypeVO = new ReportTypeVO();
					reportTypeVO.setId(report.getId());
					reportTypeVO.setImagePath(report.getImagePath());
					reportTypeVO.setName(report.getApplicabletype());
					reportTypeVO.setReportSubType(new ArrayList<ReportSubTypeVO>());
					reportTypeMap.put(report.getApplicabletype(), reportTypeVO);
					reportTypeList.add(reportTypeVO);
				} else {
					ReportSubTypeVO reportSubTypeVO = new ReportSubTypeVO();
					reportSubTypeVO.setId(report.getId());
					reportSubTypeVO.setImagePath(report.getImagePath());
					reportSubTypeVO.setName(report.getSubType());
					reportTypeVO.getReportSubType().add(reportSubTypeVO);
				}

			});
			propertyVo.getReportType().put(deafultEntityType, reportTypeList);
		}
	}

	private void populateAggregation(ReportPropertyVO propertyVo, String deafultEntityType) {
		List<AggregationMetaData> aggregationList = (List<AggregationMetaData>) propertyCacheDao
				.loadPropertyAccordingToType(AggregationMetaData.class, deafultEntityType);
		if (null != aggregationList && !aggregationList.isEmpty()) {
			List<Attribute> aggregationtype = new ArrayList<>();
			aggregationList.forEach((aggregation) -> {
				Attribute attribute = new Attribute();
				attribute.setName(aggregation.getName());
				attribute.setDisplayName(aggregation.getDisplayName());
				attribute.setId(String.valueOf(aggregation.getId()));
				aggregationtype.add(attribute);
			});
			propertyVo.getAggregationtype().put(deafultEntityType, aggregationtype);
		}
	}

	private void populateSamplingPeriod(ReportPropertyVO propertyVo, String deafultEntityType) {
		List<SamplingPeriodInterval> samplingPeriodList = (List<SamplingPeriodInterval>) propertyCacheDao
				.loadPropertyAccordingToType(SamplingPeriodInterval.class, deafultEntityType);
		if (null != samplingPeriodList && !samplingPeriodList.isEmpty()) {
			List<Attribute> samplingPeriodInterval = new ArrayList<>();
			samplingPeriodList.forEach((samplingperiod) -> {
				Attribute attribute = new Attribute();
				attribute.setName(samplingperiod.getName());
				attribute.setDisplayName(samplingperiod.getDisplayName());
				attribute.setId(String.valueOf(samplingperiod.getId()));
				samplingPeriodInterval.add(attribute);
			});
			propertyVo.setSamplingPeriodInterval(samplingPeriodInterval);
		}
	}

	private void populateDuration(ReportPropertyVO propertyVo) {
		// This both common feature for all
		List<Duration> durationList = (List<Duration>) propertyCacheDao.loadProperty(Duration.class);
		if (null != durationList && !durationList.isEmpty()) {
			List<Attribute> durationlist = new ArrayList<>();
			durationList.forEach((duration) -> {
				Attribute attribute = new Attribute();
				attribute.setName(duration.getName());
				attribute.setDisplayName(duration.getDisplayName());
				attribute.setId(String.valueOf(duration.getId()));
				durationlist.add(attribute);
			});
			propertyVo.setDuration(durationlist);
		}
	}

	private void populateMetric(ReportPropertyVO propertyVo, Entities entity) {
		List<Metrics> metricList = (List<Metrics>) propertyCacheDao.loadPropertyAccordingToType(Metrics.class,
				entity.getId());
		if (null != metricList && !metricList.isEmpty()) {
			List<Attribute> metrics = new ArrayList<>();
			metricList.forEach((metric) -> {
				Attribute attribute = new Attribute();
				attribute.setName(metric.getName());
				attribute.setDisplayName(metric.getDisplayname());
				attribute.setId(String.valueOf(metric.getId()));
				metrics.add(attribute);
			});
			propertyVo.getMetrics().put(entity.getId(), metrics);
		}
	}

	private void populateEntityProperty(ReportPropertyVO propertyVo, Entities entity) {
		List<Property> propertyList = (List<Property>) propertyCacheDao.loadPropertyAccordingToType(Property.class,
				entity.getId());
		if (null != propertyList && !propertyList.isEmpty()) {
			List<Attribute> properties = new ArrayList<>();
			propertyList.forEach((property) -> {
				Attribute attribute = new Attribute();
				attribute.setName(property.getName());
				attribute.setDisplayName(property.getDisplayName());
				attribute.setId(property.getId());
				properties.add(attribute);
			});
			propertyVo.getProperty().put(entity.getId(), properties);
		}
	}

	private void populateEntityAttribute(ReportPropertyVO propertyVo, Entities entity) {
		List<EntityAttribute> entityAttributeListList = (List<EntityAttribute>) propertyCacheDao
				.loadPropertyAccordingToType(EntityAttribute.class, entity.getId());
		if (null != entityAttributeListList && !entityAttributeListList.isEmpty()) {
			List<Attribute> legends = new ArrayList<>();
			entityAttributeListList.forEach((property) -> {
				Attribute attribute = new Attribute();
				attribute.setName(property.getName());
				attribute.setDisplayName(property.getDisplayName());
				attribute.setId(property.getId());
				legends.add(attribute);
			});
			propertyVo.getLegends().put(entity.getId(), legends);
		}
	}

	private List<Entities> populateEntity(ReportPropertyVO propertyVo, String deafultEntityType) {
		// Helps to perepare property
		List<Entities> entityList = (List<Entities>) propertyCacheDao.loadPropertyAccordingToType(Entities.class,
				deafultEntityType);
		if (null != entityList && !entityList.isEmpty()) {
			List<Attribute> entities = new ArrayList<>();
			entityList.forEach((entity) -> {
				Attribute attribute = new Attribute();
				attribute.setName(entity.getName());
				attribute.setDisplayName(entity.getDisplayName());
				attribute.setId(String.valueOf(entity.getId()));
				entities.add(attribute);
			});
			propertyVo.setEntities(entities);
		}
		return entityList;
	}

	// Need to Setup Default report acess type
	private Map<String, Object> getDefaultDataAcessType(UserDetails userDetails) throws Exception {
		Map<String, Object> resultMap = new HashMap<>();

		// List<Dashboard> dashboardList=userDetails.getDashboards();
		// added below code becaz above code is getting repeated dashboard for
		// several time of UserDetail.
		UserTemplateVO userTemplateVO = new UserTemplateVO();
		userTemplateVO.setUserId(userDetails.getId());
		List<Dashboard> dashboardList = userTemplateService.getUserDashboards(userTemplateVO);

		if (null != dashboardList && !dashboardList.isEmpty()) {
			List<Attribute> normalAttribute = new ArrayList<>();
			for (Dashboard dashboard : dashboardList) {
				if (!com.logicq.ngr.constant.ReportType.DASH_BOARD.getValue().equals(dashboard.getType())) {
					Object defaultAcess = resultMap.get(FeatureEnum.DEFAULT.getValue());
					if (null == defaultAcess) {
						Attribute defaultAttr = new Attribute();
						defaultAttr.setName(dashboard.getType());
						defaultAttr.setDisplayName(dashboard.getType().toUpperCase());
						defaultAttr.setId(dashboard.getType().toLowerCase());
						resultMap.put(FeatureEnum.DEFAULT.getValue(), defaultAttr);
					} else {
						Attribute normal = new Attribute();
						normal.setName(dashboard.getType());
						normal.setDisplayName(dashboard.getType().toUpperCase());
						normal.setId(dashboard.getType().toLowerCase());
						normalAttribute.add(normal);
					}
				}
			}
			if (null != normalAttribute && !normalAttribute.isEmpty()) {
				resultMap.put(FeatureEnum.NORMAL.getValue(), normalAttribute);
			}
		}

		return resultMap;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Map<String, String> loadAttributesFromProperty() {
		Map<String, String> attributesMap = new HashMap<String, String>();
		List<Property> attributeList = (List<Property>) propertyCacheDao.loadProperty();
		if (null != attributeList && !attributeList.isEmpty()) {
			for (Property row : attributeList) {
				attributesMap.put(row.getId(), row.getName());
			}

		}

		return attributesMap;

	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public FeatureDetailsVO getAllFeatureProperty() {
		FeatureDetailsVO featureDetails = new FeatureDetailsVO();
		List<FeatureSetup> dasboardList = (List<FeatureSetup>) propertyCacheDao.loadProperty(FeatureSetup.class);
		List<FeatureProperty> featureList = (List<FeatureProperty>) propertyCacheDao
				.loadProperty(FeatureProperty.class);
		List<FeaturePropertyVO> featurePropertyList = new ArrayList<>();
		List<FeaturePropertyVO> mgmtPropertyList = new ArrayList<>();
		dasboardList.forEach((dash) -> {
			FeaturePropertyVO fetaureProperty = new FeaturePropertyVO();
			fetaureProperty.setDisplayName(dash.getDisplayName());
			fetaureProperty.setId(String.valueOf(dash.getId()));
			fetaureProperty.setName(dash.getName());
			featureList.forEach((featurprop) -> {
				Attribute attribute = new Attribute();
				attribute.setDisplayName(featurprop.getDisplayName());
				attribute.setId(featurprop.getId());
				attribute.setName(featurprop.getName());
				if (featurprop.getType().equals(dash.getApplicableto())) {
					if ("report".equals(dash.getApplicableto())) {
						fetaureProperty.getReport().add(attribute);
					}
					if ("dashboard".equals(dash.getApplicableto())) {
						fetaureProperty.getDashboard().add(attribute);
					}
					if ("user".equals(dash.getApplicableto()) || "profile".equals(dash.getApplicableto())
							|| "data".equals(dash.getApplicableto())) {
						// This code added to handle default acess which need to
						// setup in DB also . Currently random view type is
						// default
						if ("data".equals(dash.getApplicableto()) && null == fetaureProperty.getDefaultacess()) {
							fetaureProperty.setDefaultacess(attribute);
						}

						fetaureProperty.getFeatures().add(attribute);
					}
				}
			});
			if ("user".equals(dash.getApplicableto()) || "profile".equals(dash.getApplicableto())
					|| "data".equals(dash.getApplicableto())) {
				mgmtPropertyList.add(fetaureProperty);
			} else {
				featurePropertyList.add(fetaureProperty);
			}
		});

		featureDetails.setManagement(mgmtPropertyList);
		featureDetails.setReportFeatureList(featurePropertyList);
		return featureDetails;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<EntityType> getAllEntityTypeList() {
		return (List<EntityType>) propertyCacheDao.loadProperty(EntityType.class);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<Attribute> loadSpecificProperties(Attribute queryAttribute, Attribute queryEntity,
			String fillterPattern) {
		List<Attribute> attributes = new ArrayList<>();

		List<Object[]> rows = (List<Object[]>) propertyCacheDao.getAttributeForSpecificColum(queryEntity.getName(),
				queryAttribute.getName(), fillterPattern);

		if (null != rows) {
			for (Object row : rows) {
				Attribute attr = new Attribute();
				attr.setId(String.valueOf(row));
				attr.setDisplayName(String.valueOf(row).toUpperCase());
				attr.setName(String.valueOf(row));
				attributes.add(attr);
			}
		}
		return attributes;
	}

	@Override
	public Map<String, Attribute> getFeatureSetup() throws Exception {
		Map<String, Attribute> fatureMap = new HashMap<>();
		List<FeatureSetup> featureDetaiList = (List<FeatureSetup>) propertyCacheDao.loadProperty(FeatureSetup.class);
		for (FeatureSetup feature : featureDetaiList) {
			Attribute attribute = new Attribute();
			attribute.setDisplayName(feature.getDisplayName());
			attribute.setName(feature.getName());
			attribute.setId(String.valueOf(feature.getId()));
			fatureMap.put(feature.getName(), attribute);
		}
		return fatureMap;
	}

	@Override
	public Map<String, Object> getPropertyValues(Map<String, Object> filterColumsValues, List<Attribute> properties,
			Attribute entity) {
		// TODO Auto-generated method stub
		return null;
	}

}
