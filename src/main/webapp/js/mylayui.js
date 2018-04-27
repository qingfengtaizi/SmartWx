
/**
 * 全局变量
 */
var GLOBAL = {};

/**
 * 设置ajax全局默认参数
 *
 */
jQuery.ajaxSetup({
    //global : false,
    async: true,//异步加载
    type: "post",
    dataType: "json",
    cache: false, // 不缓存数据
    error: function (XMLHttpRequest, textStatus, errorThrown) {
        // console.log(XMLHttpRequest);
        // console.log(textStatus);
        // console.log(errorThrown);
    },
    complete: function (XMLHttpRequest, textStatus) {
        // var sessionState = XMLHttpRequest.getResponseHeader("sessionState"); //通过XMLHttpRequest取得响应头,sessionState
        // //判断是否登录
        // if (sessionState == 'notLogin') {
        //     // layer.confirm('请先登录系统', {icon: 3, title: '提示'}, function () {
        //     //     location.href("/common/index.html");
        //     // })
        //     layer.msg('请先登录系统', {shift: -1}, function () {
        //         location.href = "/login";
        //     });
        // }
        // if (sessionState == 'notAuth') {
        //     // layer.confirm('请先登录系统', {icon: 3, title: '提示'}, function () {
        //     //     location.href("/common/index.html");
        //     // })
        //     layer.msg('对不起您没有相应权限', {shift: -1}, function () {
        //         location.href = "/common/index";
        //     });
        // }
    }
});

