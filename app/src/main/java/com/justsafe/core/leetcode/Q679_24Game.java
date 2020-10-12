package com.justsafe.core.leetcode;

import java.util.ArrayList;

public class Q679_24Game {
    ArrayList<String> s = new ArrayList();
    String[] op = new String[]{"+", "-", "*", "/"};
    int[] nums;

    public boolean judgePoint24(int[] nums) {
        this.nums = nums;
        addNext(true, "", 0);
        System.out.println(s);
        boolean re = false;
        Q1626Calculator mQ1626Calculator2 = new Q1626Calculator();
        for (String ss : s) {
            re = mQ1626Calculator2.calculate2(ss);
            if (re) {
                break;
            }
        }
        return re;
    }

    private boolean addNext(boolean b, String s1, int i) {
        boolean re = false;
        if (s1.length() >= 7) {
            String temp = s1;
            boolean re2 = true;
            for (int z = 0; z < nums.length; z++) {
                boolean re1 = temp.contains(nums[z] + "");
                if (re1) {
                    temp = temp.replaceFirst(nums[z] + "", "+");
                } else {
                    re2 = false;
                    break;
                }
            }
            if (re2) {
                s.add(s1);
            }
            return true;
        }
        if (b) {
            for (int z = 0; z < nums.length; z++) {
                if (s1.length() > i) {
                    s1 = s1.substring(0, i);
                }
                s1 = s1 + nums[z];
                b = !b;
                i++;
                re = addNext(b, s1, i);
                if (re) {
                    i--;
                    b = !b;
                }
            }
        } else {
            for (int z = 0; z < op.length; z++) {
                if (s1.length() > i) {
                    s1 = s1.substring(0, i);
                }
                s1 = s1 + op[z];
                b = !b;
                i++;
                re = addNext(b, s1, i);
                if (re) {
                    i--;
                    b = !b;
                }
            }
        }
        return re;

    }

    private class Q1626Calculator {
        private ArrayList<Ops> NumSs = new ArrayList<>();

        public boolean calculate2(String s) {
            int[][] s1 = new int[6][];
            s1[0] = new int[]{3, 5, 1};
            s1[1] = new int[]{3, 1, 5};
            s1[2] = new int[]{1, 3, 5};
            s1[3] = new int[]{1, 5, 3};
            s1[4] = new int[]{5, 1, 3};
            s1[5] = new int[]{5, 3, 1};
            NumSs = new ArrayList<>();
            formatS(s);
            for (int z = 0; z < s1.length; z++) {
                int[] s2 = s1[z];
                double sum = NumSs.get(s2[0] - 1).getNum();
                double sum2 = 0;
                int first = s2[0];
                if (z == 3 || z == 4) {
                    Ops cc = NumSs.get(s2[0]);
                    Ops c1 = NumSs.get(s2[0] - 1);
                    Ops c2 = NumSs.get(s2[0] + 1);
                    if (cc.getV().equals("-")) {
                        sum = c1.getNum() - c2.getNum();
                    }
                    if (cc.getV().equals("+")) {
                        sum = c1.getNum() + c2.getNum();
                    }
                    if (cc.getV().equals("*")) {
                        sum = c1.getNum() * c2.getNum();
                    }
                    if (cc.getV().equals("/")) {
                        sum = c1.getNum() / (double) c2.getNum();
                    }

                    cc = NumSs.get(s2[1]);
                    c1 = NumSs.get(s2[1] - 1);
                    c2 = NumSs.get(s2[1] + 1);
                    if (cc.getV().equals("-")) {
                        sum2 = c1.getNum() - c2.getNum();
                    }
                    if (cc.getV().equals("+")) {
                        sum2 = c1.getNum() + c2.getNum();
                    }
                    if (cc.getV().equals("*")) {
                        sum2 = c1.getNum() * c2.getNum();
                    }
                    if (cc.getV().equals("/")) {
                        sum2 = c1.getNum() / (double) c2.getNum();
                    }
                    cc = NumSs.get(s2[2]);
                    if (cc.getV().equals("-")) {
                        sum = sum - sum2;
                    }
                    if (cc.getV().equals("+")) {
                        sum = sum + sum2;
                    }
                    if (cc.getV().equals("*")) {
                        sum = sum * sum2;
                    }
                    if (cc.getV().equals("/")) {
                        if (sum2 != 0) {
                            sum = sum / (double) sum2;
                        }
                    }
                } else {
                    for (int i = 0; i < s2.length; i++) {
                        Ops cc = NumSs.get(s2[i]);
                        if (cc.isOp()) {
                            boolean re = false;
                            Ops c2;
                            if (first <= s2[i]) {
                                c2 = NumSs.get(s2[i] + 1);
                            } else {
                                c2 = NumSs.get(s2[i] - 1);
                                re = true;
                            }
                            if (cc.getV().equals("-")) {
                                if (re) {
                                    sum = c2.getNum() - sum;
                                } else {
                                    sum = sum - c2.getNum();
                                }
                            }
                            if (cc.getV().equals("+")) {
                                sum = sum + c2.getNum();
                            }
                            if (cc.getV().equals("*")) {
                                sum = sum * c2.getNum();
                            }
                            if (cc.getV().equals("/")) {
                                if (re) {
                                    if (sum != 0) {
                                        sum = c2.getNum() / (double) sum;
                                    }
                                } else {
                                    sum = sum / (double) c2.getNum();
                                }
                            }
                        }
                    }
                }
                if ((float) sum == 24) {
                    return true;
                }
            }
            return false;
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

}

