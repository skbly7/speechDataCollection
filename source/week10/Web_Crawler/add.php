<?php
include "include/connect.php";
?>
<!doctype html>
<html lang="en">
<head>
    <meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>Akshar Speech | Text Analysis</title>
    


<link rel="stylesheet" href="http://yui.yahooapis.com/pure/0.5.0/pure-min.css">

        <link rel="stylesheet" href="basic.css">


</head>
<body>






<div id="layout">
    <?php include "include/sidebar.php"; 

	if(isset($_GET['msg'])&&$_GET['msg']!="")
	{
		echo '<script>alert(\'';
		echo $_GET['msg'];
		echo '\');</script>';
	}
	?>

    <div id="main">
        <div class="header">
            <h2>Add new URL and select options</h2>
        </div>

        <div class="content">
					<form class="pure-form pure-form-stacked" action="db/add_url.php" method="GET">
						<fieldset>
							<label for="email">URL</label>
							<input class="pure-input-1"  name="URL" type="text" placeholder="Enter your URL here" required>
							<div class="pure-u-1-2" style="width:49%;">
								<label for="language">Language</label>
								<select name="lang" class="pure-u-1">
								<?php language_list(); ?>
								</select>
							</div>
							<div class="pure-u-1-2">
								<label for="language">Website</label>
								<select name="website" class="pure-u-1">
								<?php website_url(); ?>
								</select>
							</div>

<!--
	<label for="uwords">Unique words (Empty to ignore a field)</label>				
    <div class="pure-u-1-2" style="width:49%;">
        <input class="pure-input-1" type="text" placeholder="Minimum unique words">
    </div>
    <div class="pure-u-1-2">
        <input class="pure-input-1" type="text" placeholder="Maximum unique words">
    </div>
	<label for="uwords">Sentance length (Empty to ignore a field)</label>				
    <div class="pure-u-1-2" style="width:49%;">
        <input class="pure-input-1" type="text" placeholder="Minimum length">
    </div>
    <div class="pure-u-1-2">
        <input class="pure-input-1" type="text" placeholder="Maximum length">
    </div>
-->
	
							<label for="remember" class="pure-checkbox">
								<input name="re" type="checkbox" value="1"> Recursively
							</label>
							<button type="submit" class="pure-button pure-button-primary">Add URL</button>
						</fieldset>
					</form>
        </div>
    </div>
</div>



</body>
</html>
