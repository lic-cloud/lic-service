function getStep() {
    $.ajax({
        type: 'get',
        cache:false,
        url: '/install/step',
        success: function (data) {
            localStorage.removeItem("initStatus");
            localStorage.setItem("initStatus", data);
        }
    });
}

