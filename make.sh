#!/bin/sh
export CLASSPATH=./jfreechart-1.0.1.jar:./xchart-3.4.1-CUSTOM.jar:.
javac *.java
echo
echo "My chart lib"
time java PlotMyLib
echo
echo "XChart lib"
time java PlotXLib
echo
echo "My chart"
time java PlotMy
echo
echo "XChart"
time java PlotX
echo
echo "JFreeChart"
time java PlotJ
echo
echo "XChart anti-aliasing"
time java PlotXAA
