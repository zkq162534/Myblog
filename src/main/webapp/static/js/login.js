$(function () {
    $("#login-form").bootstrapValidator({
        message: "This value is not valid",
        feedbackIcons: {
            valid: "glyphicon glyphicon-ok",
            invalid:"glyphicon glyphicon-remove",
            validating: "glyphicon glyphicon-refresh"
        },
        fields: {
            username: {
                message: "用户名验证失败",
                validators: {
                    notEmpty: {
                        message:"用户名不能为空"
                    },
                    stringLength: {
                        min: 6,
                        max: 18,
                        message: '用户名长度必须在6到18位之间'
                    }
                }
            },
            password: {
                validators: {
                    notEmpty: {
                        message: '密码不能为空'
                    },
                    stringLength: {
                        min: 6,
                        max: 18,
                        message: '用户名长度必须在6到18位之间'
                    },
                    regexp: {
                        regexp: /^[a-zA-Z0-9_]+$/,
                        message: "密码只能包含大写、小写、数字和下划线"
                    }
                }
            }
        }
    });
    $("#login").on("click",function () {
        var username=$("#username").val()
        var password=$("#password").val()
        var remeber=$("#remeberMe").val()
        $.ajax({
            url:"/login.action",
            type: "POST",
            dataType:"json",
            data:{"username":username,"password":password,"remberMe":remeber},
            success:function (data) {
                if(data==true){
                    window.location.href="/toMain.action";
                }else{
                    alert("用户名或密码错误，请重新输入")
                }
            }
        })
    })
    function getCookie(key){
        var arr1 = document.cookie.split('; ');//这里是分号+空格分隔
        for( var i = 0 , len = arr1.length ; i < len ; i++ ){
            var arr2 = arr1[i].split('=');
            if( arr2[0] == key ){
                return decodeURI(arr2[1]);
            }
        }
    }
});