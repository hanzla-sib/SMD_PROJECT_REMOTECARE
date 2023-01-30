<?php

include "config.php";
$response=array();

$p_email=$_POST['p_email'];
$p_name=$_POST['p_name'];
$d_name="";
$d_email=$_POST['d_email'];
$date=$_POST['date'];
$time=$_POST['time'];

// echo $date;
// echo $time;


// $sql = "UPDATE doctor_appointment SET appoint_status= 'Accepted',Date= '$date',Time= '$time'
//         WHERE p_email='$p_email' and d_email='$d_email' ";
$sql = "UPDATE doctor_appointment SET appoint_status= 'Accepted', Date1= '$date',Time1= '$time'
        WHERE p_email='$p_email' and d_email='$d_email' ";

      if ($con->query($sql) === TRUE) 
      {
       
        $sql = "UPDATE patient_appointment SET appoint_status= 'Accepted', Date1= '$date',Time1= '$time' 
        WHERE p_email='$p_email' and d_email='$d_email' ";
         if ($con->query($sql) === TRUE) 
         {
          echo "succsss both tables";
          $sql3 = "SELECT name FROM user WHERE email = '$d_email' ";
          $result = $con->query($sql3);
          if ($result->num_rows > 0) 
          {
            while($row = $result->fetch_assoc()) 
            {
                $d_name=$row["name"];
            }
          }
         
          $sql2 = "INSERT into appointment_history (`d_name`, `d_email`, `Time1`, `Date1`, `p_name`, `p_email`) VALUES ('$d_name','$d_email','$time','$date','$p_name','$p_email')";
          $res=mysqli_query($con,$sql2);
          if($res){
              echo "insereted in Appointment History Table";
          }
          else{
              echo "not inserted error";
          }
         } 
         else 
         {
           echo "Error updating both: " . $con->error;
         }
      } 
      else 
      {
        echo "Error updating one: " . $con->error;
      }






  // echo "hello Successfully";
?>