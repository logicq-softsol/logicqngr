package com.logicq.ngr.helper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.servlet.ServletContext;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itextpdf.text.Document;
import com.logicq.ngr.common.constant.Constant;
import com.logicq.ngr.common.helper.DateHelper;
import com.logicq.ngr.common.property.MailAttribute;
import com.logicq.ngr.constant.Constants;
import com.logicq.ngr.constant.FileType;
import com.logicq.ngr.controllers.LoadApplicationData;
import com.logicq.ngr.model.Task;
import com.logicq.ngr.model.TaskStatus;
import com.logicq.ngr.model.common.Attribute;
import com.logicq.ngr.model.common.Configuration;
import com.logicq.ngr.model.common.Metric;
import com.logicq.ngr.model.report.DashboardReport;
import com.logicq.ngr.model.response.DruidResponse;
import com.logicq.ngr.model.response.ExportDetails;
import com.logicq.ngr.service.TaskStatusService;
import com.logicq.ngr.service.security.UserService;
import com.logicq.ngr.util.ReportPDFWriter;
import com.logicq.ngr.vo.EmailVO;
import com.logicq.ngr.vo.TaskVO;
import com.logicq.ngr.vo.UserTemplateVO;

import au.com.bytecode.opencsv.CSVWriter;

@Component
public class ReportDownloadHelper {

	private static final Logger logger = Logger.getLogger(ReportDownloadHelper.class);

	public static ObjectMapper mapper = new ObjectMapper();

	@Autowired
	MailAttribute mailAttribute;

	@Autowired
	UserService userService;

	@Autowired
	TaskStatusService taskStatusService;

	@Autowired
	ServletContext servletContext;

	/**
	 * @param listOfDashBoards
	 * @throws IOException
	 */
	public List<String[]> processDruidResponseData(ExportDetails exportDetails) {
		List<String[]> eventDataList = new ArrayList<>();

		for (DashboardReport dashboardreport : exportDetails.getUserReport().getDashboardReports()) {
			if (null != dashboardreport) {
				if (!StringUtils.isEmpty(dashboardreport.getReports())) {
					parseResponse(exportDetails, eventDataList, dashboardreport);
				}
				/*
				 * if (null != dashboardreport.getGraphReports() &&
				 * !dashboardreport.getGraphReports().isEmpty()) {
				 * parseGraphResponse(headerList, eventDataList,
				 * dashboardreport); }
				 * 
				 * if (null != dashboardreport.getTableReports() &&
				 * !dashboardreport.getTableReports().isEmpty()) {
				 * parseTableResponse(headerList, eventDataList,
				 * dashboardreport); }
				 */
			}
		}

		return eventDataList;
	}

	// List<String> fileHeaderList
	private void parseResponse(ExportDetails exportDetails, List<String[]> eventDataList,
			DashboardReport dashboardreport) {
		dashboardreport.getReports().forEach(report -> {
			List<String> tableHeaderList = new ArrayList<>();
			if (!StringUtils.isEmpty(report.getResponse().getReportConfiguration().getConfiguration().getName())) {
				exportDetails.setFilename(report.getResponse().getReportConfiguration().getConfiguration().getName()
						.replaceAll("\\s", "").trim());
			}
			if (!StringUtils.isEmpty(report.getResponse())
					&& !StringUtils.isEmpty(report.getResponse().getReportConfiguration())) {

				List<Attribute> attributes = null;
				if (Constants.TABLE
						.equalsIgnoreCase(report.getResponse().getReportConfiguration().getConfiguration().getType())
						|| Constants.TABLE_REAL_TIME.equalsIgnoreCase(
								report.getResponse().getReportConfiguration().getConfiguration().getType())) {
					attributes = report.getResponse().getReportConfiguration().getConfiguration().getProperties();
				} else {
					attributes = report.getResponse().getReportConfiguration().getConfiguration().getLegends();
				}

				// added handle to metric
				List<Metric> metricList = report.getResponse().getReportConfiguration().getConfiguration().getMetrics();
				if (null != metricList && !metricList.isEmpty()) {
					for (Metric metric : metricList) {
						metric.setId(metric.getName());
						attributes.add(metric);
					}
				}

				// List<Attribute> attributes =
				// report.getResponse().getConfiguration().getAttributes();
				// getHeaderAndFieldAttributes(attributes, displayHeaderList,
				// tableHeaderList);
				getHeaderAndFieldAttributes(attributes, exportDetails.getFileHeaderList(), tableHeaderList);

				if (report.getResponse() instanceof DruidResponse) {

					DruidResponse druidResponse = (DruidResponse) report.getResponse();
					if (!StringUtils.isEmpty(druidResponse.getResponse())) {
						druidResponse.getResponse().forEach(response -> {
							response.getEvents().forEach(event -> {
								parseListOfEventsForGraphRespose(tableHeaderList, eventDataList, event);
							});
						});
					}
				}
			}
		});
	}

