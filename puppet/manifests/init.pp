# init.pp - Puppet manifest

exec { 'apt-get update':
  command => '/usr/bin/apt-get update',
}

class base {

    # allow anyone to install stuff into /opt
    file { "/opt":
        ensure => directory,
        mode => 0777
    }
}
