layui.use(['table',"util","layer"],function () {
    var table = layui.table;
    var util=layui.util;
    var layer=layui.layer;
    table.render({
        elem:"#bloglist",
        height:450,
        width:1165,
        url:"/getBlogByPage.action",
        page:true,
        id:"bloglisttable",
        limit:10,
        cols: [[ //表头
            {checkbox: true, fixed: true}
            ,{field: 'id', title: 'ID',width:80, sort: true, fixed: 'left'}
            ,{field: 'title', title: '标题', width:347}
            ,{field: 'data', title: '发布时间',sort: true,width:200,templet: '<div>{{ layui.util.toDateString(d.data,"yyyy-MM-dd") }}</div>'}
            // d.time 中的 time 即是接口返回的时间字段，如果是 unix 时间戳，这里记得要 d.time*1000，如果是毫秒数，这里直接传 d.time 即可
            ,{field: 'zan', title: '点赞数', sort: true,width:100}
            ,{field: 'label', title: '标签',width:80}
            ,{field:'right', title: '操作', width:300,toolbar:"#bloglistbar",align:"center"}
        ]]
    });
    //监听事件,此处填写table的layui-filter属性
    //监听工具条
    table.on('tool(bloglist)', function(obj){
        var data = obj.data;
        if(obj.event === 'detail'){
            $.ajax({
                url:"/AddBlog.html",
                type: "POST",
                dataType:"html",
                success:function (adata){
                    $("#layui-body").html(adata);
                    $.ajax({
                        url:"/getBlogById.action",
                        type: "POST",
                        dataType:"json",
                        data:{"id":data.id},
                        success:function (bdata) {
                            console.log(bdata)
                            $("#title").val(bdata.title)
                            $("#label").val(bdata.label)
                            $("#AddBlog").html(bdata.article)
                        }
                    })

                }
            })
        } else if(obj.event === 'del'){
            layer.confirm('真的删除行么', function(index){
                layer.close(index);
                $.ajax({
                    url:"/deleteBlog.action",
                    type: "POST",
                    dataType:"json",
                    data: {id:data.id},
                    success:function (data) {
                        if(data[0]=="true"){
                            alert("删除成功")
                            table.reload('bloglisttable',{
                                url:"/getBlogByPage.action"
                                ,page: {
                                    curr: 1 //重新从第 1 页开始
                                }
                            })
                        }else{
                            alert("删除失败")
                        }
                    }
                })
            });
        } else if(obj.event === 'edit'){
            $.ajax({
                url:"/AddBlog.html",
                type: "POST",
                dataType:"html",
                success:function (adata){
                    $("#layui-body").html(adata);
                    $.ajax({
                        url:"/getBlogById.action",
                        type: "POST",
                        dataType:"json",
                        data:{"id":data.id},
                        success:function (bdata) {
                            console.log(bdata)
                            $("#title").val(bdata.title)
                            $("#label").val(bdata.label)
                            $("#AddBlog").html(bdata.article)
                        }
                    })

                }
            })
        }
    });
    //搜索框逻辑处理
    var $ = layui.jquery, active = {
        reload: function(){
            var keyword = $('#keyword');
            //执行重载
            table.reload('bloglisttable', {
                url:"/getBlogWithKeyWord.action"
                ,where: {keyword:keyword.val()}
            });
        }
    };
    $('.searchBlog .layui-btn').on('click', function(){
        var type = $(this).data('type');
        active[type] ? active[type].call(this) : '';
    });
})