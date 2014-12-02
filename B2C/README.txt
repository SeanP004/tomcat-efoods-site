############################################################
##
##  B2C: Frontend Client Website
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
* Versioning
* Structure
    * Program Layout
    * Site Layout
    * APIs
* Database

========================================
Getting Started
========================================

Quick Start: Standalone
------------------------------

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

Prerequisites
------------------------------

Basic Requirements:

* Java 7 and J2EE or later
* Apache Tomcat v8.0 Server or later
* Apache Derby Network Server 10.10.2.0 or later

For development:

* Java Development Kit 1.7
* Eclipse Java EE IDE for Web Developers 4.4

For frontend development:

* Node.js v0.10.32 or later
* NPM (Node.js Package Manager) 1.3.6 or later
* Bower.js 1.3.12 or later
* Grunt-cli v0.1.13 or later
* Lessc 2.0.0 (Less Compiler) [JavaScript]

Installation
------------------------------

For development:

1.  Run the commands on the terminal:

        cd ~/workspace
        mkdir ProjC
        cd ProjC
        git init
        git clone git@bitbucket.org:vwchu/eecs4413-projc.git

2.  Open Eclipse.
3.  Click "Import" on the "File" menu.
4.  Select "Existing Projects into Workspace".
5.  Browse to the project directory and select "B2C".
6.  Check "eFoods".
7.  Click "Finish".

8.  Start the database, by running the command,
    assuming that it has been configured and tables
    setup (see Database section) already:

        derby_start

9.  Select the project in the "Project Explorer".
10. Right-click, click "Run As" > "Run on Server".
11. Add the project to the server, and Click "Finish".

12. To develop for the frontend, run follow these
    additional commands on the terminal:

        cd ~/workspace/ProjC/B2C/web-src
        bower install
        npm install
        grunt dev

For deployment:

1.  Download the latest version of package at:

        https://bitbucket.org/vwchu/eecs4413-projc/downloads/B2C-<version>.war

    where <version> is version and build identifier using the
    the Semantic Versioning guidelines (http://semver.org/).

2.  Extract to WAR file into your Tomcat server instance.

        mkdir eFoods
        cd eFoods
        jar -xvf ../B2C-<version>.war
        cd ..
        mv eFoods /path/to/tomcat

3.  Updated the config files as necessary, see Configuration section.
    Requires that the AUTH subproject has been configured as well.
4.  Start the database, by running the command,
    assuming that it has been configured and tables
    setup (see Database section) already:

        derby_start

5.  Start the Tomcat server, by running the command:

        tomcat_start

6.  Open a web browser and go to:

        http://localhost/eFoods

    Exact URL depends on the server configuration.

Configurations
------------------------------

    WebContent/WEB-INF/web.xml:

        Context Params:

            secret              # Agreed secret key with authenticate service
            authUri             # URI to authenticate service
            authCallback        # URI to authenticate callback (relative to context path)
            shippingCost        # Inital shipping cost, in dollars
            shippingWaverCost   # Minimum total of a cart to waiver shipping cost, in dollar
            taxRate             # Tax rate, as fraction of 1
            userData            # Directory to store the users' purchase orders
            ordersXsd           # Path to purchase order XML schema
            ordersXslt          # Path to purchase order XML transform, to schema from internal
            ordersXsltView      # Path to purchase order XML transform, from XML to HTML
            ordersPrefix        # Canonical URI to orders (relative to context path)

        Init Params:

            MainFilter:

                ignores         # A list of URL paths relative to context path to ignore in
                                  the internal routing service. (newline delimited)
            RoutingServlets:

                routes          # A list of Servlet and URL sub paths pairs (relative to
                                  routing servlet) that the routing servlet can route traffic
                                  to. Pairs are delimited by newline. Key (Servlet name) and
                                  value (URL) are separated by equal sign. If the pair is
                                  prefixed by exclamation mark, the routing requires
                                  authentication.

            EndPointServlets:

                target          # URL to JSP or static file to render the response to
                                  the user. (optional)
                restrictedUsers # A list of user account IDs that the Servlet allows access
                                  to. Requires that authenication is enabled. (newline
                                  delimited). (optional)

    WebContent/META-INF/context.xml:

        <?xml version="1.0" encoding="UTF-8" ?>
        <Context privileged="true" reloadable="true">
            <WatchedResource>WEB-INF/web.xml</WatchedResource>
            <Manager pathname="" />
            <Resource
                name="jdbc/EECS"
                factory="org.apache.tomcat.jdbc.pool.DataSourceFactory"
                type="javax.sql.DataSource"
                driverClassName="org.apache.derby.jdbc.ClientDriver"
                url="jdbc:derby://localhost:64413/CSE"
                username="student"
                password="secret"
            />
            <ResourceLink
                global="jdbc/EECS"
                name="jdbc/EECS"
                type="javax.sql.DataSource"
            />
        </Context>

        -   /Context/Resource/@driver       Specify the database driver and type
        -   /Context/Resource/@url          Specify the URL and port to the database
        -   /Context/Resource/@username     Specify the database username (credentials)
        -   /Context/Resource/@passwrod     Specify the database password (credentials)

