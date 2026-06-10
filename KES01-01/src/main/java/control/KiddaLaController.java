/**
 * クラス名：	KiddaLaController
 * 概要　　：	KIDDA-LA業務システムを制御する。
 * 作成者名：	丸山
 * 作成日　：	20XX/06/20
 * 修正者名：
 * 修正日　：
 */
package control;

import java.io.IOException;
import java.util.ArrayList;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import action.CustomerModifyAction;
import action.CustomerSearchAction;
import action.DeliveryCompleteAction;
import action.DeliveryConfirmAction;
import action.ItemMenuDisplayAction;
import action.OrderInputDisplayAction;
import action.OrderRegisterAction;
import model.Customer;
import model.Item;
import model.OrderControlUtility;
import model.OrderDetail;

@WebServlet("/KiddaLaController")
public class KiddaLaController extends HttpServlet {
	// GETリクエスト処理
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// doPostメソッドの呼出し
		doPost(request, response);
	}

	// POSTリクエスト処理
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// リクエスト情報に対する文字コードの設定する
		request.setCharacterEncoding("UTF-8");
		// 各画面から送信されるリクエストパラメータ"command"の値を取得する
		String command = request.getParameter("command");
		// リクエストパラメータ"command"の値がない場合
		if (command == null || command.isEmpty()) {
			// 値を"MainMenu"にする
			command = "MainMenu";
		}
		// 次画面用の変数
		String nextPage = null;
		// セッションオブジェクト格納用変数
		HttpSession session = null;
		// リクエストパラメータ"command"の値に対応した処理を実行する
		switch (command) {
		case "MainMenu":
			// 次画面に"MainMenu.jsp"を設定する
			nextPage = "MainMenu.jsp";
			break;
		case "CustomerSearchDisplay":
			// 次画面に"CustomerSearch.jsp"を設定する
			nextPage = "CustomerSearch.jsp";
			// セッションオブジェクトを取得する（ない場合はnullが返る）
			session = request.getSession(false);
			// セッションオブジェクトがある場合
			if (session != null) {
				// セッションオブジェクトを破棄する
				session.invalidate();
			}
			break;
		case "CustomerSearch":
			try {
				// 次画面に"CustomerSearch.jsp"を設定する
				nextPage = "CustomerSearch.jsp";
				// リクエストパラメータ"tel"の値を取得する
				String paramTel = request.getParameter("tel");
				// リクエストパラメータ"name"の値を取得する
				String paramName = request.getParameter("name");
				// リクエストパラメータの値を配列にする
				String[] data = { paramTel, paramName };
				// CustomerSearchActionクラスのインスタンスを生成する
				CustomerSearchAction customerSearchAction = new CustomerSearchAction();
				// executeメソッドに配列にしたリクエストパラメータを渡し，結果として顧客情報の二次元配列を受け取る
				String[][] customerData = customerSearchAction.execute(data);
				// セッションオブジェクトを取得する（ない場合は生成する）
				session = request.getSession();
				// 顧客情報の二次元配列があり要素数が０ではない場合
				if (customerData != null && customerData.length != 0) {
					// セッションオブジェクト（スコープ）に顧客情報の二次元配列を登録する
					session.setAttribute("customerData", customerData);
				// 顧客情報がないか要素数が０の場合
				} else {
					// セッションオブジェクト（スコープ）の顧客情報の二次元配列を削除する
					session.removeAttribute("customerData");
					// リクエストオブジェクト（スコープ）にエラーメッセージを登録する
					request.setAttribute(
							"errorMsg", "一致する情報は見つかりませんでした。");
				}
			} catch (Exception e) {
				// リクエストオブジェクト（スコープ）にエラーメッセージを登録する
				request.setAttribute(
						"errorMsg", e.getMessage());
				// 例外をキャッチした場合は次画面に"Error.jsp"を設定する
				nextPage = "Error.jsp";
			}
			break;
		case "OrderInputDisplay":
			try {
				// 次画面に"OrderInput.jsp"を設定する
				nextPage = "OrderInput.jsp";
				// リクエストパラメータ"custId"の値を取得する
				String custId = request.getParameter("custId");
				// セッションオブジェクトを取得する（ない場合は生成する）
				session = request.getSession();
				// OrderInputDisplayActionクラスのインスタンスを生成する
				OrderInputDisplayAction orderInputDisplayAction = new OrderInputDisplayAction();
				// executeメソッドの取得したリクエストパラメータを渡し，結果として顧客情報を受け取る
				Customer customer = orderInputDisplayAction.execute(custId);
				// セッションオブジェクト（スコープ）に顧客情報を登録する
				session.setAttribute("customer", customer);
				// ItemMenuDisplayActionクラスのインスタンスを生成する
				ItemMenuDisplayAction itemMenuDisplayAction = new ItemMenuDisplayAction();
				// executeメソッドを呼び出し，結果として商品情報の二次元配列を受け取る
				String[][] itemData = itemMenuDisplayAction.execute();
				// セッションオブジェクト（スコープ）に商品情報の二次元配列を登録する
				session.setAttribute("itemData", itemData);
			} catch (Exception e) {
				// リクエストオブジェクト（スコープ）にエラーメッセージを登録する
				request.setAttribute(
						"errorMsg", e.getMessage());
				// 例外をキャッチした場合は次画面に"Error.jsp"を設定する
				nextPage = "Error.jsp";
			}
			break;
		case "OrderRegister":
			try {
				// 次画面に"DeliveryConfirm.jsp"を設定する
				nextPage = "DeliveryConfirm.jsp";
				// セッションオブジェクトを取得する
				session = request.getSession();
				// セッションスコープから顧客情報を取得する
				Customer customer = (Customer)session.getAttribute("customer");
				// セッションスコープから商品情報の二次元配列を取得する
				String[][] itemData = (String[][])session.getAttribute("itemData");
				// リクエストパラメータ"quantity"の値を配列で取得する
				String[] aryQuantity = request.getParameterValues("quantity");
				// 注文明細を格納するするためにArrayListクラスのインスタンスを生成する
				ArrayList<OrderDetail> orderDetailList = new ArrayList<OrderDetail>();
				// 商品情報の二次元配列の行数分繰り返す
				for(int i = 0; i < itemData.length; i++) {
					// 配列要素aryQuantity[i]の値（数量）をint型に変換する
					int quantity = Integer.parseInt(aryQuantity[i]);
					// int型に変換した値（数量）が１以上の場合
					if(quantity >= 1) {
						// 配列要素itemData[i][4]の値（価格）の","を""に置き換える
						String strPrice = itemData[i][4].replaceAll(",", "");
						// 変数strPriceの値（価格）をint型に変換する
						int price = Integer.parseInt(strPrice);
						// Itemクラスのインスタンスを生成する
						Item item = new Item(itemData[i][0], itemData[i][1], itemData[i][2], price);
						// 現在の日付を取得する
						String orderDate = OrderControlUtility.getDate();
						// OrderDetailクラスのインスタンスを生成する
						OrderDetail orderDetail = 
								new OrderDetail(0, customer, item, orderDate, quantity, null, 1);
						// ArrayListオブジェクトにOrderDetailオブジェクトを追加する
						orderDetailList.add(orderDetail);
					}
				}
				// ArrayListオブジェクトの要素数が１以上の場合
				if(orderDetailList.size() >= 1) {
					// OrderRegisterActionクラスのインスタンスを生成する
					OrderRegisterAction orderRegisterAction = new OrderRegisterAction();
					// executeメソッドに注文明細を格納したArrayListオブジェクトを渡し，結果として注文明細を格納したArrayListオブジェクトを受け取る
					orderDetailList = orderRegisterAction.execute(orderDetailList);
					// リクエストオブジェクト（スコープ）に注文明細を格納したArrayListオブジェクトを登録する
					request.setAttribute("orderDetailList", orderDetailList);
					// リクエストオブジェクト（スコープ）にメッセージフラグを登録する
					request.setAttribute("msgFlag", "registered");
				// ArrayListオブジェクトの要素数が１以上でない場合
				} else {
					// リクエストオブジェクト（スコープ）にエラーメッセージを登録する
					request.setAttribute("errorMsg", "商品数量を入力してください。");
					// 次画面に"OrderInput.jsp"を設定する
					nextPage = "OrderInput.jsp";
				}
			}catch(Exception e) {
				// リクエストオブジェクト（スコープ）にエラーメッセージを登録する
				request.setAttribute(
						"errorMsg", e.getMessage());
				// 例外をキャッチした場合は次画面に"Error.jsp"を設定する
				nextPage = "Error.jsp";
			}
			break;
		case "DeliveryConfirm":
			try {
				// 次画面に"DeliveryConfirm.jsp"を設定する
				nextPage = "DeliveryConfirm.jsp";
				// セッションオブジェクトを取得する
				session = request.getSession();
				// セッションスコープから顧客情報を取得する
				Customer customer = (Customer)session.getAttribute("customer");
				// 顧客情報から顧客IDを取得する
				int custId = customer.getCustId();
				// DeliveryConfirmActionクラスのインスタンスを生成する
				DeliveryConfirmAction deliveryConfirmAction = new DeliveryConfirmAction();
				// executeメソッドに顧客IDを渡し，結果として注文明細を格納したArrayListオブジェクトを受け取る
				ArrayList<OrderDetail> orderDetailList = deliveryConfirmAction.execute(custId);
				// ArrayListオブジェクトの要素数が１以上の場合
				if(orderDetailList.size() >= 1) {
					// リクエストオブジェクト（スコープ）に注文明細を格納したArrayListオブジェクトを登録する
					request.setAttribute("orderDetailList", orderDetailList);
					// リクエストオブジェクト（スコープ）にメッセージフラグを登録する
					request.setAttribute("msgFlag", "confirm");
				// ArrayListオブジェクトの要素数が１以上でない場合
				} else {
					// リクエストオブジェクト（スコープ）にメッセージフラグを登録する
					request.setAttribute("msgFlag", "noList");
					// 次画面に"OrderInput.jsp"を設定する
					nextPage = "OrderInput.jsp";
				}
			}catch(Exception e) {
				// リクエストオブジェクト（スコープ）にエラーメッセージを登録する
				request.setAttribute(
						"errorMsg", e.getMessage());
				// 例外をキャッチした場合は次画面に"Error.jsp"を設定する
				nextPage = "Error.jsp";
			}
			break;
		case "DeliveryComplete":
			try {
				// 次画面に"DeliveryConfirm.jsp"を設定する
				nextPage = "DeliveryConfirm.jsp";
				// セッションオブジェクトを取得する
				session = request.getSession();
				// セッションスコープから顧客情報を取得する
				Customer customer = (Customer)session.getAttribute("customer");
				// 顧客情報から顧客IDを取得する
				int custId = customer.getCustId();
				// DeliveryCompleteActionクラスのインスタンスを生成する
				DeliveryCompleteAction deliveryCompleteAction = new DeliveryCompleteAction();
				// executeメソッドに顧客IDを渡し，結果としてint型の値（登録した行数：１以上）を取得する
				int result = deliveryCompleteAction.execute(custId);
				// 変数resultの値が１以上の場合
				if(result >= 1) {
					// リクエストオブジェクト（スコープ）にメッセージフラグを登録する
					request.setAttribute("msgFlag", "completed");
				// 変数resultの値が１以上でない場合
				} else {
					// リクエストオブジェクト（スコープ）にエラーメッセージを登録する
					request.setAttribute("errorMsg", "配達完了処理に失敗しました！");
				}
			}catch(Exception e) {
				// リクエストオブジェクト（スコープ）にエラーメッセージを登録する
				request.setAttribute(
						"errorMsg", e.getMessage());
				// 例外をキャッチした場合は次画面に"Error.jsp"を設定する
				nextPage = "Error.jsp";
			}
			break;
		case "CustomerModify":
			try {
				// 次画面に"OrderInput.jsp"を設定する
				nextPage = "OrderInput.jsp";
				// リクエストパラメータ"custName"の値を取得する
				String custName = request.getParameter("custName");
				// リクエストパラメータ"kana"の値を取得する
				String kana = request.getParameter("kana");
				// リクエストパラメータ"tel"の値を取得する
				String tel = request.getParameter("tel");
				// リクエストパラメータ"address"の値を取得する
				String address = request.getParameter("address");
				// セッションオブジェクトを取得する
				session = request.getSession();
				// セッションスコープから顧客情報を取得する
				Customer customer = (Customer) session.getAttribute("customer");
				// 顧客情報から顧客IDを取得する
				int custId = customer.getCustId();
				// 新しいCustomerクラスのインスタンス（顧客情報）を生成する
				Customer newCustomer = new Customer(custId, custName, kana, tel, address);
				// CustomerModifyActionクラスのインスタンスを生成する
				CustomerModifyAction customerModifyAction = new CustomerModifyAction();
				// executeメソッドに新しいCustomerクラスのインスタンス（顧客情報）を渡し，結果としてint型の値（更新した行数：１）を取得する
				int count = customerModifyAction.execute(newCustomer);
				if(count == 1) {
					// セッションオブジェクト（スコープ）に新しい顧客情報を登録する
					session.setAttribute("customer", newCustomer);
					// リクエストオブジェクト（スコープ）にメッセージフラグを登録する
					request.setAttribute("msgFlag", "modified");
				}
			} catch(Exception e) {
				// リクエストオブジェクト（スコープ）にエラーメッセージを登録する
				request.setAttribute(
						"errorMsg", e.getMessage());
				// 例外をキャッチした場合は次画面に"Error.jsp"を設定する
				nextPage = "Error.jsp";
			}
			break;
		}
		// 次のページへの転送
		RequestDispatcher rd = request.getRequestDispatcher(nextPage);
		rd.forward(request, response);
	}
}
