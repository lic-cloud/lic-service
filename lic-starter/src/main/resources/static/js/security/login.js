layui.use('layer', function () {
    let layer = layui.layer;
});
//判断当前用户页面是不是最外面的页面
/*if (top !== self) {
    parent.location.href = '/login.html';
}*/
//当用户登录后 访问登录页面默认不会跳转 设置跳转到主页面
//获取登录成功后存入的token
let token = localStorage.getItem("token");
//登录过了走这个流程
if (token != null && token.trim().length != 0) {
    $.ajax({
        type: 'get',
        url: '/users/current?token=' + token,//获取当前登录用户 看是否为空
        success: function (data) {
            location.href = '/index.html';
        },
        error: function (xhr, textStatus, errorThrown) {
            let msg = xhr.responseText;
            let response = JSON.parse(msg);
            let code = response.code;
            let message = response.message;
            //如果过期,去除token
            if (code == 401) {
                localStorage.removeItem("token");
            }
        }
    });
}

function login(obj) {
    $('#login-form').bootstrapValidator();
    //把按钮禁用掉
    $(obj).attr("disabled", true);
    let bootstrapValidator = $("#login-form").data('bootstrapValidator');
    //触发全部验证
    bootstrapValidator.validate();
    //获取当前表单验证状态
    if (!bootstrapValidator.isValid()) {
        $(obj).attr("disabled", false);
        return;
    }
    $.ajax({
        type: 'post',
        url: '/login',
        data: $("#login-form").serialize(),
        success: function (data) {
            //token存到前端里面
            localStorage.setItem("token", data.token);
            location.href = '/index.html';
        },
        error: function (xhr, textStatus, errorThrown) {
            let msg = xhr.responseText;
            let response = JSON.parse(msg);
            layer.msg(response.message, {shift: -1, time: 4000});
            $(obj).attr("disabled", false);
        }
    });
}
