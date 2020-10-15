function initFileList(url){
    $.ajax({
        type : 'get',
        url : url,
        contentType: "application/json; charset=utf-8",
        async:false,
        success : function(data) {
            let length = data.length;
            for(let i = 0; i < length; i++){
                let tr = "<tr mapping-id='" + data[i]['id'] + "' is-dir='" + data[i]['isDir'] + "'>";
                tr += addCol(data[i]['fileName']);
                tr += addCol(buildOperation(data[i]));
                tr += addCol(renderSize(data[i]['size']));
                tr += addCol(data[i]['updateAt']);
                $("#dt-table").append(tr);
            }
        },
        error:function (xhr,status,error) {
            layer.msg(xhr.responseText, {shift: -1, time: 1000});
        }
    });
}
function addCol(data) {
    if (data != null) {
        return "<td>" + data +"</td>";
    }
    return "<td></td>";
}

/**
 * 构建文件列表中的 操作 列
 * @param data fileMapping
 */
function buildOperation(data) {
    // 默认按钮组， 文件的增改善查等
    let operation =
        '<div class="btn-group file-func-item">\n' +
        '  <i class="glyphicon glyphicon-option-horizontal dropdown-toggle hvr-bounce-in " data-toggle="dropdown" aria-expanded="false"></i>' +
        '  <ul class="dropdown-menu" role="menu">\n' +
        '    <li><a href="#">详情</a></li>\n' +
        '    <li><a href="#">重命名</a></li>\n' +
        '    <li><a href="#">移动或复制</a></li>\n' +
        '    <li><a href="#">移入回收站</a></li>\n' +
        '  </ul>\n' +
        '</div>'
    operation += '<i class="glyphicon glyphicon-link file-func-item hvr-bounce-in"></i>'
    operation += '<i class="glyphicon glyphicon-download-alt file-func-item hvr-bounce-in" onclick="getDownloadToken(this)"></i>'
    return operation;
}
/// <summary>
/// 格式化文件大小的JS方法
/// </summary>
/// <param name="filesize">文件的大小,传入的是一个bytes为单位的参数</param>
/// <returns>格式化后的值</returns>
function renderSize(value){
    if(null==value||value===''){
        return "0 Bytes";
    }
    var unitArr = ["Bytes","KB","MB","GB","TB","PB","EB","ZB","YB"];
    var index=0;
    var srcSize = parseFloat(value);
    index=Math.floor(Math.log(srcSize)/Math.log(1024));
    var size =srcSize/Math.pow(1024,index);
    size=size.toFixed(2);//保留的小数位数
    return size+unitArr[index];
}

function getDownloadToken(obj) {
    $.ajax({
        type: 'get',
        url: '/file/download?mappingId=' + obj.parentElement.parentElement.getAttribute("mapping-id"),

        success: function (data) {
            downloadLocalHostServer(data);
        },
        error: function (xhr, textStatus, errorThrown) {
            alert("error:");
        }
    });
}
