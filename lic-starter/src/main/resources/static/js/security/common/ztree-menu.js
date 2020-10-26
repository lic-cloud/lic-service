function getMenuTree() {
	const root = {
		id: 0,
		name: "root",
		open: true,
	};

	$.ajax({
		type : 'get',
		url : '/permissions/all',
		contentType : "application/json; charset=utf-8",
		async : false,
		success : function(data) {
			const length = data.length;
			const children = [];
			for (let i = 0; i < length; i++) {
				const d = data[i];
				children[i] = createNode(d);
			}

			root.children = children;
		}
	});

	return root;
}

function initMenuDatas(roleId){
	$.ajax({
		type : 'get',
		url : '/permissions?roleId=' + roleId,
		success : function(data) {
			var length = data.length;
			var ids = [];
			for(let i=0; i<length; i++){
				ids.push(data[i]['id']);
			}

			initMenuCheck(ids);
		}
	});
}

function initMenuCheck(ids) {
	let node;
	const treeObj = $.fn.zTree.getZTreeObj("treeDemo");
	const length = ids.length;
	if(length > 0){
		node = treeObj.getNodeByParam("id", 0, null);
		treeObj.checkNode(node, true, false);
	}

	for(let i=0; i<length; i++){
		node = treeObj.getNodeByParam("id", ids[i], null);
		treeObj.checkNode(node, true, false);
	}

}

function getCheckedMenuIds(){
	const treeObj = $.fn.zTree.getZTreeObj("treeDemo");
	const nodes = treeObj.getCheckedNodes(true);

	const length = nodes.length;
	const ids = [];
	for(let i=0; i<length; i++){
		const n = nodes[i];
		const id = n['id'];
		ids.push(id);
	}

	return ids;
}

function createNode(d) {
	const id = d['id'];
	const pId = d['parentId'];
	const name = d['name'];
	const child = d['child'];

	const node = {
		open: true,
		id: id,
		name: name,
		pId: pId,
	};

	if (child != null) {
		const length = child.length;
		if (length > 0) {
			const children = [];
			for (let i = 0; i < length; i++) {
				children[i] = createNode(child[i]);
			}

			node.children = children;
		}

	}
	return node;
}

function initParentMenuSelect(){
	$.ajax({
        type : 'get',
        url : '/permissions/parents',
        async : false,
        success : function(data) {
			const select = $("#parentId");
			select.append("<option value='0'>root</option>");
            for(let i=0; i<data.length; i++){
				const d = data[i];
				const id = d['id'];
				const name = d['name'];

				select.append("<option value='"+ id +"'>" +name+"</option>");
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
