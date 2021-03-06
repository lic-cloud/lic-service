layui.use('layer', function () {
    let layer = layui.layer;
});

$.fn.zTree.init($("#treeDemo"), getSettting(), getMenuTree());
initData();

function initData() {
    let id = getUrlParam("id");
    if (id != "") {
        $.ajax({
            type: 'get',
            url: '/roles/' + id,
            async: false,
            success: function (data) {
                $("#id").val(data.id);
                $("#name").val(data.name);
                $("#description").val(data.description);
            },
            error: function (xhr, status, error) {
                layer.msg(xhr.responseText, {shift: -1, time: 4000});
            }
        });
        initMenuDatas(id);
    }
}


$('#form').bootstrapValidator();

function add(obj) {
    $(obj).attr("disabled", true);
    let bootstrapValidator = $("#form").data('bootstrapValidator');
    bootstrapValidator.validate();
    if (!bootstrapValidator.isValid()) {
        $(obj).attr("disabled", false);
        return;
    }

    let format = $("#form").serializeObject();
    format.permissionIds = getCheckedMenuIds();

    $.ajax({
        type: 'post',
        url: '/roles',
        contentType: "application/json; charset=utf-8",
        data: JSON.stringify(format),
        success: function (data) {
            layer.msg("角色添加成功", {shift: -1, time: 1000}, function () {
                location.href = "roleList.html";
            });
        },
        error: function (xhr, status, error) {
            $(obj).attr("disabled", false);
            layer.msg(xhr.responseJSON.errors, {shift: -1, time: 4000});
        }
    });
}
