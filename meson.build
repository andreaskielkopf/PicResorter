#
# How to converted a standard eclipse java git project to meson 
# 
# meson will be used to build the executable jar file
# (C) 13.3.2022 Andreas Kielkopf given into GPL v3
#
# This file meson.build is locatetd in the root of the project
# ~/git/PicResorter/meson.build
#
project('PicResorter', 'java') # The name of the project in eclipse

#
# All java sources are in the subdir ~/git/PicResorter/src/
# in a tree like structure. meson will dive 
# therfore you need also a ./src/meson.buildinto this 
#
subdir('src') 

test('javatest', myjar)

# After placing the the meson.build files in the right place, you only need to run:
#
# meson setup build
#
# to create ./build and configure it. you may use any other name for "build", like "buidir" ...

# Then run:
#
# cd ./build;meson compile;cd ..

#
# The jarfile can be found at ./build/src/PicResorter.jar
#