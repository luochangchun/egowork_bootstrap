var loginUser = {};
var phone_reg = /^1[3|4|5|7|8][0-9]\d{8}$/;
var tel_reg = /(^1[34578]\d{9}$)|(^0\d{2,3}-\d{7,8}$)|(^\d{7,8}$)/;
var pwd_reg = /^[A-Za-z0-9\!\@\#\$\%\^\&\*\(\)\_\+\`\~\-\=]{6,22}$/;
var username_reg = /^[a-zA-Z]{1}[0-9a-zA-Z_]{5,22}$/;
var email_reg = /[\w!#$%&'*+/=?^_`{|}~-]+(?:\.[\w!#$%&'*+/=?^_`{|}~-]+)*@(?:[\w](?:[\w-]*[\w])?\.)+[\w](?:[\w-]*[\w])?/;
var form_url = "http://localhost:8080";
jQuery.fn.widResize = function (Func) {
	Func();
	$(window).resize(function () {
		Func();
	});
}
// function initCourse() {
// 	$.ajax({
// 		url: "http://192.168.11.222/egocourse/webapp/cou/list?currentPage=1&pageSize=8",    //请求的url地址   
// 		dataType: "json",   //返回格式为json    
// 		async: true, //请求是否异步，默认为异步，这也是ajax重要特性    
// 		data: {},    //参数值    
// 		type: "GET",   //请求方式    
// 		success: function (data) {
// 			console.log(data);
// 		},
// 		complete: function () {
// 			//请求完成的处理    
// 		},
// 		error: function () {
// 			//请求出错处理    
// 		}
// 	});
// }
$(document).ready(function () {
	var startDate = new Date();
	//预约表单参观日历初始化
	$('#visitPicker').datetimepicker({
		language: 'zh-CN',
		format: 'yyyy-mm-dd hh:ii',
		autoclose: true,
		startDate: new Date(),
		todayBtn: true
	});
	//线上孵化日历初始化
	$('#onlinePicker').datetimepicker({
		language: 'zh-CN',
		format: 'yyyy-mm-dd',
		autoclose: true,
		todayBtn: true,
		minView: '2'
	});
	//共享会议开始日历
	$('#shareFrom').datetimepicker({
		language: 'zh-CN',
		format: 'yyyy-mm-dd hh:ii',
		startDate: new Date(),
		minuteStep: 10,
		autoclose: true,
		todayBtn: true,
	});
	//共享会议结束日历
	$('#shareTo').datetimepicker({
		language: 'zh-CN',
		format: 'yyyy-mm-dd hh:ii',
		minuteStep: 10,
		startDate: new Date((new Date()).getTime() + 30*60*1000),
		autoclose: true,
		todayBtn: true,
	});

	$('#visitBtn').click(function () {
		bookVisit();
	})
	// 验证姓名是否输入
	$("#visitName").blur(function () {
		var visitName = $("#visitName").val();
		if (visitName == "" || visitName == undefined) {
			$("#visitName").parent().parent('.form-group').addClass("has-error");
		} else {
			$("#visitName").parent().parent('.form-group').removeClass("has-error");
		}
	})
	// 验证手机号是否输入
	$("#visitPhone").blur(function () {
		var visitPhone = $("#visitPhone").val();
		if (visitPhone === "" || !tel_reg.test(visitPhone) || visitPhone.length < 7) {
			$("#visitPhone").parent().parent('.form-group').addClass("has-error");
		} else {
			$("#visitPhone").parent().parent('.form-group').removeClass("has-error");
		}
	})
	// 预约参观提交
	function bookVisit(e) {
		var visitName = $("#visitName").val();
		var visitPhone = $("#visitPhone").val();
		var visitIncubator = $("#visitIncubator option:selected").text();
		var visitIncubatorId = $("#visitIncubator option:selected").attr('data-id');
		var visitTime = $("#visitPicker").find("input").val();

		var visitProduct = $("#visitProduct option:selected").text();
		var visitProductId = $("#visitProduct option:selected").attr('data-id');
		if (visitName == "") {
			alert("请输入联系人姓名");
			return false;
		}
		if (visitPhone == "") {
			alert("请输入联系方式");
			return false;
		}
		if (visitTime == "") {
			alert("请选择参观时间");
			return false;
		} else {
			var createAt = new Date(visitTime);
		}

		var params = {
			"contact": visitName,//联系人姓名
			"phone": visitPhone,//联系人手机号
			"incubator": visitIncubator,//孵化器名字
			"incubatorId": visitIncubatorId,//孵化器id
			"visitAt": createAt,//参观时间
			"interest": visitProduct, //感兴趣产品
			"interestId": visitProductId //感兴趣产品id
		};
		var settings = {
			"async": true,
			"crossDomain": true,
			"url": form_url + "/front/Apply/inster/visitapply.do",
			"method": "POST",
			"headers": {
				"content-type": "application/x-www-form-urlencoded",
				"cache-control": "no-cache",
				"postman-token": "ae2414ac-3bc5-34fe-6d2c-d969aa34ff91"
			},
			"data": params
		}
		$.ajax(settings).done(function (data) {
			if (data.status == true) {
				alert(data.message);
				$('#bookVisit').modal('hide');
			} else {
				alert("提交失败，请稍后再试");
			}
		});
	}
	// 发布需求表单
	function demandApply() {
		var demandClassify = $("#demandClassify option:selected").val();
		var demandClassifyId = $("#demandClassify option:selected").attr('data-id');
		var demandTitle = $("#demandTitle").val();
		var demandNeeds = $("#demandNeeds").val();
		var demandEnterprise = $("#demandEnterprise").val();
		var demandName = $("#demandName").val();
		var demandPhone = $("#demandPhone").val();
		if (demandEnterprise == "") {
			alert("请输入企业名称");
			return false;
		}
		if (demandName == "") {
			alert("请输入联系人姓名");
			return false;
		}
		if (demandPhone == "") {
			alert("请输入联系方式");
			return false;
		}
		var params = {
			"classifyId": demandClassifyId, //需求分类id
			"classify": demandClassify, //需求分类
			"enterprise": demandEnterprise, //企业名称
			"contact": demandName, //联系人
			"phone": demandPhone, //手机号
			"title": demandTitle, //需求标题
			"needs": demandNeeds //需求内容
		};
		var settings = {
			"async": true,
			"crossDomain": true,
			"url": form_url + "/front/Apply/inster/needs.do",
			"method": "POST",
			"headers": {
				"content-type": "application/x-www-form-urlencoded",
				"cache-control": "no-cache",
				"postman-token": "ae2414ac-3bc5-34fe-6d2c-d969aa34ff91"
			},
			"data": params
		}
		$.ajax(settings).done(function (data) {
			if (data.status == true) {
				alert(data.message);
				$('#demandForm').modal('hide');
			} else {
				alert("提交失败，请稍后再试");
			}
		});
	}
	$('#demandBtn').click(function () {
		demandApply();
	})

	// 线上孵化表单
	function onlineApply() {
		var onlineEnterprise = $("#onlineEnterprise").val(); //企业名称
		var onlineSubstance = $("#onlineSubstance option:selected").text(); //企业性质
		var onlineSubstanceId = $("#onlineSubstance option:selected").attr('data-id'); //企业性质id
		var onlineEmployee = $("#onlineEmployee").val(); //在职人数
		var onlineBusiness = $("#onlineBusiness").val(); //经营范围及主营业务
		var onlineTime = $("#onlinePicker").find("input").val(); //注册时间
		var onlineSignupAddr = $("#onlineSignupAddr").val(); //注册地址
		var onlineCapital = $("#onlineCapital").val(); //注册资金
		var onlineName = $("#onlineName").val(); //联系人
		var onlinePhone = $("#onlinePhone").val(); //机号
		var onlineEmployee_reg = /^[1-9]\d{0,3}$/;
		if (onlineSubstanceId == "-1") {
			alert("请选择企业性质");
			return false;
		}
		if (onlineEmployee == '' || onlineEmployee == 0 || !onlineEmployee_reg.test(onlineEmployee)) {
			alert("请输入企业在职人数");
			return false;
		}
		if (onlineBusiness == '') {
			alert("请输入经营范围及主营业务");
			return false;
		}
		if (onlineCapital == '' || !onlineEmployee_reg.test(onlineCapital)) {
			alert("请输入注册资金");
		}
		if (onlineName == "") {
			alert("请输入联系人姓名");
			return false;
		}
		if (onlinePhone == "") {
			alert("请输入联系方式");
			return false;
		}
		if (onlineTime == "") {
			var params = {
				"contact": onlineName,
				"phone": onlinePhone,
				"enterprise": onlineEnterprise,
				"signupAddr": onlineSignupAddr,
				"business": onlineBusiness,
				"substanceId": onlineSubstanceId,
				"substance": onlineSubstance,
				"employee": onlineEmployee,
				"capital": onlineCapital
			};
		} else {
			var createAt = new Date(onlineTime);
			var params = {
				"contact": onlineName,
				"phone": onlinePhone,
				"enterprise": onlineEnterprise,
				"signup": createAt,
				"signupAddr": onlineSignupAddr,
				"business": onlineBusiness,
				"substanceId": onlineSubstanceId,
				"substance": onlineSubstance,
				"employee": onlineEmployee,
				"capital": onlineCapital
			};
		}
		var settings = {
			"async": true,
			"crossDomain": true,
			"url": form_url + "/front/Apply/inster/onlineapply.do",
			"method": "POST",
			"headers": {
				"content-type": "application/x-www-form-urlencoded",
				"cache-control": "no-cache",
				"postman-token": "ae2414ac-3bc5-34fe-6d2c-d969aa34ff91"
			},
			"data": params
		}
		$.ajax(settings).done(function (data) {
			console.log(data);
			if (data.status == true) {
				alert(data.message);
				$('#onlineForm').modal('hide');
			} else {
				alert("提交失败，请稍后再试");
			}
		});
	}
	$("#onlineBtn").click(function () {
		onlineApply();
	})
	// 众创空间表单
	function createSpace() {
		var createIncubator = $("#createIncubator option:selected").text(); //孵化器
		var createIncubatorId = $("#createIncubator option:selected").attr('data-id'); //孵化器id
		var createAppellation = $("#createAppellation").val(); //公司名称
		var createQuantity = $("#createQuantity").val(); //团队人数
		var createCases = $("#createCases input:radio:checked").attr('data-value'); //成立情况
		var createGraduate = $('#createGraduate input:radio:checked').attr('data-id'); //是否毕业5年
		var createRange = $("#createRange").val(); //经营范围
		var createArea = $("#createArea").val(); //工位数量
		var createName = $("#createName").val(); //联系人
		var createPhone = $("#createPhone").val(); //联系电话	
		if (createIncubatorId == "-1") {
			alert("请选择入驻孵化器");
			return false;
		}
		if (createGraduate == undefined) {
			alert("请选择是否为毕业5年以上");
			return false;
		}
		if (createName == "") {
			alert("请输入联系人姓名");
			return false;
		}
		if (createPhone == "") {
			alert("请输入联系方式");
			return false;
		}
		if (createCases == undefined) {
			var params = {
				"type": 1,
				"incubatorId": createIncubatorId,
				"incubator": createIncubator,
				"appellation": createAppellation,
				"quantity": createQuantity,
				"graduate": createGraduate,
				"range": createRange,
				"area": createArea,
				"name": createName,
				"phone": createPhone
			};
		} else {
			var params = {
				"type": 1,
				"incubatorId": createIncubatorId,
				"incubator": createIncubator,
				"appellation": createAppellation,
				"quantity": createQuantity,
				"cases": createCases,
				"graduate": createAppellation,
				"range": createRange,
				"area": createArea,
				"name": createName,
				"phone": createPhone
			};
		}

		var settings = {
			"async": true,
			"crossDomain": true,
			"url": form_url + "/front/Apply/inster/tenancy.do",
			"method": "POST",
			"headers": {
				"content-type": "application/x-www-form-urlencoded",
				"cache-control": "no-cache",
				"postman-token": "ae2414ac-3bc5-34fe-6d2c-d969aa34ff91"
			},
			"data": params
		}
		$.ajax(settings).done(function (data) {
			console.log(data);
			if (data.status == true) {
				alert(data.message);
				$('#createSpace').modal('hide');
			} else {
				alert("提交失败，请稍后再试");
			}
		});
	}
	$("#createBtn").click(function () {
		createSpace();
	})
	// 共享会议室
	function shareMeetingRoom() {
		var shareEnterprise = $("#shareEnterprise").val(); //申请企业
		var shareName = $("#shareName").val(); //联系人
		var sharePhone = $("#sharePhone").val(); //手机号
		var shareSubject = $("#shareSubject").val(); //会议主题
		var shareaAddr = $("#shareaAddr option:selected").text(); //会议地址
		var shareaAddrId = $('#shareaAddr option:selected').attr('data-id'); //会议地址Id
		var shareFrom = $("#shareFrom").find("input").val(); //开始时间
		var shareTo = $("#shareTo").find("input").val(); //结束时间
		var shareUsage = $("#shareUsage option:selected").text(); //用途
		var shareUsageId = $("#shareUsage option:selected").attr('data-id'); //用途Id
		var shareAttender = $("#attender").val(); //参加人数
		var shareRequestId = ""; //会议需求
		var shareRequest = $("#shareRequest").val(); //会议需求

		if (shareEnterprise == "") {
			alert("请输入企业名称");
			return false;
		}
		if (shareName == "") {
			alert("请输入联系人姓名");
			return false;
		}
		if (sharePhone == "") {
			alert("请输入联系方式");
			return false;
		}
		if (shareSubject == "") {
			alert("请输入会议活动名称");
			return false;
		}
		if (shareaAddrId == "-1") {
			alert("请输入会议活动地址");
			return false;
		}
		if (shareFrom == "") {
			alert("请输入会议开始时间");
			return false;
		}
		if (shareTo == "") {
			alert("请输入会议结束时间");
			return false;
		}
		if (shareUsageId == "-1") {
			alert("请输入会议用途");
			return false;
		}
		if (shareAttender == "") {
			alert("请输入参会人数");
			return false;
		}

		$.each($('#shareRequestId input:checkbox'), function () {
			if (this.checked) {
				shareRequestId += "," + $(this).attr('data-id');
			}
		});
		if (shareRequestId == "") {
			alert("请参会需求");
		    return false;
		} else {
			shareRequestId = shareRequestId.substring(1)
		}
		var params = {
			"enterprise": shareEnterprise,
			"contact": shareName,
			"phone": sharePhone,
			"subject": shareSubject,
			"addr": shareaAddr,
			"addrId": shareaAddrId,
			"from": new Date(shareFrom),
			"to": new Date(shareTo),
			"usage": shareUsage,
			"usageId": shareUsageId,
			"attender": shareAttender,
			"requestId": shareRequestId,
			"request": shareRequest
		};
		var settings = {
			"async": true,
			"crossDomain": true,
			"url": form_url + "/front/Apply/inster/roomapply.do",
			"method": "POST",
			"headers": {
				"content-type": "application/x-www-form-urlencoded",
				"cache-control": "no-cache",
				"postman-token": "ae2414ac-3bc5-34fe-6d2c-d969aa34ff91"
			},
			"data": params
		}
		$.ajax(settings).done(function (data) {
			console.log(data);
			if (data.status == true) {
				alert(data.message);
				$('#shareMeetingRoom').modal('hide');
			} else {
				alert("提交失败，请稍后再试");
			}
		});
	}
	$("#shareBtn").click(function () {
		shareMeetingRoom();
	})

	// 空间租赁表单
	function spaceRent() {
		var rentIncubator = $("#rentIncubator option:selected").text(); //孵化器
		var rentIncubatorId = $("#rentIncubator option:selected").attr('data-id'); //孵化器id
		var rentAppellation = $("#rentAppellation").val(); //公司名称
		var rentQuantity = $("#rentQuantity").val(); //团队人数
		var rentCases = $("#rentCases input:radio:checked").attr('data-value'); //成立情况
		var rentGraduate = $('#rentGraduate input:radio:checked').attr('data-id'); //是否毕业5年
		var rentRange = $("#rentRange").val(); //经营范围
		var rentArea = $("#rentArea").val(); //工位数量
		var rentName = $("#rentName").val(); //联系人
		var rentPhone = $("#rentPhone").val(); //联系电话	
		if (rentIncubatorId == "-1") {
			alert("请选择入驻孵化器");
			return false;
		}
		if (rentGraduate == undefined) {
			alert("请选择是否为毕业5年以上");
			return false;
		}
		if (rentName == "") {
			alert("请输入联系人姓名");
			return false;
		}
		if (rentPhone == "") {
			alert("请输入联系方式");
			return false;
		}
		if (rentCases == undefined) {
			var params = {
				"type": 2,
				"incubatorId": rentIncubatorId,
				"incubator": rentIncubator,
				"appellation": rentAppellation,
				"quantity": rentQuantity,
				"graduate": rentGraduate,
				"range": rentRange,
				"area": rentArea,
				"name": rentName,
				"phone": rentPhone
			};
		} else {
			var params = {
				"type": 2,
				"incubatorId": rentIncubatorId,
				"incubator": rentIncubator,
				"appellation": rentAppellation,
				"quantity": rentQuantity,
				"cases": rentCases,
				"graduate": rentAppellation,
				"range": rentRange,
				"area": rentArea,
				"name": rentName,
				"phone": rentPhone
			};
		}

		var settings = {
			"async": true,
			"crossDomain": true,
			"url": form_url + "/front/Apply/inster/tenancy.do",
			"method": "POST",
			"headers": {
				"content-type": "application/x-www-form-urlencoded",
				"cache-control": "no-cache",
				"postman-token": "ae2414ac-3bc5-34fe-6d2c-d969aa34ff91"
			},
			"data": params
		}
		$.ajax(settings).done(function (data) {
			console.log(data);
			if (data.status == true) {
				alert(data.message);
				$('#spaceRent').modal('hide');
			} else {
				alert("提交失败，请稍后再试");
			}
		});
	}
	$("#rentBtn").click(function () {
		spaceRent();
	})
	//[h-ctrl]
	function hCtrl() {
		$("[h-ctrl]").each(function () {
			var $this = $(this);
			var $h = 0;
			if ($this.find("*[h-main]").length) {
				$this.find("*[h-main]").css({ "height": "auto" });
				$h = $this.find("*[h-main]")[0].offsetHeight;
			} else {
				$this.find("*[h-list]").each(function () {
					$(this).css({ "height": "auto" });
					if ($(this)[0].offsetHeight > $h) $h = $(this)[0].offsetHeight;
				});
			}
			var $rull = $this.data("rull");
			var $w = document.body.clientWidth;
			var $rullnum;
			if ($rull == 'sm') $rullnum = 768;
			if ($rull == 'md') $rullnum = 992;
			if ($rull == 'lg') $rullnum = 1200;
			if ($w < $rullnum) {
				$this.find("*[h-list]").css({ "height": "auto" });
			} else {
				$this.find("*[h-list]").css({ "height": $h });
			}
		});
	};
	$(window).widResize(hCtrl);



	$.ajax({
		type: "get",
		contentType: "text/plain;charset=utf-8",
		url: "/ajax?resource=resource=signup/getUserInSession",
		dataType: "json",
		success: function (data) {
			if (data.userId > 0) {
				loginUser = data;
			} else {
				loginUser = null;
			}
			if (loginUser != null && loginUser.userName) {
				$("#unloginElement").html("<a class=\"padder-xxs\" href=\"javascript:void(0)\">您好," + loginUser.userName + "</a>&nbsp;&nbsp;|&nbsp;&nbsp;<a class=\"text-3C\" href=\"javascript:void(0)\" onclick=\"logout();return false;\">注销</a>");
			}
		}
	});

	//注册
	$(".signup form").last().hide();
	$(".registered").click(function () {
		$(this).addClass("line-info").siblings().removeClass("line-info");
		$(this).parent().find("form").eq($(this).index()).show().siblings().hide();
	});
});


function validationLogin(url) {
	$.ajax
		({
			type: "get",
			contentType: "text/plain;charset=utf-8",
			url: "/ajax?resource=resource=signup/getUserInSession",
			dataType: "json",
			async: false,
			success: function (data) {
				if (data.userId > 0) {
					loginUser = data;
				}
				else {
					loginUser = null;
				}
				if (loginUser !== null && loginUser.userName) {

					if (url !== "") {
						if (url.split("?")[1] === "tsb") {
							singleLogin(loginUser.userName, loginUser.aesPassword);
						}
						else if (url.split("?")[1].startsWith("http")) {
							if (url.split("?").length > 2) {
								window.open(url.split("?")[1] + "?" + url.split("?")[2]);
							}
							else {
								window.open(url.split("?")[1]);
							}
							window.location.reload();
						}
						else {
							window.location.href = window.location.protocol + "//" + window.location.host + "/" + url.split("?")[1];
						}
					}

				}
				else {
					window.location.href = window.location.protocol + "//" + window.location.host + "/" + url;
				}
			}
		});
}
function singleLogin(userName, aesPassword) {
	var tmp = document.createElement("form");
	tmp.action = "./buildrequest";
	tmp.target = "_blank";
	tmp.method = "post";
	var userNameInput = document.createElement("input");
	userNameInput.type = "hidden";
	userNameInput.name = "userName";
	userNameInput.value = userName;
	tmp.appendChild(userNameInput);

	var aesPasswordInput = document.createElement("input");
	aesPasswordInput.type = "hidden";
	aesPasswordInput.name = "aesPassword";
	aesPasswordInput.value = aesPassword;
	tmp.appendChild(aesPasswordInput);

	document.body.appendChild(tmp);
	tmp.submit();
	document.body.removeChild(tmp);

}

/* ===== Tooltips ===== */

//$('#tooltip').tooltip();
Array.prototype.remove = function (s) {
	for (var i = 0; i < this.length; i++) {
		if (s == this[i])
			this.splice(i, 1);
	}
}

/**
 * 自定义Map对象
 */
function Map() {
	/** 存放键的数组(遍历用到) */
	this.keys = new Array();
	/** 存放数据 */
	this.data = new Object();

	/**
	 * 放入一个键值对
	 * 
	 * @param {String}
	 *            key
	 * @param {Object}
	 *            value
	 */
	this.put = function (key, value) {
		if (this.data[key] == null) {
			this.keys.push(key);
		}
		this.data[key] = value;
	};

	/**
	 * 获取某键对应的值
	 * 
	 * @param {String}
	 *            key
	 * @return {Object} value
	 */
	this.get = function (key) {
		return this.data[key];
	};

	/**
	 * 删除一个键值对
	 * 
	 * @param {String}
	 *            key
	 */
	this.remove = function (key) {
		this.keys.remove(key);
		this.data[key] = null;
	};

	/**
	 * 遍历Map,执行处理函数
	 * 
	 * @param {Function}
	 *            回调函数 function(key,value,index){..}
	 */
	this.each = function (fn) {
		if (typeof fn != 'function') {
			return;
		}
		var len = this.keys.length;
		for (var i = 0; i < len; i++) {
			var k = this.keys[i];
			fn(k, this.data[k], i);
		}
	};

	/**
	 * 获取键值数组(类似Java的entrySet())
	 * 
	 * @return 键值对象{key,value}的数组
	 */
	this.entrys = function () {
		var len = this.keys.length;
		var entrys = new Array(len);
		for (var i = 0; i < len; i++) {
			entrys[i] = {
				key: this.keys[i],
				value: this.data[i]
			};
		}
		return entrys;
	};

	/**
	 * 判断Map是否为空
	 */
	this.isEmpty = function () {
		return this.keys.length == 0;
	};

	/**
	 * 获取键值对数量
	 */
	this.size = function () {
		return this.keys.length;
	};

	/**
	 * 重写toString
	 */
	this.toString = function () {
		var s = "{";
		for (var i = 0; i < this.keys.length; i++ , s += ',') {
			var k = this.keys[i];
			s += k + "=" + this.data[k];
		}
		s += "}";
		return s;
	};
}

/* 全局变量，存放菜单列表 */
var channelList = new Map();

function toMail(url) {
	window.location.href = url;
}

/**
 * 点击子菜单
 * 
 * @param id
 */
function loadChannel(id, template) {
	$.ajax({
		type: "GET",
		contentType: "text/plain;charset=utf-8",
		url: "admin/channel/getOne.do?cid=" + id,
		dataType: "json",
		success: function (data) {
			if (data.code == 200) {
				window.location.href = window.location.protocol + "//"
					+ window.location.host + "/" + template;// 你可以跟换里面的网
			} else if (data.code == 500) {
				alert('数据加载异常：' + JSON.stringify(data.error));
			}
		}
	});
}

/**
 * 切换菜单 （loadArticle）
 * 
 * @param id
 */
function getNewsDetail(id, cnid, template) {

	/* 加载内容 */
	$.ajax({
		type: "GET",
		contentType: "text/plain;charset=utf-8",
		url: "admin/article/getOne.do?cid=" + cnid + "&artid=" + id,
		dataType: "json",
		success: function (data) {
			if (data.code == 200) {
				window.location.href = window.location.protocol + "//"
					+ window.location.host + "/" + template;// 你可以跟换里面的网
			} else if (data.code == 500) {
				alert('数据加载异常：' + JSON.stringify(data.error));
			}
		}
	});

}

/**
 * 登陆 login	：支持 用户名、手机
 */
function login() {
	var phone = $("#phone").val();
	var password = $("#password").val();
	var template = "index.html";

	if (location.search.startsWith("?")) {
		if (location.search.split("?").length > 2) {
			template = location.search.split("?")[1] + "?" + location.search.split("?")[2];
		}
		else {
			template = location.search.split("?")[1];
		}
	}

	if (!phone) {
		alert("请输入账号"); return;
	}
	if (!password) {
		alert("请输入密码"); return;
	}
	var querier = new Req.Querier({
		params: { resource: "signup/" + phone + "/" + password + "/login" },
		async: false,
		success: function (data) {
			if (data.code === 200) {
				if (location.search === "") {
					window.location.href = document.referrer;
				}
				else if (template === "tsb") {
					template = "signin.html?tsb";
				}
				else if (template.startsWith("http")) {
					window.open(template);
					window.location.href = document.referrer;
				}
				else {
					window.location.href = window.location.protocol + "//" + window.location.host + "/" + template;
				}
			}
			else {
				alert(JSON.stringify(data.message));

			}
		}
	});
	querier.ajax();

	if (template.indexOf("tsb") !== -1) {
		validationLogin(template);
		window.location.href = document.referrer;
	}

}

/**
 * 注销 logout
 */
function logout() {
	/*var template = "index.html";*/
	/*var referrer = document.referrer;*/

	var querier = new Req.Querier({
		params: { resource: "signup/logout" },
		success: function (data) {
			window.location.reload();
			/*if(referrer !== "")
			{
				window.location.reload();
			}*/
			/*else
			{
                window.location.href = window.location.protocol + "//" + window.location.host + "/" + template;
            }*/
		},
		error: function (e) { }
	});
	querier.ajax();
}

/**
 * 发送验证码
 */
function sendPhoneCode() {
	var phone = $("#phone").val();
	if (!phone_reg.test(phone)) {
		alert("手机号码格式不正确！");
		return;
	}
	if (sendCodeToValidate(phone)) {
		var querier = new Req.Querier({
			params: { resource: "signup/" + phone + "/sendCode" },
			success: function (data) {
				if (data.code == 200) {		//发送成功
					var count = 60;
					$('#sendCode').attr('disabled', true)
					var resend = setInterval(function () {
						$('#sendCode').val(count + "秒后可重新获取");
						if (count <= 0) {
							clearInterval(resend);
							$('#sendCode').val("获取验证码").removeAttr('disabled style');
						}
						count--;
					}, 1000);
				} else {
					alert('发送验证码异常：' + JSON.stringify(data.message));
					return;
				}
			}
		});
		querier.ajax();
	}
}

/**
 * 发送验证码 数据后台校验
 * @param phone	后台校验手机号
 * @returns {Boolean}
 */
function sendCodeToValidate(phone) {
	var result = false;
	var querier = new Req.Querier({
		async: false,
		params: { resource: "signup/" + phone + "/validatePhone" },
		success: function (data) {
			if (data.code == 404) {	//校验通过，获取验证码
				result = true;
			} else if (data.code == 200) {
				alert('手机号已存在');
				result = false;
			}
		}
	});
	querier.ajax();
	return result;
}

/**
 * 注册 register
 */
function register() {
	var phone = $("#phone").val();
	var password = $("#password").val();
	var phone_code = $("#phone_code").val();
	if (!phone_reg.test(phone)) {
		alert("手机号码格式不正确！");
		return;
	}
	if (!phone_code) {
		alert("请输入验证码！");
		return;
	}
	if (!pwd_reg.test(password)) {
		alert("请输入6到22位非特殊字符密码！");
		return;
	}
	var params = {
		"resource": "signup/newRegist",
		"fields": {
			"phone": phone,
			"phoneCode": phone_code,
			"userPassword": password,
			"userRePassword": password
		}
	};
	var inserter = new Req.Inserter({
		params: params,
		success: function (data) {
			if (data.code == 200) {
				alert("注册成功!");
				login();
			} else {
				alert('注册失败：' + JSON.stringify(data.message));
				return;
			}
		}
	});
	inserter.ajax();
}

/**
 * 切换菜单 （loadArticle）
 * 
 * @param id
 */
function getJobs(id) {
	/* 加载内容 */
	$.ajax({
		type: "GET",
		contentType: "text/plain;charset=utf-8",
		url: "admin/article/getChilds.do?cid=" + id,
		dataType: "json",
		success: function (data) {
			if (data.code == 200) {
				var articles = data.articles;
				var htmlStr = "";
				if (articles.length > 0) {
					for (var i = 0; i < articles.length; i++) {
						htmlStr += "<h3 class=\"title\">" + articles[i].title
							+ "</h3><p>" + articles[i].content + "</p>";
					}
				} else {
					htmlStr = "<h3 class=\"title\"></h3><p>暂无内容</p>";
				}
				$('#jobs').html(htmlStr);
			} else if (data.code == 500) {
				alert('数据加载异常：' + JSON.stringify(data.error));
			}
		}
	});

}

/**
 * 加载子菜单
 * 
 * @param id
 */
function loadMenu(pid) {

	if (channelList == null || typeof (channelList) == "undefined"
		|| channelList.get("" + pid) == "undefined"
		|| channelList.get("" + pid) == null
		|| channelList.get("" + pid) == false || !channelList) {
		// 初始化
		if (channelList == null) {
			channelList = new Map();
		}
		// 请求后台加载菜单
		$.ajax({
			type: "GET",
			contentType: "text/plain;charset=utf-8",
			url: "admin/channel/listMenu.do?pid=" + pid + "&type=channel",
			dataType: "json",
			success: function (data) {
				var channels = data.list;
				var htmlStr = '';
				/* 加载菜单 */
				if ((channels != null || channels != '')
					&& channels.length > 0) {
					htmlStr = "<ul class='dropdown-menu'>";
					for (var i = 0; i < channels.length; i++) {
						htmlStr += "<li><a href='javascript:void(0)' "
							+ "onclick=\"loadChannel("
							+ channels[i].id + ",'"
							+ channels[i].template
							+ "');return false;\">"
							+ channels[i].name + "</a></li>";
					}
					htmlStr += '</ul>';
				}
				$('#menu_' + pid).append(htmlStr);
				channelList.put("" + pid, htmlStr);
			}
		});
	} else {
		$('#menu_' + pid).addClass("sfHover");
	}
}

/**
 * 加载子菜单
 * 
 * @param id
 */
function listCategory(id, isParent) {
	if (id == '' || id == null || typeof (id) == "undefined") {
		id = 0;
	}

	$.ajax({
		type: "GET",
		contentType: "text/plain;charset=utf-8",
		url: "admin/category/listCategory.do?pid=" + id,
		dataType: "json",
		success: function (data) {
			var models = data.category;
			var htmlStr = '';
			/* 加载父类 */
			if ((models != null || models != '') && models.length > 0
				&& isParent == 1) {
				for (var i = 0; i < models.length; i++) {
					htmlStr += "<div class='panel panel-default'> "
						+ "<div class='panel-heading'>"
						+ "<a data-toggle='collapse' data-parent='#help-nav' "
						+ " href='#help-nav-" + models[i].id + "' "
						+ " onclick='listCategory(" + models[i].id
						+ "," + 0 + ")'" + " class='collapsed'>"
						+ models[i].name + "</a>" + "</div>"
						+ "<div id='help-nav-" + models[i].id
						+ "' class='panel-collapse collapse '>"
						+ "<div class='panel-body' id='child-nav-"
						+ models[i].id + "'>" + "</div>" + "</div>"
						+ "</div>";
				}
				$('#help-nav').html(htmlStr);
			}

			/* 加载子类 */
			if ((models != null || models != '') && models.length > 0
				&& isParent == 0) {
				htmlStr = "<ul>";
				for (var i = 0; i < models.length; i++) {
					htmlStr += "<li><a href='#'>" + models[i].name
						+ "</a></li>";
				}
				htmlStr += "</ul>";
				$('#child-nav-' + id).html(htmlStr);
			}

		}
	});
}

/**
 * 留言
 */
function submitGuestbook() {

	var content = $("#text").val();
	var name = $("#name").val();
	var mail = $("#email").val();
	var phone = $("#tel").val();

	if (content == null || content == '') {
		return;
	}

	var data = {
		"nickname": name,
		"content": content,
		"email": mail,
		"phone": phone
	};

	$.ajax({
		type: "POST",
		contentType: "application/json",
		url: "admin/guestbook/record.do",
		data: JSON.stringify(data),
		dataType: "json",
		success: function (data) {
			if (data.code == 200) {
				$("#text").val('');
				$("#name").val('');
				$("#email").val('');
				$("#tel").val('');
				alert('提交成功！感谢您的留言！');
			} else if (data.code == 500) {
				alert('提交留言失败！');
			}
		}
	});
}

/**
 * 上一页
 */
function nextPage(cid) {
	var currentPageNo = parseInt($("#pageIndex").val()) + 1;
	queryList(cid, currentPageNo);
}

/**
 * 下一页
 */
function prePage(cid) {
	var currentPageNo = parseInt($("#pageIndex").val()) - 1 <= 0 ? 1 : parseInt($("#pageIndex").val()) - 1;
	queryList(cid, currentPageNo);
}



/**
 * 查询文章列表
 */
function queryList(cid, currentPageNo) {
	var pageSize = 6;

	var data = {
		currentPageNo: currentPageNo,
		pageSize: pageSize,
		cid: cid
	};

	$.ajax({
		url: "admin/article/findByPage.do",
		data: data,
		type: "get",
		dataType: "json",
		success: function (retData) {
			var result = retData.articles;
			var htmlStr = '';
			var currIndex = parseInt($("#pageIndex").val());
			if (result.currentPageNo == currIndex) {
				return;
			}

			if (result != null && result != '') {
				$("#picShow").html('');
				$("#pageIndex").val(result.currentPageNo);
				for (var i = 0; i < result.data.length; i++) {
					htmlStr = "<div class=\"col-sm-4 col-md-2 col-xs-4 item animate_afc d2 animate_start\">"
						+ "<div class=\"portfolio-item\">"
						+ "<img src=\"" + result.data[i].icon + "\" alt=\" \" />"
						+ "<div class=\"clearfix\"></div>"
						+ "</div>"
						+ "</div>";
					$("#picShow").append(htmlStr);
				}
			}
		}

	});
}

/**
 * 获取cookie
 * @param name
 * @returns
 */
function getCookie(c_name) {
	if (document.cookie.length > 0) {
		c_start = document.cookie.indexOf(c_name + "=")
		if (c_start != -1) {
			c_start = c_start + c_name.length + 1
			c_end = document.cookie.indexOf(";", c_start)
			if (c_end == -1) c_end = document.cookie.length
			return unescape(document.cookie.substring(c_start, c_end))
		}
	}
	return ""
}

/**
 * 存放cookie
 * expireseconds 过期秒数
 */
function setCookie(c_name, value, expireseconds) {
	var exdate = new Date()
	exdate.setDate(exdate.getTime() + expireseconds)
	document.cookie = c_name + "=" + escape(value) + ((expireseconds == null) ? "" : ";expires=" + exdate.toGMTString())
}


/**
 * 会话服务 start
 */

function getContextPath() {
	var pathname = document.location.pathname;
	var index = pathname.substr(1).indexOf("/");
	var result = pathname.substr(0, index + 1);
	return result;
}

function getBasePath() {
	var curr_path = document.location.href;
	var index = curr_path.indexOf("/#");
	var result = curr_path.substr(0, index + 1);
	return result;
}

Req = {
	version: 0.1,
	//requestPath : getContextPath()+"/ajax"
	requestPath: "/ajax"
};

Req.apply = function (o, c, defaults) {
	if (defaults) {
		Req.apply(o, defaults);
	}
	if (o && c && typeof c == 'object') {
		for (var p in c) {
			o[p] = c[p];
		}
	}
	return o;
};

Req.Pager = function (config) {
	Req.apply(this, config, {
		page_size: 10,
		page_index: 1,
		total_count: 0
	});
	this.setPageSize = function (size) {
		this.page_size = size;
	};
	this.setPageIndex = function (index) {
		this.page_index = index;
	};
	this.setTotalCount = function (total_count) {
		this.total_count = total_count;
	};
}

Req.Querier = function (config) {
	Req.apply(this, config, {
		resource: '',
		page_size: 10,
		page_index: 1,
		module: window.location.hash,
		order: "",
		action: "",
		params: {},
		async: true,
		success: callbackSuccess,
		error: callbackError
	});
	this.setParams = function (params) {
		this.params = params;
	};
	this.ajax = function () {
		if (!this.params || this.params.length == 0) {
			Req.ErrorHandle('params is null');
			return;
		}
		if (!this.params.resource || this.params.resource.length == 0) {
			Req.ErrorHandle('resource is null');
			return;
		}
		return $.ajax({
			type: 'post',
			dataType: 'json',
			contentType: "application/x-www-form-urlencoded; charset=UTF-8",
			url: Req.requestPath,
			async: this.async,
			data: encodeURI("type=get&param=" + JSON.stringify(this.params).replace(new RegExp(/(&)/g), "%26")),
			success: Req.PreSuccess,
			error: this.error,
			successCallback: this.success//,
			//beforeSend:beforeSendfun
		});//.always(ajaxEndfun);
	}
}

Req.Updater = function (config) {
	Req.apply(this, config, {
		resource: '',
		params: {},
		async: true,
		module: window.location.hash,
		fields: {},
		datalist: [],
		array: [],
		id: '',
		success: callbackSuccess,
		error: callbackError
	});
	this.setParams = function (params) {
		this.params = params;
	};
	this.setFields = function (fields) {
		this.fields = fields;
	};
	this.ajax = function () {
		if (!this.params || this.params.length == 0) {
			Req.ErrorHandle('params is null');
			return;
		}
		if (!this.params.resource || this.params.resource.length == 0) {
			Req.ErrorHandle('resource is null');
			return;
		}
		return $.ajax({
			type: 'post',
			dataType: 'json',
			contentType: "application/x-www-form-urlencoded; charset=UTF-8",
			url: Req.requestPath,
			async: this.async,
			data: encodeURI("type=put&param=" + JSON.stringify(this.params).replace(new RegExp(/(&)/g), "%26")),
			success: Req.PreSuccess,
			error: this.error,
			successCallback: this.success//,
			//beforeSend:beforeSendfun
		});//.always(ajaxEndfun);
	}
}

Req.Inserter = function (config) {
	Req.apply(this, config, {
		resource: '',
		fields: {},
		async: true,
		module: window.location.hash,
		datalist: [],
		array: [],
		success: callbackSuccess,
		error: callbackError
	});
	this.setFields = function (fields) {
		this.fields = fields;
	};
	this.ajax = function () {
		if (!this.params || this.params.length == 0) {
			Req.ErrorHandle('params is null');
			return;
		}
		if (!this.params.resource || this.params.resource.length == 0) {
			Req.ErrorHandle('resource is null');
			return;
		}
		return $.ajax({
			type: 'post',
			dataType: 'json',
			contentType: "application/x-www-form-urlencoded; charset=UTF-8",
			url: Req.requestPath,
			async: this.async,
			data: encodeURI("type=post&param=" + JSON.stringify(this.params).replace(new RegExp(/(&)/g), "%26")),
			success: Req.PreSuccess,
			error: this.error,
			successCallback: this.success//,
			//beforeSend:beforeSendfun
		});//.always(ajaxEndfun);
	}
}

Req.Deleter = function (config) {
	Req.apply(this, config, {
		resource: '',
		params: {},
		async: true,
		module: window.location.hash,
		datalist: [],
		id: '',
		success: callbackSuccess,
		error: callbackError
	});
	this.setParams = function (params) {
		this.params = params;
	};
	this.ajax = function () {
		if (!this.params || this.params.length == 0) {
			Req.ErrorHandle('params is null');
			return;
		}
		if (!this.params.resource || this.params.resource.length == 0) {
			Req.ErrorHandle('resource is null');
			return;
		}
		return $.ajax({
			type: 'post',
			dataType: 'json',
			contentType: "application/x-www-form-urlencoded; charset=UTF-8",
			url: Req.requestPath,
			async: this.async,
			data: encodeURI("resource=" + this.resource + "&type=delete&param=" + JSON.stringify(this)),
			success: Req.PreSuccess,
			error: this.error,
			successCallback: this.success//,
			//beforeSend:beforeSendfun
		});//.always(ajaxEndfun);
	}
}

Req.Action = function (config) {
	Req.apply(this, config, {
		resource: '',
		params: {},
		async: true,
		module: window.location.hash,
		id: '',
		action: '',
		success: callbackSuccess,
		error: callbackError
	});
	this.setParams = function (params) {
		this.params = params;
	};
	this.ajax = function () {
		if (!this.params || this.params.length == 0) {
			Req.ErrorHandle('params is null');
			return;
		}
		if (!this.params.resource || this.params.resource.length == 0) {
			Req.ErrorHandle('resource is null');
			return;
		}
		return $.ajax({
			type: 'post',
			dataType: 'json',
			contentType: "application/x-www-form-urlencoded; charset=UTF-8",
			url: Req.requestPath,
			async: this.async,
			data: encodeURI("resource=" + this.resource + "&type=action&param=" + JSON.stringify(this)),
			success: Req.PreSuccess,
			error: this.error,
			successCallback: this.success
		});
	}
}

Req.ErrorHandle = function (type) {
	if (type == 'params is null') {
		alert("params can't be null");
	}
	if (type == 'resource is null') {
		alert("resource can't be null");
	}
}

Req.PreSuccess = function (data) {
	/*if(data.code>999){
		
		if(data.code==3003){
			baseJS._dialogBox({title:_rb.common.error.title,
				content:_rb.cloud.error[data.code],
				IsComplieformRb:false,
				callbackType:"error",
				countdown:false});
			this.error(data);
		}else{
			baseJS._dialogBox({title:_rb.common.error.title,
				content:_rb.cloud.error[data.code],
				IsComplieformRb:false,
				callbackType:"error"});
			
			this.error(data);
		}
	}*/
	if (data.code == 419) {		//session失效
		alert("未登录!");
		window.location.href = getContextPath() + "/index.html";
	}
	else
		this.successCallback(data);
}
// /////////////////////////////////////////////////////////////////////


function callbackError(XMLHttpRequest, textStatus, errorThrown, config) {
	alert("callbackerror!");
	/*if(window.location.hash.split("#/").length > 1) {
		if(window.location.hash.split("#/")[1].indexOf("app") == 0) {
			baseJS._processError(XMLHttpRequest, textStatus, errorThrown);
		}
	}*/
}

function callbackSuccess(msg) {
	//alert("suc");
}

/**
 * 会话服务 end
 */
