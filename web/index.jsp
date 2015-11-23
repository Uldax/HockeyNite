<%-- 
    Document   : newjsp
    Created on : Nov 16, 2015, 4:34:45 PM
    Author     : JUASP-G73-Android
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Travail pratique 3 - AJAX</title>

    <!-- Bootstrap Core CSS - Uses Bootswatch Flatly Theme: http://bootswatch.com/flatly/ -->
    <link href="css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom CSS -->
    <link href="css/freelancer.css" rel="stylesheet">

    <!-- Custom Fonts -->
    <link href="font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">
    <link href="http://fonts.googleapis.com/css?family=Montserrat:400,700" rel="stylesheet" type="text/css">
    <link href="http://fonts.googleapis.com/css?family=Lato:400,700,400italic,700italic" rel="stylesheet" type="text/css">

    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->

</head>

<body id="page-top" class="index">

    <!-- Navigation -->
    <nav class="navbar navbar-default navbar-fixed-top">
        <div class="container">
            <!-- Brand and toggle get grouped for better mobile display -->
            <div class="navbar-header page-scroll">
                <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand" href="#page-top">TP3 - AJAX Client</a>
            </div>

            <!-- Collect the nav links, forms, and other content for toggling -->
            <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                <ul class="nav navbar-nav navbar-right">
                    <li class="hidden">
                        <a href="#page-top"></a>
                    </li>
                    <li class="page-scroll">
                        <a href="#portfolio">Matchs</a>
                    </li>
					<li class="page-scroll">
                        <a href="#updates">Updates</a>
                    </li>
                    <li class="page-scroll">
                        <a href="#about">About</a>
                    </li>
					 <li class="page-scroll betRelated"  style="display: none;">
                        <a href="#bets">My bets</a>
                    </li>
                  
                </ul>
            </div>
            <!-- /.navbar-collapse -->
        </div>
        <!-- /.container-fluid -->
    </nav>

    <!-- Header -->
    <header>
        <div class="container">
            <div class="row">
                <div class="col-lg-12">
                    <img class="img-responsive" src="img/profile.png" alt="">
                    <div class="intro-text">
                        <span class="name">HockeyNite AJAX</span>
                        <hr class="star-light">
                        <span class="skills">Matchs overview - Match details - Betting system</span>
                    </div>
                </div>
            </div>
        </div>
    </header>

    <!-- Portfolio Grid Section -->
    <section id="portfolio">
        <div class="container">
            <div class="row">
                <div class="col-lg-12 text-center">
                    <h2>Matchs</h2>
                    <hr class="star-primary">
                </div>
            </div>
            <div class="row">
                <div class="col-sm-4 portfolio-item">
                    <a href="#portfolioModal1" id="0" class="portfolio-link" data-toggle="modal">
                        <div class="caption">
                            <div class="caption-content">
                                <i class="fa fa-search-plus fa-3x"></i>
                            </div>
                        </div>
                        <img src="img/portfolio/avsb.png" class="img-responsive" alt="">
                    </a>
                    <div class="col-sm-offset-3 col-sm-4">
                    <button onclick="myFunction(0,'A VS B')">Bet on this match</button>
                       </div>
                </div>
                <div class="col-sm-4 portfolio-item">
                    <a href="#portfolioModal2" id="1" class="portfolio-link" data-toggle="modal">
                        <div class="caption">
                            <div class="caption-content">
                                <i class="fa fa-search-plus fa-3x"></i>
                            </div>
                        </div>
                        <img src="img/portfolio/cvsd.png" class="img-responsive" alt="">
                    </a>
                    <div class="col-sm-offset-3 col-sm-4">
                    <button onclick="myFunction(1,'C VS D')">Bet on this match</button>
                       </div>
                </div>
                <div class="col-sm-4 portfolio-item">
                    <a href="#portfolioModal3" id="2" class="portfolio-link" data-toggle="modal">
                        <div class="caption">
                            <div class="caption-content">
                                <i class="fa fa-search-plus fa-3x"></i>
                            </div>
                        </div>
                        <img src="img/portfolio/avsd.png" class="img-responsive" alt="">
                    </a>
                    <div class="col-sm-offset-3 col-sm-4">
                        <button onclick="myFunction(2,'A VS D')">Bet on this match</button>
                    </div>
                </div>
            </div>
        </div>
    </section>
	
	<!-- Updates Section -->
    <section class="success" id="updates">
        <div class="container">
            <div class="row">
                <div class="col-lg-12 text-center">
                    <h2>Updates</h2>
                    <hr class="star-light">
                </div>
            </div>
            <div class="row">
            <div class="col-lg-8 col-lg-offset-2">
					<p>This is an example of match update for the match A vs D.</p>
                    
            </div>
		    <div class="row">
			</div>
                    <div class="col-lg-8 col-lg-offset-2">
					<p>This is an example of match update for the match A vs D.</p>
                    
            </div>
            </div>
        </div>
    </section>
	
	
    <!-- About Section -->
    <section  id="about">
        <div class="container">
            <div class="row">
                <div class="col-lg-12 text-center">
                    <h2>About</h2>
                    <hr class="star-primary">
                </div>
            </div>
            <div class="row">
                <div class="col-lg-4 col-lg-offset-2">
                    <p>This is a web client for our Java Server. This client use AJAX to fetch all the informations from the Server.</p>
                </div>
                <div class="col-lg-4">
                    <p>This is a web client for our Java Server. This client use AJAX to fetch all the informations from the Server.</p>
                </div>               
            </div>
        </div>
    </section>
	
	<!-- Bets Section -->
    <section class="success betRelated" id="bets" style="display: none;">
        <div class="container">
            <div class="row">
                <div class="col-lg-12 text-center">
                    <h2>My bets</h2>
                    <hr class="star-light">
                </div>
            </div>
            <div class="row">
            <div class="col-lg-8 col-lg-offset-2">
					<p>To receive an update on all the bets you placed. Please click the button below.</p>                
            
			</div>                    
			 <div class="col-lg-8 col-lg-offset-2 text-center">
                    <a href="#portfolioModalBets" class="portfolio-link btn btn-lg btn-outline" data-toggle="modal">
                        <i class="fa fa-download"></i> Get my bets results
                    </a>
                </div>
            </div>
        </div>
    </section>



    <!-- Footer -->
    <footer class="text-center">
        <div class="footer-above">
            <div class="container">
                <div class="row">       
             
                    <div class="footer-col col-md-4">
                        <h3>TP3</h3>
                        <p>AJAX Client</p>
                    </div>
                </div>
            </div>
        </div>
        <div class="footer-below">
            <div class="container">
                <div class="row">
                    <div class="col-lg-12">
                        Copyright &copy; Univeristé de Sherbrooke, Julien Aspirot, Paul Contat and Charly Bong.
                    </div>
                </div>
            </div>
        </div>
    </footer>

    <!-- Scroll to Top Button (Only visible on small and extra-small screen sizes) -->
    <div class="scroll-top page-scroll visible-xs visible-sm">
        <a class="btn btn-primary" href="#page-top">
            <i class="fa fa-chevron-up"></i>
        </a>
    </div>

    <!-- Portfolio Modals -->
    <div class="portfolio-modal modal fade" id="portfolioModal1" tabindex="-1" role="dialog" aria-hidden="true">
        <div class="modal-content">
            <div class="close-modal" data-dismiss="modal">
                <div class="lr">
                    <div class="rl">
                    </div>
                </div>
            </div>
            <div class="container">
                <div class="row">
                    <div class="col-lg-8 col-lg-offset-2">
                        <div class="modal-body">
                            <h2>Match Team A vs Team B</h2>
                            <hr class="star-primary">
                            <img src="img/portfolio/avsb.png" class="img-responsive img-centered" alt="">
                            <h3>Events</h3>
                            <p id="0_events"></p>
                            <ul class="list-inline item-details">
                                <li>Time:
                                    <strong><span id="0_time">Start Bootstrap</span>
                                    </strong>
                                </li>
                                <li>Periode:
                                    <strong><span id="0_periode">Start Bootstrap</span>
                                    </strong>
                                </li>                              
                            </ul>
                             <ul class="list-inline item-details">
                                <li>[A] team score:
                                    <strong><span id="0_score_home">Start Bootstrap</span>
                                    </strong>
                                </li>
                                <li>[B] team score:
                                    <strong><span id="0_score_ext">Start Bootstrap</span>
                                    </strong>
                                </li>                              
                            </ul>
                            <button type="button" class="btn btn-default" data-dismiss="modal"><i class="fa fa-times"></i> Close</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        
    </div>
    <div class="portfolio-modal modal fade" id="portfolioModal2" tabindex="-1" role="dialog" aria-hidden="true">
        <div class="modal-content">
            <div class="close-modal" data-dismiss="modal">
                <div class="lr">
                    <div class="rl">
                    </div>
                </div>
            </div>
            <div class="container">
                <div class="row">
                    <div class="col-lg-8 col-lg-offset-2">
                        <div class="modal-body">
                            <h2>Match Team C vs Team D</h2>
                            <hr class="star-primary">
                            <img src="img/portfolio/cvsd.png" class="img-responsive img-centered" alt="">
                            <h3>Events</h3>
                            <p id="1_events"></p>
                            <ul class="list-inline item-details">
                                <li>Time:
                                    <strong><span id="1_time">Start Bootstrap</span>
                                    </strong>
                                </li>
                                <li>Periode:
                                    <strong><span id="1_periode">Start Bootstrap</span>
                                    </strong>
                                </li>                              
                            </ul>
                             <ul class="list-inline item-details">
                                <li>[C] team score:
                                    <strong><span id="1_score_home">Start Bootstrap</span>
                                    </strong>
                                </li>
                                <li>[D] team score:
                                    <strong><span id="1_score_ext">Start Bootstrap</span>
                                    </strong>
                                </li>                              
                            </ul>
                            <button type="button" class="btn btn-default" data-dismiss="modal"><i class="fa fa-times"></i> Close</button>
                        </div>
                    </div>
                </div>
                
            </div>
        </div>
    </div>
    <div class="portfolio-modal modal fade" id="portfolioModal3" tabindex="-1" role="dialog" aria-hidden="true">
        <div class="modal-content">
            <div class="close-modal" data-dismiss="modal">
                <div class="lr">
                    <div class="rl">
                    </div>
                </div>
            </div>
            <div class="container">
                <div class="row">
                    <div class="col-lg-8 col-lg-offset-2">
                        <div class="modal-body">
                            <h2>Match Team A vs Team D</h2>
                            <hr class="star-primary">
                            <img src="img/portfolio/avsd.png" class="img-responsive img-centered" alt="">
                            <h3>Events</h3>
                            <p id="2_events"></p>
                            <ul class="list-inline item-details">
                                <li>Time:
                                    <strong><span id="2_time">Start Bootstrap</span>
                                    </strong>
                                </li>
                                <li>Periode:
                                    <strong><span id="2_periode">Start Bootstrap</span>
                                    </strong>
                                </li>                              
                            </ul>
                            <ul class="list-inline item-details">
                                <li>[A] team score:
                                    <strong><span id="2_score_home">Start Bootstrap</span>
                                    </strong>
                                </li>
                                <li>[D] team score:
                                    <strong><span id="2_score_ext">Start Bootstrap</span>
                                    </strong>
                                </li>                              
                            </ul>
                            <button type="button" class="btn btn-default" data-dismiss="modal"><i class="fa fa-times"></i> Close</button>
                        </div>
                    </div>
                </div>
                
            </div>
        </div>
    </div>
	    <div class="portfolio-modal modal fade" id="portfolioModalBets" tabindex="-1" role="dialog" aria-hidden="true">
        <div class="modal-content">
            <div class="close-modal" data-dismiss="modal">
                <div class="lr">
                    <div class="rl">
                    </div>
                </div>
            </div>
            <div class="container">
                <div class="row">
                    <div class="col-lg-8 col-lg-offset-2">
                        <div class="modal-body">
                            <h2>Bets results</h2>
                            <hr class="star-primary">
                            <img src="img/portfolio/safe.png" class="img-responsive img-centered" alt="">
                            <p>Balbalbal Bet 1 won</p>
                            <ul class="list-inline item-details">
                                <li>Client:
                                    <strong><a href="http://startbootstrap.com">Start Bootstrap</a>
                                    </strong>
                                </li>
                                <li>Date:
                                    <strong><a href="http://startbootstrap.com">April 2014</a>
                                    </strong>
                                </li>
                                <li>Service:
                                    <strong><a href="http://startbootstrap.com">Web Development</a>
                                    </strong>
                                </li>
                            </ul>
                            <button type="button" class="btn btn-default" data-dismiss="modal"><i class="fa fa-times"></i> Close</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
 

    <!-- jQuery -->
    <script src="js/jquery.js"></script>

    <!-- Bootstrap Core JavaScript -->
    <script src="js/bootstrap.min.js"></script>

    <!-- Plugin JavaScript -->
    <script src="http://cdnjs.cloudflare.com/ajax/libs/jquery-easing/1.3/jquery.easing.min.js"></script>
    <script src="js/classie.js"></script>
    <script src="js/cbpAnimatedHeader.js"></script>

    <!-- Contact Form JavaScript -->
    <script src="js/jqBootstrapValidation.js"></script>
  

    <!-- Custom Theme JavaScript -->
    <script src="js/freelancer.js"></script>

