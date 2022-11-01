package lk.ijse.dep9.api;

import jakarta.annotation.Resource;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import lk.ijse.dep9.api.util.HttpServlet2;
import lk.ijse.dep9.dto.ItemDTO;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@WebServlet(name = "ItemServlet", value = "/items/*",loadOnStartup = 0)
public class ItemServlet extends HttpServlet2 {

    @Resource(lookup = "java:/comp/env/jdbc/pos")
    private DataSource pool;
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        loadAllItem(response);
//        if (request.getPathInfo() == null || request.getPathInfo().equals("/")) {
//            String query = request.getParameter("q");
//            String size = request.getParameter("size");
//            String page = request.getParameter("page");
//            if (query != null && size != null && page != null) {
//                if (!size.matches("\\d+") || !page.matches("\\d+")) {
//                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "wrong size");
//                } else {
//                    searchPaginatedItems(response, query, Integer.parseInt(size), Integer.parseInt(page));
//                }
//            }  else if (size != null & page != null) {
//                if (!size.matches("\\d+") || !page.matches("\\d+")) {
//                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "wrong size");
//                }
//            } else {
//                loadAllItem(response);
//            }
//
//        } else {
//            Matcher matcher = Pattern.compile("^/([a-zA-Z0-9]{8}(-[a-zA-Z0-9]{4}){3}-[a-zA-Z0-9]{12}/?)$").matcher(request.getPathInfo());
//            if (matcher.matches()) {
//                getItemDetails(response, matcher.group(1));
//            } else {
//                response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "expected valid id");
//            }
//        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.getWriter().println("item doPost");

    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.getWriter().println("item doDelete");
    }
    protected void doPatch(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.getWriter().println("item doPatch");
    }
    private void loadAllItem(HttpServletResponse response) throws IOException {
        try (Connection connection = pool.getConnection()){
            System.out.println("loadall");
            Statement stm = connection.createStatement();
            ResultSet rst = stm.executeQuery("SELECT * FROM item");
            ArrayList<ItemDTO> items = new ArrayList<>();

            while (rst.next()){
                String code = rst.getString("code");
                String description = rst.getString("description");
                double unit_price = rst.getDouble("unit_price");
                int stock = rst.getInt("stock");
                ItemDTO dto = new ItemDTO(code, description, unit_price, stock);
                items.add(dto);


                Jsonb jsonb = JsonbBuilder.create();

                response.setContentType("application/json");
                jsonb.toJson(items, response.getWriter());
            }

        }catch (SQLException e){
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,"Failed to server");
        } catch (IOException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,"Failed to server");
        }
    }
    private void searchPaginatedItems(HttpServletResponse response, String query, int page, int size){

    }
    private void getItemDetails(HttpServletResponse response,String code){

    }


}
