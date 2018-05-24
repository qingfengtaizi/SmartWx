/**
 * 公共方法
 * @author zhaoqf
 */
(function ($, util) {
    /**
     * 异步加载js文件,
     * 已存在不会重新加载,顺序加载
     *
     * @param src           string或array
     * @param callback      回调函数
     */
    util.loadJs = function (src, callback) {
        if (typeof src == "string") {
            src = [src];
        }

        var jsarr = [], scriptArr = document.getElementsByName("script");
        for (var i = 0; i < scriptArr.length; i++) {
            jsarr.push(scriptArr[i].src);
        }
        function load() {
            if (src.length > 0) {
                var link = src.shift();
                if (util.indexOfArray(jsarr, link) > -1) {
                    return load();
                }
                var head = document.getElementsByTagName('head')[0];
                var script = document.createElement('script');
                script.type = 'text/javascript';
                script.src = link;
                script.onload = load;
                head.appendChild(script);
            } else {
                typeof callback == "function" && callback();
            }
        }

        load();
    };

    /**
     * 执行js文件,不产生script标签
     *
     * @param src           string或array
     * @param callback      回调函数
     */
    util.executeJs = function (src, callback) {
        if (typeof src == "string") {
            src = [src];
        }
        if (src.length > 0) {
            var url = src.shift();
            $.ajax({
                type: 'get',
                url: url,
                dataType: 'script',
                success: function () {
                    if (src.length = 0) {
                        callback && callback();
                    } else {
                        executeJs(src, callback);
                    }
                }
            });
        }
    };

    /**
     * 异步加载css文件,
     * 已存在不会重新加载,顺序加载
     * @param src           string或array
     * @param callback      回调函数
     */
    util.loadCss = function (href, callback) {
        if (typeof href == "string") {
            href = [href];
        }
        var cssarr = [], linkArr = document.getElementsByName("link");
        for (var i = 0; i < linkArr.length; i++) {
            cssarr.push(linkArr[i].href);
        }
        function load() {
            if (href.length > 0) {
                var url = href.shift();
                if (util.indexOfArray(cssarr, url) > -1) {
                    return load();
                }
                var head = document.getElementsByTagName('head')[0];
                var link = document.createElement('link');
                link.rel = "stylesheet";
                link.type = "text/css";
                link.href = url;
                link.onload = load;
                head.appendChild(link);
            } else {
                typeof callback == "function" && callback();
            }
        }

        load();
    };

    /**
     * 获取url中的参数
     *
     * @param name
     * @returns {*}
     */

    util.getUrlParam = function (name) {
        var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
        var r = window.location.search.substr(1).match(reg);
        if (r != null) return decodeURI(r[2]);
        return null; //返回参数值
    };

    /**
     * 判断是否有定义
     *
     */
    util.isDefined = function (obj) {
        return typeof(obj) != "undefined";
    };
    /**
     * 判断对象是否是个方法
     *
     * @param obj
     * @returns {boolean}

     */

    util.isFunction = function isFunction(obj) {
        if (obj && typeof(obj) == "function") {
            return true;
        } else {
            return false;
        }
    };

    /**
     * 获取值在数组中下标
     *
     *
     * @param arr
     * @param val
     */
    util.indexOfArray = function (arr, v) {
        if (!Array.isArray(arr)) {
            return -1;
        }
        for (var i = 0; i < arr.length; i++) {
            if (arr[i] == v) {
                return i
            }
        }
        return -1;
    };

    /**
     * 删除数组中指定值
     *
     * @param arr
     * @param val
     */
    util.removeFromArray = function (arr, v) {
        var index = util.indexOfArray(arr, v);
        if (index > -1) {
            arr.splice(index, 1);
        }
        return arr;
    };

    /**
     * push非重复值
     *
     * @param arr
     * @param val
     */
    util.pushArray2 = function (arr, v) {
        if (util.indexOfArray(arr, v) == -1) {
            arr.push(v);
        }
        return arr;
    };

    /**
     * 获取id下所有表单元素的键值对
     *
     * @param formId

     */
    util.getFormData = function (formId) {
        var field = [];
        var fieldElem = $('#' + formId).find('input,select,textarea');
        $.each(fieldElem, function (_, item) {
            if (!item.name) return;
            if (/^checkbox|radio$/.test(item.type) && !item.checked) return;
            field[item.name] = $(item).val();
        });
        return field;
    }

}($, window.util = {}));


