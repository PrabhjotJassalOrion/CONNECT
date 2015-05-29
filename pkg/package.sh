#!/bin/bash

bundle install --path vendor/bundle

mkdir connect

cp ../Product/Production/Deploy/ear/glassfish/target/CONNECT-GF.ear connect/

fpm \
  --rpm-os linux -a all \
  --pre-install pre-install.sh \
  -s dir -t rpm \
  --rpm-user orionConnect --rpm-group orionConnect \
  --prefix /opt \
  --description "Connect" -n "connect" \
  --version 0.0.1 \
  --iteration 1 \
  --directories . \
  ./connect

rm -rf connect
