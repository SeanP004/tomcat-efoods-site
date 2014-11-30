EECS 4413 Project C, eFoods
===========================

This repository holds the source code for the Building
E-Commerce Systems, [EECS 4413](http://www.eecs.yorku.ca/course_archive/2014-15/F/4413/)
final project, for Fall 2014, with [Professor Hamzeh Roumani](http://www.eecs.yorku.ca/~roumani/),
[York University](http://www.eecs.yorku.ca). The project description is available at
http://www.eecs.yorku.ca/~roumani/course/4413/res/projC/.

Contents
--------

* This Repo
* Installation

This Repo
---------

The project consists of three separate, but related components.
Each component is stored in a different directory in this
repository. Each component has its own README for more details on
installation and development. The components are:

1. **B2C**, the frontend client website
2. **B2B**, the non-interactive, offline business-to-business
   procurement application
3. **AUTH**, the authentication service

Installation
------------

For development (in general), run the following
commands in the console:

    mkdir ProjC
    cd ProjC
    git init
    git clone git@bitbucket.org:vwchu/eecs4413-projc.git

For deployment, download the following packages (where X.X is
the version of each of the downloaded packages):

    https://bitbucket.org/vwchu/eecs4413-projc/downloads/AUTHvX.X.zip
    https://bitbucket.org/vwchu/eecs4413-projc/downloads/B2BvX.X.jar
    https://bitbucket.org/vwchu/eecs4413-projc/downloads/B2CvX.X.war

For more detailed instructions with regards to installation,
setup and configuration, see the README of each specific
component.
