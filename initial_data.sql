insert into FormField
(form, field, defaultDisplayName, defaultValue, maxLength,
 hidden, isHidable,
 isRequired, canBeOptional,
 autoGenerate, isAutoGeneratable,
 useCurrentTime, isTimeField) values
('Donor', 'donorNumber', 'Donor #', '', 15,
 '0', '0',
 '1', '0',
 '1', '1',
'0', '0'),
('Donor', 'firstName', 'First Name', '', 0,
'0', '1',
'1', '1',
'0', '0',
'0', '0'),
('Donor', 'middleName', 'Middle Name', '', 0,
'1', '1',
'0', '1',
'0', '0',
'0', '0'),
('Donor', 'lastName', 'Last Name', '', 0,
'0', '1',
'1', '1',
'0', '0',
'0', '0'),
('Donor', 'callingName', 'Calling Name', '', 0,
'1', '1',
'0', '1',
'0', '0',
'0', '0'),
('Donor', 'birthDate', 'Birth Date', '', 0,
'0', '1',
'1', '1',
'0', '0',
'0', '0'),
('Donor', 'birthDateEstimated', 'Estimated', '', 0,
'0', '1',
'0', '1',
'0', '0',
'0', '0'),
('Donor', 'age', 'Age', '', 0,
'1', '1',
'0', '1',
'0', '0',
'0', '0'),
('Donor', 'gender', 'Gender M or F', '', 0,
'0', '1',
'1', '1',
'0', '0',
'0', '0'),
('Donor', 'nationalID', 'National ID', '', 0,
'1', '1',
'0', '1',
'0', '0',
'0', '0'),
('Donor', 'bloodGroup', 'Blood Group', '', 0,
'0', '1',
'0', '1',
'0', '0',
'0', '0'),
('Donor', 'donorStatus', 'Donor status', '', 0,
'0', '1',
'0', '1',
'0', '0',
'0', '0'),
('Donor', 'address', 'Postal Address', '', 0,
'0', '1',
'0', '1',
'0', '0',
'0', '0'),
('Donor', 'city', 'City', '', 0,
'0', '1',
'0', '1',
'0', '0',
'0', '0'),
('Donor', 'province', 'Province', '', 0,
'0', '1',
'0', '1',
'0', '0',
'0', '0'),
('Donor', 'district', 'District', '', 0,
'0', '1',
'0', '1',
'0', '0',
'0', '0'),
('Donor', 'state', 'State', '', 0,
'1', '1',
'0', '1',
'0', '0',
'0', '0'),
('Donor', 'country', 'Country', '', 0,
'0', '1',
'0', '1',
'0', '0',
'0', '0'),
('Donor', 'zipcode', 'Zip Code', '', 0,
'0', '1',
'0', '1',
'0', '0',
'0', '0'),
('Donor', 'phoneNumber', 'Phone number', '', 0,
'0', '1',
'0', '1',
'0', '0',
'0', '0'),
('Donor', 'otherPhoneNumber', 'Other phone number', '', 0,
'1', '1',
'0', '1',
'0', '0',
'0', '0'),
('Donor', 'donorPanel', 'Donor Panel', '', 0,
'0', '1',
'0', '1',
'0', '0',
'0', '0'),
('Donor', 'preferredContactMethod', 'Preferred contact method', '', 0,
'0', '1',
'0', '1',
'0', '0',
'0', '0'),
('Donor', 'notes', 'Notes', '', 0,
'0', '1',
'0', '1',
'0', '0',
'0', '0'),
('Donor', 'dateOfLastDonation', 'Date of last donation', '', 0,
'0', '1',
'0', '1',
'0', '0',
'0', '0'),
('Donor', 'createdDate', 'Created on', '', 0,
'0', '0',
'0', '0',
'0', '0',
'0', '0'),
('Donor', 'createdBy', 'Created by', '', 0,
'0', '0',
'0', '0',
'0', '0',
'0', '0'),
('Donor', 'lastUpdatedTime', 'Last modified on', '', 0,
'0', '0',
'0', '0',
'0', '0',
'0', '0'),
('Donor', 'lastUpdatedBy', 'Last modified by', '', 0,
'0', '0',
'0', '0',
'0', '0',
'0', '0');

