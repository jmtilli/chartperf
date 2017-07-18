import javax.swing.*;
import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;
import org.knowm.xchart.*;
import org.knowm.xchart.style.*;
import org.knowm.xchart.style.markers.*;

public class PlotXAA {
  public static void main(String[] args)
  {
    double[] xpoints = new double[Data.data.length];
    double[] ypoints = new double[Data.data.length];
    double[] ypoints2 = new double[Data.data.length];
    for (int i = 0; i < xpoints.length; i++)
    {
      xpoints[i] = i * 0.00100 + 0.00050;
    }
    for (int i = 0; i < ypoints2.length; i++)
    {
      ypoints2[i] = 10*Math.log(2*Data.data[i])/Math.log(10);
    }

    for (int i = 0; i < 1000; i++)
    {
      XYChart xychart = new XYChartBuilder().width(800).height(600).title("XRR").xAxisTitle("degrees").yAxisTitle("dB").build();
      xychart.getStyler().setChartBackgroundColor(UIManager.getColor("Panel.background"));
      xychart.getStyler().setDefaultSeriesRenderStyle(org.knowm.xchart.XYSeries.XYSeriesRenderStyle.Line);
      xychart.getStyler().setYAxisLabelAlignment(Styler.TextAlignment.Right);
      xychart.getStyler().setPlotMargin(0);
      xychart.getStyler().setPlotContentSize(.99);
      xychart.getStyler().setLegendVisible(false);
      org.knowm.xchart.XYSeries ser2 = xychart.addSeries("Measurement", xpoints, ypoints);
      ser2.setLineColor(Color.BLUE);
      ser2.setLineWidth(1);
      ser2.setMarker(new None());
      org.knowm.xchart.XYSeries ser1 = xychart.addSeries("Simulation", xpoints, ypoints2);
      ser1.setLineColor(Color.RED);
      ser1.setLineWidth(1);
      ser1.setMarker(new None());
      xychart.getStyler().setYAxisMin(-5.0);
      xychart.getStyler().setYAxisMax(60.0);
      BufferedImage buf;
      buf = new BufferedImage(1024, 768, BufferedImage.TYPE_INT_RGB);
      xychart.paint(buf.createGraphics(), 1024, 768);
    }
  }
}
