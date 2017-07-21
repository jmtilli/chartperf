import javax.swing.*;
import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.io.*;
import javax.imageio.*;
import java.util.*;

public class MyLineChart {
  public class Series {
    public final double[] xpoints;
    public final double[] ypoints;
    public final Color color;
    public Series(double[] xpoints, double[] ypoints, Color color)
    {
      this.xpoints = xpoints;
      this.ypoints = ypoints;
      this.color = color;
    }
  };
  private ArrayList<Series> series = new ArrayList<Series>();
  private double ymax;
  private double ymin;
  private boolean ybounds = false;
  private double xmax;
  private double xmin;
  private boolean xbounds = false;
  public void addSeries(double[] xpoints, double[] ypoints, Color color)
  {
    this.series.add(new Series(xpoints, ypoints, color));
  }
  public void setXBounds(double xmin, double xmax)
  {
    if (xmin >= xmax || Double.isNaN(xmin) || Double.isNaN(xmax))
    {
      this.xbounds = false;
      return;
    }
    this.xbounds = true;
    this.xmin = xmin;
    this.xmax = xmax;
  }
  public void setYBounds(double ymin, double ymax)
  {
    if (ymin >= ymax || Double.isNaN(ymin) || Double.isNaN(ymax))
    {
      this.ybounds = false;
      return;
    }
    this.ybounds = true;
    this.ymin = ymin;
    this.ymax = ymax;
  }
  private static double getTics(double min, double max)
  {
    double l = Math.log(max-min)/Math.log(10);
    for (int exp = (int)(l-2);
         exp <= (int)(l+2);
         exp++)
    {
      double pow = Math.pow(10.0, exp);
      for (double d: new double[]{1, 2, 5})
      {
        if (d*pow >= (max-min)/20)
        {
          return d*pow;
        }
      }
    }
    throw new RuntimeException("never happens");
  }
  private static double getTic(double min, double max, double tic, int i)
  {
    double firstTic = Math.ceil(min/tic)*tic;
    return firstTic + tic*i;
  }
  private static int getTicCount(double min, double max, double tic)
  {
    double firstTic = getTic(min, max, tic, 0);
    return ((int)((max - firstTic)/tic)) + 1;
  }
  public void render(Graphics2D graph, int width, int height)
  {
    double ymax;
    double ymin;
    double xmax;
    double xmin;
    double xtic;
    double ytic;
    if (!this.xbounds)
    {
      xmin = Double.POSITIVE_INFINITY;
      xmax = Double.NEGATIVE_INFINITY;
      for (Series series: this.series)
      {      
        for (double xpoint: series.xpoints)
        {
          if (xpoint < xmin)
          {
            xmin = xpoint;
          }
          if (xpoint > xmax)
          {
            xmax = xpoint;
          }
        }
      }      
    }
    else
    {
      xmin = this.xmin;
      xmax = this.xmax;
    }
    if (!this.ybounds)
    {
      ymin = Double.POSITIVE_INFINITY;
      ymax = Double.NEGATIVE_INFINITY;
      for (Series series: this.series)
      {      
        for (double ypoint: series.ypoints)
        {
          if (ypoint < ymin)
          {
            ymin = ypoint;
          }
          if (ypoint > ymax)
          {
            ymax = ypoint;
          }
        }
      }
    }
    else
    {
      ymin = this.ymin;
      ymax = this.ymax;
    }
    ytic = getTics(ymin, ymax);
    xtic = getTics(xmin, xmax);
    double rectxmin = 40, rectxmax = width-(1024-1010);
    double rectymin = 10, rectymax = height-(768-720);
    double rectxdiff = rectxmax - rectxmin;
    double rectydiff = rectymax - rectymin;
    graph.setColor(Color.WHITE);
    graph.fillRect(0, 0, width, height);
    Line2D.Double shape = new Line2D.Double();
    graph.setStroke(new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 10.0f, new float[]{3.0f, 3.0f}, 0.0f));
    graph.setColor(Color.GRAY);
    int xTicCount = getTicCount(xmin, xmax, xtic);
    int yTicCount = getTicCount(ymin, ymax, ytic);
    for (int j = 0; j < yTicCount; j++)
    {
      double y = (ymax-getTic(ymin, ymax, ytic, j))/(ymax-ymin)*rectydiff + rectymin;
      double x1 = rectxmin;
      double x2 = rectxmax;
      double y1 = y;
      double y2 = y;
      shape.setLine(x1, y1, x2, y2);
      graph.draw(shape);
    }
    for (int j = 0; j < xTicCount; j++)
    {
      double x = (getTic(xmin, xmax, xtic, j)-xmin)/(xmax-xmin)*rectxdiff + rectxmin;
      double y1 = rectymin;
      double y2 = rectymax;
      double x1 = x;
      double x2 = x;
      shape.setLine(x1, y1, x2, y2);
      graph.draw(shape);
    }
    graph.setStroke(new BasicStroke(1.0f));
    graph.setClip((int)rectxmin, (int)rectymin, (int)rectxdiff, (int)rectydiff);
    for (Series series: this.series)
    {
      double[] xpoints = series.xpoints;
      double[] ypoints = series.ypoints;
      graph.setColor(series.color);
      for (int j = 1; j < xpoints.length; j++)
      {
        double x1 = (xpoints[j-1]-xmin)/(xmax-xmin)*rectxdiff + rectxmin;
        double x2 = (xpoints[j]-xmin)/(xmax-xmin)*rectxdiff + rectxmin;
        double y1 = (ymax-ypoints[j-1])/(ymax-ymin)*rectydiff + rectymin;
        double y2 = (ymax-ypoints[j])/(ymax-ymin)*rectydiff + rectymin;
        shape.setLine(x1, y1, x2, y2);
        graph.draw(shape);
      }
    }
    graph.setClip(0, 0, width, height);
    Rectangle2D.Double rect;
    rect = new Rectangle2D.Double(rectxmin, rectymin, rectxdiff, rectydiff);
    graph.setStroke(new BasicStroke(1.0f));
    graph.setColor(Color.BLACK);
    graph.draw(rect);
    graph.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    graph.setColor(Color.BLACK);
    for (int j = 0; j < yTicCount; j++)
    {
      double y = (ymax-getTic(ymin, ymax, ytic, j))/(ymax-ymin)*rectydiff + rectymin;
      double x1 = rectxmin;
      double x2 = rectxmax;
      double y1 = y;
      double y2 = y;
      graph.drawString(String.format("%.2g", getTic(ymin, ymax, ytic, j)),
                       6, (int)y + 5);
    }
    for (int j = 0; j < xTicCount; j++)
    {
      double x = (getTic(xmin, xmax, xtic, j)-xmin)/(xmax-xmin)*rectxdiff + rectxmin;
      double y1 = rectymin;
      double y2 = rectymax;
      double x1 = x;
      double x2 = x;
      graph.drawString(String.format("%.2g", getTic(xmin, xmax, xtic, j)), (int)x - 10, height-16);
    }
    graph.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
  }
};
