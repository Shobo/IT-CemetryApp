
$(document).ready(function () {
    $(".nav li a").click(function (e) {
       $(".nav li a").removeClass("active");
       $(this).addClass("active");
       e.preventDefault();
       loadOptions($(this).text());
   });
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
