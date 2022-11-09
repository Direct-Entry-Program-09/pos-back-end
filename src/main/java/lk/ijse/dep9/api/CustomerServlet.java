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
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@WebServlet(name = "Customer-Servlet",value = "/customers/*",loadOnStartup = 0)
public class CustomerServlet extends HttpServlet2 {
    @Resource(lookup = "java:/comp/env/jdbc/pos")//for tom cat if for glassfish jdbc/lms
    private DataSource pool;
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        if (req.getPathInfo()==null || req.getPathInfo().equals("/")){
//            String q = req.getParameter("q");
//            String size = req.getParameter("size");
//            String page = req.getParameter("page");
//            if (q!=null & size!=null & page!=null){
//                if (!size.matches("\\d+") || !page.matches("\\d+")){
//                    resp.sendError(HttpServletResponse.SC_BAD_REQUEST,"Invalid size and page");
//                }else {
//                    searchPaginatedCustomers(resp,q,Integer.parseInt(size),Integer.parseInt(page));
//                }
//            }else {
//                loadAllCustomers(resp);
//            }
//        }
//        Matcher matcher = Pattern.compile("^/([a-zA-Z0-9]{8}(-[a-zA-Z0-9]{4}){3}-[a-zA-Z0-9]{12}/?)$").matcher(req.getPathInfo());
//        if (matcher.matches()) {
//            getCustomerDetail(resp, matcher.group(1));
//        }else {
//            resp.sendError(HttpServletResponse.SC_NOT_FOUND,"Invalid id");
//        }
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

    private void getCustomerDetail(HttpServletResponse response, String cid) throws IOException {

        try (Connection connection = pool.getConnection()) {
            System.out.println(cid);
            PreparedStatement stm = connection.prepareStatement("SELECT * FROM customer WHERE id LIKE ?");
            stm.setString(1,cid);
            ResultSet rst = stm.executeQuery();
            if(rst.next()){
                String id1 = rst.getString("id");
                String name = rst.getString("name");
                String address = rst.getString("address");

                response.setContentType("application/json");
                JsonbBuilder.create().toJson(new CustomerDTO(id1,name,address),response.getWriter());

            }else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND,"Invalid customer id");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,"Internal server error");
        }

    }




    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        CustomerDTO customerDTO = JsonbBuilder.create().fromJson(req.getReader(), CustomerDTO.class);
        try (Connection connection = pool.getConnection()) {
            customerDTO.setId(UUID.randomUUID().toString());
            PreparedStatement stm = connection.prepareStatement("INSERT INTO customer (id, name, address) VALUES (?,?,?)");
            stm.setString(1,customerDTO.getId());
            stm.setString(2,customerDTO.getName());
            stm.setString(3,customerDTO.getAddress());
            int i = stm.executeUpdate();
            if (i==1){
                resp.setStatus(HttpServletResponse.SC_CREATED);
            }else {
                resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.getWriter().println("customer doDelete");
    }
    protected void doPatch(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.getWriter().println("customer doPatch");
    }

}