	public static void getHeaderAndFieldAttributes(List<Attribute> attributes, List<String> displayHeaderList,
			List<String> tableHeaderList) {
		displayHeaderList.add("TimeStamp");
		tableHeaderList.add("timestamp");

		if (null != attributes && !attributes.isEmpty()) {
			attributes.forEach((attr) -> {
				displayHeaderList.add(attr.getDisplayName().trim());
				tableHeaderList.add(attr.getId().trim());
			});
		}
	}

	/**
	 * @param tableFieldList
	 * @param eventDataList
	 * @param eventObj
	 * @throws Exception
	 */
	private static void parseListOfEventsForGraphRespose(List<String> tableFieldList, List<String[]> eventDataList,
			Map<String, Object> eventObj) {
		Map<String, Object> eventDataMap = (HashMap<String, Object>) eventObj;
		List<String> datalist = new ArrayList<>();
		tableFieldList.forEach((columnName) -> {
			if (eventDataMap.get(columnName.trim()) instanceof Double) {
				datalist.add(String
						.valueOf(Double.valueOf(String.valueOf(eventDataMap.get(columnName.trim()))).longValue()));
			} else {
				datalist.add(String.valueOf(eventDataMap.get(columnName.trim())));
			}
		});
		if (!datalist.isEmpty()) {
			eventDataList.add(datalist.toArray(new String[datalist.size()]));
		}
	}

	/**
	 * @param csvWriter
	 * @param fieldList
	 * @param csvdata
	 * @throws IOException
	 */
	public void writeCSVData(ExportDetails exportDetails) throws Exception {
		prepareFilePath(exportDetails);
		CSVWriter csvWriter = new CSVWriter(new FileWriter(exportDetails.getFilePath()));
		List<String> fieldList = exportDetails.getFileHeaderList();
		List<String[]> csvdata = exportDetails.getFileContent();

		List<String[]> reportDetail = new ArrayList<>();
		reportDetail.add(null != exportDetails.getFilename()
				? new String[] { "Report : " + exportDetails.getFilename() } : null);
		reportDetail.add(null != exportDetails.getUserReport().getEmail()
				? new String[] { "Email Id : " + exportDetails.getUserReport().getEmail() } : null);
		reportDetail.add(null != exportDetails.getUserReport().getUserName()
				? new String[] { "User Name : " + exportDetails.getUserReport().getUserName() } : null);
		reportDetail
				.add(null != DateHelper.getDateTimeFormatFromMillisec(exportDetails.getReportcreationTime())
						? new String[] { "Report Creation Date : "
								+ DateHelper.getDateTimeFormatFromMillisec(exportDetails.getReportcreationTime()) }
						: null);
		for (String[] strings : reportDetail) {
			if (null != strings)
				csvWriter.writeNext(strings);
		}

		// csvWriter.writeNext(new String[]{new String()});
		// csvWriter.writeNext(new String[]{'\n'});

		StringBuilder buildheaderstring = new StringBuilder();
		fieldList.forEach((headerName) -> {
			buildheaderstring.append(headerName).append(",");
		});
		String[] headerArray = buildheaderstring.deleteCharAt(buildheaderstring.length() - 1).toString().split(",");
		csvWriter.writeNext(headerArray);
		for (String[] csvRowArray : csvdata) {
			csvWriter.writeNext(csvRowArray);
		}
		csvWriter.close();
	}

