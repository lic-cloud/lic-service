showDictSelect("sex", "sex");
layui.use(['layer', 'laydate'], function () {
    let layer = layui.layer;
    let playdate = layui.laydate;
    playdate.render({
        elem: '#birthday'
    });
});
$('#form').bootstrapValidator();
function add() {
    let format = $("#form").serializeObject();
    let date = new Date(format.birthday);
    format.birthday = date.valueOf();
    $.ajax({
        type: 'post',
        url: '/users/register',
        contentType: "application/json; charset=utf-8",
        data: JSON.stringify(format),
        success: function (data) {
            localStorage.removeItem("initStatus");
            localStorage.setItem("initStatus", "step2");
            layer.msg("添加成功", {shift: -1, time: 1000});
        },
        error: function (xhr, status, error) {
            layer.msg(xhr.responseText, {shift: -1, time: 4000});
        }
    });
}

function cacheSystem() {
    let format = $("#form2").serializeObject();
    $.ajax({
        type: 'post',
        url: '/install/addCacheAndSystem',
        contentType: "application/json; charset=utf-8",
        data: JSON.stringify(format),
        success: function (data) {
            localStorage.removeItem("initStatus");
            localStorage.setItem("initStatus", "step3");
            layer.msg("添加成功", {shift: -1, time: 1000});
        },
        error: function (xhr, status, error) {
            layer.msg(xhr.responseText, {shift: -1, time: 4000});
        }
    });
}

function otherSet() {
    let format = $("#form3").serializeObject();
    $.ajax({
        type: 'post',
        url: '/install/addOtherSet',
        contentType: "application/json; charset=utf-8",
        data: JSON.stringify(format),
        success: function (data) {
            localStorage.removeItem("initStatus");
            localStorage.setItem("initStatus", "finish");
            layer.msg("添加成功", {shift: -1, time: 1000});
        },
        error: function (xhr, status, error) {
            layer.msg(xhr.responseText, {shift: -1, time: 4000});
        }
    });
}

function goLogin() {
    location.href = '/login.html';
}
