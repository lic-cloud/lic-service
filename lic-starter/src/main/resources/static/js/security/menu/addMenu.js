layui.use('layer', function () {
    const layer = layui.layer;
});

$('#form').bootstrapValidator();
initParentMenuSelect();

function add(obj) {
    $(obj).attr("disabled", true);
    const bootstrapValidator = $("#form").data('bootstrapValidator');
    bootstrapValidator.validate();
    if (!bootstrapValidator.isValid()) {
        $(obj).attr("disabled", true);
        return;
    }
    const format = $("#form").serializeObject();

    $.ajax({
        type: 'post',
        url: '/permissions',
        contentType: "application/json; charset=utf-8",
        data: JSON.stringify(format),
        success: function (data) {
            layer.msg("添加成功", {shift: -1, time: 1000}, function () {
                location.href = "menuList.html";
            });
        },
        error: function (xhr, status, error) {
            $(obj).attr("disabled", false);
            layer.msg(xhr.responseText, {shift: -1, time: 4000});
        }
    });
}

function selectCss() {
    layer.open({
        type: 2,
        title: "样式",
        area: ['800px', '400px'],
        maxmin: true,
        shadeClose: true,
        content: ['icon.html']
    });
}
