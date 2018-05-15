layui.use(['element', 'layer', 'upload'], function () {
    var layer = layui.layer;
    var $firstA=$("#index_menu .layui-nav-item:first>a");

    if($firstA.attr("data-url")){
        window.homeHash=$firstA.attr("data-url");
    }else{
        window.homeHash = $("#index_menu .layui-nav-item:first dd:first>a").data("url");
    }
    //如果由index.html首次进入页面,顶掉页面url历史记录
    var title = document.title;
    var hash = (location.hash == "" || location.hash.length === 1) ? homeHash : location.hash.substring(1);
    var url = location.protocol + '//' + location.host + location.pathname + location.search + '#' + hash;
    history.replaceState(null, title, url);
    // replaceState事件无法手动触发
    hashchange();

    //hash监听，用于回退及点击菜单
    window.addEventListener('hashchange', hashchange, false);

    //点击修改密码
    $('#changePassword').click(function () {
        var passIndex = layer.open({
            type: 1,
            title: '密码修改',
            content: $("#edit_pwd"),
            area: ['400px', '300px'],
            shade: 0.6, //遮罩透明度
            anim: 1, //0-6的动画形式，-1不开启
            btn: ['确认修改', '取消'],
            yes: function () {
                var nowpassword = $('#nowpassword').val();
                var password = $('#password').val();
                var repassword = $('#repassword').val();
                if (nowpassword == '') {
                    layer.msg('请输入初始密码');
                } else if (password == '') {
                    layer.msg('请输入新密码');
                } else if (repassword == '') {
                    layer.msg('请确认新密码');
                } else if (password != repassword) {
                    layer.msg('两次密码不一致');
                } else {
                    $.ajax({
                        url: '/user/updatepwd',
                        data: {
                            pwd: nowpassword,
                            newpwd: repassword
                        },
                        success: function (result) {
                            if (result.success ) {
                                layer.msg('修改密码成功3s后自动跳转到登录页');
                                localStorage.clear('password');
                                setTimeout(function () {
                                    window.location.href = '/views/login.html';
                                }, 3000)

                            } else {
                                layer.msg(result.msg);
                            }
                        },
                        error: function () {
                            layer.msg("操作异常");
                        }
                    });
                }
            },
            end: function () {
                $('#nowpassword').val('');
                $('#password').val('');
                $('#repassword').val('');
            },
        })
    });

    $(document).on("click","a[data-url]", function (e) {
        var hash = e.target.getAttribute("data-url");
        jump(hash);
    });

    $("#userName").text(localStorage.nickname);

    //获取当前公众号及其他公众号
    getCountStatus();

    var _hmt=_hmt||[];(function(){var hm=document.createElement("script");hm.src="https://hm.baidu.com/hm.js?f07686635800b1dc0587d91cd81bf3b0";var s=document.getElementsByTagName("script")[0];s.parentNode.insertBefore(hm,s)})();
});

//获取当前公众号及其他公众号
function getCountStatus(id) {
    $.ajax({
        url: '/account/listForPage',
        data:{id:id},
        success: function (result) {
            if (result.success) {
                if(id){
                    location.reload();
                }else{
                    renderHtml({
                        targetId: 'account_nav',
                        template: "/views/common/template/account-nav.html",
                        htmlData:result
                    });
                }
            }
        }
    });
}

//弹出增加公众号
function openaddCount() {
    layer.open({
        type: 1,
        title: '增加公众号',
        content: $("#add_count"),
        area: ['400px', '300px'],
        shade: 0.6, //遮罩透明度
        anim: 1, //0-6的动画形式，-1不开启
        btn: ['确认修改', '取消'],
        yes: function () {
            // TODO:再加逻辑
            var name=$("#add_count input[name=name]");//名称
            var AppID=$("#add_count input[name=AppID]");
            var ID=$("#add_count input[name=ID]");
        },
        end: function () {
            $('#nowpassword').val('');
            $('#password').val('');
            $('#repassword').val('');
        },
    })
}

//切换至公众号
function changeCount(id) {
    getCountStatus(id);
}

// 退出
$("#exit").on("click", function () {
    $.ajax({
        url: '/user/logout',
        dataType: "json",
        success: function (result) {
            if (result.success) {
                window.location.href = "/views/login.html";
            } else {
                layer.msg("退出失败");
            }
        }
    })
});

/**
 * hashchange的回调函数，渲染右侧
 *
 * @author zhaoqf
 */
function hashchange() {
    var hash = (location.hash == "" || location.hash.length === 1) ? homeHash : location.hash.substring(1);
    hash=hash.replace(/\[.*\]/,"");
    var path="/views/"+hash+".html";
    renderHtml({
        targetId: 'index_right',
        template: path
    });
    var $a = $(".fsh-menu a[data-url='" + hash + "']");
    if($a.length>0){
        $a.parent().addClass('layui-this');
    }
    // if (!$a.parent().hasClass("layui-this")) {
        // $a.parents('li').addClass('layui-nav-itemed').siblings('li').removeClass('layui-nav-itemed');
        // $a.parent("dd").addClass('layui-this').siblings('.layui-this').removeClass('layui-this');
    // }
    layer.closeAll();
}

/**
 * 改变hash方式跳转
 *
 * @author zhaoqf
 */
function jump(hash) {
    // if (getCookie("username")) {
        if(location.hash=="#"+hash){
            hashchange()
        }else {
            location.hash=hash
        }
    // } else {
    //     window.location.href = '/views/login.html'
    // }
}

/**
 * 获取hash中的参数，hash=/aaa/aaa[a=1&b=2]
 *
 * @author zhaoqf
 */
function getHashParam() {
    var hash=location.hash;
    var param=hash.match(/\[(.*)\]/,"");
    if(!param){
        return {};
    }else{
        var arr=param[1].split("&");
        var o={};
        for(var i=0;i<arr.length;i++){
            var d=arr[i].split("=");
            d.length==2 && (o[d[0]]=d[1]);
        }
        return o;
    }
}