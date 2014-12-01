############################################################
##
##  B2B: Offline Procurement Application
##  README.txt
##
##  A component of the EECS 4413 Project C, eFoods.
##
##  Written by Michael Leung (mmleung7@yorku.ca)
##  Revised on December 1, 2014
##  Copyright 2014. All right reserved.
##
############################################################

========================================
Content
========================================

* Getting Started
    * Prerequisites
    * Installation
    * Configurations
    * Scheduler
* Versioning
* Structure
    * Program Layout (Development)

========================================
Getting Started
========================================

Prerequisites
------------------------------

Basic Requirements:

* Java 7 and J2EE or later
* Apache Tomcat v8.0 Server or later
* XML files generated as eFoods orders

For development:

* Java Development Kit 1.7
* Eclipse Java EE IDE for Web Developers 4.4

Installation
------------------------------

For development:

NOTE:   if the project has been prepared for during B2C to
        setup of the eFoods please skip steps 1 to 4.
        Please start at step 5 as preparation for installition
        as already been prepared.

1.  Run the commands on the terminal:

        cd ~/workspace
        mkdir ProjC
        cd ProjC
        git init
        git clone git@bitbucket.org:vwchu/eecs4413-projc.git

2.  Open Eclipse.
3.  Click "Import" on the "File" menu.
4.  Select "Existing Projects into Workspace".
5.  Browse to the project directory and select "B2B".
6.  Check "B2B".
7.  Click "Finish".

8.  Select the project in the "Project Explorer".
9.  Right-click, click "Run As" > "Java Application".

10. Console will display a message once the application is
    finished generating the requested files.
    The following message will be generated as shown below:
    " ------ HTML file has be Generated -------- "  when done
        or
    "No Orders" when it can not find orders.
    The generated reports can be found in the
    ./reports directory inside where B2B is stored.

For deployment:

1.  Download the latest version of package executable jar at:

        https://bitbucket.org/vwchu/eecs4413-projc/downloads/B2B-<version>.jar

    where <version> is version and build identifier using the
    the Semantic Versioning guidelines (http://semver.org/).

2.  Extract to WAR file into your Tomcat server instance.

        mkdir B2B
        cd B2B
        jar -xvf B2B-<version>.jar

3.  Update the config file as necessary, see Configurations section.

4.  To execute, run the following command in the terminal:

        java controller.Main

5.  Console will display a message once the application is
    finished generating the requested files.
    The following message will be generated as shown below:
        " ------ HTML file has be Generated -------- "  when done
        or
        "No Orders" when it can not find orders.
    The generated reports can be found in the
    ./reports directory inside where B2B is stored.

6. To view the generated, can be opened by a Web Browser.
   open the folder ./reports located in B2B and click on one of
   the generated reports.
   or
   In Terminal run the following commands:
        firefox file://<file path of report>


Configurations
------------------------------

    ./res/config.ini:

        Each lines of the configuration file is formated in the following:
        <variable name>=<variable value>

        storeDir            # The directory where the generated report files
                              are located and stored in.
        dataURL             # The URL directory location where the eFoods reports
                              are stored for extracting data.
        WTORONTO            # The URL to where the WSDL file for WTORONTO
        WVANCOUVER          # The URL to where the WSDL file for WVANCOUVER
        WHALIFAX            # The URL to where the WSDL file for WHALIFAX
        key                 # Agreed secret key with authenticate service
        xslt                # The URL to xml template to format the files output


Scheduler
------------------------------

The below is the explaination for the setup of a scheduler

For Windows:

    Use Window Scheduler:

    1. Update the configuration setting within config.ini are correct,
       before proceeding.
       As necessary, see Configurations section.

    2.  Ensure the permissions of the folders and files are set so
        that they are readable and accessible to the web server.

    3.  Configure the Scheduler Tasks in Windows, as follows (for example):

            Action:             Start a program
            Program/script:     Location where jdk java.exe is located
            Arguments:          -c controller.main where B2B is located
            Start in:           where B2B is located

For Linux (UNIX):

    Use Cron Scheduler

    1. In Terminal, install or create or edit my own cron jobs.

            crontab -e

                1 2 3 4 5 java /path/to/controller.main

                Where,

                -   1: Minute (0-59)
                -   2: Hours (0-23)
                -   3: Day (0-31)
                -   4: Month (0-12 [12 == December])
                -   5: Day of the week(0-7 [7 or 0 == sunday])
                -   /path/to/command - Script or command name to schedule

========================================
Versioning
========================================

For transparency into our release cycle and in striving to maintain
backward compatibility, Bootstrap is maintained under the Semantic
Versioning guidelines (http://semver.org/). Sometimes we screw up,
but we'll adhere to those rules whenever possible.

========================================
Structure
========================================

Program Layout (Development)
------------------------------

    B2B                     # Root directory
    ├── res                 # Configs, Output assets
    │   ├── css             # Stylesheet
    │   └── xmlres          # XML rendering assets
    └── src                 # Java source code
        ├── controller      # Main app startup class
        └── model           # Business logic (model)
            ├── common      # Shared utilities
            ├── exception   # Exception handlers
            ├── order       # Order data structure
            └── xml         # XML generators and handlers
