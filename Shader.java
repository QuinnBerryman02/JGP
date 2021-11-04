import java.awt.*;
import java.util.ArrayList;;

public class Shader {
    public String rRaw = "";
    public String gRaw = "";
    public String bRaw = "";
    public String aRaw = "";

    public Shader() {
        super();
        setRaws(ColorWheel.DEFAULT_RAW);
    }

    public Shader(String r, String g, String b, String a) {
        rRaw = r;
        gRaw = g;
        bRaw = b;
        aRaw = a;
    }
    
    public static Color red(Point p) {
        return Color.RED;
    }
    public static Color rainbow(Point p) {
        int r = Math.floorMod(p.x,256);
        int g = Math.floorMod(p.y,256);
        int b = Math.floorMod(p.x*p.y,256);
        Color c = new Color(r, g, b, 255);
        return c;
    }
    public static Color dotProduct(Point p, Point o) {
        int r = (int)Math.floor(dotProduct(p.x, p.y, o.x+0, o.y-100));
        int g = (int)Math.floor(dotProduct(p.x, p.y, o.x+Math.sqrt(3.0/4.0)*100, o.y+100/2));
        int b = (int)Math.floor(dotProduct(p.x, p.y, o.x-Math.sqrt(3.0/4.0)*100, o.y+100/2));
        Color c = new Color(r,g,b,255);
        return c;
    }

    public static Color custom(int r, int g, int b, int a) {
        return new Color(r,g,b,a);
    }

    public static double dotProduct(double x1, double y1, double x2, double y2) {
        return 255*(0.5 + (0.5 * ((x1*x2 + y1*y2) / (Math.sqrt(x1 * x1 + y1 * y1) * Math.sqrt(x2 * x2 + y2 * y2)))));
    }

    public String toString() {
        return  rRaw + " " +
                gRaw + " " +
                bRaw + " " +
                aRaw + " ";
    }

    public ArrayList<String> getRaws() {
        ArrayList<String> temp = new ArrayList<String>() {{
            add(rRaw);
            add(gRaw);
            add(bRaw);
            add(aRaw);
        }};
        return temp;
    }

    public void setRaws(ArrayList<String> raws) {
        rRaw = raws.get(0);
        gRaw = raws.get(1);
        bRaw = raws.get(2);
        aRaw = raws.get(3);
    }
}