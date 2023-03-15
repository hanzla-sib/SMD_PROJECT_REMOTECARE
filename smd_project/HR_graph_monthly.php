<?php
include "config.php";
$response=array();

$email=$_POST['p_email'];


$sql = "SELECT extract(MONTH from `date_log`) as month,AVG(`HeartBeat_daily`) as HR from heartbeat where `Demail`= '$email'
 group by month; ";
$result = $con->query($sql);

if ($result->num_rows > 0) 
{
  while($row = $result->fetch_assoc()) 
  {

     $temp=array();
      $temp['month']=$row["month"];
      $temp['totalStepssum']=$row["HR"];
      array_push($response,$temp);
   
  }

  echo json_encode($response);

}
else
{
    echo"No entry";
}


?>
