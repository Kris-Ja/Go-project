javac -g -d target/ src/*.java
rm -r bin
cp -r src bin
javac -g -classpath target/ bin/*.java
