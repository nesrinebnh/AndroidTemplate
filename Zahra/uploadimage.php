
<?php

	require('index.php');

	if($conn){
		$image = $_POST["image"];
		$name = $_POST["name"];
		$lat = $_POST["lat"];
		$lng = $_POST["lng"];
		$price = $_POST["price"];
		$address = $_POST["address"];

		$newName = getRandomWord(rand(7,20));

		$ImagePath = "uploads/$newName.jpg";
		$InsertSQL = "insert into appartement(name, type,lat,lng,adress, prix, image) values ('$newName','allocation','$lat','$lng','$address', '$price','$ImagePath')";

		if(mysqli_query($conn,$InsertSQL)){
			file_put_contents($ImagePath, base64_decode($image));
			echo json_encode(array('response'=>'Image Uploaded successfully'));
		}else{
			echo json_encode(array('response'=>'Image Upload failed'));
		}
	}else{
			echo json_encode(array('response'=>'Connection to database failed'));
		}

	function getRandomWord($len) {
	    $word = array_merge(range('a', 'z'), range('A', 'Z'));
	    shuffle($word);
	    return substr(implode($word), 0, $len);
	}

	 
	/*$ImageData = strval($_POST['check']);
	$fileNameNew = uniqid('', true);*/
	/*$ImageData = strval($_POST['image_path']);
	 
	 	$ImageName = $_POST['image_name'];*/

	/*$ImageData = $_POST['check'];
	$base=$_REQUEST['check'];*/

	//$ImagePath = "uploads/2.jpg";

	//file_put_contents($ImagePath,base64_decode($ImageData));

	/*if(empty($_REQUEST['check'])){
	    $username = 'Anonymous';

	 	$result = array(
			'image name' => 'empty',
			

		);
		echo json_encode($result);
	} 
	else{
		$result = array(
			'image name' => 'ok',
			

		);
		echo json_encode($result);
	}  
	/*if($_SERVER['REQUEST_METHOD'] == 'POST'){

		

	 	$result = array(
			'image name' => $ImageName,
			

		);
		echo json_encode($result);
	}*/


	/*$ext =  pathinfo($ImageData)['extension'];

	

	$fileActualExt = strtolower($ext);

	$fileNameNew = uniqid('', true).".".$fileActualExt;

	$fileDestination = 'uploads/'.$fileNameNew;

	

	/*$imageString = file_get_contents($ImageData);
	$save = file_put_contents($fileDestination,$imageString);*/

	//$value = move_uploaded_file($ImageData, $fileDestination);
	/*$value = copy($ImageData, $fileDestination);
	$result = array(
		'image path'=> $ImageData,
		'unique id' => $fileNameNew,
		'EXT' => $ext,
		'destination'=> $fileDestination,
		'value'=>$value

	);
	echo json_encode($result);

	//copy($ImageData, $fileDestination);
	//Get the file
	
	/*$InsertSQL = "insert into appartement values (1,'app1','allocation','14.2517','37.2547','address cite kgjghkd', 102145,'$fileDestination')";
	if(mysqli_query($conn, $InsertSQL)){

		mysqli_close($conn);
	}*/

	

	

	

	/*$fileNameNew = uniqid('', true);
	$ImagePath = "uploads/$fileNameNew.png";
	 
	$ServerURL = "$ImagePath";
	 
	$InsertSQL = "insert into image (image_path) values ('$ServerURL')";
	 
	/*if(mysqli_query($conn, $InsertSQL)){

		file_put_contents($ImagePath,base64_decode($ImageData));

		echo "Your Image Has Been Uploaded.";
	}
	 
		mysqli_close($conn);
	}else{
		echo "Not Uploaded";
	}*/

?>