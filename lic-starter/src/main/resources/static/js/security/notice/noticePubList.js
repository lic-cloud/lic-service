layui.use(['layer', 'laydate'], function () {
    const layer = layui.layer;
    const laydate = layui.laydate;
    laydate.render({
        elem: '#beginTime'
    });
    laydate.render({
        elem: '#endTime'
    });
});

const isRead = showDictSelect("isRead", "isRead", true);

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
                "url": "/notices/published",
                "type": "get",
                "data": function (d) {
                    d.title = $('#title').val();
                    d.beginTime = $('#beginTime').val();
                    d.endTime = $('#endTime').val();
                    d.isRead = $('#isRead').val();
                }
            },
            "dom": "<'dt-toolbar'r>t<'dt-toolbar-footer'<'col-sm-10 col-xs-12 hidden-xs'i><'col-xs-12 col-sm-10' p v>>",
            "columns": [
                {"data": "title", "defaultContent": ""},
                {
                    "data": "create_at",
                    "defaultContent": "",
                    "orderable": false,
                    "render": function (data, type, row) {
                        return row['createAt'];
                    }
                },
                {
                    "data": "",
                    "defaultContent": "",
                    "orderable": false,
                    "render": function (data, type, row) {
                        const isRead = row['isRead'];
                        if (isRead) {
                            return "已读";
                        }
                        return "<span style='color:red'>未读</span>";
                    }
                },
                {"data": "readTime", "defaultContent": "","orderable": false},
                {
                    "data": "",
                    "defaultContent": "",
                    "orderable": false,
                    "render": function (data, type, row) {
                        const id = row['id'];
                        return "<button class='layui-btn layui-btn-xs' title='详情' onclick='showDetail(this,\"" + id + "\")'><i class='layui-icon'>&#xe65f;</i></button>";
                    }
                },
            ],
            "order": [0, "desc"]
        });
}

function showDetail(obj, id) {
    const tr = $(obj).parents("tr")[0];
    const td = $(tr).find("td").eq(2);
    const v = $(td).text();
    const isRead = (v === "已读");
    if (!isRead) {
        $(td).text("已读");
    }

    layer.open({
        type: 2,
        title: false,
        area: ['800px', '400px'],
        shadeClose: true,
        content: ['detail.html?id=' + id]
    });

    if (!isRead) {
        parent.$("[unreadNotice]").each(function () {
            let n = $(this).text();
            if (!isNaN(n) && n > 0) {
                n = n - 1;
                if (n > 0) {
                    $(this).html("<span class='layui-badge'>" + n + "</span>");
                } else {
                    $(this).html("");
                }
            }
        });
    }
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
                layer.msg(xhr.responseText, {shift: -1, time: 1000});
            }
        });

        layer.close(1);
    });
}

$("#searchBt").click(function () {
    example.ajax.reload();
});

init();
