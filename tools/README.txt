############################################################
##
##  B2C Standalone Runtime
##  README.txt
##
##  A tool for the EECS 4413 Project C, eFoods.
##
##  Written by Vincent Chu (vwchu@yorku.ca)
##  Revised on December 2, 2014
##  Copyright 2014. All right reserved.
##
############################################################

========================================
Prerequisites
========================================

* Java 7 and J2EE or later

========================================
Installation
========================================

1.  Download the latest version of package at:

        https://bitbucket.org/vwchu/eecs4413-projc/downloads/B2C-<version>.war

    where <version> is version and build identifier using the
    the Semantic Versioning guidelines (http://semver.org/).

2.  Download the supporting tools package (includes jetty­run,
    other apache libraries, embedded derby DBMS, and the
    eFoods database)

        https://bitbucket.org/vwchu/eecs4413-projc/downloads/B2C-sa-runtime-<version>.zip

    where <version> is version and build identifier using the
    the Semantic Versioning guidelines (http://semver.org/).

3.  Rename WAR file to eFoods.war.
4.  Extract the tools package and place “eFoods.war” in the /app subfolder.
5.  At the root of the archive, execute the following command to start the webserver.

        java -jar jetty-runner.jar --port 4413 --lib lib/ --path /eFoods app/eFoods.war

6.  Navigate to URL http://localhost:4413/eFoods to use the app.
