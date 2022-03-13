#
# This is /src/meson.build in a eclipse java project, where all java sources are in a tree-structure beginning with src/de/....
#
# The main java source is in the following file:
#
mysource = files( 'de/uhingen/kielkopf/andreas/picresorter/PicResorter.java' )

#
# Now meson should compile all necessary java-files and creat a runnable jar file 
#
# The name of the jarfile should be PicResorter.jar
# And the main class that should be started is de.uhingen.kielkopf.andreas.picresorter.PicResorter
#
myjar = jar('PicResorter', 
            mysource,
            main_class : 'de.uhingen.kielkopf.andreas.picresorter.PicResorter')

#
# The result will be placed in  $builddirectory/src/PicResorter.jar
#