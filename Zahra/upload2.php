<?php
require('index.php');
if(isset($_POST['submit'])){
	$file = $_FILES['file'];
	$fileName = $_FILES['file']['name'];
	$fileTMPName = $_FILES['file']['tmp_name'];
	$fileSize = $_FILES['file']['size'];
	$fileError = $_FILES['file']['error'];
	$fileType = $_FILES['file']['type'];

	$fileExt = explode('.',$fileName);
	$fileActualExt = strtolower(end($fileExt));

	$allowed = array('jpg', 'png', 'jpeg');

	if(in_array($fileActualExt, $allowed)){

		if($fileError === 0){
			if($fileSize< 500000){

				$fileNameNew = uniqid('', true).".".$fileActualExt;
				$fileDestination = 'uploads/'.$fileNameNew;
				move_uploaded_file($fileTMPName, $fileDestination);
				echo $fileDestination;
				

			}else{
				echo "your file is too big";
			}


		}else{
			echo "there was an error uploading your file";
		}
			
	}else{
		echo "you can't not upload files of this type!";
	}

}