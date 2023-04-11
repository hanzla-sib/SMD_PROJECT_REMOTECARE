<?php

include "config.php";
$response=array();

$p_email=$_POST["p_email"];
$d_email=$_POST["d_email"];

$query="DELETE FROM `patient_appointment` WHERE `p_email` = '$p_email' and `d_email` = '$d_email' ";
$res=mysqli_query($con,$query);
if($res)
{
    echo "Deleted Succesfully";
}
else
{
    echo "Error in deleting";
}


$query="DELETE FROM `doctor_appointment` WHERE `p_email` = '$p_email' and `d_email` = '$d_email' ";
$res=mysqli_query($con,$query);
if($res)
{
    echo "Deleted Succesfully";
}
else
{
    echo "Error in deleting";
}

$sql = "SELECT * FROM `doctor_recommendation` where `d_email`= '$d_email' and `p_email`='$p_email' ";
$result = $con->query($sql);

if ($result->num_rows > 0) 
{


    $query="DELETE FROM `doctor_recommendation` WHERE `p_email` = '$p_email' and `d_email` = '$d_email' ";
    $res=mysqli_query($con,$query);
    if($res)
    {
    
        echo "Deleted Succesfully";
    }
    else
    {
        echo "Error in deleting";
    }
    
}
else
{
    echo"No entry";
}


?>
