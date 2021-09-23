import java.awt.*;
import java.awt.event.*;
import javax.swing.event.*;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class Main {
    public static void main(String[] args) {
        Screen myScreen = new Screen();
    }

    
}

class Screen {
    public final static int W = Toolkit.getDefaultToolkit().getScreenSize().width;
    public final static int H = Toolkit.getDefaultToolkit().getScreenSize().height;
    public static int A = 0;
    public static int B = 0;
    public static int C = 0;
    public static int D = 0;
    public static int Resolution = 10;
    public JFrame frame = new JFrame("Graphing Program");
    public JPanel panel = new JPanel();
    public static int mode = 1;

    public Screen() {
        frame.add(panel);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(new EmptyBorder(5,5,5,5));
        DrawPane graphPanel = new DrawPane();
        graphPanel.setBorder(new EmptyBorder(5,5,5,5));
        graphPanel.setPreferredSize(new Dimension(W,H));
        JPanel sliderPanel = new JPanel();
        JPanel runPanel = new JPanel();
        JPanel fourierPanel = new JPanel();
        sliderPanel.setBorder(new EmptyBorder(5,5,5,5));
        sliderPanel.setLayout(new BoxLayout(sliderPanel, BoxLayout.X_AXIS));
        runPanel.setBorder(new EmptyBorder(5,5,5,5));
        runPanel.setLayout(new BoxLayout(runPanel, BoxLayout.X_AXIS));
        fourierPanel.setBorder(new EmptyBorder(5,5,5,5));
        fourierPanel.setLayout(new BoxLayout(fourierPanel, BoxLayout.X_AXIS));
        panel.add(graphPanel);
        panel.add(sliderPanel);
        panel.add(runPanel);
        panel.add(fourierPanel);
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
        TextField functionField = new TextField();
        JButton runButton = new JButton("Function");
        JButton runButtonNC = new JButton("Over Paint");
        TextField function2Field = new TextField();
        JButton fourierButton = new JButton("Fourier");
        JButton fourierButtonNC = new JButton("Over Paint");
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
        runPanel.add(functionField);
        runPanel.add(Box.createRigidArea(new Dimension(5, 0)));
        runPanel.add(runButton);
        runPanel.add(Box.createRigidArea(new Dimension(5, 0)));
        runPanel.add(runButtonNC);
        fourierPanel.add(function2Field);
        fourierPanel.add(Box.createRigidArea(new Dimension(5, 0)));
        fourierPanel.add(fourierButton);
        fourierPanel.add(Box.createRigidArea(new Dimension(5, 0)));
        fourierPanel.add(fourierButtonNC);
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

class DrawPane extends JPanel{
    public ArrayList<Double> points = new ArrayList<Double>();
    public ArrayList<Double> points2 = new ArrayList<Double>();
    public DrawPane() {
        super();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        System.out.println("points size: " + points.size());
        for (int t=1;t<points.size();t++) {
            if (Screen.mode==2) {
                if (t%(Screen.W*Screen.Resolution)==0) {

                } else {
                    g.drawLine(points.get(t-1).intValue(), Screen.H - points2.get(t-1).intValue(), points.get(t).intValue(), Screen.H - points2.get(t).intValue());
                }
            } else {
                g.drawLine((int)((t-1)%Screen.W), Screen.H - points.get(t-1).intValue(), (int)((t)%Screen.W), Screen.H - points.get(t).intValue());
            }
        }
    }

    public void f(String raw) {
        points.clear();
        for (int t=0;t<Screen.W;t++) {
            if(!raw.equals("")) {
                ArrayList<String> args =  slicer(raw);
                //System.out.println("Array: " + args);
                //System.out.println("Slicer 1: " + slicer("1"));
                //System.out.println("Slicer 20: " + slicer("20"));
                points.add(scheme(args, t));    //(+ (* -1 (/ (* t t) 400)) 600) //(+ (/ (* t t) 2333) (* 20 (tan (sin (+ (* 444 t) 5)))))
            } else {
                points.add(0.0);
            }
        }
    }

    public void fNC(String raw) {
        for (int t=0;t<Screen.W;t++) {
            if(!raw.equals("")) {
                ArrayList<String> args =  slicer(raw);
                //System.out.println("Array: " + args);
                //System.out.println("Slicer 1: " + slicer("1"));
                //System.out.println("Slicer 20: " + slicer("20"));
                points.add(scheme(args, t));    //(+ (* -1 (/ (* t t) 400)) 600) //(+ (/ (* t t) 2333) (* 20 (tan (sin (+ (* 444 t) 5)))))
            } else {
                points.add(0.0);
            }
        }
    }

    public void f(String raw, String raw2) {
        points.clear();
        points2.clear();
        for (int t=0;t<Screen.W*Screen.Resolution;t++) {
            if(!raw.equals("")) {
                ArrayList<String> args =  slicer(raw);
                ArrayList<String> args2 =  slicer(raw2);
                // System.out.println("Array: " + args);
                // System.out.println("Slicer a: " + slicer("(* (^ 2 a) t)"));
                // System.out.println("Slicer b: " + slicer("(* (^ 2 a) 3333)"));
                points.add(scheme(args, t));    //(+ (* -1 (/ (* t t) 400)) 600) //(+ (/ (* t t) 2333) (* 20 (tan (sin (+ (* 444 t) 5)))))
                points2.add(scheme(args2, t));
            } else {
                points.add(0.0);
                points2.add(0.0);
            }
        }
    }

    public void fNC(String raw, String raw2) {
        for (int t=0;t<Screen.W*Screen.Resolution;t++) {
            if(!raw.equals("")) {
                ArrayList<String> args =  slicer(raw);
                ArrayList<String> args2 =  slicer(raw2);
                //System.out.println("Array: " + args);
                //System.out.println("Slicer 1: " + slicer("1"));
                //System.out.println("Slicer 20: " + slicer("20"));
                points.add(scheme(args, t));    //(+ (* -1 (/ (* t t) 400)) 600) //(+ (/ (* t t) 2333) (* 20 (tan (sin (+ (* 444 t) 5)))))
                points2.add(scheme(args2, t));
            } else {
                points.add(0.0);    //(+ 600 (* (^ 2 c) (sin (/ t a)) (cos (/ t d))))
                points2.add(0.0);  //(+ 600 (* 30 c (/ 300 t) (cos (/ t a))))
            }
        }
    }

    public ArrayList<String> slicer (String raw) { //(x y z w (r t)) -> {x, y, z, w, (r t)}
        String stripped = "";
        ArrayList<String> out = new ArrayList<String>();
        if (raw.contains(" ")==false) {
            out.add(raw);
            return out;
        }
        for (int i=0;i<raw.length();i++) {
            if(raw.charAt(i)=='(') {
                int counter = 1;
                for (int j=i+1;j<raw.length();j++) {
                    if(raw.charAt(j)=='(') {
                        counter++;
                    } else if (raw.charAt(j)==')') {
                        counter--;
                        if (counter==0) {
                            stripped = raw.substring(i+1, j);
                            break;
                        }
                    }
                }
                break;
            }
        }
        for (int i=0;i<stripped.length();i++) {
            // System.out.println(i + " | " + stripped);
            if (stripped.charAt(i) == ' ' && out.size()==0) {
                // System.out.println("\tFunction Found, now: " + stripped);
                out.add(stripped.substring(0, i));
                stripped = stripped.substring(i+1, stripped.length());
                // System.out.println("\tFunction Removed, now: " + stripped);
                i=-1;
            } else if(stripped.charAt(i)=='(') {
                // System.out.println("\tBrackets Found, now: " + stripped);
                int counter = 1;
                for (int j=i+1;j<stripped.length();j++) {
                    if(stripped.charAt(j)=='(') {
                        counter++;
                    } else if (stripped.charAt(j)==')') {
                        counter--;
                        if (counter==0) {
                            out.add(stripped.substring(i, j+1));
                            if (j+1>=stripped.length()) {
                                stripped = "";
                            } else {
                                stripped = stripped.substring(j+2, stripped.length());
                            }
                            // System.out.println("\tBrackets Removed, now: " + stripped);
                            i=-1;
                            break;
                        }
                    }
                }
            } else if (stripped.charAt(i)!=' ' && out.size()!=0) {
                // System.out.println("\tVariable Found, now: " + stripped);
                if (i+1 == stripped.length()) {
                    out.add(stripped);
                    // System.out.println("\tVariable Removed, now: " + stripped);
                    break;
                }
                for (int y=i+1;y<stripped.length();y++) {
                    if (stripped.charAt(y)==' ') {
                        out.add(stripped.substring(i, y));
                        stripped = stripped.substring(y+1, stripped.length());
                        i=-1;
                        // System.out.println("\tVariable Removed, now: " + stripped);
                        break;
                    }
                }
            }
        }
        return out;
    }

    public double scheme(ArrayList<String> args, double t) {    
        double answer = 0.0;
        for (int k=1;k<args.size();k++) {
        //System.out.println("||| k: " + k + " is: " + args.get(k) + " sliced: " + slicer(args.get(k)) + " eval: " + scheme(slicer(args.get(k)),t));
        }
        if (args.size()==0) {
            return 0.0;
        } else if (args.size() == 1) {
            if (args.get(0).equals("t")) {
                return t;
            } else if (args.get(0).equals("a")) {
                return (double)Screen.A;
            } else if (args.get(0).equals("b")) {
                return (double)Screen.B;
            } else if (args.get(0).equals("c")) {
                return (double)Screen.C;
            } else if (args.get(0).equals("d")) {
                return (double)Screen.D;
            } else {
                return Double.parseDouble(args.get(0));
            }
        }
        switch (args.get(0)) {
            case "+":
                for (int k=1;k<args.size();k++) {
                    answer += scheme(slicer(args.get(k)),t);
                }
                break;
            case "*":
                answer = scheme(slicer(args.get(1)),t);
                for (int k=2;k<args.size();k++) {
                    answer *= scheme(slicer(args.get(k)),t);
                }
                break;
            case "-":
                if (args.size()==2) {
                    answer = -1 * scheme(slicer(args.get(1)),t);
                    break;
                } else {
                    answer = scheme(slicer(args.get(1)),t);
                    for (int k=2;k<args.size();k++) {
                        answer -= scheme(slicer(args.get(k)),t);
                    }
                }
                break;
            case "/":
                if (args.size()==2) {
                    answer = 1 / scheme(slicer(args.get(1)),t);
                    break;
                } else {
                    answer = scheme(slicer(args.get(1)),t);
                    for (int k=2;k<args.size();k++) {
                        answer /= scheme(slicer(args.get(k)),t);
                    }
                }
                break;
            case "cos":
                if (args.size()>2) {
                    System.out.println("ARGUMENT ERROR");
                    return 0.0;
                } else {
                    answer =  Math.cos(scheme(slicer(args.get(1)),t));
                }
                break;
            case "sin":
                if (args.size()>2) {
                    System.out.println("ARGUMENT ERROR");
                    return 0.0;
                } else {
                    answer =  Math.sin(scheme(slicer(args.get(1)),t));
                }
                break;
            case "tan":
                if (args.size()>2) {
                    System.out.println("ARGUMENT ERROR");
                    return 0.0;
                } else {
                    answer =  Math.tan(scheme(slicer(args.get(1)),t));
                }
                break;
            case "^":
                if (args.size()!=3) {
                    System.out.println("ARGUMENT ERROR ^");
                    return 0.0;
                } else {
                    answer = Math.pow(scheme(slicer(args.get(1)),t),scheme(slicer(args.get(2)),t));
                }
                break;
            default:
                break;
        }
        return answer;
    }
}
