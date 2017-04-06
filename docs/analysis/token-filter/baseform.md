
# Baseform

With the baseform analysis, you can use a token filter for reducing word forms to their base form.

Currently, only baseforms for german and english are implemented.

Example: the german base form of `zurückgezogen` is `zurückziehen`.

name | description
---- | -----------
language | (optional) default is de. Currently only de or en (see https://github.com/jprante/elasticsearch-plugin-bundle/tree/master/src/main/resources/baseform)
respect_keywords | (optional) true or false

## Examples

### German

Kibana query
```
GET _analyze
{
  "tokenizer": "standard",
  "filter": [
    {
      "type": "baseform"
    }
  ],
  "text": "gehe"
}
```

Result
```
{
  "tokens": [
    {
      "token": "gehe",
      "start_offset": 0,
      "end_offset": 4,
      "type": "<ALPHANUM>",
      "position": 0
    },
    {
      "token": "gehen",
      "start_offset": 0,
      "end_offset": 4,
      "type": "<ALPHANUM>",
      "position": 0
    }
  ]
}
```

### English

Kibana query
```
GET _analyze
{
  "tokenizer": "standard",
  "filter": [
    {
      "type": "baseform",
      "language": "en"
    }
  ],
  "text": "going"
}
```

Result
```
{
  "tokens": [
    {
      "token": "going",
      "start_offset": 0,
      "end_offset": 5,
      "type": "<ALPHANUM>",
      "position": 0
    },
    {
      "token": "go",
      "start_offset": 0,
      "end_offset": 5,
      "type": "<ALPHANUM>",
      "position": 0
    }
  ]
}
```
