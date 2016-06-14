#!/bin/bash
# setup_web.sh

USER=${USER:-vagrant}
VAGRANT_HOME=${VAGRANT_HOME:-/home/$USER}
HOME=$VAGRANT_HOME
APACHE_MIRROR=${APACHE_MIRROR:-http://mirror.csclub.uwaterloo.ca/apache}
TOMCAT_HOME=${TOMCAT_HOME:-$HOME/tomcat}
TOMCAT_VERSION=${TOMCAT_VERSION:-8.0.33}
TOMCAT_PORT=${TOMCAT_PORT:-8080}
TAGLIBS_VERSION=${TAGLIBS_VERSION:-1.2.5}
DERBY_HOME=${DERBY_HOME:-$HOME/derby}
DERBY_VERSION=${DERBY_VERSION:-10.12.1.1}
DERBY_PORT=${DERBY_PORT:-1527}
NVM_SOURCE=${NVM_SOURCE:-https://raw.githubusercontent.com/creationix/nvm}
NVM_DIR=${NVM_DIR:-$HOME/.nvm}
NVM_VERSION=${NVM_VERSION:-0.31.0}

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

  #
  # create_command(command, ...options)
  #   generates and saves a shell script from the
  #   given function referenced by command and with
  #   given options; saves shell script in ~/bin
  #   as <command>.
  #
  # command: name of function to reference
  # options:
  #   -D<variable>=<value>
  #     declare an environment variable to include
  #   -F<function>
  #     name of function to include
  #   <variable>
  #     declare an environment variable to include.
  #     same as: -D<variable>=$<variable>
  #
  create_command() {
    local arg fn varname value
    local -a helpers=()
    local func_name=$1; shift
    local sh_script=$HOME/bin/$func_name
    mkdir -p $HOME/bin
    touch $sh_script
    chmod +x $sh_script
    {
      echo '#!/bin/bash'
      echo "# $sh_script"
      echo
      for arg in "$@"; do
        if [[ $arg == '-D'* ]]; then
          varname=$(echo ${arg:2} | cut -f1 -d'=')
          value=$(echo ${arg:2} | cut -f2- -d'=')
          define declare $varname "$value" /dev/stdout
        elif [[ $arg == '-F'* ]]; then
          helpers+=(${arg:2})
        elif [[ $arg == '-'* ]]; then
          echo "Warning: create_command bad option $arg"
        else
          define declare $arg "$(eval echo '$'$arg)" /dev/stdout
        fi
      done
      echo
      for fn in ${helpers[@]} $func_name; do
        declare -f $fn; echo
      done
      echo $func_name' "$@"'
    } | sed -re 's/[ ]+$//' > $sh_script
  }

  #
  # package_url(package)
  #   outputs the download link for the given package
  #
  # package: name of the package to download
  #
  package_url() {
    local package=$1
    local -A urls=(
      [tomcat]='$1/tomcat/tomcat-8/v$2/bin/apache-tomcat-$2.tar.gz'
      [taglibs]='$1/tomcat/taglibs/taglibs-standard-$2/taglibs-standard-$3-$2.jar'
      [derby]='$1/db/derby/db-derby-$2/db-derby-$2-bin.tar.gz'
      [nvm]='$1/v$2/install.sh'
    )
    local -A args=(
      [tomcat]="$APACHE_MIRROR $TOMCAT_VERSION"
      [taglibs]="$APACHE_MIRROR $TAGLIBS_VERSION $2"
      [derby]="$APACHE_MIRROR $DERBY_VERSION"
      [nvm]="$NVM_SOURCE $NVM_VERSION"
    )
    set ${args[$package]}
    eval echo ${urls[$package]}
  }

  #
  # extract(archive)
  #   A handy extract program for extracting
  #   files from various archive formats
  #
  # archive: filepath to archive to be extract
  #
  extract() {
    if [ ! -f $1 ] ; then
      echo "'$1' is not a valid file!"
    else
      case $1 in
        *.tar.bz2)  tar xvjf $1     ;;
        *.tar.gz)   tar xvzf $1     ;;
        *.bz2)      bunzip2 $1      ;;
        *.rar)      unrar x $1      ;;
        *.gz)       gunzip $1       ;;
        *.tar)      tar xvf $1      ;;
        *.tbz2)     tar xvjf $1     ;;
        *.tgz)      tar xvzf $1     ;;
        *.zip)      unzip $1        ;;
        *.Z)        uncompress $1   ;;
        *.7z)       7z x $1         ;;
        *.jar)      jar xf $1       ;;
        *.war)      jar xf $1       ;;
        *)          echo "'$1' cannot be extracted via >extract<" ;;
      esac
    fi
  }

  #
  # move_merge(source, dest)
  #   rename source to dest, or move source(s) to
  #   destinations, merges the source directory with
  #   content in destination directory
  #
  # source:   source directory to move files from
  # dest:     destination directory to move files to
  #
  move_merge() {
    local dest="${@:${#@}}"
    local abspath="$(cd "$(dirname "$dest")"; pwd)/$(basename "$dest")"
    for src in ${@:1:$((${#@} -1))}; do (
      cd "$src";
      find . -type d -exec mkdir -p "$abspath"/\{} \;
      find . -type f -exec mv -v \{} "$abspath"/\{} \;
      find . -type d -empty -delete
    ) done
    rm -fr $1
  }

## Routines: Installation

  #
  # sudo install_basics()
  #   updates the system package repositories
  #   installs the basics packages:
  #     build-essential
  #     make
  #     git
  #     default-jdk
  #
  install_basics() {
    sudo apt-get update
    sudo apt-get install -y build-essential make git
    sudo apt-get install -y default-jdk
    define export JAVA_HOME '/usr/lib/jvm/default-java'
    define export PATH '$PATH:$JAVA_HOME/bin'
    define export PATH '$PATH:$HOME/bin:.'
  }

  #
  # [sudo] install_package(download_url, dest, environ)
  #   installs the package from the given download
  #   link to the given destination and sets the
  #   given environment variable to that destination.
  #
  # download_url: download source URL of the package
  # dest:         destination path to place extracted files
  # environ:      variable name to set as destination path
  #
  install_package() {
    local package=$(basename $1)
    echo "Downloading: $1"; wget -q $1
    case $package in
      *.tar.gz)
        echo "Extracting: $package"; extract $package
        move_merge "$(basename $package '.tar.gz')" "$2"
        define export $3 $2
        define export PATH '$PATH:$'$3'/bin'
        rm -v $package
        ;;
      *.jar)
        mv -v $package $2/$package
        ;;
    esac
  }

  #
  # install_tomcat()
  #   installs the tomcat server
  #
  install_tomcat() {
    sudo chown -R vagrant $TOMCAT_HOME
    sudo chgrp -R vagrant $TOMCAT_HOME
    install_package $(package_url tomcat) $TOMCAT_HOME CATALINA_HOME
    define export TOMCAT_HOME $TOMCAT_HOME
    define alias tomcat_start 'tomcat_manage start'
    define alias tomcat_stop  'tomcat_manage stop'
    for i in impl spec jstlel compat; do
      install_package $(package_url taglibs $i) $TOMCAT_HOME/lib
    done
    for i in $TOMCAT_HOME/lib/*.jar; do
      define '' CLASSPATH '$CLASSPATH:'$i $TOMCAT_HOME/bin/setenv.sh
    done
  }

  #
  # install_derby()
  #   installs the derby DB and server
  #
  install_derby() {
    install_package $(package_url derby) $DERBY_HOME DERBY_HOME
    define alias derby_start 'derby_manage start'
    define alias derby_stop 'derby_manage stop'
    for i in $DERBY_HOME/lib/*.jar; do
      define '' CLASSPATH '$CLASSPATH:'$i $TOMCAT_HOME/bin/setenv.sh
    done
  }

  #
  # install_nodejs()
  #   installs NodeJS, Bower, GruntJS and LESS-CSS
  #
  install_nodejs() {
    echo "Downloading and running: $(package_url nvm)";
    wget -qO- "$(package_url nvm)" | NVM_DIR=$NVM_DIR bash
    source $NVM_DIR/nvm.sh
    echo "Downloading: Node"
    nvm install node &> /dev/null
    nvm use node
    npm install -g bower grunt-cli less
  }

## Routines: Maintenance

  #
  # compile_servlets()
  #   compiles the java source code in the B2C/src
  #   directory into the B2C/build/classes directory
  #   with the servlet API.
  #
  compile_servlets() {
    cd $HOME/vagrant_root/B2C/src
    local fileset=$(mktemp 'java.txt.XXXXX')
    find . | grep '[.]java$' | sed 's|^[.]/||' > $fileset
    javac -cp .:$TOMCAT_HOME/lib/servlet-api.jar -d ../build/classes @$fileset
    rm $fileset
  }

  #
  # tomcat_manage(action)
  #   manages the Tomcat server
  # action:
  #   start   - starts the server
  #   stop    - stops the server
  #   deploy  - recompiles the java source code
  #   backup  - backups the java binaries
  #   clean   - cleans the java binaries
  #
  tomcat_manage() {
    local classes=$HOME/vagrant_root/B2C/build/classes
    if [[ 'start' == $1 ]]; then
      $TOMCAT_HOME/bin/startup.sh
    elif [[ 'stop' == $1 ]]; then
      $TOMCAT_HOME/bin/shutdown.sh
    elif [[ 'deploy' == $1 ]]; then
      tomcat_manage clean
      compile_servlets
    elif [[ 'backup' == $1 ]]; then
      cp -a $classes $classes-$(date '+%y%m%d%H%M')
    elif [[ 'clean' == $1 ]]; then
      rm -rf $classes/*
    fi
  }

  #
  # derby_manage(action)
  #   manages the Derby DB and server
  # action:
  #   start   - starts the server
  #   stop    - stops the server
  #   deploy  - cleans and loads the database
  #   clean   - cleans the database
  #
  derby_manage() {
    source $DERBY_HOME/bin/setNetworkServerCP
    if [[ 'start' == $1 || 'stop' == $1 ]]; then
      $DERBY_HOME/bin/$1NetworkServer -p $DERBY_PORT
    elif [[ 'deploy' == $1 ]]; then
      derby_manage clean
      $DERBY_HOME/bin/ij $HOME/vagrant_root/setup/db.setup.sql
    elif [[ 'clean' == $1 ]]; then
      $DERBY_HOME/bin/ij $HOME/vagrant_root/setup/db.cleanup.sql
    fi
  }

## Routines: B2B

  #
  # b2b_compile()
  #   compiles the java source code in the B2B/src
  #   directory into the B2B/bin directory.
  #
  b2b_compile() {
    cd $HOME/vagrant_root/B2B/src
    local fileset=$(mktemp 'java.txt.XXXXX')
    find . | grep '[.]java$' | sed 's|^[.]/||' > $fileset
    javac -d ../bin @$fileset
    rm $fileset
  }

  #
  # b2b_manage(action)
  #   manages the B2B application
  # action:
  #   run     - runs the application
  #   compile - recompiles the application's binaries
  #   backup  - creates a backups of the application's binaries
  #   clean   - cleans the application's binaries
  #
  b2b_manage() {
    local b2b=$HOME/vagrant_root/B2B
    if [[ 'run' == $1 ]]; then
      if [[ ! -f controller/Main.class ]]; then
        b2b_manage compile
      fi
      (cd $b2b; java -cp '.:bin' controller.Main)
    elif [[ 'compile' == $1 ]]; then
      b2b_manage clean
      b2b_compile
    elif [[ 'backup' == $1 ]]; then
      cp -a $b2b/bin $b2b/bin-$(date '+%y%m%d%H%M')
    elif [[ 'clean' == $1 ]]; then
      rm -rf $b2b/bin/*
    fi
  }

## Main

  install_basics
  install_tomcat
  install_derby
  install_nodejs
  create_command b2b_manage -Fb2b_compile
  create_command tomcat_manage TOMCAT_HOME -Fcompile_servlets
  create_command derby_manage DERBY_HOME DERBY_PORT
  derby_manage deploy
  tomcat_manage deploy
