#!/bin/bash
# git fetch && git checkout master
git checkout master
# git pull
read -p "Enter 'release branch' name to be created from 'master' (NOTE: No need of prefix 'release/'): " NEW_RELEASE_BRANCH_NAME
git checkout -b release/$NEW_RELEASE_BRANCH_NAME
# change any file
read -p "Enter new development version (For Example: 0.0.2-SNAPSHOT): " NEW_VERSION 
echo NEW_VERSION | mvn release:update-versions -DautoVersionSubmodules=true
git add .
git commit -am "updated to version $NEW_VERSION"
git push origin release/$NEW_RELEASE_BRANCH_NAME
