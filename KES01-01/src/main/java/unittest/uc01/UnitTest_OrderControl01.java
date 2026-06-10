package unittest.uc01;

import model.Customer;

public class UnitTest_OrderControl01 {
    static class TestCase {
        String testName;
        Runnable test;

        TestCase(String name, Runnable test) {
            this.testName = name;
            this.test = test;
        }
    }

    public static void main(String[] args) {

        Customer[] c = { null };

        TestCase[] testCases = {

            new TestCase("項番1", () -> {
                c[0] = new Customer(
                    1,
                    "青木まゆみ",
                    "アオキマユミ",
                    "09012345678",
                    "東京都千代田区神田小川町1-1-1"
                );
            }),
            new TestCase("項番2", () -> {
                c[0] = new Customer(
                    0,
                    null,
                    null,
                    null,
                    null
                );
            }),
            new TestCase("項番3", () -> {        // setCustId
                c[0].setCustId(2);
            }),
            new TestCase("項番5", () -> {        // getCustId
                int result = c[0].getCustId();
                assertEqual(2, result);
            }),
            new TestCase("項番4", () -> {        // setCustId
                c[0].setCustId(0);
            }),
            new TestCase("項番6", () -> {        // getCustId
                int result = c[0].getCustId();
                assertEqual(0, result);
            }),
            new TestCase("項番7", () -> {        // setCustName
                c[0].setCustName("青木まゆみ");
            }),
            new TestCase("項番9", () -> {        // getCustName
                String result = c[0].getCustName();
                assertEqual("青木まゆみ", result);
            }),
            new TestCase("項番8", () -> {        // setCustName
                c[0].setCustName(null);
            }),
            new TestCase("項番10", () -> {       // getCustName
                String result = c[0].getCustName();
                assertEqual(null, result);
            }),
            new TestCase("項番11", () -> {       // setKana
                c[0].setKana("アオキマユミ");
            }),
            new TestCase("項番13", () -> {       // getKana
                String result = c[0].getKana();
                assertEqual("アオキマユミ", result);
            }),
            new TestCase("項番12", () -> {       // setKana
                c[0].setKana(null);
            }),
            new TestCase("項番14", () -> {       // getKana
                String result = c[0].getKana();
                assertEqual(null, result);
            }),
            new TestCase("項番15", () -> {       // setTel
                c[0].setTel("09012345678");
            }),
            new TestCase("項番17", () -> {       // getTel
                String result = c[0].getTel();
                assertEqual("09012345678", result);
            }),
            new TestCase("項番16", () -> {       // setTel
                c[0].setTel(null);
            }),
            new TestCase("項番18", () -> {       // getTel
                String result = c[0].getTel();
                assertEqual(null, result);
            }),

            new TestCase("項番19", () -> {       // setAddress
                c[0].setAddress("東京都千代田区神田小川町1-1-1");
            }),
            new TestCase("項番21", () -> {       // getAddress
                String result = c[0].getAddress();
                assertEqual("東京都千代田区神田小川町1-1-1", result);
            }),
            new TestCase("項番20", () -> {       // setAddress
                c[0].setAddress(null);
            }),
            new TestCase("項番22", () -> {       // getAddress
                String result = c[0].getAddress();
                assertEqual(null, result);
            }),
        };

        // テスト実行
        for (TestCase tc : testCases) {
            System.out.print("[" + tc.testName + "] → ");
            try {
                tc.test.run();
                System.out.println("成功");
            } catch (AssertionError e) {
                System.out.println("失敗: " + e.getMessage());
            }
        }

    }

    static void assertEqual(int expected, int actual) {
        if (expected != actual) {
            throw new AssertionError("期待値:" + expected + " 実際:" + actual);
        }
    }

    static void assertEqual(String expected, String actual) {
        if (expected == null && actual == null) return;
        if (expected == null || !expected.equals(actual)) {
            throw new AssertionError("期待値:" + expected + " 実際:" + actual);
        }
    }

}
