<?php

include "config.php";
$response=array();

$p_email=$_POST['p_email'];
$p_name=$_POST['p_name'];
$d_email=$_POST['d_email'];
$d_name=$_POST['d_name'];


$sql = "INSERT INTO `patient_appointment`(`p_name`, `p_email`, `appoint_status`, `d_name` , `d_email` ) 
VALUES ('$p_name' , '$p_email', 'Pending' , '$d_name', '$d_email'  )";

if ($con->query($sql)) 
{

    $sql = "INSERT INTO `doctor_appointment`(`d_name`, `d_email`, `appoint_status`, `p_name` , `p_email` ) 
    VALUES ('$d_name' , '$d_email', 'Pending' , '$p_name', '$p_email'  )";

    if ($con->query($sql)) 
    {
        echo"successful";
    }
    else 
    {
      echo "Error updating ".$con->error;
    }
}
else 
{
  echo "Error updating ".$con->error;
}


  echo "hello Successfully";
?>