insert into FormField
(form, field, defaultDisplayName, defaultValue, maxLength,
 hidden, isHidable,
 isRequired, canBeOptional,
 autoGenerate, isAutoGeneratable,
 useCurrentTime, isTimeField) values
('DonorDeferral', 'deferredOn', 'Deferred on', '', 15,
 '0', '0',
 '1', '0',
 '0', '0',
 '0', '0'),
('DonorDeferral', 'deferredUntil', 'Deferred until', '', 15,
 '0', '0',
 '1', '0',
 '0', '0',
 '0', '0'),
('DonorDeferral', 'deferredBy', 'Deferred by', '', 15,
 '0', '0',
 '1', '0',
 '0', '0',
 '0', '0'),
('DonorDeferral', 'deferralReason', 'Deferral Reason', '', 15,
 '0', '0',
 '1', '0',
 '0', '0',
 '0', '0'),
('DonorDeferral', 'deferralReasonText', 'Other details', '', 15,
 '0', '0',
 '0', '1',
 '0', '0',
 '0', '0');

insert into FormField
(form, field, defaultDisplayName, defaultValue, maxLength,
 hidden, isHidable,
 isRequired, canBeOptional,
 autoGenerate, isAutoGeneratable,
 useCurrentTime, isTimeField) values
('Donation', 'donationIdentificationNumber', 'Donation Identification Number', '', 0,
 '0', '0',
 '1', '0',
 '0', '1',
 '0', '0'),
('Donation', 'donationBatchNumber', 'Batch number', '', 0,
 '0', '1',
 '0', '1',
 '0', '0',
 '0', '0'),
 ('Donation', 'donationDate', 'Donated on', '', 0,
 '0', '0',
 '1', '0',
 '0', '0',
 '1', '1'),
('Donation', 'donorNumber', 'Donor number', '', 0,
 '0', '1',
 '1', '1',
 '0', '0',
 '0', '0'),
('Donation', 'donationType', 'Donation type', 'Voluntary', 0,
 '0', '1',
 '1', '1',
 '0', '0',
 '0', '0'),
('Donation', 'bloodBagType', 'Blood Bag type', 'Single', 0,
 '0', '1',
 '1', '1',
 '0', '0',
 '0', '0'),
('Donation', 'useParametersFromBatch', 'Donation center/site from batch?', '', 0,
 '0', '1',
 '0', '1',
 '0', '0',
 '0', '0'),
('Donation', 'notes', 'Notes', '', 0,
 '0', '1',
 '0', '1',
 '0', '0',
 '0', '0'),
('Donation', 'bloodTypingStatus', 'Blood Typing Status', '', 0,
 '0', '0',
 '0', '0',
 '0', '0',
 '0', '0'),
('Donation', 'bloodAbo', 'Blood ABO', '', 0,
 '0', '0',
 '0', '0',
 '0', '0',
 '0', '0'),
('Donation', 'bloodRh', 'Blood Rh', '', 0,
 '0', '0',
 '0', '0',
 '0', '0',
 '0', '0'),
('Donation', 'ttiStatus', 'TTI Status', '', 0,
 '0', '0',
 '0', '0',
 '0', '0',
 '0', '0'),
('Donation', 'createdDate', 'Created on', '', 0,
 '0', '0',
 '0', '0',
 '0', '0',
 '0', '0'),
('Donation', 'createdBy', 'Created by', '', 0,
 '0', '0',
 '0', '0',
 '0', '0',
 '0', '0'),
('Donation', 'lastUpdatedTime', 'Last modified on', '', 0,
 '0', '0',
 '0', '0',
 '0', '0',
 '0', '0'),
('Donation', 'lastUpdatedBy', 'Last modified by', '', 0,
 '0', '0',
 '0', '0',
 '0', '0',
 '0', '0');

insert into FormField
(form, field, defaultDisplayName, defaultValue, maxLength,
 hidden, isHidable,
 isRequired, canBeOptional,
 autoGenerate, isAutoGeneratable,
 useCurrentTime, isTimeField) values
('DonationBatch', 'batchNumber', 'Batch number', '', 0,
 '0', '0',
 '1', '0',
 '1', '1',
 '0', '0'),
('DonationBatch', 'notes', 'Notes', '', 0,
 '0', '1',
 '0', '1',
 '0', '0',
 '0', '0'),
