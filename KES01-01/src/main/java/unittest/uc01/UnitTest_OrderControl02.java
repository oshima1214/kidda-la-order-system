package unittest.uc01;

import java.util.ArrayList;

import dao.CustomerSearchDBAccess;
import model.Customer;

public class UnitTest_OrderControl02 {
    public static void main(String[] args) {
        CustomerSearchDBAccess dao = new CustomerSearchDBAccess();
        
        try {
            ArrayList<Customer> list1 = dao.searchCustomerByTel(
                                           "08012345678");
            System.out.println(list1.size());
            System.out.println(list1.get(0).getCustId());
            System.out.println("--------------------");
            
            ArrayList<Customer> list2 = dao.searchCustomerByTel(
                                           "0120345678");
            System.out.println(list2.size());
            System.out.println("--------------------");
            
            ArrayList<Customer> list3 = dao.searchCustomerByTel("");
            System.out.println(list3.size());
            System.out.println("--------------------");
            
            ArrayList<Customer> list4 = dao.searchCustomerByTel(null);
            System.out.println(list4.size());
            System.out.println("--------------------");
            
            ArrayList<Customer> list5 = dao.searchCustomerByTel(
                                            "08012345678");
            System.out.println("テスト失敗");
        }catch(Exception e){
            e.printStackTrace();
            System.out.println(e.getMessage());
            
        }
    }

}
