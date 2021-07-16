Dynamo Framework - Spring based framework for developing web & mobile applications.

2.4.0 (July 16, 2021)
-------------------
●	Updated Drools Decision support libraries.

2.3.4 (June 15, 2021)
-------------------
●	New Inventory Item added.

2.3.3 (June 1, 2021)
-------------------
●	Updated IAM user creation policy.

2.3.2 (May 26, 2021)
-------------------
●	Fixed S3 Bucket creation CORS issue.

2.3.1 (May 26, 2021)
-------------------
●	Fixed S3 Bucket creation CORS issue.

2.3.0 (May 24, 2021)
-------------------
●	Added AWS IAM management & S3 Bucket management module.

2.2.0 (May 5, 2021)
-------------------
●	Added RabbitMQ module.
●	Updated RabbitMQ configuration.

2.1.5 (April 16, 2021)
----------------------
●   Fixed date format to MM/dd/YYYY in Audit log.
●   Fixed Group error during User creation.

2.1.1 -2.1.4: (April 6, 2021)
----------------------------
●   Fixed issues while using maven versions plugin.

2.1.0:
------
●   Added Inventory module for Inventory item management.

2.0.0:
------
●   Updated Spring Boot version to 2.4.0 and Spring Version to 5.3.1
●   Added UserOAuthToken builder support in Auth module.

1.9.0:
------
●   Updated user creation functionality.

1.8.2:
------
●   Added Address entity to manage postal addresses.
●   Updated default Organization creation functionality to support addresses.

1.8.1:
------
●   Updated code to set additional configuration attributes in Email templates.

1.8.0:
------
●   Added support for Checkstyle, PMD and Javadocs.

1.7.0:
------
●   Updated controller to support password reset requests from mobile applications.

1.6.1:
------
●   Fixed Type Adapter Not registered error while creating user

1.6.0:
------
●   Updated timezone options in User entity based on Zoom's website.
●   Added support to manage user specific OAuth tokens.
●   Fixed code that sets the user roles CSV in the current DTO entity.
●   Commented out code to capture method output data in audit log due to issues in GSON parsing for certain cases. Will have to revisit later.

1.5.0:
------
●   Updated code to support OpenJDK 13.
●   Fixed issue where role, group and user saves did not function because of wrong transaction property.

1.4.0:
------
●   Added service method to create a user with roles and groups.
●   Added option to login using Email Id, Unique User Id or both.
●   Updated Spring boot version to 2.2.0RELEASE and Spring version to 5.2.0.RELEASE.
●   Added validate goal to Maven build.
●   Added service to create Multipart file from byte array.

1.2.0:
------
●   Added dynamo-admin-webapp module inside Dynamo Framework project.

1.1.0:
------
●  Removed Maven dependencies module and brought in all dependencies into Parent POM.
●  Added dynamo-webapp module inside Dynamo Framework project.
●  Renamed organization and audit HTML directories to have 'dynamo-' prefix and updated controllers accordingly.

1.0.0:
------
●  Removed dynamo-sample-apps module. Sample applications will be managed from a separate project.

0.16.0:
------
●  Added dynamo-communication module with SMS support using AWS, SINCH and Textlocal services. 
●  Added general and module specific (Auth, Audit, Communication) spring configuration classes to manage properties.  
●  Removed logic to check password strength.
●  Added support to programmatically set properties in email templates sent to users. 
	Currently, Forgot password and Complete sign-up functions are covered.

0.15.0:
------
●  Added support to set time zone for a user. 
●  Added support to store audit log date with time zone information. 
●  Added support to display audit log data as per user's time zone (zone ID) information. 
●  Added logic to check password strength.


0.14.0:
------
●  Added support to track audit log by organization.
●  Added Spring security annotations to restrict Dynamo modules & APIs to appropriate roles.
●  Extended Spring security UserDetails to include organization ID.
●  Renamed SYSTEM_ADMIN user to ROLE_SYSTEM_ADMIN to be used in WebSecurity config support.
●  Renamed URL endpoints for audit and organziation modules. Removed the 'admin' prefix previously used.

0.13.0:
------
●  Added REST API to retrieve user by email.
●  Updated password encoding logic.

0.12.0:
------
●  Refactored organization module such that each organization can have its own set of roles and groups.
●  Updated authentication workflow such that email is now used for application login. Previously used username field is removed
	and no longer used in the authentication flow.
●  Updated application's look and feel to provide more whitespace and rounder corners for screen elements.
	
0.11.0:
------
●  Added support for OAuth2.0 based authentication. DB tables and support classed are manged in dynamo-oauth module. 

0.7.0:
------
●  Added file system storage service to manage file uploads.

0.5.0:
------
●  Revamped Dynamo Organization module UI with new styles.

0.4.1:
------
●  Added schema references to table and sequence names in all Dynamo entities.

0.4.0:
------
●  Refactored Auth module to include support for Authentication and Authorization.
●  Created LDAP module to authenticate users with LDAP directory like Apache Directory.
●  Created Audit module to support audit logging. Service methods can be annotated with audit classes to support all auditable events.
●  Created Admin module to add application configuration support with the help of Spring Cloud Config Server. Dynamo App, Dynamo Org and 
	Dynamo Auth modules are configured to act as Spring Cloud Config clients.

0.3.2:
------
●  Updated POM to include Javadocs and Source plugin to generate Javadocs for the framework.

0.3.0:
------
●  Updated Dynamo Auth module to include support for managing (create, update) users, groups and roles.
●  Added 'Forgot Password' support to Auth module where users can reset their password using a system generated link sent to their email.
●  Refactored Dynamo Auth's library module to include Spring MVC logic (controllers). They were previously managed in Auth's Webapp module.

0.2.0:
------
●  Updated Spring boot version from 1.5.2.RELEASE to 1.5.3.RELEASE. Spring version is 4.3.8.RELEASE.
●  Added support for Camunda Embedded form in workflow module. Users can specify 'FormKey' attribute in BPMN models to associate HTML/Thymeleaf forms for user tasks. 
●  Added Java Doc style comments for Workflow APIs.

0.1.0:
------
●  Added Auth module based on Spring Security.
●  Added Audit module based on AspectJ for adding audit functionality for user logins and method access.
●  Added Drools module for reference implementation of rules project.
●  Added Workflow module based on Camunda BPMN.
