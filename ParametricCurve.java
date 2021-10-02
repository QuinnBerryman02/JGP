import java.util.ArrayList;
import java.awt.*;

public class ParametricCurve extends Curve{
    public String raw1;
    public String raw2;
    public ArrayList<Double> pointsX = new ArrayList<Double>();
    public ArrayList<Double> pointsY = new ArrayList<Double>();

    public ParametricCurve() {
        super();
    }

    @Override
    public void update() {
        pointsX.clear();
        pointsY.clear();
        raw1 = Screen.fFields.get(0).getText();
        raw2 = Screen.fFields.get(1).getText();
        ArrayList<String> args1 =  slicer(raw1);
        ArrayList<String> args2 =  slicer(raw2);
        for (double t=mnF;t<mxF;t+=chF) {
            if(!raw1.equals("")&&!raw2.equals("")) {
                double xEvaluate = scheme(args1, t);
                double xTransform = ((xEvaluate*zoomX) + offsetX);
                double yEvaluate = scheme(args2, t);
                double yTransform = (Screen.H + offsetY) - (yEvaluate * zoomY);
                //(Screen.H + Screen.offsetY) - (int)(points2.get(t-1).intValue() * Screen.zoomY)
                pointsX.add(xTransform);   
                pointsY.add(yTransform);
                //g.drawLine(, (Screen.H + Screen.offsetY) - (int)(points2.get(t-1).intValue() * Screen.zoomY), (int)((points.get(t).intValue()*Screen.zoomX) + (Screen.offsetX)), (Screen.H + Screen.offsetY) - (int)(points2.get(t).intValue() * Screen.zoomY));
            } else {
                pointsX.add(0.0);
                pointsY.add(0.0);
            }
        }
        Screen.graph.repaint();
    }

    public void draw(Graphics g) {
        for (int t=1;t<pointsX.size();t++) {    
            g.drawLine(pointsX.get(t-1).intValue(), pointsY.get(t-1).intValue(),pointsX.get(t).intValue(),pointsY.get(t).intValue());
        }
    }
}