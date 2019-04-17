var basePath = "/";
var productCategoryHandle = new ListHandle({
  basePath: basePath,
  tableId: '#grid-table',
  pagerId: '#grid-pager',
  formId: '#queryForm',
  entityName: '商品分类',
  winWidth: '500px',
  winHeight: '400px',
  primaryKey: 'productCateId',
  urls:{
    list: basePath+'productCategory/list',
    addBefore: basePath+'common/mgr/productCategory/addProductCategory',
    editBefore: basePath+'productCategory/editBefore',
    enabled: basePath+'productCategory/enabled',
    disabled: basePath+'productCategory/disabled',
    deleted: basePath+'productCategory/deleted',
  }
},{
  viewCoupons : function(businessId) {
    artDialog.data("params", {"businessId": businessId, "businessType": 2});
    artDialog.open(basePath+'couponRelation/lists', {
        title : '查看优惠券',
        width : "800px",
        height : "500px",
        drag : true,
        resize : true,
        lock : true
    });
  }
});
var utilsHandle = new UtilsHandle({
  basePath : "/"
});
$(function(){
  var colNames = ['分类ID', '分类名称', '创建日期', '操作'];
  var colModel = [
    {name: 'productCateId', index: 'product_cate_id', width: 50, align: "center", formatter: function(cellvalue, options, rowObject){
      return cellvalue.toFixed(0);
    }}, 
    {name: 'productCateName', index: 'product_cate_name', width: 50, align: "center"}, 
    {name: 'createTime', index: 'create_time', width: 50, align: "center", formatter:'date', formatoptions: {newformat:'Y-m-d'}}, 
    {align: "center", editable: false, sortable: false, formatter: function(cellvalue, options, rowObject){
      var temp = '';
      if (hasAuthorize('couponRelation-set')) {
        temp += '<a class="linetaga" href="javascript: productCategoryHandle.viewCoupons(\'' + rowObject.productCateId.toFixed(0) + '\');">查看优惠券</a>';
        temp += '<a class="linetaga" href="javascript:///;" data-id="' + rowObject.productCateId.toFixed(0) + '">设置优惠券</a>';
      }
      if(hasAuthorize('productCategory-editBefore')){
        temp += '<a class="linetaga" href="javascript: productCategoryHandle.edit(\'' + rowObject.productCateId.toFixed(0) + '\');" >编辑</a>';
      }
      if(rowObject.enabled==1){
        if(hasAuthorize('productCategory-disabled')){
          temp += '<a class="linetaga" href="javascript: productCategoryHandle.disabled(\'' + rowObject.productCateId.toFixed(0) + '\');" >禁用</a>';
        }
      }else{
        if(hasAuthorize('productCategory-enabled')){
          temp += '<a class="linetaga" href="javascript: productCategoryHandle.enabled(\'' + rowObject.productCateId.toFixed(0) + '\');" >启用</a>';
        }
      }
      return temp;
    }}
  ];
  var rowList = [10, 20, 30, 50];
  var rownumbers = true;
  var multiselect = true;
  var config={caption: "商品分类列表", colNames: colNames, colModel: colModel, rowList: rowList, rownumbers: rownumbers, multiselect: multiselect, callback: function(){
    var cfgs = new Array();
    $("a[data-id]").each(function() {
      cfgs.push({
        object : $(this),
        datas : {"businessId": $(this).attr("data-id")},
        service : "couponService",
        title : "设置优惠券",
        width : "800px",
        height : "500px",
        multiselect : true,
//        callback : function(coupon, datas) {
        callback : function(coupons, datas) {
          var couponIds = "";
          coupons.forEach(function(coupon) {
            couponIds += ","+coupon.couponId;
          });
          var params={"businessId": datas.businessId, "businessType": 2, "couponIds": couponIds.length > 0 ? couponIds.substring(1) : couponIds};
//          var couponIds = coupon.couponId;
//          var params={"businessId": datas.businessId, "businessType": 1, "couponIds": couponIds};
          $.ajax({
            contentType: "application/json",
            url: basePath+'couponRelation/set',
            data: JSON.stringify(params),
            type: 'post',
            dataType: 'json',
            success: function(res){
              if(res.code==0){
                artDialog.alert("设置成功");
              }else{
                artDialog.alert(res.msg);
              }
            }
          });
        }
      });
    });
    utilsHandle.addChooses(cfgs);
  }};
  productCategoryHandle.init(config);
});