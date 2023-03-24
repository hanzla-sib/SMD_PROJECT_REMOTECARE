<?php
 
if(isset($_POST["video"])) {
    // Decode the Base64-encoded video data
    $video_data = base64_decode($_POST["video"]);
  
    // Specify the directory to save the uploaded video
    $target_dir = "uploads/";
  
    // Generate a unique file name for the video
    $target_file = $target_dir . uniqid() . ".mp4";
  
    // Save the decoded video data to the specified file
    if (file_put_contents($target_file, $video_data)) {
      echo "The video has been uploaded successfully.";
    } else {
      echo "Sorry, there was an error uploading your video.";
    }
  } else {
    echo "No video data received.";
  }
?>



