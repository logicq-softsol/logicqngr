package com.logicq.ngr.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.logicq.ngr.common.exception.BusinessException;
import com.logicq.ngr.common.helper.RandomNumberHelper;
import com.logicq.ngr.dao.AbstractDAO;
import com.logicq.ngr.dao.UserAdminDao;
import com.logicq.ngr.dao.UserTemplateDao;
import com.logicq.ngr.model.UserDetails;
import com.logicq.ngr.model.report.UserTemplate;
import com.logicq.ngr.model.report.UserTemplateKey;
import com.logicq.ngr.vo.UserTemplateVO;

@Repository
public class UserTemplateDaoImpl extends AbstractDAO<UserTemplate> implements UserTemplateDao {

	private static final long serialVersionUID = 9185357398907602569L;

	@Autowired
	UserAdminDao userAdminDao;

	ObjectMapper objectMapper = new ObjectMapper();

	/**
	 * This method is used to search the data from User_Template Table of MySql
	 * DataBase based on the TemplateId, DashBoardId, QueryType ,UserId values
	 * of UserTemplate class.
	 * 
	 * @param UserTemplate
	 * @return List<UserTemplate>.
	 * @throws Exception
	 */
	public List<UserTemplate> searchUserTemplate(UserTemplateVO userTemplateVO) throws Exception {

		StringBuilder selectQuery = new StringBuilder();
		selectQuery.append("from UserTemplate ut ");

		StringBuilder whereQuery = new StringBuilder();

		if (null != userTemplateVO) {
			whereQuery.append("where ");
			if (!StringUtils.isEmpty(userTemplateVO.getUserId())) {
				whereQuery.append("ut.userTemplateKey.userDetails.id='").append(userTemplateVO.getUserId() + "'");
			} else {
				throw new Exception("userId can not be Null");
			}
			if (!StringUtils.isEmpty(userTemplateVO.getDashboardId())) {
				whereQuery.append("and ut.userTemplateKey.dashboardId='").append(userTemplateVO.getDashboardId() + "'");
			}

			/*
			 * if (!StringUtils.isEmpty(userTemplateVO.getTemplateId())) {
			 * whereQuery.append("and ut.userTemplateKey.templateId='"
			 * ).append(userTemplateVO.getTemplateId() + "'"); }
			 */
			if (!StringUtils.isEmpty(userTemplateVO.getUserTemplateId())) {
				whereQuery.append("and ut.userTemplateKey.userTemplateId='")
						.append(userTemplateVO.getUserTemplateId() + "'");
			}
			selectQuery.append(whereQuery);
		}
		return executeQuery(selectQuery.toString());

	}

	public UserTemplate getUserTemplate(UserTemplateVO userTemplateVO) throws Exception {
		StringBuilder selectQuery = new StringBuilder();
		selectQuery.append("from UserTemplate ut ");

		StringBuilder whereQuery = new StringBuilder();

		if (null != userTemplateVO) {
			whereQuery.append("where ");
			if (userTemplateVO.getDashboardId() != null) {
				whereQuery.append("ut.userTemplateKey.dashboardId='").append(userTemplateVO.getDashboardId() + "'");
			} else {
				throw new Exception("dashBoardId can not be Null");
			}

			/*
			 * if (userTemplateVO.getTemplateId() != null) { whereQuery.append(
			 * "and ut.userTemplateKey.templateId='"
			 * ).append(userTemplateVO.getTemplateId() + "'"); } else { throw
			 * new Exception("TemplateId can not be Null"); }
			 */

			if (userTemplateVO.getUserTemplateId() != null) {
				whereQuery.append("and ut.userTemplateKey.userTemplateId='")
						.append(userTemplateVO.getUserTemplateId() + "'");
			} else {
				throw new Exception("UserTemplateId can not be Null");
			}
			selectQuery.append(whereQuery);
		}
		return executeQueryForUniqueRecord(selectQuery.toString());
	}

	/**
	 * This method is used to edit JsonString Field of MySql User_Template Table
	 * based on the TemplateId, DashBoardId ,UserId values of UserTemplateVO
	 * class.
	 * 
	 * @param UserTemplateVO
	 * @return null.
	 * @throws Exception
	 */
	public void editUserTemplate(UserTemplateVO userTemplateVO) throws Exception {
		List<UserTemplate> listUserTemplate = searchUserTemplate(userTemplateVO);
		UserTemplate fetchUserTemplate = listUserTemplate.get(0);
		Map<String, Object> fetchUserTemplateMap = objectMapper.readValue(new String(fetchUserTemplate.getJsonString()),
				new TypeReference<Map<String, Object>>() {
				});
		fetchUserTemplateMap.putAll(userTemplateVO.getJsonString());
		fetchUserTemplate
				.setJsonString(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsBytes(fetchUserTemplateMap));
		update(fetchUserTemplate);
	}

