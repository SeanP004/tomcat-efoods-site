# -*- mode: ruby -*-
# vi: set ft=ruby :

require_relative 'setup/environ.rb'

Vagrant.configure(2) do |config|
  vmshared_symlink = 'VBoxInternal2/SharedFoldersEnableSymlinksCreate/'

  def deep_transform(object, block)
    if object.is_a? Array then
      return object.map! do |item|
        deep_transform(item, block)
      end
    elsif object.is_a? Hash then
      return object.each do |key, value|
        object[key] = deep_transform(value, block)
      end
    else
      return block.call(object)
    end
  end

  machines = deep_transform(Environ.machines, lambda { |object|
    if object.is_a? Proc then object.call else object end
  })

  base_machine = machines[:default]
  machines.each do |machine_name, machine|
    unless machine_name == :default then
      machine_data = base_machine.merge(machine) do |key, oldval, newval|
        if oldval.is_a? Array then
          oldval + newval
        elsif oldval.is_a? Hash then
          oldval.merge(newval)
        else
          newval
        end
      end

      # Configure and initialize machine
      config.vm.define machine_name do |vmconfig|
        machine_params = machine_data[:params]
        vmconfig.vm.box = machine_params[:box]
        vmconfig.vm.network machine_params[:network], ip: machine_params[:ip]
        vmconfig.vm.provider 'virtualbox' do |vb|
          vb.customize [
            'modifyvm', :id,
            '--name', machine_params[:boxname],
            '--groups', machine_params[:groups],
            '--natdnshostresolver1', 'on',
            '--memory', machine_params[:memory],
            '--cpus', machine_params[:cpus],
          ]
        end

        machine_ssh = machine_data[:sshparams]
        vmconfig.ssh.username = machine_ssh[:username]
        vmconfig.ssh.shell = machine_ssh[:shell]

        # Attach shared folders to machine
        machine_data[:syncdirs].each do |syncdir|
          if syncdir.is_a? Array then
            fields = [:hostpath, :vmpath, :options]
            syncdir.push({}) if syncdir.length == 2
            syncdir = [fields, syncdir].transpose.to_h
          end
          sfopts_default = {
            id: syncdir[:vmpath].gsub(/\W+/, "_"),
            create: true,
            symlink: true,
            owner: 'vagrant',
            group: 'vagrant',
          }
          sfconfig = sfopts_default.merge(syncdir[:options] ||= {})
          vmconfig.vm.synced_folder syncdir[:hostpath], syncdir[:vmpath] do |sf|
            sfconfig.each do |key, value|
              case key
                when :create        then sf.create = value
                when :disabled      then sf.disabled = value
                when :group         then sf.group = value
                when :mount_options then sf.mount_options = value
                when :owner         then sf.owner = value
                when :type          then sf.type = value
                else
                  sf[key] = value
              end
            end # sfconfig.each
          end # vmconfig.vm.synced_folder
          if sfconfig[:symlink]
            vmconfig.vm.provider 'virtualbox' do |vb|
              vb.customize [
                'setextradata', :id,
                "#{vmshared_symlink}/#{sfconfig[:id]}", 1
              ]
            end
          end
        end # machine_data[:syncdirs]

        # Run machine provisioning scripts
        machine_data[:scripts].each do |script|
          if script.is_a? Array then
            fields = [:name, :script, :options]
            script.push({}) if script.length == 2
            script = [fields, script].transpose.to_h
          end
          shopts_default = {
            inline: true,
            privileged: true,
            run: 'once',
          }
          shconfig = shopts_default.merge(script[:options] ||= {})
          shconfig[:name] = "#{machine_name}_#{script[:name]}"
          shconfig[if shconfig.delete(:inline) then :inline else :path end] = script[:script]
          vmconfig.vm.provision "shell", run: shconfig.delete(:run) do |sh|
            shconfig.each do |key, value|
              case key
                when :name        then sh.name = value
                when :inline      then sh.inline = value
                when :path        then sh.path = value
                when :privileged  then sh.privileged = value
                when :args        then sh.args = value
                when :env         then sh.env = value
                else
                  sh[key] = value
              end
            end # shconfig.each
          end # vmconfig.vm.provision "shell"
        end # machine_data[:scripts]

      end # config.vm.define
    end # unless machine_name == :default
  end # machines.each

end
