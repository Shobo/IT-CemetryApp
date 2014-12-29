$(document).ready(function() {
    $(".form-radio").change(showNumberBox);
    $("#grave-name").change(loadParcels);
    $("#grave-parcel").change(loadGraves);
    $("#grave-number").change(setSurface);
    $("#doc-nr").keyup(fillNumber);
});

function showNumberBox() {
    if ($(this).val() == "1" || $(this).val() == "2")
        $(".hidden-obj").show();
    else
        $(".hidden-obj").hide();
    $("#doc-nr").val("");
}

function loadParcels() {
    var input = $("#grave-parcel");
    if ($(this).find(':first-child').val() < 0)
        $(this).find(":first-child").remove();
    $.get("ConcessionServlet", {act:"loadGraveDetails", field:"parcel", graveyard:$(this).find(':selected').val()},
        function(data) {
            input.empty();
            for (var i = -1; i < data.length; i++) {
                var option = document.createElement("option");
                option.setAttribute('value', i);
                if (i >= 0)
                    option.innerHTML = data[i]['number'];
                else
                    option.innerHTML = "Selectează...";
                input.append(option);
            }
        }, "json");
    input.show();
}

function loadGraves() {
    var input = $("#grave-number");
    if ($(this).find(':first-child').val() < 0)
        $(this).find(":first-child").remove();
    $.get("ConcessionServlet", {act:"loadGraveDetails", field:"grave", graveyard:$("#grave-name").find(':selected').val(),
            parcel:$(this).find(':selected').val()},
        function(data) {
            input.empty();
            for (var i = -1; i < data.length; i++) {
                var option = document.createElement("option");
                if (i >= 0) {
                    option.innerHTML = data[i]['number'];
                    option.setAttribute('value', data[i]['surface']);
                }
                else {
                    option.innerHTML = "Selectează...";
                    option.setAttribute('value', i);
                }
                input.append(option);
            }
        }, "json");
    input.show();
}

function setSurface() {
    if ($(this).find(':first-child').val() < 0)
        $(this).find(":first-child").remove();
    $("#grave-surface").val($("#grave-number").find(':selected').val() + " m2");
}

function fillNumber() {
    var selected = $('input[name=receipt-cause]:checked');
    console.log(selected.val());
    var selectedPar = $('.radio-text[value=' + selected.val() + ']');
    console.log(selectedPar.text());
    console.log($(this).val());
    var s = selectedPar.text();
    var replWith = $(this).val();
    if (replWith == "")
        replWith = "____";
    if (s.search("____") > -1)
        s = s.replace("____", $(this).val());
    else if (s.search("/") > -1)
        s = s.replace(/:.*\//, ": " + replWith + "/");
    else
        s = s.replace(/:.*/, ": " + replWith);
    selectedPar.text(s);

}