package com.logicq.ngr.service.report;

import com.logicq.ngr.vo.UserTemplateVO;

public interface ReportDownloadService {


	 String exportReport(UserTemplateVO userTemplateVO) throws Exception;
}
