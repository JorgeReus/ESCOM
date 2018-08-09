<?php
$conexion = new mysqli('localhost','test','pass','ukranioFest');

$array = array(array('Partido','Votos',array('role'=>'style'),array('role'=>'annotation')));


$consulta = "SELECT nombre,votos,color FROM candidato";
	$result = $conexion->query($consulta);
	
	while ($fila = $result->fetch_array()) {
		array_push($array,array($fila['nombre'], intval($fila['votos']), $fila['color'], $fila['votos']));
	}
	
echo json_encode($array);
?>