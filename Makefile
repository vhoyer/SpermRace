#%: %.class
	#clear
	#java bucky

#%.class: %.java
	#javac -g $<
####################################
bin=./bin
src=./src
SRC := $(wildcard src/*.java)
CLASSPATH=-classpath .:./bin:

list := $(SRC:src/%.java=$(bin)/%.class)

run: $(list)
	java $(CLASSPATH) Application

%: $(src)/%.java
	javac -g -d $(bin) $(CLASSPATH) $^

$(bin)/%.class: $(src)/%.java
	javac -g -d $(bin) $(CLASSPATH) $^

clean:
	rm bin/*.class

jar: $(list)
	jar -cfmv SpermRace.jar MANIFEST.MF bin/

build: $(list)
	javac -g -d $(bin) $(CLASSPATH) $(src)/*.java

rebuild:
	mkdir $(bin) || rm $(bin)/*.class || echo "nothing to remove"
	javac -g -d $(bin) $(CLASSPATH) $(src)/*.java
	java $(CLASSPATH) Application

.PHONY: run clean rebuild build jar
