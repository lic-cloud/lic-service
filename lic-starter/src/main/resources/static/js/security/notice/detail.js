initData();

function initData() {
    let id = getUrlParam("id");
    if (id != "") {
        $.ajax({
            type: 'get',
            url: '/notices?id=' + id,
            async: false,
            success: function (data) {
                let notice = data.notice;
                if (notice != null) {
                    $("#title").text(notice.title);
                    $("#status").val(notice.status);
                    $("#demo").text(notice.content);
                    $("#updateTime").text(notice.updateTime);
                }

                let users = data.users;
                if (users != null) {
                    for (let i = 0; i < users.length; i++) {
                        let u = users[i];
                        $("#users").append("<li>" + u.nickname + "</li>");
                    }
                }
            },
            error: function (xhr, status, error) {
                layer.msg(xhr.responseText, {shift: -1, time: 4000});
            }
        });

    }
}

let layedit, index;
layui.use('layedit', function () {
    layedit = layui.layedit;
    index = layedit.build('demo', {
        tool: ['strong']
    });
});
