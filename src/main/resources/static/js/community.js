function post() {
    var questionId = $("#question_id").val();
    // console.log(questionId);
    var content = $("#comment_content").val();
    // console.log(content);
    $.ajax({
        type:"POST",
        url:"/comment",
        contentType:'application/json',
        data:JSON.stringify({
            "parentId": questionId,
            "content": content,
            "type": 1,
        }),
        success: function (response) {
            if (response.code==200){
                $("#comment_section").hide();
            }else {
                if (response.code==2003){
                    var isAccepted = confirm(response.message);
                    if (isAccepted){
                        window.open("https://github.com/login/oauth/authorize?client_id=19ebcb4f921719bd4518&redirect_uri=http://localhost:8080/callback&scope=user&state=1");
                        window.localStorage.setItem("closable","true");
                    }
                }else {

                    alert(response.message)
                }
            }
        },
        dataType:"json"
    });
}