import java.util.ArrayList;
import java.awt.*;

public class StandardCurve extends Curve{
    public String raw;
    public ArrayList<Double> pointsY = new ArrayList<Double>();

    public StandardCurve() {
        super();
    }

    @Override
    public void update() {
        pointsY.clear();
        raw = Screen.fFields.get(0).getText();
        ArrayList<String> args = slicer(raw);
        //need to optimise, by evaluating anything that doesnt have a t, all the other evals should be constants
        for (double t=0;t<Screen.W;t+=1) {
            if(!raw.equals("")) {
                double xTransform = ((t - ((double)offsetX))/zoomX);
                double evaluate = scheme(args, xTransform);
                double yTransform = (Screen.H + offsetY) - (evaluate * zoomY);
                pointsY.add(yTransform);     
                //System.out.println("t : " + t + "\txTrans : " + xTransform + "\teval : " + evaluate + "\tyTrans" + yTransform);
            } else {
                pointsY.add(0.0);
            }
        }
        Screen.graph.repaint();
    }

    public void draw(Graphics g) {
        for (int t=1;t<pointsY.size();t++) {
            g.drawLine(t-1, pointsY.get(t-1).intValue(), t, pointsY.get(t).intValue());
        }
    }
}
