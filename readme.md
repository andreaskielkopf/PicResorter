PicResorter
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
1. Download PicResorter.jar [github](https://github.com/andreaskielkopf/PicResorter) and save it where you want `wget https://github.com/andreaskielkopf/PicResorter/Picresorter.jar/raw/master/Picresorter.jar`
2. Check the sha256sum `sha256sum PicResorter.jar`
 
#### sha256sum of v1.0
c034352a3a71e3688c0d452fec4ed4fedace90efd89b303752c42c0ce17fbabd  PicResorter.jar

----
P.S. If current developments trouble you, you can [find peace](https://www.jw.org/en/library/series/more-topics/russia-invades-ukraine-bible-meaning-hope/)
P.S. Wenn dich die aktuellen Entwicklungen beunruhigen kannst du [Frieden finden](https://www.jw.org/de/bibliothek/artikelserien/weitere-themen/russland-marschiert-in-ukraine-ein-biblische-bedeutung-hoffnung/)
 