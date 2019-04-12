var basePath="/";
var parentParams=artDialog.data('params');
$(function(){
  $("#cardBtn").click(function(){
    var cardId = $("input[name=cardId]").val();
    if(! cardId){
      artDialog.alert("输入微信卡券Id");
      return false;
    }
    $.ajax({
      url: basePath+"coupon/getWxCardInfo",
      data: {'cardId': cardId},
      type: 'post',
      dataType: 'json',
      success: function(res){
        if(res.code==0){
          alert(JSON.stringify(res.data))
        }else{
          artDialog.alert(res.msg)
        }
      }
    });
  });
  $("#editCouponForm").validate({
    rules: {
      title: {
        required: true,
        zhengze: ".{1,10}"
      }
    },
    messages: {
      title: {
        required: "必填",
        zhengze: "长度在1至10个字"
      }
    }
  });
  
  $("#saveBtn").click(function(){
    var flag = $("#editCouponForm").valid();
    if(! flag) return;
    var data=$("#editCouponForm").serializeArray();
    var params = {};
    $.each(data, function(i, field){
      var name = field.name;
      params[name] = field.value;
      console.log(name + "  " + field.value)
    });
    $("#saveBtn").attr("disabled", true);
    $.ajax({
      contentType: "application/json",
      url: basePath+"coupon/add",
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