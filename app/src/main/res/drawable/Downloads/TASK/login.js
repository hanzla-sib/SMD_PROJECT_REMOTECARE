const loginText = document.querySelector(".title-text .login");
const loginForm = document.querySelector("form.login");
const loginBtn = document.querySelector("label.login");
const signupBtn = document.querySelector("label.signup");
const signupLink = document.querySelector("form .signup-link a");
signupBtn.onclick = (()=>{
  loginForm.style.marginLeft = "-50%";
  loginText.style.marginLeft = "-50%";
});
loginBtn.onclick = (()=>{
  loginForm.style.marginLeft = "0%";
  loginText.style.marginLeft = "0%";
});
signupLink.onclick = (()=>{
  signupBtn.click();
  return false;
});

function email(emaill){
window.emaiil=emaill.value;
}
function password(passwordd){
window.passworrd=passwordd.value;

}
function confirmPassword(conpass){
window.cPassword=conpass.value;
}
function PasswordCheck(){
  if(window.passworrd===window.cPassword){
    alert("Signup successful");
    window.correctness=1;
  }
  else{
    alert("Confirmed password and password do not match");
    window.correctness=0;
  }

}
function EmailSignIn(thiss){
window.SEmail=thiss.value;
}

function PasswordSignIn(thiss){
window.SPassword=thiss.value;
}

function SignedIn(){
  if(window.SEmail===window.emaiil){
    if(window.correctness===1){
      if(window.passworrd===window.SPassword){
        alert("Log in successful");
        window.open("dashboard.html");
      }
      else{
        alert("Wrong password entered");
      }
    }
    else{
      alert("This account is not correctly signed up.");
    }
  }
  else{

    alert("This email is not registered.");
  }

}
