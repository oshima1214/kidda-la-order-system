package jointest.uc01;

import action.CustomerSearchAction;
import dao.CustomerSearchDBAccess;

public class JoinTest_OrderControl03 {

    public static void main(String[] args) {
        try {
            CustomerSearchAction action = new CustomerSearchAction();

            System.out.println("結合テスト：CustomerSearchAction → CustomerSearchDBAccess");
            System.out.println("--------------------");

            System.out.println("[項番1 CustomerSearchAction → CustomerSearchDBAccess]");
            CustomerSearchDBAccess dao = new CustomerSearchDBAccess();
            System.out.println("Actionクラス：" + action.getClass().getName());
            System.out.println("DAOクラス：" + dao.getClass().getName());

            System.out.println("--------------------");

            System.out.println("[項番2 電話番号のみで顧客検索]");
            String[] data1 = { "09023456781", "" };
            String[][] result1 = action.execute(data1);
            printResult(result1);

            System.out.println("--------------------");

            System.out.println("[項番3 カナのみで顧客検索]");
            String[] data2 = { "", "カルロスサンタナ" };
            String[][] result2 = action.execute(data2);
            printResult(result2);

            System.out.println("--------------------");

            System.out.println("[項番4 電話番号とカナで顧客検索]");
            String[] data3 = { " 09034567812", "シバタリュウイチ" };
            String[][] result3 = action.execute(data3);
            printResult(result3);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void printResult(String[][] result) {
        for (int i = 0; i < result.length; i++) {
            System.out.println(result[i][0]);
            System.out.println(result[i][1]);
            System.out.println(result[i][2]);
            System.out.println(result[i][3]);
        }
    }
}
