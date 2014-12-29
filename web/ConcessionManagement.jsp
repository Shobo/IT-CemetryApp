<%@ page import="edu.cs.ubbcluj.ro.utils.Constants" %>
<%@ page import="edu.cs.ubbcluj.ro.model.Graveyard" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.Calendar" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Evidenta contractelor de concesiune</title>
    <link href="css/styles.css" rel="stylesheet" type="text/css">
    <script src="jquery-1.11.1.js"></script>
    <script src="jquery.js"></script>
    <script src="concessions.js"></script>
</head>
<body>

<div class="menu">
    <div>
        <img class="logo" src="resources/logo.gif" alt="logo">
    </div>
</div>

<div class="content">
    <div>
        <form class="data-form" method="POST" action="ConcessionServlet">
            <fieldset>
                <p class="form-header"><% out.print(Constants.HEADER1); %></p>
                <p class="form-header1"><% out.print(Constants.HEADER1_1); %></p>
                <label for="concessionar-name">Nume și prenume</label>
                <ul name="concessionar-name" class="autocomplete-select" property="name"></ul>
                <input type="text" id="concessionar-name" name="concessionar-name" class="autocomplete">
                <br>
                <label for="concessionar-cnp">CNP</label>
                <ul name="concessionar-cnp" class="autocomplete-select" property="cnp"></ul>
                <input type="text" id="concessionar-cnp" name="concessionar-cnp" class="autocomplete">
                <br>
                <label for="concessionar-address">Adresa</label>
                <ul name="concessionar-address" class="autocomplete-select" property="address"></ul>
                <input type="text" id="concessionar-address" name="concessionar-address" class="autocomplete">
            </fieldset>

            <fieldset>
                <p class="form-header"><% out.print(Constants.HEADER2); %></p>
                <label for="grave-name">Cimitir</label>
                <select id="grave-name">
                    <option value="-1">Seletează...</option>
                    <% List<Graveyard> graveyards = (List<Graveyard>)session.getAttribute("graveyards");
                       int i = 0;
                       for (Graveyard g : graveyards) { %>
                    <option value="<% out.print(i++); %>"><% out.print(g.getName()); %></option>
                    <% } %>
                </select>
                <label for="grave-parcel">Parcela</label>
                <select id="grave-parcel"></select>
                <label for="grave-number">Numar</label>
                <select id="grave-number"></select>
                <label for="grave-surface">Suprafata</label>
                <input type="text" id="grave-surface" name="grave-surface" disabled>
                <br>
            </fieldset>
            <fieldset>
                <p class="form-header"><% out.print(Constants.HEADER3); %></p>
                <label for="receipt-number">Numar chitanta</label>
                <input type="text" id="receipt-number" name="receipt-number">
                <label >Chitanta a fost achitata in baza</label>
                <input class="form-radio" type="radio" name="receipt-cause" value="1">
                <p class="radio-text" value="1">Cererii numarul: ____/<% out.print(Calendar.getInstance().get(Calendar.YEAR));%></p>
                <input class="form-radio" type="radio" name="receipt-cause" value="2">
                <p class="radio-text" value="2">Adeverintei de inhumare numarul: ____</p>
                <input class="form-radio" type="radio" name="receipt-cause" value="3">
                <p class="radio-text" value="3">Reconcesionarii cu prezentarea fotografiei</p>
                <label for="doc-nr" class="hidden-obj">Numar</label>
                <input type="text" id="doc-nr" name="doc-nr" visible="false" class="hidden-obj">
            </fieldset>
            <fieldset>
                <input type="submit" name="act" value="Salveaza"/>
                <input type="submit" name="act" value="Anuleaza"/>
                <input type="submit" name="act" value="Listeaza"/>
            </fieldset>
            <fieldset>
                <img src="resources/down-arrow.png" class="more-icon">
                <a href="">Mai multe detalii</a>
            </fieldset>
        </form>
    </div>
</div>

</body>
</html>
