
<%@page import="com.gps.util.UserSession"%>
<%@page import="com.gps.vo.GpsUser"%>
<%

// System.out.println("loading header.jsp......");
String userEmail = "";
UserSession userSession = (UserSession)request.getSession().getAttribute("userSession");
if(userSession != null){
// 	System.out.println("bpd user......");
	GpsUser bpdUser =  userSession.getGpsUser();
	if(bpdUser != null)
	userEmail = bpdUser.getEmail();
// 	System.out.println("userEmail = "+userEmail);
}
%>
<div id="ibm-masthead">
<div id="ibm-mast-options" role="region" aria-label="mast head" >
<ul role="presentation">

		<li id="ibm-home" role="presentation" style="position: relative; display: block; width: 70px; height: 38px; padding-right: 15px; display: block;">
			<a href="https://w3.ibm.com" aria-describedby="ibm-welcome-msg" aria-label="IBM Home page link" tabindex="0" role="button">w3</a>
		</li>
		<li id="ibm-sso" role="presentation" >
		<% if(userEmail != null && !userEmail.equals("")) { %>
 			<span class="ibm-thumbnail">
				<img width="25" height="25" src="//w3-connections.ibm.com/profiles/photo.do?email=<%=userEmail%>" alt="<%=userEmail%> profile picture">
			</span>
		<% } %>
			<span id="ibm-welcome-msg" class="ibm-user">Welcome back <span id="ibm-user-name"><%=userEmail%></span></span>
				<!--  <span id="ibm-welcome-msg" class="ibm-user">Welcome <span id="ibm-user-name"> </span> </span> --> 
		</li>
       	<li role="presentation" class="">
            <a href="logout.htm" role="button" tabindex="-1" aria-label="Sign out" aria-describedby="ibm-user-name"><strong>Sign out</strong></a>
       	</li>
	</ul>

</div>
<div id="ibm-universal-nav" role="navigation" aria-label="GPS">
<p id="ibm-site-title" role="presentation"><em>IT - GPSD - RCA (Root Cause Analysis)</em></p>
<ul id="ibm-menu-links">
<li><a href="https://w3.ibm.com/sitemap/us/en/">Site map</a></li>
</ul>
<div id="ibm-search-module" role="search">	       
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
<div id="loading-div" class="loading-div" style="display: none;">
	Loading, please wait..
	<img alt="loading.." src="images/w3/loader.gif" />
</div>
<!-- MASTHEAD_END -->

<script type="text/javascript">
window.onload=function(){

	dojo.query(".ibm-thumbnail").style({
    "background-image": ''    
});

};
</script>