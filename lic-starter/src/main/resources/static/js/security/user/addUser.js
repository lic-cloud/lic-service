$('#form').bootstrapValidator();
layui.use(['layer', 'laydate'], function () {
    const layer = layui.layer;
    const playdate = layui.laydate;
    playdate.render({
        elem: '#birthday'
    });
});

showDictSelect("sex", "sex");
initRoles();/*初始化角色*/
function add(obj) {
    $(obj).attr("disabled", true);
    const bootstrapValidator = $("#form").data('bootstrapValidator');
    //触发全部验证
    bootstrapValidator.validate();
    //获取当前表单验证状态
    if (!bootstrapValidator.isValid()) {
        $(obj).attr("disabled", false);
        return;
    }

    const format = $("#form").serializeObject();
    format.roleIds = getCheckedRoleIds();
    const date = new Date(format.birthday);
    format.birthday = date.valueOf();
    $.ajax({
        type: 'post',
        url: '/users',
        contentType: "application/json; charset=utf-8",
        data: JSON.stringify(format),
        success: function (data) {
            layer.msg("添加成功", {shift: -1, time: 1000}, function () {
                location.href = "userList.html";
            });
        },
        error: function (xhr, status, error) {
            $(obj).attr("disabled", false);
            layer.msg(xhr.responseText, {shift: -1, time: 4000});
        }
    });
}
