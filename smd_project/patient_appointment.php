<?php

include "config.php";
$response=array();
$email=$_POST['email'];


// $sql = "CREATE TABLE patient_appointment (p_name VARCHAR(128), p_email VARCHAR(128) , appoint_status VARCHAR(128) ,d_name VARCHAR(128), d_email VARCHAR(128) , PRIMARY KEY(p_email,d_email)  );  ";

$sql = "CREATE TABLE patient_appointment (p_name VARCHAR(128), p_email VARCHAR(128) , appoint_status VARCHAR(128) ,d_name VARCHAR(128), d_email VARCHAR(128) , PRIMARY KEY(p_email,d_email)  );  ";


  echo "hello Successfully";
?>