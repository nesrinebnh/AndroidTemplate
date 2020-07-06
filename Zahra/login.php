<?php

	require('index.php');

	$code = strval($_POST["check"]);
	//echo 'code '.$code.' type '.gettype($code);
	//$code = "OU-EPSP-15221";
	echo 'code '.$code.' type '.gettype($code);
	//echo 'type '.gettype($code);
	$mysql_qry = "select code_etab, etablissement from etablissements where code_etab like '$code';";
	$result = mysqli_query($conn, $mysql_qry);
	if(!mysqli_query($conn, $mysql_qry)){  
		echo 'il y a une erreur avec le SGBD (problème de connexion, table introuvable, ...). <a href="index.htm"> Retour </a> ';  
    	die('error' .mysql_error());  
	}
	$row = $result->fetch_array(MYSQLI_NUM);

	$username = $row[1];


	if(mysqli_num_rows($result)>0){
		echo ' if statment ';
		$json['result'] = $username;
		echo json_encode($json);
	}



	//$row = $result->fetch_array(MYSQLI_NUM);

	//$username = $row[1];
	//echo '<br> username '.$username.'<br>';

	/*echo ' this is a test <br>';
	echo ' show '.mysqli_num_rows($result);
	if(mysqli_num_rows($result)>0){
		echo ' if statment ';
		$json['result'] = $username;
		echo json_encode($json);
	}*/
 
	/*if ($conn->connect_error) {
	    die("Connection failed: " . $conn->connect_error);
	}
 
	//if everything is fine
	 
	//creating an array for storing the data 
	$heroes = array(); 
	$conn -> set_charset("utf8");
	 
	//this is our sql query 
	$sql = "SELECT code_etab, etablissement FROM etablissements limit 1;";
	$query1 = mysqli_query($conn,$sql);

	$result = array();
	while(($qrep=$query1->fetch_assoc())) {
	    $result[] = $qrep;
	}
	 
	
	echo json_encode($result);

	$conn->close()
*/
?>