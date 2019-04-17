var basePath="/";
var parentParams=artDialog.data('params');
$(function(){
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
    var couponId = $(":hidden[name=couponId]").val();
    var view = $(":checkbox[name=view]")[0].checked ? 1 : 0;
    var viewDialog = $(":checkbox[name=viewDialog]")[0].checked ? 1 : 0;
    var viewHome = $(":checkbox[name=viewHome]")[0].checked ? 1 : 0;
    var params = {"couponId": couponId, "view": view, "viewDialog": viewDialog, "viewHome": viewHome};
    $("#saveBtn").attr("disabled", true);
    $.ajax({
      contentType: "application/json",
      url: basePath+"coupon/edit",
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