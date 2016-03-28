# setup/environ.rb

module Environ
  class << self
    attr_accessor :boxname
    attr_accessor :homedir
    attr_accessor :machines
  end
end

Environ.boxname = boxname = 'eecs4413-project'
Environ.homedir = homedir = '/home/vagrant'
Environ.machines = {
  default: {
    params: {
      box: 'ubuntu/trusty64',
      groups: '/_vagrant',
      network: 'private_network',
      memory: 1024,
      cpus: 1,
    },
    sshparams: {
      username: 'vagrant',
      shell: "bash -c 'BASH_ENV=/etc/profile exec bash'",
    },
    syncdirs: [
      ['.', "#{homedir}/vagrant_root"]
    ],
    scripts: [
      {
        name: 'print_ip',
        script: <<-end,
          ip addr show dev eth1 | grep -o 'inet [0-9.]*'
        end
        options: {
          privileged: false,
          run: 'always',
        },
      },
    ],
  },
  main: {
    params: {
      boxname: "#{boxname}",
      ip: '192.168.56.101',
    },
    syncdirs: [
      ['B2C/WebContent', "#{homedir}/tomcat/webapps/eFoods"],
      ['B2C/build/classes', "#{homedir}/tomcat/webapps/eFoods/WEB-INF/classes"],
      ['AUTH', '/var/www/html/eFoods'],
    ],
    scripts: [
      {
        name: 'web',
        script: './setup/setup_web.sh',
        options: {
          inline: false,
          privileged: false,
        },
      },
      {
        name: 'auth',
        script: './setup/setup_auth.sh',
        options: {
          inline: false,
          privileged: false,
        },
      },
      {
        name: 'start_services',
        script: <<-end,
          $HOME/bin/tomcat_manage start &
          $HOME/bin/derby_manage start &
        end
        options: {
          privileged: false,
          run: 'always',
        },
      },
    ],
  },
}
