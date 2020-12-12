let fileTableItem = ["类型","文件名","文件操作", "文件大小", "修改日期"];
let fileType = ["share", "normal","recycle"];
function addTdTag(str) {
    return "<td>" + (str == null ? "" : str) + "</td>";
}

function buildFileName(name) {
    return '<td  onclick="getDownloadToken(this)">' + name + '</td>';
}


/**
 * 构建文件列表中的 操作 列
 * @param id fileMapping
 */
function buildOperation(id, type) {
    // 默认按钮组， 文件的增改善查等
    let operation = "";
    operation += '<i class="glyphicon glyphicon-info-sign file-func-item" title="详情"></i>'
    if (type == "normal") {
        operation += '<i class="glyphicon glyphicon-share file-func-item" title="分享"></i>'
        operation += '<i class="glyphicon glyphicon-edit file-func-item" title="重命名"></i>'
        operation += '<i class="glyphicon glyphicon-copy file-func-item" title="复制或移动"></i>'
        operation += '<i class="glyphicon glyphicon-trash file-func-item" title="移入回收站"></i>'
    }
    if (type == "recycle") {
        operation += '<i class="glyphicon glyphicon-repeat file-func-item" title="从回收站恢复"></i>'
    }
    return operation;
}
/// <summary>
/// 格式化文件大小的JS方法
/// </summary>
/// <param name="filesize">文件的大小,传入的是一个bytes为单位的参数</param>
/// <returns>格式化后的值</returns>
function renderSize(value){
    if(null==value||value===''||value===0){
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
function getFileTypeBySuffix(value, isDir) {
    if (isDir == true) {
        return "文件夹"
    }
    return  value.substring(value.lastIndexOf('.') + 1);
}
function getDownloadToken(id) {
    ajax_get('/file/download?mappingId=' + id, null,downloadLocalHostServer);
}


function initTable(url, tableId, fileType){

    let thead = '<thead id=' + tableId + '><tr>' + buildTableItems(fileTableItem) + '</tr></thead>';
    $('#' + tableId).append(thead);
    $('#' + tableId).DataTable({
        "searching": false,
        "processing": false,
        "serverSide": true,
        "language": {
            "url": "/js/plugin/datatables/Chinese.lang"
        },
        "ajax": {
            "url": url,
            "type": "get",
            "error": function (xhr, textStatus, errorThrown) {
                fail_prompt(xhr.responseText)
            }
        },
        "dom": "<'dt-toolbar'r>t<'dt-toolbar-footer'<'col-sm-10 col-xs-12 hidden-xs'i><'col-xs-12 col-sm-10' p v>>",
        "columns": [
            {
                "data": "",
                "defaultContent":"",
                "width": "5%",
                "orderable": false,
                "render": function (data, type, row) {
                    return getFileTypeBySuffix(row['fileName'], row['isDir']);
                }
            },
            {
                "data": "",
                "defaultContent": "",
                "width": "55%",
                "render": function (data, type, row) {
                    if (row['isDir'] === true) {
                        return String.format(
                            '<a href="#" onclick="jump2Dir(\'{0}\', \'{1}\')">{2}</a>',
                            tableId, row['id'], row['fileName'])
                    }
                    return String.format(
                        '<a href="#" onclick="getDownloadToken({0})">{1}</a>',
                        row['id'], row['fileName']);
                }
            },
            {
                "width": "20%",
                "data": "",
                "defaultContent": "操作栏",
                "oderable": false,
                "render": function (data, type, row) {
                    return buildOperation(row['id'], fileType);
                }
            },
            {
                "width": "10%",
                "data": "size",
                "defaultContent": "文件大小",
                "orderable": false,
                "render": function (data, type, row) {
                    return renderSize(data);
                }
            },
            {"width": "10%","data": "updateAt", "defaultContent": ""}
        ],
        "order": [[0, "asc"]]
    });
    jump2Dir(tableId, 0);

}

function buildTableItems(items) {
    let res = "";
    for (let item of items) {
        res += "<th>" + item + "</th>";
    }
    return res;
}

function Progress(val, surplus) {
    let dom = $(".progress-bar")
    dom.parent().css("display", '')
    dom.attr("aria-valuenow", val)

    dom.css("width", val + "%")
}

function progressSize(text) {
    let dom = $(".progress-bar")
    dom.text("剩余: " + text)
}

function hideProgress() {
    let dom = $(".progress-bar")
    dom.attr("aria-valuenow", 0)
    dom.css("width", 0 + "%")
    dom.parent().css("display", "none")
}


function jump2Dir(tableId, id) {

    let table = $('#' + tableId).DataTable();
    // 回跳时面包屑刷新
    let parent = $("#"+tableId+"-bar")
    let array = parent.children();
    for (let i = 0; i < array.length; i++){
        if (array[i].classList.contains("active")) {
            $(array[i]).replaceWith(buildBarItem(tableId, array[i].value, array[i].innerHTML, false));
        }
        if (array[i].getAttribute("data-id") == id) {
            array.splice(i);
            parent.empty();
            parent.append(array);
            break;
        }
    }
    let targetMapping = ajax_sync_get("/file?mappingId=" + id);
    parent.append(buildBarItem(tableId, id, targetMapping.fileName));


    table.ajax.url( "/file/page?pid=" + id).load();
}

function buildBarItem(tableId, id, displayName, active = true) {
    let template ;
    if (active) {
        template = String.format('<li class="active" data-id="{0}">{1}</li>', id, displayName)
    } else {
        template = String.format('<li onClick="jump2Dir(\'{0}\', \'{1}\')" data-id="{2}"><a href="#">{3}</a></li>',
            tableId, id, id, displayName)
    }

    return template;
}