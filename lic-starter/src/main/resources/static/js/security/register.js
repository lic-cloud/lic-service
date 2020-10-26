showDictSelect("sex", "sex");
layui.use(['layer', 'laydate'], function () {
    const layer = layui.layer;
    const playdate = layui.laydate;
    playdate.render({
        elem: '#birthday'
    });
});
$('#form').bootstrapValidator();

function add(obj) {
    //把按钮禁用掉
    $(obj).attr("disabled", true);
    //触发全部验证
    const bootstrapValidator = $("#form").data('bootstrapValidator');
    bootstrapValidator.validate();
    //获取当前表单验证状态
    if (!bootstrapValidator.isValid()) {
        $(obj).attr("disabled", false);
        return;
    }
    const format = $("#form").serializeObject();
    /*将日期格式2020-10-01的生日，转换为时间戳*/
    const date = new Date(format.birthday);
    format.birthday = date.valueOf();
    $.ajax({
        type: 'post',
        url: '/users/register',
        contentType: "application/json; charset=utf-8",
        data: JSON.stringify(format),
        success: function (data) {
            layer.msg("添加成功", {shift: -1, time: 1000}, function () {
                location.href = "login.html";
            });
        },
        error: function (xhr, status, error) {
            $(obj).attr("disabled", false);
            layer.msg(xhr.responseText, {shift: -1, time: 4000});
        }
    });
}
