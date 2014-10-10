<?php
include "include/connect.php";
?>
<!doctype html>
<html lang="en">
<head>
    <meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>Akshar Speech | Pending URLs</title>
    


<link rel="stylesheet" href="http://yui.yahooapis.com/pure/0.5.0/pure-min.css">

        <link rel="stylesheet" href="basic.css">


</head>
<body>




<?php if(!(isset($_POST['search']))) {?>

<div id="layout">
    <?php include "include/sidebar.php"; ?>

    <div id="main">
        <div class="header">
            <h2>Please select options and download list of sentances accordingly.</h2>
        </div>
        <div class="content">
					<form class="pure-form pure-form-stacked" method="post">
						<fieldset>
    <div class="pure-u-1-2" style="width:49%;">
        							<label for="language">Language</label>
							<select name="lang" class="pure-u-1">
								<?php language_list(); ?>
							</select>
    </div>
    <div class="pure-u-1-2">
        							<label for="language">Website</label>
							<select id="website" class="pure-u-1">
								<?php website_url(); ?>
							</select>
    </div>


	<label for="uwords">Unique words (Empty to ignore a field)</label>				
    <div class="pure-u-1-2" style="width:49%;">
        <input class="pure-input-1" type="text" placeholder="Minimum unique words">
    </div>
    <div class="pure-u-1-2">
        <input class="pure-input-1" type="text" placeholder="Maximum unique words">
    </div>
	<label for="uwords">Count of words (Empty to ignore a field)</label>				
    <div class="pure-u-1-2" style="width:49%;">
        <input class="pure-input-1" type="text" placeholder="Minimum words" name="minl">
    </div>
    <div class="pure-u-1-2">
        <input class="pure-input-1" type="text" placeholder="Maximum words" name="maxl">
    </div>
		<input type="text" name="search" hidden value="1">
							<button type="submit" class="pure-button pure-button-primary">Submit</button>
						</fieldset>
					</form>
        </div>
   
    </div>
</div>

<?php } else { ?>

<div id="layout">
    <?php include "include/sidebar.php"; ?>

    <div id="main">
        <div class="header">
            <h2>Search Results</h2>
        </div>

        <div class="header">
            <table class="pure-table">
    <thead>
        <tr>
            <th>#</th>
            <th>Sentence</th>
	</tr>
    </thead>

    <tbody>
	<?php
	
	$query = "SELECT * FROM `".$_POST['lang']."_sentence` WHERE ";
	if($_POST['minl']=="")
	$query.=" (LENGTH(sen) - LENGTH(REPLACE(sen, ' ', ''))+1)>0";
	else
	$query.=" (LENGTH(sen) - LENGTH(REPLACE(sen, ' ', ''))+1)>".$_POST['minl'];

	if($_POST['maxl']!="")
	$query.=" AND  (LENGTH(sen) - LENGTH(REPLACE(sen, ' ', ''))+1)<".$_POST['maxl'];
	
	$result = mysql_query($query);
	$i=1;
	while($row = mysql_fetch_array($result)){
	echo '	<tr';
	if($i%2==0)
		echo ' class="pure-table-odd"';
	echo '>
			<td>'.$row['id'].'</td>
			<td>'.$row['sen'].'</td>
		</tr>
		';
	$i++;
	}
	
	?>
    </tbody>
</table>
        </div>
    </div>
</div>


<?php } ?>
</body>
</html>
