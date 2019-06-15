package com.senac.pedro.database;

import com.senac.pedro.model.Order;
import com.senac.pedro.model.OrderItem;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;

public class XMLDatabase {
    public static void save() {
        try {
            DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
            Document document = documentBuilder.newDocument();

            Element root = document.createElement("orders");
            document.appendChild(root);

            for (Order order : Memory.orders) {
                    Element orderTag = document.createElement("order");

                    Element name = document.createElement("name");
                    name.setTextContent(order.getName());
                    orderTag.appendChild(name);

                    Element address = document.createElement("address");
                    address.setTextContent(order.getAddress());
                    orderTag.appendChild(address);

                    Element phone = document.createElement("phone_number");
                    phone.setTextContent(order.getPhoneNumber());
                    orderTag.appendChild(phone);

                    Element created = document.createElement("created");
                    created.setTextContent(order.getCreated());
                orderTag.appendChild(created);

                Element itemsTag = document.createElement("items");
                for(OrderItem item: order.getItems()) {
                    Element itemTag = document.createElement("item");

                    Element itemName = document.createElement("name");
                    itemName.setTextContent(item.getName());
                    itemTag.appendChild(itemName);

                    Element quantity = document.createElement("quantity");
                    quantity.setTextContent(item.getQuantity().toString());
                    itemTag.appendChild(quantity);

                    itemsTag.appendChild(itemTag);
                }
                orderTag.appendChild(itemsTag);

                root.appendChild(orderTag);
            }

            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            DOMSource source = new DOMSource(document);
            StreamResult streamResult = new StreamResult(new File("C:\\Users\\Pedro Sarkis\\Desktop\\data2.xml"));
            transformer.transform(source, streamResult);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
