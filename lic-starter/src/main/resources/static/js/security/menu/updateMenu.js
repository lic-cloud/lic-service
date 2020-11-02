layui.use('layer', function () {
    let layer = layui.layer;
});

initParentMenuSelect();
let id = getUrlParam("id");
initData();
$('#form').bootstrapValidator();

function initData() {
    if (id != "") {
        $.ajax({
            type: 'get',
            url: '/permissions/' + id,
            async: false,
            success: function (data) {
                $("#id").val(data.id);
                $("#parentId").val(data.parentId);
                $("#name").val(data.name);
                let css = data.css;
                $("#css").val(css);
                $("#href").val(data.href);
                $("#type").val(data.type);
                $("#permission").val(data.permission);
                $("#sort").val(data.sort);
                if (css != "") {
                    $("#cssImg").addClass("fa");
                    $("#cssImg").addClass(css);
                }
            },
            error: function (xhr, status, error) {
                layer.msg(xhr.responseText, {shift: -1, time: 4000});
            }
        });
    }
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

function update(obj) {
    $(obj).attr("disabled", true);
    if ($("#parentId").val() == id) {
        layer.msg("父级菜单不能是自己");
        return;
    }
    let bootstrapValidator = $("#form").data('bootstrapValidator');
    bootstrapValidator.validate();
    if (!bootstrapValidator.isValid()) {
        $(obj).attr("disabled", false);
        return;
    }

    let format = $("#form").serializeObject();

    $.ajax({
        type: 'put',
        url: '/permissions/',
        contentType: "application/json; charset=utf-8",
        data: JSON.stringify(format),
        success: function (data) {
            layer.msg("修改成功", {shift: -1, time: 1000}, function () {
                location.href = "menuList.html";
            });
        },
        error: function (xhr, status, error) {
            $(obj).attr("disabled", false);
            layer.msg(xhr.responseText, {shift: -1, time: 4000});
        }
    });
}