	/**
	 * @param csvWriter
	 * @param fieldList
	 * @param csvdata
	 * @throws IOException
	 */

	public HSSFWorkbook writeXLSData(HSSFWorkbook workbook, List<String> fieldList, List<String[]> xlsdata)
			throws IOException {

		HSSFSheet sheet = workbook.createSheet("sheet");
		StringBuilder buildheaderstring = new StringBuilder();
		fieldList.forEach((headerName) -> {
			buildheaderstring.append(headerName).append(",");
		});

		String[] headerArray = buildheaderstring.deleteCharAt(buildheaderstring.length() - 1).toString().split(",");

		Row row = sheet.createRow(0);

		int cellnum = 0;
		Cell cell = null;
		for (String col : headerArray) {
			cell = row.createCell(cellnum);
			cell.setCellValue((col));
			cellnum++;
		}

		int rownum = 1;
		for (Object[] xlsrowArray : xlsdata) {
			row = sheet.createRow(rownum++);
			cellnum = 0;
			for (Object obj : xlsrowArray) {
				cell = row.createCell(cellnum++);
				if (obj instanceof Date) {
					cell.setCellValue((Date) obj);
				} else if (obj instanceof Boolean) {
					cell.setCellValue((Boolean) obj);
				} else if (obj instanceof String) {
					cell.setCellValue((String) obj);
				} else if (obj instanceof Double) {
					cell.setCellValue((Double) obj);
				}
			}
		}
		return workbook;
	}

	/**
	 * This method will create PDF
	 * 
	 * @param reportDataList
	 * @return
	 * @throws Exception
	 */
	public void createPDF(ExportDetails exportDetails) throws Exception {
		prepareFilePath(exportDetails);
		Document document = new Document();
		// return ReportPDFWriter.getInstance().createPDFContent(exportDetails,
		// document);
		ReportPDFWriter.getInstance().createFinalPDF(exportDetails, document);
	}

	public UserTemplateVO createUserTemplateVoObj(TaskVO taskRequest) {
		UserTemplateVO userTemplateVO = new UserTemplateVO();
		String fileType = userTemplateVO.getFileType();
		userTemplateVO.setUserTemplateId(userTemplateVO.getUserTemplateId());
		userTemplateVO.setTemplateId(userTemplateVO.getTemplateId());
		userTemplateVO.setUserName(userTemplateVO.getUserName());
		userTemplateVO.setDashboardId(userTemplateVO.getDashboardId());
		userTemplateVO.setFileType(fileType);
		return userTemplateVO;
	}

