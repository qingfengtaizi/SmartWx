// JavaScript Document
$(function () {

    //label
    $(".bor").next().css("float", "left");


});

$(function () {
    $(".firstLi").each(function () {
        var $this = $(this);
        if (location.href.indexOf($this.attr("href")) > -1) {
            $this.parent().addClass("active");
            return false;
        }
    });
});


//判断浏览器的兼容模式
$(function () {
    var is360 = false;
    var isIE = false;
    if (window.navigator.appName.indexOf("Microsoft") != -1) {
        isIE = true;
    }
    if (isIE && (window.navigator.userProfile + '') == 'null') {
        is360 = true;
    }
    if (is360 & window.top.document.compatMode == "BackCompat") {  //判断360浏览器为怪异模式
        document.body.innerHTML = "<div class='bodyimg'>" + "<p style=';margin-left:90px;font-size:24px;color:#a82e2e'>sorry~~360安全浏览器兼容模式下不支持该页面</p>" +
            "<p style=';margin-left:130px;margin-top:30px;font-size:13px;color:#999;width:400px'>浏览器名称：" + navigator.appName + "</p>" +
            "<p style=';margin-left:130px;margin-top:10px;font-size:13px;color:#999;width:400px'>版本号：" + navigator.appVersion + "</p>" +
            "<p style='font-size:13px;color:#86b1e0;margin-top:60px;'>建议使用Firefox  、Google  、IE浏览器等新版本的浏览器或者在360安全浏览器的地址栏将浏览器的模式改为极速模式</p>" + "</div>";
        $(".bodyimg").css({
            "width": " ",
            "border": "1px  solid  ",
            "left": "30%",
            "top": "20%",
            "position": "absolute",
            "background": " "
        });
    }
});


String.prototype.trim = function () {
    return this.replace(/(^\s*)|(\s*$)/g, "");
};