package com.senac.pedro.api;

import com.senac.pedro.conf.Constants;
import com.senac.pedro.conf.TypeData;
import com.senac.pedro.database.Memory;
import com.senac.pedro.database.XMLDatabase;
import com.senac.pedro.model.Order;
import com.senac.pedro.serializer.OrderJSONSerializer;
import com.senac.pedro.serializer.OrderXMLSerializer;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ReceiveOrderServlet extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String data = request.getParameter("content");

        if(Constants.TYPE_DATA == TypeData.JSON) {
            Order order = new OrderJSONSerializer().parse(data);
            Memory.orders.add(order);
        } else if(Constants.TYPE_DATA == TypeData.XML) {
            Order order = new OrderXMLSerializer().parse(data);
            Memory.orders.add(order);
        } else {
            throw new RuntimeException("Nunca vai acontecer");
        }

        XMLDatabase.save();

        RequestDispatcher rd = request.getRequestDispatcher("success.jsp");
        rd.forward(request,response);
    }
}