	public void sendMail(String PathURL, String fileType, String userName, String reportName,
			UserTemplateVO userTemplateVO, String taskFailReason) throws Exception {

		String subject = "mail testing";
		String body = "Hello";
		String cc = null;
		String toAddresses = null;
		if (PathURL == null && fileType == null && reportName == null) {
			subject = "Exception";
			body = taskFailReason;
		}

		String mailServer = mailAttribute.getMailserver();
		String from = mailAttribute.getMailfrom();
		// String toAddresses = userDetails.getEmail();
		if (userTemplateVO != null) {
			EmailVO emailVO = userTemplateVO.getEmail();
			cc = emailVO.getCc();
			toAddresses = emailVO.getTo();
			subject = emailVO.getSubject();
		}

		Properties properties = System.getProperties();
		properties.setProperty("mail.smtp.host", mailServer);
		Session session = Session.getDefaultInstance(properties);
		MimeMessage message = new MimeMessage(session);

		// InternetAddress address = new InternetAddress(toAddresses);
		message.addRecipients(Message.RecipientType.TO, InternetAddress.parse(toAddresses.trim()));
		message.addRecipients(Message.RecipientType.CC, InternetAddress.parse(cc.trim()));

		message.setFrom(new InternetAddress(from));
		message.setSubject(subject);
		MimeBodyPart mimeBodyPart = new MimeBodyPart();
		mimeBodyPart.setText(body);
		mimeBodyPart.setContent(body, "text/html");

		Multipart multipart = new MimeMultipart();
		DataSource dataSource = new FileDataSource(PathURL);
		mimeBodyPart.setDataHandler(new DataHandler(dataSource));
		mimeBodyPart.setFileName(reportName + "." + fileType.toLowerCase());

		/*
		 * File att = new File(new
		 * File("C://Users//611165938//Pictures//Test//"), "ReadMe.txt");
		 * mimeBodyPart.attachFile(att);
		 */

		multipart.addBodyPart(mimeBodyPart);
		message.setContent(multipart);
		message.setSentDate(new Date());
		Transport.send(message);
		logger.info("Email has been sent successfully to  " + toAddresses);
	}

	/*
	 * public void sendMail(byte[] fileContentInByteFormat, String fileType,
	 * String userName, String reportName, UserTemplateVO userTemplateVO,String
	 * taskFailReason) throws Exception {
	 * 
	 * String subject = "mail testing"; String body = "Hello"; String cc = null;
	 * String toAddresses = null; if(fileContentInByteFormat == null && fileType
	 * == null && reportName == null) { subject = "Exception"; body =
	 * taskFailReason; }
	 * 
	 * String mailServer = mailAttribute.getMailserver(); String from =
	 * mailAttribute.getMailfrom(); // String toAddresses =
	 * userDetails.getEmail(); if (userTemplateVO != null) { EmailVO emailVO =
	 * userTemplateVO.getEmail(); cc = emailVO.getCc(); toAddresses =
	 * emailVO.getTo(); subject = emailVO.getSubject();
	 * 
	 * }
	 * 
	 * Properties properties = System.getProperties();
	 * properties.setProperty("mail.smtp.host", mailServer); Session session =
	 * Session.getDefaultInstance(properties); MimeMessage message = new
	 * MimeMessage(session);
	 * 
	 * // InternetAddress address = new InternetAddress(toAddresses);
	 * message.addRecipients(Message.RecipientType.TO,
	 * InternetAddress.parse(toAddresses.trim()));
	 * message.addRecipients(Message.RecipientType.CC,
	 * InternetAddress.parse(cc.trim()));
	 * 
	 * message.setFrom(new InternetAddress(from)); message.setSubject(subject);
	 * MimeBodyPart mimeBodyPart = new MimeBodyPart();
	 * mimeBodyPart.setText(body); mimeBodyPart.setContent(body, "text/html");
	 * 
	 * Multipart multipart = new MimeMultipart();
	 * 
	 * if (fileType != null) { if (fileType.equalsIgnoreCase("CSV")) {
	 * DataSource dataSource = new ByteArrayDataSource(fileContentInByteFormat,
	 * "text/csv"); mimeBodyPart.setDataHandler(new DataHandler(dataSource));
	 * mimeBodyPart.setFileName(reportName + ".csv"); } else if
	 * (fileType.equalsIgnoreCase("XLS")) { DataSource dataSource = new
	 * ByteArrayDataSource(fileContentInByteFormat, "text/excel");
	 * mimeBodyPart.setDataHandler(new DataHandler(dataSource));
	 * mimeBodyPart.setFileName(reportName + ".xls"); } else if
	 * (fileType.equalsIgnoreCase("PDF")) { DataSource dataSource = new
	 * ByteArrayDataSource(fileContentInByteFormat, "application/pdf");
	 * mimeBodyPart.setDataHandler(new DataHandler(dataSource));
	 * mimeBodyPart.setFileName(reportName + ".pdf"); } }
	 * 
	 * multipart.addBodyPart(mimeBodyPart); message.setContent(multipart);
	 * message.setSentDate(new Date()); Transport.send(message); logger.info(
	 * "Email has been sent successfully to  " + toAddresses);
	 * 
	 * }
	 */
	/**
	 * @param inputjson
	 * @throws IOException
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws JsonProcessingException
	 */
	public String exctractReportName(Map<String, Object> inputjson)
			throws IOException, JsonParseException, JsonMappingException, JsonProcessingException {
		Configuration configuration = mapper.readValue(
				mapper.writeValueAsString((Map<String, Object>) inputjson.get("configuration")), Configuration.class);

		String fileName = null;
		if (!configuration.getName().isEmpty() && null != configuration.getName()) {
			fileName = configuration.getName().trim();
		} else {
			fileName = mailAttribute.getReportName();
		}
		return fileName;
	}

