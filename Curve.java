import java.awt.*;
import java.util.*;

public class Curve {
    public double zoomX = 1;
    public double zoomY = 1;
    public int offsetX = 0;
    public int offsetY = 0;
    public double mnF = 0;
    public double mxF = Screen.W;
    public double chF = 1;
    public int A = 1;
    public int B = 1;
    public int C = 1;
    public int D = 1;
    public Shader shader;

    public Curve () {
        shader = new Shader();
        // shader.rRaw = new String("255");
        // shader.gRaw = new String("0");
        // shader.bRaw = new String("0");
        // shader.aRaw = new String("255");
    }
    
    public void update() {

    }

    public void draw(Graphics g) {

    }

    public ArrayList<String> getRaws() {
        return null;
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
                return (double)A;
            } else if (args.get(0).equals("b")) {
                return (double)B;
            } else if (args.get(0).equals("c")) {
                return (double)C;
            } else if (args.get(0).equals("d")) {
                return (double)D;
            } else if (args.get(0).equals("pi")) {
                return Math.PI;
            } else if (args.get(0).equals("e")) {
                return Math.E;
            } else if (args.get(0).equals("rand")) {
                return Math.random();
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
            case "acos":
                if (args.size()>2) {
                    System.out.println("ARGUMENT ERROR");
                    return 0.0;
                } else {
                    answer =  Math.acos(scheme(slicer(args.get(1)),t));
                }
                break;
            case "asin":
                if (args.size()>2) {
                    System.out.println("ARGUMENT ERROR");
                    return 0.0;
                } else {
                    answer =  Math.asin(scheme(slicer(args.get(1)),t));
                }
                break;
            case "atan":
                if (args.size()>2) {
                    System.out.println("ARGUMENT ERROR");
                    return 0.0;
                } else {
                    answer =  Math.atan(scheme(slicer(args.get(1)),t));
                }
                break;
            case "cosh":
                if (args.size()>2) {
                    System.out.println("ARGUMENT ERROR");
                    return 0.0;
                } else {
                    answer =  Math.cosh(scheme(slicer(args.get(1)),t));
                }
                break;
            case "sinh":
                if (args.size()>2) {
                    System.out.println("ARGUMENT ERROR");
                    return 0.0;
                } else {
                    answer =  Math.sinh(scheme(slicer(args.get(1)),t));
                }
                break;
            case "tanh":
                if (args.size()>2) {
                    System.out.println("ARGUMENT ERROR");
                    return 0.0;
                } else {
                    answer =  Math.tanh(scheme(slicer(args.get(1)),t));
                }
                break;
            case "^":
                answer = scheme(slicer(args.get(args.size()-1)),t);
                for (int k=args.size()-1;k>1;k--) {
                    answer = Math.pow(scheme(slicer(args.get(k-1)), t), answer);
                }
                break;
            case "floor":
                if (args.size()>2) {
                    System.out.println("ARGUMENT ERROR");
                    return 0.0;
                } else {
                    answer =  Math.floor(scheme(slicer(args.get(1)),t));
                }
                break;
            case "ceil":
                if (args.size()>2) {
                    System.out.println("ARGUMENT ERROR");
                    return 0.0;
                } else {
                    answer =  Math.ceil(scheme(slicer(args.get(1)),t));
                }
                break;
            case "max":
                answer = scheme(slicer(args.get(1)),t);
                for (int i=2;i<args.size();i++) {
                    double check = scheme(slicer(args.get(i)),t);
                    if (check>answer) {
                        answer = check; 
                    }
                }
            case "min":
                answer = scheme(slicer(args.get(1)),t);
                for (int i=2;i<args.size();i++) {
                    double check = scheme(slicer(args.get(i)),t);
                    if (check<answer) {
                        answer = check; 
                    }
                }
            case "rand":
                if (args.size()!=3) {
                    System.out.println("ARGUMENT ERROR");
                    return 0.0;
                } else {
                    answer = scheme(slicer(args.get(1)),t) + Math.random()*Math.abs(scheme(slicer(args.get(2)),t)-scheme(slicer(args.get(1)),t));
                }
                break;
            case "%":
                if (args.size()!=3) {
                    System.out.println("ARGUMENT ERROR");
                    return 0.0;
                } else {
                    answer = Math.floorMod((int)scheme(slicer(args.get(1)),t), (int)scheme(slicer(args.get(2)),t));
                }
                break;
            default:
                break;
        }
        return answer;
    }

    public String toString() {
        return String.valueOf(zoomX) +
        String.valueOf(zoomY) +
        String.valueOf(offsetX) +
        String.valueOf(offsetY) +
        String.valueOf(mnF) +
        String.valueOf(mxF) +
        String.valueOf(chF) +
        String.valueOf(A) +
        String.valueOf(B) +
        String.valueOf(C) +
        String.valueOf(D) +
        String.valueOf(shader) + 
        String.valueOf(getRaws()); 
    }
}