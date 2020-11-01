initInterface()

function initInterface() {
    let token = localStorage.getItem("token");
    $.ajax({
        type: 'get',
        url: '/users/count',
        success: function (data) {
            if (data === 1) {
                if (top !== self && token == null && token.trim().length === 0) {
                    location.href = '/login.html';
                }
            } else if (data === 0) {
                location.href = '/init.html';
            }
        }
    });
}

