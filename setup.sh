# setup some basic environment variables.
# define the add user function.

if [[ $EUID -ne 0 ]]; then
echo "You must be root to run this script. Aborting...";
   exit 1;
fi

source versions.sh

export CDIR=`pwd`
export LOGFILE=/root/build.log
export PASSWORD=`openssl passwd -1 password`

##########
# enable logging. Logs both to file and screen.
exec 2>&1
exec > >(tee -a $LOGFILE)

function add_a_user {
  # create the group, if needed.
  result=`getent group $1 | grep $1 | wc -l`
  if [ "$result" == "0" ];
  then
addgroup $1
  fi
  # put the root user into the group, if needed.
  result=`getent group $1 | grep root | wc -l`
  if [ "$result" == "0" ];
  then
usermod -a -G $1 root
  fi
  # create the user, if needed.
  result=`getent passwd $1 | grep $1 | wc -l`
  if [ "$result" == "0" ];
  then
useradd -m -s /bin/bash -g $1 $1 -p $PASSWORD
  fi
su $1 -c "mkdir -p /home/$1/.ssh"
  su $1 -c "chmod 700 /home/$1/.ssh"
  if [ ! -f /home/$1/.ssh/id_rsa ];
  then
su $1 -c "ssh-keygen -t rsa -P '' -f /home/$1/.ssh/id_rsa"
    su $1 -c "cat /home/$1/.ssh/id_rsa.pub >> /home/$1/.ssh/authorized_keys"
  fi
if [ ! -f /home/$1/.ssh/id_dsa ];
  then
su $1 -c "ssh-keygen -t dsa -P '' -f /home/$1/.ssh/id_dsa"
    su $1 -c "cat /home/$1/.ssh/id_dsa.pub >> /home/$1/.ssh/authorized_keys"
  fi
su $1 -c "chmod 600 /home/$1/.ssh/authorized_keys"
  echo "Created user: $1"
  # If you want to give sudo access to the accounts, uncomment the following line.
  # NOTE: This instance is very insecure. Proceed with caution.
  # adduser $1 sudo
}


