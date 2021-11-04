import java.awt.*;
import java.util.*;

public abstract class Curve {
    protected int number;
    protected int funcs;
    protected double zoomX = 1;
    protected double zoomY = 1;
    protected int offsetX = 0;
    protected int offsetY = 0;
    protected double mnF;
    protected double mxF;
    protected double chF;
    protected ArrayList<Integer> consts = new ArrayList<Integer>();
    protected Shader shader;

    public Curve () {
        shader = new Shader();
        number = Screen.curves.size();
    }
    
    public abstract void update();
    public abstract void draw(Graphics g);
    public abstract ArrayList<String> getRaws();
    public abstract void setRaws(ArrayList<String> raws);

    public ArrayList<Integer> getConsts() {
        return consts;
    }
    public int getConsts(int index) {
        return consts.get(index);
    }
    public double getZoomX() {
        return zoomX;
    }
    public double getZoomY() {
        return zoomY;
    }
    public int getOffsetX() {
        return offsetX;
    }
    public int getOffsetY() {
        return offsetY;
    }
    public double getMNF() {
        return mnF;
    }
    public double getMXF() {
        return mxF;
    }
    public double getCHF() {
        return chF;
    }
    public int getNumber() {
        return number;
    }
    public Shader getShader() {
        return shader;
    }
    public int getFuncs() {
        return funcs;
    }
    public void setConsts(ArrayList<Integer> values) {
        consts.clear();
        for (Integer v : values) {
            consts.add(v);
        }
    }
    public void setConsts(int index, int value) {
        consts.set(index, value);
    }
    public void setZoom(double x, double y) {
        zoomX = x;
        zoomY = y;
    }
    public void setOffset(int x, int y) {
        offsetX = x;
        offsetY = y;
    }
    public void setFields(double mnF,double mxF,double chF) {
        this.mnF = mnF;
        this.mxF = mxF;
        this.chF = chF;
    }
    public void setShader(Shader s) {
        this.shader = s;
    }
    public void initConsts(int amount) {
        consts.clear();
        for (int i=0;i<amount;i++) {
            consts.add(1);
        }
    }

