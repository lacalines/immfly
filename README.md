# immfly

Application can be started by calling gradle bootRun task.

Role check was added to the endpoint but can be disabled until full authentication is implemented by using spring 'unsecured' profile instead of the 'default' one.
Note that the application will still require access to a redis instance and to the external endpoint.
