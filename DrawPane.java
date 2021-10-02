import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class DrawPane extends JPanel {
    public DrawPane() {
        super();
        addMouseListener(new MyMouse());
        addMouseWheelListener(new MyWheel());
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Screen.curve.draw(g);
    }
}

class MyMouse implements MouseListener {
    public static int ox = 0;
    public static int oy = 0;
    public MyMouse() {
        super();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        //
    }

    public void mousePressed(MouseEvent e) {
        if (e.getButton()==1) {
            ox = e.getX();
            oy = e.getY();
        }
    }

    public void mouseReleased(MouseEvent e) {
        if (e.getButton()==1) {
            int dx = e.getX() - ox;
            int dy = oy - e.getY();
            Screen.curve.offsetX += dx;
            Screen.curve.offsetY -= dy;
            Screen.labels[0].setText("X offset: " + Screen.curve.offsetX);
            Screen.labels[1].setText("Y offset: " + Screen.curve.offsetY);
            Screen.curve.update();
        }
    }

    public void mouseEntered(MouseEvent e) {
        //System.out.println("Event = " + e);
    }

    public void mouseExited(MouseEvent e) {
        //System.out.println("Event = " + e);
    }
}

class MyWheel implements MouseWheelListener{
    public MyWheel() {
        super();
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent mwe) {
        Screen.curve.zoomX *= Math.pow(1.1,-mwe.getPreciseWheelRotation());
        Screen.curve.zoomY *= Math.pow(1.1,-mwe.getPreciseWheelRotation());
        Screen.labels[2].setText("X Zoom: " + Screen.curve.zoomX);
        Screen.labels[3].setText("Y Zoom: " + Screen.curve.zoomY);
        Screen.curve.update();
    }
}