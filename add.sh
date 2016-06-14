#!/bin/bash
source ~/.bash_profile
svn stat|awk '{if($1 == "?") print $2}'|xargs svn add
svn commit -m "sync"
