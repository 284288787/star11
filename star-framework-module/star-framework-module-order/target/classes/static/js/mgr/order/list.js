var basePath = "/";
var orderHandle = new ListHandle({
  basePath: basePath,
  tableId: '#grid-table',
  pagerId: '#grid-pager',
  formId: '#queryForm',
  entityName: '订单',
  winWidth: '1300px',
  winHeight: '550px',
  primaryKey: 'orderId',
  urls:{
    list: basePath+'order/list',
    addBefore: basePath+'common/mgr/order/addOrder',
    viewBefore: basePath+'order/editBefore'
  }
},{
  exportTransportOrder: function(){
    artDialog.open(basePath+'common/mgr/order/exportTransportOrder', {
      title : "导出订单",
      width : "600px",
      height : "300px",
      drag : true,
      resize : true,
      lock : true
    });
  },
  exportOrder: function(){
    var params = orderHandle.getQueryParams();
    params["key"] = "order";
    params["handle"] = "com.star.truffle.module.order.service.ExportOrder";
    var url = '/download/excel/data?params=';
    url+=encodeURI(JSON.stringify(params));
    window.open(url);
  },
  deliverGoods: function(orderId){
    artDialog.prompt("填写快递单号：", function(expressNumber) {
      expressNumber = $.trim(expressNumber);
      if(! expressNumber){
        artDialog.alert("请填写快递单号");
        return false;
      }
      orderHandle.ajax({
        url : '/order/deliverGoods',
        data: {orderId: orderId, expressNumber: expressNumber},
        success : function(res) {
          if (res.code == 0) {
            artDialog.tips("已发货修改成功")
            orderHandle.query();
          } else {
            artDialog.alert(res.msg)
          }
        }
      });
    });
  },
  deliverGoodsFinish: function(orderId){
    artDialog.confirm("确定已完成？：", function() {
      orderHandle.ajax({
        url : '/order/deliverGoodsFinish',
        data: {orderId: orderId},
        success : function(res) {
          if (res.code == 0) {
            artDialog.tips("修改成功")
            orderHandle.query();
          } else {
            artDialog.alert(res.msg)
          }
        }
      });
    });
  },
  setDiscountedPrice: function(orderId, discountedPrice, totalBrokerageFirst, totalBrokerageSecond, totalMoney){
    artDialog.confirm("<div class='dialog'>\
      <div>设置优惠及提成</div>\
      <p>订单金额："+(totalMoney / 100.0).toFixed(2)+"元，不包含运费和优惠</p>\
      <p>优惠金额：<input class='price' type='text' placeholder='优惠金额' value='"+(discountedPrice / 100.0).toFixed(2)+"'></p>\
      <p>运营提成：<input class='first' type='text' placeholder='一级运营商提成' value='"+(totalBrokerageFirst / 100.0).toFixed(2)+"'></p>\
      <p>分销提成：<input class='second' type='text' placeholder='二级分销商提成' value='"+(totalBrokerageSecond / 100.0).toFixed(2)+"'></p>\
    </div>", function(win) {
          
      var price = $(".dialog input.price", $(win.document)).val();
      var first = $(".dialog input.first", $(win.document)).val();
      var second = $(".dialog input.second", $(win.document)).val();
      if(! price){
        artDialog.alert("请填写优惠金额");
        return false;
      }
      if(!first)first=0;
      if(!second)second=0;
      var reg = new RegExp("^(([1-9]\\d*)|0)(\\.\\d{1,2}){0,1}$");
      if(! reg.test(price)){
        artDialog.tips("请填写正确的优惠金额");
        return false;
      }
      if(! reg.test(first)){
        artDialog.tips("请填写正确的运营提成");
        return false;
      }
      if(! reg.test(second)){
        artDialog.tips("请填写正确的分销提成");
        return false;
      }
      orderHandle.ajax({
        url : '/order/setDiscountedPrice',
        data: {orderId: orderId, price: price, first: first, second: second},
        success : function(res) {
          if (res.code == 0) {
            artDialog.tips("设置优惠成功")
            orderHandle.query();
          } else {
            artDialog.alert(res.msg)
          }
        }
      });
    });
  },
});
new UtilsHandle({
  choose: [
    {
      object: $("input[name=regionName]"),
      service: "distributionRegionService",
      title: "选择分销地区",
      width: "800px",
      height: "500px",
      callback: function(rowObject){
        $("input[name=regionName]").val(rowObject.name);
        $("input[name=regionId]").val(rowObject.regionId);
      }
    },
    {
      object: $("input[name=distributorName]"),
      service: "distributorService",
      title: "选择分销商",
      width: "800px",
      height: "550px",
      valid: function(){
        return {valid: true};//{valid:$("#condition").val() != "", msg: "请先选择地区"};
      },
      condition: function(){
        var cond = {'regionId': $("input[name=regionId]").val(), 'regionName': $("input[name=regionName]").val()};
        return JSON.stringify(cond);
      },
      callback: function(rowObject){
        $("input[name=distributorName]").val(rowObject.name);
        $("input[name=distributorId]").val(rowObject.distributorId);
      }
    }
  ]
},{});
$(function(){
  var states={1:'待付款',2:'待提货',3:'已提货',4:'已退货',5:'已删除'};
  var transportStates={1:'待发货',2:'已发货',3:'已完成'};
  var colors={1:'#e87108',2:'#ad30de',3:'green',4:'red',5:'gray'}
  var colNames = ['操作', '订单ID', '订单状态', '运输状态', '删除状态', '快递单号', '订单编号', '邮费', '优惠金额', '应付金额', '实付金额', '运营提成', '分销提成', '提货码', '订单类型', '用户姓名', '用户手机号', '订单备注', '收货类型', '收货地址', '收件人', '收件人手机', '分销区域', '分销商', '店铺名称', '店铺电话', '店铺地址', '创建日期'];
  var colModel = [
    {align: "center", width: "130px", editable: false, sortable: false, frozen: true, formatter: function(cellvalue, options, rowObject){
      var temp = '';
      if(hasAuthorize('order-editBefore')){
        if(rowObject.state >= 2){
          if(rowObject.transportState == 1 || rowObject.transportState == null){
            if(hasAuthorize('order-deliverGoods'))
              temp += '<a class="linetaga" href="javascript: orderHandle.deliverGoods(\'' + rowObject.orderId.toFixed(0) + '\');" >已发货</a>';
          }else if(rowObject.transportState == 2){
            if(hasAuthorize('order-deliverGoodsFinish'))
              temp += '<a class="linetaga" href="javascript: orderHandle.deliverGoodsFinish(\'' + rowObject.orderId.toFixed(0) + '\');" >已完成</a>';
          } 
        }
      }
      if(hasAuthorize('order-setDiscountedPrice')){
        if(rowObject.state == 1)
        temp += '<a class="linetaga" href="javascript: orderHandle.setDiscountedPrice(\'' + rowObject.orderId.toFixed(0) + '\',\''+(rowObject.discountedPrice?rowObject.discountedPrice:0)+'\',\''+(rowObject.totalBrokerageFirst?rowObject.totalBrokerageFirst:0)+'\',\''+(rowObject.totalBrokerage?rowObject.totalBrokerage:0)+'\',\''+(rowObject.totalMoney?rowObject.totalMoney:0)+'\');" >设置优惠</a>';
      }
      temp += '<a class="linetaga" href="javascript: orderHandle.view(\'' + rowObject.orderId.toFixed(0) + '\');" >查看详情</a>';
      return temp;
    }},
    {name: 'orderId', index: 'order_id', width: "60px", align: "center", frozen: true, formatter: function(cellvalue, options, rowObject){
      return cellvalue.toFixed(0);
    }}, 
    {name: 'state', index: 'state', width: "70px", align: "center", frozen: true, formatter: function(cellvalue, options, rowObject){
      return '<span style="color:'+colors[cellvalue]+'">'+states[cellvalue]+'</span>';
    }}, 
    {name: 'transportState', index: 'transport_state', width: "70px", align: "center", frozen: true, formatter: function(cellvalue, options, rowObject){
      if(!cellvalue) return "";
      return '<span style="color:'+colors[cellvalue]+'">'+transportStates[cellvalue]+'</span>';
    }}, 
    {name: 'deleted', index: 'deleted', width: "60px", align: "center", formatter: 'select', editoptions: {value:'1:已删除;0:未删除'}},
    {name: 'expressNumber', index: 'express_number', width: "140px", align: "left", formatter: function(cellvalue, options, rowObject){
      var val = cellvalue?cellvalue:"";
      return "<button class='editExpressNumber button grey' data-expr='"+val+"' data-oid='"+rowObject.orderId+"'>修改</button><div style='word-wrap: break-word;word-break:break-all;white-space:normal'>" + val + "</div>";
    }}, 
    {name: 'orderCode', index: 'order_code', width: "100px", align: "center"}, 
    {name: 'despatchMoney', index: 'despatch_money', width: "70px", align: "center", formatter: function(cellvalue, options, rowObject){
      if(!cellvalue) return '0.00';
      return (cellvalue / 100.0).toFixed(2);
    }}, 
    {name: 'discountedPrice', index: 'discounted_price', width: "70px", align: "center", formatter: function(cellvalue, options, rowObject){
      if(!cellvalue) return '0.00';
      return (cellvalue / 100.0).toFixed(2);
    }}, 
    {name: 'totalMoney', index: 'total_money', width: "70px", align: "center", formatter: function(cellvalue, options, rowObject){
      return (cellvalue / 100.0).toFixed(2);
    }}, 
    {width: "70px", align: "center", formatter: function(cellvalue, options, rowObject){
      var money = rowObject.totalMoney;
      if(rowObject.despatchMoney) money+=rowObject.despatchMoney;
      if(rowObject.discountedPrice) money-=rowObject.discountedPrice;
      return (money / 100.0).toFixed(2);
    }}, 
    {name: 'totalBrokerageFirst', index: 'total_brokerage_first', width: "70px", align: "center", formatter: function(cellvalue, options, rowObject){
      return (cellvalue / 100.0).toFixed(2);
    }}, 
    {name: 'totalBrokerage', index: 'total_brokerage', width: "70px", align: "center", formatter: function(cellvalue, options, rowObject){
        return (cellvalue / 100.0).toFixed(2);
    }}, 
    {name: 'pickupCode', index: 'pickup_code', width: "70px", align: "center"}, 
    {name: 'type', index: 'type', width: "60px", align: "center", formatter: 'select', editoptions: {value:'1:自主下单;2:代客下单'}},
    {name: 'name', index: 'name', width: "120px", align: "center", formatter: function(cellvalue, options, rowObject){
      return "<div style='word-wrap: break-word;word-break:break-all;white-space:normal'>" + cellvalue + "</div>";
    }}, 
    {name: 'mobile', index: 'mobile', width: "100px", align: "center"}, 
    {name: 'remark', index: 'remark', width: "140px", align: "left", formatter: function(cellvalue, options, rowObject){
      var val = cellvalue?cellvalue:"";
      return "<button class='editRemark button grey' data-expr='"+val+"' data-oid='"+rowObject.orderId+"'>修改</button><div style='word-wrap: break-word;word-break:break-all;white-space:normal'>" + val + "</div>";
    }}, 
    {name: 'deliveryType', index: 'delivery_type', width: "60px", align: "center", formatter: 'select', editoptions: {value:'1:自提;2:快递'}}, 
    {name: 'deliveryAddress', index: 'delivery_address', width: "170px", align: "center", formatter: function(cellvalue, options, rowObject){
      if(!cellvalue) return "";
      var temp = "";
      if(rowObject.provinceName) temp += rowObject.provinceName;
      if(rowObject.cityName) temp += rowObject.cityName;
      if(rowObject.areaName) temp += rowObject.areaName;
      temp += cellvalue;
      return "<div style='word-wrap: break-word;word-break:break-all;white-space:normal'>" + temp + "</div>";
    }}, 
    {name: 'deliveryName', index: 'delivery_name', width: "120px", align: "center"}, 
    {name: 'deliveryMobile', index: 'delivery_mobile', width: "100px", align: "center"}, 
    {name: 'regionName', index: 'region_id', width: "70px", align: "center"}, 
    {name: 'distributorName', index: 'distributor_id', width: "90px", align: "center"}, 
    {name: 'shopName', index: 'shop_name', width: "120px", align: "center", formatter: function(cellvalue, options, rowObject){
      return "<div style='word-wrap: break-word;word-break:break-all;white-space:normal'>" + cellvalue + "</div>";
    }}, 
    {name: 'shopMobile', index: 'shop_mobile', width: "100px", align: "center"},
    {name: 'shopAddress', index: 'shop_address', width: "120px", align: "center", formatter: function(cellvalue, options, rowObject){
      return "<div style='word-wrap: break-word;word-break:break-all;white-space:normal'>" + cellvalue + "</div>";
    }}, 
    {name: 'createTime', index: 'create_time', width: "140px", align: "center", formatter:'date', formatoptions: {srcformat: 'Y-m-d H:i:s', newformat:'Y-m-d H:i:s'}}, 
  ];
  var rowList = [10, 20, 30, 50];
  var rownumbers = true;
  var multiselect = true;
  var config={sortorder: "desc", rowNum: 50, dataType:'local', caption: "订单列表", autowidth: false, colNames: colNames, colModel: colModel, rowList: rowList, rownumbers: rownumbers, multiselect: multiselect, callback: function(){
    $(".editExpressNumber").click(function(){
      var orderId = $(this).attr("data-oid");
      var value = $(this).attr("data-expr");
      artDialog.prompt("修改快递单号：", function(expressNumber) {
        expressNumber = $.trim(expressNumber);
        if(! expressNumber){
          artDialog.alert("请填写快递单号");
          return false;
        }
        orderHandle.ajax({
          url : '/order/updateExpressNumber',
          data: {orderId: orderId, expressNumber: expressNumber},
          success : function(res) {
            if (res.code == 0) {
              artDialog.tips("修改快递单号成功")
              orderHandle.query();
            } else {
              artDialog.alert(res.msg)
            }
          }
        });
      }, value);
      return false;
    });
    $(".editRemark").click(function(){
      var orderId = $(this).attr("data-oid");
      var value = $(this).attr("data-expr");
      artDialog.prompt("修改订单备注：", function(remark) {
        remark = $.trim(remark);
        if(! remark){
          artDialog.alert("请填写订单备注");
          return false;
        }
        orderHandle.ajax({
          url : '/order/updateRemark',
          data: {orderId: orderId, remark: remark},
          success : function(res) {
            if (res.code == 0) {
              artDialog.tips("修改订单备注成功")
              orderHandle.query();
            } else {
              artDialog.alert(res.msg)
            }
          }
        });
      }, value);
      return false;
    });
  }};
  orderHandle.init(config, {
    jsonReader: {
      repeatitems : false
    },
    shrinkToFit: false,
  });
  orderHandle.query();
});