========================================
Versioning
========================================

For transparency into our release cycle and in striving to maintain
backward compatibility, this project is maintained under the Semantic
Versioning guidelines (http://semver.org/). Sometimes we screw up,
but we'll adhere to those rules whenever possible.

========================================
Structure
========================================

Program Layout (Development)
------------------------------

    B2C                     # Root directory
    ├── src                 #   Java source code
    │   ├── controller      #     Controller servlet
    │   │   ├── api         #       API controllers
    │   │   └── view        #       View controllers
    │   ├── filter          #     Filters (ad-hoc)
    │   ├── listener        #     Listeners (ad-hoc)
    │   ├── model           #     Core business logic (model)
    │   │   ├── account     #       Account management
    │   │   ├── cart        #       Cart data structures
    │   │   ├── catalog     #       Catalog data structures
    │   │   ├── checkout    #       Checkout logic
    │   │   ├── common      #       Shared utilities
    │   │   ├── dao         #       Data access layer
    │   │   ├── exception   #       Exception handling
    │   │   └── pricing     #       Pricing logic
    │   └── tests           #     Test classes
    ├── WebContent          #   Web content directory
    │   ├── assets          #     Static assets for the HTML markup
    │   │   ├── css         #       Stylesheet (generated)
    │   │   ├── fonts       #       Fonts (generated)
    │   │   ├── js          #       JavaScript (generated)
    │   │   └── xmlres      #       XML rendering assets
    │   ├── META-INF        #     Context.xml (database config)
    │   └── WEB-INF         #     Private web content and configs
    │       ├── includes    #       JSP fragments
    │       ├── pages       #       JSP pages
    │       ├── tests       #       Test JSP pages
    │       └── xmlres      #       XML rendering assets (private)
    └── web-src             #   Source web (client-side) subproject
        ├── fonts           #     Fonts (source)
        ├── js              #     Source JavaScript files
        └── less            #     Source Stylesheets

Site Layout
------------------------------

    /eFoods                 # Site Root
    ├── /                   #   Default view; home page (storefront)
    ├── /Home               #   Home page (storefront)
    ├── /Browse             #   Catalog view; browse and search items
    ├── /item/*             #   Individual item pages
    ├── /cart               #   Cart view, summary of items in shopping cart
    ├── /confirm            #   Confirmation page before checkout, requires login
    ├── /checkout           #   Checkout completion page, redirects to order
    ├── /admin              #   Admin panel, shows site stats
    └── /api                #   API Root
        ├── catalog         #     Query the catalog [GET]
        ├── cart            #     Manipulate the shopping cart [GET,POST]
        ├── checkout        #     Checkout the cart [GET]
        ├── order/*         #     Query and view purchase orders [GET]
        ├── auth            #     Authentication callback [GET]
        └── login           #     Request login or logout [GET]

APIs
------------------------------

| Method | Url           | Description                                                              |
| ------ | ------------- | ------------------------------------------------------------------------ |
| GET    | /api/catalog  | Query the catalog for items and categories.                              |
|        |               |                                                                          |
|        |               | * type               - Specifies the type of query.                      |
|        |               |     * itemlist       - Get a list of items. (default)                    |
|        |               |         * orderBy    - Sort order: one of number, name, price, catid.    |
|        |               |         * searchTerm - Filter items by name.                             |
|        |               |         * category   - Filter items by category.                         |
|        |               |         * offset     - Return results after given offset.                |
|        |               |         * fetch      - Return a specific number of results.              |
|        |               |         * minPrice   - Filter items by minimum price.                    |
|        |               |         * maxPrice   - Filter items by maximum price.                    |
|        |               |     * item           - Get a specific item.                              |
|        |               |         * number     - Unique identifier of specific item.               |
|        |               |     * catlist        - Get a list of categories.                         |
|        |               |         * orderBy    - Sort order: one of id, name.                      |
|        |               |         * searchTerm - Filter categories by name.                        |
|        |               |         * offset     - Return results after given offset.                |
|        |               |         * fetch      - Return a specific number of results.              |
|        |               |     * category       - Get a specific category.                          |
|        |               |         * id         - Unique identifier of specific category.           |
| ------ | ------------- | ------------------------------------------------------------------------ |
| GET /  | /api/cart     | Query and perform actions on the content of the shopping cart.           |
| POST   |               |                                                                          |
|        |               | * action             - Specifies the type of action.                     |
|        |               |     * list           - Get cart status and list of items. (default)      |
|        |               |     * add            - Add item to cart.                                 |
|        |               |         * number     - Unique identifier of specific item.               |
|        |               |     * remove         - Remove item from cart.                            |
|        |               |         * number     - Unique identifier of specific item.               |
|        |               |     * bulk           - Perform a bulk update operation on cart.          |
|        |               |         * number     - Unique identifier of specific item(s).            |
|        |               |                        Semi-colon separated list of IDs.                 |
|        |               |         * quantity   - Corresponding quantities to update cart.          |
|        |               |                        Semi-colon separated list of quantities.          |
| ------ | ------------- | ------------------------------------------------------------------------ |
| GET    | /api/checkout | Perform checkout operation of the shopping cart. Requires login.         |
| ------ | ------------- | ------------------------------------------------------------------------ |
| GET    | /api/order    | Query and view purchase orders.                                          |
|        |               |                                                                          |
|        |               |      /               - List all orders (URLs) in system. (default)       |
|        |               |      /<account>      - List all orders (URLs) for specified account.     |
|        |               |      /<order>.xml    - Return the order file, if exists.                 |
| ------ | ------------- | ------------------------------------------------------------------------ |
| GET    | /api/auth     | Callback for the authentication service to redirect users to.            |
|        |               |                                                                          |
|        |               |      * account       - Account ID of the logging in account.             |
|        |               |      * name          - Full name of the user logging in account.         |
|        |               |      * ref           - The referrer URL to redirect to user back to.     |
|        |               |      * signer        - A hash of the parameter with the secret to        |
|        |               |                        ensure the integrity of the request.              |
| ------ | ------------- | ------------------------------------------------------------------------ |
| GET    | /api/login    | Request login or logout                                                  |
|        |               |                                                                          |
|        |               |      * action        - Specifies the type of action.                     |
|        |               |          * login     - Request login action. (default)                   |
|        |               |          * logout    - Request logout action.                            |
|        |               |      * ref           - The referrer URL to redirect to user back to.     |
| ------ | ------------- | ------------------------------------------------------------------------ |

========================================
Database
========================================

The table schema is as follows:

The CATEGORY table:

| COLUMN_NAME          | TYPE_NAME | DEC& | NUM& | COLUM& | COLUMN_DEF | CHAR_OCTE& | IS_NULL& |
| -------------------- | --------- | ---- | ---- | ------ | ---------- | ---------- | -------- |
| ID                   | INTEGER   | 0    | 10   | 10     | AUTOINCRE& | NULL       | NO       |
| NAME                 | VARCHAR   | NULL | NULL | 20     | NULL       | 40         | YES      |
| DESCRIPTION          | VARCHAR   | NULL | NULL | 50     | NULL       | 100        | YES      |
| PICTURE              | BLOB      | NULL | NULL | 10485& | NULL       | NULL       | YES      |

The ITEM table:

| COLUMN_NAME          | TYPE_NAME | DEC& | NUM& | COLUM& | COLUMN_DEF | CHAR_OCTE& | IS_NULL& |
| -------------------- | --------- | ---- | ---- | ------ | ---------- | ---------- | -------- |
| NUMBER               | CHAR      | NULL | NULL | 8      | NULL       | 16         | NO       |
| NAME                 | VARCHAR   | NULL | NULL | 50     | NULL       | 100        | YES      |
| PRICE                | DOUBLE    | NULL | 2    | 52     | NULL       | NULL       | YES      |
| QTY                  | INTEGER   | 0    | 10   | 10     | NULL       | NULL       | YES      |
| ONORDER              | INTEGER   | 0    | 10   | 10     | NULL       | NULL       | YES      |
| REORDER              | INTEGER   | 0    | 10   | 10     | NULL       | NULL       | YES      |
| CATID                | INTEGER   | 0    | 10   | 10     | NULL       | NULL       | YES      |
| SUPID                | INTEGER   | 0    | 10   | 10     | NULL       | NULL       | YES      |
| COSTPRICE            | DOUBLE    | NULL | 2    | 52     | NULL       | NULL       | YES      |
| UNIT                 | VARCHAR   | NULL | NULL | 20     | NULL       | 40         | YES      |
