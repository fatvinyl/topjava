
var ajaxUrl = 'ajax/meals/'; // URL, по которому работает MealAjaxController
var datatableApi;

$(function () {                                 //$ - это JQuery
    datatableApi = $('#datatable').DataTable({ //id = datatable в users.jsp.
        "paging": false,
        "info": true,
        "columns": [ //колонки. по умолчанию индексируются с 0
            {
                "data": "date_time" //data - это связка между приходящими данными JSON и таблицей, name - навание колонки
            },
            {
                "data": "description"
            },
            {
                "data": "calories"
            },
            {
                "defaultContent": "Edit",
                "orderable": false
            },
            {
                "defaultContent": "Delete",
                "orderable": false
            }
        ],
        "order": [ //сортировка по умолчанию при отрисовке таблицы.
            [
                0, //сортироваться будут по name (индекс 0)
                "asc" //сортировка будет по порядку
            ]
        ]
    });
    makeEditable();//делаем таблицу редактируемой (функция описана в datatablesUtils.js)
});