    public static ArrayList<String> slicer (String raw) { 
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
            if(args.get(0).length()==1) {
                int test = args.get(0).charAt(0)-'A';
                if(test>=0 && test<consts.size()) {
                    return (double)consts.get(test);
                }
            }
            if (args.get(0).equals("t")) {
                return t;
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
            case "=":
                answer = 1;
                if(args.size()<=2) {
                    answer=1;
                } else {
                    double check = scheme(slicer(args.get(1)),t);
                    for (int i=2;i<args.size();i++) {
                        if(scheme(slicer(args.get(i)),t)==check) {
                            answer *= 1;
                        } else {
                            answer *= 0;
                        }
                    }
                }
                break;
            case "!=":
                answer = 1;
                if(args.size()<=2) {
                    answer=1;
                } else {
                    double check = scheme(slicer(args.get(1)),t);
                    for (int i=2;i<args.size();i++) {
                        if(scheme(slicer(args.get(i)),t)!=check) {
                            answer *= 1;
                        } else {
                            answer *= 0;
                        }
                    }
                }
                break;
            case ">":
                answer = 1;
                if(args.size()<=2) {
                    answer=1;
                } else {
                    for (int i=2;i<args.size();i++) {
                        if(scheme(slicer(args.get(i-1)),t)>scheme(slicer(args.get(i)),t)) {
                            answer *= 1;
                        } else {
                            answer *= 0;
                        }
                    }
                }
                break;
            case "<":
                answer = 1;
                if(args.size()<=2) {
                    answer=1;
                } else {
                    for (int i=2;i<args.size();i++) {
                        if(scheme(slicer(args.get(i-1)),t)<scheme(slicer(args.get(i)),t)) {
                            answer *= 1;
                        } else {
                            answer *= 0;
                        }
                    }
                }
                break;
            case "or":
                answer = 0;
                if(args.size()<=2) {
                    answer=0;
                } else {
                    for (int i=2;i<args.size();i++) {
                        if(scheme(slicer(args.get(i-1)),t)==1) {
                            answer = 1;
                            break;
                        }
                    }
                }
                break;
            case "and":
                answer = 1;
                if(args.size()<=2) {
                    answer=1;
                } else {
                    for (int i=2;i<args.size();i++) {
                        if(scheme(slicer(args.get(i-1)),t)!=1) {
                            answer = 0;
                            break;
                        }
                    }
                }
                break;
            case "'":
                if (args.size()!=2) {
                    System.out.println("ARGUMENT ERROR");
                    return 0.0;
                }
                answer = derivative(slicer(args.get(1)), t);
                break;
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

    public double derivative(ArrayList<String> args, double t) {    
        double answer = 0.0;
        if (args.size()==0) {
            return 0.0;
        } else if (args.size() == 1) {
            if (args.get(0).equals("t")) {
                return 1.0;
            } else {
                return 0;
            }
        }
        switch (args.get(0)) {
            case "+":
                for (int k=1;k<args.size();k++) {
                    answer += derivative(slicer(args.get(k)),t);
                }
                break;
            case "*":
                ArrayList<Double> answers = new ArrayList<>();
                for (int k=1;k<args.size();k++) {
                    answers.add(scheme(slicer(args.get(k)), t));
                }
                for (int i=1;i<args.size();i++) {
                    double tempAnswer = 0;
                    for (int j=0;j<answers.size();j++) {
                        if (j!=i-1) {
                            tempAnswer*=answers.get(j);
                        } else {
                            tempAnswer*=derivative(slicer(args.get(i)), t);
                        }
                    }
                    answer += tempAnswer;
                }
                break;
            case "-":
                //normal
                break;
            case "/":
                //quotient
                break;
            case "cos":
                if (args.size()>2) {
                    System.out.println("ARGUMENT ERROR");
                    return 0.0;
                } else {
                    answer =  -Math.sin(scheme(slicer(args.get(1)),t)) * derivative(slicer(args.get(1)),t);
                }
                break;
            case "sin":
                if (args.size()>2) {
                    System.out.println("ARGUMENT ERROR");
                    return 0.0;
                } else {
                    answer =  Math.cos(scheme(slicer(args.get(1)),t)) * derivative(slicer(args.get(1)),t);
                }
                break;
            case "tan":
                //sec ^2
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
                if (args.size()!=3) {
                    System.out.println("ARGUMENT ERROR");
                    return 0.0;
                }
                double u = scheme(slicer(args.get(1)),t);
                double v = scheme(slicer(args.get(2)),t);
                double du = derivative(slicer(args.get(1)),t);
                double dv = derivative(slicer(args.get(2)),t);
                double utothev = 1;
                if (u==0) {
                    if(v==0) {
                        utothev = 1;
                    } else {
                        answer = 0;
                        break;
                    }
                } else if(v==0) {
                    utothev =1;
                } else {
                    utothev = Math.pow(u, v);
                }
                if(du==0 && dv!=0) {
                    answer = utothev * (dv*Math.log(u));
                } else if(du!=0 && dv==0) {
                    answer = utothev * (v * (1 / u) * du);
                } else if(du==0 && dv==0) {
                    answer = utothev;
                } else {
                    answer = utothev * ( (dv*Math.log(u)) + (v * (1 / u) * du) );
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
        return "id: " + String.valueOf(number) + " " +
        "zx: " + String.valueOf(zoomX) + " " +
        "zy: " + String.valueOf(zoomY) + " " +
        "ox: " + String.valueOf(offsetX) + " " +
        "oy: " + String.valueOf(offsetY) + " " +
        "mnF: " + String.valueOf(mnF) + " " +
        "mxF: " + String.valueOf(mxF) + " " +
        "chF: " + String.valueOf(chF) + " " +
        "consts: " + String.valueOf(consts) + " " +
        "shader: " + String.valueOf(shader) +  " " +
        "raws: " + String.valueOf(getRaws()); 
    }
}