import java.awt.*;
import java.util.ArrayList;

public class ColorWheel extends Curve{
    public ArrayList<Color> pointsC = new ArrayList<Color>();

    public ColorWheel() {
        super();
    }
    
    @Override
    public void update() {
        pointsC.clear();
        shader.rRaw = Screen.fFields.get(0).getText();
        shader.gRaw = Screen.fFields.get(1).getText();
        shader.bRaw = Screen.fFields.get(2).getText();
        shader.aRaw = Screen.fFields.get(3).getText();
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
        Screen.graph.repaint();
    }

    public void draw(Graphics g) {
        for (int t=0;t<pointsC.size();t++) {
            g.setColor(pointsC.get(t));
            g.drawLine(t, 0, t, Screen.H);
        }
    }
}