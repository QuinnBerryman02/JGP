import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.event.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class Screen {
    public final static int W = Toolkit.getDefaultToolkit().getScreenSize().width;
    public final static int H = Toolkit.getDefaultToolkit().getScreenSize().height;
    public static Curve curve;
    public static JFrame frame = new JFrame("Graphing Program");
    public static JPanel panel = new JPanel();
    public static DrawPane graph;
    public static JLabel[] labels = {null, null, null, null};
    public static ArrayList<TextField> fFields = new ArrayList<TextField>();

    public Screen() {
        frame.add(panel);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(new EmptyBorder(5,5,5,5));

        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenu editMenu = new JMenu("Edit");
        JMenu newMenu = new JMenu("New");
        JMenuItem newStandard = new JMenuItem("Standard");
        newStandard.setMnemonic(KeyEvent.VK_S);
        newStandard.setActionCommand("Standard");
        JMenuItem newParametric = new JMenuItem("Parametric");
        newParametric.setMnemonic(KeyEvent.VK_P);
        newParametric.setActionCommand("Parametric");
        JMenuItem importMenuItem = new JMenuItem("Import");
        importMenuItem.setMnemonic(KeyEvent.VK_I);
        importMenuItem.setActionCommand("Import");
        JMenuItem exportMenuItem = new JMenuItem("Export");
        exportMenuItem.setMnemonic(KeyEvent.VK_E);
        exportMenuItem.setActionCommand("Export");
        JMenuItem resetOMenuItem = new JMenuItem("Reset Offset");
        resetOMenuItem.setMnemonic(KeyEvent.VK_O);
        resetOMenuItem.setActionCommand("Reset Offset");
        JMenuItem resetZMenuItem = new JMenuItem("Reset Zoom");
        resetZMenuItem.setMnemonic(KeyEvent.VK_Z);
        resetZMenuItem.setActionCommand("Reset Zoom");
        JMenuItem resetMenuItem = new JMenuItem("Reset");
        resetMenuItem.setMnemonic(KeyEvent.VK_R);
        resetMenuItem.setActionCommand("Reset");
        fileMenu.add(newMenu);
        newMenu.add(newStandard);
        newMenu.add(newParametric);
        fileMenu.add(importMenuItem);
        fileMenu.add(exportMenuItem);
        editMenu.add(resetOMenuItem);
        editMenu.add(resetZMenuItem);
        editMenu.add(resetMenuItem);
        menuBar.add(fileMenu);
        menuBar.add(editMenu);

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

        JPanel renderPanel = new JPanel();
        renderPanel.setBorder(new EmptyBorder(5,5,5,5));
        renderPanel.setLayout(new BoxLayout(renderPanel, BoxLayout.X_AXIS));
        JLabel minLabel = new JLabel("Min");
        JLabel maxLabel = new JLabel("Max");
        JLabel changeLabel = new JLabel("Change");
        TextField minField = new TextField("0");
        TextField maxField = new TextField(Integer.toString(W));
        TextField changeField = new TextField("1");
        renderPanel.add(minLabel);
        renderPanel.add(Box.createRigidArea(new Dimension(5, 0)));
        renderPanel.add(minField);
        renderPanel.add(Box.createRigidArea(new Dimension(5, 0)));
        renderPanel.add(maxLabel);
        renderPanel.add(Box.createRigidArea(new Dimension(5, 0)));
        renderPanel.add(maxField);
        renderPanel.add(Box.createRigidArea(new Dimension(5, 0)));
        renderPanel.add(changeLabel);
        renderPanel.add(Box.createRigidArea(new Dimension(5, 0)));
        renderPanel.add(changeField);
        renderPanel.add(Box.createRigidArea(new Dimension(5, 0)));

        graph = new DrawPane();
        graph.setBorder(new EmptyBorder(5,5,5,5));
        graph.setPreferredSize(new Dimension(W,H));
        
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

        frame.setJMenuBar(menuBar);
        panel.add(labelPanel);
        panel.add(renderPanel);
        panel.add(graph);
        panel.add(sliderPanel);
        panel.add(new FunctionPanel(1,new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                curve.mnF = Double.parseDouble(minField.getText());
                curve.mxF = Double.parseDouble(maxField.getText());
                curve.chF = Double.parseDouble(changeField.getText());
                curve.update();
            }
        }));

        frame.pack();
        frame.setVisible(true);

        aSlider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent event) {
                curve.A = aSlider.getValue();
                curve.update();
            }
        });
        bSlider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent event) {
                curve.B = bSlider.getValue();
                curve.update();
            }
        });
        cSlider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent event) {
                curve.C = cSlider.getValue();
                curve.update();
            }
        });
        dSlider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent event) {
                curve.D = dSlider.getValue();
                curve.update();
            }
        });
        newStandard.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                curve = new StandardCurve();
                panel.remove(panel.getComponentCount()-1);
                FunctionPanel fpanel = new FunctionPanel(1,new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        curve.mnF = Double.parseDouble(minField.getText());
                        curve.mxF = Double.parseDouble(maxField.getText());
                        curve.chF = Double.parseDouble(changeField.getText());
                        curve.update();
                    }
                });
                panel.add(fpanel);
                minField.setText("0");
                maxField.setText("1280");
                changeField.setText("1");
                aSlider.setValue(0);
                bSlider.setValue(0);
                cSlider.setValue(0);
                dSlider.setValue(0);
                labels[0].setText("X offset: " + curve.offsetX);
                labels[1].setText("Y offset: " + curve.offsetY);
                labels[2].setText("X Zoom: " + curve.zoomX);
                labels[3].setText("Y Zoom: " + curve.zoomY);
                frame.pack();
                frame.repaint();
            }
        });
        newParametric.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                curve = new ParametricCurve();
                panel.remove(panel.getComponentCount()-1);
                FunctionPanel fpanel = new FunctionPanel(2,new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        curve.mnF = Double.parseDouble(minField.getText());
                        curve.mxF = Double.parseDouble(maxField.getText());
                        curve.chF = Double.parseDouble(changeField.getText());
                        curve.update();
                    }
                });
                panel.add(fpanel);
                minField.setText("0");
                maxField.setText("12800");
                changeField.setText("1");
                aSlider.setValue(0);
                bSlider.setValue(0);
                cSlider.setValue(0);
                dSlider.setValue(0);
                labels[0].setText("X offset: " + curve.offsetX);
                labels[1].setText("Y offset: " + curve.offsetY);
                labels[2].setText("X Zoom: " + curve.zoomX);
                labels[3].setText("Y Zoom: " + curve.zoomY);
                frame.pack();
                frame.repaint();
            }
        });
        importMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               System.out.println("Import"); 
            }
        });
        exportMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Export"); 
            }
        });
        resetOMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                curve.offsetX=0;
                curve.offsetY=0;
                labels[0].setText("X offset: " + curve.offsetX);
                labels[1].setText("Y offset: " + curve.offsetY);
                curve.update();
            }
        });
        resetZMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                curve.zoomX=1;
                curve.zoomY=1;
                labels[2].setText("X Zoom: " + curve.zoomX);
                labels[3].setText("Y Zoom: " + curve.zoomY);
                curve.update();
            }
        });
        resetMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                curve.offsetX=0;
                curve.offsetY=0;
                curve.zoomX=1;
                curve.zoomY=1;
                labels[0].setText("X offset: " + curve.offsetX);
                labels[1].setText("Y offset: " + curve.offsetY);
                labels[2].setText("X Zoom: " + curve.zoomX);
                labels[3].setText("Y Zoom: " + curve.zoomY);
                curve.update();
            }
        });
        curve = new StandardCurve();
    }

    class FunctionPanel extends JPanel {
        public JButton runButton;
        public FunctionPanel (int n, ActionListener a) {
            super();
            Screen.fFields.clear();
            setBorder(new EmptyBorder(5,5,5,5));
            setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
            JPanel fields = new JPanel();
            fields.setBorder(new EmptyBorder(5,5,5,5));
            fields.setLayout(new BoxLayout(fields, BoxLayout.Y_AXIS));
            for (int i=0;i<n;i++) {
                TextField t = new TextField();
                Screen.fFields.add(t);
                if (i==0) {
                    fields.add(t);
                } else {
                    fields.add(Box.createRigidArea(new Dimension(0, 5)));
                    fields.add(t);
                }
            }
            runButton = new JButton("Run");
            runButton.addActionListener(a);
            add(fields);
            add(Box.createRigidArea(new Dimension(5, 0)));
            add(runButton);
        }
    }
}
