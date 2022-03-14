3061ef2b44e992a49dfef4ba28e10d7288996920aea2ca1376b7ce5abacb3c6c  ./PicResorter.jarPicResorter
===========
A simple hack to help a friend resort and rename pictures by drag and drop

Description
-----------

PicResorter is a GUI-Tool using drag & drop

You can drag Pictures into PicResorter. PicResorter will 
 * show the picture in a tile
 * show the typ of file with the color of the tile
   * **grey**       - picture is a normal file
   * **magenta**    - this is a link to a picture
   * **blue**       - picture is a hidden file
   * **orange**     - this is no picture, but a directory
   * **red**        - this file was not readable 
   * **empty**      - this seems to be no picture at all
 * the actual filename under every picture/file 
 * show a potential new filename

### clear
 The window will become empty
### Width
 You can select how many digits should be used 
### Start
 You can select what will be the first filenumber
### Name
 The new Name consists of:
 * Leading part
 * $1
 * Trailing part 
 The actuall file extension will remain
 
### Rename files
 all files will get their new name ;-)

Usage:
------
java -jar PicResorter.jar

Project Members
---------------

- ©2022 [Andreas Kielkopf](https://github.com/andreaskielkopf)


Depends on
---------- 

- `java` (1.8 or any newer version should work)

License
-------
The license is `GNU General Public License v3.0`


ToDos:
------


Install:
--------
1. Download [PicResorter.jar](https://github.com/andreaskielkopf/PicResorter/raw/master/PicResorter.jar) from [github](https://github.com/andreaskielkopf/PicResorter) and save it where you want 
`https://github.com/andreaskielkopf/PicResorter/raw/master/Picresorter.jar`
2. Check the sha256sum `sha256sum PicResorter.jar`
3. start with `java -jar ./PicResorter.jar`
 
#### sha256sum
3061ef2b44e992a49dfef4ba28e10d7288996920aea2ca1376b7ce5abacb3c6c  ./PicResorter.jar

----
P.S. If current developments trouble you, you can [find peace](https://www.jw.org/en/library/series/more-topics/russia-invades-ukraine-bible-meaning-hope/)
P.S. Wenn dich die aktuellen Entwicklungen beunruhigen kannst du [Frieden finden](https://www.jw.org/de/bibliothek/artikelserien/weitere-themen/russland-marschiert-in-ukraine-ein-biblische-bedeutung-hoffnung/)
 