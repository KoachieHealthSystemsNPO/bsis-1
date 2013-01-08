<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%!public long getCurrentTime() {
		return System.nanoTime();
	}%>

<c:set var="unique_page_id"><%=getCurrentTime()%></c:set>
<c:set var="editConfigureFormDivId">editConfigureFormDiv-${unique_page_id}</c:set>
<c:set var="editConfigureFormId">editConfigureForm-${unique_page_id}</c:set>
<c:set var="editConfigureTableId">editConfigureTable-${unique_page_id}</c:set>

<script>
  $(document).ready(function() {

    $(".updateFormFieldButton").button().click(function() {
      	var row = $(this).parent().parent().parent().children();

      	var idInput = $(row[0]).find('input[name="formFieldId"]');
        console.log(idInput.val());

        var hiddenInput = $(row[2]).find('input[name="hidden"]');
        console.log(hiddenInput.is(":checked"));

        var isRequiredInput = $(row[3]).find('input[name="isRequired"]');
        console.log(isRequiredInput);
        console.log(isRequiredInput.is(":checked"));

        var autoGenerateInput = $(row[4]).find('input[name="autoGenerate"]');
        console.log(autoGenerateInput.is(":checked"));

        var displayNameInput = $(row[5]).find('input[name="displayName"]');
        console.log(displayNameInput.val());

        var defaultValueInput = $(row[6]).find('input[name="defaultValue"]');
        console.log(defaultValueInput.val());

//        var copyFromSelectInput = $(row[5]).find('select :selected');
//        console.log(copyFromSelectInput.val());

        $.ajax({
          url: "configureFormFieldChange.html",
          data: {id: idInput.val(),
            		 hidden: hiddenInput.is(":checked"),
            		 isRequired: isRequiredInput.is(":checked"),
            		 autoGenerate: autoGenerateInput.is(":checked"),
            		 displayName: displayNameInput.val(),
            		 defaultValue: defaultValueInput.val(),
            		 //sourceField: copyFromSelectInput.val(),
            		},
          type: "POST",
          success: function() {
            			   $.showMessage("Configuration Field Saved");
            			   reloadCurrentTab();
          				 },
          error:	 function() {
					           $.showMessage("Something went wrong." + jsonResponse["errMsg"], {
          											    backgroundColor : 'red'
            											});
					         },
        });
    });

    var configureFormTable = $("#${editConfigureTableId}").dataTable({
      "bJQueryUI" : true,
      "bSort" : false,
      "bPaginate" : false,
      "bFilter" : false,
      "bInfo" : false,
    });

  });
</script>

<c:if test="${model.hasErrors}">
	<script>
    showErrorMessage("${model.message}");
  </script>
</c:if>
<c:if test="${model.success == true}">
	<script>
    showMessage("${model.message}");
  </script>
</c:if>
<c:if test="${model.success == false}">
	<script>
    showErrorMessage("${model.message}");
  </script>
</c:if>

<div id="${editConfigureFormDivId}" class="adminConfigureFormDiv">

	<div
		style="font-weight: bold; font-style: italic; margin-bottom: 20px; margin-top: 20px;">
		Configure Properties for fields of ${model.formName} Form:</div>
	<table id="${editConfigureTableId}">
		<thead>
			<tr>
				<th style="display:none" />
				<th>Field</th>
				<th>Hidden?</th>
				<th>Required?</th>
				<th style="display: none;">Auto Generate?</th>
				<th>Display Name</th>
				<th>Default Value</th>
				<th>Save Changes</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="formField" items="${model.formFields}">
				<tr>
					<td style="display: none;">
						<input name="formFieldId" value="${formField.id}" />
					</td>
					<td>${formField.defaultDisplayName}
					</td>
					<td>
						<c:if test="${formField.hidden == true}">
							<input type="checkbox" name="hidden" checked />
						</c:if>
						<c:if test="${formField.hidden == false}">
							<input type="checkbox" name="hidden" />
						</c:if>
					</td>
					<td>
						<c:if test="${formField.isRequired == true}">
							<input type="checkbox" name="isRequired" checked />
						</c:if>
						<c:if test="${formField.isRequired == false}">
							<input type="checkbox" name="isRequired" />
						</c:if>
					</td>
					<td style="display:none;">
						<c:if test="${formField.autoGenerate == true}">
							<input type="checkbox" name="autoGenerate" checked />
						</c:if>
						<c:if test="${formField.autoGenerate == false}">
							<input type="checkbox" name="autoGenerate" />
						</c:if>
					</td>
					<td>
						<input type="text" name="displayName" class="tableInputShort" value="${formField.displayName}" />
					</td>
					<td>
						<input type="text" name="defaultValue" class="tableInputShort" value="${formField.defaultValue}" />
					</td>
					<td>
					<!--div>
						<select class="copyFormFieldFromSelect" name="copyFromSelect">

							<c:if test="${formField.derived == true}">
								<option value="nocopy">Don't copy</option>
							</c:if>
							<c:if test="${formField.derived == false}">
								<option value="nocopy" selected="selected">Do not copy</option>
							</c:if>

							<c:forEach var="fieldToCopyFrom" items="${model.formFields}">

								<c:if test="${fieldToCopyFrom.field != formField.field}">

									<c:if test="${fieldToCopyFrom.field == formField.sourceField}">
										<option value="${fieldToCopyFrom.field}" selected="selected">${fieldToCopyFrom.defaultDisplayName}</option>
									</c:if>
									<c:if test="${fieldToCopyFrom.field != formField.sourceField}">
										<option value="${fieldToCopyFrom.field}">${fieldToCopyFrom.defaultDisplayName}</option>
									</c:if>

								</c:if>
							</c:forEach>
						</select>
						</div-->
						<div style="text-align: right;">
							<button class="updateFormFieldButton">Save</button>
						</div>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>
