<?php
include "config.php";
$response=array();

$email=$_POST['p_email'];


$sql = "SELECT extract(MONTH from `date_log`) as month,sum(`Burnt_Calories`) as totalburntCalories from weekly_steps where `Demail`= '$email'
 group by month; ";
$result = $con->query($sql);

if ($result->num_rows > 0) 
{
  while($row = $result->fetch_assoc()) 
  {

     $temp=array();
      $temp['month']=$row["month"];
      $temp['totalBurntcalssum']=$row["totalburntCalories"];
      array_push($response,$temp);
   
  }

  echo json_encode($response);

}
else
{
    echo"No entry";
}


?>
