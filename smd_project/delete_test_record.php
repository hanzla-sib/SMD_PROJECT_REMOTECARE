<?php

include "config.php";
$response=array();

$email=$_POST["p_email"];
$image_url=$_POST["image_url"];

$query="DELETE FROM `test_record` WHERE `Temail` = '$email' and `imageurl` = '$image_url' ";
$res=mysqli_query($con,$query);


if($res)
{

    echo "Deleted Succesfully";
}
else
{
    echo "Error in deleting";
}

?>
