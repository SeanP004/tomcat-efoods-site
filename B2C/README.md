B2C: Frontend Client Website
============================

## Content ##

* Getting Started
    * Prerequisites
    * Installation
    * Development
    * Deployment
* Configurations
* Structure
    * Program Layout
    * Site Layout
    * APIs
* Database
* Documentation

## Getting Started ##

### Prerequisites ###

#### Basic Requirements: ####

* Java 7 and J2EE or later
* Apache Tomcat v8.0 Server or later
* Apache Derby Network Server 10.10.2.0 or later

#### For development: ####

* Java Development Kit 1.7
* Eclipse Java EE IDE for Web Developers 4.4

##### For frontend development: #####

* Node.js v0.10.32 or later
* NPM (Node.js Package Manager) 1.3.6 or later
* Bower.js 1.3.12 or later
* Grunt-cli v0.1.13 or later
* Lessc 2.0.0 (Less Compiler) [JavaScript]

### Installation ###

#### For development: ####

1.  Run the commands on the terminal:

    cd ~/workspace
    mkdir ProjC
    cd ProjC
    git init
    git clone git@bitbucket.org:vwchu/eecs4413-projc.git

2.  Open **Eclipse**.
3.  Click **Import** on the **File** menu.
4.  Select **Existing Projects into Workspace**.
5.  Browse to the project directory and select **B2C**.
6.  Check **eFoods**.
7.  Click **Finish**.

8.  Start the database, by running the command,
    assuming that it has been provided already:

    derby_start

9.  Run the project in the **Project Explorer**.
10.  Right-click, click **Run As** > **Run on Server**.
11. Add the project to the server, and Click **Finish**.

12. To develop for the frontend, run follow these
    additional commands on the terminal:

    cd ~/workspace/ProjC/B2C/web-src
    bower install
    npm install
    grunt dev

#### For deployment: ####


### Structure ###

#### Program Layout ####

