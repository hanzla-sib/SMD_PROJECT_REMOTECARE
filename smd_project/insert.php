<?php

include "config.php";
$response=array();
if(isset($_POST["name"],$_POST["email"],$_POST["password"],$_POST["type"],$_POST["gender"]))
{
	$name=$_POST["name"];
	$password=$_POST["password"];
	$email=$_POST["email"];
    $type=$_POST["type"];
    $gender=$_POST["gender"];

	$query="INSERT INTO `user`(`name`, `email`, `password`, `user_type`,`gender` ) VALUES ('$name','$email','$password','$type','$gender')";

	$res=mysqli_query($con,$query);
    $query="INSERT INTO `daily_steps`( `Demail`, `steps_daily`) VALUES ('$email','0')";

	$res=mysqli_query($con,$query);



	if($res)
	{
		echo "success in creting both table";
	}
	else{
		echo "no sucess in creting both table";

	}
}
else
{
	echo "data not cooming from volley";
}





?>
