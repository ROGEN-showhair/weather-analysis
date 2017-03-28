<%--
  Created by IntelliJ IDEA.
  User: xudi1
  Date: 2017/3/9
  Time: 17:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Weather-Analysis</title>
</head>
<body>
下载：
<form method="get" action = "DownloadServlet" >
  <hr>
  <p>FileName：<input type="text" name="fileName" ></p>
  <p>FilePath：<input type="text" name="filePath"></p>
  <input name = "download" type="submit" value="下载数据">
  <hr />
</form>
上传：
<form method="post" action = "UploadServlet" enctype="multipart/form-data">
  <input type="file" name="selectfile" value="选择文件">
  <input name = "upload" type="submit" value="上传数据">
</form>
<hr>
分析：
<form method="get" action = "AnalysisServlet">
  <input name = "calculation" type="submit" value = "开始计算">
</form>
<hr/>
显示：
<form method="get" action = "ShowResultServlet">
  <input name = "show" type="submit" value = "显示结果">
</form>

</body>
</html>
