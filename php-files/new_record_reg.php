<?php

// Establish the database connection
$servername = "your_servername";
$username = "your_username";
$password = "your_password";
$dbname = "your_dbname";

$conn = mysqli_connect($servername, $username, $password, $dbname);

// Check the connection
if (!$conn) {
    die("Connection failed: " . mysqli_connect_error());
}

$name=trim($_POST['name']);
$roll=trim($_POST['roll']);
$year=trim($_POST['year']);
$dept=trim($_POST['dept']);
$email=trim($_POST['email']);
$transacinfo=trim($_POST['transacinfo']);
$payreceiver=trim($_POST['payreceiver']);

$qry1="select * from tbl_latepay where roll='$roll'";
$raw=mysqli_query($conn,$qry1);
$count=mysqli_num_rows($raw);

if($count>0){
    $response="exist";
}else{
    $qry2="INSERT INTO `tbl_latepay` (`id`, `name`, `roll`, `year`, `dept`, `email`, `transacinfo`, `payreceiver`) values (NULL, '$name', '$roll', '$year', '$dept', '$email', '$transacinfo', '$payreceiver')";
    $res=mysqli_query($conn,$qry2);
    if ($res==true){
        $response="inserted";
    }else{
        $response="failed";
    }
}
echo $response;


// Close the connection
mysqli_close($conn);

?>