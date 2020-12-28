function forgetPassword() {
    layer.open({
        title: ['找回密码', 'font-size:18px;'],
        type: 2,
        area: ['400px', '400px'],
        fixed: false,
        shadeClose: true,
        //skin: "layui-layer-lan",
        resize: false,
        closeBtn: 1,
        content: "../../pages/user/forgetPassword.html"
    });
}

$('#form').bootstrapValidator();

function retrievePassword(obj) {
    $(obj).attr("disabled", true);
    let bootstrapValidator = $("#form").data('bootstrapValidator');
    bootstrapValidator.validate();
    if (!bootstrapValidator.isValid()) {
        $(obj).attr("disabled", false);
        return;
    }
    let format = $("#form").serializeObject();
    ajax_post("/users/retrievePassword", JSON.stringify(format), function () {
        $(obj).attr("disabled", false);
        document.getElementById("form").reset();
        success_prompt("找回成功", 1000);
    }, function () {
        $(obj).attr("disabled", false);
        fail_prompt("用户不存在", 4000);
    })
}
