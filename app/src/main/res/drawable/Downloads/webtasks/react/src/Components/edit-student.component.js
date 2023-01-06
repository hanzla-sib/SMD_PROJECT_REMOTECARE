// EditStudent Component for update student data

// Import Modules
import React, { useState, useEffect } from "react";
import { useParams, useNavigate } from "react-router-dom";
import axios from "axios";
import StudentForm from "./StudentForm";

// EditStudent Component
const EditStudent = () => {
  let params = useParams();
  let _id = params.id;
  let navigate = useNavigate();
  const [formValues, setFormValues] = useState({ name: '', email: '', rollno: '' })
	
  //onSubmit handler
  const onSubmit = (studentObject) => {
    axios
    .put(
      "http://localhost:4000/students/update-student/" + _id,
      studentObject
    )
    .then((res) => {
      if (res.status === 200) {
      alert("Student successfully updated");
      navigate("/student-list");
      } else Promise.reject();
    })
    .catch((err) => alert("Something went wrong"));
  };

  // Load data from server and reinitialize student form
  useEffect(() => {
    axios
    .get(
      "http://localhost:4000/students/update-student/" + _id
    )
    .then((res) => {
      const { name, email, rollno } = res.data;
      setFormValues({ name, email, rollno });
    })
    .catch((err) => console.log(err));
  }, [_id]);

  // Return student form
  return (
    <StudentForm
    initialValues={formValues}
    onSubmit={onSubmit}
    enableReinitialize
    >
    Update Student
    </StudentForm>
  );
};

// Export EditStudent Component
export default EditStudent;
