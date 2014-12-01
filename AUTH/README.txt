############################################################
##
##  AUTH: The Authenication Service
##  README.txt
##
##  A component of the EECS 4413 Project C, eFoods.
##
##  Written by Vincent Chu (vwchu@yorku.ca)
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
* Usage
* Versioning
* Structure

========================================
Getting Started
========================================

Prerequisites
------------------------------

Basic Requirements:

* Apache HTTP Server (LAMP)
* PHP 5 (Recommended 5.4+)

For development:

* A text editor (Recommended Sublime Text 3)

Installation
------------------------------

For development:

1.  Run the commands on the terminal:

        cd ~/workspace
        mkdir ProjC
        cd ProjC
        git init
        git clone git@bitbucket.org:vwchu/eecs4413-projc.git

For deployment:

1.  Download the latest version of package at:

        https://bitbucket.org/vwchu/eecs4413-projc/downloads/AUTH-<version>.zip

    where <version> is version and build identifier using the
    the Semantic Versioning guidelines (http://semver.org/).

2.  Extract to ZIP file and place it in a directory that is served by Apache.
3.  Fix permissions (while at the root of the extracted folder.)

        find . -type f -exec chmod o+r {} +
        find . -type d -exec chmod o+rx {} +

4.  Updated the config files as necessary, see Configuration section.
    Requires that the B2C subproject has been configured as well.
5.  From the B2C, attempt to request to login.

Configurations
------------------------------

    ./config.ini

        secret      # Agreed secret key with B2C server
        uri         # URI to authenticate callback B2C server (can
                      be overwrite by callback parameter, see Usage)

    ./.htaccess

        RewriteEngine On
        RewriteCond %{HTTPS} !=on
        RewriteRule (.*) https://%{HTTP_HOST}%{REQUEST_URI} [R,L]

        RewriteCond %{REQUEST_URI} \.ini$
        RewriteRule \.ini$ - [R=404]

    ./zoned/.htaccess

        SSLRequireSSL
        AuthType Basic
        AuthName "Restricted Access"
        AuthBasicProvider pam
        require valid-user

        Order deny,allow
        deny from all
        allow from 130.63   # Change this IP as needed, do not use localhost.
                            # We tried, trust us. It doesn't work.

========================================
Usage
========================================

    This AUTH script is request via a GET or POST request.
    The parameters are:

        ref         # The referrer URL to redirect to user back to.
        signer      # Hash of parameter and secret to ensure integrity of request
        callback    # URI to authenticate callback B2C server

    Requests user back to the callback, on successful authentication.

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

    AUTH                    # Root directory
    ├── assets              # Static assets for the HTML markup
    │   ├── css             # Stylesheet
    │   └── images          # Logo and favicon
    └── zoned               # HTTP basic auth required
