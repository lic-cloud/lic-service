//    用户名
function YHMonblus(){
    var username=document.getElementById("username");
    // var reN =/^\d{6,18}$/;
    var re = /^[a-zA-Z_]{6,18}$/;
    if(username.value==""){
        document.getElementById('YHMerror').innerText="请输入用户名";
    }
    else if(username.value.length < 6 ||username.value.length > 18){
        console.log(username.value);
        document.getElementById('YHMerror').innerText="格式错误,长度应为6-18个字符";
    }
    else if(!re.test(username.value)){

        document.getElementById('YHMerror').innerText="格式错误,只能包含英文字母和下划线";
    }
    else {
        document.getElementById('YHMerror').innerText ="";
    }
}
function YHMonfocu(){
    document.getElementById('YHMerror').innerText ="";
}
// 昵称
function YHMonblus2(){
    var nickname=document.getElementById("nickname");
    // var reN =/^\d{6,18}$/;
    var re = /^[a-zA-Z_]{6,18}$/;
    if(nickname.value==""){
        document.getElementById('YHMerror2').innerText="请输入昵称";
    }
    else if(nickname.value.length < 6 ||nickname.value.length > 18){
        console.log(nickname.value);
        document.getElementById('YHMerror2').innerText="格式错误,长度应为6-18个字符";
    }
    else if(!re.test(nickname.value)){

        document.getElementById('YHMerror2').innerText="格式错误,只能包含英文字母和下划线";
    }
    else {
        document.getElementById('YHMerror2').innerText ="";
    }
}
function YHMonfocu2(){
    document.getElementById('YHMerror2').innerText ="";
}
//   密码
function MMonblus(){
    var password=document.getElementById("password");
    var re = /^(?=.*\d)(?=.*[a-zA-Z])[\da-zA-Z]{6,}$/;
    // var reg=/[A-Za-z].*[0-9]|[0-9].*[A-Za-z]/;

    if(password.value==""){
        document.getElementById('MMerror').innerText="请输入密码";
    }
    else if(password.value.length < 6){
        document.getElementById('MMerror').innerText="格式错误,,密码长度至少为6位";
    }

    else if(!re.test(password.value)){
        document.getElementById('MMerror').innerText="格式错误,必须包含英文字母大小写和数字";
    }
    else {
        document.getElementById('MMerror').innerText ="";
    }
}
function MMonfocu(){
    document.getElementById('MMerror').innerText ="";
}

//    确认密码
function QRMMonblus(){
    var password=document.getElementById("password");
    var confirmPassword=document.getElementById("confirmPassword");
    if(confirmPassword.value==""){
        document.getElementById('QRMMerror').innerText="请输入确认密码";
    }
    else if(password.value != confirmPassword.value){
        document.getElementById('QRMMerror').innerText="两次密码输入不一致";
    }
    else {
        document.getElementById('QRMMerror').innerText ="";
    }
}
function QRMMonfocu(){
    document.getElementById('QRMMerror').innerText ="";
}

//    性别
function XBonblus(){
//        var radios = document.getElementsByName("gender");
//        if(radios.checked == false){
//            document.getElementById('XBerror').innerText="请选择性别";
//        }else {
//            document.getElementById('XBerror').innerText ="";
//        }
}
function XBonfocu(){
//        document.getElementById('XBerror').innerText ="";
}

//    手机号
function LXDHonblus(){
    var phone=document.getElementById("phone");
    let re = /^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1}))+\d{8})$/;
    if(phone.value==""){
        document.getElementById('LXDHerror').innerText="请输入手机号";
    }
    else if(!re.test(phone.value)){
        document.getElementById('LXDHerror').innerText="手机号格式输入错误";
    }
    else {
        document.getElementById('LXDHerror').innerText ="";
    }
}
function LXDHonfocu(){
    document.getElementById('LXDHerror').innerText ="";
}
//    电话
function LXDHonblus2(){
    var phone=document.getElementById("phone2");
    let re = /^(\(\d{3,4}\)|\d{3,4}-|\s)?\d{7,14}$/;
    if(phone.value==""){
        document.getElementById('LXDHerror2').innerText="请输入电话";
    }
    else if(!re.test(phone.value)){
        document.getElementById('LXDHerror2').innerText="电话格式输入错误";
    }
    else {
        document.getElementById('LXDHerror2').innerText ="";
    }
}
function LXDHonfocu2(){
    document.getElementById('LXDHerror2').innerText ="";
}
//    电子邮箱
function DZYXonblus(){
    var email=document.getElementById("email");
    var re= /[a-zA-Z0-9]{1,10}@[a-zA-Z0-9]{1,5}\.[a-zA-Z0-9]{1,5}/;
    if(email.value==""){
        document.getElementById('DZYXerror').innerText="请输入电子邮箱";
    }
    else if(!re.test(email.value)){
        document.getElementById("DZYXerror").innerHTML="邮箱格式不正确";
    }
    else {
        document.getElementById('DZYXerror').innerText ="";
    }
}
function DZYXonfocu(){
    document.getElementById('DZYXerror').innerText ="";
}
