insert ignore into User (username,password,firstname,isAdmin,isStaff,isActive,isDeleted) values
('admin','admin321123','admin',1,1,1,0),
('test','test','technician',0,1,1,0);

insert into FormField
(form, field, defaultDisplayName, defaultValue, maxLength,
 hidden, isHidable,
 isRequired, canBeOptional,
 autoGenerate, isAutoGeneratable) values
('Donor', 'donorNumber', 'Donor Number', '', 15,
 '0', '0',
 '1', '0',
 '1', '1'),
('Donor', 'firstName', 'First Name', '', 0,
'0', '1',
'1', '1',
'0', '0'),
('Donor', 'middleName', 'Middle Name', '', 0,
'1', '1',
'0', '1',
'0', '0'),
('Donor', 'lastName', 'Last Name', '', 0,
'0', '1',
'0', '1',
'0', '0'),
('Donor', 'callingName', 'Calling Name', '', 0,
'1', '1',
'0', '1',
'0', '0'),
('Donor', 'birthDate', 'Birth Date', '', 0,
'0', '1',
'0', '1',
'0', '0'),
('Donor', 'age', 'Age', '', 0,
'0', '1',
'0', '1',
'0', '0'),
('Donor', 'gender', 'Gender', '', 0,
'0', '1',
'0', '1',
'0', '0'),
('Donor', 'nationalID', 'National ID', '', 0,
'1', '1',
'0', '1',
'0', '0'),
('Donor', 'bloodGroup', 'Blood Group', '', 0,
'0', '1',
'0', '1',
'0', '0'),
('Donor', 'address', 'Address', '', 0,
'0', '1',
'0', '1',
'0', '0'),
('Donor', 'city', 'City', '', 0,
'0', '1',
'0', '1',
'0', '0'),
('Donor', 'province', 'Province', '', 0,
'0', '1',
'0', '1',
'0', '0'),
('Donor', 'district', 'District', '', 0,
'0', '1',
'0', '1',
'0', '0'),
('Donor', 'state', 'State', '', 0,
'1', '1',
'0', '1',
'0', '0'),
('Donor', 'country', 'Country', '', 0,
'0', '1',
'0', '1',
'0', '0'),
('Donor', 'zipcode', 'Zip Code', '', 0,
'0', '1',
'0', '1',
'0', '0'),
('Donor', 'phoneNumber', 'Phone number', '', 0,
'0', '1',
'0', '1',
'0', '0'),
('Donor', 'otherPhoneNumber', 'Other phone number', '', 0,
'1', '1',
'0', '1',
'0', '0'),
('Donor', 'donorPanel', 'Donor Panel', '', 0,
'0', '1',
'0', '1',
'0', '0'),
('Donor', 'notes', 'Notes', '', 0,
'0', '1',
'0', '1',
'0', '0'),
('Donor', 'createdDate', 'Created on', '', 0,
'0', '0',
'0', '0',
'0', '0'),
('Donor', 'createdBy', 'Created by', '', 0,
'0', '0',
'0', '0',
'0', '0'),
('Donor', 'lastUpdatedTime', 'Last modified on', '', 0,
'0', '0',
'0', '0',
'0', '0'),
('Donor', 'lastUpdatedBy', 'Last modified by', '', 0,
'0', '0',
'0', '0',
'0', '0');

insert into FormField
(form, field, defaultDisplayName, defaultValue, maxLength,
 hidden, isHidable,
 isRequired, canBeOptional,
 autoGenerate, isAutoGeneratable) values
('DonorDeferral', 'deferredOn', 'Deferred on', '', 15,
 '0', '0',
 '1', '0',
 '0', '0'),
('DonorDeferral', 'deferredUntil', 'Deferred until', '', 15,
 '0', '0',
 '1', '0',
 '0', '0'),
('DonorDeferral', 'deferredBy', 'Deferred by', '', 15,
 '0', '0',
 '1', '0',
 '0', '0'),
