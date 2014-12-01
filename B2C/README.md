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

#### APIs ####

```
#!text
| Method | Url           | Description                                                     |
| ------ | ------------- | --------------------------------------------------------------- |
| GET    | /api/catalog  | Query the catalog for items and categories.                     |
|        |               |                                                                 |
|        |               | * type               - Required, specifies the type of query.   |
|        |               |     * itemlist       - Get a list of items.                     |
|        |               |         * orderBy    - Sort order: one of number, name,         |
|        |               |                          price, catid.                          |
|        |               |         * searchTerm - Filter items by name.                    |
|        |               |         * category   - Filter items by category.                |
|        |               |         * offset     - Return results after given offset.       |
|        |               |         * fetch      - Return a specific number of results.     |
|        |               |         * minPrice   - Filter items by minimum price.           |
|        |               |         * maxPrice   - Filter items by maximum price.           |
|        |               |     * item           - Get a specific item.                     |
|        |               |         * number     - Unique identifier of specific item.      |
|        |               |     * catlist        - Get a list of categories.                |
|        |               |         * orderBy    - Sort order: one of id, name.             |
|        |               |         * searchTerm - Filter categories by name.               |
|        |               |         * offset     - Return results after given offset.       |
|        |               |         * fetch      - Return a specific number of results.     |
|        |               |     * category       - Get a specific category.                 |
|        |               |         * id         - Unique identifier of specific category.  |
| ------ | ------------- | --------------------------------------------------------------- |
| GET    | /api/cart     | Query the content of the shopping cart.                         |
|        |               |                                                                 |
|        |               | * action             - Required, specifies the type of action.  |
|        |               |     * list           - Get cart status and list of items.       |
| ------ | ------------- | --------------------------------------------------------------- |
| POST   | /api/cart     | Perform action on the shopping cart.                            |
|        |               |                                                                 |
|        |               | * action             - Required, specifies the type of action.  |
|        |               |     * add            - Add item to cart.                        |
|        |               |         * number     - Unique identifier of specific item.      |
|        |               |     * remove         - Remove item from cart.                   |
|        |               |         * number     - Unique identifier of specific item.      |
|        |               |     * bulk           - Perform a bulk update operation on cart. |
|        |               |         * number     - Unique identifier of specific item(s).   |
|        |               |                        Semi-colon separated list of IDs.        |
|        |               |         * quantity   - Corresponding quantities to update cart. |
|        |               |                        Semi-colon separated list of quantities. |
| ------ | ------------- | --------------------------------------------------------------- |
```

### Database ###

The table schema is as follows:

#### The **CATEGORY** table: ####

| COLUMN_NAME          | TYPE_NAME | DEC& | NUM& | COLUM& | COLUMN_DEF | CHAR_OCTE& | IS_NULL& |
| -------------------- | --------- | ---- | ---- | ------ | ---------- | ---------- | -------- |
| ID                   | INTEGER   | 0    | 10   | 10     | AUTOINCRE& | NULL       | NO       |
| NAME                 | VARCHAR   | NULL | NULL | 20     | NULL       | 40         | YES      |
| DESCRIPTION          | VARCHAR   | NULL | NULL | 50     | NULL       | 100        | YES      |
| PICTURE              | BLOB      | NULL | NULL | 10485& | NULL       | NULL       | YES      |

#### The **ITEM** table: ####

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
