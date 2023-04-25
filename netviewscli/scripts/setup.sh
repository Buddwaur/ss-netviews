#!/bin/sh
#If you modify this file, make sure to remove windows crlf (if applicable) with dos2unix command

#This script will install ONOS & Mininet in the current directory
CWD=$(pwd)

#Install dependencies
echo y | sudo apt install python
echo y | sudo apt install nodejs
echo y | sudo apt install default-jdk

#Install Bazel
sudo apt install apt-transport-https curl gnupg -y
curl -fsSL https://bazel.build/bazel-release.pub.gpg | gpg --dearmor >bazel-archive-keyring.gpg
sudo mv bazel-archive-keyring.gpg /usr/share/keyrings
echo "deb [arch=amd64 signed-by=/usr/share/keyrings/bazel-archive-keyring.gpg] https://storage.googleapis.com/bazel-apt stable jdk1.8" | sudo tee /etc/apt/sources.list.d/bazel.list 
sudo apt update && sudo apt install bazel 
sudo apt update && sudo apt install bazel-3.0.0
#At this point, Bazel 3.0.0 should be installed

#Clone Mininet
git clone https://github.com/mininet/mininet
cd mininet
git checkout -b 2.3.0d6
./util/install.sh -fnv
cd ..

#At this point, mininet should be installed

echo y | sudo apt install net-tools

#Attempt to install ONOS
git clone https://github.com/opennetworkinglab/onos.git
cd onos
git checkout onos-2.3
bazel build onos

#onos-prep-karaf script is throwing erros about changing owner. To resolve this we:
#Move the same script with the --no-same-owner tag into /tools/package.
#sudo rm ./tools/package/onos-prep-karaf
#sudo chmod +x ../onos-prep-karaf
#sudo cp ../onos-prep-karaf ./tools/package

#sudo bazel build onos

cd $CWD

echo export ONOS_ROOT="$CWD/onos"
echo source $ONOS_ROOT/tools/dev/bash_profile

echo export ONOS_ROOT="$CWD/onos" >> ~/.bashrc
echo 'source $ONOS_ROOT/tools/dev/bash_profile' >> ~/.bashrc