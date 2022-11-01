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
import java.sql.*;
import java.util.ArrayList;


@WebServlet(name = "Customer-Servlet",value = "/customers/*",loadOnStartup = 0)
public class CustomerServlet extends HttpServlet2 {
    @Resource(lookup = "java:/comp/env/jdbc/pos")//for tom cat if for glassfish jdbc/lms
    private DataSource pool;
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getPathInfo()==null || req.getPathInfo().equals("/")){
            String q = req.getParameter("q");
            String size = req.getParameter("size");
            String page = req.getParameter("page");

            searchPaginatedCustomers(req,resp,q,Integer.parseInt(size),Integer.parseInt(page));
        }

//        loadAllCustomers(resp);

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
    private void searchPaginatedCustomers(HttpServletRequest request,HttpServletResponse response,String query, int size,int page) throws IOException {
        try (Connection connection = pool.getConnection()) {
            String sql="SELECT COUNT(id) AS count FROM customer WHERE id LIKE ? OR name LIKE ? OR address LIKE ?";
            PreparedStatement stm= connection.prepareStatement(sql);
            query="%"+query+"%";
            stm.setString(1,query);
            stm.setString(2,query);
            stm.setString(3,query);
            ResultSet resultSet = stm.executeQuery();
            resultSet.next();
            int count = resultSet.getInt("count");
            response.setIntHeader("X-Total-Count",count);
            PreparedStatement statement2 = connection.prepareStatement("SELECT * FROM customer WHERE id LIKE ? OR name LIKE ? OR address LIKE ? LIMIT ? OFFSET ?");
            statement2.setString(1,query);
            statement2.setString(2,query);
            statement2.setString(3,query);
            statement2.setInt(4,size);
            statement2.setInt(5,(page-1)*size);
            ResultSet resultSet1 = statement2.executeQuery();


            ArrayList<CustomerDTO> customers = new ArrayList<>();

            while (resultSet1.next()){
                String id = resultSet1.getString("id");
                String name = resultSet1.getString("name");
                String address = resultSet1.getString("address");
                customers.add(new CustomerDTO(id,name,address));
            }
            Jsonb jsonb = JsonbBuilder.create();
            response.setContentType("application/json");
            jsonb.toJson(customers,response.getWriter());

        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_BAD_REQUEST,"Invalid query, size or page has been passed");
        } catch (IOException e) {
            e.printStackTrace();
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
