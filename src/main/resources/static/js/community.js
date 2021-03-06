/**
 * 提交回复
 */
function post() {
    var questionId = $("#question_id").val();
    // console.log(questionId);
    var content = $("#comment_content").val();
    // console.log(content);

    comment2target(questionId, 1, content);
}

function comment2target(targetId, type, content) {


    if (!content) {
        alert("不能回复空内容~~~");
        return;
    }


    $.ajax({
        type: "POST",
        url: "/comment",
        contentType: 'application/json',
        data: JSON.stringify({
            "parentId": targetId,
            "content": content,
            "type": type,
        }),
        success: function (response) {
            if (response.code == 200) {
                // $("#comment_section").hide();
                window.location.reload();
            } else {
                if (response.code == 2003) {
                    var isAccepted = confirm(response.message);
                    if (isAccepted) {
                        window.open("https://github.com/login/oauth/authorize?client_id=19ebcb4f921719bd4518&redirect_uri=http://localhost:8080/callback&scope=user&state=1");
                        window.localStorage.setItem("closable", "true");
                    }
                } else {

                    alert(response.message)
                }
            }
        },
        dataType: "json"
    });


}

function comment(e) {
    var commentId = e.getAttribute("data-id");
    var content = $("#input-" + commentId).val();
    comment2target(commentId, 2, content)

}


/**
 * 展开二级评论
 */
function collapseComments(e) {
    var id = e.getAttribute("data-id");
    var comments = $("#comment-" + id);

    // 获取一下二级评论的展开状态
    var collapse = e.getAttribute("data-collapse");
    if (collapse) {
        // 折叠二级评论
        comments.removeClass("in");
        e.removeAttribute("data-collapse");
        e.classList.remove("active");
    } else {
        var subCommentContainer = $("#comment-" + id);
        if (subCommentContainer.children().length != 1) {
            //展开二级评论
            comments.addClass("in");
            // 标记二级评论展开状态
            e.setAttribute("data-collapse", "in");
            e.classList.add("active");
        } else {
            $.getJSON("/comment/" + id, function (data) {
                $.each(data.data.reverse(), function (index, comment) {
                    var mediaLeftElement = $("<div/>", {
                        "class": "media-left"
                    }).append($("<img/>", {
                        "class": "media-object img-rounded",
                        "src": comment.user.avatarUrl
                    }));

                    var mediaBodyElement = $("<div/>", {
                        "class": "media-body"
                    }).append($("<h5/>", {
                        "class": "media-heading",
                        "html": comment.user.name
                    })).append($("<div/>", {
                        "html": comment.content
                    })).append($("<div/>", {
                        "class": "menu"
                    })
                        .append($("<span/>", {
                                "class": "pull-right",
                                "html": moment(comment.gmtCreate).format('YYYY-MM-DD')
                            }
                        )));

                    var mediaElement = $("<div/>", {
                        "class": "media"
                    }).append(mediaLeftElement).append(mediaBodyElement);

                    var commentElement = $("<div/>", {
                        "class": "col-lg-12 col-md-12 col-sm-12 col-xs-12 comments"
                    }).append(mediaElement);

                    subCommentContainer.prepend(commentElement);
                });
                //展开二级评论
                comments.addClass("in");
                // 标记二级评论展开状态
                e.setAttribute("data-collapse", "in");
                e.classList.add("active");
            });
        }
    }

// function collapseComments(e) {
//     // var id = $(e).data("id");
//     var id = e.getAttribute("data-id");
//     // console.log(id);
//     var comments = $("#comment-" + id);
//
//
//
//     $.getJSON("/comment/"+id,function (data) {
//
//        var commentBody = $("comment-body-"+id);
//        var items = [];
//
//        $.each(data.data,function (comment) {
//            items.push("<li id='"+key+"'>"+val+"</li>");
//
//        });
//
//        $("<div/>",{
//            "class":"col-lg-12 col-mg-12 col-sm-12 col-xs-12 collapse sub-comments",
//            "id":"comment-"+id,
//            html:items.join("")
//        }).appendTo(commentBody);
//
//     });
//
//
//     comments.toggleClass("in");
//     var icon = $(e);
//     icon.toggleClass("active");

}

function selectTag(e) {
    var value = e.getAttribute("data-tag");

    var previous = $("#tag").val();

    if (previous.split(',').indexOf(value) == -1) {

        if (previous) {
            $("#tag").val(previous + "," + value);
        } else {
            $("#tag").val(value);

        }
    }
}

function showSelectTag() {

    $('#selectTag').show();

}

// //<!CDATA[
// var bodyBgs = [];    //创建一个数组变量来存储背景图片的路径
// // bodyBgs[0] = "images/suimu.jpg";
// // bodyBgs[1] = "images/suimu.jpg";
// // bodyBgs[2] = "images/suimu.jpg";
// // bodyBgs[3] = "images/suimu.jpg";
// // bodyBgs[0] = "images/01_bg.jpg";
// // bodyBgs[1] = "images/02_bg.jpg";
// // bodyBgs[2] = "images/03_bg.jpg";
// // bodyBgs[3] = "images/04_bg.jpg";
// bodyBgs[0] = "#0bfff3";
// bodyBgs[1] = "palegreen";
// bodyBgs[2] = "mediumpurple";
// bodyBgs[3] = "lightgray";
// bodyBgs[4] = "saddlebrown";
// bodyBgs[5] = "pink";
// bodyBgs[6] = "orange";
// bodyBgs[7] = "lightsteelblue";
//
//
// var randomBgIndex = Math.round(Math.random() * 8);
//
// //输出随机的背景图
// // document.write('<style>body{background:url(' + bodyBgs[randomBgIndex] + ') background-size:100% 100% ; background-attachment: fixed}</style>');
// document.write('<style>body{background:' + bodyBgs[randomBgIndex] + ' }</style>');
// //]]>


