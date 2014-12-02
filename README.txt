############################################################
##
##  EECS 4413 Project C, eFoods
##  README.txt
##
##  Written by Vincent Chu (vwchu@yorku.ca)
##  Revised on December 1, 2014
##  Copyright 2014. All right reserved.
##
############################################################

This repository holds the source code for the Building
E-Commerce Systems, EECS 4413 Fall 2014[1], final project[2]
with Professor Hamzeh Roumani[3], York University[4].

    1. http://www.eecs.yorku.ca/course_archive/2014-15/F/4413/
    2. http://www.eecs.yorku.ca/~roumani/course/4413/res/projC/
    3. http://www.eecs.yorku.ca/~roumani/
    4. http://www.eecs.yorku.ca

The project consists of three separate, but related components.
Each component is stored in a different directory in this
repository. Each component has its own README for more details on
installation and development. The components are:

    1. B2C, the frontend client website
    2. B2B, the non-interactive, offline business-to-business
       procurement application
    3. AUTH, the authentication service

========================================
Getting Started [Quick]
========================================

Basic Requirements:

* Java 7 and J2EE or later

Installation:

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
