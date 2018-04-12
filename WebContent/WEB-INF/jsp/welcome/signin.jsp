<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>


<style type="text/css">
	.ibm-common-overlay input[type="password"] {
		border: 1px solid #eee;
		color: #666;
		padding: 3px;
	}

	.ibm-common-overlay input[type="password"]:hover {
		border: 1px solid #ccc;
		color: #666;
	}

	.ibm-common-overlay input[type="password"]:focus {
		border: 1px solid #ccc;
		color: #666;
		background: #666;
		filter: progid : DXImageTransform.Microsoft.gradient ( startColorstr =
		'#666', endColorstr = '#eee' );
		background: -webkit-gradient(linear, left top, left bottom, from(#666),
		to(#eee) );
		background: -moz-linear-gradient(top, #666, #eee);
		background: -o-linear-gradient(top, #666, #eee);
	}
</style>
<form:form id="loginForm" action="login.do" commandName="userVo" class="ibm-column-form" method="POST" style="margin-top: 5px;">
<div class="ibm-container ibm-alternate ibm-buttons-last">
	<div class="ibm-container-body" >

	<form:hidden path="otpCheck" id="otpCheck" />		


		<div id="intranet_login">
		<p class="ibm-overlay-intro" style="text-align: justify;">GPSDRCA requires a valid IBM Intranet ID & Password to log-in. User IDs must be authorized at the account or group level in order to view account data.</p>

		<div><p><span id="validationResponse" style="color: #CC0000;">${ErrorMessage}</span></p></div>
			<p><label for="userName">User name:</label><span>
						<form:input path="userName" size="20" cssStyle="width: 175px;" maxlength="200" cssClass="required" onKeyPress="return submitOnEnter(this,event)" autocomplete="off"/></span></p>
			<p><label for="password">Password:</label><span>
						<form:password path="password" showPassword="false" size="20" cssStyle="width: 175px;" maxlength="200" cssClass="required" onKeyPress="return submitOnEnter(this,event)" onfocus="if(dojo.byId('userName').value != ''){ dojo.byId('password').value = ''}else{dojo.byId('userName').focus()}" autocomplete="off"/></span></p>
			<div class="ibm-overlay-rule"><hr /></div>
			<div class="ibm-buttons-row"><p>
					<input value="Sign-in" id="signin-submit-btn" type="button" name="ibm-submit" class="ibm-btn-arrow-pri"  onClick="return validateUser();" onSubmit="return validateUser();" />
					<span class="ibm-sep">&nbsp;</span>
					<input value="Cancel" id="signin-cancel-btn" type="button" name="ibm-cancel" class="ibm-btn-cancel-sec" onclick="clearCancel();"/>
			</p></div>
		</div>
		
		<div id="otp_login" style="display: none;">
			<p class="ibm-overlay-intro" style="text-align: justify;">One Time Password OTP has been sent to you on email, please check your email and provide the OTP for authentication.</p>
			<div><p><span id="otpValidationResponse" style="color: #CC0000;">${ErrorMessage}</span></p></div>
			<p><label for="password">OTP:</label><span>

			<form:password path="otp" showPassword="false" size="20" cssStyle="width: 175px;" maxlength="200" cssClass="required" onKeyPress="return submitOnEnter(this,event)" autocomplete="off"/></span></p>

			<div class="ibm-overlay-rule"><hr /></div>
			<div class="ibm-buttons-row"><p>
					<input value="Validate OTP" id="signin-submit-btn" type="button" name="ibm-submit" class="ibm-btn-arrow-pri"  onClick="return validateUser();" onSubmit="return validateUser();" />
					<span class="ibm-sep">&nbsp;</span>
					<input value="Cancel" id="otp-cancel-btn" type="button" name="ibm-cancel" class="ibm-btn-cancel-sec" onclick="clearCancel();"/>
			</p></div>
		</div>
		
	</div>
	
</div>
</form:form>


<script type="text/javascript">
	try{
		if(dojo.byId("userName").val() == ""){
			dojo.byId("userName").focus();
		}else{
			dojo.byId("password").focus();
		}
	}catch(e){}

	function clearCancel(){
		ibmweb.overlay.hide('signin-box');
		var intranetLogin = dojo.byId("intranet_login");
		var otpLogin = dojo.byId("otp_login");
		var otpCheckBox = dojo.byId("otpCheck");
		var contractListDiv = dojo.byId("validationResponse");
		var otpValidationResponse = dojo.byId("otpValidationResponse");
		
		otpCheckBox.value = 'N';
		contractListDiv.innerHTML = '';
		otpValidationResponse.innerHTML = '';
		otpLogin.style.display = 'none';
		intranetLogin.style.display = 'block';
	}
	
	function validateUser(){
		// Local var representing if the form has been sent at all
		//var hasBeenSent = false;
		// Local var representing node to be updated
		var contractListDiv = dojo.byId("validationResponse");
		var intranetLogin = dojo.byId("intranet_login");
		var otpLogin = dojo.byId("otp_login");
		var otpCheckBox = dojo.byId("otpCheck");
		
		// Using dojo.xhrPost, as the amount of data sent could be large
		dojo.xhrPost({
			// The URL of the request
			url: "validateUser.do",
			form: dojo.byId("loginForm"),
			// The success handler
			load: function(response) {

				if(response != null && response == "SUCCESS"){
					dojo.byId("loginForm").submit();
					return true;
				}else if(response != null && response == "ADMIN_SUCCESS"){
					otpCheckBox.value = 'Y';
					otpLogin.style.display = 'block';
					intranetLogin.style.display = 'none';
					return false;
				} else {
					if(otpCheckBox.value === 'Y'){
						contractListDiv = dojo.byId("otpValidationResponse");
						contractListDiv.innerHTML = response;
					} else {
						contractListDiv.innerHTML = response;
					}
					
					return false;
				}
			},
			// The error handler
			error: function() {
				contractListDiv.innerHTML = "An error occured, please try again.";
				return false;
			},
			// The complete handler
			handle: function() {
				// hasBeenSent = true;
			}
		});

	}

</script>


