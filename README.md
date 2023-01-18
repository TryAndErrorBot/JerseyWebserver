# Jersey Webserver

Demo project with a jersey server for the Benno Rest import. This server can be run locally.

## Introduce

The server receives the file and outputs its name.

## Instruction

### Upload a file

To upload a file, use the following curl command:

    curl -X POST -F "file=@/path/to/file" http://localhost:8080/rest/inbox