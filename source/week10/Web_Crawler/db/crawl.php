<?php
//div[id=bodyContent] p
ini_set('display_errors',1);
ini_set('display_startup_errors',1);
error_reporting(E_ALL | E_STRICT);
ini_set('html_errors', 1);
ini_set('max_execution_time', 0);
set_time_limit(0);


include "../include/crawl.php";
include "../include/connect.php";

function startsWith($haystack, $needle)
{
    return $needle === "" || strpos($haystack, $needle) === 0;
}

function add_sentence($s,$l)
{
	$sen=mysql_real_escape_string($s);
	//INSERT INTO `all_sentence`(`sen`, `length`) VALUES ("I am shivam",10)
	$sql='INSERT INTO `'.$l.'_sentence`(`sen`, `length`) VALUES ("'.$s.'",'.strlen($s).')';
	mysql_query($sql);
}

function add_url($url,$website,$lang,$re)
{
	$url=mysql_real_escape_string($url);
	//INSERT INTO `all_sentence`(`sen`, `length`) VALUES ("I am shivam",10)
	$sql="INSERT INTO `zadmin_crawl`.`all_url` (`url`, `website`, `language`, `re`) VALUES ('".$url."', '".$website."', '".$lang."', '".$re."')";
	//echo $sql;
	mysql_query($sql);
}

function al_jazirah($url,$path,$website,$rec,$lang)
{
	$html=file_get_html($url);
	$data=$html->find('p[class=news]');
	$count=0;
	$count2=0;
	$p=parse_url($url);
	foreach($data as $i)
	{
		$para=strip_tags($i->innertext);
		echo $para."<br/><br/>";
	}
	$data=$html->find('a');
	foreach($data as $i)
	{
		$para=$i->href;
		if(startsWith($i->href,'/20'))
		echo $para."<br/><br/>";
	}
}

function crawl($url,$path,$website,$rec,$lang)
{
	$html=file_get_html($url);
	$data=$html->find('div[id=bodyContent] p');
	$count=0;
	$count2=0;
	$p=parse_url($url);
	foreach($data as $i)
	{
		$para=strip_tags($i->innertext);
		$no_bracket=preg_replace("/\([^)]+\)/",'',$para);
		$no_bracket=preg_replace("/\[[^)]+\]/",'',$no_bracket);
		$re = '/# Split sentences on whitespace between them.
			(?<=                # Begin positive lookbehind.
			  [.!?]             # Either an end of sentence punct,
			| [.!?][\'"]        # or end of sentence punct and quote.
			)                   # End positive lookbehind.
			(?<!                # Begin negative lookbehind.
			  Mr\.              # Skip either "Mr."
			| Mrs\.             # or "Mrs.",
			| Ms\.              # or "Ms.",
			| Jr\.              # or "Jr.",
			| Dr\.              # or "Dr.",
			| Prof\.            # or "Prof.",
			| Sr\.              # or "Sr.",
			| T\.V\.A\.         # or "T.V.A.",
			| St\.         # or "T.V.A.",
			| \s[A-Z]\.              # or initials ex: "George W. Bush",
								# or... (you get the idea).
			)                   # End negative lookbehind.
			\s+                 # Split on whitespace between sentences.
			/ix';
		$sentences = preg_split($re, $no_bracket, -1, PREG_SPLIT_NO_EMPTY);
		foreach($sentences as $sentence)
		{
			if(strlen($sentence)>10 && (!strpos($sentence,':')) &&  (ctype_upper($sentence[0])||ctype_digit($sentence[0])))
			{
				add_sentence($sentence,$lang);
				$count++;
			}
		}
	}
	$data=$html->find('a');
	$count2=0;
	$url="";
	foreach($data as $i)
	{
		if(startsWith($i->href,'/wiki/') && (!strpos($i->href,':')))
		{
			if(strpos($i->href,'#')>0)
				$i->href=substr($i->href, 0, strpos($i->href,'#'));
	//		echo "http://".$p["host"].$i->href."<br/>";
			add_url("http://".$p["host"].$i->href,$website,3,$rec);
			$count2++;
		}
	}
	echo $count." sentences have been added from URL : ".$url."<br/>";
	echo $count2." links have been added from URL : ".$url;
}

function crawled($id)
{
	$sql='UPDATE `all_url` SET done=1 WHERE id='.$id;
	mysql_query($sql);
}

$query = "SELECT * FROM `website`,`all_url` WHERE `all_url`.website=`website`.id AND done=0 AND website=4 ORDER BY `all_url`.`id` ASC LIMIT 0,5";
$result = mysql_query($query);
while($row = mysql_fetch_array($result)){
/*
	crawled($row['id']);
	if($row['language']<4)
	crawl($row['url'],$row['path'],$row['website'],$row['re'],$row['language']);					//Crawl and do anything you wish with this URL.. :P
	else if($row['website']==4)
	al-jazirah($row['url'],$row['path'],$row['website'],$row['re'],$row['language']);
	sleep(0.5);
*/
	al_jazirah($row['url'],$row['path'],$row['website'],$row['re'],$row['language']);
}
?>