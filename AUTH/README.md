# <a name="top"></a>AUTH: The Authenication Service

[<- Back to main](../README.md)

* [Getting Started](#start)
* [Usage](#usage)
* [Configurations](#config)
* [Structure](#struct)

## <a name="start"></a>Getting Started

1.  Run the following commands in the terminal to start the
    virtual machine and to connect into the server. See the
    main [README](../README.md) for more details.

        vagrant up
        vagrant ssh

2.  To restart the Apache web server:

        sudo service apache2 restart

## <a name="usage"></a>Usage

This `AUTH` script is request via a `GET` or `POST` request.
The parameters are:

Parameters | Description
---------- | ------------------------------
ref        | The referrer URL to redirect to user back to.
signer     | Hash of parameter and secret to ensure integrity of request
callback   | URI to authenticate callback B2C server

Redirects user back to the callback, on successful authentication.

## <a name="config"></a>Configurations

### `./config.ini`

Parameters | Description
---------- | ------------------------------
`secret`   | Agreed secret key with B2C server
`uri`      | URI to authenticate callback B2C server (can be overwrite by callback parameter, see Usage)

### `./.htaccess`

**With `HTTP`:**

    RewriteEngine On
    RewriteCond %{REQUEST_URI} \.ini$
    RewriteRule \.ini$ - [R=404]

**With `HTTPS`, append these additional lines of code:**

    RewriteEngine On
    RewriteCond %{HTTPS} !=on
    RewriteRule (.*) https://%{HTTP_HOST}%{REQUEST_URI} [R,L]

### `./zoned/.htaccess`

**With `HTTP`:**

    AuthType Basic
    AuthName "Restricted Access"
    AuthBasicProvider pam
    require valid-user

    Order deny,allow
    deny from all
    allow from localhost

**With `HTTPS`, append these additional lines of code:**

    SSLRequireSSL

**Note:** With HTTPS, because the SSL certificate on the server is self-signed, your browser may warn you about the site being 
insecure or prevent you from accessing the page. Please select, `Continue Anyways` or `Add to Exceptions` and ignore the error.

## <a name="struct"></a>Structure

    AUTH            # Root directory
    ├── assets      # Static assets for the HTML markup
    │   ├── css     # Stylesheet
    │   └── images  # Logo and favicon
    └── zoned       # HTTP basic auth required

[<- Top of Page](#top)
