//treeSelect组件
layui.define(['layer'], function (exports) {
    "use strict";

    var MOD="select2",MOD_NAME = 'form',
        layer = layui.layer,
        $ = layui.jquery,

        hint = layui.hint(),
        THIS = 'layui-this',
        SHOW = 'layui-show',
        HIDE = 'layui-hide',
        DISABLED = 'layui-disabled',
        dom = $(document),
        win = $(window);
    var select2 = function (options) {
        this.options = options;
        this.v = '1.0.0';
    };

    function indexOfArray(arr,v) {
        if(!Array.isArray(arr)){
            return -1;
        }
        for (var i = 0; i < arr.length; i++) {
            if (arr[i] == v) return i;
        }
        return -1;
    }
    function removefromArray(arr,v) {
        if(!Array.isArray(arr)){
            return;
        }
        var index = indexOfArray(arr,v);
        if (index > -1) {
            arr.splice(index, 1);
        }
    }

    //placeholder是否显示
    select2.prototype.hidePlaceholder = function(title,select){
        //判断是否全部删除，如果全部删除则展示提示信息
        if(title.find(".multiSelect a").length != 0){
            title.children("input.layui-input").attr("placeholder","");
        }else{
            title.children("input.layui-input").attr("placeholder",select.find("option:eq(0)").text());
        }
    }
    /**
     * 初始化下拉树选择框
     */
    select2.prototype.render = function(filter){
        var that = this,selects = $(('select.select2'+function(){
            return filter ? ('[lay-filter="' + filter +'"]') : '';
        }()));
        var TIPS = '请选择', CLASS = 'layui-form-select', TITLE = 'layui-select-title'
            ,NONE = 'layui-select-none', initValue = '', thatInput
            , hide = function(e, clear){
            if(!$(e.target).parent().hasClass(TITLE) || clear){
                $('.'+CLASS).removeClass(CLASS+'ed ' + CLASS+'up');
                thatInput && initValue && thatInput.val(initValue);
            }
            thatInput = null;
        }

            ,events = function(reElem, disabled, isSearch){
            var select = $(this)
                ,title = reElem.find('.' + TITLE)
                ,input = title.find('input')
                ,multiSelect = title.find(".multiSelect")
                ,dl = reElem.find('dl:eq(0)')
                ,dds = dl.find('div>dd').length==0 ? dl.find('dd') : dl.find('div>dd');

            that.hidePlaceholder(title,select);
            if(disabled) return;

            //展开下拉
            var showDown = function(){
                var top = reElem.offset().top + reElem.outerHeight() + 5 - win.scrollTop()
                    ,dlHeight = dl.outerHeight();
                $("body").find("."+CLASS).removeClass(CLASS+'ed');
                reElem.addClass(CLASS+'ed');
                dds.removeClass(HIDE);

                //上下定位识别
                if(top + dlHeight > win.height() && top >= dlHeight){
                    reElem.addClass(CLASS + 'up');
                }
            }, hideDown = function(choose){
                reElem.removeClass(CLASS+'ed ' + CLASS+'up');
                input.blur();

                if(choose) return;

                notOption(input.val(), function(none){
                    if(none){
                        initValue = dl.find('.'+THIS).html();
                        input && input.val(initValue);
                    }
                });
            };

            //点击标题区域
            title.on('click', function(e){
                if(typeof select.attr('multiple') && typeof select.attr('multiple') === 'string') {
                    reElem.hasClass(CLASS + 'ed') ? hideDown(true): showDown();
                    dl.find(".layui-input").val("");
                    setTimeout(function () {  // 直接写focus，会失败。。
                        dl.find(".layui-input").focus();
                    },500)
                }else{
                    reElem.hasClass(CLASS + 'ed') ? (
                        hideDown()
                    ) : (
                        hide(e, true),
                            showDown()
                    );
                    dl.find('.' + NONE).remove();
                }
                e.stopPropagation();
                //选择完毕关闭下拉
                $(document).not(title.find(".layui-anim")).off('click', hide).on('click', hide);
            });

            //点击箭头获取焦点
            title.find('.layui-edge').on('click', function(){
                input.focus();
            });

            //键盘事件
            input.on('keyup', function(e){
                var keyCode = e.keyCode;
                //Tab键
                if(keyCode === 9){
                    showDown();
                }
            }).on('keydown', function(e){
                var keyCode = e.keyCode;
                //Tab键
                if(keyCode === 9){
                    hideDown();
                } else if(keyCode === 13){ //回车键
                    e.preventDefault();
                }
            });

            //检测值是否不属于select项
            var notOption = function(value, callback, origin){
                var num = 0;
                var ignoreCase =  typeof(select.attr("lay-case"))=="undefined"; // 忽略大小写
                layui.each(dds, function(){
                    var othis = $(this)
                        ,text = othis.text()
                        ,not = ignoreCase?text.toLowerCase().indexOf(value.toLowerCase()) === -1 : text.indexOf(value) === -1;
                    if(value === '' || (origin === 'blur') ? value !== text : not) num++;
                    origin === 'keyup' && othis[not ? 'addClass' : 'removeClass'](HIDE);
                });
                var none = num === dds.length;
                return callback(none), none;
            };

            //搜索匹配
            var search = function(e){
                var value = this.value, keyCode = e.keyCode;

                if(keyCode === 9 || keyCode === 13
                    || keyCode === 37 || keyCode === 38
                    || keyCode === 39 || keyCode === 40
                ){
                    return false;
                }

                notOption(value, function(none){
                    if(none){
                        dl.find('.'+NONE)[0] || dl.append('<p class="'+ NONE +'">无匹配项</p>');
                    } else {
                        dl.find('.'+NONE).remove();
                    }
                }, 'keyup');

                if(value === ''){
                    dl.find('.'+NONE).remove();
                }
            };
            if(isSearch){
                input.on('keyup', search).on('blur', function(e){
                    thatInput = input;
                    initValue = dl.find('.'+THIS).html();
                    setTimeout(function(){
                        notOption(input.val(), function(none){
                            if(none && !initValue){
                                // input.val(''); //todo 不知道什么用处，影响到我的简化多选下拉框了
                            }
                        }, 'blur');
                    }, 200);
                });
            }

            //多选删除
            title.delegate(".multiSelect a i","click",function(e){
                var valueStr = select.val() || [];
                var _this = $(this);
                e.stopPropagation();
                title.find("dd").each(function(){
                    if($(this).find("span").text() == _this.siblings("span").text()){
                        $(this).find("input").removeAttr("checked");
                        $(this).find(".layui-form-checkbox").removeClass("layui-form-checked");
                        removefromArray(valueStr,$(this).attr("lay-value"));
                        select.val(valueStr);
                        layui.event.call(this, MOD_NAME, 'select('+ select.attr('lay-filter') +')', {
                            elem: select[0]
                            ,value: valueStr
                            ,othis: reElem
                        });
                    }
                });
                $(this).parent("a").remove();
                that.hidePlaceholder(title,select);
            });

            //选择
            var omit = typeof select.attr("lay-omit") === 'undefined'; // 简写为 已选择x条
            dds.on('click', function(event){
                var othis = $(this), value = othis.attr('lay-value'),valueStr = select.val() || [];
                var filter = select.attr('lay-filter'); //获取过滤器
                if(typeof select.attr('multiple') && typeof select.attr('multiple') === 'string'){
                    if(othis.hasClass(DISABLED)) return false;
                    if(othis.find("input[type='checkbox']").is(':checked')){
                        if (omit) {
                            multiSelect.html(multiSelect.html() + "<a href='javascript:;'><span>"+othis.find("span").text()+"</span><i></i></a>");
                        } else {
                            input.eq(0).val("已选择"+othis.parent().find('[type=checkbox]:checked').length+"条");
                        }
                        valueStr.push(value);
                    }else{
                        if (omit) {
                            multiSelect.find("a").each(function(){
                                if($(this).find("span").text() == othis.find("span").text()){
                                    $(this).remove();
                                    removefromArray(valueStr,value);
                                }
                            })
                        } else {
                            var num =othis.parent().find('[type=checkbox]:checked').length;
                            if (num == 0) {
                                input.eq(0).val("");
                            } else {
                                input.eq(0).val("已选择"+num+"条");
                            }
                            removefromArray(valueStr,value);
                        }

                    }
                    select.val(valueStr).removeClass('layui-form-danger');
                    layui.event.call(this, MOD_NAME, 'select('+ filter +')', {
                        elem: select[0]
                        ,value: valueStr
                        ,othis: reElem
                    });
                    that.hidePlaceholder(title,select);
                }else{
                    if(othis.hasClass(DISABLED)) return false;
                    if(othis.hasClass('layui-select-tips')){
                        input.val('');
                    } else {
                        input.val(othis.text());
                        othis.addClass(THIS);
                    }
                    othis.siblings().removeClass(THIS);
                    select.val(value).removeClass('layui-form-danger');
                    layui.event.call(this, MOD_NAME, 'select('+ filter +')', {
                        elem: select[0]
                        ,value: value
                        ,othis: reElem
                    });
                    hideDown(true);
                    return false;
                    reElem.find('dl>dt').on('click', function(e){
                        return false;
                    });
                }
            });
            dl.on('click', function (e) {
                if(typeof select.attr('multiple') && typeof select.attr('multiple') === 'string') {
                    e.stopPropagation(); // 阻止事件冒泡，使下拉框长显示
                }
            })
        }

        selects.each(function(index, select){
            var othis = $(this)
                ,hasRender = othis.next('.'+CLASS)
                ,disabled = this.disabled
                ,selected = $(select.options[select.selectedIndex]) //获取当前选中项
                ,optionsFirst = select.options[0];
            if(typeof othis.attr('multiple') && typeof othis.attr('multiple') === 'string'){
                var value = $(select).val()
            }else{
                var value = select.value
            }
            var isSearchInput = typeof othis.attr('lay-search') != 'undefined';

            // if(typeof othis.attr('lay-ignore') === 'string') return othis.show();

            var isSearch = typeof othis.attr('lay-search') === 'string'
                ,isMultiple = typeof othis.attr('multiple') === 'string'
                ,placeholder = optionsFirst ? (
                optionsFirst.value ? TIPS : (optionsFirst.innerHTML || TIPS)
            ) : TIPS;

            //替代元素
            var inputValue =  !(typeof $(select).attr("lay-omit") === 'undefined')&&value!=null&&value.length>0 ? '已选择'+value.length+"条" : "";
            if(typeof othis.attr('multiple') && typeof othis.attr('multiple') === 'string') {
                var reElem = $(['<div class="' + (isMultiple ? '' : 'layui-unselect ') + CLASS + (disabled ? ' layui-select-disabled' : '') + '">'
                    , '<div class="' + TITLE + '"><input type="text" class="layui-input" value="'+inputValue+'" placeholder="' + placeholder + '"><div class="layui-input multiSelect" >' + function(){
                        var aLists = [];
                        if(typeof $(select).attr("lay-omit")==='undefined' && value != null && value != undefined && value.length != 0){
                            for(var aList = 0;aList<value.length;aList++){
                                if(value[aList]){
                                    var chooseText = '';
                                    $(select).find('option').each(function (i, ele) {
                                        if (typeof $(this).attr('value') == 'undefined') {
                                            if ($(this).text() == value[aList]) {
                                                chooseText = $(this).text();
                                                return false;
                                            }
                                        } else if ($(this).attr('value') == value[aList]) {
                                            chooseText = $(this).text();
                                            return false;
                                        }
                                    });
                                    aLists.push("<a href='javascript:;'><span>"+chooseText+"</span><i></i></a>")
                                }
                            }
                        }
                        return aLists.join('');
                    }(othis.find('*')) + '<i class="layui-edge"></i></div>'
                    , '<dl class="layui-anim layui-anim-upbit' + (othis.find('optgroup')[0] ? ' layui-select-group' : '') + '" '+ (isSearchInput?'style="overflow-y: hidden;padding: 5px 0px 0px;"':'') +'>' + function (options) {
                        var arr = [];
                        layui.each(options, function (index, item) {
                            if (index === 0 && !item.value) {
                                if (isSearchInput) {
                                    arr.push('<dd lay-value="" class="layui-select-tips" style="padding-right: 10px; margin-bottom: 5px;"><input class="layui-input" placeholder="关键字搜索"></dd>');
                                } else {
                                    arr.push('<dd lay-value="" class="layui-select-tips">' + (item.innerHTML || TIPS) + '</dd>');
                                }
                            }else {
                                if (index ===1 && isSearchInput) {
                                    arr.push('<div  style="max-height: 247px; overflow-y: auto" >')
                                }
                                if(value != null && value != undefined && value.length != 0) {
                                    for (var checkedVal = 0; checkedVal < value.length; checkedVal++) {
                                        if (value[checkedVal] == item.value) {
                                            arr.push('<dd lay-value="' + item.value + '">' + '<input type="checkbox" ' + (item.disabled ? "disabled" : "") + ' checked lay-filter="searchChecked" title="' + item.innerHTML + '" lay-skin="primary"></dd>');
                                            return false;
                                        }
                                    }
                                }
                                arr.push('<dd lay-value="' + item.value + '">' + '<input type="checkbox" ' + (item.disabled ? "disabled" : "") + ' lay-filter="searchChecked" title="' + item.innerHTML + '" lay-skin="primary"></dd>');
                            }
                        });
                        arr.length === 0 && arr.push('<dd lay-value="" class="' + DISABLED + '">没有选项</dd>');
                        if (isSearchInput) {
                            arr.join("</div>");
                        }
                        return arr.join('');
                    }(othis.find('*')) + '</dl>'
                    , '</div>'].join(''));

                hasRender[0] && hasRender.remove(); //如果已经渲染，则Rerender
                othis.after(reElem);
                layui.form.render("checkbox");
                events.call(this, reElem, disabled, isMultiple);
            }else{
                var reElem = $(['<div class="' + (isSearch ? '' : 'layui-unselect ') + CLASS + (disabled ? ' layui-select-disabled' : '') + '">'
                    , '<div class="' + TITLE + '"><input type="text" placeholder="' + placeholder + '" value="' + (value ? selected.html() : '') + '" ' + (isSearch ? '' : 'readonly') + ' class="layui-input' + (isSearch ? '' : ' layui-unselect') + (disabled ? (' ' + DISABLED) : '') + '">'
                    , '<i class="layui-edge"></i></div>'
                    , '<dl class="layui-anim layui-anim-upbit' + (othis.find('optgroup')[0] ? ' layui-select-group' : '') + '">' + function (options) {
                        var arr = [];
                        layui.each(options, function (index, item) {
                            if (index === 0 && !item.value) {
                                arr.push('<dd lay-value="" class="layui-select-tips">' + (item.innerHTML || TIPS) + '</dd>');
                            } else if (item.tagName.toLowerCase() === 'optgroup') {
                                arr.push('<dt>' + item.label + '</dt>');
                            } else {
                                arr.push('<dd lay-value="' + item.value + '" class="' + (value === item.value ? THIS : '') + (item.disabled ? (' ' + DISABLED) : '') + '">' + item.innerHTML + '</dd>');
                            }
                        });
                        arr.length === 0 && arr.push('<dd lay-value="" class="' + DISABLED + '">没有选项</dd>');
                        return arr.join('');
                    }(othis.find('*')) + '</dl>'
                    , '</div>'].join(''));

                hasRender[0] && hasRender.remove(); //如果已经渲染，则Rerender
                othis.after(reElem);
                layui.form.render("checkbox");
                events.call(this, reElem, disabled, isSearch);
            }
        });
    };
    // 导出组件
    exports(MOD, new select2());
});