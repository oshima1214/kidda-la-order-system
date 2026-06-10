/**
 * クラス名：	OrderControlUtility
 * 概要　　：	注文管理用ユーティリティ
 * 作成者名：
 * 作成日　：
 * 修正者名：
 * 修正日　：
 */
package model;
import java.util.ArrayList;
import java.util.Calendar;
public class OrderControlUtility {
	
	public static String[][] customerToArray(ArrayList<Customer> list) {

		int listSize = list.size();
		String[][] tableData = new String[listSize][4];

		for(int i = 0; i < listSize; i++) {

			Customer customer = list.get(i);
			tableData[i][0] = Integer.toString(customer.getCustId());
			tableData[i][1] = customer.getCustName();
			tableData[i][2] = customer.getKana();
			tableData[i][3] = customer.getAddress();
		}

		return tableData;
	}

	public static String[][] itemToArray(ArrayList<Item> list) {

		int listSize = list.size();
		String[][] tableData = new String[listSize][6];

		for(int i = 0; i < listSize; i++) {

			Item item = list.get(i);
			tableData[i][0] = item.getItemId();
			tableData[i][1] = item.getItemName();
			tableData[i][2] = item.getSize();
			tableData[i][3] = Integer.toString(0);
			tableData[i][4] = Integer.toString(item.getPrice());
			tableData[i][5] = Integer.toString(0);
		}

		return tableData;
	}

	public static String[][] orderToArray(ArrayList<OrderDetail> orderDetailList) {

		int listSize = orderDetailList.size();
		String[][] tableData = new String[listSize][7];

		for(int i = 0; i < listSize; i++) {

			OrderDetail orderDetail = orderDetailList.get(i);

			tableData[i][0] = Long.toString(orderDetail.getNo());

			Item item = orderDetail.getItem();
			tableData[i][1] = item.getItemId();
			tableData[i][2] = item.getItemName();
			tableData[i][3] = item.getSize();

			Tax tax = orderDetail.getTax();
			double rate = tax.getRate();

			int quantity = orderDetail.getQuantity();
			int price = item.getPrice();
			price = (int)(price * (1 + rate));

			tableData[i][4] = Integer.toString(quantity);
			tableData[i][5] = Integer.toString(price);

			int subTotal = quantity * price;
			tableData[i][6] = Integer.toString(subTotal);
		}

		return tableData;
	}
	
	public static String getDate() {
		Calendar cal = Calendar.getInstance();
	    int year = cal.get(Calendar.YEAR);
	    int month = cal.get(Calendar.MONTH) + 1;
	    int day = cal.get(Calendar.DATE);
	    return year + "-" + month + "-" + day;
	}
}