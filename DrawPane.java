import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class DrawPane extends JPanel {
    public DrawPane() {
        super();
        setBorder(new EmptyBorder(5,5,5,5));
        setPreferredSize(new Dimension(Screen.W,Screen.H));
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
    public int ox = 0;
    public int oy = 0;
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
            int oldX = Screen.curve.getOffsetX();
            int oldY = Screen.curve.getOffsetY();
            int dx = e.getX() - ox;
            int dy = oy - e.getY();
            Screen.curve.setOffset(oldX + dx,oldY - dy);
            Screen.labelPanel.sync(Screen.curve);  //"X offset: " + (oldX + dx)   //"Y offset: " + (oldY - dy)
            Screen.curve.update();
            Screen.graph.repaint();
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
        double oldX = Screen.curve.getZoomX();
        double oldY = Screen.curve.getZoomY();
        double zX = Math.pow(1.1,-mwe.getPreciseWheelRotation());
        double zY = Math.pow(1.1,-mwe.getPreciseWheelRotation());
        Screen.curve.setZoom(oldX * zX, oldY * zY);
        Screen.labelPanel.sync(Screen.curve); //"X Zoom: " + (oldX * zX)   //"Y Zoom: " + (oldY * zY)
        Screen.curve.update();
        Screen.graph.repaint();
    }
}