
# ISBN API

A simple API to convert a ISBN number into multiple ISBN10 and ISBN13 formats

## Examples

### ISBN-13
Kibana
```
GET _isbn/978-1-56619-909-4
```

Result
```
{
  "result": {
    "isbn10": "1566199093",
    "isbn10formatted": "1-56619-909-3",
    "isbn13": "9781566199094",
    "isbn13formatted": "978-1-56619-909-4",
    "invalid": null
  }
}
```

### ISBN-10
Kibana
```
GET _isbn/0-19-852663-6
```

Result
```
{
  "result": {
    "isbn10": "0198526636",
    "isbn10formatted": "0-19-852663-6",
    "isbn13": "9780198526636",
    "isbn13formatted": "978-0-19-852663-6",
    "invalid": null
  }
}
```

### Invalid number

Kibana
```
GET _isbn/1337
```

Result
```
{
  "result": {
    "isbn10": null,
    "isbn10formatted": null,
    "isbn13": null,
    "isbn13formatted": null,
    "invalid": "1337"
  }
}
```
