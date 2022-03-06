package de.uhingen.kielkopf.andreas.picresorter;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class Picture extends JPanel {
   private static final float                   a               =.25f;
   private static final Color                   COLOR_UNREADABLE=new Color(.99f, .0f, .0f, a);  // Color.RED;
   private static final Color                   COLOR_LINK      =new Color(.99f, .0f, .99f, a); // Color.MAGENTA;
   private static final Color                   COLOR_HIDDEN    =new Color(.2f, .2f, .5f, a);   // Color.BLUE/PINK;
   private static final Color                   COLOR_DIR       =new Color(.99f, .5f, .0f, a);  // Color.ORANGE;
   private static final Color                   COLOR_STANDARD  =new Color(.5f, .5f, .5f, a);   // Color.GRAY
   private static final long                    serialVersionUID=8612712940431200000L;
   private static CopyOnWriteArrayList<Picture> list            =new CopyOnWriteArrayList<>();
   public static CopyOnWriteArrayList<Picture> getList() {
      return list;
   }
   public static void setList(CopyOnWriteArrayList<Picture> list1) {
      if (list1 != null)
         Picture.list=list1;
   }
   private static Renamer renamer=new Renamer();
   public static Renamer getRenamer() {
      return renamer;
   }
   public static void setRenamer(Renamer renamer1) {
      if (renamer1 != null)
         Picture.renamer=renamer1;
   }
   private final File file;
   private final Path path;
   private JLabel     labelFile;
   private JLabel     newName;
   private Color      colorsave;
   private boolean    selected;
   public Picture(File file1) throws Exception {
      path=file1.toPath();
      this.file=file1;
      if (!Files.exists(path))
         throw new NoSuchFileException("File '" + path + "' not found");
      if (!Files.isSymbolicLink(path))
         for (Picture picture:list) {
            if ((Files.isSameFile(path, picture.path)) && (!Files.isSymbolicLink(picture.path)))
               throw new FileAlreadyExistsException("double File: " + path);
         }
      else
         for (Picture picture:list) {
            if ((Files.isSameFile(path, picture.path)) && (path.compareTo(picture.path) == 0))
               throw new FileAlreadyExistsException("double Link: " + path);
         }
      initialize();
      colorsave=getNameLabel().getForeground();
   }
   private void initialize() throws IOException {
      setLayout(new BorderLayout(0, 0));
      setBackground(Color.DARK_GRAY);
      setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
      decorate();
      createIcon();
      add(getLabelFile(), BorderLayout.CENTER);
      add(getNameLabel(), BorderLayout.SOUTH);
   }
   private void createIcon() {
      ImageIcon largeIcon=new ImageIcon(file.getPath());
      long      max      =128;
      int       b        =(int) max;
      int       h        =largeIcon.getIconHeight();
      int       w        =largeIcon.getIconWidth();
      int       x        =0;
      int       y        =0;
      if (h > w) {
         w=(int) ((w * max) / h);
         h=(int) max;
         x=(b - w) / 2;
      } else {
         h=(int) ((h * max) / w);
         w=(int) max;
         y=(b - h) / 2;
      }
      BufferedImage smallImg=new BufferedImage(b, b, BufferedImage.TYPE_4BYTE_ABGR);
      Graphics2D    g2      =smallImg.createGraphics();
      g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
      g2.setColor(COLOR_STANDARD);
      g2.fillRect(0, 0, b, b);
      g2.drawImage(largeIcon.getImage(), x, y, w, h, null);
      g2.dispose();
      getLabelFile().setIcon(new ImageIcon(smallImg));
   }
   private void decorate() throws IOException {
      Color color=COLOR_STANDARD;
      if (Files.isDirectory(path))
         color=COLOR_DIR;
      if (Files.isRegularFile(path))
         color=COLOR_STANDARD;
      if (Files.isHidden(path))
         color=COLOR_HIDDEN;
      if (Files.isSymbolicLink(path))
         color=COLOR_LINK;
      if (!Files.isReadable(path))
         color=COLOR_UNREADABLE;
      setBackground(color);
   }
   private JLabel getLabelFile() {
      if (labelFile == null) {
         labelFile=new JLabel(this.file.getName());
         labelFile.setFont(new Font("Dialog", Font.ITALIC, 12));
         labelFile.setHorizontalTextPosition(SwingConstants.CENTER);
         labelFile.setVerticalTextPosition(SwingConstants.BOTTOM);
         labelFile.setHorizontalAlignment(SwingConstants.CENTER);
      }
      return labelFile;
   }
   public String getNewName() {
      int    i   =list.indexOf(this);
      String name=renamer.getNameFor(file, i);
      return name;
   }
   public void updateName() {
      getNameLabel().setText(getNewName());
   }
   public boolean rename() {
      Path path2=path.normalize().resolveSibling(getNewName());
      if (Files.exists(path2))
         return false;
      try {
         Files.move(path, path2, StandardCopyOption.ATOMIC_MOVE);
         return true;
      } catch (IOException e) {
         System.err.println(e.getMessage());
      }
      return false;
   }
   @Override
   public String toString() {
      StringBuilder builder=new StringBuilder();
      builder.append("Picture [path=");
      builder.append(path);
      builder.append(", getNewName()=");
      builder.append(getNewName());
      builder.append("]");
      return builder.toString();
   }
   private JLabel getNameLabel() {
      if (newName == null) {
         newName=new JLabel("Namelabel");
         newName.setHorizontalAlignment(SwingConstants.CENTER);
      }
      return newName;
   }
   public void setSelected(boolean selected1) {
      this.selected=selected1;
      getNameLabel().setForeground((selected) ? Color.RED : colorsave);
      this.repaint();
      this.getParent().repaint();
   }
}
