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
    ajax_post("/users/register", JSON.stringify(format), function () {
        localStorage.removeItem("initStatus");
        localStorage.setItem("initStatus", initStep.step2);
        success_prompt("添加成功");
    }, function (data) {
        let message = JSON.parse(data.responseText).message;
        fail_prompt(message);
    })
}

function cacheSystem() {
    let format = $("#form2").serializeObject();
    ajax_post("/install/addCacheAndSystem", JSON.stringify(format), function () {
        localStorage.removeItem("initStatus");
        localStorage.setItem("initStatus", initStep.step3);
        success_prompt("添加成功");
    }, function (data) {
        let message = JSON.parse(data.responseText).message;
        fail_prompt(message);
    })
}

function otherSet() {
    let format = $("#form3").serializeObject();
    ajax_post("/install/addOtherSet", JSON.stringify(format), function () {
        localStorage.removeItem("initStatus");
        localStorage.setItem("initStatus", initStep.finish);
        success_prompt("添加成功");
    }, function (data) {
        let message = JSON.parse(data.responseText).message;
        fail_prompt(message);
    })
}
