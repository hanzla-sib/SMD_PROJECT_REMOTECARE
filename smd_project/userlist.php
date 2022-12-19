<?php

include "config.php";
$response=array();
$email=$_POST['email'];


$my_user_type="1";
$sql = "SELECT `user_type` FROM `user` where `email`= '$email'";
$result = $con->query($sql);
$check=FALSE;
  if ($result->num_rows > 0) 
  {
    
            while($row = $result->fetch_assoc()) 
            {
                $my_user_type=$row['user_type'];
            }
            if($my_user_type=="1")
            {
                $sql = "SELECT `name`,`email` FROM `user` where `user_type`= '2'";
                $result = $con->query($sql);
                        if ($result->num_rows > 0) 
                        {
                                while($row = $result->fetch_assoc()) 
                                {
                                  $em=$row["email"];
                                  $sql = "SELECT * FROM `patient_appointment` where `d_email`='$em' and `p_email`='$email'";
                                  $result1 = $con->query($sql);
                                  if ($result1->num_rows > 0) 
                                  {                                
                                
                                  } 
                                  else{
                                    $temp=array();
                                    $temp['name']=$row["name"];
                                    $temp['email']=$row["email"];
                                    array_push($response,$temp);
                                  }          
                                }
                               
                        }
                        else
                        {

                        }

        } else 
        {
            echo "user not found";
        }         
  } 
  else 
  {
    echo "user not found";
  }
// //   echo json_encode($userimage);

echo json_encode($response);
?>