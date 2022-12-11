<?php

include "config.php";
$response=array();
if(isset($_POST["Memail"],$_POST["steps_monthly"]))
{
	$email=$_POST["Memail"];
	$steps_monthly=$_POST["steps_monthly"];

	$query="INSERT INTO `weekly_steps`( `Memail`,`date_log`, `steps_monthly`) VALUES ('$email',curdate(),'$steps_monthly')";

	$res=mysqli_query($con,$query);


	if($res)
	{
		$response['id']=mysqli_insert_id($con);
		$response['reqmsg']="weekly steps Inserted!";
		$response['reqcode']="1";
	}
	else{
		$response['id']="NA";
		$response['reqmsg']="Error Inserting weekly steps!";
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
