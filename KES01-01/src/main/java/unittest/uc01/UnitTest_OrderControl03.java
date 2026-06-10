package unittest.uc01;

import action.CustomerSearchAction;

public class UnitTest_OrderControl03 {

    public static void main(String[] args) {

        test("項番1",
            new String[] { "090 1234 5678", "" },
            new String[][] {
                { "1", "青木まゆみ", "アオキマユミ", "東京都千代田区神田小川町1-1-1" }
            }
        );

        test("項番2",
            new String[] { "", "ア オ キ" },
            new String[][] {
                { "1", "青木まゆみ", "アオキマユミ", "東京都千代田区神田小川町1-1-1" }
            }
        );

        test("項番3",
            new String[] { "09012345678", "アオキ" },
            new String[][] {
                { "1", "青木まゆみ", "アオキマユミ", "東京都千代田区神田小川町1-1-1" }
            }
        );

        test("項番4",
            new String[] { "", "" },
            new String[][] {
                { null, null }
            }
        );

        test("項番5",
            new String[] { "00000000000", "" },
            new String[][] {
                { null, null }
            }
        );

        test("項番6",
            new String[] { "", "アオヤマ" },
            new String[][] {
                { null, null }
            }
        );

        test("項番7",
            new String[] { "00000000000", "アオヤマ" },
            new String[][] {
                { null, null }
            }
        );
    }

    private static void test(String testName, String[] data, String[][] expected) {
        System.out.print("[" + testName + "] → ");

        try {
            CustomerSearchAction action = new CustomerSearchAction();
            String[][] result = action.execute(data);

            if (isSame(expected, result)) {
                System.out.println("成功");
            } else {
                System.out.println("失敗");
            }

        } catch (Exception e) {
            System.out.println("例外発生");
            e.printStackTrace();
        }
    }

    private static boolean isSame(String[][] expected, String[][] actual) {
        if (actual == null) {
            return false;
        }

        if (expected.length != actual.length) {
            return false;
        }

        for (int i = 0; i < expected.length; i++) {
            if (expected[i].length != actual[i].length) {
                return false;
            }

            for (int j = 0; j < expected[i].length; j++) {
                if (expected[i][j] == null && actual[i][j] == null) {
                    continue;
                }

                if (expected[i][j] == null || !expected[i][j].equals(actual[i][j])) {
                    return false;
                }
            }
        }

        return true;
    }
}