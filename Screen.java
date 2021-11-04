import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.io.*;

import javax.swing.event.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class Screen {
    public final static int W = Toolkit.getDefaultToolkit().getScreenSize().width;
    public final static int H = Toolkit.getDefaultToolkit().getScreenSize().height;
    public static ArrayList<Curve> curves = new ArrayList<Curve>();
    public static Curve curve;
    public static Curve curve2;

    public static JFrame frame = new JFrame("Graphing Program");
    public static JPanel panel = new JPanel();
    public static LabelPanel labelPanel;
    public static RenderPanel renderPanel;
    public static DrawPane graph;
    public static SliderPanel sliderPanel;
    public static FunctionPanel functionPanel;

    public static ArrayList<TextField> fFields = new ArrayList<TextField>();
    public static String fileName = "save.txt";

    public Screen() {
        frame.add(panel);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(new EmptyBorder(5,5,5,5));

        JMenuBar menuBar = new JMenuBar();
        JMenu curveMenu = new JMenu("Curve");
        JMenu moveMenu = new JMenu("Move");
        JMenu colorMenu = new JMenu("Color");
        JMenu newMenu = new JMenu("New");
        JMenuItem currCurve = new JMenuItem("Current");
        currCurve.setMnemonic(KeyEvent.VK_C);
        currCurve.setActionCommand("Current");
        JMenuItem newStandard = new JMenuItem("Standard");
        newStandard.setMnemonic(KeyEvent.VK_S);
        newStandard.setActionCommand("Standard");
        JMenuItem newParametric = new JMenuItem("Parametric");
        newParametric.setMnemonic(KeyEvent.VK_P);
        newParametric.setActionCommand("Parametric");
        JMenuItem newStandardPolar = new JMenuItem("Standard Polar");
        newStandardPolar.setMnemonic(KeyEvent.VK_X);
        newStandardPolar.setActionCommand("Standard Polar");
        JMenuItem newParametricPolar = new JMenuItem("Parametic Polar");
        newParametricPolar.setMnemonic(KeyEvent.VK_Z);
        newParametricPolar.setActionCommand("Parametic Polar");
        JMenuItem importMenuItem = new JMenuItem("Import");
        importMenuItem.setMnemonic(KeyEvent.VK_I);
        importMenuItem.setActionCommand("Import");
        JMenuItem exportMenuItem = new JMenuItem("Export");
        exportMenuItem.setMnemonic(KeyEvent.VK_E);
        exportMenuItem.setActionCommand("Export");
        JMenuItem currentColorMenuItem = new JMenuItem("Current");
        currentColorMenuItem.setMnemonic(KeyEvent.VK_C);
        currentColorMenuItem.setActionCommand("Current");
        JMenuItem newColorMenuItem = new JMenuItem("New");
        newColorMenuItem.setMnemonic(KeyEvent.VK_N);
        newColorMenuItem.setActionCommand("New");
        JMenuItem importColorMenuItem = new JMenuItem("Import");
        importColorMenuItem.setMnemonic(KeyEvent.VK_I);
        importColorMenuItem.setActionCommand("Import");
        JMenuItem exportColorMenuItem = new JMenuItem("Export");
        exportColorMenuItem.setMnemonic(KeyEvent.VK_E);
        exportColorMenuItem.setActionCommand("Export");
        JMenuItem resetOMenuItem = new JMenuItem("Reset Offset");
        resetOMenuItem.setMnemonic(KeyEvent.VK_O);
        resetOMenuItem.setActionCommand("Reset Offset");
        JMenuItem resetZMenuItem = new JMenuItem("Reset Zoom");
        resetZMenuItem.setMnemonic(KeyEvent.VK_Z);
        resetZMenuItem.setActionCommand("Reset Zoom");
        JMenuItem resetMenuItem = new JMenuItem("Reset");
        resetMenuItem.setMnemonic(KeyEvent.VK_R);
        resetMenuItem.setActionCommand("Reset");
        curveMenu.add(currCurve);
        curveMenu.add(newMenu);
        newMenu.add(newStandard);
        newMenu.add(newParametric);
        newMenu.add(newStandardPolar);
        newMenu.add(newParametricPolar);
        curveMenu.add(importMenuItem);
        curveMenu.add(exportMenuItem);
        moveMenu.add(resetOMenuItem);
        moveMenu.add(resetZMenuItem);
        moveMenu.add(resetMenuItem);
        colorMenu.add(currentColorMenuItem);
        colorMenu.add(newColorMenuItem);
        colorMenu.add(importColorMenuItem);
        colorMenu.add(exportColorMenuItem);
        menuBar.add(curveMenu);
        menuBar.add(moveMenu);
        menuBar.add(colorMenu);

        labelPanel = new LabelPanel();
        renderPanel = new RenderPanel();
        graph = new DrawPane();
        sliderPanel = new SliderPanel(7);
        functionPanel = new FunctionPanel(1,new onChange());

        frame.setJMenuBar(menuBar);
        panel.add(labelPanel);
        panel.add(renderPanel);
        panel.add(graph);
        panel.add(sliderPanel);
        panel.add(functionPanel);

        frame.pack();
        frame.setVisible(true);

        currCurve.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Shader tempShader = curve.getShader();
                if (curve instanceof ColorWheel) {
                    curve = curve2;
                    curve.setShader(tempShader);
                } else {
                    return;
                }

                panel.remove(panel.getComponentCount()-1);
                functionPanel = new FunctionPanel(curve.getFuncs(),new onChange());
                panel.add(functionPanel);

                functionPanel.updateFields(curve);
                System.out.println(curve);
                renderPanel.updateFields(curve);
                sliderPanel.updateSliders(curve);
                labelPanel.sync(curve);
                
                frame.pack();
                frame.repaint();
                graph.repaint();
                System.out.println(curve);
            }
        });
        newStandard.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                curve2 = curve;
                curve = new StandardCurve();
                curve.setShader(curve2.getShader());
                curve.initConsts(curve2.getConsts().size());

                panel.remove(panel.getComponentCount()-1);
                functionPanel = new FunctionPanel(1,new onChange());
                panel.add(functionPanel);

                renderPanel.sync(curve,StandardCurve.DEFAULT);
                sliderPanel.updateSliders(curve2);
                labelPanel.sync(curve);

                frame.pack();
                frame.repaint();
                graph.repaint();
                System.out.println(curve);
            }
        });
        newParametric.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                curve2 = curve;
                curve = new ParametricCurve();
                curve.setShader(curve2.getShader());
                curve.initConsts(curve2.getConsts().size());

                panel.remove(panel.getComponentCount()-1);
                functionPanel = new FunctionPanel(2,new onChange());
                panel.add(functionPanel);

                renderPanel.sync(curve,ParametricCurve.DEFAULT);
                sliderPanel.updateSliders(curve2);
                labelPanel.sync(curve);

                frame.pack();
                frame.repaint();
                graph.repaint();
                System.out.println(curve);
            }
        });
        newStandardPolar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                curve2 = curve;
                curve = new StandardPolarCurve();
                curve.setShader(curve2.getShader());
                curve.initConsts(curve2.getConsts().size());

                panel.remove(panel.getComponentCount()-1);
                functionPanel = new FunctionPanel(1,new onChange());
                panel.add(functionPanel);

                renderPanel.sync(curve,StandardPolarCurve.DEFAULT);
                sliderPanel.updateSliders(curve2);
                labelPanel.sync(curve);

                frame.pack();
                frame.repaint();
                graph.repaint();
                System.out.println(curve);
            }
        });
        newParametricPolar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                curve2 = curve;
                curve = new ParametricPolarCurve();
                curve.setShader(curve2.getShader());
                curve.initConsts(curve2.getConsts().size());

                panel.remove(panel.getComponentCount()-1);
                functionPanel = new FunctionPanel(2,new onChange());
                panel.add(functionPanel);

                renderPanel.sync(curve,ParametricPolarCurve.DEFAULT);
                sliderPanel.updateSliders(curve2);
                labelPanel.sync(curve);

                frame.pack();
                frame.repaint();
                graph.repaint();
                System.out.println(curve);
            }
        });
        importMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               System.out.println("Import"); 
               System.out.println(curve);
            }
        });
        exportMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Export"); 
                if (curve instanceof ColorWheel) {
                    return;
                }
                try {
                    FileWriter fw = new FileWriter(fileName,false);
                    PrintWriter pw = new PrintWriter(fw);
                    boolean found = false;
                    for (Curve c : curves) {
                        pw.println(c.toString());
                        if (c.getNumber() == curve.getNumber()) {
                            found = true;
                        }
                    }
                    if (found) {
                        pw.close();
                        return;
                    }
                    //add a new curve
                    curves.add(curve);
                    pw.println(curve.toString());
                    pw.close();
                    
                } catch (IOException f) {
                    System.out.println(f);
                }

            }
        });
        resetOMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                curve.setOffset(0, 0);
                labelPanel.sync(curve);
                curve.update();
                graph.repaint();
            }
        });
        resetZMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                curve.setZoom(1, 1);
                labelPanel.sync(curve);
                curve.update();
                graph.repaint();
            }
        });
        resetMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                curve.setOffset(0, 0);
                curve.setZoom(1, 1);
                labelPanel.sync(curve);
                curve.update();
                graph.repaint();
            }
        });
        currentColorMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (curve instanceof ColorWheel) {
                    return;
                }
                curve2 = curve;
                curve = new ColorWheel();
                curve.setShader(curve2.getShader());

                panel.remove(panel.getComponentCount()-1);
                functionPanel = new FunctionPanel(4,new onChange());
                panel.add(functionPanel);

                functionPanel.updateFields(curve);
                renderPanel.sync(curve, ColorWheel.DEFAULT);
                sliderPanel.updateSliders(curve2);
                labelPanel.sync(curve);

                frame.pack();
                frame.repaint();
                curve.update();
                graph.repaint();
                System.out.println(curve);
            }
        });
        newColorMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!(curve instanceof ColorWheel)) {
                    curve2 = curve;
                }
                curve = new ColorWheel();

                panel.remove(panel.getComponentCount()-1);
                functionPanel = new FunctionPanel(4,new onChange());
                panel.add(functionPanel);

                renderPanel.sync(curve, ColorWheel.DEFAULT);
                functionPanel.sync(curve, ColorWheel.DEFAULT_RAW);
                sliderPanel.updateSliders(curve2);
                labelPanel.sync(curve);

                frame.pack();
                frame.repaint();
                curve.update();
                graph.repaint();
                System.out.println(curve);
            }
        });
        importColorMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               System.out.println("Import"); 
            }
        });
        exportColorMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Export"); 
            }
        });
        curve = new StandardCurve();
        sliderPanel.updateCurve(curve);
        renderPanel.sync(curve, StandardCurve.DEFAULT);;
    }

    class LabelPanel extends JPanel {
        private ArrayList<JLabel> labels = new ArrayList<JLabel>();

        public LabelPanel() {
            setBorder(new EmptyBorder(5,5,5,5));
            setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
            JLabel xoLabel = new JLabel("X offset: 0");
            JLabel yoLabel = new JLabel("Y offset: 0");
            JLabel xzLabel = new JLabel("X Zoom: 1");
            JLabel yzLabel = new JLabel("Y Zoom: 1");
            labels.add(xoLabel);
            labels.add(yoLabel);
            labels.add(xzLabel);
            labels.add(yzLabel);
            add(xoLabel);
            add(yoLabel);
            add(xzLabel);
            add(yzLabel);
        }

        public void sync(Curve c) {
            labels.get(0).setText("X offset: " + c.getOffsetX());
            labels.get(1).setText("Y offset: " + c.getOffsetY());
            labels.get(2).setText("X Zoom: " + c.getZoomX());
            labels.get(3).setText("Y Zoom: " + c.getZoomY());
        }
    }

    class RenderPanel extends JPanel {
        private ArrayList<JLabel> labels = new ArrayList<JLabel>();
        private ArrayList<TextField> fields = new ArrayList<TextField>();

        public RenderPanel() {
            setBorder(new EmptyBorder(5,5,5,5));
            setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
            labels.add(new JLabel("Min"));
            labels.add(new JLabel("Max"));
            labels.add(new JLabel("Change"));
            fields.add(new TextField("0"));
            fields.add(new TextField(String.valueOf(W)));
            fields.add(new TextField("1"));
            for (int i=0;i<3;i++) {
                add(labels.get(i));
                add(Box.createRigidArea(new Dimension(5, 0)));
                add(fields.get(i));
                if(i!=2) {
                    add(Box.createRigidArea(new Dimension(5, 0)));
                }
            }
        }

        public void updateFields(Curve c) {
            fields.get(0).setText(String.valueOf(c.getMNF()));
            fields.get(1).setText(String.valueOf(c.getMXF()));
            fields.get(2).setText(String.valueOf(c.getCHF()));
        }

        public void updateCurve(Curve c) {
            double mnF = Double.parseDouble(fields.get(0).getText());
            double mxF = Double.parseDouble(fields.get(1).getText());
            double chF = Double.parseDouble(fields.get(2).getText());
            if(chF == 0) {
                chF = 1;
            }
            c.setFields(mnF, mxF, chF);
        }

        public void sync(Curve c, ArrayList<Double> d) {
            c.setFields(d.get(0), d.get(1), d.get(2));
            updateFields(c);
        }
    }

    class SliderPanel extends JPanel {
        private int min = -25;
        private int max = 25;
        private int change = 1;
        private int minor = 1;
        private int major = 5;
        private int perRow = 4;
        private ArrayList<JLabel> labels = new ArrayList<JLabel>();
        private ArrayList<JSlider> sliders = new ArrayList<JSlider>();
        private ArrayList<JPanel> rows = new ArrayList<JPanel>();

        public SliderPanel(int amount) {
            setBorder(new EmptyBorder(5,5,5,5));
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            for (int i=0;i<amount;i++) {
                char letter = (char)(65 + i);
                JLabel label = new JLabel(String.valueOf(letter));
                JSlider slider = new JSlider(min,max,change);
                slider.setMinorTickSpacing(minor);  
                slider.setMajorTickSpacing(major);  
                slider.setPaintTicks(true);  
                slider.setPaintLabels(true);  
                labels.add(label);
                sliders.add(slider);
                if(i % perRow == 0) {
                    JPanel row = new JPanel();
                    row.setBorder(new EmptyBorder(5,5,5,5));
                    row.setLayout(new BoxLayout(row, BoxLayout.X_AXIS));
                    rows.add(row);
                    add(row);
                }
                rows.get(rows.size()-1).add(label);
                rows.get(rows.size()-1).add(Box.createRigidArea(new Dimension(5, 0)));
                rows.get(rows.size()-1).add(slider);
                if(i % 4 != 3) {
                    rows.get(rows.size()-1).add(Box.createRigidArea(new Dimension(5, 0)));
                }
                final int j = i;
                slider.addChangeListener(new ChangeListener() {
                    public void stateChanged(ChangeEvent event) {
                        curve.setConsts(j,sliders.get(j).getValue());
                        curve.update();
                        graph.repaint();
                    }
                });

            }
        }

        public void updateSliders(Curve c) {
            int i = 0;
            for (JSlider s : sliders) {
                if(c == null) {
                    s.setValue(1);
                } else {
                    s.setValue(c.getConsts(i));
                }
                i++;
            }
        }

        public void updateCurve(Curve c) {
            c.initConsts(sliders.size());
            int i = 0;
            for (JSlider s : sliders) {
                c.setConsts(i, s.getValue());
                i++;
            }
        }

        public void sync(Curve c, ArrayList<Integer> values) {
            c.setConsts(values);
            updateSliders(c);
        }
    }

    class FunctionPanel extends JPanel {
        private JButton runButton;
        private ArrayList<TextField> fields = new ArrayList<TextField>();

        public FunctionPanel (int n, ActionListener a) {
            super();
            setBorder(new EmptyBorder(5,5,5,5));
            setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
            JPanel fieldPanel = new JPanel();
            fieldPanel.setBorder(new EmptyBorder(5,5,5,5));
            fieldPanel.setLayout(new BoxLayout(fieldPanel, BoxLayout.Y_AXIS));
            for (int i=0;i<n;i++) {
                TextField t = new TextField();
                fields.add(t);
                if (i==0) {
                    fieldPanel.add(t);
                } else {
                    fieldPanel.add(Box.createRigidArea(new Dimension(0, 5)));
                    fieldPanel.add(t);
                }
            }
            runButton = new JButton("Run");
            runButton.addActionListener(a);
            add(fieldPanel);
            add(Box.createRigidArea(new Dimension(5, 0)));
            add(runButton);
        }

        public void updateFields(Curve c) {
            for (int i=0;i<fields.size();i++) {
                fields.get(i).setText(c.getRaws().get(i));
            }
        }

        public void updateCurve(Curve c) {
            ArrayList<String> temp = new ArrayList<String>();
            for (int i=0;i<fields.size();i++) {
                temp.add(fields.get(i).getText());
            }
            c.setRaws(temp);
        }

        public void sync(Curve c ,ArrayList<String> raws) {
            c.setRaws(raws);
            updateFields(c);
        }
    }

    class onChange implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            renderPanel.updateCurve(curve);
            functionPanel.updateCurve(curve);
            curve.update();
            graph.repaint();
        }
    }
}
