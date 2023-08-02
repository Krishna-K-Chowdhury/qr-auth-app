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

$uname=$_GET['t1'];
$pwd=$_GET['t2'];

$qry="select * from tbl_admin where username='$uname' and password='$pwd'";

$raw=mysqli_query($conn,$qry);

$count=mysqli_num_rows($raw);

if($count>0)
 echo "found";
else
 echo "not found";

// Close the connection
mysqli_close($conn);
?>