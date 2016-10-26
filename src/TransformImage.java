/*
 * use this code on your free will
 * OsvaldoM
 * life is meant to be lived


 *CLASSE com o painel que se transforma
 */

/**
 *
 * @author OsvaldoMaria
 */
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileNameExtensionFilter;

public class TransformImage {

    public static class TransformingCanvas extends JComponent {

        private double translateX;
        private double translateY;
        private double scaleX,scaleY,shearX,shearY;
        private double rotate;
        boolean crop,setIdentity;
        Rectangle rectangle;
        BufferedImage image;
        String path="";
        AffineTransform tx = new AffineTransform();

        TransformingCanvas() {
            translateX = 0;
            translateY = 0;
            rotate = 0;
            scaleX = 1;
            scaleY = 1;
            shearX = 0;
            shearY = 0;
            
            crop = false;
            rectangle= new Rectangle(0,0,30,30);
            setOpaque(true);
            setDoubleBuffered(true);
        }

        @Override
        public void paint(Graphics g) {
            if(path.equals("")){
             image = loadImage("kid.jpg");
            }else{
            image = loadImage(path);
            }
            AffineTransform tx = new AffineTransform();
            tx.translate(translateX, translateY);
            tx.scale(scaleX, scaleY);
            tx.rotate(Math.toRadians(rotate), this.getWidth() / 2, this.getHeight() / 2);
            tx.shear(shearX, shearY);

            Graphics2D ourGraphics = (Graphics2D) g;
            ourGraphics.setColor(Color.WHITE);
            ourGraphics.fillRect(0, 0, getWidth(), getHeight());
            ourGraphics.setTransform(tx);
            ourGraphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
            ourGraphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                    RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            ourGraphics.setColor(Color.BLACK);
            ourGraphics.drawString("Imagem a transformar", 50, 30);
            if (!crop) {
                ourGraphics.drawImage(image, tx, null);
                System.out.println("first run");
            }
//            else if(setIdentity==true){
//                System.out.println("identity"+setIdentity);
//               tx.setToIdentity();
//               tx.translate(getWidth()/2, getHeight()/2);
//               ourGraphics.drawImage(image, tx, null);
//               setIdentity=false;
//            }
            else {
                BufferedImage croped = cropImage(image,rectangle);
                image=croped;
                ourGraphics.drawImage(croped, tx, null);
                crop=false;
                System.out.println("not first run");
            }
            // super.paint(g);
        }

        private BufferedImage loadImage(String fileName) {
            BufferedImage img = null;
            try {
                img = ImageIO.read(new File(fileName));
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
            return img;
        }

        private BufferedImage cropImage(BufferedImage src, Rectangle rect) {
            BufferedImage image = src.getSubimage(0, 0, rect.width, rect.height);
            BufferedImage copyOfImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
            Graphics g = copyOfImage.createGraphics();
            g.drawImage(image, 0, 0, null);
            return copyOfImage;
        }
    }

