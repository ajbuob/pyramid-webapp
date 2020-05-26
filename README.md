# pyramid-webapp

## Description
Sample pyramid web application, built with Spring Boot and Gradle. This webapp exposes a REST API endpoint to determine if the given word is a 'pyramid word' 

A word is a 'pyramid word' if the letters can be arranged in increasing frequency, starting with 1 and continuing without gaps and without duplicates.

###Examples
banana is a pyramid word because you have 1 'b', 2 'n's, and 3 'a's.

bandana is not a pyramid word because you have 1 'b' and 1 'd'.

### GET /pyramid/\<WORD\>

#### Query String Parameters
#####  displayResultMap = [true|false] (optional, populates integerToCharacterMap when isPyramidWord=true)

#### JSON Response

```json
{
    "word": "banana",
    "integerToCharacterMap": {
        "1": "b",
        "2": "n",
        "3": "a"
    },
    "isPyramidWord": true
}
```
OR

```json
{
    "word": "bandana",
    "integerToCharacterMap": null,
    "isPyramidWord": false
}
```

#### HTTP Response Codes

200 (OK) 

400 (Bad Request) if the submitted WORD contains characters other than letters. (alphabetic characters)

500 (Internal Server Error)

## NOTES
* Included the exported Postman collection file (Pyramid.postman_collection.json) in the projects root directory which can also be used for any integration testing. 


