<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
  pageContext.setAttribute("newLineChar", "\n");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<link type="text/css" rel="stylesheet" href="css/admin.css" />
<script type="text/javascript" src="js/admin/users.js"></script>


<div id="adminTab" class="leftPanel">
	<ul>
		<li id="adminWelcomePage">
			<a href="adminWelcomePageGenerator.html">Admin Home</a>
		</li>
		<li id="bloodTypingTests">
			<a href="bloodTypingTests.html">Blood typing tests</a>
		</li>
		<li id="bloodTypingRules">
			<a href="bloodTypingRules.html">Blood typing rules</a>
		</li>
		<li id="configureForms">
			<a href="configureForms.html">Configure Forms</a>
		</li>
		<li id="backupData">
			<a href="backupDataFormGenerator.html">Backup Data</a>
		</li>
		<li id="locationForm">
			<a href="configureLocationsFormGenerator.html">Centers/Sites</a>
		</li>
		<li id="productTypesForm">
			<a href="configureProductTypesFormGenerator.html">Product Types</a>
		</li>
		<li id="bloodBagTypesForm">
			<a href="configureBloodBagTypesFormGenerator.html">Blood Bag Types</a>
		</li>
		<li id="donorTypesForm">
			<a href="configureDonationTypesFormGenerator.html">Donation Types</a>
		</li>
		<li id="requestTypesForm">
			<a href="configureRequestTypesFormGenerator.html">Request Types</a>
		</li>
		<li id="crossmatchTypesForm">
			<a href="configureCrossmatchTypesFormGenerator.html">Crossmatch Types</a>
		</li>
		<li id="productVolumesForm">
			<a href="configureProductVolumesFormGenerator.html">Product Volumes</a>
		</li>
		<li id="configureUsersForm">
			<a href="configureUsersFormGenerator.html">Users</a>
		</li>
		<li id="configureTipsForm">
			<a href="configureTipsFormGenerator.html">Tips</a>
		</li>
		<li id="configureWorksheet">
			<a href="configureWorksheetsFormGenerator.html">Worksheets</a>
		</li>
		<li id="createSampleData">
			<a href="createSampleDataFormGenerator.html">Sample Data</a>
		</li>
	</ul>
</div>
