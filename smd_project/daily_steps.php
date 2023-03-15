<?php
include "config.php";
$response=array();
$email=$_POST['p_email'];

$date = new DateTime("now", new DateTimeZone('Asia/Karachi') );
$date=$date->format('Y-m-d');
$sql = "SELECT * FROM `daily_steps` where `Demail`= '$email' ";
$result = $con->query($sql);

if ($result->num_rows > 0) 
{
  while($row = $result->fetch_assoc()) 
  {
      if($date==$row["date_log"])
    {
        $step=$row["steps_daily"];
        echo "$step";
    }
    else
    {
        $step=0;
        echo "$step";
    }
      
  }

}
else
{
    echo"No entry";
}


?>
