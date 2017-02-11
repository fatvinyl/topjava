function makeEditable() {
    // $('.delete').click(function () {//нажимаем иконку delete (крестик) у какого-нибудь юзера в таблице. '.delete' означает, что мы вешаем его на весь класс, т.е. можем нажать у любого юзера
    //     deleteRow($(this).attr("id"));//вызывается функция (см. ниже) для удаления строки. в нее передаем id-шник нажатой кнопки
    // });

    $('#detailsForm').submit(function () { //на событие submit формы мы вешаем функцию save()
        save();
        return false; //говорим, что обрабатывать это событие никак не нужно
    });

    $(document).ajaxError(function (event, jqXHR, options, jsExc) {
        failNoty(event, jqXHR, options, jsExc);
    });
}

function add() { //нажимаем на кнопку "+" в браузере, срабатывает onclick="add()" в users.jsp
    $('#id').val(null);//берем элемент id из формы detailsForm, присваиваем значение null
    $('#editRow').modal(); //берем элемент id=editRow, который помечен modal() и вызываем Boostrap-овский плагин, который открывает модальное окно
}

function deleteRow(id) { //функция для удаления строки
    $.ajax({ //вызываем метод jQuery.ajax(). это основной метод. это обертка jQuery над XmlHttpRequest
        url: ajaxUrl + id, //указываем url, по которому будет происодить запрос
        type: 'DELETE', //указываем тип запроса
        success: function () { //колбэк, т.е. по успешному окончанию, когда нам сервер отдаст данные, нужно выполнить функцию.
            updateTable(); //метод обновления таблицы (см. ниже)
            successNoty('Deleted'); //всплывает скрипт с уведомлением "delete". метод см. ниже
        }
    });
}

function updateTable() { //обновляем всю таблицу
    $.get(ajaxUrl, function (data) { //по ajax запрашиваем все данные. .get() - это уже сокращенная форма записи ajax, где мы передаем не объекты, а параметры (url, коллбэк функция)
        datatableApi.clear(); //в случае success мы все данные чистим
        $.each(data, function (key, item) {//вспомогательная функция jQuery, которая прохоит по всем значениям data, которые вернулиь из сервера.
            datatableApi.row.add(item); //затем вставляем данные в таблицу
        });
        datatableApi.draw(); //отрисовываем таблицу
    });
}

function save() {
    var form = $('#detailsForm');//берем форму
    $.ajax({
        type: "POST",
        url: ajaxUrl,
        data: form.serialize(),//в качестве данных передаем сериализацию формы. все имена и значения сериализуются в параметры запроса
        success: function () {
            $('#editRow').modal('hide');//по успешному окончанию прячем диалоговое окно
            updateTable();//обновляем таблицу
            successNoty('Saved');
        }
    });
}

var failedNote;

function closeNoty() {
    if (failedNote) {
        failedNote.close();
        failedNote = undefined;
    }
}

function successNoty(text) {
    closeNoty();
    noty({
        text: text,
        type: 'success',
        layout: 'bottomRight',
        timeout: true
    });
}

function failNoty(event, jqXHR, options, jsExc) {
    closeNoty();
    failedNote = noty({
        text: 'Failed: ' + jqXHR.statusText + "<br>",
        type: 'error',
        layout: 'bottomRight'
    });
}

