import java.awt.*;
import java.util.ArrayList;

public class ColorWheel extends Curve{
    public static final ArrayList<Double> DEFAULT = new ArrayList<>() {{add(0.0); add((double)Screen.W); add(1.0);}};
    public static final ArrayList<String> DEFAULT_RAW = new ArrayList<>() {{add("0"); add("0"); add("0"); add("255");}};
    public ArrayList<Color> pointsC = new ArrayList<Color>();

    public ColorWheel() {
        super();
        funcs = 4;
    }
    
    @Override
    public void update() {
        pointsC.clear();
        ArrayList<String> rStr = slicer(shader.rRaw);
        ArrayList<String> gStr = slicer(shader.gRaw);
        ArrayList<String> bStr = slicer(shader.bRaw);
        ArrayList<String> aStr = slicer(shader.aRaw);
        if(!shader.rRaw.equals("")&&!shader.gRaw.equals("")&&!shader.bRaw.equals("")&&!shader.aRaw.equals("")) {
            for (double t=0;t<Screen.W;t+=1) { 
                pointsC.add(Shader.custom(  (int)scheme(rStr,t), 
                                            (int)scheme(gStr,t),
                                            (int)scheme(bStr,t), 
                                            (int)scheme(aStr,t)));
            }
        } else {
            pointsC.add(Color.WHITE);  
        }
    }

    public void draw(Graphics g) {
        for (int t=0;t<pointsC.size();t++) {
            g.setColor(pointsC.get(t));
            g.drawLine(t, 0, t, Screen.H);
        }
    }

    public ArrayList<String> getRaws() {
        //System.out.println("Raw "+raw);
        ArrayList<String> raws = new ArrayList<String>() {{
            add(shader.rRaw);
            add(shader.gRaw);
            add(shader.bRaw);
            add(shader.aRaw);
        }};
        return raws;
    }

    public void setRaws(ArrayList<String> raws) {
        shader.setRaws(raws);
    }
}