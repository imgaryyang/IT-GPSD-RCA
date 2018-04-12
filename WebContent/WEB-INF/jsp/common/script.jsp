<link href="//1.w3.s81c.com/common/v17/css/w3.css" rel="stylesheet" title="w3" type="text/css" />
<script src="//1.w3.s81c.com/common/js/dojo/w3.js" type="text/javascript"></script>
<%-- 
<link href="<%=request.getContextPath()%>/css/jquery-ui-1.10.3.custom.css" rel="stylesheet" title="jquery" type="text/css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.9.1.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-ui-1.10.3.custom.js"></script>
  --%>
<script  type="text/javascript">





ibmweb.config.imageUrl = "//1.w3.s81c.com/i/v17/";
ibmweb.config.signin.enabled = false;




if(!$.browser.msie){
	// Delay jQuery Ready function. w3.js do something to disturb controls jQuery onReady does.
	// So on ready first for w3.js to fully loaded the DOM then fires afterW3Loaded() from w3.js. it will resume jQuery ready 
	// and loaded all the required data. Only happens in FirsFox. Need to do more research on it.
	jQuery.readyWait += 1;
}





</script>