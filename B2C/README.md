# <a name="top"></a>B2C: Frontend Client Website

[<- Back to main](../README.md)

* [Getting Started](#start)
* [Configurations](#config)
* [Structure](#struct)
* [RESTful APIs](#api)
* [Database](#db)

## <a name="start"></a>Getting Started

1.  Run the following commands in the terminal to start the
    virtual machine and to connect into the server. See the
    main [README](../README.md) for more details.

        vagrant up
        vagrant ssh

2.  To recompile the Java codebase, run the command:

        tomcat_manage deploy

3.  To clean and rebuild the database, run the command:

        derby_manage deploy

4.  To clean and rebuild the frontend JavaScript and CSS,
    run the command:

        cd vagrant_root/B2C/web-src
        nvm use node
        npm install --no-bin-links
        grunt

    In development mode, run:

        grunt dev

## <a name="config"></a>Configurations

#### WebContent/WEB-INF/web.xml:

Parameter            | Type    | Class            | Description
-------------------- | ------- | ---------------- | --------------------------------------------
`secret`             | Context | RoutingServlets  | Agreed secret key with authenticate service
`authUri`            | Context | RoutingServlets  | URI to authenticate service
`authCallback`       | Context | RoutingServlets  | URI to authenticate callback (relative to context path)
`shippingCost`       | Context | PricingRules     | Inital shipping cost, in dollars
`shippingWaiverCost` | Context | PricingRules     | Minimum total of a cart to waiver shipping cost, in dollar
`taxRate`            | Context | PricingRules     | Tax rate, as fraction of 1
`userData`           | Context | OrdersClerk      | Directory to store the users' purchase orders
`ordersXsd`          | Context | OrdersClerk      | Path to purchase order XML schema
`ordersXslt`         | Context | OrdersClerk      | Path to purchase order XML transform, to schema from internal
`ordersXsltView`     | Context | OrdersClerk      | Path to purchase order XML transform, from XML to HTML
`ordersPrefix`       | Context | OrdersClerk      | Canonical URI to orders (relative to context path)
`ignores`            | Init    | MainFilter       | A list of URL paths relative to context path to ignore in the internal routing service. (newline delimited)
`routes`             | Init    | RoutingServlets  | A list of Servlet and URL sub paths pairs (relative to routing servlet) that the routing servlet can route traffic to. Pairs are delimited by newline. Key (Servlet name) and value (URL) are separated by equal sign. If the pair is prefixed by exclamation mark, the routing requires authentication.
`target`             | Init    | EndPointServlets | URL to JSP or static file to render the response to the user. (optional)
`restrictedUsers`    | Init    | EndPointServlets | A list of user account IDs that the Servlet allows access to. Requires that authenication is enabled. (newline delimited). (optional)

#### WebContent/META-INF/context.xml:

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<Context privileged="true" reloadable="true">
    <WatchedResource>WEB-INF/web.xml</WatchedResource>
    <Manager pathname="" />
    <Resource
        name="jdbc/EECS"
        factory="org.apache.tomcat.jdbc.pool.DataSourceFactory"
        type="javax.sql.DataSource"
        driverClassName="org.apache.derby.jdbc.ClientDriver"
        url="jdbc:derby://localhost:1527/eFoods"
        username="student"
        password="secret"
    />
    <ResourceLink
        global="jdbc/EECS"
        name="jdbc/EECS"
        type="javax.sql.DataSource"
    />
</Context>
```

XPath                         | Description
----------------------------- | -------------------------------------
`/Context/Resource/@driver`   | Specify the database driver and type
`/Context/Resource/@url`      | Specify the URL and port to the database
`/Context/Resource/@username` | Specify the database username (credentials)
`/Context/Resource/@passwrod` | Specify the database password (credentials)

## <a name="struct"></a>Structure

### Program Layout (Development)

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

### Site Layout

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

## <a name="api"></a>RESTful APIs

### `GET /api/catalog`

Query the catalog for items and categories.

**Parameter `type` - Specifies the type of query.**

Value       | Description
----------- | -----------------------
`itemlist`  | Get a list of items. (default)
`item`      | Get a specific item.
`catlist`   | Get a list of categories.
`category`  | Get a specific category.

**When `type=itemlist`:**

Parameters   | Description
------------ | -----------------------
`orderBy`    | Sort order: one of `number`, `name`, `price`, `catid`.
`searchTerm` | Filter items by name.
`category`   | Filter items by category.
`offset`     | Return results after given offset.
`fetch`      | Return a specific number of results.
`minPrice`   | Filter items by minimum price.
`maxPrice`   | Filter items by maximum price.

**When `type=item`:**

Parameters | Description
---------- | -----------------------
`number`   | Unique identifier of specific item.

**When `type=catlist`:**

Parameters   | Description
------------ | -----------------------
`orderBy`    | Sort order: one of `id` or `name`.
`searchTerm` | Filter categories by `name`.
`offset`     | Return results after given offset.
`fetch`      | Return a specific number of results.

**When `type=category`:**

Parameters | Description
---------- | -----------------------
`id`       | Unique identifier of specific category.

### `GET /api/cart`

Query the content of the shopping cart.

### `POST /api/cart`

Perform actions on the content of the shopping cart.

**Parameter `action` - Specifies the type of action.**

Value    | Description
-------- | ----------------------------------------
`list`   | Get cart status and list of items. (default)
`add`    | Add item to cart.
`remove` | Remove item from cart.
`bulk`   | Perform a bulk update operation on cart.

**When `action=add` or `action=remove`:**

Parameter | Description
--------- | ---------------------------
`number`  | Unique identifier of specific item.

**When `action=bulk`:**

Parameter  | Description
---------- | ---------------------------
`number`   | Unique identifier of specific item(s). Semicolon separated list of IDs.
`quantity` | Corresponding quantities to update cart. Semicolon separated list of quantities.

### `GET /api/checkout`

Perform checkout operation of the shopping cart. Requires login.

### `GET /api/order`

Query and view purchase orders of all users in system.

### `GET /api/order/<account>`

Query and view purchase orders for a specific user in system
given the `<account>` user name.

### `GET /api/order/<order>.xml`

Return the order file, if exists, given the <order> file
name, that can be retrieved by querying `/api/order` or
`/api/order/<account>`.

### `GET /api/auth`

Callback for the authentication service to redirect users to.

Parameter | Description
--------- | ---------------------------
`account` | Account ID of the logging in account.
`name`    | Full name of the user logging in account.
`ref`     | The referrer URL to redirect to user back to.
`signer`  | A hash of the parameter with the secret to ensure the integrity of the request.

### `GET /api/login`

Request login or logout

Parameter | Description
--------- | ---------------------------
`action`  | Specifies the type of action.
`ref`     | The referrer URL to redirect to user back to.

**Allowed values of `action` parameter:**

Values   | Description
-------- | ---------------------------
`login`  | Request login action. (default)
`logout` | Request logout action.

## <a name="db"></a>Database

The table schema is as follows:

### The `CATEGORY` table:

COLUMN_NAME          | TYPE_NAME | DEC& | NUM& | COLUM& | COLUMN_DEF | CHAR_OCTE& | IS_NULL&
-------------------- | --------- | ---- | ---- | ------ | ---------- | ---------- | --------
ID                   | INTEGER   | 0    | 10   | 10     | AUTOINCRE& | NULL       | NO
NAME                 | VARCHAR   | NULL | NULL | 20     | NULL       | 40         | YES
DESCRIPTION          | VARCHAR   | NULL | NULL | 50     | NULL       | 100        | YES
PICTURE              | BLOB      | NULL | NULL | 10485& | NULL       | NULL       | YES

### The `ITEM` table:

COLUMN_NAME          | TYPE_NAME | DEC& | NUM& | COLUM& | COLUMN_DEF | CHAR_OCTE& | IS_NULL&
-------------------- | --------- | ---- | ---- | ------ | ---------- | ---------- | --------
NUMBER               | CHAR      | NULL | NULL | 8      | NULL       | 16         | NO
NAME                 | VARCHAR   | NULL | NULL | 50     | NULL       | 100        | YES
PRICE                | DOUBLE    | NULL | 2    | 52     | NULL       | NULL       | YES
QTY                  | INTEGER   | 0    | 10   | 10     | NULL       | NULL       | YES
ONORDER              | INTEGER   | 0    | 10   | 10     | NULL       | NULL       | YES
REORDER              | INTEGER   | 0    | 10   | 10     | NULL       | NULL       | YES
CATID                | INTEGER   | 0    | 10   | 10     | NULL       | NULL       | YES
SUPID                | INTEGER   | 0    | 10   | 10     | NULL       | NULL       | YES
COSTPRICE            | DOUBLE    | NULL | 2    | 52     | NULL       | NULL       | YES
UNIT                 | VARCHAR   | NULL | NULL | 20     | NULL       | 40         | YES

[<- Top of Page](#top)