('DonorDeferral', 'deferralReason', 'Deferral Reason', '', 15,
 '0', '0',
 '1', '0',
 '0', '0'),
('DonorDeferral', 'deferralReasonText', 'Other details', '', 15,
 '0', '0',
 '0', '1',
 '0', '0');

insert into FormField
(form, field, defaultDisplayName, defaultValue, maxLength,
 hidden, isHidable,
 isRequired, canBeOptional,
 autoGenerate, isAutoGeneratable) values
('CollectedSample', 'collectionNumber', 'Collection Number', '', 0,
 '0', '0',
 '1', '0',
 '1', '1'),
('CollectedSample', 'collectionBatchNumber', 'Batch number', '', 0,
 '0', '1',
 '0', '1',
 '0', '0'),
 ('CollectedSample', 'collectedOn', 'Collected on', '', 0,
 '0', '0',
 '1', '0',
 '0', '0'),
('CollectedSample', 'donorNumber', 'Donor number', '', 0,
 '0', '1',
 '1', '1',
 '0', '0'),
('CollectedSample', 'donationType', 'Donation type', '', 0,
 '0', '1',
 '1', '1',
 '0', '0'),
('CollectedSample', 'bloodBagType', 'Blood Bag type', '', 0,
 '0', '1',
 '1', '1',
 '0', '0'),
('CollectedSample', 'useParametersFromBatch', 'Use collection center and site as entered in collection batch', '', 0,
 '0', '1',
 '0', '1',
 '0', '0'),
('CollectedSample', 'collectionSite', 'Collection site', '', 0,
 '0', '1',
 '1', '1',
 '0', '0'),
('CollectedSample', 'collectionCenter', 'Collection center', '', 0,
 '0', '1',
 '1', '1',
 '0', '0'),
('CollectedSample', 'notes', 'Notes', '', 0,
 '0', '1',
 '0', '1',
 '0', '0'),
('CollectedSample', 'bloodTypingStatus', 'Blood Typing Status', '', 0,
 '0', '0',
 '0', '0',
 '0', '0'),
('CollectedSample', 'ttiStatus', 'TTI Status', '', 0,
 '0', '0',
 '0', '0',
 '0', '0'),
('CollectedSample', 'createdDate', 'Created on', '', 0,
 '0', '0',
 '0', '0',
 '0', '0'),
('CollectedSample', 'createdBy', 'Created by', '', 0,
 '0', '0',
 '0', '0',
 '0', '0'),
('CollectedSample', 'lastUpdatedTime', 'Last modified on', '', 0,
 '0', '0',
 '0', '0',
 '0', '0'),
('CollectedSample', 'lastUpdatedBy', 'Last modified by', '', 0,
 '0', '0',
 '0', '0',
 '0', '0');

insert into FormField
(form, field, defaultDisplayName, defaultValue, maxLength,
 hidden, isHidable,
 isRequired, canBeOptional,
 autoGenerate, isAutoGeneratable) values
('CollectionBatch', 'batchNumber', 'Batch number', '', 0,
 '0', '0',
 '1', '0',
 '0', '1'),
('CollectionBatch', 'collectionCenter', 'Collection center', '', 0,
 '0', '1',
 '0', '1',
 '0', '0'),
('CollectionBatch', 'collectionSite', 'Collection site', '', 0,
 '0', '1',
 '0', '1',
 '0', '0'),
('CollectionBatch', 'notes', 'Notes', '', 0,
 '0', '1',
 '0', '1',
 '0', '0'),
('CollectionBatch', 'createdDate', 'Created on', '', 0,
 '0', '0',
 '0', '0',
 '0', '0'),
('CollectionBatch', 'createdBy', 'Created by', '', 0,
 '0', '0',
 '0', '0',
 '0', '0'),
('CollectionBatch', 'lastUpdatedTime', 'Last modified on', '', 0,
 '0', '0',
 '0', '0',
 '0', '0'),
('CollectionBatch', 'lastUpdatedBy', 'Last modified by', '', 0,
 '0', '0',
 '0', '0',
 '0', '0');

