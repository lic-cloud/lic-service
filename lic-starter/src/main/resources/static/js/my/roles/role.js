function initRoles() {
    $.ajax({
        type: 'get',
        url: '/roles/all',
        async: false,
        success: function (data) {
            const r = $("#roles");

            for (let i = 0; i < data.length; i++) {
                const d = data[i];
                const id = d['id'];
                const name = d['name'];

                const t = "<label><input type='checkbox' value='" + id + "'>"
                    + name + "</label> &nbsp&nbsp";

                r.append(t);
            }
        }
    });
}

function getCheckedRoleIds() {
    const ids = [];
    $("#roles input[type='checkbox']").each(function () {
        if ($(this).prop("checked")) {
            ids.push($(this).val());
        }
    });

    return ids;
}

function initRoleDatas(userId) {
    $.ajax({
        type: 'get',
        url: '/roles?userId=' + userId,
        success: function (data) {
            const length = data.length;
            for (let i = 0; i < length; i++) {
                $("input[type='checkbox']").each(function () {
                    var v = $(this).val();
                    if (v === data[i]['id']) {
                        $(this).attr("checked", true);
                    }
                });
            }
        }
    });
}
