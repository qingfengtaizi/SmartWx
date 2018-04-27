layui.use(['form', 'layer'], function () {
    var form = layui.form
        , layer = layui.layer;
    $('#login').keydown(function (e) {
        if (e.which == 13) {
            $("#fshLogin").trigger("click");
        }
    });
    //监听提交
    $('#fshLogin').click(function () {
        var username = $.trim($("#username").val());
        var password = $.trim($("#password").val());
        if(username==""){
            layer.msg('请输入账号');
            return;
        }
        if(password==""){
            layer.msg('请输入密码');
            return;
        }
        $.ajax({
            url: '/user/login',
            type: 'POST',
            dataType: 'json',
            data: {
                account: username,
                pwd: password
            },
            success: function (result) {
                if (result.success) {
                    if ($('#remember').is(':checked')) {
                        localStorage.setItem('remember', name);
                        localStorage.setItem('password', password);
                    } else {
                        localStorage.clear('remember');
                        localStorage.clear('password');
                    }
                    localStorage.setItem('username', result.data?result.data:"管理员");
                    window.location.href = "/views/index.html";
                }else{
                    layer.msg(result.msg || "登录失败");
                }
            },
            error: function () {
                layer.msg("登录失败");
            }
        })
    })
});

//判断是否存在过用户
if (localStorage.getItem('remember')) {
    $("#remember").attr("checked", true);
    $("#username").val(localStorage.getItem('remember'));
    $("#password").val(localStorage.getItem('password'))
}
//百度统计
var _hmt=_hmt||[];(function(){var hm=document.createElement("script");hm.src="https://hm.baidu.com/hm.js?f07686635800b1dc0587d91cd81bf3b0";var s=document.getElementsByTagName("script")[0];s.parentNode.insertBefore(hm,s)})();