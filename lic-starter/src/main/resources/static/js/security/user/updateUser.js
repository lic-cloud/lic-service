layui.use(['layer', 'laydate'], function () {
    let layer = layui.layer;
    let playdate = layui.laydate;
    playdate.render({
        elem: '#birthday'
    });
});
showDictSelect("sex","sex");
showDictSelect("status","userStatus");
initRoles();
initData();
$('#form').bootstrapValidator();

function initData() {
    let id = getUrlParam("id");
    if (id != "") {
        $.ajax({
            type: 'get',
            url: '/users/' + id,
            async: false,
            success: function (data) {
                $("#id").val(data.id);
                $("#username").val(data.username);
                $("#nickname").val(data.nickname);
                $("#phone").val(data.phone);
                $("#telephone").val(data.telephone);
                $("#email").val(data.email);
                let match = data.birthday.match(/(\d{4}-\d{2}-\d{2})/);
                $("#birthday").val(match[1]);
                $("#sex").val(data.sex);
                $("#status").val(data.status);
                $("#usedCapacity").val(data.usedCapacity);
                $("#totalCapacity").val(data.totalCapacity);
                if ((data.username) === "admin") {
                    $("#totalCapacity").attr('readonly', 'readonly');
                }
            },
            error: function (xhr, status, error) {
                layer.msg(xhr.responseText, {shift: -1, time: 4000});
            }
        });
        initRoleDatas(id);
    }
}

function update(obj) {
    $(obj).attr("disabled", true);
    let bootstrapValidator = $("#form").data('bootstrapValidator');
    bootstrapValidator.validate();
    if (!bootstrapValidator.isValid()) {
        $(obj).attr("disabled", false);
        return;
    }

    let format2 = $("#form").serializeObject();
    format2.roleIds = getCheckedRoleIds();
    let date = new Date(format2.birthday);
    format2.birthday = date.valueOf();
    $.ajax({
        type: 'put',
        url: '/users',
        contentType: "application/json; charset=utf-8",
        data: JSON.stringify(format2),
        success: function (data) {
            layer.msg("更新用户成功", {shift: -1, time: 1000}, function () {
                location.href = "userList.html";
            });
        },
        error: function (xhr, status, error) {
            $(obj).attr("disabled", false);
            layer.msg(xhr.responseJSON.errors, {shift: -1, time: 4000});
        }
    });
}
