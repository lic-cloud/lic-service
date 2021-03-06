layui.use(['layer'], function () {
    let layer = layui.layer;
});
let userStatus = showDictSelect("status", "userStatus", true);

let pers = checkPermission();

let example;

function init() {
    example =
        $('#dt-table').DataTable({
            "searching": false,/*搜索样式禁用*/
            "processing": false,
            "serverSide": true,/*服务端分页*/
            "language": {/*汉化*/
                "url": "/js/plugin/datatables/Chinese.lang"
            },
            "ajax": {
                "url": "/users",
                "type": "get",
                "data": function (d) {/*搜索条件*/
                    d.username = $('#username').val();
                    d.nickname = $('#nickname').val();
                    d.status = $('#status').val();
                },
                "error": function (xhr, textStatus, errorThrown) {
                    let msg = xhr.responseText;
                    let response = JSON.parse(msg);
                    let code = response.code;
                    let message = response.message;
                    if (code == 400) {
                        layer.msg(message);
                    } else if (code == 401) {
                        localStorage.removeItem("token");
                        layer.msg("token过期，请先登录", {shift: -1, time: 1000}, function () {
                            location.href = '/login.html';
                        });
                    } else if (code == 403) {
                        console.log("未授权:" + message);
                        layer.msg('未授权');
                    } else if (code == 500) {
                        layer.msg('系统错误：' + message);
                    }
                }
            },
            "dom": "<'dt-toolbar'r>t<'dt-toolbar-footer'<'col-sm-10 col-xs-12 hidden-xs'i><'col-xs-12 col-sm-10' p v>>",
            "columns": [/*数据的渲染*/
                {"data": "username", "defaultContent": ""},
                {"data": "nickname", "defaultContent": ""},
                {"data": "phone", "defaultContent": ""},
                {"data": "email", "defaultContent": ""},
                {
                    "data": "status",
                    "defaultContent": "",
                    "orderable":false,
                    "render": function (data, type, row) {
                        return userStatus[data];
                    }
                },
                {
                    "data": "usedCapacity",
                    "defaultContent": "",
                    "orderable":false,
                    "render": function (data, type, row) {
                        return data / 1024 > 0 ? data / 1024 + "G" : data + "MB";
                    }
                },
                {
                    "data": "totalCapacity",
                    "defaultContent": "",
                    "orderable":false,
                    "render": function (data, type, row) {
                        return data / 1024 > 0 ? data / 1024 + "G" : data + "MB";
                    }
                },
                {
                    "data": "",
                    "defaultContent": "",
                    "orderable": false,
                    "render": function (data, type, row) {
                        let id = row['id'];
                        let href = "updateUser.html?id=" + id;
                        return buttonEdit(href, "sys:user:add", pers);
                    }
                },

            ],/*升序降序*/
            "order": [[0, "desc"], [1, "asc"]]
        });
}

$("#searchBt").click(function () {
    example.ajax.reload();
});

init();
