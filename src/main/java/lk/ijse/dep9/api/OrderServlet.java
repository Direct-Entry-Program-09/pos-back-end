package lk.ijse.dep9.api;

import jakarta.annotation.Resource;
import jakarta.json.bind.JsonbBuilder;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lk.ijse.dep9.api.util.HttpServlet2;
import lk.ijse.dep9.dto.OrderDTO;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@WebServlet(name = "OrderServlet", value = "/orders/*", loadOnStartup = 0)
public class OrderServlet extends HttpServlet2 {
    @Resource(lookup = "java:/comp/env/jdbc/pos")//for tom cat if for glassfish jdbc/lms
    private DataSource pool;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //response.getWriter().println("order doGet");
        if (request.getPathInfo() == null || request.getPathInfo().equals("/")) {
            String query = request.getParameter("q");
            String page = request.getParameter("page");
            String size = request.getParameter("size");
            if (query != null && page != null && size != null) {
                if (!size.matches("\\d+") || !page.matches("\\d+")) {
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid page or size");
                } else {

                    searchAllOrders(query, Integer.parseInt(page), Integer.parseInt(size), response);
                }
            } else {
                Matcher matcher = Pattern.compile("^/([A-Fa-f\\d]{8}(-[A-Fa-f\\d]{4}){3}-[a-fA-F\\d]{12})$").matcher(request.getPathInfo());
                if (matcher.matches()) {
                    getOrderDetails(response, matcher.group(1));
                } else {
                    response.sendError(HttpServletResponse.SC_NOT_IMPLEMENTED);
                }
            }
        }
    }
private void searchAllOrders(String query, int page,int size, HttpServletResponse response) throws IOException{

}
    private void getOrderDetails(HttpServletResponse response, String orderID) throws ServletException, IOException {
        try (Connection connection = pool.getConnection()) {
            PreparedStatement stm = connection.prepareStatement("SELECT  * FROM `order` WHERE id=?");
            stm.setString(1, orderID);
            ResultSet rst = stm.executeQuery();
            if (rst.next()) {
                String id = rst.getString("id");
                String date = rst.getString("date");
                String customer_id = rst.getString("customer_id");
                OrderDTO oDTO = new OrderDTO(id, date, customer_id);
                response.setContentType("application/json");
                JsonbBuilder.create().toJson(oDTO, response.getWriter());

            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Invalid customer Id");
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to Fetch");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.getWriter().println("order doPost");
    }
}
