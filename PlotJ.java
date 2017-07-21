import javax.swing.*;
import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;
import org.jfree.data.xy.*;
import org.jfree.chart.*;
import org.jfree.chart.plot.*;

public class PlotJ {
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

    for (int i = 0; i < 500; i++)
    {
        XYSeries series1 = new XYSeries("Simulated data");
        XYSeries series2 = new XYSeries("Measured data");
        XYSeriesCollection dataset;
        XYPlot xyplot;
        JFreeChart chart;
        for(int j=0; j<xpoints.length; j++) {
            series1.add(xpoints[j],ypoints[j]);
            series2.add(xpoints[j],ypoints2[j]);
        }
        dataset = new XYSeriesCollection(series1);
        dataset.addSeries(series2);
        chart = ChartFactory.createXYLineChart("XRR","degrees","dB",dataset,PlotOrientation.VERTICAL,true,true,false);
        xyplot = chart.getXYPlot();
        /*xyplot.getDomainAxis().setAutoRange(false);
        xyplot.getDomainAxis().setRange(0,5);*/
        xyplot.getRangeAxis().setAutoRange(false);
        xyplot.getRangeAxis().setRange(-5.0,60.0);
        chart.setAntiAlias(false); /* this is faster */
        chart.createBufferedImage(1024, 768);
    }
    long start = System.nanoTime();
    for (int i = 0; i < 500; i++)
    {
        XYSeries series1 = new XYSeries("Simulated data");
        XYSeries series2 = new XYSeries("Measured data");
        XYSeriesCollection dataset;
        XYPlot xyplot;
        JFreeChart chart;
        for(int j=0; j<xpoints.length; j++) {
            series1.add(xpoints[j],ypoints[j]);
            series2.add(xpoints[j],ypoints2[j]);
        }
        dataset = new XYSeriesCollection(series1);
        dataset.addSeries(series2);
        chart = ChartFactory.createXYLineChart("XRR","degrees","dB",dataset,PlotOrientation.VERTICAL,true,true,false);
        xyplot = chart.getXYPlot();
        /*xyplot.getDomainAxis().setAutoRange(false);
        xyplot.getDomainAxis().setRange(0,5);*/
        xyplot.getRangeAxis().setAutoRange(false);
        xyplot.getRangeAxis().setRange(-5.0,60.0);
        chart.setAntiAlias(false); /* this is faster */
        chart.createBufferedImage(1024, 768);
    }
    long end = System.nanoTime();
    System.out.println((end - start)/1000.0/1000.0/500 + " ms per chart");
  }
}