	/**
	 * This method is used to save data to User_Template Table of MySql DataBase
	 * based on the object of UserTemplateVO class.
	 * 
	 * @param UserTemplateVO
	 * @return null.
	 * @throws Exception
	 */
	public void saveUserTemplate(UserTemplateVO userTemplateVO) throws Exception {
		UserTemplate result = convertToUserTemplate(userTemplateVO);
		save((UserTemplate) result);
	}

	/**
	 * This method is used to delete data from User_Template Table of MySql
	 * DataBase, based on the id field of UserTemplate class.
	 * 
	 * @param UserTemplate
	 * @return null.
	 * @throws Exception
	 */
	public void deleteUserTemplate(UserTemplate userTemplate) throws Exception {
		StringBuilder selectQuery = new StringBuilder();
		selectQuery.append("from UserTemplate ut ");

		StringBuilder whereQuery = new StringBuilder();

		if (null != userTemplate) {
			whereQuery.append("where ");

			if (userTemplate.getUserTemplateKey().getDashboardId() != null) {
				whereQuery.append("ut.userTemplateKey.dashboardId='")
						.append(userTemplate.getUserTemplateKey().getDashboardId() + "'");
			} else {
				throw new Exception("dashBoardId can not be Null");
			}

			// if (userTemplate.getUserTemplateKey().getTemplateId() != null) {
			// whereQuery.append("and ut.userTemplateKey.templateId='")
			// .append(userTemplate.getUserTemplateKey().getTemplateId() + "'");
			// } else {
			// throw new Exception("templateId can not be Null");
			// }

			if (userTemplate.getUserTemplateKey().getUserTemplateId() != null) {
				whereQuery.append("and ut.userTemplateKey.userTemplateId='")
						.append(userTemplate.getUserTemplateKey().getUserTemplateId() + "'");
			} else {
				throw new Exception("userTemplateId can not be Null");
			}

			selectQuery.append(whereQuery);
		}
		userTemplate = executeQueryForUniqueRecord(selectQuery.toString());
		delete(userTemplate);
	}

	/**
	 * This method is used to convert UserTemplateVO object to UserTemplate
	 * object. This method is called by saveUserTemplate(UserTemplateVO
	 * userTemplateVO) because from UI we get JsonString field of UserTemplateVO
	 * as a map, but we need to convert that map to Json String and store into
	 * DB.
	 * 
	 * @param UserTemplateVO
	 * @return Object of UserTemplate.
	 * @throws Exception
	 */
	public UserTemplate convertToUserTemplate(UserTemplateVO userTemplateVO) throws Exception {
		UserTemplate result = new UserTemplate();
		if (null == result.getUserTemplateKey()) {
			result.setUserTemplateKey(new UserTemplateKey());
		}
		if (userTemplateVO.getJsonString() != null) {
			result.setJsonString(objectMapper.writeValueAsBytes(userTemplateVO.getJsonString()));
		}
		if (userTemplateVO.getDashboardId() != null) {
			result.getUserTemplateKey().setDashboardId(userTemplateVO.getDashboardId());
		}
		if (userTemplateVO.getReportType() != null) {
			result.setReportType(userTemplateVO.getReportType());
		}
		if (userTemplateVO.getUserName() != null) {
			UserDetails userDetails = userAdminDao.getUser(userTemplateVO.getUserName());
			result.getUserTemplateKey().setUserDetails(userDetails);
		}
		result.getUserTemplateKey().setUserTemplateId(RandomNumberHelper.generateRandomAlphaNumericString());
		return result;
	}

	@Override
	public List<UserTemplate> getUserTemplatesOfDashboard(String dashboardId) {

		/*
		 * String query = "from UserTemplate ut where  ut.reportType='" +
		 * reportType + "' and ut.dashBoardId='" + dashboardId + "'";
		 */

		String query = "from UserTemplate ut where ut.userTemplateKey.dashboardId='" + dashboardId + "'";

		return executeQuery(query);
	}

	@Override
	public List<UserTemplate> getAllUserTemplate() {
		/*
		 * String query = "from UserTemplate"; return executeQuery(query);
		 */

		return (List<UserTemplate>) loadClass(UserTemplate.class);
	}

	@Override
	public void saveUserTemplate(UserTemplate userTemplate) {
		save(userTemplate);
	}

