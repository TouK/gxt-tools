<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<html>
	<head>

		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
		<meta name="gwt:property" content="locale=pl"/>
		<meta name="author" content="TouK.pl" />

        <link rel="stylesheet" type="text/css" href="css/gxt-all.css" />


		<style>
			#loading {
			  position: absolute;
			  left: 50%;
			  top: 40%;
			  margin-left: -75px;
			  padding: 2px;
			  z-index: 20001;
			  height: auto;
			  border: 1px solid #ccc;
			}

			#loading a {
			  color: #225588;
			}

			#loading .loading-indicator {
			  background: white;
			  color: #444;
			  font: bold 13px tahoma, arial, helvetica;
			  padding: 10px;
			  margin: 0;
			  height: auto;
			  width: 150px;
			}

			#loading .loading-indicator img {
			  margin-right:8px;
			  float:left;
			  vertical-align:top;
			}

			#loading-msg {
			  font: normal 10px arial, tahoma, sans-serif;
			  white-space: nowrap;
			}

		</style>

	</head>

	<body>
		<div id="loading">
		    <div class="loading-indicator">
		    <img src="images/large-loading.gif" width="32" height="32"/>Dictionary demo<br />
		    <span id="loading-msg">Ładuję aplikację...</span>
		    </div>
		</div>
		<script language="javascript" src="pl.touk.top.dictionary.webapp.DictionaryDemoModule.nocache.js"></script>
		<iframe id="__gwt_historyFrame" style="width:0;height:0;border:0"></iframe>
	</body>
</html>