/**<pre> 
 *==========================================================================
 *
 * Copyright: (C) IBM Corporation 2013 -- IBM Internal Use Only
 *
 *==========================================================================
 *
 *    FILE: FormGenerationException.java
 *    CREATOR:Waqar Malik
 *    DEPT: GBS PAK
 *    DATE: 01/01/2013
 *
 *-PURPOSE-----------------------------------------------------------------
 * 
 *--------------------------------------------------------------------------
 *
 *
 *-CHANGE LOG--------------------------------------------------------------
 * 01/01/2013Waqar Malik Initial coding.
 *==========================================================================
 * </pre> */

package com.gps.exceptions;

/**
 * This is a custom exception for Form generation service.
 * 
 * @authorWaqar Malik
 */

public class FormGenerationException extends GPSException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3384917468849727592L;

	/**
	 * @param String message
	 * This constructor accepts the message occured causing the exception
	 */
	public FormGenerationException(String message) {
		super(message);
	}
	
	/**
	 * @param message
	 * @param cause
	 */
	public FormGenerationException(String message, Throwable cause) {
		super(message,cause);
	}
}
