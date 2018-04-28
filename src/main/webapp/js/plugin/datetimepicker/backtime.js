/**
 * Created by Administrator on 2017/9/18.
 */
$(function () {
   //初始化区间开始日期的方法
    intervalDate("start-date", "start_form_date");
    //初始化区间结束日期的方法
    intervalDate("end-date", "end_form_date");
    //选择年月日时分秒的
    formDatetime();
    //选择年月日的
    formDate();
    //选择年月日的   弹出框位置为左上方
    formDate1();
    //选择年月日的    只能选择当前日期以后的日期
    voteFormDate();
    //选择年月的
    formMonth();
    //选择年的
    formYear();
    //选择时间
    formTime();
});
//日期插件
//选择年月日时分秒的       startView: 2,   minView: 2, format: 'yyyymmdd  hh:ii',
function formDatetime() {
    $('.form_datetime').datetimepicker({
        format: 'yyyy-mm-dd hh:ii',
        weekStart: 1,
        todayBtn: 1,
        autoclose: true,
        todayHighlight: 1,
        startView: 2,
        minView: 0,
        pickerPosition: 'bottom-left',
        forceParse: false,
        language: 'zh-CN'
    });
}

//选择年月日的       startView: 2,   minView: 2, format: 'yyyymmdd',
function formDate() {
    $('.form_date').datetimepicker({
        format: 'yyyy-mm-dd',
        weekStart: 1,
        autoclose: true,
        startView: 2,
        minView: 2,
        pickerPosition: 'bottom-left',
        forceParse: false,
        language: 'zh-CN'
    });
}

//选择年月日的       startView: 2,   minView: 2, format: 'yyyymmdd',
function voteFormDate() {
    $('.vote_form_date').datetimepicker({
        format: 'yyyy-mm-dd hh:ii',
        weekStart: 1,
        autoclose: true,
        startView: 2,
        minView: 0,
        pickerPosition: 'bottom-left',
        forceParse: false,
        startDate: new Date(),
        language: 'zh-CN'
    });
}

function formDate1() {
    $('.form_date1').datetimepicker({
        format: 'yyyy-mm-dd',
        weekStart: 1,
        autoclose: true,
        startView: 2,
        minView: 2,
        pickerPosition: 'top-left',
        forceParse: false,
        language: 'zh-CN'
    });
}

//选择年月的    startView: 3,   minView: 3, format: 'yyyymm',
function formMonth() {
    $('.form_month').datetimepicker({
        format: 'yyyy-mm',
        weekStart: 1,
        autoclose: true,
        startView: 3,
        minView: 3,
        pickerPosition: 'bottom-left',
        forceParse: false,
        language: 'zh-CN'
    });
}

//选择年的     startView: 4,   minView: 4, format: 'yyyy',
function formYear() {
    $('.form_year').datetimepicker({
        format: 'yyyy',
        weekStart: 1,
        autoclose: true,
        startView: 4,
        minView: 4,
        pickerPosition: 'bottom-left',
        forceParse: false,
        language: 'zh-CN'
    });
}

//选择时间
function formTime() {
    $('.form_time').datetimepicker({
        language: 'zh-CN',
        pickerPosition: "bottom-left",
        format: "hh:ii",
        weekStart: 1,
        todayBtn: 0,
        todayHighlight: 0,
        autoclose: 1,
        startView: 1,
        minView: 0,
        maxView: 1,
        forceParse: 0,
    });
}

//选择年月日区间的      startView: 2,   minView: 2, format: 'yyyymmdd',
function intervalDate(txtId, dateId) {
    $('#' + dateId).datetimepicker({
        format: 'yyyy-mm-dd',
        weekStart: 1,
        autoclose: true,
        startView: 2,
        minView: 2,
        pickerPosition: 'bottom-left',
        forceParse: false,
        language: 'zh-CN'
    }).on('changeDate', function (ev) {
        var da = ev.date.valueOf();
        da = new Date(da);
        var year = da.getFullYear();
        var month = (da.getMonth() + 1 < 10 ? '0' + (da.getMonth() + 1) : da.getMonth() + 1);
        var date = (da.getDate() < 10 ? '0' + da.getDate() : da.getDate())
        // console.log(date)
        var d = [year, month, date].join('-');
        $("#" + txtId).text(d);
    });
}

//切换显示 隐藏 popover
function togglePopover(obj) {
    var s = $(obj).parents(".popoverWrap");
    s.find(".popover").toggle();
    $('html').click(function (i) {
        if (!$(i.target).parents(".popoverWrap").is(s)) {
            s.find(".popover").hide();
        }
    });
}