#!/bin/bash

fpm \
  --rpm-os linux -a all \
  --pre-install pre-install.sh \
  -s dir -t rpm \
  --rpm-user orionConnect --rpm-group orionConnect \
  --prefix /opt/orionConnect \
  --description "Orion Connect" -n "orionConnect" \
  --version 0.0.1 \
  --iteration 1 \
  --directories . \
  orionConnect-0.0.1
