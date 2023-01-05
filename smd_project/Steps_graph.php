<?php
include "config.php";
$response=array();

$email=$_POST['p_email'];




$sql = "SELECT * FROM `weekly_steps` where `Demail`= '$email' ";
$result = $con->query($sql);

if ($result->num_rows > 0) 
{
  while($row = $result->fetch_assoc()) 
  {

     $temp=array();
      $temp['date']=$row["date_log"];
      $temp['steps']=$row["steps_daily"];
      array_push($response,$temp);
    
  }

  echo json_encode($response);
}
else
{
    echo"No entry";
}

?>
