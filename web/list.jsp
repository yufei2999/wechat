<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/commons/taglibs.jsp" %>
<html>
<head>
    <title>music list</title>
    <script type="text/javascript" src="${ctx}/js/jquery.min.js"></script>
    <script>
        function getMusicDetail(url, songName, artistName) {
            $('#url').val(url);
            $('#songName').val(songName);
            $('#artistName').val(artistName);
            $('#form').submit();
        }
    </script>
</head>
<body>
    <c:forEach items="${list}" var="item">
        <div><a href="${item.url}">${item.artistName} - ${item.songName}</a></div>
    </c:forEach>
    <br>
    <c:forEach items="${list}" var="item">
        <div><a href="javascript:;" onclick="getMusicDetail('${item.url}', '${item.songName}', '${item.artistName}');">${item.artistName} - ${item.songName}</a></div>
    </c:forEach>
    <form id="form" action="${ctx}/music" method="get">
        <input type="hidden" name="rt" value="d">
        <input type="hidden" id="url" name="url">
        <input type="hidden" id="songName" name="songName">
        <input type="hidden" id="artistName" name="artistName">
    </form>
</body>
</html>
