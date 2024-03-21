shopt -s globstar
mkdir target
javac -d target/ src/**/*.java
jar cvfe GoGame.jar GoGame/Main -C target/ GoGame/