    public static class TranslateHandler implements MouseListener,
            MouseMotionListener {

        private int lastOffsetX;
        private int lastOffsetY;
        TransformingCanvas canvas;

        public TranslateHandler(TransformingCanvas tc) {
            this.canvas = tc;
        }

        public void mousePressed(MouseEvent e) {
            // capture starting point
            lastOffsetX = e.getX();
            lastOffsetY = e.getY();
        }

        public void mouseDragged(MouseEvent e) {

            // new x and y are defined by current mouse location subtracted
            // by previously processed mouse location
            int newX = e.getX() - lastOffsetX;
            int newY = e.getY() - lastOffsetY;

            // increment last offset to last processed by drag event.
            lastOffsetX += newX;
            lastOffsetY += newY;

            // update the canvas locations
            canvas.translateX += newX;
            canvas.translateY += newY;

            // schedule a repaint.
            canvas.repaint();
        }

        public void mouseClicked(MouseEvent e) {
            if (e.getSource() == Main.rotateRight) {
                try {
                    int value = Integer.parseInt(Main.rotationValue.getText());
                    canvas.rotate = canvas.rotate + value;
                } catch (Exception ex) {
                    canvas.rotate = canvas.rotate + 30;
                }
                // schedule a repaint.
                canvas.repaint();
            }
            if (e.getSource() == Main.rotateLeft) {
                try {
                    int value = Integer.parseInt(Main.rotationValue.getText());
                    canvas.rotate = canvas.rotate - value;
                } catch (Exception ex) {
                    canvas.rotate = canvas.rotate - 30;
                }
                // schedule a repaint.
                canvas.repaint();
            }
            if(e.getSource()==Main.cropBT){
                canvas.crop=true;
                int x=  Integer.parseInt(Main.x.getText());
                int y=  Integer.parseInt(Main.y.getText());
                int a=  Integer.parseInt(Main.a.getText());
                int b=  Integer.parseInt(Main.b.getText());
                canvas.rectangle= new Rectangle(x,y,a,b);
                // schedule a repaint.
                canvas.repaint();
            }
//            else if(e.getSource()==Main.identity){
//                System.out.println("identity clickd");
//                canvas.setIdentity=true;
//                // schedule a repaint.
//                canvas.repaint();
//            }
    if(e.getSource()==Main.scaleBT){
                try{
                    Double valueX = Double.parseDouble(Main.scaleValueX.getText());
                    Double valueY = Double.parseDouble(Main.scaleValueY.getText());
                    canvas.scaleX+=valueX;
                    canvas.scaleY+=valueY;
                }catch(Exception ex){
                    canvas.scaleX+=0.5;
                    canvas.scaleY+=0.5;
                }
                canvas.repaint();
            }
            if(e.getSource()==Main.shearBT){
                try{
                    Double valueX = Double.parseDouble(Main.shearValueX.getText());
                    Double valueY = Double.parseDouble(Main.shearValueY.getText());
                    canvas.shearX+=valueX;
                    canvas.shearY+=valueY;
                }catch(Exception ex){
                    canvas.shearX+=0.5;
                    canvas.shearY+=0.5;
                }
                canvas.repaint();
            }
            if(e.getSource()==Main.translateBT){
                try{
                    Double valueX = Double.parseDouble(Main.translateValueX.getText());
                    Double valueY = Double.parseDouble(Main.translateValueY.getText());
                    canvas.translateX+=valueX;
                    canvas.translateY+=valueY;
                }catch(Exception ex){
                    canvas.translateX+=0.5;
                    canvas.translateY+=0.5;
                }
                canvas.repaint();
            }
            if(e.getSource()==Main.shearBT){
                try{
                    Double valueX = Double.parseDouble(Main.shearValueX.getText());
                    Double valueY = Double.parseDouble(Main.shearValueY.getText());
                    canvas.shearX+=valueX;
                    canvas.shearY+=valueY;
                }catch(Exception ex){
                    canvas.shearX+=1;
                    canvas.shearY+=1;
                }
                canvas.repaint();
                
            }
            if(e.getSource()==Main.identity){
                canvas.tx.setToIdentity();
                canvas.tx.scale(100, 1);
                canvas.repaint();
                System.out.println("im here");
            }
            if(e.getSource()==Main.openFile){
                JFileChooser fileChooser = new JFileChooser();
FileNameExtensionFilter extFilter = new FileNameExtensionFilter(
        "Image File", "jpg", "png");
        fileChooser.setAcceptAllFileFilterUsed(false);
    // Set the file filter
    fileChooser.addChoosableFileFilter(extFilter);

    int returnValue = fileChooser.showOpenDialog(null);

    if (returnValue == JFileChooser.APPROVE_OPTION) {
      File selectedFile = fileChooser.getSelectedFile();
      canvas.path=selectedFile.toString();
    }
    canvas.repaint();
            }
            
        }

        public void mouseEntered(MouseEvent e) {
        }

        public void mouseExited(MouseEvent e) {
        }

        public void mouseMoved(MouseEvent e) {
        }

        public void mouseReleased(MouseEvent e) {
        }
    }

    public static class ScaleHandler implements MouseWheelListener {

        TransformingCanvas canvas;

        public ScaleHandler() {

        }

        public ScaleHandler(TransformingCanvas tc) {
            this.canvas = tc;
        }

        public void mouseWheelMoved(MouseWheelEvent e) {
            if (e.getScrollType() == MouseWheelEvent.WHEEL_UNIT_SCROLL) {

                // make it a reasonable amount of zoom
                // .1 gives a nice slow transition
                canvas.scaleY=canvas.scaleX += (.1 * e.getWheelRotation());
                // don't cross negative threshold.
                // also, setting scaleX to 0 has bad effects
                canvas.scaleY=canvas.scaleX = Math.max(0.00001, canvas.scaleX);
                canvas.repaint();
            }
        }
    }

}
