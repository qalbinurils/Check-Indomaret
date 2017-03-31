<?php
//Dipakai jika ingin memanggil default url
$link_server = "http://$_SERVER[HTTP_HOST]";
//=======================================
$server="localhost";
$username = "root";
$password = "";
$database = "db_indomaret";
$connect_db = mysql_connect($server, $username, $password);
$find_db = mysql_select_db($database);

if ($find_db) {

 //echo "Database  Ada";

}else {

 //echo "Database Tidak Ada";

}
?>
