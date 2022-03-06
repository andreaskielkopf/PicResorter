package de.uhingen.kielkopf.andreas.picresorter;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.text.NumberFormat;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class Renamer extends JPanel implements ChangeListener, ActionListener, KeyListener {
   private static final long serialVersionUID=2187744020820633335L;
   private JTextField        txtDateiname;
   private JSpinner          spinnerW;
   private JSpinner          spinnerS;
   private Font              font            =new Font("Dialog", Font.PLAIN, 18);
   private JLabel            lblWidth;
   private JLabel            lblStart;
   private JLabel            lblName;
   private NumberFormat      nf              =NumberFormat.getInstance();
   Resorter                  resorter;
   public Renamer() {
      initialize();
   }
   private void initialize() {
      setFont(font);
      setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
      add(Box.createHorizontalStrut(20));
      add(getLblWidth());
      add(getSpinnerW());
      add(Box.createHorizontalStrut(20));
      add(getLblStart());
      add(getSpinnerS());
      add(Box.createHorizontalStrut(20));
      add(getLblName());
      add(getTxtDateiname());
      add(Box.createHorizontalStrut(20));
   }
   private JTextField getTxtDateiname() {
      if (txtDateiname == null) {
         txtDateiname=new JTextField();
         txtDateiname.setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
         txtDateiname.setCaretColor(Color.RED);
         txtDateiname.setFont(font);
         txtDateiname.setHorizontalAlignment(SwingConstants.CENTER);
         txtDateiname.setText("Pict_$1");
         txtDateiname.setColumns(5);
         txtDateiname.addActionListener(this);
         txtDateiname.addKeyListener(this);
      }
      return txtDateiname;
   }
   private JSpinner getSpinnerW() {
      if (spinnerW == null) {
         spinnerW=new JSpinner();
         int                digits=3;
         SpinnerNumberModel wsnm  =new SpinnerNumberModel(digits, 1, 7, 1);
         nf.setGroupingUsed(false);
         nf.setMaximumFractionDigits(0);
         nf.setMinimumIntegerDigits(digits);
         nf.setMaximumIntegerDigits(digits);
         wsnm.addChangeListener(this);
         spinnerW.setModel(wsnm);
         spinnerW.setFont(font);
      }
      return spinnerW;
   }
   private JSpinner getSpinnerS() {
      if (spinnerS == null) {
         spinnerS=new JSpinner();
         spinnerS.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
         spinnerS.setAlignmentX(0.25f);
         SpinnerNumberModel ssnm=new SpinnerNumberModel(1, 1, 1000, 1);
         ssnm.addChangeListener(this);
         spinnerS.setModel(ssnm);
         spinnerS.setFont(font);
      }
      return spinnerS;
   }
   private JLabel getLblWidth() {
      if (lblWidth == null) {
         lblWidth=new JLabel("Width");
         lblWidth.setFont(font);
      }
      return lblWidth;
   }
   private JLabel getLblStart() {
      if (lblStart == null) {
         lblStart=new JLabel("Start");
         lblStart.setFont(font);
      }
      return lblStart;
   }
   private JLabel getLblName() {
      if (lblName == null) {
         lblName=new JLabel("Name");
         lblName.setFont(font);
      }
      return lblName;
   }
   /**
    * Erzeugt eunen neuen Namen für dieses File
    * 
    * @param file
    * @param i
    * @return
    */
   public String getNameFor(File file, int i) {
      int    start =((SpinnerNumberModel) getSpinnerS().getModel()).getNumber().intValue();
      String rename=getTxtDateiname().getText();
      String name  =file.getName();
      String number=nf.format(start + i);
      String ext   ="";
      if (name.contains("."))
         ext=name.substring(name.lastIndexOf("."));
      if (!rename.contains("$1"))
         rename+="_$1";
      rename=rename.replace("$1", number);
      return rename + ext;
   }
   public void setResorter(Resorter resorter1) {
      if (resorter1 == null)
         return;
      this.resorter=resorter1;
   }
   @Override
   public void stateChanged(ChangeEvent e) {
      if (e.getSource() == getSpinnerW().getModel()) {
         SpinnerNumberModel ssnm  =(SpinnerNumberModel) getSpinnerS().getModel();
         int                digits=((SpinnerNumberModel) getSpinnerW().getModel()).getNumber().intValue();
         nf.setMaximumIntegerDigits(digits);
         nf.setMinimumIntegerDigits(digits);
         int max=((int) Math.pow(10, digits));
         if (ssnm.getNumber().intValue() > max)
            ssnm.setValue(Integer.valueOf(max));
         ssnm.setMaximum(Integer.valueOf(max));
         getSpinnerS().setModel(ssnm);
      }
      updateLater();
   }
   @Override
   public void actionPerformed(ActionEvent e) {
      updateLater();
   }
   @Override
   public void keyTyped(KeyEvent e) {
      updateLater();
   }
   @Override
   public void keyPressed(KeyEvent e) { /* not implemented */ }
   @Override
   public void keyReleased(KeyEvent e) { /* not implemented */}
   private void updateLater() {
      SwingUtilities.invokeLater(new Runnable() {
         @Override
         public void run() {
            resorter.updateNames();
         }
      });
   }
}
