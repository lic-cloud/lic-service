layui.use('layer', function () {
    const layer = layui.layer;
});

function init() {
    $.ajax({
        type: 'get',
        url: '/users/current',
        async: false,
        data: $("#form").serialize(),
        success: function (data) {
            $("#username").val(data.username);
        },
        error: function (xhr, status, error) {
            layer.msg(xhr.responseText, {shift: -1, time: 1000});
        }
    });

}

init();
$('#form').bootstrapValidator();

function update(obj) {
    $(obj).attr("disabled", true);
    const bootstrapValidator = $("#form").data('bootstrapValidator');
    bootstrapValidator.validate();
    if (!bootstrapValidator.isValid()) {
        $(obj).attr("disabled", false);
        return;
    }
    $.ajax({
        type: 'put',
        url: '/users/' + $("#username").val(),
        data: $("#form").serialize(),
        success: function (data) {
            layer.msg("修改成功", {shift: -1, time: 1000}, function () {
                deleteCurrentTab();
            });
        },
        error: function (xhr, status, error) {
            $(obj).attr("disabled", false);
            layer.msg(xhr.responseText, {shift: -1, time: 4000});
        }
    });
}