//替换template模板语法定界符
template.defaults.rules[1].test = /<%(#?)((?:==|=#|[=-])?)[ \t]*([\w\W]*?)[ \t]*(-?)%>/;

function initPage(page, callBack,container) {
    !container && (container=$("#pagination"));
    if (page.total != 0) {
        // 创建分页
        container.pagination(page.total, {
            current_page: page.page - 1,//当前页，默认是从0开始
            items_per_page: page.pageSize, //每页显示数目
            num_edge_entries: 1, //边缘页数
            num_display_entries: 4, //主体页数
            prev_show_always: false,
            next_show_always: false,
            prev_text: "上一页",
            next_text: "下一页",
            callback: function (index) {
                //获取列表数据并刷新
                if ((page.page - 1) != index) {
                    callBack({
                        page: index + 1
                    });
                }
            }
        });
    }
    return page;
}






/******************************************** layer相关 开始 ***********************************************/

/**
 */
/**
 * 弹出提示框
 *
 * @param content 内容
 * @param icon 0:警告，1:正确，2:错误，3:问号，4:锁定，5:苦脸，6:笑脸
 */
function showAlert(content, icon) {
    if (!icon) {
        icon = 0;
    }

    var index = layer.alert(content, {icon: icon});
    return index;
}

/**
 * 消息提示框(最上面提示，随机动画)
 * @param content
 * @param icon

 */
function showTopMsg(content, icon) {
    if (!icon) {
        icon = 6;
    }
    var index = layer.msg(content, {
        offset: 0,
        shift: randomInt,
        icon: icon
    });

    return index;
}

/**
 * 提示信息
 * @param content

 */
function showMsg(content) {
    layer.msg(content);
}

/**
 * 生成随机数
 * @returns {number}
 */
function randomInt() {
    return Math.floor(Math.random() * 6);
}

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
 * 弹出窗口
 *
 * @param params
 *          title:窗口标题
 *          width:窗口宽度（默认700）
 *          height:窗口高度（默认500）
 *          template:html模板路径
 *          saveUrl:保存/修改 接口
 *
 *          tableObj:table对象
 *          treeObj:TODO，暂时不用，之后再拿ztree先封装一个
 *
 *          beforeSubmit:表单提交前执行的方法
 *          submited：表单提交前执行的方法
 *
 *          success:弹出后的成功回调方法
 * @returns {*}

 */
function showDialog(params) {
    var index = null;
    if (!params.width) {
        params.width = 700;// 默认宽度
    }
    if (!params.height) {
        params.height = 500;// 默认高度
    }
    if (!isDefined(params.btn)) {
        params.btn = ['确认', '取消'];// 默认按钮名称
    }

    if (params.template) {
        //获取模板
        $.ajax({
            url: getTemplatePath(params.template),
            dataType: 'html',
            async: false,//异步加载
            success: function (html) {
                //打开窗口
                index = layer.open({
                    type: 1//0（信息框，默认）1（页面层）2（iframe层）3（加载层）4（tips层）
                    , title: params.title
                    , area: [params.width + 'px', params.height + 'px']
                    , content: template.render(html, params.htmlData)
                    , btn: params.btn
                    , yes: function (index, layero) {
                        if (isFunction(params.yes)) {
                            //回调函数
                            params.yes(index, layero);
                        } else {
                            if (params.saveUrl) {
                                //注：先注册监听事件：监听提交，验证通过才会执行此方法
                                layui.form.on('submit(*)', function (data) {
                                    var beforeSubmit = true;
                                    if (isFunction(params.beforeSubmit)) {
                                        beforeSubmit = params.beforeSubmit();
                                    }
                                    if (beforeSubmit) {
                                        //新增
                                        $.ajax({
                                            url: params.saveUrl,
                                            data: data.field,
                                            async: false,
                                            success: function (result) {
                                                if (result.success) {
                                                    close(index);
                                                    showMsg(result.msg);
                                                    //刷新列表
                                                    params.tableObj && reloadTable(params.tableObj);
                                                    //执行完成回调
                                                    params.submited && isFunction(params.submited) && params.submited();
                                                } else {
                                                    showAlert(result.msg);
                                                }
                                            }
                                        });
                                    }

                                    return false;
                                });

                                //表单验证
                                layui.form.validate();//默认form名称为add_form（可不填写）
                            }
                        }
                    },
                    btn2: function (index, layero) {
                        if (isFunction(params.btn2)) {
                            //回调函数
                            params.btn2(index, layero);
                        } else {
                            close(index);
                        }
                    },
                    cancel: function (index, layero) {
                        if (isFunction(params.cancel)) {
                            //回调函数
                            params.cancel(index, layero);
                        } else {
                            close(index);
                        }
                    },
                    success: function (layero, index) {
                        if (isFunction(params.success)) {
                            //回调函数
                            params.success(index, layero);
                        }
                        layui.form && layui.form.render();
                    },
                    end: function (layero, index) {
                        if (isFunction(params.end)) {
                            //回调函数
                            params.end(index, layero);
                        }
                    }
                });
            }
        });
    }

    return index;
}

/**
 * 弹出小窗口
 *
 */
function showMiniDialog(params) {
    if (!params.width) {
        params.width = 500;// 默认宽度
    }
    if (!params.height) {
        params.height = 500;// 默认高度
    }
    showDialog(params);
}


/**
 * 加载层
 *
 * @param icon
 * @returns {*}

 */
function loading(icon) {
    if (!icon) {
        icon = 0;
    }
    return layer.load(icon);
}

/**
 * 关闭弹窗
 *
 * @param index 弹窗对象

 */
function close(index) {
    layer.close(index);
}

/******************************************** layer相关 结束 ***********************************************/
/**
 * 渲染模板
 *
 * @param params
 * {
 *  targetId:目标id
 *  template:模板名称(注：不写路径，默认从template文件下查找)
 *  htmlData:数据对象
 *  callBack:回调函数
 * }

 */
function renderHtml(params) {
    //获取模板
    $.ajax({
        url: getTemplatePath(params.template),
        dataType: 'html',
        type: "get",
        // async: false,//异步加载
        success: function (html) {
            //渲染模板
            $('#' + params.targetId).html(template.render(html, params.htmlData));
            if (params.callBack) {
                params.callBack();
            }
        }
    });
}

/**
 * 获取模板完成路径
 *
 * @param template 注：不写路径，默认从template文件下查找
 * @returns {string}
 */
function getTemplatePath(template) {
    if (template.indexOf("/") !=0) {
        var path=location.hash.substring(1,location.hash.lastIndexOf("/")+1);
        return "/views"+path+"template/" + template+".html";
    } else {
        return template;
    }
}

/**
 * 判断对象是否有定义
 *
 * @param template
 * @returns {boolean}

 */
function isDefined(obj) {
    return typeof(obj) != "undefined";
}

/**
 * 判断对象是否是个方法
 *
 * @param obj
 * @returns {boolean}

 */
function isFunction(obj) {
    if (obj && typeof(obj) == "function") {
        return true;
    } else {
        return false;
    }
}

/**
 * Json对象转换为String字符串
 *
 * @param jsonObj Json对象

 */
function json2String(jsonObj) {
    return JSON.stringify(jsonObj);
}

/**
 * String字符串转换为Json对象
 *
 * @param str 字符串

 */
function string2Json(str) {
    return JSON.parse(str);
}

/**
 * 删除数组中指定值
 *
 * @param arr
 * @param val

 */
function removeArrayValue(arr, val) {
    for (var i = 0; i < arr.length; i++) {
        if (arr[i] == val) {
            arr.splice(i, 1);
            break
        }
    }
}

/**
 * 获取所有表单域的键值对
 *
 * @param formId

 */
function getFormData(formId) {
    var field = [];
    var fieldElem = $('#' + formId).find('input,select,textarea');

    layui.each(fieldElem, function (_, item) {
        if (!item.name) return;
        if (/^checkbox|radio$/.test(item.type) && !item.checked) return;
        field[item.name] = item.value;
    });

    return field;
}


/**
 * 列表页面查询数据
 *
 * @param tableObj render()返回table对象
 * @param formId 表单id，默认list_form
 * @param searchId 查询按钮id，默认list_search

 */
function searchData(tableObj, formId, searchId) {
    $('#' + getListSearchId(searchId)).on('click', function () {
        reloadTable(tableObj, formId);
    });
}


/**
 * 表格重载
 *
 * @param tableObj render()返回table对象
 * @param formId 查询表单id，默认list_form（可不填）

 */
function reloadTable(tableObj, form) {
    if(typeof form=="undefined" || typeof form=="string"){
        tableObj.reload({
            page: {
                curr: 1
            }
            , where: getFormData(getListFormId(form))
        });
    }else if(typeof form=="object"){                 //传入参数
        tableObj.reload({
            page: {
                curr: form.page || 1
            }
            , where: form
        });
    }
}

/**
 * 列表页面默认表单id
 * @param id

 */
function getListFormId(id) {
    if (id) {
        return id;
    } else {
        return 'list_form';
    }
}

/**
 * 列表页面默认查询按钮id
 * @param id

 */
function getListSearchId(id) {
    if (id) {
        return id;
    } else {
        return 'list_search';
    }
}


//JS操作cookies方法!
//读取cookies
function getCookie(name) {
    var arr, reg = new RegExp("(^| )" + name + "=([^;]*)(;|$)");

    if (arr = document.cookie.match(reg))

        return (decodeURI(arr[2]));
    else
        return null;
}

//删除cookies
function delCookie(name) {
    var exp = new Date();
    exp.setTime(exp.getTime() - 1);
    var cval = getCookie(name);
    if (cval != null)
        document.cookie = name + "=" + cval + ";expires=" + exp.toGMTString();
}


function pushArray2(arr,v) {
    if(indexOfArray(arr,v)==-1){
        arr.push(v);
    }
    return arr;
}
function indexOfArray(arr,v) {
    if(!Array.isArray(arr)){
        return -1;
    }
    for(var i=0;i<arr.length;i++){
        if(arr[i]==v){
            return i
        }
    }
    return -1;
}

function removeFromArray(arr,v) {
    var index = indexOfArray( arr, v);
    if (index > -1) {
        arr.splice(index, 1);
    }
    return arr;
}

/**
 * 执行js文件,不产生script标签
 *
 * @param src           string或array
 * @param callback      回调函数
 * @author zhaoqf
 */
function executeJs(src,callback) {
    if(typeof src=="string"){
        src=[src];
    }
    if(src.length>0){
        var url=src.shift();
        $.ajax({
            type: 'get',
            url: url,
            dataType: 'script',
            success: function () {
                if(src.length=0){
                    callback && callback();
                }else{
                    executeJs(src,callback);
                }
            }
        });
    }
}


/**
 * 加载js文件,
 * 头部添加script标签,已存在不会重新加载,顺序加载
 *
 * @param src           string或array
 * @param callback      回调函数
 * @author zhaoqf
 */
function loadJs(src,callback) {
    if(typeof src=="string"){
        src=[src];
    }

    var jsarr=[],scriptArr=document.getElementsByName("script");
    for(var i=0;i<scriptArr.length;i++){
        jsarr.push(scriptArr[i].src);
    }
    function load() {
        if(src.length>0){
            var link=src.shift();
            if(ifinArray(jsarr,link)){
                return load();
            }
            var head = document.getElementsByTagName('head')[0];
            var script = document.createElement('script');
            script.type = 'text/javascript';
            script.src = link;
            script.onload = load;
            head.appendChild(script);
        }else{
            typeof callback=="function" && callback();
        }
    }
    load();
}

/**
 * 加载css文件,
 * 头部添加link标签,已存在不会重新加载,顺序加载
 *
 * @param src           string或array
 * @param callback      回调函数
 * @author zhaoqf
 */
function loadCss(href,callback) {
    if(typeof href=="string"){
        href=[href];
    }
    var cssarr=[],linkArr=document.getElementsByName("link");
    for(var i=0;i<linkArr.length;i++){
        cssarr.push(linkArr[i].href);
    }
    function load() {
        if(href.length>0){
            var url=href.shift();
            if(ifinArray(cssarr,url)){
                return load();
            }
            var head = document.getElementsByTagName('head')[0];
            var link = document.createElement('link');
            link.rel = "stylesheet";
            link.type = "text/css";
            link.href = url;
            link.onload = load;
            head.appendChild(link);
        }else{
            typeof callback=="function" && callback();
        }
    }
    load();
}

/**
 * 是否在数组中
 *
 * @param arr
 * @param v
 * @author zhaoqf
 */
function ifinArray(arr, v) {
    if (!Array.isArray(arr)) {
        return false;
    }
    for (var i = 0; i < arr.length; i++) {
        if (arr[i] == v) {
            return true;
        }
    }
    return false;
}

/**
 * 获取url中的参数
 *
 * @param name
 * @returns {*}
 */
function getUrlParam(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
    var r = window.location.search.substr(1).match(reg);  //匹配目标参数
    if (r != null) return decodeURI(r[2]);
    return null; //返回参数值
}

/**
 *  ajax初始化下拉列表，支持select与多选select
 * @param param
 *        param.url     路径
 *        param.field   [值字段，名字段]
 *        param.elem    选择器或jq元素
 *
 *        param.type    选填，默认post
 *        param.before  选填，数据预处理函数
 *        param.callBack  选填，渲染完成回调函数
 *
 *        取data-value为选中值（多选,拼接）
 *        取placeholder为默认提示
 *
 *       示例
 initSelect({
            elem:"#add_form select[name=provinceId]",
            url:"/common/getprovince",
            type:"GET",
            field:["provinceId","provinceName"],
        });
 * @author zhaoqf
 */
function initSelect(param) {
    var $elem = param.elem instanceof jQuery ? param.elem:$(param.elem);
    if ($elem.length == 0) {
        return;
    }
    var vdefault=$elem.attr("data-value");
    $elem.removeAttr("data-value");
    var ismultiple=$elem.attr("multiple")!=undefined?true:false;
    var placeholder=$elem.attr("placeholder");
    placeholder=placeholder?placeholder:"请选择";
    $.ajax({
        url: param.url,
        type: param.type || "POST",
        data: param.data || {},
        success: function (result) {
            if (!result.success || !result.data) {
                return;
            }
            var data= param.before? param.before(result.data):result.data;
            if(ismultiple){
                var arr=vdefault==undefined?[]:vdefault.split(",");
                $.each(data,function (i,v) {
                    v.value=v[param.field[0]];
                    v.name=v[param.field[1]];
                    v.selected=ifinArray(arr,v.value)?"selected":"";
                })
            }else{
                $.each(data,function (i,v) {
                    v.value=v[param.field[0]];
                    v.name=v[param.field[1]];
                    v.selected=v.value==vdefault?"selected":"";
                })
            }
            var temp='<option value="">'+placeholder+'</option>'+'<% for(var i = 0; i < data.length; i++){ %>';
            temp+='<option value="<%=data[i].value%>" <%=data[i].selected%> ><%=data[i].name%></option><%}%>';
            $elem.html(template.render(temp,{data:data}));
            ismultiple?(layui.select2 && layui.select2.render()):(layui.form.render("select"));
            param.callBack && param.callBack();
        }
    })
}


/**
 *  ajax初始化checkbox列表
 * @param param
 *        param.url     路径
 *        param.field   [值字段，名字段]
 *        param.name    name
 *        param.elem    容器选择器或jq元素
 *
 *        param.type    选填，默认post
 *        param.before  选填，数据预处理函数
 *        param.callBack  选填，渲染完成回调函数
 *
 *        取data-value为选中值（,拆分）
 *
 *       示例
 initCheckboxList({
            elem: "#courseTagList",
            url: "/tag/list",
            type: "GET",
            field: ["id", "tagName"],
            name:"courseTags"
        });
 *
 * @author zhaoqf
 */
function initCheckboxList(param) {
    var $elem = param.elem instanceof jQuery ? param.elem:$(param.elem);
    if ($elem.length == 0) {
        return;
    }
    var vdefault=$elem.attr("data-value");
    $elem.removeAttr("data-value");
    $.ajax({
        url: param.url,
        type: param.type || "POST",
        data: param.data || {},
        success: function (result) {
            if (!result.success || !result.data) {
                return;
            }
            var data= param.before? param.before(result.data):result.data;
            var arr=vdefault==undefined?[]:vdefault.split(",");
            $.each(data,function (i,v) {
                v.value=v[param.field[0]];
                v.title=v[param.field[1]];
                v.checked=ifinArray(arr,v.value)?"checked":"";
            });

            var temp='<% for(var i = 0; i < data.length; i++){ %>';
            temp+='<input type="checkbox" name="<%=name%>" <%=data[i].checked%>  value="<%=data[i].value%>" title=<%=data[i].title%> lay-skin="primary">';
            temp+='<%}%>';
            $elem.html(template.render(temp,{data:data,name:param.name}));
            layui.form.render("checkbox");
            param.callBack && param.callBack();
        }
    })
}



