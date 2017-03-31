<?php
include 'koneksi.php';
if (isset($_GET["id"])) {
    $id = $_GET['id'];

    // get a product from products table
    $result = mysql_query("SELECT * FROM tb_login WHERE id = $id");
    if (!empty($result)) {
        // check for empty result
        if (mysql_num_rows($result) > 0) {

            $result = mysql_fetch_array($result);

            $h = array();
            $h["id"] = $result["id"];
            $h["username"] = $result["username"];
            $h["password"] = $result["password"];
            $h["id_roles"] = $result["id_roles"];
            // success
            $response["success"] = 1;

            // user node
            $response["user"] = array();

            array_push($response["user"], $h);

            // echoing JSON response
            echo json_encode($response, JSON_PRETTY_PRINT);
        } else {
            // no product found
            $response["success"] = 0;
            $response["message"] = "No product found";

            // echo no users JSON
            echo json_encode($response);
        }
    } else {
        // no product found
        $response["success"] = 0;
        $response["message"] = "No product found";

        // echo no users JSON
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