	/**
	 * @param userTemplateVO
	 * @param fieldList
	 * @param result
	 * @param fileName
	 * @param fileContent
	 * @param reportType
	 * @param stringWriter
	 * @param isLocalStore
	 * @return
	 * @throws IOException
	 * @throws Exception
	 */
	public String generateReport(ExportDetails exportDetails) throws Exception {
		if (!StringUtils.isEmpty(exportDetails.getReportType())) {

			validateFile(exportDetails);

			if (FileType.CSV.getValue().equalsIgnoreCase(exportDetails.getReportType())) {
				writeCSVData(exportDetails);
				return exportDetails.getFilePathURL();
			} else if (FileType.XLS.getValue().equalsIgnoreCase(exportDetails.getReportType())) {
				formatXLSContent(exportDetails);
				return exportDetails.getFilePathURL();
			} else if (FileType.PDF.getValue().equalsIgnoreCase(exportDetails.getReportType())) {
				createPDF(exportDetails);
				return exportDetails.getFilePathURL();
			}
		}
		return null;
	}

	/**
	 * @param fieldList
	 * @param fileContent
	 * @return
	 */
	private void formatXLSContent(ExportDetails exportDetails) throws Exception {

		prepareFilePath(exportDetails);

		if (null != exportDetails.getFilePath()) {
			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet firstSheet = workbook.createSheet("Report");

			doXLSDataFormat(firstSheet, exportDetails);

			FileOutputStream fos = new FileOutputStream(new File(exportDetails.getFilePath()));
			HSSFCellStyle hsfstyle = workbook.createCellStyle();
			hsfstyle.setBorderBottom((short) 1);
			hsfstyle.setFillBackgroundColor((short) 245);
			workbook.write(fos);
			fos.close();
		}
	}

	public void createTaskStatus(TaskStatus taskStatus, Task task) throws Exception {
		taskStatus.setTaskExecutionDateTime(task.getTaskExecutionDate() + " " + task.getTaskExecutionTime());
		taskStatus.setTask(task);
		taskStatusService.createTaskStatus(taskStatus);
	}