```
#!text
    B2C
    ├── README.md
    ├── src
    │   ├── controller
    │   │   ├── api
    │   │   │   ├── API.java
    │   │   │   ├── AuthAPI.java
    │   │   │   ├── CartAPI.java
    │   │   │   ├── CatalogAPI.java
    │   │   │   ├── CheckoutAPI.java
    │   │   │   ├── LoginAPI.java
    │   │   │   └── OrdersAPI.java
    │   │   ├── EndPointServlet.java
    │   │   ├── Main.java
    │   │   ├── RoutingServlet.java
    │   │   └── view
    │   │       ├── AdminView.java
    │   │       ├── CartView.java
    │   │       ├── CatalogView.java
    │   │       ├── CheckoutView.java
    │   │       ├── ConfirmationView.java
    │   │       ├── HomeView.java
    │   │       ├── ItemView.java
    │   │       └── View.java
    │   ├── filter
    │   │   ├── CartFilter.java
    │   │   ├── CheckoutFilter.java
    │   │   └── CrossSellingFilter.java
    │   ├── listener
    │   │   └── SessionCountListener.java
    │   ├── model
    │   │   ├── account
    │   │   │   └── Account.java
    │   │   ├── cart
    │   │   │   ├── CartElement.java
    │   │   │   └── Cart.java
    │   │   ├── catalog
    │   │   │   ├── Catalog.java
    │   │   │   ├── CategoryFilter.java
    │   │   │   ├── Category.java
    │   │   │   ├── CategoryList.java
    │   │   │   ├── ItemFilter.java
    │   │   │   ├── Item.java
    │   │   │   └── ItemList.java
    │   │   ├── checkout
    │   │   │   ├── OrdersClerk.java
    │   │   │   ├── OrdersList.java
    │   │   │   └── Receipt.java
    │   │   ├── common
    │   │   │   ├── CommonUtil.java
    │   │   │   └── XMLUtil.java
    │   │   ├── dao
    │   │   │   ├── CatalogDBAO.java
    │   │   │   ├── CatalogDB.java
    │   │   │   ├── CatalogDBQuery.java
    │   │   │   └── OrdersDAO.java
    │   │   ├── exception
    │   │   │   ├── AppException.java
    │   │   │   ├── CategoryNotFoundException.java
    │   │   │   ├── DataAccessException.java
    │   │   │   ├── InvalidBulkUpdateException.java
    │   │   │   ├── InvalidCartQuantityException.java
    │   │   │   ├── InvalidQueryFilterException.java
    │   │   │   ├── ItemNotFoundException.java
    │   │   │   ├── OrderCheckoutException.java
    │   │   │   ├── OrderNotFoundException.java
    │   │   │   ├── PricingRuleValueException.java
    │   │   │   ├── UserAccessDeniedException.java
    │   │   │   └── XMLGenerationException.java
    │   │   └── pricing
    │   │       ├── Cost.java
    │   │       ├── PriceFilter.java
    │   │       ├── PriceManager.java
    │   │       └── PricingRules.java
    │   └── tests
    │       ├── TestCart.java
    │       └── TestCatalogModel.java
    ├── WebContent
    │   ├── assets
    │   │   ├── css
    │   │   │   └── main.css
    │   │   ├── fonts
    │   │   │   ├── _flaticon.css
    │   │   │   ├── flaticon.eot
    │   │   │   ├── flaticon.html
    │   │   │   ├── flaticon.svg
    │   │   │   ├── flaticon.ttf
    │   │   │   ├── flaticon.woff
    │   │   │   ├── glyphicons-halflings-regular.eot
    │   │   │   ├── glyphicons-halflings-regular.svg
    │   │   │   ├── glyphicons-halflings-regular.ttf
    │   │   │   └── glyphicons-halflings-regular.woff
    │   │   ├── js
    │   │   │   └── main.js
    │   │   └── xmlres
    │   │       └── POView.xslt
    │   ├── META-INF
    │   │   ├── context.xml
    │   │   └── MANIFEST.MF
    │   └── WEB-INF
    │       ├── includes
    │       │   ├── AddToCartButton.jspx
    │       │   ├── Cart.jspx
    │       │   ├── CatalogItem.jspx
    │       │   ├── CatalogList.jspx
    │       │   ├── CategoryPic.jspx
    │       │   ├── CategoryQuickLinks.jspx
    │       │   ├── Checkout.jspx
    │       │   ├── Confirmation.jspx
    │       │   ├── ErrorMessage.jspx
    │       │   ├── Footer.jspx
    │       │   ├── Header.jspx
    │       │   ├── Item.jspx
    │       │   ├── ItemsShowcase.jspx
    │       │   ├── OrderSummary.jspx
    │       │   ├── Search.jspx
    │       │   ├── SiteHeader.jspx
    │       │   └── Splash.jspx
    │       ├── pages
    │       │   ├── AdminView.jspx
    │       │   ├── CartView.jspx
    │       │   ├── CatalogView.jspx
    │       │   ├── CheckoutView.jspx
    │       │   ├── ConfirmationView.jspx
    │       │   ├── Error404.jspx
    │       │   ├── HomeView.jspx
    │       │   └── ItemView.jspx
    │       ├── tests
    │       ├── web.xml
    │       └── xmlres
    │           ├── APIResponse.jspx
    │           ├── PO.xsd
    │           └── PO.xslt
    └── web-src
        ├── bower.json
        ├── fonts
        │   ├── _flaticon.css
        │   ├── flaticon.eot
        │   ├── flaticon.html
        │   ├── flaticon.svg
        │   ├── flaticon.ttf
        │   └── flaticon.woff
        ├── Gruntfile.js
        ├── js
        │   ├── ajax.js
        │   ├── cartbtn.js
        │   ├── cart.js
        │   ├── carttable.js
        │   ├── element.js
        │   ├── elements.js
        │   ├── formdata.js
        │   ├── functions.js
        │   ├── search.js
        │   └── template.js
        ├── less
        │   ├── base.less
        │   ├── bootstrap.less
        │   ├── buttons.less
        │   ├── cart.less
        │   ├── catalog.less
        │   ├── footer.less
        │   ├── header.less
        │   ├── itempic.less
        │   ├── jumbotron.less
        │   ├── main.less
        │   ├── mixins.less
        │   ├── search.less
        │   └── variables.less
        └── package.json
```

#### Site Layout ####

```
#!text
    /eFoods
    ├── /
    ├── /browse
    ├── /item/*
    ├── /cart
    ├── /confirm
    ├── /checkout
    ├── /admin
    └── /api
        ├── catalog
        ├── cart
        ├── checkout
        ├── order/*
        ├── auth
        └── login
```

### Database ###

The table schema is as follows:

The **CATEGORY** table:

| COLUMN_NAME          | TYPE_NAME | DEC& | NUM& | COLUM& | COLUMN_DEF | CHAR_OCTE& | IS_NULL& |
| -------------------- | --------- | ---- | ---- | ------ | ---------- | ---------- | -------- |
| ID                   | INTEGER   | 0    | 10   | 10     | AUTOINCRE& | NULL       | NO       |
| NAME                 | VARCHAR   | NULL | NULL | 20     | NULL       | 40         | YES      |
| DESCRIPTION          | VARCHAR   | NULL | NULL | 50     | NULL       | 100        | YES      |
| PICTURE              | BLOB      | NULL | NULL | 10485& | NULL       | NULL       | YES      |

The **ITEM** table:

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
