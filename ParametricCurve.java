import java.util.ArrayList;
import java.awt.*;

public class ParametricCurve extends Curve{
    public static final ArrayList<Double> DEFAULT = new ArrayList<>() {{add(0.0); add((double)Screen.W * 5); add(1.0);}};
    public String raw1 = "";
    public String raw2 = "";
    public ArrayList<Double> pointsX = new ArrayList<Double>();
    public ArrayList<Double> pointsY = new ArrayList<Double>();
    public ArrayList<Color> pointsC = new ArrayList<Color>();

    public ParametricCurve() {
        super();
        funcs = 2;
    }

    public void update() {
        pointsX.clear();
        pointsY.clear();
        pointsC.clear();
        ArrayList<String> args1 =  slicer(raw1);
        ArrayList<String> args2 =  slicer(raw2);
        ArrayList<String> rStr = slicer(shader.rRaw);
        ArrayList<String> gStr = slicer(shader.gRaw);
        ArrayList<String> bStr = slicer(shader.bRaw);
        ArrayList<String> aStr = slicer(shader.aRaw);
        for (double t=mnF;t<mxF;t+=chF) {
            if(!raw1.equals("")&&!raw2.equals("")) {
                double xEvaluate = scheme(args1, t);
                double xTransform = ((xEvaluate*zoomX) + offsetX);
                double yEvaluate = scheme(args2, t);
                double yTransform = (Screen.H + offsetY) - (yEvaluate * zoomY);
                pointsX.add(xTransform);   
                pointsY.add(yTransform);
                pointsC.add(Shader.custom(  (int)scheme(rStr,t), 
                                            (int)scheme(gStr,t),
                                            (int)scheme(bStr,t), 
                                            (int)scheme(aStr,t)));
            } else {
                pointsX.add(0.0);
                pointsY.add(0.0);
                pointsC.add(Color.BLACK);  
            }
        }
    }

    public void draw(Graphics g) {
        for (int t=1;t<pointsX.size();t++) { 
            g.setColor(pointsC.get(t-1));
            g.drawLine(pointsX.get(t-1).intValue(), pointsY.get(t-1).intValue(),pointsX.get(t).intValue(),pointsY.get(t).intValue());
        }
    }

    public ArrayList<String> getRaws() {
        ArrayList<String> raws = new ArrayList<String>() {{
            add(raw1);
            add(raw2);
        }};
        return raws;
    }

    public void setRaws(ArrayList<String> raws) {
        raw1 = raws.get(0);
        raw2 = raws.get(1);
    }
}