
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

    JPanel optionsPanel,rotatePanel,valuesPanel,cropPanel,shearPanel,scalePanel,translatePanel;
    JComboBox transformsCB, renderCB;
    String[] transformsOptions;
    String[] renderOptions;
    public static JButton identity = new JButton("set to identity");
    public static JTextField rotationValue,x,y,a,b,shearValueX,shearValueY,scaleValueX,scaleValueY,translateValueX,translateValueY;
    public static JButton rotateRight,rotateLeft,cropBT,shearBT,translateBT,scaleBT;

    public Main() {
        build();
    }

    public void build() {
        TransformImage.TransformingCanvas canvas;
        canvas = new TransformImage.TransformingCanvas();
        TransformImage.TranslateHandler translater = new TransformImage.TranslateHandler(canvas);
        BorderLayout bLayout = new BorderLayout(5, 20);
        identity.addMouseListener(translater);
        optionsPanel = new JPanel(new GridLayout(2, 7,20,6));
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
        
        shearValueX = new JTextField(3);
        shearValueY = new JTextField(3);
        shearBT = new JButton("Shear");
        shearBT.addMouseListener(translater);
        shearPanel = new JPanel();
        shearPanel.add(new JLabel("shear values"));
        shearPanel.add(shearValueX);
        shearPanel.add(new JLabel("X"));
        shearPanel.add(shearValueY);
        shearPanel.add(new JLabel("Y"));
        shearPanel.add(shearBT);
        
        scaleValueX = new JTextField(3);
        scaleValueY = new JTextField(3);
        scaleBT = new JButton("Scale");
        scaleBT.addMouseListener(translater);
        scalePanel = new JPanel();
        scalePanel.add(new JLabel("scale values"));
        scalePanel.add(scaleValueX);
        scalePanel.add(new JLabel("X"));
        scalePanel.add(scaleValueY);
        scalePanel.add(new JLabel("Y"));
        scalePanel.add(scaleBT);
        
        translateValueX = new JTextField(3);
        translateValueY = new JTextField(3);
        translateBT = new JButton("Translate");
        translateBT.addMouseListener(translater);
        translatePanel = new JPanel();
        translatePanel.add(new JLabel("translate values"));
        translatePanel.add(translateValueX);
        translatePanel.add(new JLabel("X"));
        translatePanel.add(translateValueY);
        translatePanel.add(new JLabel("Y"));
        translatePanel.add(translateBT);
        
        
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
        optionsPanel.add(scalePanel);
        optionsPanel.add(translatePanel);
        optionsPanel.add(shearPanel);
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
