
import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/*
 * Classe com o frame principal 
 */
/**
 *
 * @author OsvaldoMaria
 */
public class Main extends JPanel {

    JPanel optionsPanel,rotatePanel,valuesPanel,cropPanel;
    JComboBox transformsCB, renderCB;
    String[] transformsOptions;
    String[] renderOptions;
    JButton identity = new JButton("set to identity");
    public static JTextField rotationValue,x,y,a,b;
    public static JButton rotateRight,rotateLeft,cropBT;

    public Main() {
        build();
    }

    public void build() {
        TransformImage.TransformingCanvas canvas;
        canvas = new TransformImage.TransformingCanvas();
        TransformImage.TranslateHandler translater = new TransformImage.TranslateHandler(canvas);
        BorderLayout bLayout = new BorderLayout(5, 20);
        optionsPanel = new JPanel(new GridLayout(1, 7,20,6));
        rotatePanel= new JPanel();
        cropPanel= new JPanel();
        valuesPanel= new JPanel(new GridLayout(2,2));
        cropBT=new JButton("crop");
        cropBT.addMouseListener(translater);
        x= new JTextField(2);
        y= new JTextField(2);
        a= new JTextField(2);
        b= new JTextField(2);
        valuesPanel.add(x);
        valuesPanel.add(y);
        valuesPanel.add(a);
        valuesPanel.add(b);
        cropPanel.add(valuesPanel);
        cropPanel.add(cropBT);
        
        transformsOptions = new String[]{"rotate", "scale", "shear", "identity"};
        renderOptions = new String[]{"stroke", "fill"};
        transformsCB = new JComboBox(transformsOptions);
        renderCB = new JComboBox(renderOptions);
        rotateLeft=new JButton(new ImageIcon(getClass().getResource("left1.png")));
        rotateRight=new JButton(new ImageIcon(getClass().getResource("right1.png")));

        rotationValue= new JTextField(3);
        rotateRight.addMouseListener(translater);
        rotateLeft.addMouseListener(translater);
        rotatePanel.add(rotateLeft);
        rotatePanel.add(rotateRight);
        rotatePanel.add(new JLabel("rotation value"));
        rotatePanel.add(rotationValue);

        //optionsPanel.add(transformsCB);
        optionsPanel.add(renderCB);
        optionsPanel.add(identity);
        optionsPanel.add(cropPanel);
        optionsPanel.add(rotatePanel);



        canvas.addMouseListener(translater);
        canvas.addMouseMotionListener(translater);
        canvas.addMouseWheelListener(new TransformImage.ScaleHandler(canvas));

        this.setLayout(bLayout);
        this.add(optionsPanel, BorderLayout.NORTH);
        this.add(canvas, BorderLayout.CENTER);
    }

}
