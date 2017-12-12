/**
 * Created by dongbin on 2017/6/28.
 */
function topData(url,level)
{
    var tip = "";
    var tips = "";
    var action = url;
    var ids = "";
    var temp; //存放临时id
    $(":checkbox").each(function()
    {
        if($(this).attr("checked")){//被选择
            temp = $(this).attr("value");
            ids += temp + ",";
        }
    });
    if(level === 1)
    {
        tip = "您还没有选择要置顶的数据哦!";
        tips= "确定置顶数据吗";
    }
    else if(level === 0)
    {
        tip = "您还没有选择要取消置顶的数据哦!";
        tips= "确定取消置顶数据吗";
    }
    if(ids === "")
    {//判断有没有选择


        zoom.showMessageDialog(tip,"消息提示", 1500);
        return;
    }


    ids = ids.substr(0,ids.length-1);//去掉最后的逗号

    var data = {
        ids : ids,
        level : level
    };

    zoom.showConfirmDialog(tips,"消息提示", function()
    {
        $.getJSON(action, data, function(data)
        {
            if(data.status)
            {
                zoom.showMessageDialog(data.message,"消息提示", 1500);
                queryList();
            }
            else
            {
                zoom.showMessageDialog(data.message,"消息提示", 1500);
            }
            zoom.closeConfirmDialog();
        });
    });
}