insert into FormField
(form, field, defaultDisplayName, defaultValue, maxLength,
 hidden, isHidable,
 isRequired, canBeOptional,
 autoGenerate, isAutoGeneratable) values
('Product', 'collectionNumber', 'Collection Number', '', 0,
 '0', '1',
 '1', '0',
 '0', '0'),
('Product', 'productType', 'Product Type', '', 0,
 '0', '1',
 '1', '0',
 '0', '0'),
('Product', 'createdOn', 'Creation date', '', 0,
 '0', '1',
 '1', '0',
 '0', '0'),
('Product', 'expiresOn', 'Expiry date', '', 0,
 '0', '0',
 '1', '1',
 '0', '0'),
('Product', 'issuedOn', 'Issue date', '', 0,
 '0', '0',
 '0', '1',
 '0', '0'),
('Product', 'status', 'Product status', '', 0,
 '0', '0',
 '0', '1',
 '0', '0'),
('Product', 'bloodGroup', 'Blood group', '', 0,
 '0', '0',
 '0', '1',
 '0', '0'),
('Product', 'age', 'Age', '', 0,
 '0', '0',
 '0', '1',
 '0', '0'),
('Product', 'discardedOn', 'Discarded on', '', 0,
 '0', '0',
 '0', '1',
 '0', '0'),
('Product', 'discardedBy', 'Discarded by', '', 0,
 '0', '0',
 '0', '1',
 '0', '0'),
('Product', 'notes', 'Notes', '', 0,
 '0', '0',
 '0', '1',
 '0', '0'),
('Product', 'createdDate', 'Created on', '', 0,
'0', '0',
'0', '0',
'0', '0'),
('Product', 'createdBy', 'Created by', '', 0,
'0', '0',
'0', '0',
'0', '0'),
('Product', 'lastUpdatedTime', 'Last modified on', '', 0,
'0', '0',
'0', '0',
'0', '0'),
('Product', 'lastUpdatedBy', 'Last modified by', '', 0,
'0', '0',
'0', '0',
'0', '0');

insert into FormField
(form, field, defaultDisplayName, defaultValue, maxLength,
 hidden, isHidable,
 isRequired, canBeOptional,
 autoGenerate, isAutoGeneratable) values
('TTIForm', 'collectionNumber', 'Collection Number', '', 0,
 '0', '0',
 '1', '0',
 '0', '0');

insert into FormField
(form, field, defaultDisplayName, defaultValue, maxLength,
 hidden, isHidable,
 isRequired, canBeOptional,
 autoGenerate, isAutoGeneratable) values
('TestResult', 'collectionNumber', 'Collection Number', '', 0,
 '0', '1',
 '1', '0',
 '0', '0'),
('TestResult', 'discardedBy', 'Discarded by', '', 0,
 '0', '0',
 '0', '1',
 '0', '0'),
('TestResult', 'notes', 'Notes', '', 0,
 '0', '0',
 '0', '1',
 '0', '0'),
('TestResult', 'createdDate', 'Created on', '', 0,
'0', '0',
'0', '0',
'0', '0'),
('TestResult', 'createdBy', 'Created by', '', 0,
'0', '0',
'0', '0',
'0', '0'),
('TestResult', 'lastUpdatedTime', 'Last modified on', '', 0,
'0', '0',
'0', '0',
'0', '0'),
('TestResult', 'lastUpdatedBy', 'Last modified by', '', 0,
'0', '0',
'0', '0',
'0', '0');

insert into FormField
(form, field, defaultDisplayName, defaultValue, maxLength,
 hidden, isHidable,
 isRequired, canBeOptional,
 autoGenerate, isAutoGeneratable) values
('Request', 'requestNumber', 'Request number', '', 0,
 '0', '0',
 '1', '0',
 '1', '1'),
('Request', 'requestDate', 'Request date', '', 0,
 '0', '0',
 '1', '0',
 '0', '0'),
