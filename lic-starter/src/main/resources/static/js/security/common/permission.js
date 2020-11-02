function checkPermission() {
    let pers = [];
    $.ajax({
        type: 'get',
        url: '/permissions/owns',
        contentType: "application/json; charset=utf-8",
        async: false,
        success: function (data) {
            /*检测到permission这个标签就与用户的权限进行比较，若没有就隐藏标签*/
            pers = data;
            $("[permission]").each(function () {
                let per = $(this).attr("permission");
                if ($.inArray(per, data) < 0) {
                    $(this).hide();
                }
            });
        }
    });

    return pers;
}
