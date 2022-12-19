<?php
include "config.php";
$response=array();

if(!empty($_POST['image'])){
    $path=date("d-m-Y").'-'.time().'-'.rand(10000,100000).'.jpg'; 
    $email=$_POST['email'];
    $imageurl=$_POST['image'];
    if(file_put_contents($path,base64_decode($_POST['image'])))
    {
      $sql = "UPDATE user SET imageurl='$path' WHERE email='$email'";
      if ($con->query($sql) === TRUE) {
        echo "success updated image in mysql";
      } else {
        echo "Error updating record: " . $con->error;
      }
    }
    else{
        echo"failed to upload iamge";
    }
}
else
{
    echo"no iamge found";
}
  
  
?>
