<?php
include "config.php";
$response=array();

$email=$_POST['email'];
$token="";

 
$sql = "UPDATE `user` SET `user_token`='$token' where `email`= '$email'";
$result = $con->query($sql);

  if ($result) 
  {
    echo "token removed in user table";
  } 
  else 
  {
        echo "error updating user token";
  }

?>
