<?php
include "config.php";
$response=array();

$p_email=$_POST['p_email'];
$steps=$_POST['steps'];
$d_email=$_POST['d_email'];
 
$sql = "INSERT INTO `doctor_recommendation`( `p_email`, `steps_recommended`, `d_email`) VALUES ('$p_email','$steps','$d_email')";
$result = $con->query($sql);
if($result){
    echo "Successfully inserted into doctor recommended table";
}
else{
    echo "error inserting into doctor recommended table";
}
?>
