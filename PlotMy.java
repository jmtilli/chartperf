import javax.swing.*;
import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.io.*;
import javax.imageio.*;

public class PlotMy {
  public static void main(String[] args) throws Throwable
  {
    double[] xpoints = new double[Data.data.length];
    double[] ypoints = new double[Data.data.length];
    double[] ypoints2 = new double[Data.data.length];
    BufferedImage buf = null;
    
    for (int i = 0; i < xpoints.length; i++)
    {
      xpoints[i] = i * 0.00100 + 0.00050;
    }

    for (int i = 0; i < ypoints2.length; i++)
    {
      ypoints2[i] = 10*Math.log(2*Data.data[i])/Math.log(10);
    }

    double xmax = xpoints[xpoints.length-1];
    double xmin = xpoints[0];
    double ymax = 60, ymin = -5;
    double rectxmin = 30, rectxmax = 1010;
    double rectymin = 10, rectymax = 720;
    double rectxdiff = rectxmax - rectxmin;
    double rectydiff = rectymax - rectymin;

    for (int i = 0; i < 1000; i++)
    {
      buf = new BufferedImage(1024, 768, BufferedImage.TYPE_INT_RGB);
      Graphics2D graph = buf.createGraphics();
      graph.setColor(Color.WHITE);
      graph.fillRect(0, 0, 1024, 768);
      Line2D.Double shape = new Line2D.Double();
      shape = new Line2D.Double();
      graph.setStroke(new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 10.0f, new float[]{3.0f, 3.0f}, 0.0f));
      graph.setColor(Color.GRAY);
      for (int j = 0; j <= 5; j++)
      {
        double y = (ymax-10*j)/(ymax-ymin)*rectydiff + rectymin;
        double x1 = rectxmin;
        double x2 = rectxmax;
        double y1 = y;
        double y2 = y;
        shape.setLine(x1, y1, x2, y2);
        graph.draw(shape);
      }
      shape = new Line2D.Double();
      graph.setColor(Color.GRAY);
      for (int j = 1; j <= 19; j++)
      {
        double x = (0.1*j-xmin)/(xmax-xmin)*rectxdiff + rectxmin;
        double y1 = rectymin;
        double y2 = rectymax;
        double x1 = x;
        double x2 = x;
        shape.setLine(x1, y1, x2, y2);
        graph.draw(shape);
      }
      graph.setStroke(new BasicStroke(1.0f));
      graph.setClip((int)rectxmin, (int)rectymin, (int)rectxdiff, (int)rectydiff);
      for (int j = 1; j < xpoints.length; j++)
      {
        double x1 = (xpoints[j-1]-xmin)/(xmax-xmin)*rectxdiff + rectxmin;
        double x2 = (xpoints[j]-xmin)/(xmax-xmin)*rectxdiff + rectxmin;
        double y1 = (ymax-ypoints[j-1])/(ymax-ymin)*rectydiff + rectymin;
        double y2 = (ymax-ypoints[j])/(ymax-ymin)*rectydiff + rectymin;
        shape.setLine(x1, y1, x2, y2);
        graph.setColor(Color.BLUE);
        graph.draw(shape);
        x1 = (xpoints[j-1]-xmin)/(xmax-xmin)*rectxdiff + rectxmin;
        x2 = (xpoints[j]-xmin)/(xmax-xmin)*rectxdiff + rectxmin;
        y1 = (ymax-ypoints2[j-1])/(ymax-ymin)*rectydiff + rectymin;
        y2 = (ymax-ypoints2[j])/(ymax-ymin)*rectydiff + rectymin;
        shape.setLine(x1, y1, x2, y2);
        graph.setColor(Color.RED);
        graph.draw(shape);
      }
      graph.setClip(0, 0, 1024, 768);
      Rectangle2D.Double rect;
      rect = new Rectangle2D.Double(rectxmin, rectymin, rectxdiff, rectydiff);
      graph.setStroke(new BasicStroke(1.0f));
      graph.setColor(Color.BLACK);
      graph.draw(rect);
      graph.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
      graph.setColor(Color.BLACK);
      for (int j = 0; j <= 6; j++)
      {
        double y = (ymax-10*j)/(ymax-ymin)*rectydiff + rectymin;
        double x1 = rectxmin;
        double x2 = rectxmax;
        double y1 = y;
        double y2 = y;
        graph.drawString(""+(10*j), 6, (int)y + 5);
      }
      for (int j = 0; j <= 20; j++)
      {
        double x = (0.1*j-xmin)/(xmax-xmin)*rectxdiff + rectxmin;
        double y1 = rectymin;
        double y2 = rectymax;
        double x1 = x;
        double x2 = x;
        graph.drawString(""+String.format("%.1f", 0.1*j), (int)x - 10, 768-16);
      }
      graph.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
    }
    if (args.length >= 1)
    {
      ImageIO.write(buf, "png", new File(args[0]));
    }
  }
}
