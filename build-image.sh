#!/usr/bin/env bash

echo "build Started ...."

docker login default-route-openshift-image-registry.apps.demo.ibmdte.net -u $(oc whoami) -p $(oc whoami -t)
docker build -t wcare-ns/wcareuser:latest .
docker tag wcare-ns/wcareuser:latest  default-route-openshift-image-registry.apps.demo.ibmdte.net/wcare-ns/wcareuser:latest
docker push  default-route-openshift-image-registry.apps.demo.ibmdte.net/wcare-ns/wcareuser:latest


echo "build completed ...."