('DonationBatch', 'createdDate', 'Created on', '', 0,
 '0', '0',
 '0', '0',
 '0', '0',
 '0', '0'),
('DonationBatch', 'createdBy', 'Created by', '', 0,
 '0', '0',
 '0', '0',
 '0', '0',
 '0', '0'),
('DonationBatch', 'lastUpdatedTime', 'Last modified on', '', 0,
 '0', '0',
 '0', '0',
 '0', '0',
 '0', '0'),
('DonationBatch', 'lastUpdatedBy', 'Last modified by', '', 0,
 '0', '0',
 '0', '0',
 '0', '0',
 '0', '0');

insert into FormField
(form, field, defaultDisplayName, defaultValue, maxLength,
 hidden, isHidable,
 isRequired, canBeOptional,
 autoGenerate, isAutoGeneratable,
 useCurrentTime, isTimeField) values
('Worksheet', 'worksheetNumber', 'Worksheet Number', '', 0,
 '0', '0',
 '1', '0',
 '1', '1',
 '0', '0'),
('Worksheet', 'worksheetType', 'Worksheet type', '', 0,
 '0', '0',
 '1', '0',
 '0', '0',
 '0', '0'),
('Worksheet', 'notes', 'Notes', '', 0,
 '0', '1',
 '0', '1',
 '0', '0',
 '0', '0'),
('Worksheet', 'createdDate', 'Created on', '', 0,
 '0', '0',
 '0', '0',
 '0', '0',
 '0', '0'),
('Worksheet', 'createdBy', 'Created by', '', 0,
 '0', '0',
 '0', '0',
 '0', '0',
 '0', '0'),
('Worksheet', 'lastUpdatedTime', 'Last modified on', '', 0,
 '0', '0',
 '0', '0',
 '0', '0',
 '0', '0'),
('Worksheet', 'lastUpdatedBy', 'Last modified by', '', 0,
 '0', '0',
 '0', '0',
 '0', '0',
 '0', '0');

insert into FormField
(form, field, defaultDisplayName, defaultValue, maxLength,
 hidden, isHidable,
 isRequired, canBeOptional,
 autoGenerate, isAutoGeneratable,
 useCurrentTime, isTimeField) values
('Component', 'donationIdentificationNumber', 'Donation indentification number', '', 0,
 '0', '1',
 '1', '0',
 '0', '0',
'0', '0'),
('Component', 'productType', 'Product Type', '', 0,
 '0', '1',
 '1', '0',
 '0', '0',
'0', '0'),
('Component', 'createdOn', 'Creation date', '', 0,
 '0', '1',
 '1', '0',
 '0', '0',
 '0', '0'),
('Component', 'expiresOn', 'Expiry date', '', 0,
 '0', '0',
 '1', '1',
 '0', '0',
'0', '0'),
('Component', 'issuedOn', 'Issue date', '', 0,
 '0', '0',
 '0', '1',
 '0', '0',
'0', '0'),
('Component', 'status', 'Product status', '', 0,
 '0', '0',
 '0', '1',
 '0', '0',
'0', '0'),
('Component', 'bloodGroup', 'Blood group', '', 0,
 '0', '0',
 '0', '1',
 '0', '0',
'0', '0'),
('Component', 'age', 'Age', '', 0,
 '0', '0',
 '0', '1',
 '0', '0',
'0', '0'),
('Component', 'discardedOn', 'Discarded on', '', 0,
 '0', '0',
 '0', '1',
 '0', '0',
'0', '0'),
('Component', 'discardedBy', 'Discarded by', '', 0,
 '0', '0',
 '0', '1',
 '0', '0',
'0', '0'),
('Component', 'notes', 'Notes', '', 0,
 '0', '0',
 '0', '1',
 '0', '0',
'0', '0'),
('Component', 'statusChangeType', 'Status change type', '', 0,
 '0', '0',
 '0', '0',
 '0', '0',
'0', '0'),
('Component', 'issuedTo', 'Issued to', '', 0,
 '0', '0',
 '0', '0',
 '0', '0',
'0', '0'),
('Component', 'statusChangedOn', 'Status changed on', '', 0,
 '0', '0',
 '0', '0',
 '0', '0',
'0', '0'),
('Component', 'statusChangedBy', 'Status changed by', '', 0,
 '0', '0',
 '0', '0',
 '0', '0',
'0', '0'),
('Component', 'statusChangeReason', 'Status change reason', '', 0,
 '0', '0',
 '0', '0',
 '0', '0',
'0', '0'),
('Component', 'statusChangeReasonText', 'Status change details', '', 0,
 '0', '0',
 '0', '0',
 '0', '0',
'0', '0'),
('Component', 'createdDate', 'Created on', '', 0,
'0', '0',
'0', '0',
'0', '0',
'0', '0'),
('Component', 'createdBy', 'Created by', '', 0,
'0', '0',
'0', '0',
'0', '0',
'0', '0'),
('Component', 'lastUpdatedTime', 'Last modified on', '', 0,
'0', '0',
'0', '0',
'0', '0',
'0', '0'),
('Component', 'lastUpdatedBy', 'Last modified by', '', 0,
'0', '0',
'0', '0',
'0', '0',
'0', '0');

