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
 * @param style 提示样式，有alert-info-success、alert-info-danger、alert-info-warning、alert-info-info
 * @param time 消失时间
 */
function prompt(message, style, time) {
    style = (style === undefined) ? 'alert-info-success' : style;
    time = (time === undefined) ? 1200 : time;
    $('<div>')
        .appendTo('body')
        .addClass('alert-info ' + style)
        .html(message)
        .show()
        .delay(time)
        .fadeOut();
}

// 成功提示
function success_prompt(message, time) {
    prompt(message, 'alert-info-success', time);
}

// 失败提示
function fail_prompt(message, time) {
    prompt(message, 'alert-info-danger', time);
}

// 提醒
function warning_prompt(message, time) {
    prompt(message, 'alert-info-warning', time);
}

// 信息提示
function info_prompt(message, time) {
    prompt(message, 'alert-info-info', time);
}

function open_loading() {
    if ($("#loading").length === 0) {
        let loading =
            '<div id="loading" class="loader"><div class="loading">' +
            '<div></div>' +
            '<div></div>' +
            '<div></div>' +
            '<div></div>' +
            '<div></div>' +
            '</div></div>';
        $("body").append(loading);
    } else {
        $("#loading").removeClass("hide-all");
    }
}

function close_loading() {
    $("#loading").addClass("hide-all");
}

function ajax_function(url, data, success_function, method, fail_function, complete) {
    $.ajax({
        type: method,
        url: url,
        data: data,
        beforeSend: open_loading(),
        success: function (data, textStatus, xhr) {
            if (xhr.status === 200) {
                success_function(data);
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
        error: function (data) {
            if (fail_function !== undefined) {
                fail_function(data);
            } else {
                fail_prompt(data);
            }
        }
    });
}

function ajax_get(url, data, success, fail, complete) {
    ajax_function(url, data, success, "GET", fail, complete);
}

function ajax_post(url, data, success, fail, complete) {
    ajax_function(url, data, success, "POST", fail, complete);
}
