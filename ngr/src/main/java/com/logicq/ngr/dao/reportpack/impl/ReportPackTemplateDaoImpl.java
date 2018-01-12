package com.logicq.ngr.dao.reportpack.impl;

import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.logicq.ngr.common.exception.BusinessException;
import com.logicq.ngr.dao.AbstractDAO;
import com.logicq.ngr.dao.reportpack.ReportPackTemplateDao;
import com.logicq.ngr.model.reportpack.ReportpackTemplate;
import com.logicq.ngr.vo.ReportpackTemplateVO;

@Repository
public class ReportPackTemplateDaoImpl extends AbstractDAO<ReportpackTemplate>implements ReportPackTemplateDao {

	/**
	 *  This class deals with basic CRUD operations related to Report Pack Template
	 */
	private static final long serialVersionUID = 420593041601773959L;

	@Override
	public ReportpackTemplate saveReportpackTemplate(ReportpackTemplate reportpackTemplate) throws Exception {
		saveOrUpdate(reportpackTemplate);
		return reportpackTemplate;
	}

	@Override
	public List<ReportpackTemplate> getReportpackTemplate(ReportpackTemplateVO reportpackTemplateVO) throws Exception {
		StringBuilder selectQuery = new StringBuilder();
		selectQuery.append("from ReportpackTemplate rpt ");
		StringBuilder whereQuery = new StringBuilder();
		if (null != reportpackTemplateVO) {
			whereQuery.append(" where ");
			if (!StringUtils.isEmpty(reportpackTemplateVO.getReportpackId())) {
				whereQuery.append(" rpt.reportpackTemplateKey.reportpackId='").append(reportpackTemplateVO.getReportpackId() + "'");
			} else {
				throw new Exception("reportpackId can not be Null");
			}
			
			if (!StringUtils.isEmpty(reportpackTemplateVO.getReportpackTemplateId())) {
				whereQuery.append(" and rpt.reportpackTemplateKey.reportpackTemplateId='")
						.append(reportpackTemplateVO.getReportpackTemplateId() + "'");
			}
			
			selectQuery.append(whereQuery);
		}
		return executeQuery(selectQuery.toString());
	}

	@Override
	public ReportpackTemplate updateReportPackTemplate(ReportpackTemplate reportpackTemplate) throws Exception {
		update(reportpackTemplate);
		return reportpackTemplate;
	}

	/**
	 * getReportPackTemplatate based on ReportPackId and ReportPackTemplateId.
	 */
	@Override
	public ReportpackTemplate getReportPackTemplate(ReportpackTemplateVO reportpackTemplateVO) throws Exception {
		StringBuilder selectQuery = new StringBuilder();
		selectQuery.append("from ReportpackTemplate rpt ");
		StringBuilder whereQuery = new StringBuilder();
		whereQuery.append("where ");
		
		if (!StringUtils.isEmpty(reportpackTemplateVO.getReportpackId())) {
			whereQuery.append("rpt.reportpackTemplateKey.reportpackId='")
					.append(reportpackTemplateVO.getReportpackId() + "'").append(" and ");
		} else {
			throw new Exception("reportpackId can not be Null");
		}
		
		if (!StringUtils.isEmpty(reportpackTemplateVO.getReportpackTemplateId())) {
			whereQuery.append("rpt.reportpackTemplateKey.reportpackTemplateId='")
					.append(reportpackTemplateVO.getReportpackTemplateId() + "'");
		} else {
			throw new Exception("reportpackTemplateId can not be Null");
		}
		selectQuery.append(whereQuery);
		return executeQueryForUniqueRecord(selectQuery.toString());
	}

	@Override
	public List<ReportpackTemplate> getReportpackTemplateBasedOnReportType(ReportpackTemplateVO reportpackTemplateVO,
			Set<String> reportTypeSet) throws Exception {
        StringBuilder selectQuery = new StringBuilder();
        StringBuilder whereQuery = new StringBuilder();
        selectQuery.append("from ReportpackTemplate rpt ");
        whereQuery.append("where ");
        if(!StringUtils.isEmpty(reportpackTemplateVO.getReportpackId())){
              whereQuery.append("rpt.reportpackTemplateKey.reportpackId='").append(reportpackTemplateVO.getReportpackId() + "'");
        }else {
              throw new BusinessException("reportpackId can not be Null");
        }
        if(!StringUtils.isEmpty(reportTypeSet)){
              whereQuery.append("and (");
              for(String reportType:reportTypeSet){
                    if(!StringUtils.isEmpty(reportType)){
                          whereQuery.append("rpt.reportType='").append(reportType + "'").append(" or ");
                    }
                    
              }
              int lastIdx = whereQuery.lastIndexOf("or");
              whereQuery=new StringBuilder(whereQuery).replace(lastIdx, lastIdx+2,"");
              whereQuery.append(" ) ");
        }
        selectQuery.append(whereQuery);
        return executeQuery(selectQuery.toString());
	}

	@Override
	public void deleteReportPackTemplate(ReportpackTemplate reportpacktemplate) {
		delete(reportpacktemplate);
	}
}
