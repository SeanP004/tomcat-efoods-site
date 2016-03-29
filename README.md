# EECS 4413: Project C: Foods R Us!

An E-Commerce Site Backed by Web Services

* [EECS 4413 Building E-Commerce Systems, Fall 2014](docs/course_desc.md)
* [Professor H. Roumani](http://www.eecs.yorku.ca/~roumani/)
* [Project C](docs/requirements.md)
* [York University](http://www.eecs.yorku.ca)

![Workflow](docs/workflow.gif)

The project consists of three separate, but related components: an
E-Commerce frontend, a B2B batch backend WSDL tool and a PHP third-party Login.
Each component is stored in a different directory in this
repository. Each component has its own `README.md` for more details on
installation and development. The components are:

1. [B2C](B2C/README.md), the frontend client website
2. [B2B](B2B/README.md), the non-interactive, offline business-to-business procurement application
3. [AUTH](AUTH/README.md), the authentication service

## Requirements

* [Oracle VirtualBox](https://www.virtualbox.org/wiki/Downloads)
* [Vagrant](https://www.vagrantup.com/downloads.html)

## Usage

1.  Install Oracle VirtualBox and Vagrant
2.  Run the following commands in the terminal:

    ```
    git clone https://github.com/vwchu/EECS4413-ProjC.git
    cd EECS4413-ProjC
    vagrant up
    ```

3.  Open a web browser and navigate to
    [this page](http://192.168.56.101:8080/eFoods).

    **Note**: For authentication, a set of sample users (names, logins, and
    passwords) are provided in [setup/users.sample.txt](setup/users.sample.txt).

4.  To access, the server directly, run the `vagrant ssh` command in the
    terminal or connect via PuTTY or SSH to `vagrant@localhost:2222`.
    You can use the `tomcat_manage` and `derby_manage` to manage the web
    server and database respectively. The server listens to port 8080
    by default and the database server listens to port 1527 by default.

    For `tomcat_manage`, the avaiable operations:

      * `start`   - starts the server
      * `stop`    - stops the server
      * `deploy`  - recompiles the java source code
      * `backup`  - backups the java binaries
      * `clean`   - cleans the java binaries

    For `derby_manage`, the avaiable operations:

      * `start`   - starts the database server
      * `stop`    - stops the database server
      * `deploy`  - rebuilds the database from scratch as before
      * `clean`   - cleans the database

5.  To stop (halt), suspend or delete (destroy) the virtual
    machine, use the commands: `vagrant halt`, `vagrant suspend`,
    or `vagrant destroy`. To restore the machine, run `vagrant up`.
