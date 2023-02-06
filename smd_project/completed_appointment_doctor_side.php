<?php

include "config.php";
$response=array();

$d_email=$_POST['d_email'];
$p_email=$_POST['p_email'];

$Completed = "Completed";

$sql = "SELECT * FROM `doctor_appointment` where `d_email`= '$d_email' and `p_email`= '$p_email' ";
$result = $con->query($sql);

  if ($result->num_rows > 0) 
  {
    
    while($row = $result->fetch_assoc()) 
    {
            // $temp=array();
            $pat_name=$row["p_name"];
            $pat_email=$row["p_email"];
            $doc_name=$row["d_name"];
            $doc_email=$row["d_email"];
            

            $time=$row["Time1"];
            $date=$row["Date1"];
            // array_push($response,$temp);

            $sql2 = "INSERT into appointment_history (`d_name`, `d_email`, `Time1`, `Date1`, `p_name`, `p_email`) VALUES ('$doc_name','$doc_email','$time','$date','$pat_name','$pat_email')";
            $res=mysqli_query($con,$sql2);
            if($res){
                echo "insereted in Appointment History Table";
                $sql3="DELETE FROM `patient_appointment` WHERE `p_email` = '$pat_email' and `d_email` = '$doc_email' ";
                $res2=mysqli_query($con,$sql3);
                if($res2){
                    echo "deleted from patient appointment Table";
                    $sql4="DELETE FROM `doctor_appointment` WHERE `p_email` = '$pat_email' and `d_email` = '$doc_email' ";
                    $res3=mysqli_query($con,$sql4);
                    if($res3){
                        echo "deleted from doctor appointment Table";
                    }
                    else{
                        echo "not inserted error";
                    }
                }
                else{
                    echo "not inserted error";
                }    
            }
            else{
                echo "not inserted error";
            }
    }      
  } 
  else 
  {
    echo "user not found";
  }

  echo json_encode($response);
?>