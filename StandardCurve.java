import java.util.ArrayList;
import java.awt.*;

public class StandardCurve extends Curve{
    public String raw;
    public ArrayList<Double> pointsY = new ArrayList<Double>();
    public ArrayList<Color> pointsC = new ArrayList<Color>();

    public StandardCurve() {
        super();
    }

    @Override
    public void update() {
        pointsY.clear();
        pointsC.clear();
        raw = Screen.fFields.get(0).getText();
        ArrayList<String> args = slicer(raw);
        ArrayList<String> rStr = slicer(shader.rRaw);
        ArrayList<String> gStr = slicer(shader.gRaw);
        ArrayList<String> bStr = slicer(shader.bRaw);
        ArrayList<String> aStr = slicer(shader.aRaw);
        //Point oPoint = new Point(Screen.W/2,Screen.H/2);
        //need to optimise, by evaluating anything that doesnt have a t, all the other evals should be constants
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
                //pointsC.add(Shader.rainbow(new Point((int)t,(int)yTransform)));  
                //System.out.println("t : " + t + "\txTrans : " + xTransform + "\teval : " + evaluate + "\tyTrans" + yTransform);
            } else {
                pointsY.add(0.0);
                pointsC.add(Color.BLACK);  
            }
            
        }
        Screen.graph.repaint();
    }

    public void draw(Graphics g) {
        for (int t=1;t<pointsY.size();t++) {
            g.setColor(pointsC.get(t-1));
            g.drawLine(t-1, pointsY.get(t-1).intValue(), t, pointsY.get(t).intValue());
        }
    }
    
    public ArrayList<String> getRaws() {
        //System.out.println("Raw "+raw);
        ArrayList<String> raws = new ArrayList<String>();
        raws.add(raw);
        return raws;
    }
}
