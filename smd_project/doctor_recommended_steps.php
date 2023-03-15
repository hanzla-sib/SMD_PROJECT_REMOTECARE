<?php
include "config.php";
$response=array();

$p_email=$_POST['p_email'];
$steps=$_POST['steps'];
$d_email=$_POST['d_email'];
 

$sql = "SELECT * FROM `doctor_recommendation` where `d_email`= '$d_email' and `p_email`='$p_email'";
$result = $con->query($sql);

if ($result->num_rows > 0) 
{

    $query="UPDATE `doctor_recommendation` SET `steps_recommended`='$steps' where `d_email`='$d_email' and `p_email`='$p_email'";
    $res=mysqli_query($con,$query);
    if($res){
        echo "Steps updated ";
    }
    else{
        echo "Steps not updated ";	
    }

    
}
else
{
    
$sql = "INSERT INTO `doctor_recommendation`( `p_email`, `steps_recommended`, `d_email`) VALUES ('$p_email','$steps','$d_email')";
$result = $con->query($sql);
if($result){
    echo "Successfully inserted into doctor recommended table";
}
else{
    echo "error inserting into doctor recommended table";
}
}



?>
