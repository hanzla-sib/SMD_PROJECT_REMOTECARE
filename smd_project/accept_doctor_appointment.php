<?php

include "config.php";
$response=array();

$p_email=$_POST['p_email'];
$p_name=$_POST['p_name'];
$d_email=$_POST['d_email'];


$sql = "UPDATE doctor_appointment SET appoint_status= 'Accepted' 
        WHERE p_email='$p_email' and d_email='$d_email' ";

      if ($con->query($sql) === TRUE) 
      {
       
        $sql = "UPDATE patient_appointment SET appoint_status= 'Accepted' 
        WHERE p_email='$p_email' and d_email='$d_email' ";
         if ($con->query($sql) === TRUE) 
         {
          echo "succsss both tables";
         } 
         else 
         {
           echo "Error updating both: " . $con->error;
         }
      } 
      else 
      {
        echo "Error updating one: " . $con->error;
      }






  // echo "hello Successfully";
?>