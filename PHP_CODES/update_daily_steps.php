<?php

include "config.php";
$response=array();

if(isset($_POST["email"],$_POST["steps"]))
{
	$steps=$_POST["steps"];
	$email=$_POST["email"];

	$query="UPDATE `daily_steps` SET `steps_daily`='$steps' where `Demail`='$email'";
	$res=mysqli_query($con,$query);

	if($res)
	{
		$response['email']=$email;
		$response['reqmsg']="user updated!";
		$response['reqcode']="1";
	}
	else{
		$response['email']=$email;
		$response['reqmsg']="Error updating user!";
		$response['reqcode']="0";
	}
}
else
{
	$response['email']="NA";
	$response['reqmsg']="Incomplete Request!";
	$response['reqcode']="0";
}

$x=json_encode($response);
echo $x;


?>
