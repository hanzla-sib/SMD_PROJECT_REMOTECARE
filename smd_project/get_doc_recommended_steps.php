<?php
include "config.php";
$response=array();

$email=$_POST['p_email'];


$sql = "SELECT * FROM `doctor_recommendation` where `p_email`= '$email'";
$result = $con->query($sql);

if ($result->num_rows > 0) {
  // output data of each row
  while($row = $result->fetch_assoc()) {
    $temp=array();
    $temp['d_email']=$row["d_email"];
    $temp['steps']=$row["steps_recommended"];
    
    array_push($response,$temp);
  }  
} else {
  echo "user not found";
}
echo json_encode($response);

?>
