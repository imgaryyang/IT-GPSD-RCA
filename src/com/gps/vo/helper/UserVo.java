package com.gps.vo.helper ;


/**
 * 
 * @author Hassan Hanif
 *
 */

public class UserVo implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private String id;
	private String userName;
	private String password;
	private String otpCheck;
	private String otp;
	
	private long applicationId ;

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public long getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(long applicationId) {
		this.applicationId = applicationId;
	}

	public String getOtp() {
		return otp;
	}

	public void setOtp(String otp) {
		this.otp = otp;
	}

	public String getOtpCheck() {
		return otpCheck;
	}

	public void setOtpCheck(String otpCheck) {
		this.otpCheck = otpCheck;
	}

}