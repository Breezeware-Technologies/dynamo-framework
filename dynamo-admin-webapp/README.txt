TSG Admin Webapp Release Notes

1.1.0:
------
●  Updated to Dynamo Framework version 1.0.0

1.3.0:
------
●  Updated email templates to use TSG application specific labels.
●  Removed password constraints while setting user password.
●  Fixed 500 server error while setting user password from the email link.

1.2.0:
------
●  Added support to display audit log data as per client's time zone (zone ID) information. 

1.1.0:
------
●  Updated security configuration to allow application access to users with 'ROLE_SYSTEM_ADMIN' role only.
●  Updated audit log to retrieve entries across all organizations.
●  Removed support to create groups, roles and users in the application. Going forward, these activities will be done in user application only.

0.2.0:
------
●  Added support for OpenID login using Realfactor, Onelogin, Okta and Google Authentication

0.1.0:
------
●  First version of TSG webapp with basic login functionality.