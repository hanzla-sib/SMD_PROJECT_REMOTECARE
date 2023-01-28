<?php

include "config.php";
$response=array();

$d_email=$_POST['email'];
$Accepted = "Accepted";

$sql = "SELECT * FROM `doctor_appointment` where `d_email`= '$d_email' and `appoint_status` = '$Accepted'  ";
$result = $con->query($sql);

  if ($result->num_rows > 0) 
  {
    
    while($row = $result->fetch_assoc()) 
    {
            $temp=array();
            $temp['name']=$row["p_name"];
            $temp['email']=$row["p_email"];
            $temp['status']=$row["appoint_status"];
            

            $temp['time']=$row["Time1"];
            $temp['date']=$row["Date1"];
            array_push($response,$temp);
    }      

  } 
  else 
  {
    echo "user not found";
  }

  echo json_encode($response);
?>