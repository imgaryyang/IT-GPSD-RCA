/**<pre> 
 *==========================================================================
 *
 * Copyright: (C) IBM Corporation 2013 -- IBM Internal Use Only
 *
 *==========================================================================
 *
 *    FILE: GPSException.java
 *    CREATOR:Waqar Malik
 *    DEPT: GBS PAK
 *    DATE: 01/01/2013
 *
 *-PURPOSE-----------------------------------------------------------------
 * This is a runtime type base exception for ISCRCA 
 *--------------------------------------------------------------------------
 *
 *
 *-CHANGE LOG--------------------------------------------------------------
 * 01/01/2013Waqar Malik Initial coding.
 *==========================================================================
 * </pre> */

package com.gps.exceptions;

/**
 * This is a top level custom exception for ISCRCA. As per requirement and clearance purpose this class should be 
 * used and extended.
 * 
 * @authorWaqar Malik
 */

public class GPSException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5407044129113318045L;
	
	/**
	 * @param String message
	 * This constructor accepts the message occured causing the exception
	 */
	public GPSException(String message) {
		super(message);
	}
	
	/**
	 * @param message
	 * @param cause
	 */
	public GPSException(String message, Throwable cause) {
		super(message,cause);
	}

}
