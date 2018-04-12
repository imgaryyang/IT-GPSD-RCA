/**<pre> 
 *==========================================================================
 *
 * Copyright: (C) IBM Corporation 2013 -- IBM Internal Use Only
 *
 *==========================================================================
 *
 *    FILE: MonthlyStatustDaoImpl.java
 *    CREATOR:Waqar Malik
 *    DEPT: GBS PAK
 *    DATE: 04/03/2013
 *
 *-PURPOSE-----------------------------------------------------------------
 * 
 *--------------------------------------------------------------------------
 *
 *
 *-CHANGE LOG--------------------------------------------------------------
 * 04/03/2013Waqar Malik Initial coding.
 *==========================================================================
 * </pre> */
package com.gps.vo.helper;

/**
 * This is a helper vo class, for pagination purpose.
 *  
 * @authorWaqar Malik
 */
public class Pagination {
	public final static Integer PAGE_SIZE = 25;
	private Integer pageNumber;
	private Integer pageStart;
	private Integer pageEnd;
	private Integer totalRecords;
	
	public Pagination(){
//		setPageNumber(1);
	}
	
	public Integer getPageStart() {
		return pageStart;
	}

	public void setPageStart(Integer pageStart) {
		this.pageStart = pageStart;
	}

	public Integer getPageEnd() {
		return pageEnd;
	}

	public void setPageEnd(Integer pageEnd) {
		this.pageEnd = pageEnd;
	}

	public Integer getTotalRecords() {
		return totalRecords;
	}

	public void setTotalRecords(Integer totalRecords) {
		this.totalRecords = totalRecords;
	}

	

	public Integer getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(Integer pageNumber) {
		this.pageNumber = pageNumber;
	}
	
}
