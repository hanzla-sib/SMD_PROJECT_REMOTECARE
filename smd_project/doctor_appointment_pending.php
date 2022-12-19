<?php

include "config.php";
$response=array();

$d_email=$_POST['email'];
$Pending = "Pending";

$sql = "SELECT * FROM `doctor_appointment` where `d_email`= '$d_email' and `appoint_status` = '$Pending'  ";
$result = $con->query($sql);

  if ($result->num_rows > 0) 
  {
    
    while($row = $result->fetch_assoc()) 
    {
            $temp=array();
            $temp['name']=$row["p_name"];
            $temp['email']=$row["p_email"];
            $temp['status']=$row["appoint_status"];
            array_push($response,$temp);
    }      

  } 
  else 
  {
    echo "user not found";
  }

  echo json_encode($response);
?>