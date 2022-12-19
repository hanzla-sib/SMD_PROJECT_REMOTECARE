<?php

include "config.php";
$response=array();

$p_email=$_POST['p_email'];
$p_name=$_POST['p_name'];
$d_email=$_POST['d_email'];


// sql to delete a record

$sql = "DELETE FROM patient_appointment WHERE p_email = '$p_email' and d_email = '$d_email'";

if ($con->query($sql) === TRUE) 
{
    $sql = "DELETE FROM doctor_appointment WHERE p_email = '$p_email' and d_email = '$d_email'";

    if ($con->query($sql) === TRUE) 
    {
      echo "Record deleted successfully";
    }
     else 
     {
        echo "Error deleting record: " . $con->error;
     }
}
 else 
 {
    echo "Error deleting record: " . $con->error;
 }

  // echo "hello Successfully";
?>