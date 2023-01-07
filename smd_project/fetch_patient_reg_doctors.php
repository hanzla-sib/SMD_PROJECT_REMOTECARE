<?php
include "config.php";
$response=array();

$email=$_POST['email'];

$sql = "SELECT * FROM `doctor_appointment` where `d_email`= '$email' and `appoint_status`='Accepted' ";
$result = $con->query($sql);

if ($result->num_rows > 0) 
{
  while($row = $result->fetch_assoc()) 
  {

      $temp=array();
      $temp['p_email']=$row["p_email"];
      $temp['burnt_cal']=$row["appoint_status"];
      array_push($response,$temp);
    
  }

  echo json_encode($response);
}
else
{
    echo"No entry";
}

?>
