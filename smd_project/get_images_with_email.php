<?php

include "config.php";
$response=array();
$email=$_POST['d_email'];



$sql = "SELECT * FROM `user` where `email`= '$email'";
$result = $con->query($sql);
if ($result->num_rows > 0) 
{
  
          while($row = $result->fetch_assoc()) 
          {
              $temp=array();
              $temp['imageurl']=$row["imageurl"];
              $temp['email']=$row["email"];
             
              array_push($response,$temp);
          }      
          echo json_encode($response);
} 
else 
{
  echo "user not found";
}


// //   echo json_encode($userimage);


?>