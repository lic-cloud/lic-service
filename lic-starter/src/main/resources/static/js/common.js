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


//form序列化为json
$.fn.serializeObject = function()
{
    let o = {};
    let a = this.serializeArray();
    $.each(a, function() {
        if (o[this.name] !== undefined) {
            if (!o[this.name].push) {
                o[this.name] = [o[this.name]];
            }
            o[this.name].push(this.value || '');
        } else {
            o[this.name] = this.value || '';
        }
    });
    return o;
};

//获取url后的参数值
function getUrlParam(key) {
    let href = window.location.href;
    let url = href.split("?");
    if(url.length <= 1){
        return "";
    }
    let params = url[1].split("&");

    for(let i=0; i<params.length; i++){
        let param = params[i].split("=");
        if(key == param[0]){
            return param[1];
        }
    }
}


/**
 * 弹出式提示框，默认1.2秒自动消失
 * @param message 提示信息
 * @param style 提示样式，有alert-success、alert-danger、alert-warning、alert-info
 * @param time 消失时间
 */
var prompt = function (message, style, time)
{
    style = (style === undefined) ? 'alert-success' : style;
    time = (time === undefined) ? 1200 : time;
    if ($("#prompt").length === 0) {
        let prompt = '<div id="prompt" class="alert prompt"></div>';
        $("body").append(prompt);
    } else {
        $("#prompt").empty();
    }
    $('#prompt')
        .addClass('alert ' + style)
        .html(message)
        .show()
        .delay(time)
        .fadeOut();
};

// 成功提示
var success_prompt = function(message, time)
{
    prompt(message, 'alert-success', time);
};

// 失败提示
var fail_prompt = function(message, time)
{
    prompt(message, 'alert-danger', time);
};

// 提醒
var warning_prompt = function(message, time)
{
    prompt(message, 'alert-warning', time);
};

// 信息提示
var info_prompt = function(message, time)
{
    prompt(message, 'alert-info', time);
};


function open_loading() {
    // if ($("#loading").length === 0) {
    //     let loading =
    //         '<div id="loading" class="loader"><div class="loading">' +
    //         '<div></div>' +
    //         '<div></div>' +
    //         '<div></div>' +
    //         '<div></div>' +
    //         '<div></div>' +
    //         '</div></div>';
    //     $("body").append(loading);
    // } else {
    //     $("#loading").removeClass("hide-all");
    // }
    //TODO 完善加载动画
}

function close_loading() {
    //$("#loading").addClass("hide-all");
}

function ajax_function(url, data, success_function, method, fail_function, complete, async=true) {
    let returnObj;
    $.ajax({
        type: method,
        url: url,
        async: async,
        data: data,
        contentType: "application/json; charset=utf-8",
        beforeSend: open_loading(),
        success: function (data, textStatus, xhr) {
            if (xhr.status === 200) {
                success_function(data);
                returnObj = data
            } else {
                if (fail_function !== undefined) {
                    fail_function(data);
                } else {
                    fail_prompt(data);
                }
            }
        },

        complete: function () {
            if (complete !== undefined) {
                complete(data.responseJSON);
            }
            close_loading();
        },
        error: function (rsp, textStatus, xhr) {
            let data = rsp.responseJSON.message;
            if (fail_function !== undefined) {
                fail_function(data);
            } else {
                fail_prompt(data);
            }
        }
    });
    return returnObj;
}

function ajax_get(url, data, success, fail, complete) {
    ajax_function(url, data, success, "GET", fail, complete);
}

function ajax_post(url, data, success, fail, complete) {
    ajax_function(url, data, success, "POST", fail, complete);
}
function ajax_sync_get(url, data, success, fail, complete) {
    return ajax_function(url, data, success, "GET", fail, complete, false);
}



String.format = function() {
    if (arguments.length == 0)
        return null;
    let str = arguments[0];
    for ( let i = 1; i < arguments.length; i++) {
        let re = new RegExp('\\{' + (i - 1) + '\\}', 'gm');
        str = str.replace(re, arguments[i]);
    }
    return str;
};
