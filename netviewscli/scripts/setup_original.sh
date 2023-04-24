#!/bin/sh
#To run me, type 
# sudo bash setup.sh
#If you modify this file, make to remove windows crlf (if applicable) with dos2unix command

#Install dependencies
echo y | sudo apt install python
echo y | sudo apt install nodejs
echo y | sudo apt install default-jdk

#Install Bazel
sudo apt install apt-transport-https curl gnupg -y
curl -fsSL https://bazel.build/bazel-release.pub.gpg | gpg --dearmor >bazel-archive-keyring.gpg
sudo mv bazel-archive-keyring.gpg /usr/share/keyrings
echo "deb [arch=amd64 signed-by=/usr/share/keyrings/bazel-archive-keyring.gpg] https://storage.googleapis.com/bazel-apt stable jdk1.8" | sudo tee /etc/apt/sources.list.d/bazel.list
echo y | sudo apt update 
echo y | sudo apt install bazel
echo y | sudo apt update 
echo y | sudo apt full-upgrade
echo y | sudo apt install bazel-3.0.0
#echo installed bazel 3.0.0

#At this point, Bazel 3.0.0 should be installed

#Attempt to install mininet
git clone https://github.com/mininet/mininet
cd mininet
git checkout -b 2.3.0d6
./util/install.sh -fnv
cd ..

#At this point, mininet should be installed

echo y | sudo apt install net-tools

#Attempt to clone NetViews repo
git clone https://github.com/netviews/ss-netviews

#Attempt to install ONOS
sudo mkdir onos
cd onos/
git init
git remote add origin https://gerrit.onosproject.org/onos
git pull
git checkout onos-2.3

#onos-prep-karaf script -> throwing erros about changing owner. Move the same script with the --no-same-owner tag into /tools/package
sudo rm ./tools/package/onos-prep-karaf
sudo chmod +x ../onos-prep-karaf
sudo cp ../onos-prep-karaf ./tools/package

sudo bazel build onos

echo 'export ONOS_ROOT=~/onos' >> ~/.bashrc
echo 'source $ONOS_ROOT/tools/dev/bash_profile' >> ~/.bashrc