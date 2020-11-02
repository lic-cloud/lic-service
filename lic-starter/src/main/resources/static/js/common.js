$.ajaxSetup({
    cache : false,
    headers : {
        "token" : localStorage.getItem("token")
    },
    error : function(xhr, textStatus, errorThrown) {
        let msg = xhr.responseText;
        let response = JSON.parse(msg);
        let code = response.code;
        let message = response.message;
        if (code == 400) {
            layer.msg(message);
        } else if (code == 401) {
            localStorage.removeItem("token");
            location.href = '/login.html';
        } else if (code == 403) {
            console.log("未授权:" + message);
            layer.msg('未授权');
        } else if (code == 500) {
            layer.msg('系统错误：' + message);
        }
    }
});

function buttonDel(data, permission, pers){
    if(permission != ""){
        if ($.inArray(permission, pers) < 0) {
            return "";
        }
    }

    let btn = $("<button class='layui-btn layui-btn-xs' title='删除' onclick='del(\"" + data +"\")'><i class='layui-icon'>&#xe640;</i></button>");
    return btn.prop("outerHTML");
}

function buttonEdit(href, permission, pers){
    if(permission != ""){
        if ($.inArray(permission, pers) < 0) {
            return "";
        }
    }

    let btn = $("<button class='layui-btn layui-btn-xs' title='编辑' onclick='window.location=\"" + href +"\"'><i class='layui-icon'>&#xe642;</i></button>");
    return btn.prop("outerHTML");
}


function deleteCurrentTab(){
    let lay_id = $(parent.document).find("ul.layui-tab-title").children("li.layui-this").attr("lay-id");
    parent.active.tabDelete(lay_id);
}
