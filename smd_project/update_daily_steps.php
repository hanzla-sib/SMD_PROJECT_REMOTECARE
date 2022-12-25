<?php

include "config.php";
$response=array();

if(isset($_POST["email"],$_POST["steps"]))
{
	$steps=$_POST["steps"];
	$email=$_POST["email"];
	$motion=$_POST["Motion"];

	$query="UPDATE `daily_steps` SET `steps_daily`='$steps',`motion`='$motion' where `Demail`='$email'";
	$res=mysqli_query($con,$query);

	if($res){
		echo "Steps updated";
	}
	else{
		echo "Steps not updated";	
	}
}
else
{
	$response['email']="NA";
	$response['reqmsg']="Incomplete Request!";
	$response['reqcode']="0";
	$x=json_encode($response);
	echo $x;
}

?>
