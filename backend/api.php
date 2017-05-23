<?php		
		
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
	
		// update the one
		for ($i = 0; $i < sizeof($elements); $i++) {
			$element = $elements[$i];
			if ($element['id'] = $input['id']) {
				$elements[$i] = $input;
				break;
			}
		}
		break;
	case 'DELETE':
		// delete the one
		for ($i = 0; $i < sizeof($elements); $i++) {
			$element = $elements[$i];
			if ($element['id'] = $id) {			
				
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