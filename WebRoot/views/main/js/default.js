function loadSrc(src) {
	if ($("#iframe")) {
		$('#iframe').load(src);
	} else {
		console.log("id为iframe的元素不存在！");
	}
}