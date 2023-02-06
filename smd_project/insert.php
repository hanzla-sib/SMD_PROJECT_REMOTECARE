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
	$doc_type=$_POST["user_t"];



	


	$query="INSERT INTO `user`(`name`, `email`, `password`, `user_type`,`gender` , `doc_type`  ) VALUES ('$name','$email','$password','$type','$gender' , '$doc_type')";

	$res=mysqli_query($con,$query);
    $query="INSERT INTO `daily_steps`( `Demail`, `steps_daily`,`motion`) VALUES ('$email','0','Resting')";

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
