#!/bin/sh

getent group orionConnect &>/dev/null || groupadd -r orionConnect -g 2134 &>/dev/null
getent passwd orionConnect &>/dev/null || \
  useradd -r -u 2134 -g orionConnect -d /opt/orionConnect -s /sbin/nologin \
  -c "connect" orionConnect &>/dev/null
