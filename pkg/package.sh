#!/bin/bash

bundle install --path vendor/bundle

[ -d connect ] || mkdir connect

cp ../Product/Production/Deploy/ear/glassfish/target/CONNECT-GF.ear connect/

fpm \
  --rpm-os linux -a all \
  --pre-install pre-install.sh \
  -s dir -t rpm \
  --rpm-user orion --rpm-group orion \
  --prefix /opt \
  --description "Connect" -n "connect" \
  --version 4.4.1 \
  --iteration 1 \
  --directories . \
  ./connect

rm -rf connect
