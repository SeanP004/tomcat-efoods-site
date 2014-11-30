# EECS 4413 Project C, eFoods #

This repository holds the source code for the Building
E-Commerce Systems, [EECS 4413](http://www.eecs.yorku.ca/course_archive/2014-15/F/4413/)
final project, for Fall 2014, with [Professor Hamzeh Roumani](http://www.eecs.yorku.ca/~roumani/),
[York University](http://www.eecs.yorku.ca). The project description is available at
http://www.eecs.yorku.ca/~roumani/course/4413/res/projC/.

## Contents ##

* License
* This Repo
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

## License ##

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

## This Repo ##

The project consists of three separate, but related components.
Each component is stored in a different directory in this
repository. The components are:

* AUTH, the authenication component B2B, the offline non-
* interactive procurement component B2C, the online frontend
* client site, the main component.

### Overall Directory Structures ###

#### The Authentication Component ####

``` 
#!text
    AUTH
    ├── assets
    │   ├── css
    │   └── images
    └── zoned
```

#### The Business-to-Business, Procurement, Component ####

``` 
#!text
    B2B
    ├── res
    │   ├── css
    │   └── xmlres
    └── src
        ├── controller
        └── model
            ├── common
            ├── exception
            ├── order
            └── xml
```

#### The Business-to-Client, Main, Component ####

``` 
#!text
    B2C
    ├── src
    │   ├── controller
    │   │   ├── api
    │   │   └── view
    │   ├── filter
    │   ├── listener
    │   ├── model
    │   │   ├── account
    │   │   ├── cart
    │   │   ├── catalog
    │   │   ├── checkout
    │   │   ├── common
    │   │   ├── dao
    │   │   ├── exception
    │   │   └── pricing
    │   └── tests
    ├── WebContent
    │   ├── assets
    │   │   ├── css
    │   │   ├── fonts
    │   │   ├── js
    │   │   └── xmlres
    │   ├── META-INF
    │   └── WEB-INF
    │       ├── includes
    │       ├── pages
    │       ├── tests
    │       └── xmlres
    └── web-src
        ├── fonts
        ├── js
        └── less
```
