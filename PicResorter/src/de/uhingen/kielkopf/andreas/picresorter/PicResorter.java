package de.uhingen.kielkopf.andreas.picresorter;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JViewport;
import javax.swing.WindowConstants;

import de.uhingen.kielkopf.andreas.rechner.mybtn.GlasButton;

/**
 * A snall hack for a friend to sort and rename pictures with drag and drop
 * 
 * @author Andreas Kielkopf
 * @version 1.0
 * @date 6.3.2022 given into GPL v3 or higher
 */
public class PicResorter {
   public static final String RENAME_FILES="Rename Files";
   public static final String CLEAR       ="clear";
   JFrame                     frame;
   private JPanel             panel;
   private Resorter           resorter;
   private JPanel             panel_1;
   private JButton            btnRename;
   private JButton            btnClear;
   private JScrollPane        scrollPane;
   /**
    * Launch the application.
    */
   public static void main(String[] args) {
      EventQueue.invokeLater(new Runnable() {
         @Override
         public void run() {
            try {
               PicResorter window=new PicResorter();
               window.frame.setVisible(true);
            } catch (Exception e) {
               e.printStackTrace();
            }
         }
      });
   }
   /**
    * Create the application.
    */
   public PicResorter() {
      initialize();
   }
   /**
    * Initialize the contents of the frame.
    */
   private void initialize() {
      Picture.getRenamer().setResorter(getResorter());
      frame=new JFrame();
      frame.setBounds(100, 100, 800, 600);
      frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
      frame.getContentPane().add(getPanel(), BorderLayout.CENTER);
   }
   private JPanel getPanel() {
      if (panel == null) {
         panel=new JPanel();
         panel.setLayout(new BorderLayout(0, 0));
         panel.add(getPanel_1(), BorderLayout.SOUTH);
         panel.add(getScrollPane(), BorderLayout.CENTER);
      }
      return panel;
   }
   private Resorter getResorter() {
      if (resorter == null)
         resorter=new Resorter();
      return resorter;
   }
   private JPanel getPanel_1() {
      if (panel_1 == null) {
         panel_1=new JPanel();
         panel_1.setLayout(new BorderLayout(0, 0));
         panel_1.add(Picture.getRenamer(), BorderLayout.CENTER);
         panel_1.add(getBtnRename(), BorderLayout.EAST);
         panel_1.add(getBtnClear(), BorderLayout.WEST);
      }
      return panel_1;
   }
   private JButton getBtnRename() {
      if (btnRename == null) {
         btnRename=new GlasButton(RENAME_FILES);
         btnRename.setFont(Picture.getRenamer().getFont());
         btnRename.addActionListener(getResorter());
      }
      return btnRename;
   }
   private JButton getBtnClear() {
      if (btnClear == null) {
         btnClear=new GlasButton(CLEAR);
         btnClear.setFont(Picture.getRenamer().getFont());
         btnClear.addActionListener(getResorter());
      }
      return btnClear;
   }
   private JScrollPane getScrollPane() {
      if (scrollPane == null) {
         scrollPane=new JScrollPane(getResorter());
         scrollPane.setTransferHandler(getResorter().getResortTransferhandler());
         JViewport vp=scrollPane.getViewport();
         vp.addChangeListener(getResorter());
      }
      return scrollPane;
   }
}