('Request', 'requiredDate', 'Required date', '', 0,
 '0', '0',
 '1', '0',
 '0', '0'),
('Request', 'requestSite', 'Request site', '', 0,
 '0', '1',
 '1', '1',
 '0', '0'),
('Request', 'numUnitsRequested', 'No. of units requested', '', 0,
 '0', '0',
 '0', '1',
 '0', '0'),
('Request', 'numUnitsIssued', 'No. of units issued', '', 0,
 '0', '0',
 '0', '1',
 '0', '0'),
('Request', 'requestStatus', 'Request status', '', 0,
 '0', '0',
 '0', '1',
 '0', '0'),
('Request', 'bloodGroup', 'Blood group', '', 0,
 '0', '0',
 '1', '1',
 '0', '0'),
('Request', 'productType', 'Product type', '', 0,
 '0', '0',
 '1', '0',
 '0', '0'),
('Request', 'requestType', 'Request type', '', 0,
 '0', '1',
 '1', '1',
 '0', '0'),
('Request', 'patientNumber', 'Patient number', '', 0,
 '0', '1',
 '0', '1',
 '0', '0'),
('Request', 'patientFirstName', 'Patient first name', '', 0,
 '0', '1',
 '0', '1',
 '0', '0'),
('Request', 'patientLastName', 'Patient last name', '', 0,
 '0', '1',
 '0', '1',
 '0', '0'),
('Request', 'patientDiagnosis', 'Patient diagnosis', '', 0,
 '0', '1',
 '0', '1',
 '0', '0'),
('Request', 'patientBirthDate', 'Patient date of birth', '', 0,
 '0', '1',
 '0', '1',
 '0', '0'),
('Request', 'patientAge', 'Patient age', '', 0,
 '0', '1',
 '0', '1',
 '0', '0'),
('Request', 'patientGender', 'Patient gender', '', 0,
 '0', '1',
 '0', '1',
 '0', '0'),
('Request', 'ward', 'ward', '', 0,
 '0', '1',
 '0', '1',
 '0', '0'),
('Request', 'hospital', 'Hospital', '', 0,
 '0', '1',
 '0', '1',
 '0', '0'),
('Request', 'requestedBy', 'Requested by', '', 0,
 '0', '1',
 '0', '1',
 '0', '0'),
('Request', 'notes', 'Notes', '', 0,
 '0', '1',
 '0', '1',
 '0', '0'),
('Request', 'createdDate', 'Created on', '', 0,
'0', '0',
'0', '0',
'0', '0'),
('Request', 'createdBy', 'Created by', '', 0,
'0', '0',
'0', '0',
'0', '0'),
('Request', 'lastUpdatedTime', 'Last modified on', '', 0,
'0', '0',
'0', '0',
'0', '0'),
('Request', 'lastUpdatedBy', 'Last modified by', '', 0,
'0', '0',
'0', '0',
'0', '0');

insert into FormField
(form, field, defaultDisplayName, defaultValue, maxLength,
 hidden, isHidable,
 isRequired, canBeOptional,
 autoGenerate, isAutoGeneratable) values
('CompatibilityTest', 'productNumber', 'Product number', '', 0,
 '0', '0',
 '1', '0',
 '0', '0'),
 ('CompatibilityTest', 'requestNumber', 'Product number', '', 0,
 '0', '1',
 '1', '0',
 '0', '0'),
('CompatibilityTest', 'compatibilityResult', 'Compatibility result', '', 0,
 '0', '1',
 '1', '0',
 '0', '0'),
 ('CompatibilityTest', 'compatibilityTestDate', 'Compatibility test date', '', 0,
 '0', '1',
 '1', '0',
 '0', '0'),
 ('CompatibilityTest', 'transfusedBefore', 'Transfused before', '', 0,
 '1', '1',
 '0', '0',
 '0', '0'),
 ('CompatibilityTest', 'crossmatchType', 'Crossmatch type', '', 0,
 '0', '1',
 '1', '0',
 '0', '0'),
 ('CompatibilityTest', 'testedBy', 'Tested by', '', 0,
 '0', '1',
 '1', '0',
 '0', '0'),
 ('CompatibilityTest', 'notes', 'Notes', '', 0,
 '0', '1',
 '1', '0',
 '0', '0'),
 ('CompatibilityTest', 'createdDate', 'Created on', '', 0,
	'0', '0',
	'0', '0',
	'0', '0'),
