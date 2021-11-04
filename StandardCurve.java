import java.util.ArrayList;
import java.awt.*;

public class StandardCurve extends Curve{
    public static final ArrayList<Double> DEFAULT = new ArrayList<>() {{add(0.0); add((double)Screen.W); add(1.0);}};
    public String raw = "";
    public ArrayList<Double> pointsY = new ArrayList<Double>();
    public ArrayList<Color> pointsC = new ArrayList<Color>();

    public StandardCurve() {
        super();
        funcs = 1;
    }

    @Override
    public void update() {
        pointsY.clear();
        pointsC.clear();
        ArrayList<String> args = slicer(raw);
        ArrayList<String> rStr = slicer(shader.rRaw);
        ArrayList<String> gStr = slicer(shader.gRaw);
        ArrayList<String> bStr = slicer(shader.bRaw);
        ArrayList<String> aStr = slicer(shader.aRaw);
        for (double t=0;t<Screen.W;t+=1) {
            if(!raw.equals("")) {
                double xTransform = ((t - ((double)offsetX))/zoomX);
                double evaluate = scheme(args, xTransform);
                double yTransform = (Screen.H + offsetY) - (evaluate * zoomY);
                pointsY.add(yTransform);   
                pointsC.add(Shader.custom(  (int)scheme(rStr,t), 
                                            (int)scheme(gStr,t),
                                            (int)scheme(bStr,t), 
                                            (int)scheme(aStr,t))); 
            } else {
                pointsY.add(0.0);
                pointsC.add(Color.BLACK);  
            }
            
        }
    }

    public void draw(Graphics g) {
        for (int t=1;t<pointsY.size();t++) {
            g.setColor(pointsC.get(t-1));
            g.drawLine(t-1, pointsY.get(t-1).intValue(), t, pointsY.get(t).intValue());
        }
    }
    
    public ArrayList<String> getRaws() {
        ArrayList<String> raws = new ArrayList<String>() {{
            add(raw);
        }};
        return raws;
    }

    public void setRaws(ArrayList<String> raws) {
        raw = raws.get(0);
    }
}
