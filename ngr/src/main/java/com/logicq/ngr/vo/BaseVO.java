package com.logicq.ngr.vo;

public class BaseVO extends MessageVO {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1536481370938445693L;
	private String identifiers;
	private String userName;
	private int pageNumber;
	private int pageSize;
	public boolean isExport;
	private String fileType;

	public String getIdentifiers() {
		return identifiers;
	}

	public void setIdentifiers(String identifiers) {
		this.identifiers = identifiers;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public boolean isExport() {
		return isExport;
	}

	public void setExport(boolean isExport) {
		this.isExport = isExport;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	@Override
	public String toString() {
		return "BaseVO [identifiers=" + identifiers + ", userName=" + userName + ", pageNumber=" + pageNumber
				+ ", pageSize=" + pageSize + ", isExport=" + isExport + ", fileType=" + fileType + "]";
	}


}
