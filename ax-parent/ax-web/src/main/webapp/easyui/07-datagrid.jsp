<%--
  Created by IntelliJ IDEA.
  User: dell3020mt-50
  Date: 2018/4/11
  Time: 11:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>datagrid</title>
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath }/js/easyui/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/js/easyui/themes/icon.css">
    <script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery-1.8.3.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath }/js/easyui/jquery.easyui.min.js"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath }/js/ztree/zTreeStyle.css" type="text/css">
    <script type="text/javascript" src="${pageContext.request.contextPath }/js/ztree/jquery.ztree.all-3.5.js"></script>
    <script src="${pageContext.request.contextPath }/js/easyui/locale/easyui-lang-zh_CN.js"
            type="text/javascript"></script>
</head>
<body>
<%--发送ajax请求获取json数据获取datagrid--%>
<table class="easyui-datagrid" data-options="url:'${pageContext.request.contextPath}/json/datagrid_data.json'">
    <thead>
    <th data-options="field:'id'">编号</th>
    <th data-options="field:'name'">姓名</th>
    <th data-options="field:'age'">年龄</th>
    </thead>
</table>

<!-- 方式三：使用easyUI提供的API创建datagrid -->
<table id="mytable">
</table>
<script type="text/javascript">
    $(function () {
        //页面加载完成后.创建数据表格datagrid
        $("#mytable").datagrid({//                  在datagrid指定各种属性
            //    定义标题行中所有的列
            columns: [[
                {title: "编号", field: "id", checkbox: true},//checkbox复选框
                {title: "姓名", field: "name"},
                {title: "年龄", field: "age"},
                {title: "地址", field: "address"}
            ]],
            //指定数据表格发送ajax请求的地址
            url: "${pageContext.request.contextPath}/json/datagrid_data.json",
            rownumbers: true,//自动在最前面添加编号
            singleSelect: true,//复选框单选
            //  定义工具栏
            toolbar:[                           //handler为工具绑定事件
                {text:"添加",iconCls:"icon-add",handler:function () {
                        alert("add")
                    }},
                {text:"删除",iconCls:"icon-remove"},
                {text:"修改",iconCls:"icon-edit"},
                {text:"查询",iconCls:"icon-search"}
            ],
        //    显示分页条
            pagination:true
        });
    })
</script>
</body>
</html>
