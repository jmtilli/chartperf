import javax.swing.*;
import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;
import java.util.*;
import org.knowm.xchart.*;
import org.knowm.xchart.style.*;
import org.knowm.xchart.style.markers.*;

public class XLineChart {
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
  String title, xtitle, ytitle;
  public void setTitle(String title)
  {
    this.title = title;
  }
  public void setXTitle(String xtitle)
  {
    this.xtitle = xtitle;
  }
  public void setYTitle(String ytitle)
  {
    this.ytitle = ytitle;
  }
  public void render(Graphics2D graph, int width, int height)
  {
    XYChart xychart = new XYChartBuilder().width(800).height(600).title(title).xAxisTitle(xtitle).yAxisTitle(ytitle).build();
    xychart.getStyler().setChartBackgroundColor(UIManager.getColor("Panel.background"));
    xychart.getStyler().setDefaultSeriesRenderStyle(org.knowm.xchart.XYSeries.XYSeriesRenderStyle.Line);
    xychart.getStyler().setYAxisLabelAlignment(Styler.TextAlignment.Right);
    xychart.getStyler().setPlotMargin(0);
    xychart.getStyler().setPlotContentSize(.99);
    xychart.getStyler().setLegendVisible(false);
    xychart.getStyler().setAntiAlias(false);
    if (ybounds)
    {
      xychart.getStyler().setYAxisMin(ymin);
      xychart.getStyler().setYAxisMax(ymax);
    }
    if (xbounds)
    {
      xychart.getStyler().setXAxisMin(xmin);
      xychart.getStyler().setXAxisMax(xmax);
    }

    int i = 0;
    for (Series series: this.series)
    {
      org.knowm.xchart.XYSeries ser2 = xychart.addSeries("Series " + i, series.xpoints, series.ypoints);
      ser2.setLineColor(series.color);
      ser2.setLineWidth(1);
      ser2.setMarker(new None());
      i++;
    }
    xychart.paint(graph, width, height);
  }
};
