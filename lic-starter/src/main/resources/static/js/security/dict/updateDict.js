layui.use(['layer', 'laydate'], function () {
    const layer = layui.layer;
});

initData();
$('#form').bootstrapValidator();

function initData() {
    const id = getUrlParam("id");
    if (id !== "") {
        $.ajax({
            type: 'get',
            url: '/dicts/' + id,
            async: false,
            success: function (data) {
                $('#id').val(data.id);
                $('#type').val(data.type);
                $('#k').val(data.k);
                $('#val').val(data.val);
            },
            error: function (xhr, status, error) {
                layer.msg(xhr.responseText, {shift: -1, time: 4000});
            }
        });
    }
}

function update(obj) {
    $(obj).attr("disabled", true);
    const bootstrapValidator = $("#form").data('bootstrapValidator');
    bootstrapValidator.validate();
    if (!bootstrapValidator.isValid()) {
        $(obj).attr("disabled", false);
        return;
    }

    const format = $("#form").serializeObject();

    $.ajax({
        type: 'put',
        url: '/dicts',
        contentType: "application/json; charset=utf-8",
        data: JSON.stringify(format),
        success: function (data) {
            layer.msg("修改成功", {shift: -1, time: 1000}, function () {
                location.href = "dictList.html";
            });
        },
        error: function (xhr, status, error) {
            $(obj).attr("disabled", false);
            layer.msg(xhr.responseText, {shift: -1, time: 4000});
        }
    });
}
