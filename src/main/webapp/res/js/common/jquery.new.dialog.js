var plus_dialog = function (dialogDiv, params) {
  var ms = {
    init: function (obj, args) {
      return (function () {
        ms.fillHtml(obj, args);
        ms.bindEvent(obj, args);
      })();
    },

    fillHtml: function (obj, args) {
      return (function () {
        var diaStr = "";
        if (args.flag) {
          diaStr = "<div class='ps-dialog'><div class='plus-dialog'><div class='dia-content'><div class='dia-title'>" + args.titleCon + "<span class='dia-close'>X</span></div><div class='dia-text'>" + args.textCon + "</div><div class='dia-btn-group'><a class='dia-submit' href='javascript:void(0);'>确认</a><a class='dia-cancel' href='javascript:void(0);'>取消</a></div></div></div></div>";
        } else {
          diaStr = "<div class='ps-dialog'><div class='plus-dialog'><div class='dia-content'><div class='dia-title'>" + args.titleCon + "<span class='dia-close'>X</span></div><div class='dia-text'>" + args.textCon + "</div></div></div></div>";
        }
        if ($('body').find('.ps-dialog').length <= 0) {
          obj.append(diaStr);
        }
        setTimeout(function(){
        	obj.find('.dia-content').animate({
        		'opacity':'1',
        		'margin-top': '-' + obj.find('.dia-content').height()/2 + 'px'
        	},500,function(){
        		if(args.fadeOut){
                	obj.find('.dia-content').delay(3000);
                	obj.find('.dia-content').animate({
                		'opacity':'0'
                	},500,function(){
                		obj.find('.ps-dialog').remove();
                	});
                }
        	});
        },1);
      })();
    },

    bindEvent: function (obj, args) {
      return (function () {
        obj.on('click', 'span.dia-close,a.dia-cancel', function () {
          obj.find('.ps-dialog').remove();
          if (typeof (s.args.clickFlag) == "function") {
            args.clickFlag(false);
            obj.find('span.dia-close,a.dia-cancel').unbind('click');
          }
        });
        obj.on('click', 'a.dia-submit', function () {
          obj.find('.ps-dialog').remove();
          args.clickFlag(true);
          obj.find('a.dia-submit').unbind('click');
        });
      })();
    },

    typeOf: function (params, args) {
      for (var def in args) {
        if (typeof params[def] === 'undefined') {
          params[def] = args[def];
        } else if (typeof params[def] === 'object') {
          for (var deepDef in args[def]) {
            if (typeof params[def][deepDef] === 'undefined') {
              params[def][deepDef] = args[def][deepDef];
            }
          }
        }
      }
    }
  }
  var args = {
    titleCon: '',
    textCon: '',
    flag: false,
    fadeOut: false,
    clickFlag: function () {}
  }

  ms.typeOf(params, args);

  var s = this;
  s.args = params;

  return ms.init($(dialogDiv), s.args);
}