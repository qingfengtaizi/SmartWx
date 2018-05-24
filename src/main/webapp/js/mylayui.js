/**
 * 全局变量
 */
var GLOBAL = {
    choosed: []      //用于弹出层多选
};
var DEFAULT_FORMID = "list_form"; //默认表单id

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
        // console.log(XMLHttpRequest);
        // console.log(textStatus);
        // console.log(errorThrown);
    },
    complete: function (XMLHttpRequest, textStatus) {
        var sessionState = XMLHttpRequest.getResponseHeader("sessionState"); //通过XMLHttpRequest取得响应头,sessionState
        //判断是否登录
        if (sessionState == 'notLogin') {
            layer.msg('请先登录系统', {shift: -1}, function () {
                location.href = "/views/login.html";
            });
        }
    }
});

function ajax(o) {
    var i=showLoading();
    var s=o.success;
    var e=o.error;
    o.success= function(r){
        layer.close(i);
        s && s(r);
    };
    o.error= function(x,t,e){
        layer.close(i);
        e && e(x,t,e);
    };
    $.ajax(o);
}

//替换template模板语法定界符避免layui模板冲突
// template.defaults.rules[1].test = /<%(#?)((?:==|=#|[=-])?)[ \t]*([\w\W]*?)[ \t]*(-?)%>/;

/**
 * 加载层
 *
 * @param icon
 * @returns {*}

 */
function showLoading() {
    return layer.load(1, {
        shade: [0.1,'#000'] //0.1透明度的白色背景
    });
}



/**
 * pagination方法封装
 *
 * @param page 分页对象
 * @param callBack 回调函数
 * @param containe 容器(可选)
 */
