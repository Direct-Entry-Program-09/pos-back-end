package lk.ijse.dep9.api.util;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "HttpServlet2", value = "/HttpServlet2")
public class HttpServlet2 extends HttpServlet {

    protected void doPatch(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getMethod().equalsIgnoreCase("PATCH")){
            doPatch(request,response);
        }else {
            super.service(request,response);
        }
    }
}
