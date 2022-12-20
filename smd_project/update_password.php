<?php
include "config.php";

if(isset($_POST["email"],$_POST["password"]))
{
   
	$password=$_POST["password"];
	$email=$_POST["email"];

	$query="UPDATE `user` SET `password`='$password' WHERE `email`= '$email'";
	$res=mysqli_query($con,$query);


	if($res)
	{
        echo "updated password in MYsql";
	}
	else{
	
        echo "password not updated in Mysql";
	}
}
else
{
    echo "values not found by php";
}






?>
