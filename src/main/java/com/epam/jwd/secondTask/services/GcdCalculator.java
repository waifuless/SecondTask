package com.epam.jwd.secondTask.services;

//GCD = greatest common divisor
public class GcdCalculator {
    public static long findGcd(long a, long b) {
        while (b != 0) {
            long tmp = a % b;
            a = b;
            b = tmp;
        }
        return a;
    }
}
