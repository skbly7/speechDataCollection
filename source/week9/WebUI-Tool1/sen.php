<!doctype html>
<html lang="en">
<head>
    <meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="A layout example with a side menu that hides on mobile, just like the Pure website.">

    <title>Akshar Speech | Pending URLs</title>
    


<link rel="stylesheet" href="http://yui.yahooapis.com/pure/0.5.0/pure-min.css">

        <link rel="stylesheet" href="basic.css">


</head>
<body>






<div id="layout">
    <?php include "sidebar.php"; ?>

    <div id="main">
        <div class="header">
            <h2>Please select options and download list of sentances accordingly.</h2>
        </div>
        <div class="content">
					<form class="pure-form pure-form-stacked">
						<fieldset>
							<label for="email">URL</label>
							<input class="pure-input-1"  id="email" type="text" placeholder="Enter your URL here">
    <div class="pure-u-1-2" style="width:49%;">
        							<label for="language">Language</label>
							<select id="language" class="pure-u-1">
								<option>Choose language</option>
								<option>Hindi</option>
								<option>English</option>
								<option>Arabic</option>
								<option>Telgue</option>
								<option>Language</option>
								<option>Language</option>
								<option>Language</option>
								<option>Language</option>
								<option>Language</option>
								<option>Language</option>
								<option>Language</option>
							</select>
    </div>
    <div class="pure-u-1-2">
        							<label for="language">Website</label>
							<select id="website" class="pure-u-1">
								<option>Choose website</option>
								<option>wikipedia.org</option>
								<option>almokhtsar.com</option>
								<option>al-jazirah.com</option>
								<option>timesofindia.indiatimes.com</option>
								<option>Website</option>
								<option>Website</option>
								<option>Website</option>
								<option>Website</option>
								<option>Website</option>
								<option>Website</option>
							</select>
    </div>


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
	
							<label for="remember" class="pure-checkbox">
								<input id="remember" type="checkbox"> Recursively
							</label>
							<button type="submit" class="pure-button pure-button-primary">Submit</button>
						</fieldset>
					</form>
        </div>
   
    </div>
</div>



</body>
</html>
