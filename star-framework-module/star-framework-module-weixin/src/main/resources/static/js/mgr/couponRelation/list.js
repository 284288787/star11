var basePath = "/";
var params = artDialog.data("params");
var couponRelationHandle = new ListHandle({
  basePath: basePath,
  tableId: '#grid-table',
  pagerId: '#grid-pager',
  formId: '#queryForm',
  entityName: '卡券关联关系',
  winWidth: '500px',
  winHeight: '400px',
  primaryKey: 'id',
  urls:{
    list: basePath+'couponRelation/list?businessType=' + params.businessType + '&businessId=' + params.businessId,
    deleted: basePath+'couponRelation/deleted',
  }
},{});
$(function(){
  var colNames = ['id', '卡券Id', '标题'];
  var colModel = [
    {name: 'id', index: 'id', width: 50, align: "center", formatter: function(cellvalue, options, rowObject){
      return cellvalue.toFixed(0);
    }}, 
    {name: 'couponId', index: 'coupon_id', width: 50, align: "center"}, 
    {name: 'title', index: 'title', width: 50, align: "center"}, 
  ];
  var rowList = [10, 20, 30, 50];
  var rownumbers = true;
  var multiselect = true;
  var config={caption: "卡券列表", dataType:'local', height:"300px", colNames: colNames, colModel: colModel, rowList: rowList, rownumbers: rownumbers, multiselect: multiselect};
  setTimeout(function(){
    couponRelationHandle.init(config);
    couponRelationHandle.query();
  }, 300);
});