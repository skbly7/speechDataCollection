<?php
include "crawl.php";	//Simple HTML DOM parser PHP Library
$url=$_GET['url'];	//URL as a get parameter
$count=0;
$html = file_get_html($url);
$answer = $html->find('div p');	//Simple search for finding the sentences (general)
foreach($answer as $i)
{
	$ans=preg_split('/[.?!]/',$i->innertext);	//Simple splitting on basis of . ? or !
	foreach($ans as $io)
	{
		$count=$count+1;
		echo $count.". ".$io."<br/><br/>";
	}
}
$answer = $html->find('div a');	//Search for all the <a> tags i.e. Links
foreach($answer as $i)
{
	echo $i->href."<br/><br/>";
}
?>
