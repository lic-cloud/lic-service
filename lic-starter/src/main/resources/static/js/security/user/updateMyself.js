initData();
showDictSelect("sex", "sex");
$('#form').bootstrapValidator();
layui.use(['layer', 'laydate'], function () {
    let layer = layui.layer;
    let playdate = layui.laydate;
    playdate.render({
        elem: '#birthday'
    });
});

function initData() {
    $.ajax({
        type: 'get',
        url: '/users/current',
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
            $("#usedCapacity").val(data.usedCapacity);
            $("#totalCapacity").val(data.totalCapacity);
        },
        error: function (xhr, status, error) {
            layer.msg(xhr.responseText, {shift: -1, time: 4000});
        }
    });
}

function update(obj) {
    $(obj).attr("disabled", true);
    let bootstrapValidator = $("#form").data('bootstrapValidator');
    bootstrapValidator.validate();
    if (!bootstrapValidator.isValid()) {
        $(obj).attr("disabled", false);
        return;
    }

    let format = $("#form").serializeObject();
    let date = new Date(format.birthday);
    format.birthday = date.valueOf();
    $.ajax({
        type: 'put',
        url: '/users/updateMyself',
        contentType: "application/json; charset=utf-8",
        data: JSON.stringify(format),
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
