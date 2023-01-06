<?php

include "config.php";
$response=array();
if(isset($_POST["email"]))
{
    $email=$_POST["email"];

	$query="DELETE FROM `user` WHERE `email`=".$email;
	$res=mysqli_query($con,$query);


	if($res)
	{
		$response['email']=$email;
		$response['reqmsg']="user deleted!";
		$response['reqcode']="1";
	}
	else{
		$response['email']=$email;
		$response['reqmsg']="Error deleting user!";
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
