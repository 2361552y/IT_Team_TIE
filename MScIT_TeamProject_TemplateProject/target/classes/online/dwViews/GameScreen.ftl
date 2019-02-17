<html>

<head>
    <!-- Web page title -->
    <title>Top Trumps</title>

    <!-- Import JQuery, as it provides functions you will probably find useful (see https://jquery.com/) -->
    <script src="https://code.jquery.com/jquery-2.1.1.js"></script>
    <script src="https://code.jquery.com/ui/1.11.1/jquery-ui.js"></script>
    <link rel="stylesheet" href="https://code.jquery.com/ui/1.11.1/themes/flick/jquery-ui.css">

    <!-- Optional Styling of the Website, for the demo I used Bootstrap (see https://getbootstrap.com/docs/4.0/getting-started/introduction/) -->
    <link rel="stylesheet" href="http://dcs.gla.ac.uk/~richardm/TREC_IS/bootstrap.min.css">
    <script src="http://dcs.gla.ac.uk/~richardm/vex.combined.min.js"></script>
    <script>vex.defaultOptions.className = 'vex-theme-os';</script>
    <link rel="stylesheet" href="http://dcs.gla.ac.uk/~richardm/assets/stylesheets/vex.css"/>
    <link rel="stylesheet" href="http://dcs.gla.ac.uk/~richardm/assets/stylesheets/vex-theme-os.css"/>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css">

</head>

<body onload="initalize()"> <!-- Call the initalize method when the page loads -->

<div class="container">
    <div class="Top">
        <div class="row">
            <div class="col-sm-2 col-md-2 "></div>
            <div class="col-sm-8 col-md-8 "
                 style="background-color: black;box-shadow: inset 1px -1px 1px #444, inset -1px 1px 1px #9e7d86;">
                <p>
                <ul class="nav nav-tabs">
                    <li class="active"><a href="#">Top trumps game</a></li>
                </ul>
                </p>
            </div>
            <div class="col-sm-2 col-md-2 "></div>
        </div>
        <div class="row">
            <div class="col-sm-2 col-md-2 "></div>
            <div class="col-sm-8 col-md-8">
                <p>
                <ul class="nav nav-tabs" style="border-color: black">
                    <li><a id="roundNumber">need round number(js)</a></li>
                </ul>
                </p>
            </div>
            <div class="col-sm-2 col-md-2 "></div>
        </div>
    </div>
    <div class="Middle">
        <div id="player012" class="row">
            <!--blank div-->
            <div class="col-sm-2 col-md-2 "></div>
            <!--first list-->
            <div  class="col-sm-2 col-md-2 ">
                <div id="selections" class="list-group">
                    <a id="currentPlayer">The active player is </a>
                    <a id="s1" onclick="humanChoose(1)" href="##" class="list-group-item">Select: Size</a>
                    <a id="s2" onclick="humanChoose(2)" href="##" class="list-group-item">Select: Speed</a>
                    <a id="s3" onclick="humanChoose(3)" href="##" class="list-group-item ">Select: Range</a>
                    <a id="s4" onclick="humanChoose(4)" href="##" class="list-group-item ">Select: Firepower</a>
                    <a id="s5" onclick="humanChoose(5)" href="##" class="list-group-item ">Select: Cargo</a>
                </div>
            </div>
            <!--second list: player card-->
            <div id="user" class="col-sm-2 col-md-2 ">
                <ul class="list-group">
                    <a class="list-group-item active">You</a>
                    <a id="usercname" class="list-group-item">Card name(js)</a>
                    <div class="panel panel-default">
                        <div class="panel-body"><img id="userci" class="img-thumbnail"
                                                     src="http://dcs.gla.ac.uk/~richardm/TopTrumps/Avenger.jpg "></div>
                    </div>
                    <a id="userc1" class="list-group-item">Size: (js)</a>
                    <a id="userc2" class="list-group-item">Speed</a>
                    <a id="userc3" class="list-group-item ">Range</a>
                    <a id="userc4" class="list-group-item ">Firepower</a>
                    <a id="userc5" class="list-group-item ">Cargo</a>
                </ul>
            </div>
            <!--AI1-->
            <div id="ai1" class="col-sm-2 col-md-2 ">
                <ul class="list-group">
                    <a class="list-group-item active">AI player1</a>
                    <a id="ai1cname" class="list-group-item">Card name(js)</a>
                    <div class="panel panel-default">
                        <div class="panel-body"><img id="ai1ci" class="img-thumbnail"
                                                     src="http://dcs.gla.ac.uk/~richardm/TopTrumps/Avenger.jpg "></div>

                    </div>
                    <a id="ai1c1" class="list-group-item">Size: (js)</a>
                    <a id="ai1c2" class="list-group-item">Speed</a>
                    <a id="ai1c3" class="list-group-item ">Range</a>
                    <a id="ai1c4" class="list-group-item ">Firepower</a>
                    <a id="ai1c5" class="list-group-item ">Cargo</a>
                </ul>
            </div>
            <!--AI2-->
            <div id="ai2" class="col-sm-2 col-md-2 ">
                <ul class="list-group">
                    <a class="list-group-item active">AI player2</a>
                    <a id="ai2cname" class="list-group-item">Card name(js)</a>
                    <div class="panel panel-default">
                        <div class="panel-body"><img id="ai2ci" class="img-thumbnail"
                                                     src="http://dcs.gla.ac.uk/~richardm/TopTrumps/Avenger.jpg "></div>

                    </div>
                    <a id="ai2c1" class="list-group-item">Size: (js)</a>
                    <a id="ai2c2" class="list-group-item">Speed</a>
                    <a id="ai2c3" class="list-group-item ">Range</a>
                    <a id="ai2c4" class="list-group-item ">Firepower</a>
                    <a id="ai2c5" class="list-group-item ">Cargo</a>
                </ul>
            </div>
        </div>
    </div>
    <br/>
    <div id="bottom">
        <div id="player34" class="row">
            <div class="col-sm-6 col-md-6 "></div>
            <!--AI3-->
            <div class="col-sm-2 col-md-2 ">
                <ul class="list-group">
                    <a class="list-group-item active">AI player3</a>
                    <a id="ai3cname" class="list-group-item">Card name(js)</a>
                    <div class="panel panel-default">
                        <div class="panel-body"><img id="ai3ci" class="img-thumbnail"
                                                     src="http://dcs.gla.ac.uk/~richardm/TopTrumps/Avenger.jpg "></div>

                    </div>
                    <a id="ai3c1" class="list-group-item">Size: (js)</a>
                    <a id="ai3c2" class="list-group-item">Speed</a>
                    <a id="ai3c3" class="list-group-item ">Range</a>
                    <a id="ai3c4" class="list-group-item ">Firepower</a>
                    <a id="ai3c5" class="list-group-item ">Cargo</a>
                </ul>
            </div>
            <!--AI4-->
            <div id="ai4" class="col-sm-2 col-md-2 ">
                <ul class="list-group">
                    <a class="list-group-item active">AI player4</a>
                    <a id="ai4cname" class="list-group-item">Card name(js)</a>
                    <div class="panel panel-default">
                        <div class="panel-body"><img id="ai4ci" class="img-thumbnail"
                                                     src="http://dcs.gla.ac.uk/~richardm/TopTrumps/Avenger.jpg "></div>

                    </div>
                    <a id="ai4c1" class="list-group-item">Size: (js)</a>
                    <a id="ai4c2" class="list-group-item">Speed</a>
                    <a id="ai4c3" class="list-group-item ">Range</a>
                    <a id="ai4c4" class="list-group-item ">Firepower</a>
                    <a id="ai4c5" class="list-group-item ">Cargo</a>
                </ul>
            </div>
        </div>
    </div>

</div>

<script type="text/javascript">
    var roundCards;
    var roundNumbers;
    var middle = document.getElementById("middle");
    var bottom = document.getElementById("bottom");
    var player012 = document.getElementById("player012");
    var player34 = document.getElementById("player34");

    // Method that is called on page load
    function initalize() {

        // --------------------------------------------------------------------------
        // You can call other methods you want to run when the page first loads here
        // --------------------------------------------------------------------------
        getRoundCards();
        getRoundNumbers();
    }

    // -----------------------------------------
    // Add your other Javascript methods Here
    // -----------------------------------------
    // This calls the getRoundCards REST method from TopTrumpsRESTAPI
    function getRoundCards() {

        // First create a CORS request, this is the message we are going to send (a get request in this case)
        var xhr = createCORSRequest('GET', "http://localhost:7777/toptrumps/getRoundCards"); // Request type and URL

        // Message is not sent yet, but we can check that the browser supports CORS
        if (!xhr) {
            alert("CORS not supported");
        }

        // CORS requests are Asynchronous, i.e. we do not wait for a response, instead we define an action
        // to do when the response arrives
        xhr.onload = function (e) {
            roundCards = JSON.parse(xhr.response); // the text of the response

            switch (roundCards.length) {
                case 5:
                    document.getElementById("ai4cname").innerHTML = "Card name: " + roundCards[4][1];
                    document.getElementById("ai4ci").src = "http://dcs.gla.ac.uk/~richardm/TopTrumps/" + roundCards[4][1] + ".jpg";
                    document.getElementById("ai4c1").innerHTML = "Size: " + roundCards[4][2];
                    document.getElementById("ai4c2").innerHTML = "Speed: " + roundCards[4][3];
                    document.getElementById("ai4c3").innerHTML = "Range: " + roundCards[4][4];
                    document.getElementById("ai4c4").innerHTML = "Firepower: " + roundCards[4][5];
                    document.getElementById("ai4c5").innerHTML = "Cargo: " + roundCards[4][6];
                case 4:
                    document.getElementById("ai3cname").innerHTML = "Card name: " + roundCards[3][1];
                    document.getElementById("ai3ci").src = "http://dcs.gla.ac.uk/~richardm/TopTrumps/" + roundCards[3][1] + ".jpg";
                    document.getElementById("ai3c1").innerHTML = "Size: " + roundCards[3][2];
                    document.getElementById("ai3c2").innerHTML = "Speed: " + roundCards[3][3];
                    document.getElementById("ai3c3").innerHTML = "Range: " + roundCards[3][4];
                    document.getElementById("ai3c4").innerHTML = "Firepower: " + roundCards[3][5];
                    document.getElementById("ai3c5").innerHTML = "Cargo: " + roundCards[3][6];
                case 3:
                    document.getElementById("ai2cname").innerHTML = "Card name: " + roundCards[2][1];
                    document.getElementById("ai2ci").src = "http://dcs.gla.ac.uk/~richardm/TopTrumps/" + roundCards[2][1] + ".jpg";
                    document.getElementById("ai2c1").innerHTML = "Size: " + roundCards[2][2];
                    document.getElementById("ai2c2").innerHTML = "Speed: " + roundCards[2][3];
                    document.getElementById("ai2c3").innerHTML = "Range: " + roundCards[2][4];
                    document.getElementById("ai2c4").innerHTML = "Firepower: " + roundCards[2][5];
                    document.getElementById("ai2c5").innerHTML = "Cargo: " + roundCards[2][6];
                case 2:
                    document.getElementById("usercname").innerHTML = "Card name: " + roundCards[0][1];
                    document.getElementById("userci").src = "http://dcs.gla.ac.uk/~richardm/TopTrumps/" + roundCards[0][1] + ".jpg";
                    document.getElementById("userc1").innerHTML = "Size: " + roundCards[0][2];
                    document.getElementById("userc2").innerHTML = "Speed: " + roundCards[0][3];
                    document.getElementById("userc3").innerHTML = "Range: " + roundCards[0][4];
                    document.getElementById("userc4").innerHTML = "Firepower: " + roundCards[0][5];
                    document.getElementById("userc5").innerHTML = "Cargo: " + roundCards[0][6];
                    document.getElementById("ai1cname").innerHTML = "Card name: " + roundCards[1][1];
                    document.getElementById("ai1ci").src = "http://dcs.gla.ac.uk/~richardm/TopTrumps/" + roundCards[1][1] + ".jpg";
                    document.getElementById("ai1c1").innerHTML = "Size: " + roundCards[1][2];
                    document.getElementById("ai1c2").innerHTML = "Speed: " + roundCards[1][3];
                    document.getElementById("ai1c3").innerHTML = "Range: " + roundCards[1][4];
                    document.getElementById("ai1c4").innerHTML = "Firepower: " + roundCards[1][5];
                    document.getElementById("ai1c5").innerHTML = "Cargo: " + roundCards[1][6];
                    break;
            }
            player012.style.display = "none";
            player34.style.display = "none";
        }

        // We have done everything we need to prepare the CORS request, so send it
        xhr.send();
    }

    // This calls the getRoundCards REST method from TopTrumpsRESTAPI
    function getRoundNumbers() {

        // First create a CORS request, this is the message we are going to send (a get request in this case)
        var xhr = createCORSRequest('GET', "http://localhost:7777/toptrumps/getRoundNumbers"); // Request type and URL

        // Message is not sent yet, but we can check that the browser supports CORS
        if (!xhr) {
            alert("CORS not supported");
        }

        // CORS requests are Asynchronous, i.e. we do not wait for a response, instead we define an action
        // to do when the response arrives
        xhr.onload = function (e) {
            //roundNumber + currentPlayerIndex.
            roundNumbers = JSON.parse(xhr.response); // the text of the response
            if(roundNumbers[2] == 1){
                player012.style.display = "";
                document.getElementById("user").style.display = "";
                document.getElementById("ai1").style.display = "none";
                document.getElementById("ai2").style.display = "none";
            }
            document.getElementById("roundNumber").innerHTML="Round " + roundNumbers[0] + ": Players have drawn their cards.";
            var currentPlayerID = roundNumbers[1];
            var text;
            if (currentPlayerID == "1") {
                text = "The active player is </br>You";
                document.getElementById("s1").innerHTML= "Select Size";
                document.getElementById("s2").innerHTML= "Select Speed";
                document.getElementById("s1").style.display = "";
                document.getElementById("s2").style.display = "";
                document.getElementById("s3").style.display = "";
                document.getElementById("s4").style.display = "";
                document.getElementById("s5").style.display = "";
            } else {
                text = "The active player is </br> AI Player " + (currentPlayerID-1);
                document.getElementById("s1").style.display = "none";
                document.getElementById("s2").onclick = aiChoose;
                document.getElementById("s2").innerHTML= "Next Step";
                document.getElementById("s3").style.display = "none";
                document.getElementById("s4").style.display = "none";
                document.getElementById("s5").style.display = "none";
            }
            document.getElementById("currentPlayer").innerHTML = text;
        }

        // We have done everything we need to prepare the CORS request, so send it
        xhr.send();
    }
    function aiChoose() {
        var character = parseInt(Math.random()*5,10)+1;
        switch (character) {
            case 1:
                document.getElementById("s1").innerHTML = "He selected Size.";
                break;
            case 2:
                document.getElementById("s1").innerHTML = "He selected Speed.";
                break;
            case 3:
                document.getElementById("s1").innerHTML = "He selected Range.";
                break;
            case 4:
                document.getElementById("s1").innerHTML = "He selected Firepower.";
                break;
            default:
                document.getElementById("s1").innerHTML = "He selected Cargo.";
                break;
        }
        document.getElementById("s1").style.display = "";
        selectCharacter(character);
        document.getElementById("s2").onclick = showResult;
    }
    function humanChoose(character) {
        selectCharacter(character);
        document.getElementById("s1").style.display = "none";
        document.getElementById("s2").innerHTML= "Next Step";
        document.getElementById("s2").onclick = showResult;
        document.getElementById("s3").style.display = "none";
        document.getElementById("s4").style.display = "none";
        document.getElementById("s5").style.display = "none";
    }
    function selectCharacter(character) {

        var xhr = createCORSRequest('GET', "http://localhost:7777/toptrumps/selectCharacter?Character="+character); // Request type and URL+parameters

        if (!xhr) {
            alert("CORS not supported");
        }
        // xhr.onload = function(e) {
        //     xhr.response; // the text of the response
        // };
        xhr.send();
    }
    function showResult() {
        var xhr = createCORSRequest('GET', "http://localhost:7777/toptrumps/showResult"); // Request type and URL

        if (!xhr) {
            alert("CORS not supported");
        }

        xhr.onload = function(e) {
            var result = xhr.response; // the text of the response
            player012.style.display = "";
            player34.style.display = "";
            document.getElementById("ai1").style.display = "";
            document.getElementById("ai2").style.display = "";
            document.getElementById("roundNumber").innerHTML = result;
            document.getElementById("s1").style.display = "none";
            document.getElementById("s2").onclick = showWinner;
        };

        xhr.send();
    }
    function showWinner() {
        // First create a CORS request, this is the message we are going to send (a get request in this case)
        var xhr = createCORSRequest('GET', "http://localhost:7777/toptrumps/showWinner"); // Request type and URL

        // Message is not sent yet, but we can check that the browser supports CORS
        if (!xhr) {
            alert("CORS not supported");
        }

        // CORS requests are Asynchronous, i.e. we do not wait for a response, instead we define an action
        // to do when the response arrives
        xhr.onload = function (e) {
            document.getElementById("user").style.display = "none";
            document.getElementById("ai1").style.display = "none";
            document.getElementById("ai2").style.display = "none";
            player34.style.display = "none";
            var responseText = JSON.parse(xhr.response); // the text of the response
            if(responseText.length == 1){
                middle.style.display = "none";
            }else {
                document.getElementById("s2").onclick = initalize;
            }
            document.getElementById("roundNumber").innerHTML = responseText;
        };

        // We have done everything we need to prepare the CORS request, so send it
        xhr.send();
    }
    // This is a reusable method for creating a CORS request. Do not edit this.
    function createCORSRequest(method, url) {
        var xhr = new XMLHttpRequest();
        if ("withCredentials" in xhr) {

            // Check if the XMLHttpRequest object has a "withCredentials" property.
            // "withCredentials" only exists on XMLHTTPRequest2 objects.
            xhr.open(method, url, true);

        } else if (typeof XDomainRequest != "undefined") {

            // Otherwise, check if XDomainRequest.
            // XDomainRequest only exists in IE, and is IE's way of making CORS requests.
            xhr = new XDomainRequest();
            xhr.open(method, url);

        } else {

            // Otherwise, CORS is not supported by the browser.
            xhr = null;

        }
        return xhr;
    }

</script>

</body>
</html>