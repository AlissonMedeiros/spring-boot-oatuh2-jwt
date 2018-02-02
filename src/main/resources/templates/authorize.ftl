<html>
    <head>
        <link rel="stylesheet" type="text/css"
              href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" />
        <script
                src="http://code.jquery.com/jquery-3.3.1.min.js"
                integrity="sha256-FgpCb/KJQlLNfOu91ta32o/NMZxltwRo8QtmkMRdAu8="
                crossorigin="anonymous"></script>
        <script type="text/javascript"
                src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
    </head>
    <body>
    <div class="container">
        <h2>Please Confirm</h2>

        <p>
            Do you authorize "${authorizationRequest.clientId}" at "${authorizationRequest.redirectUri}" to access your protected resources
            with scope ${authorizationRequest.scope?join(", ")}.
        </p>
        <form id="confirmationForm" name="confirmationForm"
              action="../oauth/authorize" method="post">
            <input name="user_oauth_approval" value="true" type="hidden" />
            <input type="hidden" id="csrf_token" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            <button class="btn btn-primary" type="submit">Approve</button>
        </form>
        <form id="denyForm" name="confirmationForm"
              action="../oauth/authorize" method="post">
            <input name="user_oauth_approval" value="false" type="hidden" />
            <input type="hidden" id="csrf_token" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            <button class="btn btn-primary" type="submit">Deny</button>
        </form>
    </div>
    </body>
</html>
