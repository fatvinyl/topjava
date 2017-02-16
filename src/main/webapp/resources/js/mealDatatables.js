var ajaxUrl = "ajax/profile/meals/";
var datatableApi;

// $(document).ready(function () {
function clearFilter() {
    $("#filter")[0].reset();
    $.get(ajaxUrl, updateTableByData);
}

function updateTable() {
    $.ajax({
        type: "POST",
        url: ajaxUrl + "filter",
        data: $("#filter").serialize(),
        success: updateTableByData
    });
}

$(function () {
    datatableApi = $("#datatable").DataTable({
        "ajax": {
            "url": ajaxUrl,//url для отдачи данных с сервера
            "dataSrc": "" //задает, в каком параметре ответа сервера к нам приходят данные. т.к. они безо вской иерархии приходят в ROOTе, то ничего не задаем
        },
        "paging": false,
        "info": true,
        "columns": [
            {
                "data": "dateTime",
                "render": function (date, type, row) {
                    if (type == 'display') {
                        return '<span>' + date.substring(0, 16).replace("T", " ") + '</span>';
                    }
                    return date;
                }
            },
            {
                "data": "description"
            },
            {
                "data": "calories"
            },
            {
                "orderable": false,
                "defaultContent": "",
                "render": renderEditBtn
            },
            {
                "orderable": false,
                "defaultContent": "",
                "render": renderDeleteBtn
            }
        ],
        "order": [
            [
                0,
                "desc"
            ]
        ],
        "createdRow": function (row, data, dataIndex) {//данный колбэк вызывается, когда отрисовывается строка. data - это данные JSON для кажой строки
            if (data.exceed) { //проверяем
                $(row).addClass("exceeded"); //делаем соответствующий стиль
            } else {
                $(row).addClass("normal");
            }
        },
        "initComplete": makeEditable
    });
});