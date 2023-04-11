<?php
include "config.php";
$response=array();
$p_email=$_POST['p_email'];
$d_email=$_POST['d_email'];

$recom=0;

$sql1 = "SELECT * FROM `doctor_recommendation` where `d_email`= '$d_email' and `p_email`='$p_email' ";
$result1 = $con->query($sql1);

if ($result1->num_rows > 0) 
{
  while($row1 = $result1->fetch_assoc()) 
  {
    $recom=$row1["steps_recommended"];
   
    $sql = "SELECT * FROM `weekly_steps` where `Demail`= '$p_email' ";
    $result = $con->query($sql);

if ($result->num_rows > 0) 
{
  while($row = $result->fetch_assoc()) 
  {
     
     $temp=array();
      $temp['date']=$row["date_log"];
      $temp['steps']=$row["steps_daily"];
      $temp['recom_steps']=$recom;
      array_push($response,$temp);
    
  }

  echo json_encode($response);
}
else
{
    echo"No entry";
}
  }
}
else
{
    echo"no recom steps";
}
///-----------------------


?>
