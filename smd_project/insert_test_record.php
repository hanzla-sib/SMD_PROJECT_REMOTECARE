<?php

include "config.php";
$response=array();

if(!empty($_POST['email'])){
    $path=date("d-m-Y").'-'.time().'-'.rand(10000,100000).'.jpg'; 
    $details=$_POST['details'];
    $email=$_POST['email'];
    $imageurl=$_POST['image'];
    $image_link = $_POST['link'];
  
    if(file_put_contents($path,base64_decode($_POST['image'])))
    {
      $sql = "INSERT INTO `test_record`(`Temail`, `imageurl`, `details`,`image_link`) VALUES ('$email','$path','$details','$image_link')";
      if ($con->query($sql)) {
        echo $path;
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
