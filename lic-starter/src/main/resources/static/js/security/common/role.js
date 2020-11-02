function initRoles() {
    $.ajax({
        type : 'get',
        url : '/roles/all',
        async : false,
        success : function(data) {
            let r = $("#roles");

            for (let i = 0; i < data.length; i++) {
                let d = data[i];
                let id = d['id'];
                let name = d['name'];

                let t = "<label><input type='checkbox' value='" + id + "'>"
                    + name + "</label> &nbsp&nbsp";

                r.append(t);
            }
        }
    });
}

function getCheckedRoleIds() {
    let ids = [];
    $("#roles input[type='checkbox']").each(function() {
        if ($(this).prop("checked")) {
            ids.push($(this).val());
        }
    });

    return ids;
}

function initRoleDatas(userId) {
    $.ajax({
        type : 'get',
        url : '/roles?userId=' + userId,
        success : function(data) {
            let length = data.length;
            for (let i = 0; i < length; i++) {
                $("input[type='checkbox']").each(function() {
                    let v = $(this).val();
                    if (v == data[i]['id']) {
                        $(this).attr("checked", true);
                    }
                });
            }
        }
    });
}
