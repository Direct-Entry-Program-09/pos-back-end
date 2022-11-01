package lk.ijse.dep9.api;

import jakarta.annotation.Resource;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lk.ijse.dep9.api.util.HttpServlet2;
import lk.ijse.dep9.dto.CustomerDTO;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


@WebServlet(name = "Customer-Servlet",value = "/customers/*",loadOnStartup = 0)
public class CustomerServlet extends HttpServlet2 {
    @Resource(lookup = "java:/comp/env/jdbc/pos")//for tom cat if for glassfish jdbc/lms
    private DataSource pool;
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        loadAllCustomers(resp);

    }
    private void loadAllCustomers(HttpServletResponse response) throws IOException  {

        try {
            Connection connection = null;
            connection = pool.getConnection();
            Statement stm = connection.createStatement();
            ResultSet rst = stm.executeQuery("SELECT * FROM customer");

            ArrayList<CustomerDTO> dto=new ArrayList<>();
            while (rst.next()){
                String id = rst.getString("id");
                String name = rst.getString("name");
                String address = rst.getString("address");

                dto.add(new CustomerDTO(id,name,address));
            }

            connection.close();
            Jsonb jsonb = JsonbBuilder.create();
            response.setContentType("application/json");
            jsonb.toJson(dto,response.getWriter());
        } catch (SQLException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.getWriter().println("customer dopost");

    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.getWriter().println("customer doDelete");
    }
    protected void doPatch(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.getWriter().println("customer doPatch");
    }

}
