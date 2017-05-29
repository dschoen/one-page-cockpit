<?php		
	
CONST AUTH = "dGVzdDp0ZXN0";	//test-test
CONST SECRET = "jahd7audn7fnafz7aenaw7ezn78eaew7eown87xnraw87erxne7rzcneworafhadvnnt48tu0rjwhkfa3rehfhnafao87w34rnao87ONBW8aw7dbo87n7BW";

// POST, GET, DELETE
$method = $_SERVER['REQUEST_METHOD'];

// full request
$request = explode('/', trim($_SERVER['PATH_INFO'],'/'));

// retrieve the table and key from the path
$module = preg_replace('/[^a-z0-9_]+/i','',array_shift($request));
$id = array_shift($request)+0;

// Input Data
$input = json_decode(file_get_contents('php://input'),true);

error_log("Request: " . $method . " " . $module . "/" . $id );  // get
//error_log("AAA ".json_encode(getallheaders()));


// ------------------------------------------------------------------------------------

// -- Get Module ---
$data_file = "";
switch ($module) {
	case "memos":
		$data_file = "memos.json";
		break;
	case "cards":
		$data_file = "cards.json";
		break;
	case "login":
		error_log("LOGIN: " . json_encode($input));
		
		$raw = $input['username'] . ":" . $input['password'];
		$base = base64_encode($raw);
		
		if ($base == AUTH) {
			http_response_code(200);
			echo json_encode( ['secret' => SECRET ]);
			return;
		} else {
			http_response_code(401);
			echo '';
			return;
		}		
		break;
	default:
		http_response_code(400);
		echo "Bad Request!";
		return;
}

// check Security
if (getallheaders()['Authorization'] != SECRET) {
	http_response_code(401);
	echo "Not allowed.";
	return;
}

// open file
$file = fopen(dirname(__FILE__)."/".$data_file, "r") or die("Unable to open file!");
$elements = json_decode( fread($file,filesize(dirname(__FILE__)."/".$data_file)), true );
fclose($file);

switch ($method) {
	case 'GET':	
		echo json_encode($elements);
		return;
		break;
	case 'POST':	
		// create ID
		$input['id'] = hexdec(uniqid());
		
		// add element to list
		$elements[] = $input;
		break;
	case 'PUT':	
		// update one Element
		for ($i = 0; $i < sizeof($elements); $i++) {
			$element = $elements[$i];
			if ($element['id'] == $input['id']) {
				$elements[$i] = $input;
				break;
			}
		}
		break;
	case 'DELETE':
		// delete the one
		for ($i = 0; $i < sizeof($elements); $i++) {
			$element = $elements[$i];
			if ($element['id'] == $id) {				
				//error_log("DELETE Element: ".json_encode($element));				
				array_splice($elements, $i, 1);				
				break;
			}
		}
		break;
	default:
		echo "Bad Request.";
		break;		
}

// save again
$file = fopen(dirname(__FILE__)."/".$data_file, "w") or die("Unable to open file!");
fwrite($file, json_encode($elements));
fclose($file);


?>