('CompatibilityTest', 'createdBy', 'Created by', '', 0,
 '0', '0',
 '0', '0',
 '0', '0'),
('CompatibilityTest', 'lastUpdatedTime', 'Last modified on', '', 0,
 '0', '0',
 '0', '0',
 '0', '0'),
('CompatibilityTest', 'lastUpdatedBy', 'Last modified by', '', 0,
 '0', '0',
 '0', '0',
 '0', '0');

 insert into DonationType (donationType, isDeleted) values
('Voluntary', '0'),
('Family', '0'),
('Autologous', '0'),
('Other', '0');

insert into ProductStatusChangeReasonCategory (id, category, isDeleted) values
(1, 'Discarded' , '0'),
(2, 'Returned', '0'),
(3, 'Other', '0');

insert into FormField
(form, field, defaultDisplayName, defaultValue, maxLength,
 hidden, isHidable,
 isRequired, canBeOptional,
 autoGenerate, isAutoGeneratable) values
('Usage', 'product', 'Product', '', 0,
 '0', '0',
 '1', '0',
 '0', '0'),
('Usage', 'collectionNumber', 'Collection number', '', 0,
 '0', '0',
 '1', '0',
 '0', '0'),
('Usage', 'productType', 'Product type', '', 0,
 '0', '0',
 '1', '0',
 '0', '0'),
('Usage', 'usageDate', 'Usage date', '', 0,
 '0', '0',
 '1', '0',
 '0', '0'),
('Usage', 'hospital', 'Hospital', '', 0,
 '0', '1',
 '0', '1',
 '0', '0'),
('Usage', 'ward', 'Ward', '', 0,
 '0', '0',
 '0', '1',
 '0', '0'),
('Usage', 'patientName', 'Patient name', '', 0,
 '0', '0',
 '0', '1',
 '0', '0'),
 ('Usage', 'useIndication', 'Use indication', '', 0,
 '0', '0',
 '0', '1',
 '0', '0'),
 ('Usage', 'usedBy', 'Used by', '', 0,
 '0', '0',
 '0', '1',
 '0', '0'),
 ('Usage', 'notes', 'Notes', '', 0,
 '0', '0',
 '0', '1',
 '0', '0'),
 ('Usage', 'createdDate', 'Created on', '', 0,
 '0', '0',
 '0', '0',
 '0', '0'),
 ('Usage', 'createdBy', 'Created by', '', 0,
 '0', '0',
 '0', '0',
 '0', '0'),
 ('Usage', 'lastUpdatedTime', 'Last modified on', '', 0,
 '0', '0',
 '0', '0',
 '0', '0'),
 ('Usage', 'lastUpdatedBy', 'Last modified by', '', 0,
 '0', '0',
 '0', '0',
 '0', '0');

insert into ProductStatusChangeReason (statusChangeReason, category_id, isDeleted) values
('Discarded', '1', '0'),
('Returned as expired', '2', '0'),
('Other', '3', '0');

insert into RequestType (requestType, description, isDeleted) values
('Emergency', '', '0'),
('Group and Save', '', '0'),
('Group and Screen', '', '0'),
('Standard', '', '0'),
('Urgent', '', '0'),
('Elective Surgery', '', '0');

