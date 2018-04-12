<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div id="ibm-pcon"><!-- CONTENT_BEGIN -->
	<div id="ibm-content"><!-- TITLE_BEGIN --> 
 		<!-- TITLE_END --> <!-- CONTENT_BODY -->
		<div id="ibm-content-body">
			<div id="ibm-content-main">
				<div class="ibm-columns">
					<div class="ibm-col-6-6">
						<div id="ibm-mapping-app" class="ibm-container main">
						
						<div id="ibm-leadspace-head" class="ibm-alternate">
								<div id="ibm-leadspace-body">
									<ul id="ibm-navigation-trail">
										<li><a href="/GPSDRCA">GPSDRCA</a></li>
										<li><a href="#">Admin</a></li>
										<li><a href="/GPSDRCA/admin.htm">Main</a></li>
									</ul>									
									<h1 class="ibm-small">Main</h1>
																		
								</div>	
							</div>
							
									
							<div class="ibm-container-body">
								Welcome to the administration pages. Here you will be able to modify the access control list (ACL) and questionnaires.<br /><br />
								On this page some simple items might be placed here, such as which questionnaires you have authorized admin authority.
							</div>
						</div><!-- mapping top -->
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<script type="text/javascript">
	dojo.replaceClass("adminTab", "ibm-active");
	dojo.byId("adminSubTabs").style.display = 'block';
	dojo.replaceClass("adminMainTab", "ibm-active");
</script>