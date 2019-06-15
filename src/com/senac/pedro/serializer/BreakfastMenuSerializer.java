package com.senac.pedro.serializer;

import com.senac.pedro.model.Food;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class BreakfastMenuSerializer {
    private static final String url = "https://www.w3schools.com/xml/simple.xml";

    public static void main(String[] args) {
        List<Food> foods = new BreakfastMenuSerializer().fetchFromUrl();
        System.out.println(foods);
    }

    public List<Food> fetchFromUrl() {
        List<Food> foods = new ArrayList<>();

        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(new URL(url).openStream());
            Element root = doc.getDocumentElement();

            NodeList foodNodes = root.getElementsByTagName("food");
            for(int i = 0; i < foodNodes.getLength(); i++) {
                Node foodNode = foodNodes.item(i);
                NodeList values = foodNode.getChildNodes();

                Food food = new Food();
                for(int j = 0; j < values.getLength(); j++) {
                    Node value = values.item(j);

                    String textContent = value.getTextContent();
                    switch (value.getNodeName()) {
                        case "name":
                            food.setName(textContent);
                            break;
                        case "price":
                            food.setPrice(Float.parseFloat(textContent.split("\\$")[1]));
                            break;
                        case "description":
                            food.setDescription(textContent);
                            break;
                        case "calories":
                            food.setCalories(Integer.parseInt(textContent));
                            break;
                    }
                }
                foods.add(food);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return foods;
    }
}
