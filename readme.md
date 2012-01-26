# Readme

## Goal

The application should process sign-up request, received as a plain form POST
http request. The requestor will be notified by a confirmation email. The
confirmation is send with a http GET

  * form http POST request

  * url http GET request

Those requests are approved or denied by the administrator

## Prerequisite

1. Oracle JDK 6

    Download the latest JDK 6 from [Oralce](http://www.oracle.com). Install it into your system, set the *JAVA_HOME* variable and add *JAVA_HOME/bin* to path.

2. Apache Maven 3.0.x

    Download the Apache Maven from [Apache.org](http://www.apache.org). Extract the files to your local disk, set the *M2_HOME* variable and add *M2_HOME/bin* to path.

    Open your terminal, type **mvn -version** and press ENTER, you should see the following similar information.
        
        Apache Maven 3.0.4 (r1232337; 2012-01-17 16:44:56+0800)
        Maven home: D:\build\maven
        Java version: 1.6.0_30, vendor: Sun Microsystems Inc.
        Java home: D:\jdk6\jre
        Default locale: zh_CN, platform encoding: GBK
        OS name: "windows 7", version: "6.1", arch: "x86", family: "windows"


3. Eclipse 3.7 and JBoss Tools

    Download eclipse JEE bundle from [Eclipse.org](http://www.eclipse.org), and extract zip into your local disk. Start up Eclipse and follow these step to install JBoss Tools.

    1. Open EclipseMarket Dialog.
    2. Input "Jboss" in the search field.
    3. Click the "Install" to install JBoss Tools.
    4. Restart Eclipse according to the installation prompt.


4. (Optional) NetBeans 7.1

    If you select NetBeans as your preferred IDE, you can download the latest NetBeans IDE from [NetBeans.org](http://www.netbeans.org), the Java EE version is recommended. The installation progress is very simple, just click the next button following the the installing wizard.
  	
5. JBoss 7.0.2 Final
  
    Download the latest JBoss 7.0.2.Final from [JBoss.org](http://www.jboss.org), and extract the files into your local disk. You can set JBOSS_HOME variable, the location is the JBoss folder.

    You can register the JBoss server in your IDE. Eclipse user can open the "Server" view, right click the white space in the view, select "New ..." in the context menu, and follows the steps to create a JBoss server instance. If You are using NetBeans, click "Service" window, right click the white space and select "New", follow the wizard to register a JBoss server. 


## Project Setup 

### Import codes into Eclipse workspace

  If you have installed the JBoss tools, the M2eclipse(maven for eclipse plugin) should be installed. Follow these steps to import the exising codes into your Eclipse workspace.
 
  * Click *"File"-> "Import"* from Eclipse main menu, open the import dialog.
  * Select "Existing Maven project" under the maven node.
  * Select the location of the project root folder.

  If you are the first time to use Maven in your system, it will resolve and download the missing dependencies of this project. This maybe take several minutes, please keep patience.

  NetBeans user can open the project directly like the general NetBeans project.

### Configuration for JAAS security.

1. Configuration using UsersRoles login-module code.

	* Create a security domain in JBoss configuration file(*${JBOSS_HOME}/standlone/configuration/standalone.xml*).
		
          Find *<subsystem xmlns="urn:jboss:domain:security:1.0">* tag, change 'other' security-domain to the following configuration.

               <security-domains>
	                <security-domain name="other">
	                    <authentication>
	                         <login-module code="UsersRoles" flag="required">
	                            <module-option name="usersProperties" value="users.properties"/>
	                            <module-option name="rolesProperties" value="roles.properties"/>
	                        </login-module>
	                    </authentication>
	 		</security-domain>
	        </security-domains>		
	      
	
         Create users.properties and roles.properties in the project src/main/resources, when the project is packaged, they will be included in the classpath.
		
	* Specify the security domain in *jboss-web.xml*.
	
	 `	<security-domain>other</security-domain>`
	
	   There is a post I asked a question for this in the JBoss forum will help this topic.
	
	  [http://community.jboss.org/message/643007#643007](http://community.jboss.org/message/643007#643007)
	
2. Configuration using Database login-module code.

	There are two roles in the application, *ROLE_AMINISTRATOR* can approve and deny signup request, *ROLE_VIEWER* can view the pages.
	
	The */admin* path is protected by default, use *BASIC* based authentication, please refer the configuration in *web.xml*
file under the *WEB-INF* folder.
	
	The User/Roles related data are stored in the database, this can be configured in the JBoss AS easily. Follow the following steps to configuration.
	
	1. Create database *signup*(I used mysql), and grant all privileges to *signupuser/signuppass* .
	2. Create a datasource in *${JBOSS_HOME}/standlone/configuration/standalone.xml* .
	 	
           Find *&lt;subsystem xmlns="urn:jboss:domain:datasources:1.0">* tag, add a new datasource.
	 
                   <datasource jndi-name="java:jboss/datasources/signupDS" 
                                pool-name="signupPool" 
                                enabled="true" 
                                jta="true" 
                                use-java-context="true" 
                                use-ccm="true">
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
                    	 
	
	3. Create security configuration.
	 	
           Find *&lt;subsystem xmlns="urn:jboss:domain:security:1.0">* tag, add the following configuration.

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

3. Upgrade the weld runtime in JBoss AS.

   **This is a must for the latest Inifinispan 5.1.0.FINAL**.

   Download the latest weld distribution archive from [Seamframework.org](http://www.seamframework.org), and unzip the archive into your local disk. Copy the *artifacts/weld/weld-core.jar* from the uncompressed folder to the JBoss modules folder *modules/org/jboss/weld/core/main*, rename it to *weld-core.1.1.5.Final.jar* , edit the *module.xml* file in the same folder and find the weld version, and update to the latest version.  	

4. Run Project 
 
 You can deploy the application into JBoss AS from IDE or command line.

 1. Deploy from Eclipse IDE.

      1. Right Click the Project Node.

      2. Select "Run as"-&gt;"Server", and choose the Jboss AS you have registered and configured.

      3. Open your favorite browser, navigate http://localhost:8080/signup.

      If you are using NetBeans, right click the Project node, select Run in the context menu, and choose the JBoss server you registered.
    

 2. Deploy from command line.
 
      The pom.xml included a JBoss As maven plugin configuration, open command console and switch to path of the project root folder. run **mvn clean package jboss-as:deploy** in the command window, it will clean the project folder, compile and package a fresh war archive and deploy it into the runing JBoss AS.





            
 	