package action;

import java.util.ArrayList;

import dao.CustomerSearchDBAccess;
import model.Customer;
import model.OrderControlUtility;

public class CustomerSearchAction {

	public String[][] execute(String[] data) throws Exception{
	    String tel = data[0];
	    String kana = data[1];
	    
		data[0] = data[0].replace(" ", "");
		data[0] = data[0].replace("　", "");
		data[1] = data[1].replace(" ", "");
		data[1] = data[1].replace("　", "");
		
		CustomerSearchDBAccess dao = new CustomerSearchDBAccess();
        ArrayList<Customer> customerList = new ArrayList<Customer>();

        if (!data[0].equals("") && data[1].equals("")) {
            customerList = dao.searchCustomerByTel(data[0]);

        } else if (data[0].equals("") && !data[1].equals("")) {
            customerList = dao.searchCustomerByKana(data[1]);

        } else if (!data[0].equals("") && !data[1].equals("")) {
            customerList = dao.searchCustomer(data[0], data[1]);
        }
        
        if(customerList.isEmpty()) {
            return new String[0][0];
        }
        return OrderControlUtility.customerToArray(customerList);
	}
}