insert into FormField
(form, field, defaultDisplayName, defaultValue, maxLength,
 hidden, isHidable,
 isRequired, canBeOptional,
 autoGenerate, isAutoGeneratable,
 useCurrentTime, isTimeField) values
('TTIForm', 'donationIdentificationNumber', 'Donation indentification number', '', 0,
 '0', '0',
 '1', '0',
 '0', '0',
 '0', '0');

insert into FormField
(form, field, defaultDisplayName, defaultValue, maxLength,
 hidden, isHidable,
 isRequired, canBeOptional,
 autoGenerate, isAutoGeneratable,
 useCurrentTime, isTimeField) values
('Request', 'requestNumber', 'Request number', '', 0,
 '0', '0',
 '1', '0',
 '1', '1',
'0', '0'),
('Request', 'requestDate', 'Request date', '', 0,
 '0', '0',
 '1', '0',
 '0', '0',
 '1', '1'),
('Request', 'requiredDate', 'Required date', '', 0,
 '0', '0',
 '1', '0',
 '0', '0',
'0', '0'),
('Request', 'requestSite', 'Request site', '', 0,
 '0', '1',
 '1', '1',
 '0', '0',
'0', '0'),
('Request', 'numUnitsRequested', 'No. of units requested', '', 0,
 '0', '0',
 '1', '0',
 '0', '0',
'0', '0'),
('Request', 'numUnitsIssued', 'No. of units issued', '', 0,
 '0', '0',
 '0', '1',
 '0', '0',
'0', '0'),
('Request', 'requestStatus', 'Request status', '', 0,
 '0', '0',
 '0', '1',
 '0', '0',
'0', '0'),
('Request', 'patientBloodAbo', 'Blood ABO', '', 0,
 '0', '0',
 '0', '1',
 '0', '0',
'0', '0'),
('Request', 'patientBloodRh', 'Blood Rh', '', 0,
 '0', '0',
 '0', '1',
 '0', '0',
'0', '0'),
('Request', 'productType', 'Product type', '', 0,
 '0', '0',
 '1', '0',
 '0', '0',
'0', '0'),
('Request', 'requestType', 'Request type', '', 0,
 '0', '1',
 '1', '1',
 '0', '0',
'0', '0'),
('Request', 'patientNumber', 'Patient number', '', 0,
 '0', '1',
 '0', '1',
 '0', '0',
'0', '0'),
('Request', 'patientFirstName', 'Patient first name', '', 0,
 '0', '1',
 '0', '1',
 '0', '0',
'0', '0'),
('Request', 'patientLastName', 'Patient last name', '', 0,
 '0', '1',
 '0', '1',
 '0', '0',
'0', '0'),
('Request', 'patientDiagnosis', 'Patient diagnosis', '', 0,
 '0', '1',
 '0', '1',
 '0', '0',
'0', '0'),
('Request', 'patientBirthDate', 'Patient date of birth', '', 0,
 '0', '1',
 '0', '1',
 '0', '0',
'0', '0'),
('Request', 'patientAge', 'Patient age', '', 0,
 '0', '1',
 '0', '1',
 '0', '0',
'0', '0'),
('Request', 'patientGender', 'Patient gender', '', 0,
 '0', '1',
 '0', '1',
 '0', '0',
'0', '0'),
('Request', 'ward', 'Ward', '', 0,
 '0', '1',
 '0', '1',
 '0', '0',
'0', '0'),
('Request', 'department', 'Department', '', 0,
 '0', '1',
 '0', '1',
 '0', '0',
'0', '0'),
('Request', 'hospital', 'Hospital', '', 0,
 '0', '1',
 '0', '1',
 '0', '0',
'0', '0'),
('Request', 'requestedBy', 'Requested by', '', 0,
 '0', '1',
 '0', '1',
 '0', '0',
'0', '0'),
('Request', 'notes', 'Notes', '', 0,
 '0', '1',
 '0', '1',
 '0', '0',
'0', '0'),
('Request', 'createdDate', 'Created on', '', 0,
'0', '0',
'0', '0',
'0', '0',
'0', '0'),
('Request', 'createdBy', 'Created by', '', 0,
'0', '0',
'0', '0',
'0', '0',
'0', '0'),
('Request', 'lastUpdatedTime', 'Last modified on', '', 0,
'0', '0',
'0', '0',
'0', '0',
'0', '0'),
('Request', 'lastUpdatedBy', 'Last modified by', '', 0,
'0', '0',
'0', '0',
'0', '0',
'0', '0');

