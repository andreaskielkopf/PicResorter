package de.uhingen.kielkopf.andreas.rechner.mybtn;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;

import javax.swing.ButtonModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
 
public class GlasButton extends JButton {
   private final static float FSIZE    =(50f/30f);
   private static final float F002     =0.02f;
   private static final float F004     =0.04f;
   private static final float F006     =0.06f;
   private static final float F008     =0.08f;
   private static final float F010     =0.1f;
   private static final float F020     =0.2f;
   private final static float F030     =0.3f;
   private final static float F040     =0.4f;
   private final static float F050     =0.5f;
   private final static float F055     =0.55f;
   private final static float F060     =0.6f;
   private final static float F080     =0.8f;
   private static final float F104     =1.04f;
   private final static float F140     =1.4f;
   private static final Color shadow50 =new Color(0, 0, 0, 50);
   private static final Color light50  =new Color(255, 255, 255, 50);
   private static final Color shadow100=new Color(0, 0, 0, 100);
   private static final Color light100 =new Color(255, 255, 255, 100);
   private static final Color shadow70 =new Color(0, 0, 0, 70);
   private static final Point pointNull=new Point(0, 0);
   private static final Color light175 =new Color(255, 255, 255, 175);
   private static final Color light150 =new Color(255, 255, 255, 150);
   private static final Color light0   =new Color(255, 255, 255, 0);
   /**
    * Test GlasButton
    */
   public static void main(String[] args) {
      EventQueue.invokeLater(new Runnable() {
         @Override
         public void run() {
            try {
               JFrame test=new JFrame();
               test.setBounds(100, 100, 600, 450);
               test.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
               JPanel panel=new JPanel();
               panel.setLayout(new FlowLayout());
               panel.add(new GlasButton("Hallo"));
               panel.add(new GlasButton("Fabian"));
               panel.add(new GlasButton(","));
               panel.add(new GlasButton("das"));
               panel.add(new GlasButton("sind"));
               panel.add(new GlasButton("selbst"));
               panel.add(new GlasButton("gezeichnete"));
               panel.add(new GlasButton("Buttons"));
               panel.add(new GlasButton("0"));
               panel.add(new GlasButton("1"));
               panel.add(new GlasButton("10"));
               GlasButton elf=new GlasButton("11");
               elf.setEnabled(false);
               panel.add(elf);
               panel.add(new GlasButton("100"));
               panel.add(new GlasButton("101"));
               panel.add(new GlasButton("110"));
               panel.add(new GlasButton("111"));
               panel.add(new GlasButton("1000"));
               panel.add(new GlasButton("100€"));
               test.getContentPane().add(panel);
               test.setVisible(true);
            } catch (Exception e) {
               e.printStackTrace();
            }
         }
      });
   }
   private static final long serialVersionUID=-84246193169543030L;
   private float             a               =1f/5;
   private float             b               =1f/16;
   private float             c               =1f-4*a-4*b;
   private ConvolveOp        kernelOp        =new ConvolveOp(new Kernel(3, 3, new float[] {a, b, a, b, c, b, a, b, a}));
   private BufferedImage     buttonImage;
   public GlasButton() {
      super();
      Font f=getFont();
      setFont(f.deriveFont(f.getSize2D()*2.1f));
      setForeground(Color.BLUE);
      setBackground(Color.YELLOW);
      initialize();
   }
   public GlasButton(String string) {
      this();
      setText(string);
   }
   private void initialize() {
      setText("Hallo");
   }
   @Override
   public Dimension getPreferredSize() {
      String      text =getText();
      FontMetrics fm   =this.getFontMetrics(getFont());
      float       scale=FSIZE*this.getFont().getSize2D();
      int         w    =fm.stringWidth(text);
      w+=(int) (scale*F140);
      int h=fm.getHeight();
      h+=(int) (scale*F030);
      return new Dimension(w, h);
   }
   @Override
   protected void paintComponent(Graphics g) {
      Graphics2D g2      =(Graphics2D) g;
      float      scale   =FSIZE*getFont().getSize2D();
      boolean    disabled=!isEnabled();
      Graphics2D g2d     =g2;
      if (disabled) {
         if ((buttonImage==null)||(buttonImage.getWidth()!=getWidth())||(buttonImage.getHeight()!=getHeight()))
            buttonImage=(BufferedImage) createImage(getWidth(), getHeight());
         g2d=(Graphics2D) buttonImage.getGraphics();
         g2d.setFont(getFont());
      }
      g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
      if (isOpaque())
         g2d.fillRect(0, 0, getWidth(), getHeight());
      drawGlasButton(getWidth(), getHeight(), getText(), scale, g2d);
      if (disabled)
         g2.drawImage(buttonImage, kernelOp, 0, 0);
   }
   private void drawGlasButton(int width, int height, String text, float scale, Graphics2D g2d) {
      // calculate insets
      int         scale002    =(int) (scale*F002);
      float       scale004    =scale*F004;
      int         scale006    =(int) (scale*F006);
      int         scale008    =(int) (scale*F008);
      int         scale010    =(int) (scale*F010);
      float       scale020    =scale*F020;
      int         scale040    =(int) (scale*F040);
      int         scale050    =(int) (scale*F050);
      float       scale055    =scale*F055;
      int         scale060    =(int) (scale*F060);
      int         scale080    =(int) (scale*F080);
      int         scale100    =(int) scale;
      int         scale104    =(int) (scale*F104);
      float       insetF      =scale004;
      int         inset       =(int) scale004;
      int         w           =width-inset*2-1;
      int         h           =height-scale010-1;
      ButtonModel model1      =getModel();
      Color       basisFarbe  =getBackground();
      Color       schriftFarbe=getForeground();
      if (!model1.isEnabled()) {
         int gray1=(basisFarbe.getRed()+basisFarbe.getGreen()+basisFarbe.getBlue())/4;
         int gray2=(schriftFarbe.getRed()+schriftFarbe.getGreen()+schriftFarbe.getBlue())/4;
         basisFarbe=new Color(gray1, gray1, gray1);
         schriftFarbe=new Color(gray2, gray2, gray2);
      }
      Color top   =basisFarbe.brighter();
      Color bottom=basisFarbe.darker();
      Color inner =new Color(top.getRed(), top.getGreen(), top.getBlue(), 75);
      g2d.translate(inset, 0);
      // DropShadow
      g2d.setColor(model1.isRollover() ? light50 : shadow50);
      g2d.fillRoundRect((int) -scale004, scale002, w+scale008, h+scale008, scale104, scale104);
      g2d.setColor(model1.isRollover() ? light100 : shadow100);
      g2d.fillRoundRect(0, scale006, w, h, scale100, scale100);
      if (model1.isPressed())
         g2d.translate(0, insetF);
      // Button Body
      g2d.setPaint(new GradientPaint(pointNull, top, new Point(0, h), bottom));
      g2d.fillRoundRect(0, 0, w, h, scale100, scale100);// outer Color
      // int t =(top.getRGB()&0xffffff)+(75*0x1000000);
      // Color inner=new Color(t);
      g2d.setColor(inner);
      g2d.fillRoundRect(scale040, scale040, w-scale080, h-scale050, scale060, scale040);
      // Text
      FontMetrics fm   =g2d.getFontMetrics();
      float       textx=(w-fm.stringWidth(text))*F050;
      float       texty=(h+fm.getAscent()-fm.getDescent())*F050;
      g2d.setColor(shadow70);
      g2d.drawString(text, textx+scale004, texty+scale004);
      g2d.setColor(schriftFarbe);
      g2d.drawString(text, textx, texty);
      // Highlight
      g2d.setPaint(new GradientPaint(//
               new Point2D.Float(scale020, scale020), //
               light175,
               // (model.isEnabled() ? light175 : light50), //
               new Point2D.Float(scale020, scale055), light0));
      g2d.fillRoundRect((int) scale020, scale010, w-scale040, scale040, scale080, scale040);
      g2d.drawRoundRect((int) scale020, scale010, w-scale040, scale040, scale080, scale040);
      // Border
      g2d.setColor(light150);
      g2d.drawRoundRect(0, 0, w, h, scale100, scale100);
      if (model1.isPressed())
         g2d.translate(0, -insetF);
      g2d.translate(-inset, 0);
   }
}
