# 341-TimecardAPI
A Java web service that handles REST calls.  Created as part of ISTE-341 Project 2.

## Requirements
- JDK 11
- Wildfly
- Connection to RIT VPN (Database host)


## To Test
#### (macOS, Wildfly installed in /Applications):

1) Build Project with JDK 11

2) Ensure WEB-INF folder exists at project root.
   - Ensure 'classes' folder exists inside 'WEB-INF' folder.
      - Ensure .class files are inside 'classes' folder inside WEB-INF folder.
   - Ensure 'lib' folder exists inside 'WEB-INF' folder.   
      - Ensure all libraries are inside 'lib' folder inside WEB-INF folder
   - Ensure 'web.xml' file exists inside 'WEB-INF' folder.   

3) Run Wildfly

    cd /Applications/Wildfly/bin/; ./standalone.sh

4) Create WAR File (Output dir is set to Wildfly's autodeployment directory to save time).

    jar cvf /Applications/Wildfly/standalone/deployments/test.war WEB-INF

5) Delete COMPANY using POSTMAN Delete

6) Run P2Tester.java
