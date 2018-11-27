package threadingjava.math;

public class Complex {

    private double re;
    private double im;

    public Complex() {
    }

    public Complex(double re, double im) {
        this.re = re;
        this.im = im;
    }

    public double getRe() {
        return re;
    }

    public void setRe(double re) {
        this.re = re;
    }

    public double getIm() {
        return im;
    }

    public void setIm(double im) {
        this.im = im;
    }

    public Complex multiply(Complex op) {
        return new Complex(
                re * op.re - im * op.im,
                re * op.im + im * op.re
        );
    }

    public Complex add(Complex op) {
        return new Complex(
                re + op.re,
                im + op.im
        );
    }

    public Complex subtract(Complex op) {
        return new Complex(
                re - op.re,
                im - op.im
        );
    }

    public Complex divide(Complex op) {
        Complex tmp = this.multiply(new Complex(op.re, -op.im));
        double tmp2 = op.re * op.re + op.im * op.im;

        return new Complex(
                tmp.re / tmp2,
                tmp.im / tmp2
        );
    }

    @Override
    public String toString() {
        return "Complex{" + "re=" + re + ", im=" + im + '}';
    }

}
