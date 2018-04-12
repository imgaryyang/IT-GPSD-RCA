<%@ page isErrorPage="true" import="java.io.PrintWriter" contentType="text/plain"%><%@ page import="java.io.StringWriter"%>


An Error occured Technical Support Contact: BPDCHIP Global Support/Pakistan/IBM (bpdgs@pk.ibm.com)
Message:
<%=exception.getMessage()%>

StackTrace:
<%
	StringWriter stringWriter = new StringWriter();
	PrintWriter printWriter = new PrintWriter(stringWriter);
	exception.printStackTrace(printWriter);
	out.println(stringWriter);
	printWriter.close();
	stringWriter.close();
%>