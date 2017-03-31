<!DOCTYPE html>
<?php
session_start();


if(!$_SESSION['username'])
{
    header("Location: login.php");
}

?>
<html lang="en">
<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Check Indomaret</title>

    <!-- Bootstrap Core CSS -->
    <link href="css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom CSS -->
    <link href="css/sb-admin.css" rel="stylesheet">

    <!-- Custom Fonts -->
    <link href="font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">

    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->

</head>

<body>

    <div id="wrapper">

        <!-- Navigation -->
        <nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
            <!-- Brand and toggle get grouped for better mobile display -->
            <div class="navbar-header">
                <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-ex1-collapse">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand" href="dashboard-admin.php">
                  <?php echo $_SESSION['username'];?>
                </a>
            </div>
            <!-- Top Menu Items -->
            <ul class="nav navbar-right top-nav">
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown"><i class="fa fa-user"></i> <?php echo $_SESSION['username'];?> <b class="caret"></b></a>
                    <ul class="dropdown-menu">
                        <li>
                            <a href="#"><i class="fa fa-fw fa-user"></i> Profile</a>
                        </li>
                        <li>
                            <a href="#"><i class="fa fa-fw fa-gear"></i> Settings</a>
                        </li>
                        <li class="divider"></li>
                        <li>
                            <a href="logout.php"><i class="fa fa-fw fa-power-off"></i> Log Out</a>
                        </li>
                    </ul>
                </li>
            </ul>
            <!-- Sidebar Menu Items - These collapse to the responsive navigation menu on small screens -->
            <div class="collapse navbar-collapse navbar-ex1-collapse">
                <ul class="nav navbar-nav side-nav">
                  <li>
                      <a href="dashboard-admin.php"><i class="fa fa-dashboard"></i> Dashboard </a>
                  </li>
                  <li>
                      <a href="dashboard-barang.php"><i class="fa fa-fw fa-table"></i> Dashboard Barang </a>
                  </li>
                  <li>
                      <a href="dashboard-kategori.php"><i class="fa fa-fw fa-table"></i> Dashboard Kategori </a>
                  </li>
                  <li>
                      <a href="dashboard-promo.php"><i class="fa fa-fw fa-table"></i> Dashboard Promo </a>
                  </li>
                  <li class="active">
                      <a href="dashboard-user.php"><i class="fa fa-fw fa-table"></i> Dashboard User </a>
                  </li>
                  <li>
                      <a href="dashboard-teknisi.php"><i class="fa fa-fw fa-table"></i> Dashboard Teknisi </a>
                  </li>
                </ul>
            </div>
            <!-- /.navbar-collapse -->
        </nav>

        <div id="page-wrapper">

            <div class="container-fluid">

                <!-- Page Heading -->
                <div class="row">
                    <div class="col-lg-12">
                        <h1 class="page-header">
                            Dashboard User
                        </h1>
                        <ol class="breadcrumb">
                            <li>
                                <i class="fa fa-dashboard"></i>  <a href="dashboard-admin.php">Dashboard</a>
                            </li>
                            <li class="active">
                                <i class="fa fa-table"></i> User
                            </li>
                        </ol>
                    </div>
                </div>
                <!-- /.row -->

                <div class="row">
                    <div class="col-lg-6">
                        <h2>Data User</h2>
                        <div>
                          <?php
                          include 'koneksi.php';
                          $query = "SELECT nama, address, no_hp, username, md5(password) FROM tb_user";
                          $result = mysql_query($query);
                          if($result){
                           ?>
                            <table class="table table-bordered table-hover">
                                <thead>
                                    <tr>
                                        <th>Nama</th>
                                        <th>Address</th>
                                        <th>Mobile</th>
                                        <th>Username</th>
                                        <th>Password</th>
                                        <th></th>
                                        <th></th>
                                    </tr>
                                </thead>
                                <?php
                                  while($row = mysql_fetch_row($result)){
                                ?>
                                <tbody>
                                    <tr>
                                      <?php
                                          $nama = $row[0];
                                          $address = $row[1];
                                          $nohp = $row[2];
                                          $user = $row[3];
                                          $pass = $row[4];
                                      ?>
                                        <td><?php echo $nama; ?></td>
                                        <td><?php echo $address; ?></td>
                                        <td><?php echo $nohp; ?></td>
                                        <td><?php echo $user; ?></td>
                                        <td><?php echo $pass; ?></td>
                                        <td><a class='button' type='vertical-align:middle'>Edit</a></td>
                                        <td><a class='button' type='vertical-align:middle'>Delete</a></td>
                                    </tr>
                                    <?php
                                      }
                                    ?>
                                </tbody>
                            </table>
                            <?php
                            }
                            mysql_close();
                            ?>
                        </div>
                    </div>
                </div>
                <!-- /.row -->
              </br>

            </div>
            <!-- /.container-fluid -->

        </div>
        <!-- /#page-wrapper -->

    </div>
    <!-- /#wrapper -->

    <!-- jQuery -->
    <script src="js/jquery.js"></script>

    <!-- Bootstrap Core JavaScript -->
    <script src="js/bootstrap.min.js"></script>

</body>

</html>
