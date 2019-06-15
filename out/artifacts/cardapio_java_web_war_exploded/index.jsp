<%@ page import="com.senac.pedro.model.Food" %>
<%@ page import="java.util.List" %>
<%@ page import="com.senac.pedro.serializer.BreakfastMenuSerializer" %>
<%@ page import="java.util.Calendar" %>
<%@ page import="java.util.GregorianCalendar" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Atividade 6 - Leitura XML</title>

    <style>
        .hide {
            display: none;
        }

        #content_container {
            margin-left: 10px;
        }
    </style>
</head>
<body>
    <h1>Cardápio</h1>

    <p id="loading">Carregando...</p>

    <div id="container" class="hide">
        <p>
            <label for="item_type">Selecione o item que deseja comprar:</label>
            <select id="item_type" onchange="onClickItemType(this)">
                <option value=""></option>
            </select>
        </p>

        <div id="content_container" class="hide">
            <div>
                <p>Nome: <span id="name">{{nome aqui}}</span></p>
                <p>Descrição: <span id="description">{{descrição aqui}}</span></p>
                <p>Preço: <span id="price">{{preço aqui}}</span></p>
                <p>Calorias: <span id="calories">{{calorias aqui}}</span></p>
            </div>

            <p>
                <label for="amount">Qual é a quantidade do item desejada?</label>
                <input id="amount" type="text">
            </p>

            <button onclick="onClickBuy()">Comprar</button>
        </div>

        <div id="total_container" class="hide">
            <ul id="item_added">

            </ul>
            <p>Valor total do pedido: <span id="total_value">{{valor aqui}}</span></p>
            <p>Total de calorias: <span id="calories_value">{{calorias aqui}}</span></p>

            <div>
                <label for="user_name">Nome: </label>
                <input id="user_name" type="text"/>

                <label for="address">Endereço: </label>
                <input id="address" type="text" />

                <label for="phone">Telefone: </label>
                <input id="phone" type="text"/>
            </div>

            <form method="post" action="/enviar_dados">
                <input id="content" type="hidden" name="content" >
                <input type="submit" onclick="onClickSubmitXml()"/>
            </form>
        </div>
    </div>

    <script type="application/javascript">
        var data = {};
        var boughtItems = [];

        function getCurrentDate() {
            var today = new Date();
            var dd = String(today.getDate()).padStart(2, '0');
            var mm = String(today.getMonth() + 1).padStart(2, '0');
            var yyyy = today.getFullYear();

            today = yyyy + '-' + mm + '-' + dd;
            return today;
        }

        function readDataFromServer() {
            var loading = document.getElementById('loading');
            var container = document.getElementById('container');
            var itemType = document.getElementById('item_type');
            loading.classList.add('hide');
            container.classList.remove('hide');

            var content = [
                <%
                    List<Food> foods = new BreakfastMenuSerializer().fetchFromUrl();
                    for(Food food: foods) {
                        out.println(food + ",");
                    }
                %>
            ];

            for(var i = 0; i < content.length; i++) {
                var item = content[i];
                var option = document.createElement('option');
                option.text = item.name;
                option.value = item.name;

                itemType.add(option);
                data[item.name] = item;
            }
        }

        function onClickItemType(itemType) {
            var container = document.getElementById('content_container');
            container.classList.remove('hide');

            var key = itemType.options[itemType.selectedIndex].value;
            if(!key) return;

            var item = data[key];

            var name = document.getElementById('name');
            var description = document.getElementById('description');
            var price = document.getElementById('price');
            var calories = document.getElementById('calories');

            name.textContent = item.name;
            description.textContent = item.description;
            price.textContent = item.price;
            calories.textContent = item.calories;
        }

        function onClickBuy() {
            var name = document.getElementById('name');
            var description = document.getElementById('description');
            var price = document.getElementById('price');
            var calories = document.getElementById('calories');
            var amount = document.getElementById('amount');

            if(!amount.value || parseInt(amount.value) <= 0) return;

            var item = {
                name: name.textContent,
                description: description.textContent,
                price: parseFloat(price.textContent),
                calories: parseFloat(calories.textContent),
                amount: parseInt(amount.value)
            };

            boughtItems.push(item);
            generateTotal();
        }

        function generateTotal() {
            var container = document.getElementById('total_container');
            container.classList.remove('hide');

            var itemAdded = document.getElementById('item_added');
            itemAdded.textContent = '';

            var total = document.getElementById('total_value');
            var calories = document.getElementById('calories_value');

            var sumTotal = 0.0;
            var sumCalories = 0.0;
            for(var i = 0; i < boughtItems.length; i++) {
                var item = boughtItems[i];
                var t = item.price * item.amount;
                var c = item.calories * item.amount;

                sumTotal += t;
                sumCalories += c;

                var li = document.createElement('li');
                li.innerHTML =
                    'Nome: ' + item.name +
                    ', quantidade: ' + item.amount +
                    ', descrição: ' + item.description +
                    ', preço: ' + t +
                    ', calorias: ' + c + '.';
                itemAdded.appendChild(li);
            }

            total.textContent = String(sumTotal);
            calories.textContent = String(sumCalories);
        }

        function onClickSubmitXml() {
            var date = getCurrentDate();
            var name = document.getElementById('user_name').value;
            var address = document.getElementById('address').value;
            var phone = document.getElementById('phone').value;

            var xml = '<?xml version="1.0"?><order>';
            xml += '<created>' + date + '</created>';
            xml += '<name>' + name + '</name>';
            xml += '<address>' + address + '</address>';
            xml += '<phone_number>' + phone + '</phone_number>';

            xml += '<items>';
            for(var i = 0; i < boughtItems.length; i++) {
                var item = boughtItems[i];
                xml += '<item>';
                xml += '<name>' + item.name + '</name>';
                xml += '<quantity>' + item.amount + '</quantity>';
                xml += '</item>';
            }
            xml += '</items>';
            xml += '</order>';

            var content = document.getElementById('content');
            content.value = xml;
        }

        function onClickSubmitJson() {
            var date = getCurrentDate();
            var name = document.getElementById('user_name').value;
            var address = document.getElementById('address').value;
            var phone = document.getElementById('phone').value;

            var items = [];
            for(var i = 0; i < boughtItems.length; i++) {
                var item = boughtItems[i];
                items.push({
                    'name': item.name,
                    'quantity': item.amount
                });
            }

            var json = {
                "order": {
                    "created": date,
                    "name": name,
                    "address": address,
                    "phone_number": phone,
                    "items": items
                }
            };

            var content = document.getElementById('content');
            content.value = JSON.stringify(json);
        }

        readDataFromServer();
    </script>
</body>
</html>
