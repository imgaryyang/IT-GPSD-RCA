<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	String imagesBasePath = "//"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html xmlns="http://www.w3.org/1999/xhtml" lang="en-US" xml:lang="en-US">
<head>

	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
	<link rel="schema.DC" href="purl.org/DC/elements/1.0/"/>
	<link rel="SHORTCUT ICON" href="https://w3.ibm.com/favicon.ico"/>
	<meta name="Description" content="GPS Welcome" />
	<meta name="Keywords" content="GPS Client Health Information Portal" />
	<meta name="Robots" content="index,follow" />
	<meta name="Owner" content="mattique@pk.ibm.com" />
	<meta name="DC.Subject" scheme="IBM_SubjectTaxonomy" content="taxonomy" />
	<meta name="DC.Type" scheme="IBM_ContentClassTaxonomy" content="ZZ999" />
	<meta name="DC.Language" scheme="rfc1766" content="en-US" />
	<meta name="DC.Date" scheme="iso8601" content="2004-02-19" />
	<meta name="DC.Rights" content="Copyright (c) 2001,2004 by IBM Corporation"  />
	<meta name="IBM.Country" content="US" />
	<meta name="Source" content="Client Health Information Portal" />
	<meta name="Security" content="public"/>
	<meta name="IBM.Effective" scheme="W3CDTF" content="2002-12-17"/>

	<title>Welcome To GPSDRCA</title>

	<link href="https://1.w3.s81c.com/common/v17/css/w3.css" rel="stylesheet" title="w3" type="text/css" />
	<script src="https://1.w3.s81c.com/common/js/dojo/w3.js" type="text/javascript">//</script>
	<script src="webdev-modeselector-common.js" type="text/javascript">//</script>
	<script type="text/javascript">
		ibmweb.config.w3logonUrl = "javascript:ibmweb.overlay.show('signin-box');//";
		ibmweb.config.signin.enabled = true;

		function submitOnEnter(myfield, e){
			var keycode;
			if (window.event)
				keycode = window.event.keyCode;
			else if (e)
				keycode = e.which;
			else
				return true;
			if (keycode == 13) {
				validateUser();
				return false;
			}
			else
				return true;
		}
	</script>

