<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html><head>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8">	
		<title>上海远行物流供应链管理有限公司</title>		
		<link href="<%=request.getContextPath() %>/resouces/css/styles.css" type="text/css" media="screen" rel="stylesheet">
		<script type="text/javascript" src="<%=request.getContextPath() %>/resouces/js/jquery-2.1.0.min.js" charset="utf-8"></script>
        <style type="text/css">
		img, div { behavior: url(iepngfix.htc) }
		</style>
		<script type="text/javascript">
		var msg = '<%=request.getAttribute("msg")== null?"":request.getAttribute("msg") %>';
		var msg_heard = '<p class="error"><img src="<%=request.getContextPath() %>/resouces/images/error.png" height="16px" width="16px">';
		var msg_end ='</p>';
		$(document).ready(function(){
			$("#Submit").click(function(){
				 if($("#user_name").val().length == 0){
					 $("#msg").html(msg_heard +"请输入用户名！"+ msg_end);
					 $("#msg").attr("style","display:ture");
					 return false;
				 }else if($("#user_password").val().length == 0){
					 $("#msg").html(msg_heard +"请输入密码！"+ msg_end);
					 $("#msg").attr("style","display:ture");
					 return false;
				 }
				 return true;
			});
			if(msg.length > 0){
				 $("#msg").html(msg_heard +msg+ msg_end);
				 $("#msg").attr("style","display:ture");
			}
		});
		</script>
	</head>
	<body id="login">
		<div id="wrappertop"></div>
			<div id="wrapper">
					<div id="content">
						<div id="header" style="text-align:center;">
							<h1><a href=""></a></h1>
						</div>
						<div id="darkbannerwrap">
						</div>
						<form name="form1" method="post" action="<%=request.getContextPath() %>/login.action">
						<fieldset class="form">
						        <div id="msg" style="display:none">
						           
								</div>                                                             <p>
								<label for="user_name">用户名:</label>
								<input name="user_name" id="user_name" value="abc" type="text">
							</p>
							<p>
								<label for="user_password">密码:</label>
								<input name="user_password" id="user_password" type="password">
							</p>
							<button type="submit" class="positive" name="Submit" id="Submit">
								<img src="<%=request.getContextPath() %>/resouces/images/key.png" alt="Announcement">Login</button>
								<ul id="forgottenpassword">
								<li class="boldtext">|</li>
								<li><a href="http://freelancesuite.com/demo/forgot.php">Forgotten it?</a></li>
							</ul>
							</fieldset>
					</form></div>
				</div>
</body>
</html>