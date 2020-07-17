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
	print_r($_FILES['file']);

	$allowed = array('jpg', 'png', 'jpeg');

	if(in_array($fileActualExt, $allowed)){

		if($fileError === 0){
			if($fileSize< 500000000){

				$fileNameNew = uniqid('', true).".".$fileActualExt;

				
				$fileDestination = 'uploads/'.$fileNameNew;
				move_uploaded_file($fileTMPName, $fileDestination);
				$InsertSQL = "insert into appartement values (11,'app6','allocation','14.2517','37.2547','address cite kgjghkd', 102145,'$fileDestination')";
				if(mysqli_query($conn, $InsertSQL)){


					echo "Your Image Has Been Uploaded.";
				
				 
					mysqli_close($conn);
				}else{
					echo "Not Uploaded";
				}

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