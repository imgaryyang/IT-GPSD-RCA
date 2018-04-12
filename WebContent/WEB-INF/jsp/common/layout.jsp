<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<tiles:insertAttribute name="doctype" />
<html xmlns="http://www.w3.org/1999/xhtml" lang="en-US" xml:lang="en-US">
<head>
<tiles:insertAttribute name="meta" />
<title><tiles:insertAttribute name="title" ignore="true" /></title>
<tiles:insertAttribute name="script" />
</head>
<body id="ibm-com">
<div id="ibm-top" class="ibm-landing-page">
	<tiles:insertAttribute name="header" /> 
	<tiles:insertAttribute name="leadSpaces" />
	<tiles:insertAttribute name="navigation" /> 
	<tiles:insertAttribute name="content" />
	<tiles:insertAttribute name="footer" />
</div>
</body>
</html>

