<?php

include "config.php";
$response=array();

if(!empty($_POST['email'])){
    $path=date("d-m-Y").'-'.time().'-'.rand(10000,100000).'.jpg'; 
    $details=$_POST['details'];
    $email=$_POST['email'];
    $imageurl=$_POST['image'];
	// echo $details;
  
    if(file_put_contents($path,base64_decode($_POST['image'])))
    {
      $sql = "INSERT INTO `test_record`(`Temail`, `imageurl`, `details`) VALUES ('$email','$path','$details')";
      if ($con->query($sql)) {
        echo"success";
      } else {
        echo "Error updating record: ".$con->error;
      }
    }
    else{
        echo"failed to upload image";
    }
}
else{
    echo"no image found";
}

?>
