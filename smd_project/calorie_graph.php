<?php
include "config.php";
$response=array();

$email=$_POST['p_email'];

// $date=date("Y-m-d");
 

// where `p_email`= '$email'


$sql = "SELECT * FROM `consumed_calories` where `p_email`= '$email' ";
$result = $con->query($sql);

if ($result->num_rows > 0) 
{
  while($row = $result->fetch_assoc()) 
  {

     $temp=array();
      $temp['date']=$row["date_log"];
      $temp['calorie']=$row["Calories"];
      array_push($response,$temp);
    
  }

  echo json_encode($response);
}
else
{
    echo"No entry";
}

?>
