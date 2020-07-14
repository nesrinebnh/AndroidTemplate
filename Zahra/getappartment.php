<?php

	$db_name = "bd";
	$mysql_username = "root";
	$mysql_password = "";
	$server_name = "localhost";

	$conn = mysqli_connect($server_name, $mysql_username, $mysql_password, $db_name);

	if ($conn->connect_error) {
    	die("Connection failed: " . $conn->connect_error);
	}
	
	//creating an array for storing the data 
	$heroes = array(); 
	 
	//this is our sql query 
	$sql = "SELECT id, name, type,lat,lng, adress, prix, image FROM appartement;";
	 
	//creating an statment with the query
	$stmt = $conn->prepare($sql);
	 
	//executing that statment
	$stmt->execute();
	 
	//binding results for that statment 
	$stmt->bind_result($id, $name, $type, $lat, $lng ,$adress, $prix, $image);
	 
	//looping through all the records
	while($stmt->fetch()){
		
		//pushing fetched data in an array 
		$temp = [
			'id'=>$id,
			'name'=>$name,
			'type'=>$type,
			'lat'=>$lat,
			'lng'=>$lng,
			'adress'=>$adress,
			'prix'=>$prix,
			'image'=>$image
			
		];
		
		//pushing the array inside the hero array 
		array_push($heroes, $temp);
	}
	 
	//displaying the data in json format 
	echo json_encode($heroes);



?>