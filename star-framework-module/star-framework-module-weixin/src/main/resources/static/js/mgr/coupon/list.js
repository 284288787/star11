var basePath = "/";
var couponHandle = new ListHandle({
  basePath: basePath,
  tableId: '#grid-table',
  pagerId: '#grid-pager',
  formId: '#queryForm',
  entityName: '卡券',
  winWidth: '600px',
  winHeight: '450px',
  primaryKey: 'couponId',
  urls:{
    list: basePath+'coupon/list',
    addBefore: basePath+'common/mgr/coupon/addCoupon',
    editBefore: basePath+'coupon/editBefore',
    enabled: basePath+'coupon/enabled',
    disabled: basePath+'coupon/disabled',
    deleted: basePath+'coupon/deleted',
  }
},{});
$(function(){
  var colNames = ['卡券ID', '微信卡券Id', '卡券标题', '列表显示', '首页展示', '首页弹出', '创建日期', '操作'];
  var colModel = [
    {name: 'couponId', index: 'coupon_id', width: 50, align: "center", formatter: function(cellvalue, options, rowObject){
      return cellvalue.toFixed(0);
    }}, 
    {name: 'cardId', index: 'card_id', width: 100, align: "center"}, 
    {name: 'title', index: 'title', width: 150, align: "center"}, 
    {name: 'view', index: 'view', width: 50, align: "center", formatter: 'select', editoptions: {value:'1:是;0:否'}}, 
    {name: 'viewHome', index: 'view_home', width: 50, align: "center", formatter: 'select', editoptions: {value:'1:是;0:否'}}, 
    {name: 'viewDialog', index: 'view_dialog', width: 50, align: "center", formatter: 'select', editoptions: {value:'1:是;0:否'}}, 
    {name: 'createTime', index: 'create_time', width: 50, align: "center", formatter:'date', formatoptions: {newformat:'Y-m-d'}}, 
    {align: "center", editable: false, sortable: false, formatter: function(cellvalue, options, rowObject){
      var temp = '';
      if(hasAuthorize('coupon-editBefore')){
        temp += '<a class="linetaga" href="javascript: couponHandle.edit(\'' + rowObject.couponId.toFixed(0) + '\');" >编辑</a>';
      }
      if(rowObject.enabled==1){
        if(hasAuthorize('coupon-disabled')){
          temp += '<a class="linetaga" href="javascript: couponHandle.disabled(\'' + rowObject.couponId.toFixed(0) + '\');" >禁用</a>';
        }
      }else{
        if(hasAuthorize('coupon-enabled')){
          temp += '<a class="linetaga" href="javascript: couponHandle.enabled(\'' + rowObject.couponId.toFixed(0) + '\');" >启用</a>';
        }
      }
      return temp;
    }}
  ];
  var rowList = [10, 20, 30, 50];
  var rownumbers = true;
  var multiselect = true;
  var config={caption: "卡券列表", colNames: colNames, colModel: colModel, rowList: rowList, rownumbers: rownumbers, multiselect: multiselect};
  couponHandle.init(config);
});