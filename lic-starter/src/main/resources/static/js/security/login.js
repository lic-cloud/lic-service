/*生成 [1,max]，max=10 的随机数*/
$("#captchaOperation").html(Math.ceil(10 * Math.random()) + " + " + Math.ceil(10 * Math.random()));
layui.use('layer', function () {
    const layer = layui.layer;
});
//判断当前用户页面是不是最外面的页面
if (top !== self) {
    parent.location.href = '/login.html';
}
//当用户登录后 访问登录页面默认不会跳转 设置跳转到主页面
//获取登录成功后存入的token
const token = localStorage.getItem("token");
//登录过了走这个流程
if (token != null && token.trim().length !== 0) {
    $.ajax({
        type: 'get',
        url: '/users/current?token=' + token,//获取当前登录用户 看是否为空
        success: function (data) {
            location.href = '/index.html';
        },
        error: function (xhr, textStatus, errorThrown) {
            const msg = xhr.responseText;
            const response = JSON.parse(msg);
            const code = response.code;
            const message = response.message;
            //如果过期,去除token
            if (code === 401) {
                localStorage.removeItem("token");
            }
        }
    });
}
//表单校验规则
$('#login-form').bootstrapValidator({
    fields: {
        captcha: {
            validators: {
                callback: {
                    message: 'Wrong answer',
                    callback: function (value, validator) {
                        const items = $('#captchaOperation').html().split(' '),
                            sum = parseInt(items[0]) + parseInt(items[2]);
                        return value == sum;
                    }
                }
            }
        }
    }
});

function login(obj) {
    //把按钮禁用掉
    $(obj).attr("disabled", true);
    const bootstrapValidator = $("#login-form").data('bootstrapValidator');
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
            const msg = xhr.responseText;
            const response = JSON.parse(msg);
            layer.msg(response.message, {shift: -1, time: 4000});
            $(obj).attr("disabled", false);
        }
    });
}
