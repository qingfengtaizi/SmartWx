/**
 * webuploader图片上传
 */
$(function() {
  console.log('webuploader图片上传。。。');

  var webuploaderutil = {};
  // Web Uploader实例
  var uploader;

  var $list = $('#fileList'),
  // 优化retina, 在retina下这个值是2
  ratio = window.devicePixelRatio || 1,
  thumbnailWidth = 100 * ratio, thumbnailHeight = 100 * ratio,
  // 初始化Web Uploader
  uploader = WebUploader.create({
    // 自动上传。
    auto : true,
    // 文件接收服务端。
    server : path + '/file/uploadImg.do',
    // 选择文件的按钮。可选。
    // 内部根据当前运行是创建，可能是input元素，也可能是flash.
    pick : '#fileList',
    // 只允许选择文件，可选。
    accept : {
      title : 'Images',
      extensions : 'gif,jpg,jpeg,bmp,png',
      mimeTypes : 'image/*'
    },
    compress: {
   	     width: 80,
	     height: 80,

       // 图片质量，只有type为`image/jpeg`的时候才有效。
       quality: 80,

       // 是否允许放大，如果想要生成小图的时候不失真，此选项应该设置为false.
       allowMagnify: false,

       // 是否允许裁剪。
       crop: true,

       // 是否保留头部meta信息。
       preserveHeaders: true,

       // 如果发现压缩后文件大小比原来还大，则使用原来图片
       // 此属性可能会影响图片自动纠正功能
       noCompressIfLarger: true,

       // 单位字节，如果图片大小小于此值，不会采用压缩。
       compressSize: 0
   }

  });

  // 当有文件添加进来的时候
  uploader.on('fileQueued', function(file) {
	  var $li = $('<div id="' + file.id + '" class="file-item thumbnail">'
        + '<img>'
        + '</div>'), $img = $li.find('img');
     $('#fileList .thumbnail').remove();
     $list.append($li);
    // 创建缩略图
    uploader.makeThumb(file, function(error, src) {
      if (error) {
        $("#face_image").replaceWith('<span>不能预览</span>');
        $img.attr( 'src', src );
        return;
      }

      $img.attr( 'src', src );
      $("#face_image").attr('src', src);
    }, thumbnailWidth, thumbnailHeight);

  });

  // 文件上传过程中创建进度条实时显示。
  uploader.on('uploadProgress', function(file, percentage) {
    var $li = $( '#'+file.id ),
    $percent = $li.find('.progress span');
    // 避免重复创建
    if ( !$percent.length ) {
        $percent = $('<p class="progress"><span></span></p>')
                .appendTo( $li )
                .find('span');
    }

    $percent.css( 'width', percentage * 100 + '%' );
  });

  // 文件上传成功，给item添加成功class, 用样式标记上传成功。
  uploader.on('uploadSuccess', function(file, response) {
    $('#' + file.id).addClass('upload-state-done');
    //重新初始化
    //webuploaderutil.uploadPicByResp(uploadDiv, imgEle, success, error);
    console.log('response:', response);
    
     $("#empPhoto").val(response.result);
  });

  // 文件上传失败，现实上传出错。
  uploader.on('uploadError', function(file) {
    var $li = $('#' + file.id), $error = $li.find('div.error');
    // 避免重复创建
    if (!$error.length) {
      $error = $('<div class="error"></div>').appendTo($li);
    }

    $error.text('上传失败');
  });

  // 完成上传完了，成功或者失败，先删除进度条。
  uploader.on('uploadComplete', function(file) {
    $('#' + file.id).find('.progress').remove();
  });
  
  $("#submitBtn").click(function(){
	    //判断是否需要修改初始密码
	    var emp = new Object();
		emp.id = $("#empId").val().trim();
		emp.photo = $("#empPhoto").val().trim();
		if(emp.photo == ""){
			$.alert("请选择个人头像", "警告！");
			return false;
		}else{
			uploadImg(emp);
		}
	})
});
//上传个人头像
function uploadImg(empObj){
	//同步访问
	var result = "";
	$.ajax({
		url:path + "/advert/emp/savePhoto",
	    type:'POST',
	    async: false,
	    dataType:'json',
	    data:empObj,
	    success:function(msg){
	    	result = msg;
	    },
	    error:function(msg){
	    	alert(msg);
	    }
	});

	if(result.code == "-1"){
	  	$.alert("保存个人头像失败", "警告！");
	}else{ 
		//已登录过         
		var empId = $("#empId").val();
		//跳转到投递员个人信息页面
		window.location.href = path + "/advert/emp/center?empId="+empId;
	}
}