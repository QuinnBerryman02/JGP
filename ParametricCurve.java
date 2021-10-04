import java.util.ArrayList;
import java.awt.*;

public class ParametricCurve extends Curve{
    public String raw1;
    public String raw2;
    public ArrayList<Double> pointsX = new ArrayList<Double>();
    public ArrayList<Double> pointsY = new ArrayList<Double>();
    public ArrayList<Color> pointsC = new ArrayList<Color>();

    public ParametricCurve() {
        super();
    }

    @Override
    public void update() {
        pointsX.clear();
        pointsY.clear();
        pointsC.clear();
        raw1 = Screen.fFields.get(0).getText();
        raw2 = Screen.fFields.get(1).getText();
        ArrayList<String> args1 =  slicer(raw1);
        ArrayList<String> args2 =  slicer(raw2);
        ArrayList<String> rStr = slicer(shader.rRaw);
        ArrayList<String> gStr = slicer(shader.gRaw);
        ArrayList<String> bStr = slicer(shader.bRaw);
        ArrayList<String> aStr = slicer(shader.aRaw);
        //Point oPoint = new Point(Screen.W/2,Screen.H/2);
        for (double t=mnF;t<mxF;t+=chF) {
            if(!raw1.equals("")&&!raw2.equals("")) {
                double xEvaluate = scheme(args1, t);
                double xTransform = ((xEvaluate*zoomX) + offsetX);
                double yEvaluate = scheme(args2, t);
                double yTransform = (Screen.H + offsetY) - (yEvaluate * zoomY);
                //(Screen.H + Screen.offsetY) - (int)(points2.get(t-1).intValue() * Screen.zoomY)
                pointsX.add(xTransform);   
                pointsY.add(yTransform);
                pointsC.add(Shader.custom(  (int)scheme(rStr,t), 
                                            (int)scheme(gStr,t),
                                            (int)scheme(bStr,t), 
                                            (int)scheme(aStr,t)));
                //pointsC.add(Shader.rainbow(new Point((int)xTransform,(int)yTransform)));  
                //g.drawLine(, (Screen.H + Screen.offsetY) - (int)(points2.get(t-1).intValue() * Screen.zoomY), (int)((points.get(t).intValue()*Screen.zoomX) + (Screen.offsetX)), (Screen.H + Screen.offsetY) - (int)(points2.get(t).intValue() * Screen.zoomY));
            } else {
                pointsX.add(0.0);
                pointsY.add(0.0);
                pointsC.add(Color.BLACK);  
            }
        }
        Screen.graph.repaint();
    }

    public void draw(Graphics g) {
        for (int t=1;t<pointsX.size();t++) { 
            g.setColor(pointsC.get(t-1));
            g.drawLine(pointsX.get(t-1).intValue(), pointsY.get(t-1).intValue(),pointsX.get(t).intValue(),pointsY.get(t).intValue());
        }
    }

    public ArrayList<String> getRaws() {
        //System.out.println("Raw1 "+raw1);
        //System.out.println("Raw2 "+raw2);
        ArrayList<String> raws = new ArrayList<String>();
        raws.add(raw1);
        raws.add(raw2);
        return raws;
    }
}