# A Complete script for generating fresh files from gen-runtime.sh while
# maintaining the differences between specific files. 
# overwrites old java arrays with new ones.  

./gen-runtime.sh
mv Boolean.java.tmp Boolean.java
mv Character.java.tmp Character.java
mv Double.java.tmp Double.java
mv Float.java.tmp Float.java
mv Integer.java.tmp Integer.java
mv Long.java.tmp Long.java
mv Opaque.java.tmp Opaque.java
mv String.java.tmp String.java
./float-gen.py
./double-gen.py
./enum-gen.py
rm *.tmp
