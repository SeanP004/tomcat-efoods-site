EECS 4413 Project C, eFoods
===========================

This repository holds the source code for the Building
E-Commerce Systems, [EECS 4413](http://www.eecs.yorku.ca/course_archive/2014-15/F/4413/)
final project, for Fall 2014, with [Professor Hamzeh Roumani](http://www.eecs.yorku.ca/~roumani/),
[York University](http://www.eecs.yorku.ca). The project description is available at
http://www.eecs.yorku.ca/~roumani/course/4413/res/projC/.

Contents
--------

* License
* This Repo
    * Overall Directory Structures
* B2C
    * Installation
        * Prerequisites
        * Development
        * Deployment
    * Configuration
    * Usage
    * Extending
* B2B
    * Indtallation
        * Prerequisites
        * Development
        * Deployment
    * Configuration
    * Usage
* AUTH
    * Installation
        * Prerequisites
    * Configuration
    * Usage

License
-------

EECS 4413 Project C "eFoods"
Copyright 2014. Vincent Chu, Michael Leung, Manusha Patabendi.
All rights reserved.

Permission is hereby granted, free of charge, to any person obtaining
a copy of this software and associated documentation files (the
"Software"), to deal in the Software without restriction, including
without limitation the rights to use, copy, modify, merge, publish,
distribute, sublicense, and/or sell copies of the Software, and to
permit persons to whom the Software is furnished to do so, subject to
the following conditions:

*   The above copyright notice and this permission notice shall be
    included in all copies or substantial portions of the Software.
*   Modified copies of the Software must be renamed.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY
CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT,
TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE
SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.

This Repo
---------

The project consists of three separate, but related components.
Each component is stored in a different directory in this
repository. The components are:

* AUTH, the authenication component B2B, the offline non-
* interactive procurement component B2C, the online frontend
* client site, the main component.

## Overall Directory Structures ##

### The Authentication Component ###

```
#!text
    AUTH                    # Root directory
    ├── assets              # Static assets for the HTML markup
    │   ├── css             # Stylesheet
    │   └── images          # Logo and favicon
    └── zoned               # HTTP basic auth required
```

### The Business-to-Business, Procurement, Component ###

```
#!text
    B2B                     # Root directory
    ├── res                 # Configs, Output assets
    │   ├── css             # Stylesheet
    │   └── xmlres          # XML rendering assets
    └── src                 # Java source code
        ├── controller      # Main app startup class
        └── model           # Business logic (model)
            ├── common      # Shared utilities
            ├── exception   # Exception handlers
            ├── order       # Order data structure
            └── xml         # XML generators and handlers
```

### The Business-to-Client, Main, Component ###

```
#!text
    B2C                     # Root directory
    ├── src                 # Java source code
    │   ├── controller      # Controller servlet
    │   │   ├── api         # API controllers
    │   │   └── view        # View controllers
    │   ├── filter          # Filters (ad-hoc)
    │   ├── listener        # Listeners (ad-hoc)
    │   ├── model           # Core business logic (model)
    │   │   ├── account     # Account management
    │   │   ├── cart        # Cart data structures
    │   │   ├── catalog     # Catalog data structures
    │   │   ├── checkout    # Checkout logic
    │   │   ├── common      # Shared utilities
    │   │   ├── dao         # Data access layer
    │   │   ├── exception   # Exception handling
    │   │   └── pricing     # Pricing logic
    │   └── tests           # Test classes
    ├── WebContent          # Web content directory
    │   ├── assets          # Static assets for the HTML markup
    │   │   ├── css         # Stylesheet (generated)
    │   │   ├── fonts       # Fonts (generated)
    │   │   ├── js          # JavaScript (generated)
    │   │   └── xmlres      # XML rendering assets
    │   ├── META-INF        # Context.xml (database config)
    │   └── WEB-INF         # Private web content and configs
    │       ├── includes    # JSP fragments
    │       ├── pages       # JSP pages
    │       ├── tests       # Test JSP pages
    │       └── xmlres      # XML rendering assets (private)
    └── web-src             # Source web (client-side) subproject
        ├── fonts           # Fonts (source)
        ├── js              # Source JavaScript files
        └── less            # Source Stylesheets
```

