package com.eugene.element;

import com.eugene.math.Complex;
import com.eugene.math.Fft;

public class TestFft {

	/**
	 * @param args
	 */
    public static void main(String[] args) {
        int N = 4;
        Complex[] x = new Complex[N];

        // original data
        for (int i = 0; i < N; i++) {
            x[i] = new Complex(i, 0);
            x[i] = new Complex(-2*Math.random() + 1, 0);
        }
        Fft.show(x, "x");

        // FFT of original data
        Complex[] y = Fft.fft(x);
        Fft.show(y, "y = fft(x)");

        // take inverse FFT
        Complex[] z = Fft.ifft(y);
        Fft.show(z, "z = ifft(y)");

        // circular convolution of x with itself
        Complex[] c = Fft.cconvolve(x, x);
        Fft.show(c, "c = cconvolve(x, x)");

        // linear convolution of x with itself
        Complex[] d = Fft.convolve(x, x);
        Fft.show(d, "d = convolve(x, x)");
    }
}
