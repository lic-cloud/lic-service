let initStep = {
    step1: "register_root_user",
    step2: "file_sys_config",
    step3: "cache_sys_config",
    finish: "finished"
}
let initStepIndex = ["register_root_user", "file_sys_config", "cache_sys_config", "finished"];

function getStep() {
    ajax_get("/install/step", "", function (data) {
        localStorage.removeItem("initStatus");
        localStorage.setItem("initStatus", initStepIndex[data])
    })
}

