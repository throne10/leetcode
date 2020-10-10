package com.justsafe.core.leetcode;

import java.util.ArrayList;

public class Q1626Calculator {
    private ArrayList<Ops> NumSs = new ArrayList<>();

    public int calculate(String s) {
        formatS(s);
        for (int i = 0; i < NumSs.size(); i++) {
            Ops cc = NumSs.get(i);
            if (cc.isOp()) {
                boolean need = false;
                if (i - 2 > 0) {
                    Ops c0 = NumSs.get(i - 2);
                    if (c0.getV().equals("-")) {
                        need = true;
                    }
                }
                Ops c1 = NumSs.get(i - 1);
                Ops c2 = NumSs.get(i + 1);
                int v1 = c1.getNum();
                int v2 = c2.getNum();
                if (cc.getV().equals("*")) {

                    c1.change(false, "", 0);
                    c2.change(false, "", v1 * v2);
                    cc.change(true, need ? "-" : "+", 0);
                }
                if (cc.getV().equals("/")) {

                    c1.change(false, "", 0);
                    c2.change(false, "", v1 / v2);
                    cc.change(true, need ? "-" : "+", 0);
                }
            }

        }

        int sum = NumSs.get(0).getNum();
        for (int i = 0; i < NumSs.size(); i++) {
            Ops cc = NumSs.get(i);
            if (cc.isOp()) {
                Ops c2 = NumSs.get(i + 1);
                if (cc.getV().equals("-")) {
                    sum = sum - c2.getNum();
                }
                if (cc.getV().equals("+")) {
                    sum = sum + c2.getNum();
                }
            }
        }
        return sum;
    }

    private void formatS(String newS) {
        char[] c = newS.toCharArray();
        StringBuffer s = new StringBuffer();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == ' ') {
                continue;
            }
            if (c[i] == '*' || c[i] == '/' || c[i] == '+' || c[i] == '-') {
                NumSs.add(new Ops(false, "", Integer.parseInt(s.toString())));
                s = new StringBuffer();
                NumSs.add(new Ops(true, c[i] + "", 0));
            } else {
                s = s.append(c[i]);
            }
        }
        NumSs.add(new Ops(false, "", Integer.parseInt(s.toString())));

    }


    private class Ops {
        boolean isOp;

        String v;

        int num;

        public Ops(boolean isOp, String v, int num) {
            this.isOp = isOp;
            this.v = v;
            this.num = num;
        }

        public void change(boolean isOp, String v, int num) {
            this.isOp = isOp;
            this.v = v;
            this.num = num;
        }

        public int getNum() {
            return num;
        }


        public boolean isOp() {
            return isOp;
        }


        public String getV() {
            return v;
        }

    }
}

