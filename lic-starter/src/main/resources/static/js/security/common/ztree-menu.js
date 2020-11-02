function getMenuTree() {
    let root = {
        id: 0,
        name: "root",
        open: true,
    };

    $.ajax({
        type: 'get',
        url: '/permissions/all',
        contentType: "application/json; charset=utf-8",
        async: false,
        success: function (data) {
            let length = data.length;
            let children = [];
            for (let i = 0; i < length; i++) {
                let d = data[i];
                children[i] = createNode(d);
            }

            root.children = children;
        }
    });

    return root;
}

function initMenuDatas(roleId) {
    $.ajax({
        type: 'get',
        url: '/permissions?roleId=' + roleId,
        success: function (data) {
            let length = data.length;
            let ids = [];
            for (let i = 0; i < length; i++) {
                ids.push(data[i]['id']);
            }

            initMenuCheck(ids);
        }
    });
}

function initMenuCheck(ids) {
    let treeObj = $.fn.zTree.getZTreeObj("treeDemo");
    let length = ids.length;
    if (length > 0) {
        let node = treeObj.getNodeByParam("id", 0, null);
        treeObj.checkNode(node, true, false);
    }

    for (let i = 0; i < length; i++) {
        let node = treeObj.getNodeByParam("id", ids[i], null);
        treeObj.checkNode(node, true, false);
    }

}

function getCheckedMenuIds() {
    let treeObj = $.fn.zTree.getZTreeObj("treeDemo");
    let nodes = treeObj.getCheckedNodes(true);

    let length = nodes.length;
    let ids = [];
    for (let i = 0; i < length; i++) {
        let n = nodes[i];
        let id = n['id'];
        ids.push(id);
    }

    return ids;
}

function createNode(d) {
    let id = d['id'];
    let pId = d['parentId'];
    let name = d['name'];
    let child = d['child'];

    let node = {
        open: true,
        id: id,
        name: name,
        pId: pId,
    };

    if (child != null) {
        let length = child.length;
        if (length > 0) {
            let children = [];
            for (let i = 0; i < length; i++) {
                children[i] = createNode(child[i]);
            }

            node.children = children;
        }

    }
    return node;
}

function initParentMenuSelect() {
    $.ajax({
        type: 'get',
        url: '/permissions/parents',
        async: false,
        success: function (data) {
            let select = $("#parentId");
            select.append("<option value='0'>root</option>");
            for (let i = 0; i < data.length; i++) {
                let d = data[i];
                let id = d['id'];
                let name = d['name'];

                select.append("<option value='" + id + "'>" + name + "</option>");
            }
        }
    });
}

function getSettting() {
    return {
        check: {
            enable: true,
            chkboxType: {
                "Y": "ps",
                "N": "ps"
            }
        },
        async: {
            enable: true,
        },
        data: {
            simpleData: {
                enable: true,
                idKey: "id",
                pIdKey: "pId",
                rootPId: 0
            }
        },
        callback: {
            onCheck: zTreeOnCheck
        }
    };
}

function zTreeOnCheck(event, treeId, treeNode) {
//	console.log(treeNode.id + ", " + treeNode.name + "," + treeNode.checked
//			+ "," + treeNode.pId);
//	console.log(JSON.stringify(treeNode));
}
