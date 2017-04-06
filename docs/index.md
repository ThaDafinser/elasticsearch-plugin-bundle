

# ES plugin bundle

Since this plugin is a bundle of many plugins, it comes with a bunch of features!

## APIs
- [ISBN](api/isbn.md)
- [Langdetect](api/langdetect.md)

## Mapping

### Field datatypes
- Crypt
- Langdetect
- Reference
- Standardnumber


## Analysis

### Analyzers
- hyphen
- icu_collation
- naturalsort
- sortform
- standardnumber

### Tokenizers
- hyphen
- icu_collation_tokenizer
- icu_tokenizer
- naturalsort

### Token filters
- auto phrasing
- [Baseform](analysis/token-filter/baseform.md)
- concat
- decompound (fst or patricia)
- german normalization
- hyphen
- icu_folding
- icu normalizer
- icu_numberformat
- icu_transform
- lemmatize
- pair
- sortform
- standardnumber
- symbolname
- worddelimiter (worddelimiter2)
- year

## Final note
This list was extracted from this file 
https://github.com/jprante/elasticsearch-plugin-bundle/blob/master/src/main/java/org/xbib/elasticsearch/plugin/bundle/BundlePlugin.java

So if the list gets out of date, grab their the latest changes!

The chapters are hold equal to ES reference guide, so it should be simple to follow
https://www.elastic.co/guide/en/elasticsearch/reference/5.3/index.html
