package com.logicq.ngr.vo;

public class EmailVO {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2293861927667366138L;

	private String to;
	private String cc;
	private String subject;

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getCc() {
		return cc;
	}

	public void setCc(String cc) {
		this.cc = cc;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

}
