#!/bin/bash

# CA create private key and root CA certificate
openssl genrsa -out root.key
openssl req -new -x509 -key root.key -out root.crt -subj "/CN=localhost" -nodes

# keystore
keytool -keystore kafka.keystore.jks -storepass password -alias localhost -validity 3650 -genkey -keyalg RSA -dname "CN=localhost"

# create CSR(certificate signing request)
keytool -keystore kafka.keystore.jks -storepass password -alias localhost -certreq -file kafka-signing-request.crt

# CA signs the certificate
openssl x509 -req -CA root.crt -CAkey root.key -in kafka-signing-request.crt -out kafka-signed.crt -days 3650 -CAcreateserial

# we can import root CA cert & our signed certificate
# this should be private and owned by the server
keytool -keystore kafka.keystore.jks -storepass password -alias CARoot -import -file root.crt -noprompt
keytool -keystore kafka.keystore.jks -storepass password -alias localhost -import -file kafka-signed.crt

# this is for clients
keytool -keystore kafka.truststore.jks -storepass password -noprompt -alias CARoot -import -file root.

# move all these files to certs directory
mkdir -p ../certs
mv *.crt ../certs
mv *.jks ../certs
mv *.key ../certs