function initPage(page, callBack, container) {
    !container && (container = $("#pagination"));
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
    return layer.alert(content, {icon: icon});
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
        shift: Math.floor(Math.random() * 6),
        icon: icon
    });
    return index;
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
    util.isDefined(icon) || (icon = 3);
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
    if (!util.isDefined(params.btn)) {
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
                        if (util.isFunction(params.yes)) {
                            //回调函数
                            params.yes(index, layero);
                        } else {
                            if (params.saveUrl) {
                                //注：先注册监听事件：监听提交，验证通过才会执行此方法
                                layui.form.on('submit(*)', function (data) {
                                    var field = data.field || {};
                                    if (util.isFunction(params.beforeSubmit)) {
                                        var res = params.beforeSubmit(field);
                                        if (res == false) {
                                            return false;
                                        }
                                        res != true && (field = res);
                                    }
                                    ajax({
                                        url: params.saveUrl,
                                        data: field,
                                        async: false,
                                        success: function (result) {
                                            if (result.success) {
                                                layer.close(index);
                                                layer.msg(result.msg);
                                                //刷新列表
                                                params.tableObj && reloadTable(params.tableObj);
                                                //执行完成回调
                                                params.submited && util.isFunction(params.submited) && params.submited();
                                            } else {
                                                showAlert(result.msg);
                                            }
                                        }
                                    });
                                    return false;
                                });

                                //表单验证
                                layui.form.validate();//默认form名称为add_form（可不填写）
                            }
                        }
                    },
                    btn2: function (index, layero) {
                        if (util.isFunction(params.btn2)) {
                            //回调函数
                            params.btn2(index, layero);
                        } else {
                            layer.close(index);
                        }
                    },
                    cancel: function (index, layero) {
                        if (util.isFunction(params.cancel)) {
                            //回调函数
                            params.cancel(index, layero);
                        } else {
                            layer.close(index);
                        }
                    },
                    success: function (layero, index) {
                        if (util.isFunction(params.success)) {
                            //回调函数
                            params.success(index, layero);
                        }
                        layui.form && layui.form.render();
                    },
                    end: function (layero, index) {
                        if (util.isFunction(params.end)) {
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
 * 显示默认空数据提示
 *
 * @param $ele
 */
function showEmpty($ele,str) {
    $ele.html("<p class='nothing'>"+(str?str:"没有查询到相关数据！")+"</p>");
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
    var $target = $('#' + params.targetId);
    if ($target.length == 0) {
        console && console.error("#" + params.targetId + "不存在！");
    }
    //获取模板
    $.ajax({
        url: getTemplatePath(params.template),
        dataType: 'html',
        type: "get",
        // async: false,//异步加载
        success: function (html) {
            //渲染模板
            $target.html(template.render(html, params.htmlData));
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
    if (template.indexOf("/") != 0) {
        var path = location.hash.substring(1, location.hash.lastIndexOf("/") + 1);
        return "/views" + path + "template/" + template + ".html";
    } else {
        return template;
    }
}

/**
 * 表格重载
 *
 * @param tableObj render()返回table对象
 * @param formId 查询表单id，默认list_form（可不填）

 */
function reloadTable(tableObj, form) {
    if (typeof form == "undefined" || typeof form == "string") {
        console.log()
        tableObj.reload({
            page: {
                curr: 1
            }
            , where: util.getFormData(form ? form : DEFAULT_FORMID)
        });
    } else if (typeof form == "object") {                 //传入参数
        tableObj.reload({
            page: {
                curr: form.page || 1
            }
            , where: form
        });
    }
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
    var $elem = param.elem instanceof jQuery ? param.elem : $(param.elem);
    if ($elem.length == 0) {
        return;
    }
    var vdefault = $elem.attr("data-value");
    $elem.removeAttr("data-value");
    var ismultiple = $elem.attr("multiple") != undefined ? true : false;
    var placeholder = $elem.attr("placeholder");
    placeholder = placeholder ? placeholder : "请选择";
    $.ajax({
        url: param.url,
        type: param.type || "POST",
        data: param.data || {},
        success: function (result) {
            if (!result.success || !result.data) {
                return;
            }
            var data = param.before ? param.before(result.data) : result.data;
            if (ismultiple) {
                var arr = vdefault == undefined ? [] : vdefault.split(",");
                $.each(data, function (i, v) {
                    v.value = v[param.field[0]];
                    v.name = v[param.field[1]];
                    v.selected = ifinArray(arr, v.value) ? "selected" : "";
                })
            } else {
                $.each(data, function (i, v) {
                    v.value = v[param.field[0]];
                    v.name = v[param.field[1]];
                    v.selected = v.value == vdefault ? "selected" : "";
                })
            }
            var temp = '<option value="">' + placeholder + '</option>' + '<% for(var i = 0; i < data.length; i++){ %>';
            temp += '<option value="<%=data[i].value%>" <%=data[i].selected%> ><%=data[i].name%></option><%}%>';
            $elem.html(template.render(temp, {data: data}));
            ismultiple ? (layui.select2 && layui.select2.render()) : (layui.form.render("select"));
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
    var $elem = param.elem instanceof jQuery ? param.elem : $(param.elem);
    if ($elem.length == 0) {
        return;
    }
    var vdefault = $elem.attr("data-value");
    $elem.removeAttr("data-value");
    $.ajax({
        url: param.url,
        type: param.type || "POST",
        data: param.data || {},
        success: function (result) {
            if (!result.success || !result.data) {
                return;
            }
            var data = param.before ? param.before(result.data) : result.data;
            var arr = vdefault == undefined ? [] : vdefault.split(",");
            $.each(data, function (i, v) {
                v.value = v[param.field[0]];
                v.title = v[param.field[1]];
                v.checked = ifinArray(arr, v.value) ? "checked" : "";
            });

            var temp = '<% for(var i = 0; i < data.length; i++){ %>';
            temp += '<input type="checkbox" name="<%=name%>" <%=data[i].checked%>  value="<%=data[i].value%>" title=<%=data[i].title%> lay-skin="primary">';
            temp += '<%}%>';
            $elem.html(template.render(temp, {data: data, name: param.name}));
            layui.form.render("checkbox");
            param.callBack && param.callBack();
        }
    })
}



