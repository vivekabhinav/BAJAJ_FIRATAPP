package com.example.bajaj_firatapp;

import junit.framework.TestCase;

public class CalculatorTest extends TestCase {

    public void testAdd() {
        int expexcted =90;
        int actual = Calculator.add(40, 50);
    }

    public void testmul() {
        int exp =12;
        int act = Calculator.mul(4,3);

    }
    public void testadd1() {
        int ex = 70;
        int ac = Calculator.add1(20,20,30);
    }
}