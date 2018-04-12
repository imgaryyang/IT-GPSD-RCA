/**<pre> 
 *==========================================================================
 *
 * Copyright: (C) IBM Corporation 2013 -- IBM Internal Use Only
 *
 *==========================================================================
 *
 *    FILE: FormGenerationServiceImpl.java
 *    CREATOR:Waqar Malik
 *    DEPT: GBS PAK
 *    DATE: 04/01/2013
 *
 *-PURPOSE-----------------------------------------------------------------
 * 
 *--------------------------------------------------------------------------
 *
 *
 *-CHANGE LOG--------------------------------------------------------------
 * 04/01/2013Waqar Malik Initial coding.
 *==========================================================================
 * </pre> */

package com.gps.vo.helper;

/**
 * This class holds contants across the application.
 *  
 * @authorWaqar Malik
 */

public class Constant {

	public static final String YES = "Y";
	public static final String NO = "N";
	public static final Character EMPTY_CHARACTER = null;
	public static final String TRUE = "Y";
	public static final String FALSE = "N";
	
	public static String RAG_GREEN = "G";
	public static String RAG_AMBER = "A";
	public static String RAG_RED = "R";
	public static String RAG_NONE = "N";
	
	public static final String SUCCESS = "SUCCESS";
	public static final String FAIL = "FAIL";
	public static final String ADMIN_SUCCESS = "ADMIN_SUCCESS";
	
	public static final String ROLE_ADMIN = "admin";
	public static final String ROLE_CREATOR = "creator";
	public static final String ROLE_DPE = "dpe";
	public static final String ROLE_DELEGATE = "delegate";
	
	public static final String OTP_CODE = "OTP_CODE";
	public static final String OTP_TIME = "OTP_TIME";
	
	//messages properties files keys
	public static String MSGS_KEY_INVALID_USER = "invalid.user";
	public static String MSGS_KEY_INVALID_OTP = "invalid.otp";
	
	public static String MSG_KEY_EMPTY_CONTRACT = "empty.contract";
	public static String MSG_KEY_EMPTY_RCA_COORDINATOR = "empty.rca.coordinator";

	//pagination actions.
	public static String NEW_SEARCH = "NEW-SEARCH";
	public static String PAGINATE = "PAGINATE";
	//form state
	public static String NEW_STATE = "0";
	public static String SAVED_STATE = "1";
	public static String APPROVED_STATE = "2";
	public static String RETURNED_STATE = "8";
	public static String REJECTED_STATE = "9";
	//action result msgz
	public static String SUCCESSFUL = "ok";
	// Acl actions
	public static String ADD_SINGLE_CONTRACT_ACL = "Add-Single-ACL";
	public static String ADD_USER_GROUP = "Add-User-Group";
	public static String ADD_GROUP_ACL = "Add-Group-ACL";
	public static String REMOVE_ACL = "remove-ACL";
	
	public static String NEW_CONTRACT_REQUEST = "New Contract";
	public static String MOD_CONTRACT_REQUEST = "Modify Contract";
	public static String ACCESS_REQUEST = "Access Request";
    public static String REMOVE_ACCESS_REQUEST = "Remove Request";
	
	public static String FORM_TYPE_MONTHLY = "Monthly";
	public static String FORM_TYPE_CONTRACT = "Profile";
	
	public static Integer SESSION_FLUSH_SIZE = 30;
	
}
