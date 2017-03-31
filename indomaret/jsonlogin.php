<?php
$response = array();
include 'koneksi.php';

  $result =  mysql_query("SELECT * FROM tb_user") or die(mysql_error());
    if (mysql_num_rows($result) > 0) {
      $response['users'] = array();
      while($row = mysql_fetch_array($result)){
        $h = array();
        $h['id_user'] = $row['id_user'];
        $h['nama'] = $row['nama'];
        $h['address'] = $row['address'];
        $h['no_hp'] = $row['no_hp'];
        $h['username'] = $row['username'];
        $h['password'] = $row['password'];
        array_push($response['users'], $h);
      }
      $response['success'] = 1;
      $json_string = json_encode($response, JSON_PRETTY_PRINT);
      echo "<pre>" .$json_string. "</pre>";
    }else{
      $response['success'] = 0;
      $response['message'] = "No user found";
      echo json_encode($response);
    }
 ?>
