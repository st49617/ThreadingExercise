package threadingjava;

import threadingjava.math.Polynome;
import threadingjava.math.Complex;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.WritableImage;

public class NewtonSolver {

    private double xmin = -1.5;
    private double xmax = 1.5;
    private double ymin = -1.5;
    private double ymax = 1.5;

    private int width = 600;
    private int height = 600;

    private double xstep = (xmax - xmin) / width;
    private double ystep = (ymax - ymin) / height;

    private List<Complex> roots = new ArrayList<>();
    private Polynome p = new Polynome();
    private Polynome pd;

    private long startTime;
    private long stopTime;

    private Color[] colours = new Color[]{
        Color.red, Color.blue, Color.green, Color.yellow, Color.orange, Color.magenta, Color.pink, Color.cyan, Color.gray
    };

    private BufferedImage image;

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public NewtonSolver() {
        List<Complex> coef = p.getCoefficients();
        coef.add(new Complex(1, 0));
        coef.add(new Complex(0, 0));
        coef.add(new Complex(0, 0));
        coef.add(new Complex(1, 0));

        pd = p.derive();

        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

    }

    public BufferedImage getImage() {
        return image;
    }
    
    public WritableImage getImageFX() {
        return SwingFXUtils.toFXImage(getImage(), null);
    }

    public void startTimeMeasurement() {
        startTime = System.currentTimeMillis();
    }

    public void stopTimeMeasurement() {
        stopTime = System.currentTimeMillis();
    }

    public double getDuration() {
        double tmp = (stopTime - startTime) / 1000.0;
        return Math.round(tmp * 1000) / 1000.0;
    }

    public void solve() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                solvePoint(j, i);
            }
        }
    }

    public void solveRow(int row) {
        int i = row;
        for (int j = 0; j < width; j++) {
            solvePoint(j, i);
        }
    }

    private void solvePoint(int j, int i) {
        // find "world" coordinates of pixel
        double x = xmin + j * xstep;
        double y = ymin + i * ystep;
        Complex ox = new Complex(x, y);
        if (ox.getRe() == 0) {
            ox.setRe(0.0001);
        }
        if (ox.getIm() == 0) {
            ox.setIm(0.0001);
        }
        
        //Console.WriteLine(ox);
        // find solution of equation using newton's iteration
        float it = 0;
        for (int q = 0; q < 30; q++) {
            Complex diff = p.eval(ox).divide(pd.eval(ox));
            ox = ox.subtract(diff);

            //Console.WriteLine($"{q} {ox} -({diff})");
            if (Math.pow(diff.getRe(), 2) + Math.pow(diff.getIm(), 2) >= 0.5) {
                q--;
            }
            it++;
        }
        
        //Console.ReadKey();
        // find solution root number
        boolean known = false;
        int id = 0;
        for (int w = 0; w < roots.size(); w++) {
            if (Math.pow(ox.getRe() - roots.get(w).getRe(), 2) + Math.pow(ox.getIm() - roots.get(w).getIm(), 2) <= 0.01) {
                known = true;
                id = w;
            }
        }
        if (!known) {
            roots.add(ox);
            id = roots.size();
            //maxid = id + 1;
        }
        
        Color vv = colours[id % colours.length];
        //vv = Color.FromArgb(vv.R, vv.G, vv.B);
        vv = new Color(Math.min(Math.max(0, vv.getRed() - (int) it * 4), 255), Math.min(Math.max(0, vv.getGreen() - (int) it * 4), 255), Math.min(Math.max(0, vv.getBlue() - (int) it * 4), 255));
        //vv = Math.Min(Math.Max(0, vv), 255);
        
        // TODO: thread safe access??
        image.setRGB(j, i, vv.getRGB());
    }
}
