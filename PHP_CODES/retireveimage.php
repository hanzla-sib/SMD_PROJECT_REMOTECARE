<?php

include "config.php";
$response=array();

  $email=$_POST['email'];
  
// echo $email;
 $userimage=array();
  
$sql = "SELECT `Temail`,`imageurl`,`details` FROM `test_record` where `Temail`= '$email'";
$result = $con->query($sql);
  if ($result->num_rows > 0) {
    // output data of each row
    while($row = $result->fetch_assoc()) {
      $temp=array();
      $temp['imageurl']=$row["imageurl"];
      $temp['email']=$row["Temail"];
      $temp['details']=$row["details"];
      array_push($userimage,$temp);
    }
      // echo $row["imageurl"]. "<br>";

    
    
  } else {
    echo "user not found";
  }
  echo json_encode($userimage);
  // echo "hello Successfully";
?>