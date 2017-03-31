<?php
include "koneksi.php";

$username = $_POST["username"];
$password = md5($_POST["password"]);

$query = "SELECT * FROM tb_user WHERE username='$username' AND password='$password'";

$hasil = mysql_query($query) or die(mysql_error());
if (mysql_num_rows($hasil) > 0) {
$response = array();
$response["login"] = array();
while ($data = mysql_fetch_array($hasil))
{
  $h = array();
  $h['id_user'] = $data['id_user'];
  $h['nama'] = $data['nama'];
  $h['address'] = $data['address'];
  $h['no_hp'] = $data['no_hp'];
  $h['username'] = $data['username'];
  $h['password'] = $data['password'];

 array_push($response["login"], $h);
}
	$response["success"] = "1";
  echo json_encode($response, JSON_PRETTY_PRINT);
}
else {
    $response["success"] = "0";
    $response["message"] = "Tidak ada data";
	echo json_encode($response);
}
?>
