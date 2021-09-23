import java.awt.*;
import java.awt.event.*;
import javax.swing.event.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class Screen {
    public final static int W = Toolkit.getDefaultToolkit().getScreenSize().width;
    public final static int H = Toolkit.getDefaultToolkit().getScreenSize().height;
    public static int A = 0;
    public static int B = 0;
    public static int C = 0;
    public static int D = 0;
    public static int Resolution = 10;
    public static double zoomX = 1;
    public static double zoomY = 1;
    public static int offsetX = 0;
    public static int offsetY = 0;
    public JFrame frame = new JFrame("Graphing Program");
    public JPanel panel = new JPanel();
    public static JLabel[] labels = {null, null, null, null};
    public static TextField fField1;
    public static TextField fField2;
    public static int mode = 1;

    public Screen() {
        frame.add(panel);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(new EmptyBorder(5,5,5,5));

        JPanel labelPanel = new JPanel();
        labelPanel.setBorder(new EmptyBorder(5,5,5,5));
        labelPanel.setLayout(new BoxLayout(labelPanel, BoxLayout.X_AXIS));
        JLabel xoLabel = new JLabel("X offset: 0");
        JLabel yoLabel = new JLabel("Y offset: 0");
        JLabel xzLabel = new JLabel("X Zoom: 1");
        JLabel yzLabel = new JLabel("Y Zoom: 1");
        labels[0] = xoLabel;
        labels[1] = yoLabel;
        labels[2] = xzLabel;
        labels[3] = yzLabel;
        labelPanel.add(xoLabel);
        labelPanel.add(yoLabel);
        labelPanel.add(xzLabel);
        labelPanel.add(yzLabel);

        DrawPane graphPanel = new DrawPane();
        graphPanel.setBorder(new EmptyBorder(5,5,5,5));
        graphPanel.setPreferredSize(new Dimension(W,H));
        
        JPanel sliderPanel = new JPanel();
        sliderPanel.setBorder(new EmptyBorder(5,5,5,5));
        sliderPanel.setLayout(new BoxLayout(sliderPanel, BoxLayout.X_AXIS));
        JLabel aLabel = new JLabel("A");
        JLabel bLabel = new JLabel("B");
        JLabel cLabel = new JLabel("C");
        JLabel dLabel = new JLabel("D");
        JSlider aSlider = new JSlider(-25, 25, 0);
        aSlider.setMinorTickSpacing(1);  
        aSlider.setMajorTickSpacing(5);  
        aSlider.setPaintTicks(true);  
        aSlider.setPaintLabels(true);  
        JSlider bSlider = new JSlider(-25, 25, 0);
        bSlider.setMinorTickSpacing(1);  
        bSlider.setMajorTickSpacing(5);  
        bSlider.setPaintTicks(true);  
        bSlider.setPaintLabels(true);  
        JSlider cSlider = new JSlider(-25, 25, 0);
        cSlider.setMinorTickSpacing(1);  
        cSlider.setMajorTickSpacing(5);  
        cSlider.setPaintTicks(true);  
        cSlider.setPaintLabels(true);  
        JSlider dSlider = new JSlider(-25, 25, 0);
        dSlider.setMinorTickSpacing(1);  
        dSlider.setMajorTickSpacing(5);  
        dSlider.setPaintTicks(true);  
        dSlider.setPaintLabels(true); 
        sliderPanel.add(aLabel);
        sliderPanel.add(Box.createRigidArea(new Dimension(5, 0)));
        sliderPanel.add(aSlider);
        sliderPanel.add(Box.createRigidArea(new Dimension(5, 0)));
        sliderPanel.add(bLabel);
        sliderPanel.add(Box.createRigidArea(new Dimension(5, 0)));
        sliderPanel.add(bSlider);
        sliderPanel.add(Box.createRigidArea(new Dimension(5, 0)));
        sliderPanel.add(cLabel);
        sliderPanel.add(Box.createRigidArea(new Dimension(5, 0)));
        sliderPanel.add(cSlider);
        sliderPanel.add(Box.createRigidArea(new Dimension(5, 0)));
        sliderPanel.add(dLabel);
        sliderPanel.add(Box.createRigidArea(new Dimension(5, 0)));
        sliderPanel.add(dSlider); 
        
        JPanel runPanel = new JPanel();
        runPanel.setBorder(new EmptyBorder(5,5,5,5));
        runPanel.setLayout(new BoxLayout(runPanel, BoxLayout.X_AXIS));
        TextField functionField = new TextField();
        fField1 = functionField;
        JButton runButton = new JButton("Function");
        JButton runButtonNC = new JButton("Over Paint");
        runPanel.add(functionField);
        runPanel.add(Box.createRigidArea(new Dimension(5, 0)));
        runPanel.add(runButton);
        runPanel.add(Box.createRigidArea(new Dimension(5, 0)));
        runPanel.add(runButtonNC);

        JPanel fourierPanel = new JPanel(); 
        fourierPanel.setBorder(new EmptyBorder(5,5,5,5));
        fourierPanel.setLayout(new BoxLayout(fourierPanel, BoxLayout.X_AXIS));
        TextField function2Field = new TextField();
        fField2 = function2Field;
        JButton fourierButton = new JButton("Fourier");
        JButton fourierButtonNC = new JButton("Over Paint");
        fourierPanel.add(function2Field);
        fourierPanel.add(Box.createRigidArea(new Dimension(5, 0)));
        fourierPanel.add(fourierButton);
        fourierPanel.add(Box.createRigidArea(new Dimension(5, 0)));
        fourierPanel.add(fourierButtonNC);

        panel.add(labelPanel);
        panel.add(graphPanel);
        panel.add(sliderPanel);
        panel.add(runPanel);
        panel.add(fourierPanel);

        frame.pack();
        frame.setVisible(true);

        runButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Screen.mode = 1;
                graphPanel.f(functionField.getText());
                graphPanel.repaint();
            }
        });
        runButtonNC.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Screen.mode = 1;
                graphPanel.fNC(functionField.getText());
                graphPanel.repaint();
            }
        });
        fourierButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Screen.mode = 2;
                graphPanel.f(functionField.getText(),function2Field.getText());
                graphPanel.repaint();
            }
        });
        fourierButtonNC.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Screen.mode = 2;
                graphPanel.fNC(functionField.getText(),function2Field.getText());
                graphPanel.repaint();
            }
        });
        aSlider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent event) {
                Screen.A = aSlider.getValue();
                if (Screen.mode==1) {
                    graphPanel.f(functionField.getText());
                    graphPanel.repaint();
                } else {
                    graphPanel.f(functionField.getText(),function2Field.getText());
                    graphPanel.repaint();
                }
            }
        });
        bSlider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent event) {
                Screen.B = bSlider.getValue();
                if (Screen.mode==1) {
                    graphPanel.f(functionField.getText());
                    graphPanel.repaint();
                } else {
                    graphPanel.f(functionField.getText(),function2Field.getText());
                    graphPanel.repaint();
                }
            }
        });
        cSlider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent event) {
                Screen.C = cSlider.getValue();
                if (Screen.mode==1) {
                    graphPanel.f(functionField.getText());
                    graphPanel.repaint();
                } else {
                    graphPanel.f(functionField.getText(),function2Field.getText());
                    graphPanel.repaint();
                }
            }
        });
        dSlider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent event) {
                Screen.D = dSlider.getValue();
                if (Screen.mode==1) {
                    graphPanel.f(functionField.getText());
                    graphPanel.repaint();
                } else {
                    graphPanel.f(functionField.getText(),function2Field.getText());
                    graphPanel.repaint();
                }
            }
        });
    }
}