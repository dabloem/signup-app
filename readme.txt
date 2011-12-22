# Readme

## Goal

The application should process sign-up request, received as a plain form POST
http request. The requestor will be notified by a confirmation email. The
confirmation is send with a http GET

  * form http POST request

  * url http GET request

Those requests are approved or denied by the administrator

Configuration for JAAS security.

1. There are two roles in the application, ROLE_AMINISTRATOR can approve and deny signup request, ROLE_VIEWER can view the pages.

2. the /admin path is protected by default, use FORM based authentication, please refer the configuration in web.xml

3. The User/Roles related data are stored in the database, this can configured in the JBoss AS, JBoss support other options, such ldap.

 a) Create database signup(I used mysql), and grant all privileges to signupuser/signuppass.
 b) create a datasource in ${JBOSS_HOME}/standlone/configuration/standalone.xml.
 	Find <subsystem xmlns="urn:jboss:domain:datasources:1.0"> tag, add a new datasource.
 
  				<datasource jndi-name="java:jboss/datasources/signupDS" pool-name="signupPool" enabled="true" jta="true" use-java-context="true" use-ccm="true">
                    <connection-url>
                        jdbc:mysql://localhost:3306/signup
                    </connection-url>
                    <driver-class>
                        com.mysql.jdbc.Driver
                    </driver-class>
                    <driver>
                        mysql-connector-java.jar
                    </driver>
                    <pool>
                        <min-pool-size>
                            20
                        </min-pool-size>
                        <max-pool-size>
                            100
                        </max-pool-size>
                        <prefill>
                            false
                        </prefill>
                        <use-strict-min>
                            false
                        </use-strict-min>
                        <flush-strategy>
                            FailingConnectionOnly
                        </flush-strategy>
                    </pool>
                    <security>
                        <user-name>
                            signupuser
                        </user-name>
                        <password>
                            signuppass
                        </password>
                    </security>
                </datasource>
 
 	c) Create security configuration.
 	Find <subsystem xmlns="urn:jboss:domain:security:1.0"> tag, add the following configuration.
 	
 	 		<security-domains>
                <security-domain name="other">
                    <authentication>
                        <login-module code="UsersRoles" flag="required"/>
                    </authentication>
                </security-domain>
                <security-domain name="SignupRealm">
                    <authentication>
                        <login-module code="Database" flag="required">
                            <module-option name="dsJndiName" value="java:jboss/datasources/signupDS"/>
                            <module-option name="principalsQuery" value="select password from users where username=?"/>
                            <module-option name="rolesQuery" value=" select rolename, 'Roles' from user_role where username=?"/>
                        </login-module>
                    </authentication>
                </security-domain>
            </security-domains>
            
   All the configuration here is for JBoss 7 AS, Glassfish provide more friendly administration console.         
            
 	