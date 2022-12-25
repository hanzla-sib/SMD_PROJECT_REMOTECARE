<?php

include "config.php";


  $email=$_POST['email'];
  
  $userimage=array();
$sql = "SELECT * FROM `daily_steps` where `Demail`= '$email'";
$result = $con->query($sql);
  if ($result->num_rows > 0) {
    // output data of each row
    while($row = $result->fetch_assoc()) {
      $temp=array();
      $temp['steps']=$row["steps_daily"];
      array_push($userimage,$temp);
    }
    $temp['steps']=0;
    array_push($userimage,$temp);
      // echo $row["imageurl"]. "<br>";

    
    
  } else {
    $temp=array();
    $temp['steps']=0;
    array_push($userimage,$temp);
  
  }
  echo json_encode($userimage);
  // echo "hello Successfully";
?>