	/**
	 * @param task
	 * @throws Exception
	 */
	public void updateTaskDetails(Task task) throws Exception {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
		Calendar calendar = Calendar.getInstance();
		if (null != task.getTaskType() && !StringUtils.isEmpty(task.getTaskType())) {
			String taskExecutionDate = task.getTaskExecutionDate();
			calendar.setTime(simpleDateFormat.parse(taskExecutionDate));
			if (null != taskExecutionDate && !StringUtils.isEmpty(taskExecutionDate)) {
				if (task.getTaskType().equalsIgnoreCase("hourly")) {
					String currentTaskTime = task.getTaskExecutionTime();
					task.setTaskExecutionTime(LocalTime.parse(currentTaskTime).plusHours(1).plusSeconds(1).toString());
					if (LocalTime.parse(currentTaskTime).isAfter(LocalTime.parse("23:00:00"))) {
						calendar.add(Calendar.DATE, 1);
						task.setTaskExecutionDate(simpleDateFormat.format(calendar.getTime()));
					}
				} else if (task.getTaskType().equalsIgnoreCase("daily")) {
					calendar.add(Calendar.DATE, 1);
					task.setTaskExecutionDate(simpleDateFormat.format(calendar.getTime()));
				} else if (task.getTaskType().equalsIgnoreCase("weekly")) {
					calendar.add(Calendar.DATE, 7);
					task.setTaskExecutionDate(simpleDateFormat.format(calendar.getTime()));
				} else if (task.getTaskType().equalsIgnoreCase("monthly")) {
					calendar.add(Calendar.MONTH, 1);
					task.setTaskExecutionDate(simpleDateFormat.format(calendar.getTime()));
				} else if (task.getTaskType().equalsIgnoreCase("yearly")) {
					calendar.add(Calendar.YEAR, 1);
					task.setTaskExecutionDate(simpleDateFormat.format(calendar.getTime()));
				}
			}
		}
	}

	/**
	 * @param fieldList
	 * @param fileName
	 * @param fileContent
	 * @throws IOException
	 * @throws Exception
	 */
	/*
	 * public void storeReportOnDrive(List<String> fieldList, String fileName,
	 * List<String[]> fileContent, String fileType) throws IOException,
	 * Exception { CSVWriter csvWriter; String exportFilePath = null; if
	 * (LoadApplicationData.siteOptionMap != null) { exportFilePath =
	 * String.valueOf(LoadApplicationData.siteOptionMap.get("exportFilePath"));
	 * if (null != exportFilePath && !StringUtils.isEmpty(exportFilePath)) { if
	 * ("CSV".equalsIgnoreCase(fileType)) { csvWriter = new CSVWriter(new
	 * FileWriter(exportFilePath.concat(fileName + ".csv")));
	 * writeCSVData(csvWriter, fieldList, fileContent); } else if
	 * ("XLS".equalsIgnoreCase(fileType)) { HSSFWorkbook workbook = new
	 * HSSFWorkbook(); writeXLSData(workbook, fieldList, fileContent);
	 * FileOutputStream outputStream = new
	 * FileOutputStream(exportFilePath.concat(fileName + ".xls"));
	 * workbook.write(outputStream); } } else { throw new Exception(
	 * "Export file drive path is not configured properly inside SITE_OPTIONS."
	 * ); } } }
	 */

	public void validateFile(ExportDetails exportDetails) throws Exception {
		String exportFilePath = String.valueOf(LoadApplicationData.siteOptionMap.get("exportFilePath"));
		String contextPath = servletContext.getContextPath(), filePath;
		String slash = "/";
		String url = null;
		if (exportDetails.getReportFlag()) {
			// directory export report
			filePath = exportFilePath + contextPath + Constant.EXPORT_REPORT_DIR;
			exportDetails.setFilePath(filePath);
			String hostedURL = String.valueOf(LoadApplicationData.siteOptionMap.get("url"));
			url = "http://" + hostedURL + contextPath + Constant.EXPORT_REPORT_DIR;
			exportDetails.setFilePathURL(url);

		} else {
			// directory scheduling report
			filePath = exportFilePath + contextPath + Constant.SCHEDULED_REPORT_DIR + slash
					+ exportDetails.getUserReport().getUserName() + slash
					+ exportDetails.getUserReport().getDashboardReports().get(0).getId() + slash
					+ exportDetails.getUserReport().getDashboardReports().get(0).getReports().get(0).getResponse()
							.getUserTemplateId();
			exportDetails.setFilePath(filePath);
			String hostedURL = String.valueOf(LoadApplicationData.siteOptionMap.get("url"));
			url = "http://" + hostedURL + ":" + String.valueOf(LoadApplicationData.siteOptionMap.get("tomcatport"))
					+ contextPath + Constant.SCHEDULED_REPORT_DIR + slash + exportDetails.getUserReport().getUserName()
					+ slash + exportDetails.getUserReport().getDashboardReports().get(0).getId() + slash
					+ exportDetails.getUserReport().getDashboardReports().get(0).getReports().get(0).getResponse()
							.getUserTemplateId();
			exportDetails.setFilePathURL(url);

		}

		File exportfile = new File(filePath);
		if (!exportfile.exists()) {
			if (exportfile.mkdirs()) {
				// made directory or sub directory
			}
		}
	}

