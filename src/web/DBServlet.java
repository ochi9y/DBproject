package web;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class DBServlet
 */
@WebServlet("/DBServlet")
public class DBServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DBServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());


		//Connection con = null;
		//接続文字列の構築
		/* ユーザ名 */
		String user = "train2018";
		/* パスワード */
		String pass = "train2018";

		/* サーバ名 */
		String servername = "localhost:3306";
		/* DB名 */
		String dbname = "new_schema";

		// ドライバーのロード
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}


		PreparedStatement stmt = null;
		ResultSet rset = null;
		String num1id = request.getParameter("id");
		int key = Integer.parseInt(num1id);


		try
		(       Connection con = DriverManager.getConnection(
				"jdbc:mysql://"
						+ servername
						+ "/"
						+ dbname,
						user,
						pass);
				)
		{
			/* Statementの作成 */
			stmt = con.prepareStatement
					(
							"select "
									+ "  KAIINNNO"
									+ "  ,NAME"
									+ "  ,REGISTDATE"
									+ " from "
									+ "   KAIINN "
									+ "where"
									+ "  KAIINNNO =? "
							);
			/* ｓｑｌ実行 */
			stmt.setInt (1, key);
			rset = stmt.executeQuery();

			/* 取得したデータを表示します。 */
			while (rset.next())
			{
				int id = rset.getInt(1);
				String name = rset.getString(2);
				Date date = rset.getDate(3);
				request.setAttribute("id", id);
				request.setAttribute("name", name);
				request.setAttribute("date", date);

				//System.out.println(rset.getString(1));
			}

			if(request.getAttribute("id")==null)
			{
				RequestDispatcher disp = request.getRequestDispatcher("/index.html");
				disp.forward(request, response);
			}
			else {
				RequestDispatcher disp = request.getRequestDispatcher("/next.jsp");
				disp.forward(request, response);
			}
		}catch(SQLException e) {
			e.printStackTrace();

		}

		/*List<CustomersVo> CustomerList;
        CustomerList = list;

        for( CustomersVo e2: CustomerList )
        {
            System.out.println( e2.toString() );
        }*/
	}


	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