	@Override
	public List<UserTemplateVO> fetchUserTemplate(UserTemplateVO userTemplateVO) {
		StringBuilder selectQuery = new StringBuilder();
		selectQuery.append("from UserTemplate ut ");
		StringBuilder whereQuery = new StringBuilder();

		if (null != userTemplateVO) {
			whereQuery.append("where ");
			if (userTemplateVO.getDashboardId() != null) {
				whereQuery.append("ut.userTemplateKey.dashboardId='").append(userTemplateVO.getDashboardId() + "'");
			}
			/*
			 * if (!StringUtils.isEmpty(userTemplateVO.getTemplateId())) {
			 * whereQuery.append("and ut.userTemplateKey.templateId='"
			 * ).append(userTemplateVO.getTemplateId() + "'"); }
			 */
			if (!StringUtils.isEmpty(userTemplateVO.getUserTemplateId())) {
				whereQuery.append("and ut.userTemplateKey.userTemplateId='")
						.append(userTemplateVO.getUserTemplateId() + "'");
			}
			selectQuery.append(whereQuery);
		}
		List<UserTemplate> resultTemplate = executeQuery(selectQuery.toString());

		List<UserTemplateVO> resultUserTemplateVo = new ArrayList<UserTemplateVO>();

		resultTemplate.forEach(template -> {
			try {
				resultUserTemplateVo.add(convertToUserTemplateVO(template));
			} catch (Exception ex) {
				// need to add exception Handling
			}
		});
		return resultUserTemplateVo;
	}

	public UserTemplateVO convertToUserTemplateVO(UserTemplate userTemplate) throws Exception {
		UserTemplateVO result = new UserTemplateVO();
		if (userTemplate.getJsonString() != null) {
			Map<String, Object> dbUserTemplateMap = objectMapper.readValue(userTemplate.getJsonString(),
					new TypeReference<Map<String, Object>>() {
					});
			result.setJsonString(dbUserTemplateMap);
		}
		// if (userTemplate.getUserTemplateKey().getTemplateId() != null) {
		// result.setTemplateId(userTemplate.getUserTemplateKey().getTemplateId());
		// }
		if (userTemplate.getUserTemplateKey().getDashboardId() != null) {
			result.setDashboardId(userTemplate.getUserTemplateKey().getDashboardId());
		}
		if (userTemplate.getReportType() != null) {
			result.setReportType(userTemplate.getReportType());
		}
		if (userTemplate.getUserTemplateKey().getUserTemplateId() != null) {
			result.setUserTemplateId(userTemplate.getUserTemplateKey().getUserTemplateId());
		}
		return result;
	}

	@Override
	public void editUserTemplate(UserTemplate usertemplate) {
		update(usertemplate);
	}

	@Override
	public void deleteUserTemplateBasedOnUserDetails(UserTemplate userTemplate) throws Exception {
		StringBuilder selectQuery = new StringBuilder();
		selectQuery.append("from UserTemplate ut ");
		List<UserTemplate> listUserTemplate = new ArrayList<>();
		StringBuilder whereQuery = new StringBuilder();

		if (null != userTemplate) {
			whereQuery.append("where ");

			if (userTemplate.getUserTemplateKey().getDashboardId() != null) {
				whereQuery.append("ut.userTemplateKey.dashboardId='")
						.append(userTemplate.getUserTemplateKey().getDashboardId() + "'");
			}
			if (userTemplate.getUserTemplateKey().getUserDetails().getId() != null) {
				whereQuery.append("and ut.userTemplateKey.userDetails.id=")
						.append(userTemplate.getUserTemplateKey().getUserDetails().getId() + "");
			}
			selectQuery.append(whereQuery);
		}
		listUserTemplate = executeQuery(selectQuery.toString());
		listUserTemplate.forEach(userTemplateDb -> {
			delete(userTemplateDb);
		});
		listUserTemplate.clear();
	}

