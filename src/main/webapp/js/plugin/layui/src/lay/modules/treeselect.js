//treeSelect组件
;layui.define(['layer'], function (exports) {
    "use strict";

    var _MOD = 'treeselect',

        layer = layui.layer,
        $ = layui.jquery,
        hint = layui.hint(),
        dom = $(document),
        win = $(window);

    var TreeSelect = function (options) {

        //默认配置
        var defauleOption = {
            name:["id","pId","name"],
            chkboxType:{"Y": "ps", "N": "ps"},
            // checkFilter:function (n) {}, //是否真正选择        //仅checkbox有效
            // showRadioFilter:function (n) {},//是否显示radio        //仅radio有效

        };

        $.extend(defauleOption, options);
        this.options = options;
        this.v = '1.0.0';
    };

    function indexOfArray(arr, v) {
        if (!Array.isArray(arr)) {
            return -1;
        }
        for (var i = 0; i < arr.length; i++) {
            if (arr[i] == v) return i;
        }
        return -1;
    }

    function removefromArray(arr, v) {
        if (!Array.isArray(arr)) {
            return;
        }
        var index = indexOfArray(arr, v);
        if (index > -1) {
            arr.splice(index, 1);
        }
    }

    /**
     * 初始化下拉树选择框
     */
    TreeSelect.prototype.init = function ($elem) {
        var that = this,
            multiple=(typeof $elem.attr("multiple")=="string"),
            placeholder = $elem.attr("placeholder") || '请选择',
            initValue = $elem.attr("data-value"),
            hasRender = $elem.next('.layui-form-select').length > 0,
            disabled = $elem.prop("disabled"),
            isShow = false,
            showClass = "layui-form-selected",

            treeid = "selecttree_" + (new Date().getTime()),
            treeobj = null,
            $titleInput,
            $multiSelect,
            $ztree;

        //初始化数据
        if (initValue) {
            if(multiple){
                var arr = initValue.split(",");
                $.each(that.options.data, function (_, v) {
                    indexOfArray(arr, v.id) > -1 && (v.checked = true);
                });
                $elem.removeAttr("data-value");
            }else{
                $.each(that.options.data, function (_, v) {
                    if(v[that.options.name[0]]==initValue){
                        v.checked = true;
                        return false;
                    }
                });
                $elem.removeAttr("data-value");
            }
        }

        if(that.options.showRadioFilter){
            $.each(that.options.data, function (_, v) {
                if(that.options.showRadioFilter(v)==false){
                    v.nocheck=true;
                }
            });
        }
        var temp = '<div class="layui-form-select"><div class="layui-select-title">' +
            '<input type="text" readonly class="layui-input" value="" placeholder="' + placeholder + '">' +
            '<div class="layui-input multiSelect"><i class="layui-edge"></i>' +
            '</div> <ul class="layui-anim layui-anim-upbit ztree '+(multiple?'ztree-skin-checkbox':'ztree-skin-radio')+'" id="' + treeid + '"></ul></div></div>';
        var $reElem = $(template.render(temp, {}));
        hasRender[0] && hasRender.remove(); //如果已经渲染，则Rerender
        $elem.addClass("hide").after($reElem);

        var events = function ($reElem, disabled) {
            $titleInput = $reElem.find(".layui-select-title>input");
            $multiSelect = $reElem.find(".multiSelect");
            $ztree = $reElem.find(".ztree");


            var hide = function () {
                    isShow && $reElem.removeClass(showClass);
                    isShow = false;
                },
                show = function () {
                    !isShow && $reElem.addClass(showClass);
                    isShow = true;
                };
            //关闭下拉
            $(document).off('click', hide).on('click', hide);

            //多选
            if(multiple){
                var setValue = function () {
                    console.log("setValue");
                    if (!treeobj) {
                        return
                    }
                    var nodes = treeobj.getCheckedNodes(true);
                    var vArr = [],
                        html = "",
                        options="";
                    console.log("已选中节点");
                    console.log(nodes);
                    $.each(nodes, function (_, v) {
                        if(that.options.checkFilter && that.options.checkFilter(v)==false){return true}
                        vArr.push(v[that.options.name[0]]);
                        html += '<a href="javascript:;" data-value="' + v[that.options.name[0]] + '"><span>' + v[that.options.name[2]] + '</span><i></i></a>';
                        options+='<option value="' + v[that.options.name[0]] + '" selected>' + v[that.options.name[2]] + '</option>';
                    });
                    $multiSelect.find("a").remove();
                    $multiSelect.append($(html));
                    vArr.length == 0 ? ($titleInput.attr("placeholder", placeholder)) : ($titleInput.attr("placeholder", ""));
                    $elem.html(options).val(vArr);
                };
                //点击a都能触发check并阻止事件扩散
                $ztree.click(function (e) {
                    if(e.target.tagName=="A"){
                        $(e.target).siblings(".chk").trigger("click");
                    }else if(e.target.tagName=="SPAN"){
                        $(e.target).parent("a").siblings(".chk").trigger("click");
                    }
                    return false;
                });


                //点击标题区域,展开关闭或删除选项
                $multiSelect.on("click",function (e) {
                    if(e.target.tagName=="I" && e.target.className!="layui-edge"){
                        //点击删除叉号
                        var $p = $(e.target).parent();
                        var v = $p.attr("data-value");
                        var node = treeobj.getNodesByParam(that.options.name[0], v);
                        console.log("v");
                        console.log(node);
                        $.each(node,function (_,node) {
                            node.checked=false;
                            treeobj.updateNode(node);
                        });
                        $p.remove();
                        setValue();
                        return false;
                    }else{
                        isShow ? hide() : show();
                        return false;
                    }
                });



                var setting = {
                    data: {
                        key: {
                            name: that.options.name[2]
                        },
                        simpleData: {
                            enable: true,
                            idKey: that.options.name[0],
                            pIdKey: that.options.name[1],
                        }
                    },
                    check: {
                        enable: true,
                        chkStyle: "checkbox",
                        chkboxType: that.options.chkboxType
                    },
                    view: {
                        showIcon: false,
                        showTitle: false
                    },
                    callback: {
                        onCheck: setValue
                    }
                };
                treeobj = $.fn.zTree.init($("#" + treeid), setting, that.options.data);
                treeobj.expandAll(true);
                setValue();
            }else{
                // 单选
                var setValue = function () {
                    if (!treeobj) {
                        return
                    }
                    var nodes = treeobj.getCheckedNodes(true);
                    if(nodes.length==1){
                        $titleInput.val(nodes[0][that.options.name[2]]);
                        $elem.html($('<option value="' + nodes[0][that.options.name[0]] + '" selected>' + nodes[0][that.options.name[2]] + '</option>')).val(nodes[0][that.options.name[0]]);
                    }else{
                        $titleInput.val("");
                        $elem.html("").val("");
                    }
                };

                $ztree.click(function (e) {
                    if(e.target.tagName=="A"){
                        var chk=$(e.target).siblings(".chk");
                        chk.css("display")!="none" && chk.trigger("click");
                    }else if(e.target.tagName=="SPAN"){
                        var chk=$(e.target).parent("a").siblings(".chk");
                        chk.css("display")!="none" && chk.trigger("click");
                    }
                    return false;
                });

                //点击标题区域,展开关闭
                $multiSelect.click(function (e) {
                    isShow ? hide() : show();
                    return false;
                });

                $titleInput.focus(function () {
                    $multiSelect.trigger("click");
                }).blur(function () {
                    $multiSelect.trigger("click");
                });

                var setting = {
                    data: {
                        key: {
                            name: that.options.name[2]
                        },
                        simpleData: {
                            enable: true,
                            idKey: that.options.name[0],
                            pIdKey:that.options.name[1],
                        }
                    },
                    check: {
                        enable: true,
                        chkStyle: "radio",
                        chkboxType: {"Y": "", "N": ""},
                        radioType: "all"
                    },
                    view: {
                        showIcon: false,
                        showTitle: false
                    },
                    callback: {
                        onCheck: setValue
                    }
                };
                treeobj = $.fn.zTree.init($("#" + treeid), setting, that.options.data);
                treeobj.expandAll(true);
                setValue();
            }


        };
        events.call(this, $reElem, disabled);
    };

    /**
     * 判断是否json
     * @param {any} obj
     */
    function isJson(obj) {
        return typeof (obj) == "object" && Object.prototype.toString.call(obj).toLowerCase() == "[object object]" && !obj.length;
    }

    // 导出组件
    //exports(_MOD, new TreeSelect());
    //暴露接口
    exports(_MOD, function (options) {
        var treeSelect = new TreeSelect(options = options || {});
        var $elem = $(options.elem);
        if (!$elem[0])
            return hint.error('layui.treeSelect 没有找到' + options.elem + '元素');
        if (!options.data)
            return hint.error('layui.treeSelect 缺少参数 data ,data 可直接指定treedata，也可以是treedata数据url，treedata参见layui tree模块');
        if (isJson(options.data))
            treeSelect.init($elem);
        else {
            $.ajax({
                url: options.data,
                dataType: "json",
                type: !options.method ? "POST" : options.method,
                success: function (r) {
                    if (r.success) {
                        options.data = r.data;
                        treeSelect.init($elem);
                    }
                }
            });
        }
    });
});