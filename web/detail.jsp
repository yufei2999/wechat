<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/commons/taglibs.jsp" %>
<html>
<head>
    <meta charset="utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>music play</title>
    <link type="text/css" rel="stylesheet" href="${ctx}/css/music.css">
    <script type="text/javascript" src="${ctx}/js/jquery.min.js"></script>
    <script type="text/javascript">
        $(function () {
            var play = 1;
            $(".MusicPicButton").click(function () {
                if (play == 0) {
                    $(this).attr("src", "${ctx}/images/pause.png");
                    $(".MusicPlayProcess").removeClass("pause");
                    $(".MusicPicName").removeClass("pause");
                    $("audio").get(0).play();
                    play = 1;
                } else {
                    $(this).attr("src", "${ctx}/images/play.png");
                    $(".MusicPlayProcess").addClass("pause");
                    $(".MusicPicName").addClass("pause");
                    play = 0;
                    $("audio").get(0).pause();
                }
            });
        });
    </script>
</head>
<body>
<div class="Music">
    <div class="MusicPlaySound">
        <img class="MusicPlayBg" src="${ctx}/images/playBg.png"/>
        <img class="MusicPlayProcess rotate" src="${ctx}/images/process.png"/>
        <div class="MusicPlayBox">
            <p class="title">${songName}</p>
            <p class="name">${artistName}</p>
            <div class="MusicPic">
                <img class="MusicPicName PicNameRotate" src="${ctx}/images/rotate.png"/>
                <img class="MusicPicButton" src="${ctx}/images/pause.png"/>
            </div>
        </div>
    </div>
    <audio src="${url}" autoplay="autoplay" loop="loop"></audio>
</div>
</body>
</html>