insert into FormField
(form, field, defaultDisplayName, defaultValue, maxLength,
 hidden, isHidable,
 isRequired, canBeOptional,
 autoGenerate, isAutoGeneratable,
 useCurrentTime, isTimeField) values
('CompatibilityTest', 'donationIdentificationNumber', 'Donation indentification number', '', 0,
 '0', '0',
 '1', '0',
 '0', '0',
 '0', '0'),
 ('CompatibilityTest', 'requestNumber', 'Component number', '', 0,
 '0', '1',
 '1', '0',
 '0', '0',
 '0', '0'),
('CompatibilityTest', 'compatibilityResult', 'Compatibility result', '', 0,
 '0', '1',
 '1', '0',
 '0', '0',
 '0', '0'),
 ('CompatibilityTest', 'compatibilityTestDate', 'Compatibility test date', '', 0,
 '0', '1',
 '1', '0',
 '0', '0',
 '0', '0'),
 ('CompatibilityTest', 'transfusedBefore', 'Transfused before', '', 0,
 '1', '1',
 '0', '0',
 '0', '0',
 '0', '0'),
 ('CompatibilityTest', 'crossmatchType', 'Crossmatch type', '', 0,
 '0', '1',
 '1', '0',
 '0', '0',
 '0', '0'),
 ('CompatibilityTest', 'testedBy', 'Tested by', '', 0,
 '0', '1',
 '0', '0',
 '0', '0',
 '0', '0'),
 ('CompatibilityTest', 'notes', 'Notes', '', 0,
 '0', '1',
 '0', '1',
 '0', '0',
 '0', '0'),
 ('CompatibilityTest', 'createdDate', 'Created on', '', 0,
  '0', '0',
  '0', '0',
  '0', '0',
 '0', '0'),
('CompatibilityTest', 'createdBy', 'Created by', '', 0,
 '0', '0',
 '0', '0',
 '0', '0',
 '0', '0'),
('CompatibilityTest', 'lastUpdatedTime', 'Last modified on', '', 0,
 '0', '0',
 '0', '0',
 '0', '0',
 '0', '0'),
('CompatibilityTest', 'lastUpdatedBy', 'Last modified by', '', 0,
 '0', '0',
 '0', '0',
 '0', '0',
 '0', '0');

insert into FormField
(form, field, defaultDisplayName, defaultValue, maxLength,
 hidden, isHidable,
 isRequired, canBeOptional,
 autoGenerate, isAutoGeneratable,
 useCurrentTime, isTimeField) values
('Usage', 'component', 'Component', '', 0,
 '0', '0',
 '1', '0',
 '0', '0',
 '0', '0'),
('Usage', 'donationIdentificationNumber', 'Donation indentification number', '', 0,
 '0', '0',
 '1', '0',
 '0', '0',
 '0', '0'),
('Usage', 'productType', 'Product type', '', 0,
 '0', '0',
 '1', '0',
 '0', '0',
 '0', '0'),
('Usage', 'usageDate', 'Usage date', '', 0,
 '0', '0',
 '1', '0',
 '0', '0',
 '0', '0'),
