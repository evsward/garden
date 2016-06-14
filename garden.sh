#!/bin/bash
source ~/.bash_profile
gardenrc
gogarden<<EOF
gardenbuild
exit
EOF
echo ‘build success’

