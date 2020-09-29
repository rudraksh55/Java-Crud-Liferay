create table Employee (
	uuid_ VARCHAR(75) null,
	employeeId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	fullName VARCHAR(75) null,
	birthDate DATE null,
	gender VARCHAR(75) null,
	department VARCHAR(75) null,
	age VARCHAR(75) null,
	email VARCHAR(75) null
);