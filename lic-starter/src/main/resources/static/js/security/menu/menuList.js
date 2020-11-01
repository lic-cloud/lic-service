const pers = checkPermission();
initMenuList();

function initMenuList() {
    $.ajax({
        type: 'get',
        url: '/permissions',
        contentType: "application/json; charset=utf-8",
        async: false,
        success: function (data) {
            const length = data.length;
            for (let i = 0; i < length; i++) {
                const d = data[i];
                let tr = "<tr data-tt-id='" + d['id'] + "' data-tt-parent-id='" + d['parentId'] + "'>";
                const td1 = "<td>" + d['name'] + "</td>";
                tr += td1;
                let id = "<td>" + d['id'] + "</td>";
                tr += id;
                let href = "";
                if (d['href'] != null) {
                    href = d['href'];
                }
                const td2 = "<td>" + href + "</td>";
                tr += td2;

                let permission = d['permission'];
                if (permission == null) {
                    permission = "";
                }

                const td3 = "<td>" + permission + "</td>";
                tr += td3;

                let sort = d['sort'];
                if (sort == 0) {
                    sort = "";
                }

                const td4 = "<td>" + sort + "</td>";
                tr += td4;

                id = d['id'];
                href = "updateMenu.html?id=" + id;
                const edit = buttonEdit(href, "sys:menu:add", pers);
                const del = buttonDel(id, "sys:menu:del", pers);
                tr += "<td>" + edit + del + "</td>";
                tr += "</tr>"
                $("#dt-table").append(tr);
            }
        },
        error: function (xhr, status, error) {
            layer.msg(xhr.responseText, {shift: -1, time: 4000});
        }
    });
}

layui.use('layer', function () {
    const layer = layui.layer;
});

function del(id) {
    layer.confirm('确定要删除吗？', {
        btn: ['确定', '取消']
    }, function () {
        $.ajax({
            type: 'delete',
            url: '/permissions/' + id,
            success: function (data) {
                location.reload();
            },
            error: function (xhr, status, error) {
                layer.msg(xhr.responseText, {shift: -1, time: 4000});
            }
        });
    });
}

const option = {
    expandable: true,
    clickableNodeNames: true,
    onNodeExpand: function () {
        const d = this;
        console.log(d['id']);
        console.log(d['parentId']);
    },
    onNodeCollapse: function () {
        const d = this;
        console.log(d['id'] + "Collapse");
        console.log(d['parentId'] + "Collapse");
    }

};

$("#dt-table").treetable(option);
