/**
 * Created by zhaoqingfu on 2018/3/10 0010.
 */

/**
 * 设置ajax全局默认参数
 *
 */
jQuery.ajaxSetup({
    async: true,//异步加载
    type: "post",
    dataType: "json",
    cache: false, // 不缓存数据
    error: function (XMLHttpRequest, textStatus, errorThrown) {
        console.log(XMLHttpRequest);
        console.log(textStatus);
        console.log(errorThrown);
    },
    complete: function (XMLHttpRequest, textStatus) {

    }
});

/**
 * 确认对话框
 *
 * @param content 内容
 * @param okCallBack 确定按钮回调函数
 * @param icon 0:警告，1:正确，2:错误，3:问号，4:锁定，5:苦脸，6:笑脸
 * @param cancelCallBack
 */
function showConfirm(content, okCallBack, icon, cancelCallBack) {
    var index;
    isDefined(icon) || (icon = 3);
    if (cancelCallBack) {
        index = layer.confirm(content, {icon: icon, title: '提示'}, okCallBack, cancelCallBack);
    } else {
        index = layer.confirm(content, {icon: icon, title: '提示'}, okCallBack);
    }
    return index;
}

/**
 * 加载层
 *
 * @param icon
 * @returns {*}
 */
function loading() {
    layer.load(1, {shade: [0.1,'#000']});
}

function isDefined(obj) {
    return typeof(obj) != "undefined";
}

function isFunction(obj) {
    if (obj && typeof(obj) == "function") {
        return true;
    } else {
        return false;
    }
}
function removeArrayValue(arr, val) {
    for (var i = 0; i < arr.length; i++) {
        if (arr[i] == val) {
            arr.splice(i, 1);
            break
        }
    }
}
