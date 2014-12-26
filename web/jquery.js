$(document).ready(function () {
    $("#year-selection").change(changeYear);
    loadMenu();
    if (window.location.search.indexOf('?') == -1)
        loadOptions($("title").text());
});

function loadOptions(name) {
    $(".content").empty();
    name = name.trim();
    $.getJSON('resources/options.json', function(data) {
        var f = document.createElement("form");
        f.setAttribute('method',"get");
        f.setAttribute('action',data[name]["Servlet"]);

        $.each(data[name]["Options"], function(key, val) {
            var input = document.createElement("input");
            input.setAttribute('type',"submit");
            input.setAttribute('class',"option")
            input.setAttribute('value', val.Name);
            input.setAttribute('name', "act")
            f.appendChild(input);
        })

        document.getElementsByClassName("content")[0].appendChild(f)
    });
}

function loadMenu() {
    var title = $("title").text();
    $.getJSON('resources/menu.json', function(data) {
        var ul = document.createElement("ul");
        ul.setAttribute("class", "nav");

        $.each(data, function(key, val) {
            var li = document.createElement("li");
            var anch = document.createElement("a");
            anch.setAttribute('href', val.Ref)
            if (val.Name == title)
                anch.setAttribute("class", "active");
            var span = document.createElement("span");
            span.innerHTML = val.Name;
            anch.appendChild(span);
            li.appendChild(anch);
            ul.appendChild(li);
        });
        document.getElementsByClassName("menu")[0].appendChild(ul);
    });
}

function changeYear() {
    var yearval = $(this).find(':selected').text();
    var actval = $(this).attr("name");
    $.get("ConcessionServlet", {year:yearval, act:actval},
        function(data) {
            $(".content").html($(data).filter(".content").html());
        }, "html");
}