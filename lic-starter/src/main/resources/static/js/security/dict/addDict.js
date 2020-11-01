layui.use(['layer', 'laydate'], function () {
    var layer = layui.layer;
});
$('#form').bootstrapValidator();
function add(obj) {
    $(obj).attr("disabled", true);
    var bootstrapValidator = $("#form").data('bootstrapValidator');
    bootstrapValidator.validate();
    if (!bootstrapValidator.isValid()) {
        $(obj).attr("disabled", false);
        return;
    }
    var format = $("#form").serializeObject();
    $.ajax({
        type: 'post',
        url: '/dicts',
        contentType: "application/json; charset=utf-8",
        data: JSON.stringify(format),
        success: function (data) {
            layer.msg("添加成功", {shift: -1, time: 1000}, function () {
                location.href = "dictList.html";
            });
        },
        error: function (xhr, status, error) {
            $(obj).attr("disabled", false);
            layer.msg(xhr.responseText, {shift: -1, time: 4000});
        }
    });
}
