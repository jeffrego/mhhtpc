#!/bin/sh

javac -classpath .:commons-net-2.0.jar *.java
java -classpath .:commons-net-2.0.jar Screen
