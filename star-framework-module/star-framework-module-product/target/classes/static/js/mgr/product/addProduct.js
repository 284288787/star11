var basePath="/";
var parentParams=artDialog.data('params');
var screenHeight = parentParams.getScreenHeight();
$(function(){
  $("#cal1").click(function(){
    var price = $("input[name=price]").val();
    var bfb = $("input[name=brokerageFirstbfb]").val();
    if(price && bfb){
      var reg = new RegExp("^(([1-9]\\d*)|0)(\\.\\d{1,2}){0,1}$");
      if(! reg.test(price)){
        artDialog.tips("请填写正确的金额");
        return false;
      }
      var reg = new RegExp("^(([1-9]\\d*)|0)(\\.\\d{1}){0,1}$");
      if(! reg.test(bfb)){
        artDialog.tips("请填写正确的百分比");
        return false;
      }
      $("input[name=brokerageFirst]").val((price * (bfb / 100.0)).toFixed(2));
    }else{
      artDialog.tips("必须有值才能计算");
    }
  });
  $("#cal2").click(function(){
    var price = $("input[name=price]").val();
    var bfb = $("input[name=brokeragebfb]").val();
    if(price && bfb){
      var reg = new RegExp("^(([1-9]\\d*)|0)(\\.\\d{1,2}){0,1}$");
      if(! reg.test(price)){
        artDialog.tips("请填写正确的金额");
        return false;
      }
      var reg = new RegExp("^(([1-9]\\d*)|0)(\\.\\d{1}){0,1}$");
      if(! reg.test(bfb)){
        artDialog.tips("请填写正确的百分比");
        return false;
      }
      $("input[name=brokerageValue]").val((price * (bfb / 100.0)).toFixed(2));
    }else{
      artDialog.tips("必须有值才能计算");
    }
  });
  var contentEditor;
  new UtilsHandle({
    basePath: "/",
    choose: [
      {
        object: $("input[name=cateNames]"),
        service: "categoryService",
        title: "选择商品大分类",
        width: "800px",
        height: "500px",
        multiselect: true,
        callback: function(items){
          var cateNames = "";
          var cateIds = "";
          items.forEach(function(item){
            cateNames += item.cateName + " ";
            cateIds += ","+item.cateId;
          });
          $("input[name=cateNames]").val(cateNames);
          $("input[name=cateIds]").val(cateIds ? cateIds.substring(1) : "");
        }
      },{
        object: $("input[name=productCateName]"),
        service: "productCategoryService",
        title: "选择商品分类",
        width: "800px",
        height: "500px",
        callback: function(rowObject){
          $("input[name=productCateName]").val(rowObject.productCateName);
          $("input[name=productCateId]").val(rowObject.productCateId);
        }
      },{
        object: $("input[name=tag]"),
        service: "productTagService",
        title: "选择商品标签",
        width: "800px",
        height: "500px",
        callback: function(rowObject){
          $("input[name=tag]").val(rowObject.tagName);
        }
      }
    ],
    kindEditor: [{
      object: 'textarea[name=description]',
      width: '100%',
      height: '600px',
      afterCreate: function(obj){ 
        contentEditor = obj; 
      } 
    }],
    uploadImages:{uploadFileId: 'uploadImage', multiple: true, items: [{
      data: {"mark": 0},
      uploadBtn: $('#uploadCoverPicBtn'), 
      success: function (data, textStatus) {
        if(data.code==0){
          for(var o in data.data){
            var pic = data.data[o].original;
            $('#productCoverPathDiv').html('<span style="position: relative;"><img class="dataImg" width="70px" height="70px" src="'+pic+'" data="'+pic+'"><div class="close">X</div></span>'); 
          }
        }else{
          artDialog.alert(data.msg);
        }
      },
      complete: function (XMLHttpRequest, textStatus) {
        $('.close').unbind().click(function(){
          $(this).parent().remove();
        }); 
        $('.dataImg').unbind().click(function(){
          if($('#viewImg').length>0){ 
            $('#viewImg').remove(); 
          } 
          $('body').append('<img id="viewImg" style="display:none" src="'+$(this).attr("src")+'">');
          $('#viewImg').load(function(){
            var w=$(this).width();
            var h=$(this).height();
            var l = w / h;
            if(h > screenHeight * 0.85){
              h = screenHeight * 0.85;
              w = h * l;
            }
            artDialog.alert2('<div style="width:'+w+'px;height:'+h+'px;"><img style="height:'+h+'px" src="'+$(this).attr("src")+'">') 
          }); 
        }); 
      } 
    },{
      data: {"mark": 1},
      uploadBtn: $('#uploadPicBtn'), 
      success: function (data, textStatus) {
        if(data.code==0){
          for(var o in data.data){
            var pic = data.data[o].mark;
            $('#productPicturesDiv').append('<span style="position: relative;"><img class="dataImg" width="70px" height="70px" src="'+pic+'" data="'+pic+'"><div class="close">X</div></span>'); 
          }
        }else{
          artDialog.alert(data.msg);
        }
      },
      complete: function (XMLHttpRequest, textStatus) {
        $('.close').unbind().click(function(){
          $(this).parent().remove();
        }); 
        $('.dataImg').unbind().click(function(){
          if($('#viewImg').length>0){ 
            $('#viewImg').remove(); 
          } 
          $('body').append('<img id="viewImg" style="display:none" src="'+$(this).attr("src")+'">');
          $('#viewImg').load(function(){
            var w=$(this).width();
            var h=$(this).height();
            var l = w / h;
            if(h > screenHeight * 0.85){
              h = screenHeight * 0.85;
              w = h * l;
            }
            artDialog.alert2('<div style="width:'+w+'px;height:'+h+'px;"><img style="height:'+h+'px" src="'+$(this).attr("src")+'">') 
          }); 
        }); 
      } 
    }]
    }
  },{});
  $("#editProductForm").validate({
    rules: {
      cateName: {
        required: true,
      },
      title: {
        required: true,
        zhengze: ".{2,50}"
      },
      subtitle: {
        required: true,
        zhengze: ".{2,40}"
      },
      tag: {
        required: true,
        zhengze: ".{4,4}"
      },
      presellTime: {
        required: true
      },
      offShelfTime: {
        required: true
      },
      pickupTime: {
        required: true
      },
      number: {
        required: true,
        number:true
      },
      times: {
        required: true,
        number2:true
      },
      state: {
        required: true
      },
      originalPrice: {
        required: true,
        money: true
      },
      priceHan: {
        required: true,
        money: true
      },
      priceWei: {
        required: true,
        money: true
      },
      price: {
        required: true,
        money: true
      },
      brokerageFirst: {
        required: true,
        money: true
      },
      supplier: {
        required: true,
        zhengze: ".{2,10}"
      },
      supplierName: {
        required: true,
        zhengze: ".{2,10}"
      },
      supplierMobile: {
        required: true,
        mobile: true
      },
      brand: {
        zhengze: ".{0,20}"
      },
      specification: {
        required: true,
        zhengze: ".{2,40}"
      },
      originPlace: {
        required: true,
        zhengze: ".{2,20}"
      },
      brokerageType: {
        required: true
      },
      brokerageValue: {
        required: true,
        money: true
      }
    },
    messages: {
      cateName: {
        required: "必填",
      },
      title: {
        required: "必填",
        zhengze: "长度在2至50个字"
      },
      subtitle: {
        required: "必填",
        zhengze: "长度在2至40个字"
      },
      tag: {
        required: "必填",
        zhengze: "4个字"
      },
      presellTime: {
        required: "必选"
      },
      offShelfTime: {
        required: "必选"
      },
      pickupTime: {
        required: "必选"
      },
      number: {
        required: "必填"
      },
      times: {
        required: "必填"
      },
      state: {
        required: "必选"
      },
      originalPrice: {
        required: "必填",
      },
      priceHan: {
        required: "必填",
      },
      priceWei: {
        required: "必填",
      },
      price: {
        required: "必填",
      },
      brokerageFirst: {
        required: "必填",
      },
      supplier: {
        required: "必填",
        zhengze: "长度在2至10个字"
      },
      supplierName: {
        required: "必填",
        zhengze: "长度在2至10个字"
      },
      supplierMobile: {
        required: "必填",
      },
      brand: {
        zhengze: "10个字以内"
      },
      specification: {
        required: "必填",
        zhengze: "长度在2至40个字"
      },
      originPlace: {
        required: "必填",
        zhengze: "长度在2至20个字"
      },
      description: {
        required: ""
      },
      brokerageType: {
        required: "必选"
      },
      brokerageValue: {
        required: "必填",
        money: "设置错误"
      }
    }
  });
  
  $(".radioIpt").change(function(){
    var val = $(this).val();
    if(val == 1){
      $("input[type=text]", $(this).parent()).attr("disabled", true);
    }else {
      $("input[type=text]", $(this).parent()).attr("disabled", false);
    }
  });
  
  $("select[name=brokerageType]").change(function(){
    var val = $(this).val();
    if(val == 1){
      $("input[name=brokerageValue]", $(this).parent()).attr("placeholder", "提成金额");
      $("span.tip", $(this).parent()).text("输入一个金额数量，例如：0.8，表示0.8元");
    }else {
      $("input[type=text]", $(this).parent()).attr("placeholder", "提成金额百分比");
      $("span.tip", $(this).parent()).text("输入一个百分比数量，不需要%，例如：5，表示5%");
    }
  });
  
  $("#saveBtn").click(function(){
    var flag = $("#editProductForm").valid();
    if(! flag) return;
    var data=$("#editProductForm").serializeArray();
    var params = {};
    $.each(data, function(i, field){
      var name = field.name;
      params[name] = field.value;
      console.log(name + "  " + field.value)
    });
    params["price"] = (params["price"] * 100).toFixed(0);
    params["originalPrice"] = (params["originalPrice"] * 100).toFixed(0);
    params["brokerageValue"] = (params["brokerageValue"] * 100).toFixed(0);
    params["priceHan"] = (params["priceHan"] * 100).toFixed(0);
    params["priceWei"] = (params["priceWei"] * 100).toFixed(0);
    params["brokerageFirst"] = (params["brokerageFirst"] * 100).toFixed(0);
    if(params.brokerageType == 1 && params.price * 1 < params.brokerageValue * 1){
      artDialog.alert("提成金额不得大于商品售价");
      return;
    }
    params["productInventory"] = {"numberType": params["numberType"], "number": params["number"], "type": 1, "times": params["times"]};
    var pictures = new Array();
    var mainFlag = 1;
    $("#productPicturesDiv .dataImg").each(function(){
      var path = $(this).attr("data");
      if(path) {
        pictures.push({"url": path, "type": mainFlag});
        mainFlag = 2;
      }
    });
    if(pictures.length == 0){
      artDialog.alert("请上传产品图片");
      return;
    }
    params["pictures"] = pictures;
    
    params["description"]=contentEditor.html();
    if(!params.description){
      artDialog.alert("请填写产品描述");
      return;
    }
    params["coverPath"] = $("#productCoverPathDiv .dataImg:first").attr("data");
    $("#saveBtn").attr("disabled", true);
    $.ajax({
      contentType: "application/json",
      url: basePath+"product/add",
      data: JSON.stringify(params),
      type: 'post',
      dataType: 'json',
      success: function(res){
        if(res.code==0){
          parentParams.query();
          art.dialog.close();
        }else{
          artDialog.alert(res.msg)
        }
        $("#saveBtn").removeAttr("disabled");
      }
    });
  });
});