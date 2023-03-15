<?php
include "config.php";
$response=array();

$email=$_POST['p_email'];



$sql = "SELECT `date_log`, AVG(`HeartBeat_daily`) as HRB
FROM `heartbeat` 
WHERE `Demail` = '$email'
GROUP BY `date_log`";
$result = $con->query($sql);

if ($result->num_rows > 0) 
{
  while($row = $result->fetch_assoc()) 
  {

     $temp=array();
      $temp['date']=$row["date_log"];
      $temp['HR']=$row["HRB"];
      array_push($response,$temp);
    
  }

  echo json_encode($response);
}
else
{
    echo"No entry";
}

?>
