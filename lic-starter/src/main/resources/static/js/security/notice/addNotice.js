showDictSelect("status", "noticeStatus");

let pro = window.location.protocol;
let host = window.location.host;
let domain = pro + "//" + host;

let layedit, index;
layui.use(['layedit', 'upload'], function () {
    layedit = layui.layedit;
    layedit.set({
        uploadImage: {
            url: '/files/layui?domain=' + domain + "&token=" + localStorage.getItem("token"),
            type: 'post'
        }
    });
    index = layedit.build('demo');
});

function add(obj) {
    let title = $("#title").val();
    let status = $("#status").val();
    if (title.trim() == "" ) {
        layer.msg("title不能为空")
        return;
    }
    if (title.trim().length < 3 || title.trim().length > 50) {
        layer.msg("title的长度为3-50位")
        return;
    }
    if (status.trim() == "") {
        layer.msg("状态不能为空")
        return;
    }
    $(obj).attr("disabled", true);

    let format = $("#form").serializeObject();
    format.title = title;
    format.status = status;
    format.content = layedit.getContent(index);

    $.ajax({
        type: 'post',
        url: '/notices',
        contentType: "application/json; charset=utf-8",
        data: JSON.stringify(format),
        success: function (data) {
            layer.msg("添加成功", {shift: -1, time: 1000}, function () {
                location.href = "noticeList.html";
            });
        },
        error: function (xhr, status, error) {
            $(obj).attr("disabled", false);
            console.log(xhr)
            layer.msg(xhr.responseText, {shift: -1, time: 4000});
        }
    });
}
