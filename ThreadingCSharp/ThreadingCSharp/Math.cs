using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ThreadingCSharp
{
    class Polynome
    {
        public List<ComplexNumber> Coefficients { get; set; }

        public Polynome()
        {
            Coefficients = new List<ComplexNumber>();
        }

        public Polynome Derive()
        {
            Polynome p = new Polynome();
            for (int i = 1; i < Coefficients.Count; i++)
            {
                p.Coefficients.Add(Coefficients[i].Multiply(new ComplexNumber() { Re = i }));
            }

            return p;
        }

        public ComplexNumber Eval(ComplexNumber x)
        {
            ComplexNumber s = ComplexNumber.Zero;
            for (int i = 0; i < Coefficients.Count; i++)
            {
                ComplexNumber coef = Coefficients[i];
                ComplexNumber bx = x;
                int power = i;

                if (i > 0)
                {
                    for (int j = 0; j < power - 1; j++)
                        bx = bx.Multiply(x);

                    coef = coef.Multiply(bx);
                }

                s = s.Add(coef);
            }

            return s;
        }

        public override string ToString()
        {
            string s = "";
            for (int i = 0; i < Coefficients.Count; i++)
            {
                s += Coefficients[i];
                if (i > 0)
                {
                    for (int j = 0; j < i; j++)
                    {
                        s += "x";
                    }
                }
                s += " + ";
            }
            return s;
        }
    }

    class ComplexNumber
    {
        public double Re { get; set; }
        public double Im { get; set; }

        public readonly static ComplexNumber Zero = new ComplexNumber()
        {
            Re = 0,
            Im = 0
        };

        public ComplexNumber Multiply(ComplexNumber b)
        {
            ComplexNumber a = this;
            // aRe*bRe + aRe*bIm*i + aIm*bRe*i + aIm*bIm*i*i
            return new ComplexNumber()
            {
                Re = a.Re * b.Re - a.Im * b.Im,
                Im = (a.Re * b.Im + a.Im * b.Re)
            };
        }

        public ComplexNumber Add(ComplexNumber b)
        {
            ComplexNumber a = this;
            return new ComplexNumber()
            {
                Re = a.Re + b.Re,
                Im = a.Im + b.Im
            };
        }
        public ComplexNumber Subtract(ComplexNumber b)
        {
            ComplexNumber a = this;
            return new ComplexNumber()
            {
                Re = a.Re - b.Re,
                Im = a.Im - b.Im
            };
        }

        public override string ToString()
        {
            return $"({Re} + {Im}i)";
        }

        internal ComplexNumber Divide(ComplexNumber b)
        {
            // (aRe + aIm*i) / (bRe + bIm*i)
            // ((aRe + aIm*i) * (bRe - bIm*i)) / ((bRe + bIm*i) * (bRe - bIm*i))
            //  bRe*bRe - bIm*bIm*i*i
            var tmp = this.Multiply(new ComplexNumber() { Re = b.Re, Im = -b.Im });
            var tmp2 = b.Re * b.Re + b.Im * b.Im;

            return new ComplexNumber()
            {
                Re = tmp.Re / tmp2,
                Im = (tmp.Im / tmp2)
            };
        }
    }
}
