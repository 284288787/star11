var basePath = "/";
var userCouponHandle = new ListHandle({
  basePath: basePath,
  tableId: '#grid-table',
  pagerId: '#grid-pager',
  formId: '#queryForm',
  entityName: '用户已领取的优惠券',
  winWidth: '500px',
  winHeight: '400px',
  primaryKey: 'id',
  urls:{
    list: basePath+'userCoupon/list',
    addBefore: basePath+'common/mgr/userCoupon/addUserCoupon',
    editBefore: basePath+'userCoupon/editBefore',
    enabled: basePath+'userCoupon/enabled',
    disabled: basePath+'userCoupon/disabled',
    deleted: basePath+'userCoupon/deleted',
  }
},{});
$(function(){
  var colNames = ['id', '用户Id', '优惠券Id', '领取日期', '优惠券状态', '操作'];
  var colModel = [
    {name: 'id', index: 'id', width: 50, align: "center", formatter: function(cellvalue, options, rowObject){
      return cellvalue.toFixed(0);
    }}, 
    {name: 'userId', index: 'user_id', width: 50, align: "center"}, 
    {name: 'couponId', index: 'coupon_id', width: 50, align: "center"}, 
    {name: 'createTime', index: 'create_time', width: 50, align: "center", formatter:'date', formatoptions: {newformat:'Y-m-d'}}, 
    {name: 'state', index: 'state', width: 50, align: "center", formatter: 'select', editoptions: {value:'1:正常;2:已过期;3:已使用'}}, 
    {align: "center", editable: false, sortable: false, formatter: function(cellvalue, options, rowObject){
      var temp = '';
      return temp;
    }}
  ];
  var rowList = [10, 20, 30, 50];
  var rownumbers = true;
  var multiselect = true;
  var config={caption: "用户已领取的优惠券列表", colNames: colNames, colModel: colModel, rowList: rowList, rownumbers: rownumbers, multiselect: multiselect};
  userCouponHandle.init(config);
});