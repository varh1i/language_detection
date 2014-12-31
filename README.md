language_detection
==================

Detects language of text files from different languages.


Creates language profiles from text files of different languages then predict the language of each document by comparing the distance of the document to all language profiles.
Not production quality but does the work very well. :)

Info about used datasource: language_detection/src/main/resources/naacl2010-langid/README.txt

Output: 
```bash
Calculation started for EuroGov dataset...
Created profile for 10 languages in: 11891 ms.
correct: 98.73333333333333% (1481/1500)
Time for making predictions: 8122 ms

Calculation started for Wikipedia dataset...
Created profile for 75 languages in: 3254 ms.
correct: 88.01128349788434% (4368/4963)
Time for making predictions: 8155 ms
```

