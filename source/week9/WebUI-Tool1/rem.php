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
            <h2>Enter unique ID of the sentance you want to remove</h2>
        </div>
        <div class="content">
					<form class="pure-form pure-form-stacked">
						<fieldset>
							


	<label for="uwords">Unique ID</label>				
    <div class="pure-u-1-2" style="width:49%;">
        <input class="pure-input-1" type="text" placeholder="Unique ID">
    </div>
    <div class="pure-u-1-2">.
    </div>
							<button type="submit" class="pure-button pure-button-primary">Submit</button>
						</fieldset>
					</form>
        </div>
   
    </div>
</div>



</body>
</html>