	@Override
	public List<UserTemplate> getUserTemplateforReportPack(UserTemplateVO userTemplateVo,
			Set<String> userTemplateIdList, Set<String> reportTypeSet) throws Exception {

		StringBuilder selectQuery = new StringBuilder();
		StringBuilder whereQuery = new StringBuilder();
		selectQuery.append("from UserTemplate ut ");
		whereQuery.append("where ");

		if (!StringUtils.isEmpty(userTemplateVo.getDashboardId())) {
			whereQuery.append("ut.userTemplateKey.dashboardId='").append(userTemplateVo.getDashboardId() + "'");
		} else {
			throw new BusinessException("DashBoardId can not be Null");
		}

		if (!StringUtils.isEmpty(userTemplateIdList)) {
			whereQuery.append(" and (");
			for (String userTemplate : userTemplateIdList) {
				if (!StringUtils.isEmpty(userTemplateIdList)) {
					whereQuery.append("ut.userTemplateKey.userTemplateId='").append(userTemplate + "'").append(" or ");
				} else {
					throw new BusinessException("userTemplateId can not be Null");
				}

			}
			int lastIdx = whereQuery.lastIndexOf("or");
			whereQuery = new StringBuilder(whereQuery).replace(lastIdx, lastIdx + 2, "");
			whereQuery.append(" ) ");
		}

		if (!StringUtils.isEmpty(reportTypeSet)) {
			whereQuery.append(" and (");
			for (String reportType : reportTypeSet) {
				if (!StringUtils.isEmpty(reportType)) {
					whereQuery.append("ut.reportType='").append(reportType + "'").append(" or ");
				} else {
					throw new BusinessException("userTemplateId can not be Null");
				}

			}
			int lastIdx = whereQuery.lastIndexOf("or");
			whereQuery = new StringBuilder(whereQuery).replace(lastIdx, lastIdx + 2, "");
			whereQuery.append(" ) ");
		}
		if (!StringUtils.isEmpty(userTemplateVo.getUserId())) {
			whereQuery.append("and ut.userTemplateKey.userDetails.id=").append(userTemplateVo.getUserId() + "");
		} else {
			throw new BusinessException("UserId can not be Null");
		}
		selectQuery.append(whereQuery);
		return executeQuery(selectQuery.toString());
	}

	@Override
	public List<UserTemplate> searchUserTemplateforReportPack(UserTemplateVO userTemplateVO, Set<String> reportTypeSet)
			throws Exception {

		StringBuilder selectQuery = new StringBuilder();
		selectQuery.append("from UserTemplate ut ");

		StringBuilder whereQuery = new StringBuilder();

		if (null != userTemplateVO) {
			whereQuery.append("where ");
			if (!StringUtils.isEmpty(userTemplateVO.getUserId())) {
				whereQuery.append("ut.userTemplateKey.userDetails.id='").append(userTemplateVO.getUserId() + "'");
			} else {
				throw new Exception("userId can not be Null");
			}
			if (!StringUtils.isEmpty(userTemplateVO.getDashboardId())) {
				whereQuery.append("and ut.userTemplateKey.dashboardId='").append(userTemplateVO.getDashboardId() + "'");
			}

			if (!StringUtils.isEmpty(reportTypeSet)) {
				whereQuery.append(" and (");
				for (String reportType : reportTypeSet) {
					if (!StringUtils.isEmpty(reportType)) {
						whereQuery.append("ut.reportType='").append(reportType + "'").append(" or ");
					} else {
						throw new BusinessException("reportType can not be Null");
					}

				}
				int lastIdx = whereQuery.lastIndexOf("or");
				whereQuery = new StringBuilder(whereQuery).replace(lastIdx, lastIdx + 2, "");
				whereQuery.append(" ) ");
			}

			if (!StringUtils.isEmpty(userTemplateVO.getUserTemplateId())) {
				whereQuery.append("and ut.userTemplateKey.userTemplateId='")
						.append(userTemplateVO.getUserTemplateId() + "'");
			}
			selectQuery.append(whereQuery);
		}
		return executeQuery(selectQuery.toString());

	}

	@Override
	public void deleteUserTemplate(UserDetails userDtl) {
		StringBuilder deleteQuery = new StringBuilder();
		deleteQuery.append("delete from UserTemplate ut");
		deleteQuery.append(" where deleted = true");
		executeUpdate(deleteQuery.toString());

	}


	@Override
	public void markUserTemplatesAsDeleted(Set<String> userTemplateIds) {
		String query = "update UserTemplate ut set deleted=true where ut.userTemplateKey.userTemplateId IN :set";
		updateSet(query, userTemplateIds);
	}

	@Override
	public List<UserTemplate> getUserTemplateByDashboardIds(Map<String, Set> values) throws Exception {
		return selectWithInClause(values, UserTemplate.class);
	}

	@Override
	public void deleteUserTemplateByDashboardId(List<UserTemplate> usertemplates) {
		deleteList(usertemplates);

	}

	@Override
	public void removeMarkDeletedUserTemplates(List<UserTemplate> values) throws Exception {
		deleteList(values);
	}

	@Override
	public List<UserTemplate> getMarkDeleteUserTemplate(List<Long> userIds) {
		StringBuilder selectQuery = new StringBuilder();
		selectQuery.append("from UserTemplate ut where ut.deleted = true AND ut.userTemplateKey.userDetails.id IN :list");
		return (List<UserTemplate>)executeQueryWithList(selectQuery.toString(), userIds);
		
	}

}