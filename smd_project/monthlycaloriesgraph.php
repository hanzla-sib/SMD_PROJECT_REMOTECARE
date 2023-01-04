 <?php
include "config.php";
$response=array();

$email=$_POST['p_email'];

// $date=date("Y-m-d");
 

// where `p_email`= '$email'

$sql = "SELECT extract(MONTH from `date_log`) as month,sum(`Calories`) as totalcalories from consumed_calories where `p_email`= '$email'
 group by month; ";
$result = $con->query($sql);

if ($result->num_rows > 0) 
{
  while($row = $result->fetch_assoc()) 
  {

     $temp=array();
      $temp['month']=$row["month"];
      $temp['caloriesum']=$row["totalcalories"];
      array_push($response,$temp);
   
  }

  echo json_encode($response);

}
else
{
    echo"No entry";
}


?>
