showDictSelect("status", "noticeStatus");
initData();

function initData() {
    const id = getUrlParam("id");
    if (id !== "") {
        $.ajax({
            type: 'get',
            url: '/notices/' + id,
            async: false,
            success: function (data) {
                $("#id").val(data.id);
                $("#title").val(data.title);
                $("#status").val(data.status);
                $("#demo").text(data.content);
            },
            error: function (xhr, status, error) {
                layer.msg(xhr.responseText, {shift: -1, time: 4000});
            }
        });

    }
}

const pro = window.location.protocol;
const host = window.location.host;
const domain = pro + "//" + host;

let layedit, index;
layui.use('layedit', function () {
    layedit = layui.layedit;
    layedit.set({
        uploadImage: {
            url: '/files/layui?domain=' + domain + "&token=" + localStorage.getItem("token"),
            type: 'post'
        }
    });
    index = layedit.build('demo');
});


function update(obj) {
    //TODO 内容未进行判断
    const title = $("#title").val();
    const status = $("#status").val();
    if (title.trim() === "") {
        layer.msg("title不能为空")
        return;
    }
    if (title.trim().length < 3 || title.trim().length > 50) {
        layer.msg("title的长度为3-50")
        return;
    }
    if (status.trim() === "") {
        layer.msg("状态不能为空")
        return;
    }
    $(obj).attr("disabled", true);

    const format = $("#form").serializeObject();
    format.id = $("#id").val();
    format.title = title;
    format.status = status;
    format.content = layedit.getContent(index);

    $.ajax({
        type: 'put',
        url: '/notices',
        contentType: "application/json; charset=utf-8",
        data: JSON.stringify(format),
        success: function (data) {
            layer.msg("修改成功", {shift: -1, time: 1000}, function () {
                location.href = "noticeList.html";
            });
        },
        error: function (xhr, status, error) {
            layer.msg(xhr.responseText, {shift: -1, time: 4000});
        }
    });
}
