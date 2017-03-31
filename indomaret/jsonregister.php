<?php
$response = array();
include 'koneksi.php';

// check for required fields
if (isset($_POST['nama']) && isset($_POST['address']) && isset($_POST['no_hp']) && isset($_POST['username']) && isset($_POST['password'])) {

    $nama = $_POST['nama'];
    $address = $_POST['address'];
    $nohp = $_POST['no_hp'];
    $username = $_POST['username'];
    $password = md5($_POST['password']);

    // mysql inserting a new row
    $result = mysql_query("INSERT INTO tb_user(nama, address, no_hp, username, password) VALUES('$nama', '$address', '$nohp', '$username', '$password')");

    // check if row inserted or not
    if ($result) {
        // successfully inserted into database
        $response["success"] = 1;
        $response["message"] = "User successfully created.";

        // echoing JSON response
        echo json_encode($response, JSON_PRETTY_PRINT);
    } else {
        // failed to insert row
        $response["success"] = 0;
        $response["message"] = "Oops! An error occurred.";

        // echoing JSON response
        echo json_encode($response);
    }
} else {
    // required field is missing
    $response["success"] = 0;
    $response["message"] = "Required field(s) is missing";

    // echoing JSON response
    echo json_encode($response);
}
?>
