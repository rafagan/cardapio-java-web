package com.senac.pedro.serializer;

import com.senac.pedro.model.Order;
import com.senac.pedro.model.OrderItem;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;

public class OrderXMLSerializer {
    public Order parse(String data) {
        Order order = new Order();

        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            InputSource inStream = new InputSource();
            inStream.setCharacterStream(new StringReader(data));
            Document doc = db.parse(inStream);
            Element root = doc.getDocumentElement();

            NodeList orderNodes = root.getChildNodes();
            for(int i = 0; i < orderNodes.getLength(); i++) {
                Node orderValue = orderNodes.item(i);

                String txtOrderValue = orderValue.getTextContent();
                switch (orderValue.getNodeName()) {
                    case "name":
                        order.setName(txtOrderValue);
                        break;
                    case "address":
                        order.setAddress(txtOrderValue);
                        break;
                    case "phone_number":
                        order.setPhoneNumber(txtOrderValue);
                        break;
                    case "created":
                        order.setCreated(txtOrderValue);
                        break;
                    case "items":
                        NodeList items = orderValue.getChildNodes();

                        for(int j = 0; j < items.getLength(); j++) {
                            OrderItem item = new OrderItem();
                            Node nodeItem = items.item(j);
                            NodeList itemValues = nodeItem.getChildNodes();

                            for(int k = 0; k < itemValues.getLength(); k++) {
                                Node itemValue = itemValues.item(k);
                                String txtItemValue = itemValue.getTextContent();
                                switch (itemValue.getNodeName()) {
                                    case "name":
                                        item.setName(txtItemValue);
                                        break;
                                    case "quantity":
                                        item.setQuantity(Integer.parseInt(txtItemValue));
                                        break;
                                }
                            }

                            order.getItems().add(item);
                        }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return order;
    }
}
