
import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author OsvaldoMaria
 */
public class Main extends JPanel {

    JPanel optionsPanel,rotatePanel;
    JComboBox transformsCB, renderCB;
    String[] transformsOptions;
    String[] renderOptions;
    JButton applyBT = new JButton("apply");
    public static JTextField rotationValue;
    public static JButton rotateRight,rotateLeft,cropBT;

    public Main() {
        build();
    }

    public void build() {
        AffineTransformTest.TransformingCanvas canvas;
        canvas = new AffineTransformTest.TransformingCanvas();
        AffineTransformTest.TranslateHandler translater = new AffineTransformTest.TranslateHandler(canvas);
        BorderLayout bLayout = new BorderLayout(5, 20);
        optionsPanel = new JPanel(new GridLayout(1, 5));
        rotatePanel= new JPanel();
        transformsOptions = new String[]{"rotate", "scale", "shear", "identity"};
        renderOptions = new String[]{"stroke", "fill"};
        transformsCB = new JComboBox(transformsOptions);
        renderCB = new JComboBox(renderOptions);
        rotateLeft=new JButton(new ImageIcon(getClass().getResource("left1.png")));
        rotateRight=new JButton(new ImageIcon(getClass().getResource("right1.png")));
        cropBT=new JButton("crop");
        cropBT.addMouseListener(translater);
        rotationValue= new JTextField(3);
        rotateRight.addMouseListener(translater);
        rotateLeft.addMouseListener(translater);
        rotatePanel.add(rotateLeft);
        rotatePanel.add(rotateRight);
        rotatePanel.add(new JLabel("rotation value"));
        rotatePanel.add(rotationValue);

        optionsPanel.add(transformsCB);
        optionsPanel.add(renderCB);
        optionsPanel.add(applyBT);
        optionsPanel.add(cropBT);
        optionsPanel.add(rotatePanel);



        canvas.addMouseListener(translater);
        canvas.addMouseMotionListener(translater);
        canvas.addMouseWheelListener(new AffineTransformTest.ScaleHandler(canvas));

        this.setLayout(bLayout);
        this.add(optionsPanel, BorderLayout.NORTH);
        this.add(canvas, BorderLayout.CENTER);
    }

}
