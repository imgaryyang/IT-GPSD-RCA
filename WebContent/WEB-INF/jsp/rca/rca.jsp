<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>


<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
    String imagesBasePath = "//" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<head profile="w3.ibm.com">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>Datepicker</title>
    <link rel="stylesheet" type="text/css"
          href="css/jquery.datetimepicker.css" />
    <style type="text/css">
        .custom-date-style {
            background-color: red !important;
        }
    </style>

    <style type="text/css" media="all">
        .bar-green-dark {
            background: #7a3;
            color: #fff;
            padding: .3em .5em;
            font-size: 1.1em;
            font-weight: bold;
        }

        .bar-green-med-dark {
            background: #9c3;
            color: #fff;
            padding: .3em .5em;
            font-size: 1.1em;
            font-weight: bold;
        }

        .bar-green-med-dark_cell {
            background: #9c3;
            color: #fff;
            margin: 2px 2px 2px 2px;
            padding: 2px 2px 2px 2px;
            font-size: 1.1em;
            font-weight: bold;
        }

        .bar-green-med-light {
            background: #bd6;
            color: #000;
            padding: .3em .5em;
            font-size: 1.1em;
            font-weight: bold;
        }

        .bar-blue-med-dark {
            background: #47b;
            color: #fff;
            padding: .3em .5em;
            font-size: 1.1em;
            font-weight: bold;
        }

        .bar-blue-med {
            background: #69c;
            color: #fff;
            padding: .3em .5em;
            font-size: 1.1em;
            font-weight: bold;
        }

        .bar-blue-med-light {
            background: #9be;
            color: #fff;
            padding: .2em .2em;
            font-size: 1.0em;
            font-weight: bold;
        }

        .bar-gray-dark {
            background: #666;
            color: #fff;
            padding: .3em .5em;
            font-size: 1.1em;
            font-weight: bold;
        }

        .bar-gray-med-dark {
            background: #999;
            color: #fff;
            padding: .3em .5em;
            font-size: 1.1em;
            font-weight: bold;
        }

        .bar-gray-med-light {
            background: #ccc;
            color: #000;
            padding: .3em .5em;
            font-size: 1.1em;
            font-weight: bold;
        }

        .bar-gray-light {
            background: #ddd;
            color: #000;
            padding: .3em .5em;
            font-size: 1.1em;
            font-weight: bold;
        }

        .bar-blue-black {
            background: #003f69;
            color: #fff;
            padding: .3em .5em;
            font-size: 1.1em;
            font-weight: bold;
        }

        .bar-green-black {
            background: #00a6a0;
            color: #fff;
            padding: .3em .5em;
            font-size: 1.1em;
            font-weight: bold;
        }

        .bar-green-black-small {
            background: #00a6a0;
            color: #fff;
            padding: .2em .2em;
            font-size: 1.0em;
            font-weight: bold;
        }

        .lr-mar-10px {
            margin-left: 10px;
            margin-right: 10px;
            margin-top: 10px;
            margin-bottom: 10px;
        }

        .dot-bor-1px {
            border: 2px dotted #CCC
        }

        .sol-bor-1px {
            border: 1px solid #CCC
        }

        .contract-contact-info {
            width: 180px
        }

        .errors {
            color: #ff0000;
            font-style: italic;
        }

        #general_Information span {
            display: block;
            height: 25px;
        }

        #general_Information label {
            width: auto;
            width: 250px;
            font-weight: normal;
        }

        #general_Information tr {
            height: 25px;
        }

        #bcrs span {
            display: block;
            height: 30px;
        }

        #bcrs label {
            width: auto;
            width: 550px;
            font-weight: normal;
        }

        #csst span {
            display: block;
            height: 30px;
        }

        #csst label {
            width: auto;
            width: 550px;
            font-weight: normal;
        }
    </style>
</head>

<body id="ibm-com">


<div id="ibm-pcon"><!-- CONTENT_BEGIN -->
    <div id="ibm-content"><!-- TITLE_BEGIN -->
        <!-- TITLE_END --> <!-- CONTENT_BODY -->
        <div id="ibm-content-body">
            <div id="ibm-content-main">
                <div class="ibm-columns">
                    <div class="ibm-col-6-6">
                        <div id="ibm-mapping-app" class="ibm-container main" role="main" aria-label="main container">
                            <div class="ibm-container-descs">
                                <div id="main-content-top" class="main-content-top-head">
                                    <div id="main-content-bar" class="columns">
                                        <div id="msgDiv" align="center" style=" margin-bottom: 1px; "></div>
                                        <div id="main-content-title" style="float:left;">
                                            <div id="main-content-title-text" role="main" aria-label="main heading">
                                                <h2>GPSDRCA - RCAs / Actions</h2>
                                            </div>
                                        </div>
                                        <div id="main-content-btn" align="right" role="region" aria-label="help">
                                            <a href="https://w3-connections.ibm.com/wikis/home?lang=en_US#/wiki/GPS%20Root%20Cause%20Analysis%20%28RCA%29/page/Introduction"
                                               target="_blank">Help</a>
                                        </div>
                                    </div>
                                </div>

                            </div>



                            <tiles:insertAttribute name="searchFilter"/>

                            <div class="ibm-container-body" id="contractList">

                            </div>
                        </div>
                        <!-- mapping top -->
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>


<!-- IBM_FOOTER_BEGIN -->
<div id="ibm-footer-module"></div>
<div id="ibm-footer">
    <h2 class="ibm-access">Footer links</h2>
    <ul>
        <li><a class="terms" href="https://w3.ibm.com/w3/info_terms_of_use.html">Terms of use</a></li>
    </ul>
</div>
<div id="ibm-metrics" style="display:none">
    <script src="//w3.ibm.com/w3webmetrics/js/ntpagetag.js" type="text/javascript"></script>
</div>


<!-- IBM_FOOTER_END -->




</body>







<script type="text/javascript" src="js/rca-1.0.js"> // </script>

<script type="text/javascript">

    dojo.replaceClass("rcaTab", "ibm-active");
    dojo.byId("page").value = 1;

    getRCAs();
</script>