insert into ProductType (productTypeShortName, productType, description, expiresAfter, expiresAfterUnits, hasBloodGroup, canSubdivide, canPool, isDeleted) values
('Whole Blood', 'Whole Blood', '', 35, 'DAYS', '1', '1', '0', '0'),
('RCC', 'Red Cell Concentrate', '', 35, 'DAYS', '1', '1', '0', '0'),
('FFP', 'Fresh Frozen Plasma', '', 365, 'DAYS', '1', '1', '0', '0'),
('Platelets', 'Platelets', '', 5, 'DAYS', '1', '1', '0', '0'),
('Cryo', 'Cryoprecipitate', '', 35, 'DAYS', '0', '1', '0', '0'),
('Whole Blood Pedi', 'Whole Blood Pedi', '', 35, 'DAYS', '1', '1', '0', '0'),
('RCC Pedi', 'Red Cell Concentrate Pedi', '', 35, 'DAYS', '1', '1', '0', '0'),
('FFP Pedi', 'Fresh Frozen Plasma Pedi', '', 1, 'YEARS', '1', '1', '0', '0'),
('Platelets Pedi', 'Platelets Pedi', '', 5, 'DAYS', '1', '1', '0', '0'),
('Cryo Pedi', 'Cryoprecipitate Pedi', '', 35, 'DAYS', '0', '1', '0', '0');

insert into BloodBagType (bloodBagType, isDeleted) values
('Single', '0'),
('Triple', '0'),
('Pedi', '0');

insert into Location(name, isCollectionCenter, isCollectionSite, isMobileSite, isUsageSite, isDonorPanel, isDeleted, notes) values
('Lusaka', 1, 0, 0, 0, 0, 0, ''),
('Ndola', 1, 1, 0, 1, 0, 0, ''),
('Livingstone', 1, 1, 0, 1, 1, 0, ''),
('Luanshya', 1, 0, 0, 0, 1, 0, ''),
('Kasama', 1, 1, 0, 1, 0, 0, ''),
('Chipata', 1, 0, 0, 0, 0, 0, ''),
('Chingola', 1, 1, 0, 1, 1, 0, '');

insert into Tips (tipsKey, tipsName, tipsContent) values
('report.inventory.generate', 'Generate Inventory' ,'Click the Generate Inventory Report button below to generate a report of your products.'),
('testresults.find', 'Find/Edit Test Results', 'Find blood typing and TTI results by collection number'),
('products.find', 'Find/Edit Products', 'Find Products by collection number/product number/product type.'),
('products.findproduct.productsummary', 'Product Summary Page', 'Product details shown below. Discard, split or pool this product using the buttons above.'),
('collectedSamples.find', 'Find/Edit Collections', 'Find Collections by collection number/blood bag type/collection center/collection site.'),
('collections.findcollection.collectionsummary', 'Collection Summary', 'Showing details of Collection below. Click on Edit to modify the details of this collection.'),
('donors.finddonor', 'Find/Edit Donors', 'Find Donors by donor number or part of donor first name or last name. Filter donors by Blood Group.'),
('donors.finddonor.donorsummary', 'Donor Summary', 'View previous donations by this donor by selecting View Donation History. Add a new collection for this donor by selecting Add Collection.'),
('testresults.worksheet', 'Enter worksheet results', 'Find a generated worksheet and enter test results for collections in that worksheet'),
('requests.findpending', 'Find Pending Requests', 'To issue products first find pending requests for a given site by product type. Optionally specify dates to filter requests made on or after Request date and required on or before Required date'),
('requests.findpending.requestsummary', 'Request Summary Page', 'Click on Find Matching Products button above to find all available products that are compatible for the given request.'),
('requests.findpending.findmatchingproducts', 'Matching Products for request', 'Select the products you want to issue from the table below and then click on Issue button.'),
('requests.addcompatibilityresult', 'Add compatibility tests for request', 'Record if a given product is compatible or not with the patient blood sample for the given request.'),
('report.inventory.productinventorychart', 'Product Inventory Report', 'Products in Inventory by Product Type and Blood Group. Click the columns to view products by Age. Click again to return.'),
('report.products.discardedproductsreport', 'Discarded Products Over Time', 'Number of products discarded over time categorized by blood group'),
('report.products.issuedproductsreport', 'Products Issued Over Time', 'Number of products issued over time categorized by blood group'),
('report.collections.collectionsreport', 'Collections Report', 'Track number collections done within a given date range for specific centers and sites. View daily, monthly, yearly numbers.'),
('report.collections.testresultsreport', 'Test Results Report', 'Track TTI numbers by collection site and collection center done within a given date range for specific centers and sites. View daily, monthly, yearly numbers.'),
('usage.addusage', 'Add Usage Form', 'Record usage of a product within a hospital. Optionally specify Hospital name, ward, patient name.');