	public void prepareFilePath(ExportDetails exportDetails) throws Exception {
		// This will use for append file name.
		String timeStamp = String.valueOf(System.currentTimeMillis());
		exportDetails.setReportcreationTime(timeStamp);
		String exportFilePath = String.valueOf(LoadApplicationData.siteOptionMap.get("exportFilePath"));
		String contextPath = servletContext.getContextPath();
		String slash = "/", parentFilePath, url;

		parentFilePath = exportDetails.getFilePath() + slash + exportDetails.getFilename() + "_" + timeStamp;
		url = exportDetails.getFilePathURL() + slash + exportDetails.getFilename() + "_" + timeStamp;

		if (null != exportDetails) {
			if (FileType.CSV.getValue().equalsIgnoreCase(exportDetails.getReportType())) {
				exportDetails.setFilePathURL(url + "." + FileType.CSV.getValue());
				exportDetails.setFilePath(parentFilePath + "." + FileType.CSV.getValue());
			} else if (FileType.XLS.getValue().equalsIgnoreCase(exportDetails.getReportType())) {
				exportDetails.setFilePathURL(url + "." + FileType.XLS.getValue());
				exportDetails.setFilePath(parentFilePath + "." + FileType.XLS.getValue());
			} else if (FileType.PDF.getValue().equalsIgnoreCase(exportDetails.getReportType())) {
				exportDetails.setFilePathURL(url + "." + FileType.PDF.getValue());
				exportDetails.setFilePath(parentFilePath + "." + FileType.PDF.getValue());
			}
		}
	}

	public void doXLSDataFormat(HSSFSheet firstSheet, ExportDetails exportDetails) throws Exception {
		int rownum = 0;

		Map<String, String> reportDetails = new HashMap<>();
		reportDetails.put("Report Name : ", null != exportDetails.getFilename() ? exportDetails.getFilename() : null);
		reportDetails.put("Email Id : ",
				null != exportDetails.getUserReport().getEmail() ? exportDetails.getUserReport().getEmail() : null);
		reportDetails.put("User Name : ", null != exportDetails.getUserReport().getUserName()
				? exportDetails.getUserReport().getUserName() : null);
		reportDetails.put("Report Creation Date : ",
				null != DateHelper.getDateTimeFormatFromMillisec(exportDetails.getReportcreationTime())
						? DateHelper.getDateTimeFormatFromMillisec(exportDetails.getReportcreationTime()) : null);

		Set<String> keySet = reportDetails.keySet();

		for (String key : keySet) {
			if (null != reportDetails.get(key)) {
				Row row = firstSheet.createRow(rownum);
				Cell cell1 = row.createCell(0);
				cell1.setCellValue(key);
				Cell cell2 = row.createCell(1);
				cell2.setCellValue(reportDetails.get(key));
				rownum++;
			}
		}
		Row blankRow = firstSheet.createRow(rownum);
		rownum++;

		Row headerRow = firstSheet.createRow(rownum);
		rownum++;
		for (int i = 0; i < exportDetails.getFileHeaderList().size(); i++) {
			Cell cell = headerRow.createCell(i);
			cell.setCellValue(exportDetails.getFileHeaderList().get(i));
		}
		for (String[] xlsRow : exportDetails.getFileContent()) {
			Row row = firstSheet.createRow(rownum);
			int j = 0;
			for (String xlsData : xlsRow) {
				Cell cell = row.createCell(j);
				cell.setCellValue(xlsData);
				j++;
			}
			rownum++;
		}
	}
}
