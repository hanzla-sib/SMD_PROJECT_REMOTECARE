<?php

include "config.php";
$response=array();

$email=$_POST['email'];
$Accepted = "Accepted";

$sql = "SELECT * FROM `patient_appointment` where `p_email`= '$email' and `appoint_status` = '$Accepted' ";
$result = $con->query($sql);


  if ($result->num_rows > 0)
   {
        while($row = $result->fetch_assoc()) 
        {

        $temp=array();
        $temp['d_email']=$row["d_email"];
        $temp['d_name']=$row["d_name"];
        array_push($response,$temp);
        }
  } 
  else 
  {
    echo "user not found";
  }
  echo json_encode($response);

  // echo "hello Successfully";
?>