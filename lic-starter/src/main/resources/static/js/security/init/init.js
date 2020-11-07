var initStepIndex = ["step1", "step2", "step3", "finish"];
var initStep = {
    "step1": "register_root_user",
    "step2": "file_sys_config",
    "step3": "cache_sys_config",
    "finish": "finished"
}

function getStep() {
    let curStep;
    $.ajax({
        type: 'get',
        cache:false,
        async: false,
        url: '/install/step',
        success: function (data) {
            curStep = initStepIndex[data];
        }
    });
    return curStep;
}

