<?php

include "config.php";
$response=array();
if(isset($_POST["id"],$_POST["name"],$_POST["email"],$_POST["password"]))
{
    $id=$_POST["id"];
	$name=$_POST["name"];
	$password=$_POST["password"];
	$email=$_POST["email"];

	$query="UPDATE `user` SET `name`='$name',`password`='$password',`email`='$email' WHERE `id`=".$id;
	$res=mysqli_query($con,$query);


	if($res)
	{
		$response['id']=$id;
		$response['reqmsg']="user updated!";
		$response['reqcode']="1";
	}
	else{
		$response['id']=$id;
		$response['reqmsg']="Error updating user!";
		$response['reqcode']="0";

	}
}
else
{
	$response['id']="NA";
	$response['reqmsg']="Incomplete Request!";
	$response['reqcode']="0";
}

$x=json_encode($response);
echo $x;



?>
