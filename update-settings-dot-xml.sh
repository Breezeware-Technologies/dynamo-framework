#!/bin/bash
SOURCE_DIR="$(dirname "${BASH_SOURCE[0]}")"
DEST_FILE_NAME=settings.xml
DESTINATION_FILE=$SOURCE_DIR/$DEST_FILE_NAME

#VALIDATE IF ENVIRONMENT VARIABLE VALUE IS PRESENT
if [[ $NEXUS_REPO_USERNAME ]];
then
  echo "Envirionment variable is present."
else
  >&2 echo "Envirionment variable is not present."
  exit 1
fi;

#VALIDATE IF ENVIRONMENT VARIABLE VALUE IS PRESENT
if [[ $NEXUS_REPO_PASSWORD ]];
then
  echo "Envirionment variable is present."
else
  >&2 echo "Envirionment variable is not present."
  exit 1
fi;

if [ -f $DESTINATION_FILE ]; then
	cat $DESTINATION_FILE | sed -i "s/%NEXUS_REPO_USERNAME%/$NEXUS_REPO_USERNAME/g" $DESTINATION_FILE
	cat $DESTINATION_FILE
	cat $DESTINATION_FILE | sed -i "s/%NEXUS_REPO_PASSWORD%/$NEXUS_REPO_PASSWORD/g" $DESTINATION_FILE
	cat $DESTINATION_FILE
else
	>&2 echo "$DESTINATION_FILE is not present."
	exit 0
fi
