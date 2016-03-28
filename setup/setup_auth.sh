#!/bin/bash
# setup_auth.sh

USER=${USER:-vagrant}
VAGRANT_HOME=${VAGRANT_HOME:-/home/$USER}
HOME=$VAGRANT_HOME
OPENSSL_SUBJ="/C=CA/ST=ON/L=Toronto/O=York University/OU=EECS/CN=Vincent Chu/emailAddress=cse13261@cse.yorku.ca"
APACHE2_SSL='/etc/apache2/ssl'
USERS="$HOME/vagrant_root/setup/users.sample.txt"

## Routines: Helpers

  #
  # define(command, variable, value, outfile)
  #   adds an alias, export, declare, local
  #   variable and value pair to the given
  #   output file (or by default .bashrc).
  #
  # command: alias, export, declare, local
  # variable: name of the variable to set
  # value: value of the variable being set
  # outfile: output file to write to
  #
  define() {
    echo "$1 $2=\"$3\"" >> ${4:-$HOME/.bashrc}
  }

## Routines: Installation

  #
  # sudo install_basics()
  #   updates the system package repositories
  #   installs the basics packages:
  #     apache2
  #     apache2-utils
  #     apache2.2-common
  #     php5
  #     php5-curl
  #     libapache2-mod-php5
  #     openssl
  #   loads the apache2 mod_rewrite module
  #   opens access to the server logs
  #
  install_basics() {
    sudo apt-get update
    sudo apt-get install -y apache2
    sudo apt-get install -y apache2-utils apache2.2-common
    sudo apt-get install -y php5 php5-curl libapache2-mod-php5
    sudo apt-get install -y openssl
    define export PATH '$PATH:$HOME/bin:.'
    sudo a2enmod rewrite
    sudo service apache2 restart
    sudo chmod +rx /var/log/apache2
    sudo chmod +r /var/log/apache2/*.log
  }

## Routines: OpenSSL

  #
  # config_openssl()
  #   configures and generates the SSL certificate
  #   for the HTTPS on the web server
  #
  config_openssl() {
    sudo mkdir -p $APACHE2_SSL/{certs,private}
    cd $APACHE2_SSL
    if [[ ! -f ca.key || ! -f ca.csr || ! -f ca.crt ]]; then
      local pass=$(openssl rand -base64 32)
      echo -e "$pass\n$pass" | sudo openssl req -newkey rsa:2048 -keyout ca.key -out ca.csr -subj "$OPENSSL_SUBJ"
      echo $pass | sudo openssl x509 -req -days 365 -in ca.csr -signkey ca.key -out ca.crt
      sudo ln -s ca.key private/ssl-cert-snakeoil.key
      sudo ln -s ca.crt certs/ssl-cert-snakeoil.pem
    fi
    sudo a2enmod ssl
    sudo a2ensite default-ssl.conf
    sudo service apache2 restart
  }

## Routines: Config Users

  #
  # config_users()
  #   creates fake user accounts to test the
  #   authentication module from the setup/users.txt
  #   setup file.
  #
  config_users() {
    cat $USERS | while read line; do
      local name="$(echo $line | cut -f1 -d':')"
      local user="$(echo $line | cut -f2 -d':')"
      local pass="$(echo $line | cut -f3 -d':')"
      echo "Creating $user user account..."
      sudo useradd -c "$name" -p "$pass" "$user"
    done
  }

## Main

  install_basics
  config_openssl
  config_users
