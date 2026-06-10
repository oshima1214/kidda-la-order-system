package jointest.uc01;

import java.util.ArrayList;

import dao.CustomerSearchDBAccess;
import model.Customer;

public class JoinTest_OrderControl02 {

    public static void main(String[] args) {
        try {
            CustomerSearchDBAccess dao = new CustomerSearchDBAccess();
            Customer sample = new Customer(1, "青木まゆみ", "アオキマユミ",
                    "09012345678", "東京都千代田区神田小川町1-1-1");

            System.out.println("結合テスト：CustomerSearchDBAccess → Customer");
            System.out.println("--------------------");

            System.out.println("[項番1 CustomerSearchDBAccess → Customer]");
            System.out.println("DAOクラス：" + dao.getClass().getName());
            System.out.println("Customerクラス：" + sample.getClass().getName());

            System.out.println("--------------------");

            System.out.println("[項番2 searchCustomerByTel()]");
            ArrayList<Customer> telList = dao.searchCustomerByTel("09012345678");
            for (Customer customer : telList) {
                System.out.println(customer.getCustId());
                System.out.println(customer.getCustName());
                System.out.println(customer.getKana());
                System.out.println(customer.getTel());
                System.out.println(customer.getAddress());
            }

            System.out.println("--------------------");

            System.out.println("[項番3 searchCustomerByKana()]");
            ArrayList<Customer> kanaList = dao.searchCustomerByKana("アオキ");
            for (Customer customer : kanaList) {
                System.out.println(customer.getCustId());
                System.out.println(customer.getCustName());
                System.out.println(customer.getKana());
                System.out.println(customer.getTel());
                System.out.println(customer.getAddress());
            }

            System.out.println("--------------------");

            System.out.println("[項番4 searchCustomer()]");
            ArrayList<Customer> customerList = dao.searchCustomer("09012345678", "アオキ");
            for (Customer customer : customerList) {
                System.out.println(customer.getCustId());
                System.out.println(customer.getCustName());
                System.out.println(customer.getKana());
                System.out.println(customer.getTel());
                System.out.println(customer.getAddress());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
