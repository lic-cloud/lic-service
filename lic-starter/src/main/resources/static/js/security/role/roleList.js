var pers = checkPermission();
var example;
layui.use('layer', function () {
    var layer = layui.layer;
});

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
                "url": "/roles",
                "type": "get",
                "data": function (d) {
                    d.name = $('#name').val();
                },
                "error": function (xhr, textStatus, errorThrown) {
                    var msg = xhr.responseText;
                    console.log(msg);
                    var response = JSON.parse(msg);
                    var code = response.code;
                    var message = response.message;
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
                {"data": "name", "defaultContent": ""},
                {"data": "description", "defaultContent": ""},
                {
                    "data": "",
                    "defaultContent": "",
                    "orderable": false,
                    "render": function (data, type, row) {
                        return row['updateAt'];
                    }
                },
                {
                    "data": "",
                    "defaultContent": "",
                    "orderable": false,
                    "render": function (data, type, row) {
                        var id = row['id'];
                        var href = "addRole.html?id=" + id;
                        var edit = buttonEdit(href, "sys:role:add", pers);
                        var del = buttonDel(id, "sys:role:del", pers);
                        return edit + del;
                    }
                },
            ],
            "order": [[0, "desc"], [1, "asc"]]
        });
}

function del(id) {
    layer.confirm('确定要删除吗？', {
        btn: ['确定', '取消']
    }, function () {
        $.ajax({
            type: 'delete',
            url: '/roles/' + id,
            success: function (data) {
                example.ajax.reload();
                layer.msg("删除成功");
            },
            error(xhr, status, error){
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