insert into CrossmatchType (crossmatchType, isDeleted) values
('Saline @ 37 degrees', '0'),
('Anti Human Globulin', '0');

insert into GenericConfig (propertyName, propertyValue, propertyOwner) values
("dateFormat", "MM/dd/yy", "page"),
("dateTimeFormat", "MM/dd/yy hh:mm:ss a", "page"),

("rowHeight", "30", "collectionsWorksheet"),
("columnWidth", "100", "collectionsWorksheet"),
("collectionNumber", "true", "collectionsWorksheet"),
("testedOn", "false", "collectionsWorksheet"),
("Blood ABO", "true", "collectionsWorksheet"),
("Blood Rh", "true", "collectionsWorksheet"),
("HIV", "true", "collectionsWorksheet"),
("HBV", "true", "collectionsWorksheet"),
("HCV", "true", "collectionsWorksheet"),
("Syphilis", "true", "collectionsWorksheet"),

("haemoglobinUnit", "g/dL", "measurementUnit"),
("bloodPressureUnit", "mmHg", "measurementUnit"),

("ageLimitsEnabled", "true", "addDonorRequirements"),
("minimumAge", "16", "addDonorRequirements"),
("maximumAge", "65", "addDonorRequirements"),
("minimumWeight", "65", "addDonorRequirements"),
("donorWeightUnit", "Kg.", "addDonorRequirements"),
("daysBetweenConsecutiveDonations", "90", "addDonorRequirements"),

("ageLimitsEnabled", "true", "donationRequirements"),
("minimumAge", "16", "donationRequirements"),
("maximumAge", "65", "donationRequirements"),
("minimumWeight", "65", "donationRequirements"),
("donorWeightUnit", "Kg.", "donationRequirements"),
("daysBetweenConsecutiveDonations", "90", "donationRequirements"),
("donorDeferralCheckRequired", "true", "donationRequirements"),
("donorBloodGroupMatchRequired", "true", "donationRequirements"),
("donorHistoryCheckRequired", "true", "donationRequirements"),

("useShortNameForProductTypes", "true" ,"products"),

("allowPediComponentsFromFirstTimeDonors", "true", "componentSplit"),

("donorRecordRequired", "true", "productReleaseRequirements"),

("allowProductsWithoutCollection", "true", "productCreationRequirements"),
("allowImportedProducts", "true", "productCreationRequirements");

insert into CrossmatchType (crossmatchType, isDeleted) values
('Saline @ 37 degrees', '0'),
('Anti Human Globulin', '0');

insert into ConfiguredPreDonationTest (
testName, testType, resultDataType,
lowerLimit, upperLimit,
acceptableResults, allowedResults,
negateResult, enabled) values
("Donor age", "RANGE_CHECK", "INTEGER",
 "16", "65",
 "", "",
 "0", "1"
),
("Donor weight", "RANGE_CHECK", "INTEGER",
 "50", "90",
 "", "",
 "0", "1"
),
("Donor Haemoglobin Level", "RANGE_CHECK", "REAL",
 "12.5", "18.0",
 "", "",
 "0", "1"
),
("Donor blood pressure systolic", "RANGE_CHECK", "INTEGER",
 "115", "120",
 "", "",
 "0", "1"
),
("Donor blood pressure diastolic", "RANGE_CHECK", "INTEGER",
 "75", "80",
 "", "",
 "0", "1"
);

insert into DeferralReason (reason, isDeleted) values
("Other", 0),
("Symptoms of cold", 0),
("Travel to malarial region", 0),
("On medication", 0);