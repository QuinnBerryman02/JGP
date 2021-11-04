import java.util.ArrayList;
import java.awt.*;

public class ParametricPolarCurve extends Curve{
    public static final ArrayList<Double> DEFAULT = new ArrayList<>() {{add(0.0); add(2.0); add(0.001);}};
    public String raw1 = "";
    public String raw2 = "";
    public ArrayList<Double> pointsX = new ArrayList<Double>();
    public ArrayList<Double> pointsY = new ArrayList<Double>();
    public ArrayList<Color> pointsC = new ArrayList<Color>();

    public ParametricPolarCurve() {
        super();
        funcs = 2;
    }

    @Override
    public void update() {
        pointsX.clear();
        pointsY.clear();
        pointsC.clear();
        ArrayList<String> args1 = slicer(raw1);
        ArrayList<String> args2 =  slicer(raw2);
        ArrayList<String> rStr = slicer(shader.rRaw);
        ArrayList<String> gStr = slicer(shader.gRaw);
        ArrayList<String> bStr = slicer(shader.bRaw);
        ArrayList<String> aStr = slicer(shader.aRaw);
        //Point oPoint = new Point(Screen.W/2,Screen.H/2);
        //need to optimise, by evaluating anything that doesnt have a t, all the other evals should be constants
        if(chF == 0) {
            chF = 1;
        }
        for (double t=mnF*Math.PI;t<mxF*Math.PI;t+=chF*Math.PI) {
            if(!raw1.equals("")&&!raw2.equals("")) {
                double xEvaluate = scheme(args1, t);
                double yEvaluate = scheme(args2, t);
                double rectX = (Math.cos(xEvaluate)*zoomX*yEvaluate)+offsetX;
                double rectY = (Screen.H + offsetY) - (Math.sin(xEvaluate)*zoomY*yEvaluate);
                pointsX.add(rectX);   
                pointsY.add(rectY);   
                pointsC.add(Shader.custom(  (int)scheme(rStr,t), 
                                            (int)scheme(gStr,t),
                                            (int)scheme(bStr,t), 
                                            (int)scheme(aStr,t)));
                //pointsC.add(Shader.rainbow(new Point((int)t,(int)yTransform)));  
                //System.out.println("t : " + t + "\txTrans : " + xTransform + "\teval : " + evaluate + "\tyTrans" + yTransform);
            } else {
                pointsX.add(0.0);
                pointsY.add(0.0);
                pointsC.add(Color.BLACK);  
            }
            
        }
    }

    public void draw(Graphics g) {
        for (int t=1;t<pointsY.size();t++) {
            g.setColor(pointsC.get(t-1));
            g.drawLine(pointsX.get(t-1).intValue(), pointsY.get(t-1).intValue(), pointsX.get(t).intValue(), pointsY.get(t).intValue());
        }
    }
    
    public ArrayList<String> getRaws() {
        //System.out.println("Raw "+raw);
        ArrayList<String> raws = new ArrayList<String>() {{add(raw1); add(raw2);}};
        return raws;
    }

    public void setRaws(ArrayList<String> raws) {
        raw1 = raws.get(0);
        raw2 = raws.get(1);
    }
}
