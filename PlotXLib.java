import javax.swing.*;
import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.io.*;
import javax.imageio.*;

public class PlotXLib {
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

    for (int i = 0; i < 1000; i++)
    {
      XLineChart chart = new XLineChart();
      buf = new BufferedImage(1024, 768, BufferedImage.TYPE_INT_RGB);
      Graphics2D graph = buf.createGraphics();
      chart.setYBounds(ymin, ymax);
      chart.addSeries(xpoints, ypoints, Color.BLUE);
      chart.addSeries(xpoints, ypoints2, Color.RED);
      chart.render(graph, 1024, 768);
    }
    if (args.length >= 1)
    {
      ImageIO.write(buf, "png", new File(args[0]));
    }
  }
}
