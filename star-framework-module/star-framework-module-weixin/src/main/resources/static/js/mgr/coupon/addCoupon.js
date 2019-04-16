var basePath="/";
var parentParams=artDialog.data('params');
$(function(){
  var cardTypes={"GROUPON":"团购券", "CASH":"代金券", "DISCOUNT":"折扣券", "GIFT":"兑换券", "GENERAL_COUPON":"优惠券"}
  var wxcardId, title, cardType, description;
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
          var card = res.data.card;
          wxcardId = cardId;
          cardType = card.cardType;
          $("#cardTypeTd").text(cardTypes[cardType]);
          var info = card[cardType.toLowerCase()];
          title = info.baseInfo.title;
          description = info.baseInfo.description;
          $("#titleTd").text(title);
          $("#descriptionTd").text(description);
          //alert(JSON.stringify(res.data))
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
  
  $(":checkbox[name=view]").change(function(){
    if(! this.checked){
      $(":checkbox[name=viewHome]")[0].checked = false;
      $(":checkbox[name=viewDialog]")[0].checked = false;
    }
  });
  $(":checkbox[name=viewHome],:checkbox[name=viewDialog]").change(function(){
    if(this.checked){
      $(":checkbox[name=view]")[0].checked = true;
    }
  });
  $("#saveBtn").click(function(){
    if(! title){
      artDialog.alert("请先获取微信卡券信息");
      return false;
    }
    var view = $(":checkbox[name=view]")[0].checked ? 1 : 0;
    var viewDialog = $(":checkbox[name=viewDialog]")[0].checked ? 1 : 0;
    var viewHome = $(":checkbox[name=viewHome]")[0].checked ? 1 : 0;
    var params = {"cardId": wxcardId, "title": title, "description": description, "cardType": cardType, "view": view, "viewDialog": viewDialog, "viewHome": viewHome};
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