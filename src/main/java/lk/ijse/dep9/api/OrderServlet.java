package lk.ijse.dep9.api;

import jakarta.annotation.Resource;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lk.ijse.dep9.api.util.HttpServlet2;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet(name = "OrderServlet", value = "/orders/*", loadOnStartup = 0)
public class OrderServlet extends HttpServlet2 {
    @Resource(lookup = "java:/comp/env/jdbc/pos")//for tom cat if for glassfish jdbc/lms
    private DataSource pool;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.getWriter().println("order doGet");
    }

    private void getOrderDetails(HttpServletResponse response, String orderID) throws ServletException {
        try (Connection connection = pool.getConnection()) {
            PreparedStatement stm = connection.prepareStatement("SELECT  * FROM order WHERE id=?");
            stm.setString(1, orderID);
            ResultSet rst = stm.executeQuery();
            if(rst.next()){
//                String id = rst.getString("id");
//                String id = rst.getString("id");
//                String id = rst.getString("id");
                new

            }else{

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.getWriter().println("order doPost");
    }
}
