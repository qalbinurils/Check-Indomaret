<!DOCTYPE html>
<?php
session_start();
include 'koneksi.php';

if (isset($_POST['btn-login'])) {

$username = $_POST['username'];
$password = $_POST['password'];

$query = "SELECT username, password FROM tb_login WHERE username='$username'";
$result = mysql_query($query);

if (mysql_num_rows($result)) {
	echo "<script>window.open('dashboard-admin.php','_self')</script>";
				 $_SESSION['username']=$username;
	 } else {
		 echo "<script>alert('username or password is incorrect!')</script>";
	 }
	 mysqli_close($conn);
}
?>
<html >
<head>
  <title>Login</title>
  <link rel='stylesheet prefetch' href='http://fonts.googleapis.com/css?family=Open+Sans:600'>
  <link rel="stylesheet" href="css/style.css">
</head>

<body>
  <div class="login-wrap">
	<div class="login-html">
		<input id="tab-1" type="radio" name="tab" class="sign-in" checked><label for="tab-1" class="tab">Sign In</label>
		<input id="tab-2" type="radio" name="tab" class="sign-up"><label for="tab-2" class="tab">Sign Up</label>
		<form class="login-form" method="post">
			<div class="sign-in-htm">
				<div class="group">
					<label for="user" class="label">Username</label>
					<input id="user" type="text" class="input" name="username" required>
				</div>
				<div class="group">
					<label for="pass" class="label">Password</label>
					<input id="pass" type="password" class="input" name="password" data-type="password" required>
				</div>
      </br>
				<div class="group">
					<input type="submit" name="btn-login" class="button" value="Sign In">
				</div>
				<div class="hr"></div>
				<div class="foot-lnk">
					<a href="#forgot">Forgot Password?</a>
				</div>
			</div>
			<div class="sign-up-htm">
				<div class="group">
					<label for="user" class="label">Username</label>
					<input id="user" type="text" class="input">
				</div>
				<div class="group">
					<label for="pass" class="label">Password</label>
					<input id="pass" type="password" class="input" data-type="password">
				</div>
				<div class="group">
					<label for="pass" class="label">Repeat Password</label>
					<input id="pass" type="password" class="input" data-type="password">
				</div>
				<div class="group">
					<label for="pass" class="label">Email Address</label>
					<input id="pass" type="text" class="input">
				</div>
				<div class="group">
					<input type="submit" class="button" value="Sign Up">
				</div>
				<div class="hr"></div>
				<div class="foot-lnk">
					<label for="tab-1">Already Member?</a>
				</div>
			</div>
		</form>
	</div>
</div>
</body>
</html>
