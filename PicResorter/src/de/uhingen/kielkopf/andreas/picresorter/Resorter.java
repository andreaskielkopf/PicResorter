package de.uhingen.kielkopf.andreas.picresorter;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.NoSuchFileException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JViewport;
import javax.swing.Scrollable;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.TransferHandler;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class Resorter extends JPanel
         implements ActionListener, Scrollable, ChangeListener, MouseListener, MouseMotionListener {
   private static final long     serialVersionUID=632873047009023245L;
   private JLabel                dropLabel       =new JLabel(
            "<html>Drag & Drop<br>Files here<br><br>doubleclick<br>to remove</html>");
   CopyOnWriteArrayList<Picture> picturelist;
   final Resorter                me;
   int                           columns         =5;
   final static ExecutorService  pool            =Executors.newWorkStealingPool();
   final static Cursor           HAND            =new Cursor(Cursor.HAND_CURSOR);
   final static Cursor           CURSOR          =new Cursor(Cursor.DEFAULT_CURSOR);
   public Resorter() {
      initialize();
      me=this;
   }
   private void initialize() {
      picturelist=Picture.getList();
      dropLabel.setHorizontalTextPosition(SwingConstants.CENTER);
      dropLabel.setVerticalTextPosition(SwingConstants.CENTER);
      dropLabel.setHorizontalAlignment(SwingConstants.CENTER);
      dropLabel.setVerticalAlignment(SwingConstants.CENTER);
      setLayout(new GridLayout(0, columns, 5, 5));
      add(dropLabel);
      addMouseListener(this);
      addMouseMotionListener(this);
   }
   public void addPicture(File pictureFile) throws Exception {
      Picture picture=new Picture(pictureFile);
      picturelist.add(picture);
      picture.updateName();
      SwingUtilities.invokeLater(new Runnable() {
         @Override
         public void run() {
            me.add(picture);
            me.revalidate();
         }
      });
   }
   /**
    * Transferhandler um Dateinamen von Bilern zu bekommen
    * 
    * @author andreas
    */
   private TransferHandler resortTransferhandler;
   private Point           pressed;
   private Picture         pic;
   private Picture         lastPic;
   public TransferHandler getResortTransferhandler() {
      if (this.resortTransferhandler == null) {
         this.resortTransferhandler=new TransferHandler() {
            private static final long serialVersionUID=-33795231479759105L;
            /**
             * We only support importing strings.
             */
            @Override
            public boolean canImport(TransferHandler.TransferSupport info) {
               if (!info.isDrop())
                  return false;
               boolean copySupported=(COPY & info.getSourceDropActions()) == COPY;
               if (!copySupported)
                  return false;
               info.setDropAction(COPY);
               if (info.isDataFlavorSupported(DataFlavor.javaFileListFlavor))
                  return true;
               return false;
            }
            /**
             * We support both copy and move actions.
             */
            @Override
            public int getSourceActions(JComponent c) {
               return TransferHandler.COPY;// TransferHandler.LINK+
            }
            /**
             * Perform the actual import. This demo only supports drag and drop.
             */
            @SuppressWarnings("unchecked")
            @Override
            public boolean importData(TransferHandler.TransferSupport info) {
               System.out.println("import:");
               if (!info.isDrop())
                  return false;
               // DropLocation dl=info.getDropLocation();
               try {
                  List<File> filelist=(java.util.List<File>) info.getTransferable()
                           .getTransferData(DataFlavor.javaFileListFlavor);
                  for (File file:filelist) {
                     pool.execute(new Runnable() {
                        @Override
                        public void run() {
                           try {
                              me.addPicture(file);
                              System.out.println(file.getPath());
                           } catch (NoSuchFileException | FileAlreadyExistsException e) {
                              System.err.println(e.getMessage());
                           } catch (Exception e) {
                              e.printStackTrace();
                           }
                        }
                     });
                  }
                  return true;
               } catch (UnsupportedFlavorException e) {
                  System.err.println(e.getMessage());
               } catch (IOException e) {
                  e.printStackTrace();
               }
               System.err.println("import not completed");
               return false;
            }
         };
      }
      return this.resortTransferhandler;
   }
   public void updateNames() {
      for (Picture picture:picturelist)
         picture.updateName();
      this.repaint();
   }
   @Override
   public void actionPerformed(ActionEvent e) {
      if (e.getActionCommand().equals(PicResorter.CLEAR)) {
         this.picturelist.clear();
         updatePictures();
      } else
         if (e.getActionCommand().equals(PicResorter.RENAME_FILES)) {
            SwingUtilities.invokeLater(new Runnable() {
               @Override
               public void run() {
                  CopyOnWriteArrayList<Picture> list=new CopyOnWriteArrayList<>(me.picturelist);
                  for (Picture picture:me.picturelist) {
                     if (picture.rename()) {
                        list.remove(picture);
                        me.remove(picture);
                        me.revalidate();
                        me.repaint();
                     }
                  }
                  me.picturelist.retainAll(list);
               }
            });
         }
      System.out.println(e.getActionCommand());
   }
   @Override
   public Dimension getPreferredScrollableViewportSize() {
      return getPreferredSize();
   }
   @Override
   public int getScrollableUnitIncrement(Rectangle visibleRect, int orientation, int direction) {
      return 0;
   }
   @Override
   public int getScrollableBlockIncrement(Rectangle visibleRect, int orientation, int direction) {
      return 0;
   }
   @Override
   public boolean getScrollableTracksViewportWidth() {
      return false;
   }
   @Override
   public boolean getScrollableTracksViewportHeight() {
      return false;
   }
   @Override
   public void stateChanged(ChangeEvent e) {
      int width=((JViewport) e.getSource()).getWidth();
      int sum  =5 + dropLabel.getWidth() + 5;
      int count=1;
      for (Picture picture:picturelist) {
         sum+=5 + picture.getWidth();
         if (sum > width)
            break;
         count++;
      }
      if (count < 3)
         count=3;
      if (columns == count)
         return;
      System.out.println("col=" + columns + " c=" + count + " " + sum);
      columns=count;
      me.setLayout(new GridLayout(0, columns, 5, 5));
      me.invalidate();
   }
   @Override
   public void mouseClicked(MouseEvent e) {
      int c=e.getClickCount();
      System.out.println(c);
      if ((lastPic != null) && (c == 2)) {
         picturelist.remove(lastPic);
         lastPic=null;
         pic=null;
         updatePictures();
      }
   }
   private void updatePictures() {
      this.removeAll();
      this.add(dropLabel);
      for (Picture picture1:picturelist)
         this.add(picture1);
      this.updateNames();
   }
   @Override
   public void mousePressed(MouseEvent e) {
      this.pressed=e.getPoint();
      Component o=this.getComponentAt(pressed);
      if (o instanceof Picture)
         pic=(Picture) o;
      if (pic != null) {
         System.out.println(pic.getNewName());
         pic.setSelected(true);
         setCursor(HAND);
      }
   }
   @Override
   public void mouseReleased(MouseEvent e) {
      if (pic != null) {
         Point     released=e.getPoint();
         Component o       =this.getComponentAt(released);
         if (o instanceof Picture) {
            Picture ziel=(Picture) o;
            int     von =this.picturelist.indexOf(pic);
            int     nach=this.picturelist.indexOf(ziel);
            if ((von >= 0) && (nach >= 0) && (von != nach)) {
               if (von > nach) {
                  picturelist.remove(von);
                  picturelist.add(nach, pic);
               } else {
                  picturelist.add(nach, pic);
                  picturelist.remove(von);
               }
               updatePictures();
            }
         }
         pic.setSelected(false);
         lastPic=pic;
         pic=null;
         setCursor(CURSOR);
      }
   }
   @Override
   public void mouseEntered(MouseEvent e) { /* not implemented */}
   @Override
   public void mouseExited(MouseEvent e) {
      if (pic != null) {
         pic.setSelected(false);
         pic=null;
         setCursor(CURSOR);
      }
   }
   @Override
   public void mouseDragged(MouseEvent e) { /* not implemented */}
   @Override
   public void mouseMoved(MouseEvent e) { /* not implemented */}
}
