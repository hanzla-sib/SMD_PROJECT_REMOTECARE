<?php

include "config.php";
$response=array();

$email=$_POST['email'];
$Pending = "Pending";

$sql = "SELECT * FROM `patient_appointment` where `p_email`= '$email' and `appoint_status` = '$Pending' ";
$result = $con->query($sql);


  if ($result->num_rows > 0)
   {
        while($row = $result->fetch_assoc()) 
        {

        $temp=array();
        $temp['d_email']=$row["d_email"];
        $temp['d_name']=$row["d_name"];
        $emaillll=$row["d_email"];

        $sqll = "SELECT * FROM `user` where `user_type`= '2' and `email`='$emaillll'";
        $result2 = $con->query($sqll);
        while($row2 = $result2->fetch_assoc()) 
        {
          $temp['imageurl']=$row2["imageurl"]; 
          $temp['doc_type']=$row2["doc_type"]; 
        }
      
        array_push($response,$temp);
        }
  } 
  else 
  {
    echo "user not found";
  }
  echo json_encode($response);

  // echo "hello Successfully";
?>