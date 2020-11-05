var initStep = {
    "step1": "register_root_user",
    "step2": "file_sys_config",
    "step3": "cache_sys_config",
    "finish": "finished"
}
var initStepIndex = ["step1", "step2", "step3", "finish"];

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
        localStorage.setItem("initStatus", initStep["step1"]);
    })
}

function cacheSystem() {
    let format = $("#form2").serializeObject();
    ajax_post("/install/addCacheAndSystem", JSON.stringify(format),
            function () {
                localStorage.removeItem("initStatus");
                localStorage.setItem("initStatus", initStep["step3"]);
            }
        )
}

function otherSet() {
    let format = $("#form3").serializeObject();
    ajax_post("/install/addOtherSet", JSON.stringify(format), function () {
           localStorage.removeItem("initStatus");
           localStorage.setItem("initStatus", initStep["finish"]);
        }
    )
}

function goLogin() {
    location.href = '/login.html';
}