('Usage', 'hospital', 'Hospital', '', 0,
 '0', '1',
 '0', '1',
 '0', '0',
 '0', '0'),
('Usage', 'ward', 'Ward', '', 0,
 '0', '0',
 '0', '1',
 '0', '0',
 '0', '0'),
('Usage', 'patientName', 'Patient name', '', 0,
 '0', '0',
 '0', '1',
 '0', '0',
 '0', '0'),
 ('Usage', 'useIndication', 'Use indication', '', 0,
 '0', '0',
 '0', '1',
 '0', '0',
 '0', '0'),
 ('Usage', 'usedBy', 'Used by', '', 0,
 '0', '0',
 '0', '1',
 '0', '0',
 '0', '0'),
 ('Usage', 'notes', 'Notes', '', 0,
 '0', '0',
 '0', '1',
 '0', '0',
 '0', '0'),
 ('Usage', 'createdDate', 'Created on', '', 0,
 '0', '0',
 '0', '0',
 '0', '0',
 '0', '0'),
 ('Usage', 'createdBy', 'Created by', '', 0,
 '0', '0',
 '0', '0',
 '0', '0',
 '0', '0'),
 ('Usage', 'lastUpdatedTime', 'Last modified on', '', 0,
 '0', '0',
 '0', '0',
 '0', '0',
 '0', '0'),
 ('Usage', 'lastUpdatedBy', 'Last modified by', '', 0,
 '0', '0',
 '0', '0',
 '0', '0',
 '0', '0');

insert into CrossmatchType (crossmatchType, isDeleted) values
('Saline @ 37 degrees', '0'),
('Anti Human Globulin', '0');

insert into GenericConfig (propertyName, propertyValue, propertyOwner) values
("rowHeight", "30", "donationsWorksheet"),
("columnWidth", "100", "donationsWorksheet"),
("donationIdentificationNumber", "true", "donationsWorksheet"),

("haemoglobinUnit", "g/dL", "measurementUnit"),
("bloodPressureUnit", "mmHg", "measurementUnit"),

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

("allowComponentsWithoutDonation", "true", "componentCreationRequirements"),
("allowImportedComponents", "true", "componentCreationRequirements");


insert into WellType (wellType, requiresSample, isDeleted) values
("Sample", 1, 0),
("Blank", 0, 0),
("Negative control", 0,  0),
("Positive control", 0, 0);

insert into GenericConfig (propertyName, propertyValue, propertyOwner) values
("leaveOutDonationsProbability", "0.10", "createdata"),
("incorrectBloodTypeProbability", "0.05", "createdata"),
("unsafeProbability", "0.05", "createdata");

insert into GenericConfig (propertyName, propertyValue, propertyOwner) values
("bloodTypingMechanism", "BLOODTYPING_TEST_RESULTS_ELISA", "labsetup"),
("ttiMechanism", "TTI_ELISA", "labsetup"),
("recordUsage", "true", "labsetup"),
("crossmatchProcedure", "CROSSMATCH_DONE_CAN_SKIP", "labsetup");

insert into GenericConfig (propertyName, propertyValue, propertyOwner) values
("bloodTypingContext", "RECORD_BLOOD_TYPING_TESTS", "labsetup"),
("bloodTypingElisa", "true", "labsetup"),
("bloodTypingWorksheets", "false", "labsetup");

insert into GenericConfig (propertyName, propertyValue, propertyOwner) values
("ttiElisa", "true", "labsetup"),
("ttiWorksheets", "false", "labsetup"),
("recordMachineReadingsForTTI", "true", "labsetup");

insert into GenericConfig (propertyName, propertyValue, propertyOwner) values
("useWorksheets", "false", "labsetup");

insert into GenericConfig (propertyName, propertyValue, propertyOwner) values
("donorsTabEnabled", "true", "labsetup"),
("donationsTabEnabled", "true", "labsetup"),
("productsTabEnabled", "true", "labsetup"),
("testResultsTabEnabled", "true", "labsetup"),
("requestsTabEnabled", "true", "labsetup"),
("usageTabEnabled", "true", "labsetup"),
("reportsTabEnabled", "true", "labsetup");

insert into GenericConfig (propertyName, propertyValue, propertyOwner) values
("showCrossmatchConfirmation", "true", "labsetup"),
("allowSkipCrossmatch", "true", "labsetup");
