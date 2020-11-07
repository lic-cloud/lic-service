let fileTableItem = ["类型","文件名","文件操作", "文件大小", "修改日期"];

/**
 * 构建文件列表大体结构
 */
function buildList(data) {
    let length = data.length;
    for(let i = 0; i < length; i++){
        let info = data[i];
        let tr = "<tr class='file-list-col file-tr focus' " +
            "mapping-id='" + info['id'] +
            "' is-dir='" + info['isDir'] +
            "' file-size='" + info['size'] +
            "' file-name='" + info['fileName'] +
            "' is-shared'" + info['share'] +
            "' update-at='" + info['updateAt'] + "'>";
        $("#dt-table").append(tr);
    }
}
/**
 * 根据<tr>标签中的attribute构建条目
 */
function buildListItem() {
    $('.file-list-col').each(function () {
        let col = buildFileName($(this).attr('file-name'));
        col += addTdTag(buildOperation());
        col += addTdTag(renderSize($(this).attr('file-size')));
        col += addTdTag($(this).attr('update-at'));
        $(this).append(col);
    })
}

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
    // TODO 适配分享、回收站等场景
    let operation =
        '<div class="btn-group file-func-item">\n' +
        '  <i class="glyphicon glyphicon-option-horizontal dropdown-toggle" data-toggle="dropdown" aria-expanded="false"></i>' +
        '  <ul class="dropdown-menu" role="menu">\n' +
        '    <li><a href="#">详情</a></li>\n' +
        '    <li><a href="#">重命名</a></li>\n' +
        '    <li><a href="#">移动或复制</a></li>\n' +
        '    <li><a href="#">移入回收站</a></li>\n' +
        '  </ul>\n' +
        '</div>'
    operation += '<i class="glyphicon glyphicon-link file-func-item"></i>'
    operation += '<i class="glyphicon glyphicon-download-alt file-func-item" onclick="getDownloadToken(' + id + ')"></i>';
    return operation
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
function getSuffix(value) {
    return  value.substring(value.lastIndexOf('.') + 1);
}
function getDownloadToken(id) {
    ajax_get('/file/download?mappingId=' + id, null,downloadLocalHostServer);
}


function initTable(url, tableId, fileType){
    let thead = '<thead id=' + tableId + '><tr>' + buildTableItems(fileTableItem) + '</tr></thead>';
    $('#' + tableId).append(thead);
    debugger
    return $('#' + tableId).DataTable({
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
                    return getSuffix(row['fileName']);
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
                        '<a href="#" onclick="jump2Dir(\'{0}\', \'{1}\')">{2}</a>',
                        tableId, row['id'], row['fileName'])
                }
            },
            {
                "width": "20%",
                "data": "",
                "defaultContent": "",
                "oderable": false,
                "render": function (data, type, row) {
                    return buildOperation(row['id'], type);
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
}

function buildTableItems(items) {
    let res = "";
    for (let item of items) {
        res += "<th>" + item + "</th>";
    }
    return res;
}

function jump2Dir(tableId, id) {
    let table = $('#' + tableId).DataTable();
    table.ajax.reload(ajax_sync_get("/file/page?pid=" + id));
}
