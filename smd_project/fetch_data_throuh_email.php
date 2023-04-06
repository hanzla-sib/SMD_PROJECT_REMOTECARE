<?php

include "config.php";
$response=array();
$cur=$_POST['d_email'];




$sql = "SELECT * FROM `user` where `email`= '$cur'";
$result = $con->query($sql);
if ($result->num_rows > 0) 
{
  
          while($row = $result->fetch_assoc()) 
          {
             $name=$row["name"];
             echo $name;     
          }      
         
} 
else 
{
  echo "user not found";
}





?>