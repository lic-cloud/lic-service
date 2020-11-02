let pers = checkPermission();

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
                "url": "/dicts",
                "type": "get",
                "data": function (d) {
                    d.type = $("#type").val();
                },
                "error": function (xhr, textStatus, errorThrown) {
                    let msg = xhr.responseText;
                    console.log(msg);
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
                {"data": "type", "defaultContent": ""},
                {"data": "k", "defaultContent": ""},
                {"data": "val", "defaultContent": ""},
                {
                    "data": "",
                    "defaultContent": "",
                    "orderable":false,
                    "render": function (data, type, row) {
                        return row['createAt']
                    }
                },
                {
                    "data": "",
                    "defaultContent": "",
                    "orderable":false,
                    "render": function (data, type, row) {
                        return row['updateAt']
                    }
                },
                {
                    "data": "",
                    "defaultContent": "",
                    "orderable": false,
                    "render": function (data, type, row) {
                        let id = row['id'];
                        let href = "updateDict.html?id=" + id;
                        let edit = buttonEdit(href, "dict:add", pers);
                        let del = buttonDel(id, "dict:del", pers);
                        return edit + del;
                    }
                },

            ],
            "order": [[0, "asc"]]
        });
}

layui.use('layer', function () {
    let layer = layui.layer;
});

function del(id) {
    layer.confirm('确定要删除吗？', {
        btn: ['确定', '取消']
    }, function () {
        $.ajax({
            type: 'delete',
            url: '/dicts/' + id,
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
