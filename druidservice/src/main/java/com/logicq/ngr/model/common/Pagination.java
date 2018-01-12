package com.logicq.ngr.model.common;

import com.logicq.ngr.model.druid.common.PagingSpec;

public class Pagination {

	//private String pagingIdentifiers;
	private int pagenumber;
	//private int recordid;
	private int pagesize;
	//private long totalRecordCount;
	private PagingSpec pagingSpec; 
	
	
	public int getPagenumber() {
		return pagenumber;
	}
	public void setPagenumber(int pagenumber) {
		this.pagenumber = pagenumber;
	}
	public int getPagesize() {
		return pagesize;
	}
	public void setPagesize(int pagesize) {
		this.pagesize = pagesize;
	}
	public PagingSpec getPagingSpec() {
		return pagingSpec;
	}
	public void setPagingSpec(PagingSpec pagingSpec) {
		this.pagingSpec = pagingSpec;
	}
	

}
