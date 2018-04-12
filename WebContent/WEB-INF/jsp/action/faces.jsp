<!DOCTYPE HTML>
<html>
<head>
    <title>Faces Search</title>
    <link rel="stylesheet" href="//ciolab.ibm.com/misc/typeahead/builds/facestypeahead-0.4.4.min.css"/>
    <script src="//ciolab.ibm.com/misc/typeahead/builds/facestypeahead-0.4.4.min.js"></script>

   <script>

        $(function(){
            ta1 = FacesTypeAhead.init(
                $('#faces-input')[0],
                {
                    key: "typeahead_demo;webahead@us.ibm.com",
                    faces: {
                        headerLabel: "People",

                        onclick: function(person) {
                            return person.email;
                        }
                    },
                    topsearch: {
                        headerLabel: "w3 Results",
                        enabled: true
                    }
                });

            ta2 = FacesTypeAhead.init(
                $("#faces-input2")[0],
                {
                    key: "typeahead_demo;webahead@us.ibm.com"
                }
            );
        });
    </script>
</head>
<body>
    <header><h1><img src="i/faces-logo.png" alt="Faces" /></h1></header>
    <div>Faces and topsearch: <input id="faces-input" class="typeahead" autocomplete="off" /></div>
    <br />
    <div>Faces only: <input id="faces-input2" class="typeahead" /></div>
    <br />
    <br />
    <br />
    <br />
    <br />
    <br />
    <br />
    <br />
    <br />
    <br />
    <br />

    <br />
    <br />
    <br />
    <br />
    <br />
    <br />
    <br />
    <br />
    <br />
    <br />
    <br />
    <br />
    <br />
    <br />
    <br />
    <br />
    <br />
    <br />
    <br />

    <br />
    <br />
    <br />
    <br />
    <br />
    <br />
    <br />
</body>
</html>
