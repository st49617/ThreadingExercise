package threadingjava.math;

import java.util.ArrayList;
import java.util.List;

public class Polynome {

    private List<Complex> coefficients;

    public Polynome() {
        coefficients = new ArrayList<>();
    }

    public List<Complex> getCoefficients() {
        return coefficients;
    }

    public Polynome derive() {
        Polynome p = new Polynome();

        for (int i = 1; i < coefficients.size(); i++) {
            p.coefficients.add(coefficients.get(i).multiply(new Complex(i, 0)));
        }

        return p;
    }

    public Complex eval(Complex x) {
        Complex s = new Complex(0, 0);

        for (int i = 0; i < coefficients.size(); i++) {
            Complex coef = coefficients.get(i);
            Complex bx = x;
            int power = i;

            if (i > 0) {
                for (int j = 0; j < power - 1; j++) {
                    bx = bx.multiply(x);
                }

                coef = coef.multiply(bx);
            }

            s = s.add(coef);
        }

        return s;
    }

}
