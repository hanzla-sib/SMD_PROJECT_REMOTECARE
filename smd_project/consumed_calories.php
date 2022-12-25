<?php
include "config.php";
$response=array();

$email=$_POST['p_email'];
$calories=$_POST['calories'];
$date=date("Y-m-d");
 
$sql = "SELECT * FROM `consumed_calories` where `p_email`= '$email' and `date_log`='$date'";
$result = $con->query($sql);

  if ($result->num_rows > 0) 
  {
    while($row = $result->fetch_assoc()) 
    {
        $oldcalories=$row["Calories"];
        $oldcalories=$oldcalories+$calories;

        $query="UPDATE `consumed_calories` SET `Calories`='$oldcalories' WHERE `p_email`= '$email' and `date_log`='$date'";
        $res=mysqli_query($con,$query);

        if($res)
        {
            echo "updated in consumed calories";
        }
    
    }
   } 
   else {
    $query="INSERT INTO `consumed_calories`(`p_email`, `date_log`, `Calories` ) VALUES ('$email','$date','$calories')";

    $res=mysqli_query($con,$query);
    if($res){
        echo "insereted in Caloriesconsumed";
    }
    else{
        echo "not inserted error";
    }
  }

?>