</body>

</html>
<script>
$( document ).ready(function() {
    setInterval(function(){
        var randomnumber = Math.floor(Math.random() * (2 - 0 + 1)) + 0;
        console.log("loop interval 2mins");
        $.getJSON( "/TP3/matchdetails?idMatch="+randomnumber, function( data ) {             
         alert(data.matchEvent[data.matchEvent.length - 1].message);
         console.log("popup match "+randomnumber);
        });       
    }, 120000);
        
   
    console.log( "ready!" );
    $("a.portfolio-link").click(function(e){
        e.preventDefault();
        console.log($(this).attr('id'));
        var id = $(this).attr('id');
        $.getJSON( "/TP3/matchdetails?idMatch="+ id, function( data ) { 
            
          console.log(data);
          console.log(data.time);
          console.log(data.periode);
          console.log(data.domicileScore);
          console.log(data.exterieurScore);
          var html = "";
         for (i = 0; i < data.matchEvent.length; i++) { 
            html += data.matchEvent[i].message + "<br>";
        }
          console.log(html);
          $("#" + id + "_events").html(html);
        
          $("#" + id + "_time").html(data.time);
           $("#" + id + "_periode").html(data.periode);
           $("#" + id + "_score_home").html(data.domicileScore);
           $("#" + id + "_score_ext").html(data.exterieurScore);
         });
    });
});
</script>


<p id="demo"></p>

<script>
function myFunction(idMatch, infoMatch) {
    var team = prompt("Match: " + infoMatch + " pick your team!");
    team = team.toUpperCase();
    if (team != null) {
        var montant = prompt("How much to do want to bet?");
        montant = parseFloat(montant);
        
        if(montant !=null){
            $.get( "/TP3/makeabet?idMatch="+idMatch+"&equipe="+team+"&montant="+montant, function( data ) {
                if(data == 1)
                {
                   alert( "Success! Your bet has been saved!" );

                }
                else if(data == 0)
                {
                                  alert( "Failed! You cannot bet on this match anymore! Second period is over!" );
                }
               else
               {
                  alert("Failed! something died on the server!"); 
                }

              });
        }
    }
}
</script>

