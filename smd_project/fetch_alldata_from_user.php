<?php

include "config.php";
$response=array();

  $email=$_POST['email'];
  
// echo $email;
 $userimage=array();
  
$sql = "SELECT * FROM `user` where `email`= '$email'";
$result = $con->query($sql);
  if ($result->num_rows > 0) {
    // output data of each row
    while($row = $result->fetch_assoc()) {
      $temp=array();
      $temp['imageurl']=$row["imageurl"];
      $temp['user_type']=$row["user_type"];
      $temp['gender']=$row["gender"];
      $temp['name']=$row["name"];
      array_push($userimage,$temp);
    }
    echo json_encode($userimage);
      // echo $row["imageurl"]. "<br>"; 
  } else {
    echo "user not found";
  }

  // echo "hello Successfully";
?>