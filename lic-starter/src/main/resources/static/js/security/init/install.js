

showDictSelect("sex", "sex");
showSystemOrCacheSelect("cache");
showSystemOrCacheSelect("system");
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
    ajax_post("/users/register", JSON.stringify(format));
}

function cacheSystem() {
    let format = $("#form2").serializeObject();
    ajax_post("/install/addCacheAndSystem", JSON.stringify(format))
}

function otherSet() {
    let format = $("#form3").serializeObject();
    ajax_post("/install/addOtherSet", JSON.stringify(format))
}

function showSystemOrCacheSelect(id) {
    let data;
    if (id == "cache"){
        data = getCache();
    }else if (id == "system"){
        data = getSystem();
    }
    let select = $("#" + id);
    select.empty();
    $.each(data, function (k, v) {
        select.append("<option value ='" + v + "'>" + k + "</option>");
    });
    return data;
}



function getCache() {
    let type = "cache";
    sessionStorage.clear()
    let v = sessionStorage[type];
    if (v == null || v == "") {
        $.ajax({
            type: 'get',
            url: '/install/getCache',
            async: false,
            success: function (data) {
                v = {};
                $.each(data, function (i, d) {
                    v[d.k] = d.val;
                });
                sessionStorage[type] = JSON.stringify(v);
            }
        });
    }
    return JSON.parse(sessionStorage[type]);
}

function getSystem() {
    let type = "system";
    sessionStorage.clear()
    let v = sessionStorage[type];
    if (v == null || v == "") {
        $.ajax({
            type: 'get',
            url: '/install/getSystem',
            async: false,
            success: function (data) {
                v = {};
                $.each(data, function (i, d) {
                    v[d.k] = d.val;
                });
                sessionStorage[type] = JSON.stringify(v);
            }
        });
    }
    return JSON.parse(sessionStorage[type]);
}