</head>
<body id="ibm-com">
<div id="ibm-top" class="ibm-landing-page">
	<!-- MASTHEAD_BEGIN -->
	<div id="ibm-masthead">
		<div id="ibm-mast-options" role="article" aria-label="w3">
			<ul>
				<li id="ibm-home"><a href="http://w3.ibm.com">w3</a></li>
				<li id="ibm-sso" role="presentation">
					<span class="ibm-thumbnail"></span>
					<a class="ibm-sso-signin" href="javascript:ibmweb.overlay.show('signin-box')" onclick="ibmweb.overlay.show('signin-box',this);return false;" tabindex="0" dojoattrid="2" role="button" aria-label="Sign in">Sign in</a>
				</li>
			</ul>
		</div>
		<div id="ibm-universal-nav">
			<p id="ibm-site-title"><em>IT - GPSD - RCA (Root Cause Analysis)</em></p>
			<ul id="ibm-menu-links">
				<li><a href="https://w3.ibm.com/sitemap/us/en/">Site maps</a></li>
			</ul>
			<div id="ibm-search-module">
				<form method="get" action="https://w3.ibm.com/search/do/search" id="ibm-search-form">
					<p>
						<label for="q"><span class="ibm-access">Search</span></label>
						<input id="q" maxlength="100" name="qt" type="text" value="" />
						<input name="v" type="hidden" value="17"/>
						<input value="Submit" class="ibm-btn-search" id="ibm-search" type="submit"/>
					</p>
				</form>
			</div>
		</div>
	</div>
	<!-- MASTHEAD_END -->
	<!-- LEADSPACE_BEGIN -->
	<div id="ibm-leadspace-head" style="display: none;">
		<div id="ibm-leadspace-body" style="display: none;">
			<h1 class="ibm-alternate-one" style="display: none;">Welcome To GPSDRCA</h1>
		</div>
	</div>
	<!-- LEADSPACE_END -->
	<div id="ibm-pcon"><!-- CONTENT_BEGIN -->
		<div id="ibm-content">
			<div id="ibm-content-body">
				<div id="ibm-content-main">
					<div class="ibm-columns">
						<div class="ibm-col-6-4">
							<br/>
							<br/>
							<br/>
							<br/>
							<div class="ibm-columns">
								<div class="ibm-col-6-2">
									<div >
										<img src="<%=imagesBasePath%>images/image_axd.png" alt="many people" width="600"/>
									</div>

									<!--   </div> -->

									<!-- <div class="ibm-col-6-2" style="text-align:left"> -->

									<p class="ibm-intro">
										<em>Features: </em>
									</p>
									<div class="ibm-container-body">
										<ul id="checlist">
											<li>Root Cause Analysis</li>
											<li>Provide extensive reporting mechanism<li>

										</ul>


										<a href="javascript:ibmweb.overlay.show('signin-box')" onclick="ibmweb.overlay.show('signin-box',this);return false;"><h2><b>Sign in</b></h2></a>

									</div>
								</div>

							</div>
							<div class="ibm-container">
								<h2>Alerts and Reminders</h2>
								<div class="ibm-container-body">
									<div class="ibm-columns">
										<div class="ibm-col-6-4">
											<ul class="ibm-bullet-list">
												<li style="color: #CC0000; font-weight:bold;"> Please contact  gsupport@pk.ibm.com in case any assistance is required.</li>
												<li style="color: #CC0000">If you are facing issues in accessing the system, please contact gsupport@pk.ibm.com, angbeen.amin1@pk.ibm.com</li>
												<li>DISCLAIMER: Personal information (such as sensitive financial or medical data and any information identifiable to an individual) other than business contact information [indicated as mandatory on this system] necessary to provide support SHOULD NOT be entered into this system. Anyone entering personal information agrees that IBM may use and transfer it across country borders according to IBM privacy policies to provide the requested services. If you are an IBM employee or contractor and a client asks that you enter their personal information, you must read this notice to them to obtain their consent before entering their personal information.</li>
											</ul>
										</div>
									</div>
								</div>
							</div>



						</div>




						<div class="ibm-col-6-2">
							<br/>
							<br/>
							<div class="ibm-container">
								<h2>Release Information</h2>
								<div class="ibm-container-body">
									<ul class="ibm-bullet-list">
										<li>Current release in production:v1r1_ref1.0</li>
										<li>The GPSDRCA release schedule is located on the GPSDRCA wiki</li>
									</ul>
								</div>
							</div>

							<div class="ibm-container">
								<h2>Support Reference Information</h2>
								<div class="ibm-container-body">
									<p>IT - GPSD - RCA (Root Cause Analysis) support reference information including contacts, user guide, and other information is available on the GPSDRCA's Wiki</p>
									<ul class="ibm-link-list">
										<li><a class="ibm-forward-link"  target="_blank" href="https://w3-connections.ibm.com/wikis/home?lang=en_US#/wiki/GPS Root Cause Analysis %28RCA%29/page/Introduction">GPSDRCA Wiki</a></li>
									</ul>
								</div>
							</div>

							<div class="ibm-container">
								<h2>Schedule Maintenance</h2>
								<div class="ibm-container-body">
									<div class="ibm-columns">
										<div class="ibm-col-3-1">
											<ul class="ibm-bullet-list">
												<li>Scheduled maintenance window is everyday from 22:00 hrs - 23:00 hrs EST for hotfix deployments. GPSDRCA may be unavailable for short duration from Sautrday 09:00 EST to Sunday 09:00 EST for weekly backups and/or production releases</li>
											</ul>
										</div>
									</div>
								</div>
							</div>

							<div class="ibm-container">
								<h2>System Requirement</h2>
								<div class="ibm-container-body">
									<div class="ibm-columns">
										<div class="ibm-col-3-1">
											<ul class="ibm-bullet-list">
												<li>To receive the best GPSDRCA application experience from its new Web 2.0 type features, please use Mozilla Firefox 3.6.x It is now available from ISSI. Installing Firefox will allow you to retain the older Internet Explorer 6 for legacy applications.</li>
											</ul>
										</div>
									</div>
								</div>
							</div>

						</div>
					</div><!-- ibm-columns -->
				</div> <!-- ibm-content-main -->
			</div> <!-- CONTENT_BODY_END -->
		</div><!-- ibm-content -->
	</div><!-- ibm-pcon -->

	<!-- IBM_FOOTER_BEGIN -->
	<div id="ibm-footer-module"></div>
	<div id="ibm-footer">
		<h2 class="ibm-access">Footer links</h2>
		<ul>
			<li><a class="terms" href="https://w3.ibm.com/w3/info_terms_of_use.html">Terms of use</a></li>
		</ul>
	</div>
	<div id="ibm-metrics" style="display:none"><script src="//w3.ibm.com/w3webmetrics/js/ntpagetag.js" type="text/javascript"></script></div>


	<!-- IBM_FOOTER_END -->


	<div class="ibm-common-overlay" id="signin-box">
		<div class="ibm-head">
			<p><a class="ibm-common-overlay-close" href="#close">Close [x]</a></p>
		</div>
		<div class="ibm-body">
			<div class="ibm-main">
				<div class="ibm-title">
					<h2>IBM sign in</h2>
				</div>
				<p>
					<%@ include file="/WEB-INF/jsp/welcome/signin.jsp" %>
				</p>

			</div>
		</div>
		<div class="ibm-footer">
		</div>

	</div>


</div>


</body>

</html>
