$(function () {
    if(localStorage.user){
        var user=JSON.parse(localStorage.user);
        $("#login_form input[name=username]").val(user.username);
        $("#login_form input[name=password]").val(user.password);
    }
    $('#login_form').keydown(function (e) {
        if (e.which == 13) {
            $(".login-btn").trigger("click");
        }
    });
    $(".login-btn").click(function () {
        var username = $("#login_form input[name=username]").val().trim();
        var password = $("#login_form input[name=password]").val().trim();

        if (username.length == 0) {
            layer.msg("请输入登录名");
            return;
        }
        if (password.length == 0) {
            layer.msg("请输入密码");
            return;
        }
        loading();
        $.ajax({
            url: path + '/login/checkLogin',
            type: 'POST',
            dataType: 'json',
            data: {
                account: username,
                pwd: password
            },
            success: function (result) {
                layer.closeAll('loading');
                if (result.code==0) {
                    if ($('#remember').is(':checked')) {
                        localStorage.setItem('user', JSON.stringify({
                            username: username,
                            password: password
                        }));
                    } else {
                        localStorage.clear('user');
                    }
                    window.location.href = path + "/wxcms/main?userId=" + result.userId;
                }else{
                    layer.msg(result.msg || "登录失败");
                }
            },
            error: function (msg) {
                layer.closeAll('loading');
                layer.msg("登录失败");
            }
        });
    });
});

String.prototype.trim=function(){
    return this.replace(/(^\s*)|(\s*$)/g, "");
};