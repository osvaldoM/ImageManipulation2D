/*
 * use this code on your free will
 * OsvaldoM
 * life is meant to be lived
 */

/**
 *
 * @author OsvaldoMaria
 */
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

import javax.swing.JComponent;
import javax.swing.JFrame;

public class AffineTransformTest {

//	private static TransformingCanvas canvas;
//	public static void main(String[] args) {
//		JFrame frame = new JFrame();
//		canvas = new TransformingCanvas();
//		TranslateHandler translater = new TranslateHandler(canvas);
//		canvas.addMouseListener(translater);
//		canvas.addMouseMotionListener(translater);
//		canvas.addMouseWheelListener(new ScaleHandler());
//		frame.setLayout(new BorderLayout());
//		frame.getContentPane().add(canvas, BorderLayout.CENTER);
//		frame.setSize(500, 500);
//		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//		frame.setVisible(true);
//	}
    public static class TransformingCanvas extends JComponent {

        private double translateX;
        private double translateY;
        private double scale;
        private double rotate;
        boolean crop;

        TransformingCanvas() {
            translateX = 0;
            translateY = 0;
            rotate = 0;
            scale = 1;
            crop = false;

            setOpaque(true);
            setDoubleBuffered(true);
        }

        @Override
        public void paint(Graphics g) {
            BufferedImage image = loadImage("eu.jpg");
            AffineTransform tx = new AffineTransform();
            tx.translate(translateX, translateY);
            tx.scale(scale, scale);
            tx.rotate(Math.toRadians(rotate), this.getWidth() / 2, this.getHeight() / 2);

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
            } else {
                BufferedImage croped = cropImage(image, new Rectangle(0, 0, 500, 600));
                ourGraphics.drawImage(croped, tx, null);
                crop=false;
                System.out.println("not first run");
            }
            // super.paint(g);
        }

        private BufferedImage loadImage(String fileName) {
            BufferedImage img = null;
            try {
                img = ImageIO.read(this.getClass().getResource("kid.jpg"));
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
            else if (e.getSource() == Main.rotateLeft) {
                try {
                    int value = Integer.parseInt(Main.rotationValue.getText());
                    canvas.rotate = canvas.rotate - value;
                } catch (Exception ex) {
                    canvas.rotate = canvas.rotate - 30;
                }
                // schedule a repaint.
                canvas.repaint();
            }
            else if(e.getSource()==Main.cropBT){
                canvas.crop=true;
                // schedule a repaint.
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
                canvas.scale += (.1 * e.getWheelRotation());
                // don't cross negative threshold.
                // also, setting scale to 0 has bad effects
                canvas.scale = Math.max(0.00001, canvas.scale);
                canvas.repaint();
            }
        }
    }

}
