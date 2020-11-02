initMenu();
function initMenu(){
    $.ajax({
        url:"/permissions/current",
        type:"get",
        async:false,
        success:function(data){
            if(!$.isArray(data)){
                location.href='/login.html';
                return;
            }
            let menu = $("#menu");
            $.each(data, function(i,item){
                let a = $("<a href='javascript:;'></a>");

                let css = item.css;
                if(css!=null && css!=""){
                    a.append("<i aria-hidden='true' class='fa " + css +"'></i>");
                }
                a.append("<cite>"+item.name+"</cite>");
                a.attr("lay-id", item.id);

                let href = item.href;
                if(href != null && href != ""){
                    a.attr("data-url", href);
                }

                let li = $("<li class='layui-nav-item'></li>");
                if (i == 0) {/*展开第一个菜单*/
                    li.addClass("layui-nav-itemed");
                }

                li.append(a);
                menu.append(li);

                //多级菜单
                setChild(li, item.child)

            });
        }
    });
}

function setChild(parentElement, child){
    if(child != null && child.length > 0){
        $.each(child, function(j,item2){
            let ca = $("<a href='javascript:;'></a>");
            ca.attr("data-url", item2.href);
            ca.attr("lay-id", item2.id);

            let css2 = item2.css;
            if(css2!=null && css2!=""){
                ca.append("<i aria-hidden='true' class='fa " + css2 +"'></i>");
            }

            ca.append("<cite>"+item2.name+"</cite>");

            let dd = $("<dd></dd>");
            dd.append(ca);

            let dl = $("<dl class='layui-nav-child'></dl>");
            dl.append(dd);

            parentElement.append(dl);

            // 递归
            setChild(dd, item2.child);
        });
    }
}

// 登陆用户头像昵称
showLoginInfo();
function showLoginInfo(){
    $.ajax({
        type : 'get',
        url : '/users/current',
        async : false,
        success : function(data) {
            $(".admin-header-user span").text(data.nickname);

            let pro = window.location.protocol;
            let host = window.location.host;
            let domain = pro + "//" + host;

            let sex = data.sex;
            let url = data.headImgUrl;
            if(url == null || url == ""){
                if(sex == 1){
                    url = "/img/avatars/sunny.png";
                } else {
                    url = "/img/avatars/1.png";
                }

                url = domain + url;
            } else {
                url = domain + "/statics" + url;
            }
            let img = $(".admin-header-user img");
            img.attr("src", url);
        }
    });
}

showUnreadNotice();
function showUnreadNotice(){
    $.ajax({
        type : 'get',
        url : '/notices/count-unread',
        success : function(data) {
            $("[unreadNotice]").each(function(){
                if(data>0){
                    $(this).html("<span class='layui-badge'>"+data+"</span>");
                }else{
                    $(this).html("");
                }
            });

        }
    });
}

function logout(){
    $.ajax({
        type : 'get',
        url : '/logout',
        success : function(data) {
            localStorage.removeItem("token");
            location.href='/login.html';
        }
    });
}

let active;

layui.use(['layer', 'element'], function() {
    let $ = layui.jquery,
        layer = layui.layer;
    let element = layui.element; //导航的hover效果、二级菜单等功能，需要依赖element模块
    element.on('nav(demo)', function(elem){
        //layer.msg(elem.text());
    });

    //触发事件
    active = {
        tabAdd: function(obj){
            let lay_id = $(this).attr("lay-id");
            let title = $(this).html() + '<i class="layui-icon" data-id="' + lay_id + '"></i>';
            //新增一个Tab项
            element.tabAdd('admin-tab', {
                title: title,
                content: '<iframe src="' + $(this).attr('data-url') + '"></iframe>',
                id: lay_id
            });
            element.tabChange("admin-tab", lay_id);
        },
        tabDelete: function(lay_id){
            element.tabDelete("admin-tab", lay_id);
        },
        tabChange: function(lay_id){
            element.tabChange('admin-tab', lay_id);
        }
    };
    //添加tab
    $(document).on('click','a',function(){
        if(!$(this)[0].hasAttribute('data-url') || $(this).attr('data-url')===''){
            return;
        }
        let tabs = $(".layui-tab-title").children();
        let lay_id = $(this).attr("lay-id");

        for(let i = 0; i < tabs.length; i++) {
            if($(tabs).eq(i).attr("lay-id") == lay_id) {
                active.tabChange(lay_id);
                return;
            }
        }
        active["tabAdd"].call(this);
        resize();
    });

    //iframe自适应
    function resize(){
        let $content = $('.admin-nav-card .layui-tab-content');
        $content.height($(this).height() - 147);
        $content.find('iframe').each(function() {
            $(this).height($content.height());
        });
    }
    $(window).on('resize', function() {
        let $content = $('.admin-nav-card .layui-tab-content');
        $content.height($(this).height() - 147);
        $content.find('iframe').each(function() {
            $(this).height($content.height());
        });
    }).resize();

    //toggle左侧菜单
    $('.admin-side-toggle').on('click', function() {
        let sideWidth = $('#admin-side').width();
        if(sideWidth === 200) {
            $('#admin-body').animate({
                left: '0'
            });
            $('#admin-footer').animate({
                left: '0'
            });
            $('#admin-side').animate({
                width: '0'
            });
        } else {
            $('#admin-body').animate({
                left: '200px'
            });
            $('#admin-footer').animate({
                left: '200px'
            });
            $('#admin-side').animate({
                width: '200px'
            });
        }
    });

    //手机设备的简单适配
    let treeMobile = $('.site-tree-mobile'),
        shadeMobile = $('.site-mobile-shade');
    treeMobile.on('click', function () {
        $('body').addClass('site-mobile');
    });
    shadeMobile.on('click', function () {
        $('body').removeClass('site-mobile');
    });
});
