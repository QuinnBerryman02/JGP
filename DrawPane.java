import java.awt.*;
import java.util.ArrayList;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.*;

public class DrawPane extends JPanel implements MouseListener{
    public ArrayList<Double> points = new ArrayList<Double>();
    public ArrayList<Double> points2 = new ArrayList<Double>();
    public static int ox = 0;
    public static int oy = 0;
    public DrawPane() {
        super();
        addMouseListener(this);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        switch (e.getButton()) {
            case 1:
                Screen.zoomX *= 1.5;
                Screen.zoomY *= 1.5;
                Screen.labels[2].setText("X Zoom: " + Screen.zoomX);
                Screen.labels[3].setText("Y Zoom: " + Screen.zoomY);
                if (Screen.mode==1) {
                    f(Screen.fField1.getText());
                    repaint();
                } else {
                    f(Screen.fField1.getText(),Screen.fField2.getText());
                    repaint();
                }
                break;
            case 2:
                break;
            case 3:
                Screen.zoomX /= 1.5;
                Screen.zoomY /= 1.5;
                Screen.labels[2].setText("X Zoom: " + Screen.zoomX);
                Screen.labels[3].setText("Y Zoom: " + Screen.zoomY);
                if (Screen.mode==1) {
                    f(Screen.fField1.getText());
                    repaint();
                } else {
                    f(Screen.fField1.getText(),Screen.fField2.getText());
                    repaint();
                }
                break;
            default:
                System.out.println("Error on mouse code");
        }
    }

    public void mousePressed(MouseEvent e) {
        if (e.getButton()==2) {
            ox = e.getX();
            oy = e.getY();
        }
    }

    public void mouseReleased(MouseEvent e) {
        if (e.getButton()==2) {
            int dx = e.getX() - ox;
            int dy = oy - e.getY();
            Screen.offsetX += dx;
            Screen.offsetY -= dy;
            Screen.labels[0].setText("X offset: " + Screen.offsetX);
            Screen.labels[1].setText("Y offset: " + Screen.offsetY);
            if (Screen.mode==1) {
                f(Screen.fField1.getText());
                repaint();
            } else {
                f(Screen.fField1.getText(),Screen.fField2.getText());
                repaint();
            }
        }
    }

    public void mouseEntered(MouseEvent e) {
        //System.out.println("Event = " + e);
    }

    public void mouseExited(MouseEvent e) {
        //System.out.println("Event = " + e);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        System.out.println("points size: " + points.size());
        if (Screen.mode==1) {
            for (int t=1;t<points.size();t++) {
                g.drawLine((int)(t-1), (Screen.H + Screen.offsetY) - (int)(points.get(t-1).intValue() * Screen.zoomY), (int)t, (Screen.H + Screen.offsetY) - (int)(points.get(t).intValue() * Screen.zoomY));
            }
        } else if (Screen.mode==2) {
            for (int t=1;t<points.size();t++) {
                if (t%(Screen.W*Screen.Resolution)==0) {

                } else {
                    g.drawLine(points.get(t-1).intValue(), Screen.H - points2.get(t-1).intValue(), points.get(t).intValue(), Screen.H - points2.get(t).intValue());
                }
            }
        }

        
    }

    public void f(String raw) {
        points.clear();
        for (int t=0;t<Screen.W;t++) {
            if(!raw.equals("")) {
                ArrayList<String> args =  slicer(raw);
                points.add(scheme(args, (Screen.zoomX * (t - (Screen.offsetX))))); 
            } else {
                points.add(0.0);
            }
        }
    }

    public void fNC(String raw) {
        for (int t=0;t<Screen.W;t++) {
            if(!raw.equals("")) {
                ArrayList<String> args =  slicer(raw);
                points.add(scheme(args, t));
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
                points.add(scheme(args, t));   
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
                points.add(scheme(args, t)); 
                points2.add(scheme(args2, t));
            } else {
                points.add(0.0);   
                points2.add(0.0);  
            }
        }
    }

    public ArrayList<String> slicer (String raw) { 
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
