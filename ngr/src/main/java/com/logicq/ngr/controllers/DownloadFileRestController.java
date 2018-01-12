package com.logicq.ngr.controllers;

import java.io.IOException;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RequestMapping("/download")
@RestController
public class DownloadFileRestController {
	@Autowired
	ServletContext context;

	@RequestMapping(value = "/pdf", method = RequestMethod.GET, produces = "application/pdf")
	public ResponseEntity<InputStreamResource> download() throws IOException {
		ClassPathResource pdfFile = new ClassPathResource("C:\\tempFolder\\HelloWorld.pdf");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.parseMediaType("application/pdf"));
		headers.add("Access-Control-Allow-Origin", "*");
		headers.add("Access-Control-Allow-Methods", "GET, POST, PUT");
		headers.add("Access-Control-Allow-Headers", "Content-Type");
		headers.add("Content-Disposition", "filename=" + "C:\\tempFolder\\HelloWorld.pdf");
		headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
		headers.add("Pragma", "no-cache");
		headers.add("Expires", "0");

		headers.setContentLength(pdfFile.contentLength());
		ResponseEntity<InputStreamResource> response = new ResponseEntity<InputStreamResource>(
				new InputStreamResource(pdfFile.getInputStream()), headers, HttpStatus.OK);
		return response;

	}

	/*
	 * @RequestMapping(value = "/pdf", method = RequestMethod.GET, produces =
	 * "application/pdf") public ResponseEntity<InputStreamResource>
	 * downloadPdf() throws IOException {
	 * 
	 * return response;
	 * 
	 * }
	 */

}