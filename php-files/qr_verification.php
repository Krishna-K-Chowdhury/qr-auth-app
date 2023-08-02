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

$scannedVal = $_GET['t1'];
$apval = 0;

$qry = "SELECT * FROM tbl_final WHERE qrx='$scannedVal' AND apx='$apval'";
$raw = mysqli_query($conn, $qry);
$count = mysqli_num_rows($raw);

$upd = "UPDATE tbl_final SET apx='1' WHERE qrx='$scannedVal'";

if ($count > 0) {
    echo "found";
    mysqli_query($conn, $upd);
} else {
    echo "Not found";
}

// Close the connection
mysqli_close($conn);

?>
