let pers = checkPermission();
layui.use(['layer', 'laydate'], function () {
    let layer = layui.layer;
    let playdate = layui.laydate;
    playdate.render({
        elem: '#beginTime'
    });
    playdate.render({
        elem: '#endTime'
    });
});

let noticeStatus = showDictSelect("status", "noticeStatus", true);

let example;

function init() {
    example =
        $('#dt-table').DataTable({
            "searching": false,
            "processing": false,
            "serverSide": true,
            "language": {
                "url": "/js/plugin/datatables/Chinese.lang"
            },
            "ajax": {
                "url": "/notices",
                "type": "get",
                "data": function (d) {
                    d.title = $('#title').val();
                    d.status = $('#status').val();
                    d.beginTime =new Date($('#beginTime').val()).valueOf();
                    d.endTime =new Date($('#endTime').val()).valueOf();
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
                        layer.msg("token过期，请先登录", {shift: -1, time: 4000}, function () {
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
            "columns": [
                {"data": "id", "defaultContent": ""},
                {"data": "title", "defaultContent": ""},
                {
                    "data": "",
                    "defaultContent": "",
                    "orderable": false,
                    "render": function (data, type, row) {
                        return row['updateAt'];
                    }
                },
                {
                    "data": "status",
                    "defaultContent": "",
                    "render": function (data, type, row) {
                        return noticeStatus[data];
                    }
                },
                {
                    "data": "",
                    "defaultContent": "",
                    "orderable": false,
                    "render": function (data, type, row) {
                        let id = row['id'];
                        let status = row['status'];
                        let href = "updateNotice.html?id=" + id;
                        let edit = buttonEdit(href, "notice:add", pers);
                        if (status == 1) {
                            edit = "<button class='layui-btn layui-btn-xs' style='background-color:#c2c2c2;' title='不可编辑'><i class='layui-icon' style='color: #F0F0F0;'>&#xe642;</i></button>";
                        }
                        let del = buttonDel(id, "notice:del", pers);
                        return edit + del + "<button class='layui-btn layui-btn-xs' title='详情' onclick='showDetail(\"" + id + "\")'><i class='layui-icon'>&#xe65f;</i></button>";
                    }
                },
            ],
            "order": [[0, "desc"], [1, "desc"]]
        });
}

function showDetail(id) {
    layer.open({
        type: 2,
        title: false,
        area: ['800px', '400px'],
        shadeClose: true,
        content: ['detail.html?id=' + id]
    });
}

function del(id) {
    layer.confirm('确定要删除吗？', {
        btn: ['确定', '取消']
    }, function () {
        $.ajax({
            type: 'delete',
            url: '/notices/' + id,
            success: function (data) {
                example.ajax.reload();
                layer.msg("删除成功");
            },
            error: function (xhr, status, error) {
                layer.msg(xhr.responseText, {shift: -1, time: 4000});
            }
        });

        layer.close(1);
    });
}

$("#searchBt").click(function () {
    example.ajax.reload();
});

init();
